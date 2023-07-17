package minesweeper;

import it.unimi.dsi.fastutil.ints.IntIntImmutablePair;

public class FieldExposerImpl implements FieldExposer {

    private final VictoryChecker victoryChecker;
    private final BoundaryChecker boundaryChecker;

    private FieldRevealer[][] field;

    public FieldExposerImpl(VictoryChecker victoryChecker, BoundaryChecker boundaryChecker, FieldRevealer[][] field) {
        this.victoryChecker = victoryChecker;
        this.boundaryChecker = boundaryChecker;
        this.field = field;
    }

    @Override
    public void revealEntireField() {
        this.victoryChecker.setLost();

        for (FieldRevealer[] fieldRevealers : this.field) {
            for (FieldRevealer fieldRevealer : fieldRevealers) {
                fieldRevealer.reveal();
            }
        }
    }

    @Override
    public void revealedValue() {
        this.victoryChecker.checkVictory();
    }

    @Override
    public void taggedField() {
        this.victoryChecker.checkVictory();
    }

    public void revealSurroundingFields(IntIntImmutablePair position) {
        int revealedRow = position.leftInt();
        int revealedColumn = position.rightInt();

        for (int row = revealedRow - 1; row <= revealedRow + 1; row++) {
            for (int column = revealedColumn - 1; column <= revealedColumn + 1; column++) {
                if (this.boundaryChecker.isInBounds(new IntIntImmutablePair(row, column))) {
                    field[row][column].reveal();
                }
            }
        }
    }

    @Override
    public void setField(FieldRevealer[][] field) {
        this.field = field;
    }

}
