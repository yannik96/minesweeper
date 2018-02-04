package minesweeper;

import javax.swing.ImageIcon;

public class FieldRevealer {
	
	private Field[][] field;
	
	private int numberOfRows;
	private int numberOfColumns;
	
	public FieldRevealer(Field[][] field) {
		this.field = field;
		this.numberOfRows = field.length;
		this.numberOfColumns = field[0].length;
	}
	
	public void reveal(Field revealedField) {
		revealedField.reveal();
		if (revealedField instanceof Value)
			revealValue((Value) revealedField);
	}
	
	public void revealValue(Value revealedField) {
		int value = revealedField.getValue();
		if (value != 0)
			return;
		revealSurroundingFields(revealedField);
		
	}
	
	public void revealSurroundingFields(Field revealedField) {
		Position revealedPosition = revealedField.getPosition();
		for (int row = revealedPosition.row-1; row <= revealedPosition.row+1; row++) {
			for (int column = revealedPosition.column-1; column <= revealedPosition.column+1; column++) {
				Position position = new Position(row, column);
				if (isPositionInBoundary(position))
					revealField(field[row][column]);
			}	
		}
	}
	
	private void revealField(Field field) {
		if (field.isRevealed())
			return;
		if (field.isTagged())
			return;
		reveal(field);
	}
	
	private boolean isPositionInBoundary(Position position) {
		int row = position.row;
		int column = position.column;
		boolean isInRowBoundary = row >= 0 && row < numberOfRows;
		boolean isInColumnBoundary = column >= 0 && column < numberOfColumns;
		return isInRowBoundary && isInColumnBoundary;
	}	
	
	public void revealCompleteField() {
		for (int row = 0; row < numberOfRows; row++) {
			for (int column = 0; column < numberOfColumns; column++) {
				Field currentField = field[row][column];
				currentField.reveal();
				if (currentField.isTagged() && !isMine(currentField))
					setFalslyTagged(currentField);
			}
		}
	}
	
	public boolean isMine(Field field) {
		return (field instanceof Mine);
	}
	
	public void setFalslyTagged(Field field) {
		field.setIcon(new ImageIcon("Images/nomine.png"));
		field.setDisabledIcon(new ImageIcon("Images/nomine.png"));
	}

}
