package minesweeper;

import it.unimi.dsi.fastutil.ints.IntIntImmutablePair;

/**
 * A class representing a mine.
 **/
public class Mine extends Field {

    public Mine(FieldExposer fieldExposer, IntIntImmutablePair position) {
        super(fieldExposer, position);

        this.imageFile = "mine.png";
        addActionListener(e -> fieldExposer.revealMine(this));
    }

    @Override
    public void reveal() {
        if (!this.isRunning || this.revealed) {
            return;
        }

        this.revealed = true;
        this.revealButton();
        this.fieldExposer.revealEntireField();
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
