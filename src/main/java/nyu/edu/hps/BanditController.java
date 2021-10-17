package nyu.edu.hps;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Random;
import java.util.UUID;

@RestController
public class BanditController {
    private String gambler = null;
    private String casino = null;
    private String pwdGambler = null;
    private String pwdCasino = null;
    private Random rand = new Random();

    @Autowired
    private GameStatus status;


    Logger logger = LoggerFactory.getLogger(BanditController.class);

    @PostMapping("/casino/join")
    public ResponseEntity<String> joinCasino(@RequestParam(value = "name", defaultValue = "casino") String name) {
        if (casino == null) {
            casino = name;
            pwdCasino = UUID.randomUUID().toString();
        } else {
            return ResponseEntity.status(403).body("already has a casino, can't join as casino\n");
        }
        if (casino != null && gambler != null) {
            status.setCasinoTurn(true);
            status.setStart(true);
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
        if (casino != null && gambler != null) {
            status.setCasinoTurn(true);
            status.setStart(true);
        }
        return ResponseEntity.status(200).body(pwdCasino);
    }

    @GetMapping("/casino/status")
    public ResponseEntity<GameStatus> getCasinoStatus(@RequestParam(value = "pwd") String pwd) {
        if (!pwd.equals(pwdCasino)) {
            return ResponseEntity.status(401).body(null);
        }
        return ResponseEntity.status(200).body(status);
    }

    @GetMapping("/gambler/status")
    public ResponseEntity<GamblerStatus> getGamblerStatus(@RequestParam(value = "pwd") String pwd) {
        if (!pwd.equals(pwdGambler)) {
            return ResponseEntity.status(401).body(null);
        }
        return ResponseEntity.status(200).body(new GamblerStatus(status));
    }

    @PostMapping("/casino/sendMove")
    public ResponseEntity<GameStatus> casinoSendMove(@RequestParam(value = "pwd") String pwd, @RequestParam(value = "winningSlot") int winningSlot) {
        if (!status.isStart()) {
            return ResponseEntity.status(403).body(null);
        }
        if (!pwd.equals(pwdCasino)) {
            return ResponseEntity.status(401).body(null);
        }
        if (!status.isCasinoTurn()) {
            return ResponseEntity.status(401).body(null);
        }
        if (winningSlot > status.getTotalSlot()) {
            return ResponseEntity.status(403).body(null);
        }

        if (status.getCurrentRound() == 1) {
            status.setWinningSlot(winningSlot);
        } else {
            if (winningSlot != status.getWinningSlot()) {
                if (status.getSwitchLeft() > 0) {
                    status.setWinningSlot(winningSlot);
                    status.setSwitchLeft(status.getSwitchLeft() - 1);
                } else {
                    return ResponseEntity.status(403).body(null);
                }
            }
        }
        status.setCasinoTurn(false);
        return ResponseEntity.status(200).body(status);
    }

    @PostMapping("/gambler/sendMove")
    public ResponseEntity<GamblerStatus> gamblerSendMove(@RequestParam(value = "pwd") String pwd, @RequestParam(value = "slot") int slot, @RequestParam(value = "bet") int bet) {
        if (!status.isStart()) {
            return ResponseEntity.status(403).body(null);
        }
        if (!pwd.equals(pwdCasino)) {
            return ResponseEntity.status(401).body(null);
        }
        if (!status.isCasinoTurn()) {
            return ResponseEntity.status(401).body(null);
        }
        if (slot > status.getTotalSlot() ) {
            return ResponseEntity.status(403).body(null);
        }
        if (bet < 1 || bet > 3){//must bet 1 to 3
            return ResponseEntity.status(403).body(null);
        }
        status.setCurrentSlot(slot);

        status.setDeposit(status.getDeposit() - bet);

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
        }
        status.setCurrentRound(status.getCurrentRound() + 1);
        status.setCasinoTurn(true);
        if(status.getDeposit()<=0||status.getCurrentRound()>status.getTotalSlot() * 100){
            status.setStart(false);
        }
        return ResponseEntity.status(200).body(new GamblerStatus(status));
    }
}
