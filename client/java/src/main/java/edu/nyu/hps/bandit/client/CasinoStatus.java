package edu.nyu.hps.bandit.client;

public class CasinoStatus {
    private boolean gameOver;
    private boolean start;
    private boolean casinoTurn;
    private int deposit;
    private int currentRound;

    private int totalSlot;//s

    private int switchLeft;//k

    private int currentSlot;
    private int winningSlot;

    private int normalSlotRate;
    private int winningSlotRate;

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

    public CasinoStatus(boolean gameOver, boolean start, boolean casinoTurn, int deposit, int currentRound, int totalSlot, int switchLeft, int currentSlot, int winningSlot, int normalSlotRate, int winningSlotRate) {
        this.gameOver = gameOver;
        this.start = start;
        this.casinoTurn = casinoTurn;
        this.deposit = deposit;
        this.currentRound = currentRound;
        this.totalSlot = totalSlot;
        this.switchLeft = switchLeft;
        this.currentSlot = currentSlot;
        this.winningSlot = winningSlot;
        this.normalSlotRate = normalSlotRate;
        this.winningSlotRate = winningSlotRate;
    }
}
