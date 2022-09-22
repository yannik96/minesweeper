package minesweeper;

import it.unimi.dsi.fastutil.ints.IntIntImmutablePair;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/** Sets up a field, i.e. creates specified number of mines and surrounding field values. **/
public class FieldCreator {
	
	private Minesweeper minesweeper;
	
	private int numberOfRows;
	private int numberOfColumns;
	
	private int numberOfMines;
	
	private Field[][] field;
	private Set<IntIntImmutablePair> mines = new HashSet<>();

	private Random generator = new Random();

	public FieldCreator(Minesweeper minesweeper, int rows, int columns, int mines) {
		this.minesweeper = minesweeper;
		this.numberOfRows = rows;
		this.numberOfColumns = columns;
		this.numberOfMines = mines;
	}

	public Field[][] generate() {
		this.field = new Field[this.numberOfRows][this.numberOfColumns];

		generateMines();
		setField();
		calculateFieldValues();

		return field;
	}

	private void generateMines() {
		while (mines.size() < numberOfMines) {
			generateMine();
		}
	}

	private void generateMine() {
		IntIntImmutablePair position = new IntIntImmutablePair(generator.nextInt(numberOfRows), generator.nextInt(numberOfColumns));
		if (!isPositionAMine(position)) {
			mines.add(position);
		}
	}

	private boolean isPositionAMine(IntIntImmutablePair position) {
		return mines.contains(position);
	}

	/** Set field classes to be either mines or fields. **/
	private void setField() {
		for (int row = 0; row < numberOfRows; row++) {
			for (int column = 0; column < numberOfColumns; column++) {
				IntIntImmutablePair position = new IntIntImmutablePair(row, column);

				if (isPositionAMine(position)) {
					field[row][column] = new Mine(minesweeper, position);
				} else {
					field[row][column] = new Value(minesweeper, position);
				}
			}
		}
	}

	private void calculateFieldValues() {
		for (int row = 0; row < numberOfRows; row++) {
			for (int column = 0; column < numberOfColumns; column++) {
				IntIntImmutablePair position = new IntIntImmutablePair(row, column);

				if (isPositionAMine(position)) {
					increaseValueInSurroundingFields(position);
				}
			}	
		}
	}

	private void increaseValueInSurroundingFields(IntIntImmutablePair centerPosition) {
		BoundaryChecker boundaryChecker = new BoundaryChecker(numberOfRows, numberOfColumns);

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
