package minesweeper;

import it.unimi.dsi.fastutil.ints.IntIntImmutablePair;


public class Value extends Field {

    private int value = 0;

    public Value(FieldExposer fieldExposer, IntIntImmutablePair position) {
        super(fieldExposer, position);
    }

    @Override
    public void reveal() {
        if (this.canBeRevealed()) {
            revealed = true;
            this.fieldExposer.revealedValue();

            if (this.isEmpty()) {
                this.fieldExposer.revealSurroundingFields(this.position);
            }
        }
    }

    public boolean isIncorrectlyTagged() {
        return isTagged();
    }

    @Override
    public void increaseValue() {
        value++;
    }

    @Override
    public boolean isEmpty() {
        return value == 0;
    }

    public int getValue() {
        return value;
    }

}
