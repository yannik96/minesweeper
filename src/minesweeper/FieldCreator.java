package minesweeper;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class FieldCreator {
	
	private Minesweeper minesweeper;
	
	private int numberOfRows;
	private int numberOfColumns;
	
	private int numberOfMines;
	
	private Field[][] field;
	private Set<Position> mines = new HashSet<Position>();
	
	private FieldRevealer fieldRevealer;
	
	public FieldCreator(Minesweeper minesweeper, int rows, int columns, int mines) {
		this.minesweeper = minesweeper;
		this.numberOfRows = rows;
		this.numberOfColumns = columns;
		this.numberOfMines = mines;
		this.field = new Field[rows][columns];
		this.fieldRevealer = new FieldRevealer(field);
	}
	
	public Field[][] generate() {
		generateMines();
		setField();
		calculateFieldValues();
		return field;
	}
	
	public void generateMines() {
		while (mines.size() < numberOfMines)
			generateMine();
	}
	
	public void generateMine() {
		Random generator = new Random();
		int row = generator.nextInt(numberOfRows);
		int column = generator.nextInt(numberOfColumns);
		Position position = new Position(row, column);
		if (!isPositionAMine(position)) 
			mines.add(position);
	}
	
	public boolean isPositionAMine(Position position) {
		int row = position.row;
		int column = position.column;
		for (Position mine : mines) {
			int mineRow = mine.row;
			int mineColumn = mine.column;
			if (row == mineRow && column == mineColumn)
				return true;
		}
		return false;
	}
	
	public void setField() {
		for (int row = 0; row < numberOfRows; row++) {
			for (int column = 0; column < numberOfColumns; column++) {
				Position position = new Position(row, column);	
				if (isPositionAMine(position)) {
					field[row][column] = new Mine(minesweeper, fieldRevealer, position);
					continue;
				}
				field[row][column] = new Value(minesweeper, fieldRevealer, position);	
			}
		}
	}
	
	public void calculateFieldValues() {
		for (int row = 0; row < numberOfRows; row++) {
			for (int column = 0; column < numberOfColumns; column++) {
				Position position = new Position(row, column);
				if (isPositionAMine(position))
					increaseValueInSurroundingFields(position);
			}	
		}
	}
	
	public void increaseValueInSurroundingFields(Position centerPosition) {
		for (int row = centerPosition.row-1; row <= centerPosition.row+1; row++) {
			for (int column = centerPosition.column-1; column <= centerPosition.column+1; column++) {
				Position position = new Position(row, column);
				if (isPositionInBoundary(position))
					increaseValueOfField(field[row][column]);
			}	
		}
	}
	
	public boolean isPositionInBoundary(Position position) {
		int row = position.row;
		int column = position.column;
		boolean isInRowBoundary = row >= 0 && row < numberOfRows;
		boolean isInColumnBoundary = column >= 0 && column < numberOfColumns;
		return isInRowBoundary && isInColumnBoundary;
	}
	
	public void increaseValueOfField(Field field) {
		if (!(field instanceof Value))
			return;
		Value valueField = (Value) field;
		valueField.increaseValue();
	}
	
}
