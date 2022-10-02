package minesweeper;

import it.unimi.dsi.fastutil.ints.IntIntImmutablePair;


public interface FieldExposer {

    void revealSurroundingFields(IntIntImmutablePair position);

    void revealEntireField();

    void revealedValue();

    void taggedField();

    void setField(FieldRevealer[][] field);
}
