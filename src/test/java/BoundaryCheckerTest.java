import it.unimi.dsi.fastutil.ints.IntIntImmutablePair;
import minesweeper.BoundaryChecker;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BoundaryCheckerTest {

    @Test
    void testThrowsExceptionWhenGivenNegativeNumbers() {
        try {
            new BoundaryChecker(-1, -1);
        } catch (IllegalArgumentException e) {
            assertEquals("Number of rows and columns must be positive.", e.getMessage());
        }

        try {
            new BoundaryChecker(-3, -1);
        } catch (IllegalArgumentException e) {
            assertEquals("Number of rows and columns must be positive.", e.getMessage());
        }

        try {
            new BoundaryChecker(-3, -23);
        } catch (IllegalArgumentException e) {
            assertEquals("Number of rows and columns must be positive.", e.getMessage());
        }
    }

    @Test
    void testIsWithinBounds() {
        BoundaryChecker boundaryChecker = new BoundaryChecker(10, 10);

        assertTrue(boundaryChecker.isInBounds(new IntIntImmutablePair(5, 5)));
        assertTrue(boundaryChecker.isInBounds(new IntIntImmutablePair(0, 0)));
        assertTrue(boundaryChecker.isInBounds(new IntIntImmutablePair(0, 9)));
        assertTrue(boundaryChecker.isInBounds(new IntIntImmutablePair(9, 9)));
        assertTrue(boundaryChecker.isInBounds(new IntIntImmutablePair(9, 0)));
        assertTrue(boundaryChecker.isInBounds(new IntIntImmutablePair(1, 1)));
        assertTrue(boundaryChecker.isInBounds(new IntIntImmutablePair(8, 8)));
        assertTrue(boundaryChecker.isInBounds(new IntIntImmutablePair(1, 8)));
        assertTrue(boundaryChecker.isInBounds(new IntIntImmutablePair(4, 2)));

        assertFalse(boundaryChecker.isInBounds(new IntIntImmutablePair(10, 9)));
        assertFalse(boundaryChecker.isInBounds(new IntIntImmutablePair(11, 9)));
        assertFalse(boundaryChecker.isInBounds(new IntIntImmutablePair(9, 10)));
        assertFalse(boundaryChecker.isInBounds(new IntIntImmutablePair(9, 11)));
        assertFalse(boundaryChecker.isInBounds(new IntIntImmutablePair(10, 10)));
        assertFalse(boundaryChecker.isInBounds(new IntIntImmutablePair(1023, 923)));

        assertFalse(boundaryChecker.isInBounds(new IntIntImmutablePair(-1, 0)));
        assertFalse(boundaryChecker.isInBounds(new IntIntImmutablePair(-13, 0)));
        assertFalse(boundaryChecker.isInBounds(new IntIntImmutablePair(0, -1)));
        assertFalse(boundaryChecker.isInBounds(new IntIntImmutablePair(0, -23)));
        assertFalse(boundaryChecker.isInBounds(new IntIntImmutablePair(-343, -23)));
        assertFalse(boundaryChecker.isInBounds(new IntIntImmutablePair(-12, 34)));
        assertFalse(boundaryChecker.isInBounds(new IntIntImmutablePair(323, -34)));
    }
}