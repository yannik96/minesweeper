package minesweeper;

import it.unimi.dsi.fastutil.ints.IntIntImmutablePair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * A class performing the search for a suitable starting position.
 **/
public class StartingPositionFinder {

    private Field[][] field;

    private Random generator = new Random();

    private ArrayList<IntIntImmutablePair> emptyFields = new ArrayList<>();

    public StartingPositionFinder(Field[][] field) {
        this.field = field;
    }

    public IntIntImmutablePair find() {
        findAllPossibleStartingPositions();
        return randomlySelectStartingPosition();
    }

    private void findAllPossibleStartingPositions() {
        Arrays.stream(field).forEach(row -> Arrays.stream(row).forEach(field -> {
            if (field.isEmpty()) {
                emptyFields.add(field.getPosition());
            }
        }));
    }

    private IntIntImmutablePair randomlySelectStartingPosition() {
        int indexOfStartingPosition = generator.nextInt(emptyFields.size());
        return emptyFields.get(indexOfStartingPosition);
    }

}
