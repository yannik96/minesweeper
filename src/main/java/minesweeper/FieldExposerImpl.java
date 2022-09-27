package minesweeper;

import it.unimi.dsi.fastutil.ints.IntIntImmutablePair;

/**
 * Class performing the revelation of a field and potentially surrounding fields.
 **/
public class FieldExposerImpl implements FieldExposer {

    private VictoryChecker victoryChecker;
    private BoundaryChecker boundaryChecker;
    private Field[][] field;

    public FieldExposerImpl(VictoryChecker victoryChecker, BoundaryChecker boundaryChecker, Field[][] field) {
        this.victoryChecker = victoryChecker;
        this.boundaryChecker = boundaryChecker;
        this.field = field;
    }

    @Override
    public void revealEntireField() {
        for (Field[] fields : field) {
            for (Field field : fields) {
                field.reveal();
            }
        }
    }

    @Override
    public void revealValue(Value field) {
        field.reveal();

        if (field.isEmpty()) {
            revealSurroundingFields(field);
        }

        this.victoryChecker.checkVictory();
    }

    @Override
    public void revealMine(Mine field) {
        if (canRevealField(field)) {
            field.reveal();
        }

        this.victoryChecker.setLost();
    }

    @Override
    public void tagField(Field field) {
        this.victoryChecker.checkVictory();
    }

    @Override
    public void setField(Field[][] field) {
        this.field = field;
    }

    private void revealSurroundingFields(Field revealedField) {
        IntIntImmutablePair revealedPosition = revealedField.getPosition();

        int revealedRow = revealedPosition.leftInt();
        int revealedColumn = revealedPosition.rightInt();

        for (int row = revealedRow - 1; row <= revealedRow + 1; row++) {
            for (int column = revealedColumn - 1; column <= revealedColumn + 1; column++) {
                if (this.boundaryChecker.isInBounds(new IntIntImmutablePair(row, column))) {
                    field[row][column].reveal();
                }
            }
        }
    }

    private boolean canRevealField(Field field) {
        return !field.isRevealed() && !field.isTagged();
    }

}
