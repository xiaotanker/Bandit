package edu.nyu.hps.bandit.client;

import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.util.Random;

public class GamblerClient {
    private String pwd = null;
    private final String clientName = "javaGamblerClient";
    private boolean joinGame(){
        try {
            this.pwd = Utils.sendPost("/gambler/join?name=" + clientName);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    private GamblerStatus getStatus() {
        GamblerStatus status = null;
        try {
            String response = Utils.sendGet("/gambler/status?pwd="+pwd);
            status = JSON.parseObject(response, GamblerStatus.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return status;
    }
    private GamblerStatus sendMove(int slot,int bet){
        GamblerStatus status = null;
        try {
            String response = Utils.sendPost("/gambler/sendMove?pwd="+pwd+"&slot="+slot+"&bet="+bet);
            status = JSON.parseObject(response, GamblerStatus.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return status;
    }

    public void run(){
        joinGame();
        Random random = new Random();
        GamblerStatus status = getStatus();
        while(!status.isStart()){//wait for game to start
            status = getStatus();
        }

        while(!status.isGameOver()){
            if(status.isGamblerTurn()){
                int x = random.nextInt()%status.getTotalSlot();
                if(x<0){
                    x+=status.getTotalSlot();
                }
                x+=1;

                sendMove(x,Math.min(3,status.getDeposit()));
            }
            status = getStatus();
        }
    }

    public static void main(String[] args){
        GamblerClient gambler = new GamblerClient();
        gambler.run();
    }
}
