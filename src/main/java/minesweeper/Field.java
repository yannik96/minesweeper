package minesweeper;

import it.unimi.dsi.fastutil.ints.IntIntImmutablePair;

public abstract class Field implements FieldRevealer {

    protected FieldExposer fieldExposer;

    protected IntIntImmutablePair position;

    protected boolean isRunning = false;
    protected boolean revealed = false;
    protected boolean tagged = false;


    protected Field(FieldExposer fieldExposer, IntIntImmutablePair position) {
        this.fieldExposer = fieldExposer;
        this.position = position;
    }

    public abstract void increaseValue();

    public abstract boolean isEmpty();

    public boolean isTagged() {
        return tagged;
    }

    public boolean isRevealed() {
        return revealed;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setTagged(boolean tagged) {
        this.tagged = tagged;
    }

    public IntIntImmutablePair getPosition() {
        return position;
    }

    public void setRunning(boolean running) {
        this.isRunning = running;
    }

    public boolean canBeRevealed() {
        return this.isRunning && !this.isRevealed() && !this.isTagged();
    }

    public boolean isNotRevealed() {
        return !this.isRevealed();
    }

}
