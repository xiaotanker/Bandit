package edu.nyu.hps.bandit.client;

public class GamblerStatus {
    private boolean gameOver;
    private boolean start;
    private boolean gamblerTurn;
    private int deposit;
    private int currentSlot;
    private int currentRound;
    private int totalSlot;//s

    private int normalSlotRate;
    private int winningSlotRate;

    public GamblerStatus(boolean gameOver, boolean start, boolean gamblerTurn, int deposit, int currentSlot, int currentRound, int totalSlot, int normalSlotRate, int winningSlotRate) {
        this.gameOver = gameOver;
        this.start = start;
        this.gamblerTurn = gamblerTurn;
        this.deposit = deposit;
        this.currentSlot = currentSlot;
        this.currentRound = currentRound;
        this.totalSlot = totalSlot;
        this.normalSlotRate = normalSlotRate;
        this.winningSlotRate = winningSlotRate;
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

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public boolean isStart() {
        return start;
    }

    public void setStart(boolean start) {
        this.start = start;
    }

    public boolean isGamblerTurn() {
        return gamblerTurn;
    }

    public void setGamblerTurn(boolean gamblerTurn) {
        this.gamblerTurn = gamblerTurn;
    }

    public int getDeposit() {
        return deposit;
    }

    public void setDeposit(int deposit) {
        this.deposit = deposit;
    }

    public int getCurrentSlot() {
        return currentSlot;
    }

    public void setCurrentSlot(int currentSlot) {
        this.currentSlot = currentSlot;
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



}
