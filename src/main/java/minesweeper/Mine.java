package minesweeper;

import it.unimi.dsi.fastutil.ints.IntIntImmutablePair;

/**
 * A class representing a mine.
 **/
public class Mine extends Field {

    public Mine(FieldExposer fieldExposer, IntIntImmutablePair position) {
        super(fieldExposer, position);
    }

    @Override
    public void reveal() {
        if (this.canBeRevealed()) {
            this.revealed = true;
            this.fieldExposer.revealEntireField();
        }
    }

    @Override
    public void increaseValue() {
        // do nothing
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

}
