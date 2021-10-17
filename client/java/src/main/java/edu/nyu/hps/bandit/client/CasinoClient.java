package edu.nyu.hps.bandit.client;

import com.alibaba.fastjson.JSON;

import java.io.IOException;


public class CasinoClient {
    private String pwd = null;
    private final String clientName = "javaCasinoClient";
    private boolean joinGame(){
        try {
            this.pwd = Utils.sendPost("/casino/join?name=" + clientName);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    private CasinoStatus getStatus() {
        CasinoStatus status = null;
        try {
            String response = Utils.sendGet("/casino/status?pwd="+pwd);
            status = JSON.parseObject(response, CasinoStatus.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return status;
    }
    private CasinoStatus sendMove(int winningSlot){
        CasinoStatus status = null;
        try {
            String response = Utils.sendPost("/casino/sendMove?pwd="+pwd+"&winningSlot="+winningSlot);
            status = JSON.parseObject(response, CasinoStatus.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return status;
    }
    public void run(){
        joinGame();
        CasinoStatus status = getStatus();
        while(!status.isStart()){//wait for game to start
            status = getStatus();
        }

        status = sendMove(1);
        while(!status.isGameOver()){
            if(status.isCasinoTurn()){
                if(status.getSwitchLeft()>0) {
                    sendMove((status.getCurrentRound() - 1) % 100 + 1);
                }
                else{
                    sendMove(status.getWinningSlot());
                }
            }
            status = getStatus();
        }
    }

    public static void main(String[] args){
        CasinoClient casino = new CasinoClient();
        casino.run();
    }
}
