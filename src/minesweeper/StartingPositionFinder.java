package minesweeper;

import java.util.ArrayList;
import java.util.Random;

/** A class performing the search for a suitable starting position. **/
public class StartingPositionFinder {
	
	private Field[][] field;
	private int numberOfRows;
	private int numberOfColumns;
	
	private ArrayList<Position> emptyFields = new ArrayList<Position>();


	public StartingPositionFinder(Field[][] field) {
		this.field = field;
		this.numberOfRows = field.length;
		this.numberOfColumns = field[0].length;
	}


	public Position find() {
		findAllPossibleStartingPositions();
		return randomlySelectStartingPosition();
	}


	private void findAllPossibleStartingPositions() {
		for (int row = 0; row < numberOfRows; row++)
			for (int column = 0; column < numberOfColumns; column++)
				if (isEmptyField(field[row][column]))
					emptyFields.add(new Position(row, column)); 
	}


	private boolean isEmptyField(Field field) {
		if (!(field instanceof Value))
			return false;
		Value valueField = (Value) field;
		int value = valueField.getValue();
		return (value == 0);
	}


	private Position randomlySelectStartingPosition() {
		Random generator = new Random();
		int amountOfEmptyFields = emptyFields.size();
		int indexOfStartingPosition = generator.nextInt(amountOfEmptyFields);
		return emptyFields.get(indexOfStartingPosition);
	}

}
