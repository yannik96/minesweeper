package minesweeper;

import javax.swing.ImageIcon;

/** Class performing for the revelation of a field and potentially surrounding fields. **/
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
		// if field is not a mine (thus not lost), we might need to reveal surrounding fields
		if (revealedField instanceof Value)
			revealValue((Value) revealedField);
	}


	public void revealValue(Value revealedField) {
		int value = revealedField.getValue();
		// only reveal surrounding values if does not have a value
		if (value == 0)
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


	/** Reveals entire field if player has lost. **/
	public void revealEntireField() {
		for (int row = 0; row < numberOfRows; row++) {
			for (int column = 0; column < numberOfColumns; column++) {
				Field currentField = field[row][column];
				currentField.reveal();
				// field was tagged but is not a mine, thus a mistake
				if (currentField.isTagged() && !isMine(currentField))
					setIncorrectlyTagged(currentField);
			}
		}
	}


	public boolean isMine(Field field) {
		return (field instanceof Mine);
	}


	public void setIncorrectlyTagged(Field field) {
		// TODO: central resource manager
		ImageIcon noMineIcon = new ImageIcon(getClass().getClassLoader().getResource("nomine.png"));
		field.setIcon(noMineIcon);
		field.setDisabledIcon(noMineIcon);
	}

}
