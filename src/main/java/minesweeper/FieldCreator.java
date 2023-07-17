package minesweeper;

import it.unimi.dsi.fastutil.ints.IntIntImmutablePair;
import minesweeper.enums.FieldConfig;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Sets up a field, i.e. creates specified number of mines and surrounding field values.
 **/
public class FieldCreator {

    private final FieldExposer fieldExposer;

    private final FieldConfig fieldConfig;
    private Field[][] field;
    private final Set<IntIntImmutablePair> mines = new HashSet<>();

    private final Random generator = new Random();

    public FieldCreator(FieldExposer fieldExposer, FieldConfig fieldConfig) {
        this.fieldExposer = fieldExposer;
        this.fieldConfig = fieldConfig;
    }

    public Field[][] generate() {
        this.field = new Field[fieldConfig.numberRows()][fieldConfig.numberColumns()];

        generateMines();
        setField();
        calculateFieldValues();

        return field;
    }

    private void generateMines() {
        while (mines.size() < fieldConfig.numberMines()) {
            generateMine();
        }
    }

    private void generateMine() {
        IntIntImmutablePair position = new IntIntImmutablePair(generator.nextInt(fieldConfig.numberRows()), generator.nextInt(fieldConfig.numberColumns()));
        if (!isPositionAMine(position)) {
            mines.add(position);
        }
    }

    private boolean isPositionAMine(IntIntImmutablePair position) {
        return mines.contains(position);
    }

    /**
     * Set field classes to be either mines or fields.
     **/
    private void setField() {
        for (int row = 0; row < fieldConfig.numberRows(); row++) {
            for (int column = 0; column < fieldConfig.numberColumns(); column++) {
                IntIntImmutablePair position = new IntIntImmutablePair(row, column);

                if (isPositionAMine(position)) {
                    field[row][column] = new Mine(fieldExposer, position);
                } else {
                    field[row][column] = new Value(fieldExposer, position);
                }
            }
        }
    }

    private void calculateFieldValues() {
        for (int row = 0; row < fieldConfig.numberRows(); row++) {
            for (int column = 0; column < fieldConfig.numberColumns(); column++) {
                IntIntImmutablePair position = new IntIntImmutablePair(row, column);

                if (isPositionAMine(position)) {
                    increaseValueInSurroundingFields(position);
                }
            }
        }
    }

    private void increaseValueInSurroundingFields(IntIntImmutablePair centerPosition) {
        BoundaryChecker boundaryChecker = new BoundaryChecker(fieldConfig.numberRows(), fieldConfig.numberColumns());

        int centerRow = centerPosition.leftInt();
        int centerColumn = centerPosition.rightInt();

        for (int row = centerRow - 1; row <= centerRow + 1; row++) {
            for (int column = centerColumn - 1; column <= centerColumn + 1; column++) {
                if (boundaryChecker.isInBounds(new IntIntImmutablePair(row, column))) {
                    this.field[row][column].increaseValue();
                }
            }
        }
    }

}
