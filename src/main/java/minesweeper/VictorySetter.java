package minesweeper;

public interface VictorySetter {

    void setWon();

    void setLost();

    boolean shouldCheckVictory();

    Field[][] getField();

}
