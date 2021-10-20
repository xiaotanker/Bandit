package edu.nyu.hps;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@ConfigurationProperties("bandit")
public class GameStatus {
    private boolean gameOver = false;
    private boolean start = false;
    private boolean casinoTurn = true;
    private int deposit;
    private int currentRound = 1;

    private int totalSlot;//s
    private int maxSwitch;//k
    private int switchLeft;

    private int currentSlot;
    private int winningSlot;
    private boolean changedSlot;
    private int normalSlotRate;
    private int winningSlotRate;

    public int getMaxSwitch() {
        return maxSwitch;
    }

    public void setMaxSwitch(int maxSwitch) {
        this.maxSwitch = maxSwitch;
    }

    public boolean isChangedSlot() {
        return changedSlot;
    }

    public void setChangedSlot(boolean changedSlot) {
        this.changedSlot = changedSlot;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public boolean isStart() {
        return start;
    }

    public int getNormalSlotRate() {
        return normalSlotRate;
    }

    public void setNormalSlotRate(int normalSlotRate) {
        this.normalSlotRate = normalSlotRate;
    }

    public int getWinningSlotRate() {
        return winningSlotRate;
    }

    public void setWinningSlotRate(int winningSlotRate) {
        this.winningSlotRate = winningSlotRate;
    }

    public int getCurrentSlot() {
        return currentSlot;
    }

    public void setCurrentSlot(int currentSlot) {
        this.currentSlot = currentSlot;
    }

    public int getWinningSlot() {
        return winningSlot;
    }

    public void setWinningSlot(int winningSlot) {
        this.winningSlot = winningSlot;
    }

    public void setStart(boolean start) {
        this.start = start;
    }

    public boolean isCasinoTurn() {
        return casinoTurn;
    }

    public void setCasinoTurn(boolean casinoTurn) {
        this.casinoTurn = casinoTurn;
    }

    public int getDeposit() {
        return deposit;
    }

    public void setDeposit(int deposit) {
        this.deposit = deposit;
    }

    public int getCurrentRound() {
        return currentRound;
    }

    public void setCurrentRound(int currentRound) {
        this.currentRound = currentRound;
    }

    public int getTotalSlot() {
        return totalSlot;
    }

    public void setTotalSlot(int totalSlot) {
        this.totalSlot = totalSlot;
    }

    public int getSwitchLeft() {
        return switchLeft;
    }

    public void setSwitchLeft(int switchLeft) {
        this.switchLeft = switchLeft;
    }
}
