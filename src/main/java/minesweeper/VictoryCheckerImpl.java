package minesweeper;

public class VictoryCheckerImpl implements VictoryChecker {

    private Minesweeper minesweeper;

    public VictoryCheckerImpl(Minesweeper minesweeper) {
        this.minesweeper = minesweeper;
    }

    @Override
    public void checkVictory() {
        if (this.isGameWon()) {
            this.minesweeper.setWon();
        }
    }

    @Override
    public void setLost() {
        this.minesweeper.setLost();
    }

    /**
     * Checks whether the game is won.
     **/
    private boolean isGameWon() {
        for (Field[] fields : this.minesweeper.getField()) {
            for (Field field : fields) {
                // there exists a field that has not been clicked yet (right or left)
                if (field.isEnabled()) {
                    return false;
                }

                // there exists a field that is not a mine but has been flagged as such
                if (!(field instanceof Mine) && field.getToolTipText() == "flag") {
                    return false;
                }
            }
        }

        return true;
    }
}
