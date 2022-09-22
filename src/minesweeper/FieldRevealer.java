package minesweeper;

import it.unimi.dsi.fastutil.ints.IntIntImmutablePair;

import javax.swing.ImageIcon;

/** Class performing the revelation of a field and potentially surrounding fields. **/
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
		// only reveal surrounding values if it does not have a value
		if (revealedField instanceof Value revealedValueField && revealedValueField.isEmpty()) {
			revealSurroundingFields(revealedValueField);
		}
	}

	private void revealSurroundingFields(Field revealedField) {
		IntIntImmutablePair revealedPosition = revealedField.getPosition();

		int revealedRow = revealedPosition.leftInt();
		int revealedColumn = revealedPosition.rightInt();

		for (int row = revealedRow-1; row <= revealedRow+1; row++) {
			for (int column = revealedColumn-1; column <= revealedColumn+1; column++) {
				if (Utils.isPositionInBoundary(new IntIntImmutablePair(row, column), numberOfRows, numberOfColumns)) {
					revealField(field[row][column]);
				}
			}	
		}
	}

	private void revealField(Field field) {
		if (canRevealField(field)) {
			reveal(field);
		}
	}

	private boolean canRevealField(Field field) {
		return !field.isRevealed() && !field.isTagged();
	}

	/** Reveals entire field if player has lost. **/
	public void revealEntireField() {
		for (int row = 0; row < numberOfRows; row++) {
			for (int column = 0; column < numberOfColumns; column++) {
				field[row][column].reveal();
			}
		}
	}

}
