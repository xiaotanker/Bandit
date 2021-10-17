package nyu.edu.hps;

public class CasinoStatus {
    private boolean gameOver ;
    private boolean start ;
    private boolean casinoTurn ;
    private int deposit;
    private int currentRound;

    private int totalSlot;//s

    private int switchLeft;//k

    private boolean changedSlot;
    private int winningSlot;

    private int normalSlotRate;
    private int winningSlotRate;
    public CasinoStatus(GameStatus status){
        this.gameOver = status.isGameOver();
        this.start = status.isStart();
        this.casinoTurn = status.isCasinoTurn();
        this.deposit = status.getDeposit();
        this.totalSlot = status.getTotalSlot();
        this.switchLeft = status.getSwitchLeft();
        this.changedSlot = status.isChangedSlot();
        this.winningSlot = status.getWinningSlot();
        this.normalSlotRate = status.getNormalSlotRate();
        this.winningSlotRate = status.getWinningSlotRate();
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

    public boolean isChangedSlot() {
        return changedSlot;
    }

    public void setChangedSlot(boolean changedSlot) {
        this.changedSlot = changedSlot;
    }

    public int getWinningSlot() {
        return winningSlot;
    }

    public void setWinningSlot(int winningSlot) {
        this.winningSlot = winningSlot;
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

}
