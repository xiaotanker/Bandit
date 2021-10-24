package edu.nyu.hps;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Random;
import java.util.UUID;

@RestController
public class BanditController {
    private String gambler = null;
    private String casino = null;
    private String pwdGambler = null;
    private String pwdCasino = null;
    private Random rand = new Random();
    private Date casinoStartTime;
    private Date gamblerStartTime;
    private final long timeLimits = 1000*180;// 3min to make up the network cost
    private long casinoTime=0;
    private long gamblerTime=0;

    @Autowired
    private GameStatus status;


    Logger logger = LoggerFactory.getLogger(BanditController.class);

    private void setGame(){
        logger.info("game start, waiting for casino send move");
        status.setCasinoTurn(true);
        status.setStart(true);
        status.setSwitchLeft(status.getMaxSwitch());
        status.setDeposit(status.getTotalSlot()*100);
        casinoStartTime = new Date();
    }

    @PostMapping("/casino/join")
    public ResponseEntity<String> joinCasino(@RequestParam(value = "name", defaultValue = "casino") String name) {
        if (casino == null) {
            casino = name;
            pwdCasino = UUID.randomUUID().toString();
        } else {
            return ResponseEntity.status(403).body("already has a casino, can't join as casino\n");
        }
        logger.info("casino joined, name: "+ name);
        if (casino != null && gambler != null) {
            setGame();
        }
        return ResponseEntity.status(200).body(pwdCasino);
    }

    @PostMapping("/gambler/join")
    public ResponseEntity<String> joinGambler(@RequestParam(value = "name", defaultValue = "player") String name) {
        if (gambler == null) {
            gambler = name;
            pwdGambler = UUID.randomUUID().toString();
        } else {
            return ResponseEntity.status(403).body("already has a gambler, can't join as gambler\n");
        }
        logger.info("gambler joined, name: "+ name);

        if (casino != null && gambler != null) {
            setGame();
        }
        return ResponseEntity.status(200).body(pwdGambler);
    }

    @GetMapping("/casino/status")
    public ResponseEntity<CasinoStatus> getCasinoStatus(@RequestParam(value = "pwd") String pwd) {
        if (!pwd.equals(pwdCasino)) {
            logger.error("wrong pwd for casino");
            return ResponseEntity.status(401).body(null);
        }
        return ResponseEntity.status(200).body(new CasinoStatus(status));
    }

    @GetMapping("/gambler/status")
    public ResponseEntity<GamblerStatus> getGamblerStatus(@RequestParam(value = "pwd") String pwd) {
        if (!pwd.equals(pwdGambler)) {
            logger.error("wrong pwd for gambler");
            return ResponseEntity.status(401).body(null);
        }
        return ResponseEntity.status(200).body(new GamblerStatus(status));
    }

    @PostMapping("/casino/sendMove")
    public ResponseEntity<CasinoStatus> casinoSendMove(@RequestParam(value = "pwd") String pwd, @RequestParam(value = "winningSlot") int winningSlot) {
        if (!status.isStart()) {
            logger.error("wrongly send move by casino, game is not started yet ");
            return ResponseEntity.status(403).body(null);
        }
        if (!pwd.equals(pwdCasino)) {
            logger.error("wrongly send move by casino, wrong pwd ");
            return ResponseEntity.status(401).body(null);
        }
        if (!status.isCasinoTurn()) {
            logger.error("wrongly send move by casino, not casino's turn ");
            return ResponseEntity.status(401).body(null);
        }
        if (winningSlot > status.getTotalSlot()) {
            logger.error("wrongly send move by casino, invalid slot#, max# is:"+status.getTotalSlot()+", received slot# is:"+winningSlot);
            return ResponseEntity.status(403).body(null);
        }

        casinoTime += new Date().getTime() - casinoStartTime.getTime();
        logger.info("casino has used "+ casinoTime +" ms");
        if(casinoTime > timeLimits){
            logger.info("casino exceeds time limit, game over");
            status.setGameOver(true);
            return ResponseEntity.status(200).body(new CasinoStatus(status));
        }

        if (status.getCurrentRound() == 1) {
            logger.info("winning slot is now slot #"+winningSlot);
            status.setWinningSlot(winningSlot);
        } else {
            if (winningSlot != status.getWinningSlot()) {
                if (status.getSwitchLeft() > 0) {
                    status.setWinningSlot(winningSlot);
                    status.setSwitchLeft(status.getSwitchLeft() - 1);
                    logger.info("winning slot is now slot #"+winningSlot);
                } else {
                    logger.error("wrongly send move by casino, run out of switch times");
                    casinoStartTime = new Date();
                    return ResponseEntity.status(403).body(new CasinoStatus(status));
                }
            }
        }
        status.setCasinoTurn(false);
        gamblerStartTime = new Date();
        logger.info("waiting for gambler to send move");
        return ResponseEntity.status(200).body(new CasinoStatus(status));
    }
    //gambler send move
    //play on slot #{slot} and bet for {bet} dollars
    //stop the game if bet == 0
    @PostMapping("/gambler/sendMove")
    public ResponseEntity<GamblerStatus> gamblerSendMove(@RequestParam(value = "pwd") String pwd, @RequestParam(value = "slot") int slot, @RequestParam(value = "bet") int bet) {
        if (!status.isStart()) {
            logger.error("wrongly send move by gambler, game is not started yet");
            return ResponseEntity.status(403).body(null);
        }
        if (!pwd.equals(pwdGambler)) {
            logger.error("wrongly send move by gambler, wrong pwd");
            return ResponseEntity.status(401).body(null);
        }
        if (status.isCasinoTurn()) {
            logger.error("wrongly send move by gambler, not gambler's turn");
            return ResponseEntity.status(401).body(null);
        }
        if (slot > status.getTotalSlot() ) {
            logger.error("wrongly send move by gambler, slot # is not valid");
            return ResponseEntity.status(403).body(null);
        }
        if (bet < 0 || bet > 3){//must bet 1 to 3
            logger.error("wrongly send move by gambler, bet price is not valid");
            return ResponseEntity.status(403).body(null);
        }

        gamblerTime += new Date().getTime() - gamblerStartTime.getTime();
        logger.info("gambler has used "+ gamblerTime +" ms");
        if(casinoTime > timeLimits){
            logger.info("gambler exceeds time limit, game over");
            status.setGameOver(true);
            return ResponseEntity.status(200).body(new GamblerStatus(status));
        }

        if(bet == 0){
            logger.info("gambler stops playing the game, his deposit is "+ status.getDeposit());
            status.setCasinoTurn(true);
            status.setGameOver(true);
            return ResponseEntity.status(200).body(new GamblerStatus(status));
        }

        logger.info("gambler chooses slot #"+ slot + "and bets "+ bet+" dollar(s)");
        status.setChangedSlot(slot == status.getCurrentSlot());
        status.setCurrentSlot(slot);

        status.setDeposit(status.getDeposit() - bet);//pay the bet

        int dice = rand.nextInt() % 100;
        dice = dice < 0? dice + 100 : dice;
        boolean win = false;
        if(slot==status.getWinningSlot()){
            win = dice<status.getWinningSlotRate();
        }
        else{
            win = dice< status.getNormalSlotRate();
        }
        if(win){
            status.setDeposit(status.getDeposit()+ bet*2);
            logger.info("gambler wins round #"+status.getCurrentRound()+", deposit is now " + status.getDeposit());
        }
        else{
            logger.info("gambler looses round #"+status.getCurrentRound()+", deposit is now " + status.getDeposit());
        }
        if(status.getDeposit()<=0){
            logger.info("deposit is now 0, game over");
            status.setStart(false);
            status.setGameOver(true);
        }

        status.setCurrentRound(status.getCurrentRound() + 1);
        if(status.getCurrentRound()>status.getTotalSlot() * 100){
            logger.info("all rounds finished, game over");
            status.setStart(false);
            status.setGameOver(true);
        }
        status.setCasinoTurn(true);
        casinoStartTime = new Date();
        return ResponseEntity.status(200).body(new GamblerStatus(status));
    }
}
