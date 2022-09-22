package minesweeper;

import it.unimi.dsi.fastutil.ints.IntIntImmutablePair;

public class Utils {

    public static boolean isPositionInBoundary(IntIntImmutablePair position, int numberOfRows, int numberOfColumns) {
        int row = position.leftInt();
        int column = position.rightInt();

        boolean isInRowBoundary = row >= 0 && row < numberOfRows;
        boolean isInColumnBoundary = column >= 0 && column < numberOfColumns;

        return isInRowBoundary && isInColumnBoundary;
    }
}
