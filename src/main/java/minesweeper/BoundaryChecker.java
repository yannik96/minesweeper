package minesweeper;

import it.unimi.dsi.fastutil.ints.IntIntImmutablePair;

public class BoundaryChecker {

    private final int numberOfRows;
    private final int numberOfColumns;

    public BoundaryChecker(int numberOfRows, int numberOfColumns) {
        this.numberOfRows = numberOfRows;
        this.numberOfColumns = numberOfColumns;
    }

    public boolean isInBounds(IntIntImmutablePair position) {
        int row = position.leftInt();
        int column = position.rightInt();

        boolean isInRowBoundary = row >= 0 && row < this.numberOfRows;
        boolean isInColumnBoundary = column >= 0 && column < this.numberOfColumns;

        return isInRowBoundary && isInColumnBoundary;
    }
}
