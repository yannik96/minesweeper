package minesweeper;

import javax.swing.JButton;

/**
 * a class for modelling a minesweeper field
 * @author Yannik Klein
 */
public abstract class Field extends JButton {
	
	/*
	 * the playing field
	 */
	Field[][] field;
	
	/*
	 * true if the field has been revealed
	 */
	protected boolean revealed = false;
	
	/*
	 * the field's image path
	 */
	protected String image;
	
	/*
	 * the field's row and column
	 */
	protected int row;
	protected int column;
	
	public Field(int row, int column) {
		this.row = row;
		this.column = column;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getImage() {
		return image;
	}
	
	public void setField(Field[][] field) {
		this.field = field;
	}
	
	public boolean getRevealed() {
		return revealed;
	}
	
	public abstract void reveal();
	
}
