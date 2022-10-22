package minesweeper;

public class VictoryCheckerImpl implements VictoryChecker {

    private VictorySetter victorySetter;

    public VictoryCheckerImpl(VictorySetter victorySetter) {
        this.victorySetter = victorySetter;
    }

    @Override
    public void checkVictory() {
        if (this.victorySetter.shouldCheckVictory() && this.isGameWon()) {
            this.victorySetter.setWon();
        }
    }

    @Override
    public void setLost() {
        this.victorySetter.setLost();
    }

    /**
     * Checks whether the game is won.
     **/
    private boolean isGameWon() {
        for (Field[] fields : this.victorySetter.getField()) {
            for (Field field : fields) {
                // there exists a field that has not been clicked yet (right or left)
                if (!field.isRevealed() && !field.isTagged()) {
                    return false;
                }

                // there exists a field that is not a mine but has been flagged as such
                if (field instanceof Value && field.isTagged()) {
                    return false;
                }
            }
        }

        return true;
    }
}
