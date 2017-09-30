package minesweeper;

import java.awt.Color;

import javax.swing.ImageIcon;

/**
 * a class for modelling a value field
 * @author Yannik Klein
 */
public class Value extends Field {
	
	/*
	 * the field's value
	 */
	private int value = 0;

	public Value(int row, int column) {
		super(row, column);
	}
	
	/**
	 * @return the field's value
 	 */
	public int getValue() {
		return value;
	}
	
	/**
	 * increases the field's value by one
	 */
	public void increaseValue() {
		value++;
		image = value + ".png";
	}
	
	/**
	 * sets the field's value and image
	 * @param value the field's value
	 */
	public void setValue(int value) {
		this.value = value;
		image = value + ".png";
	}

	@Override
	public void reveal() {
		if (!revealed) {
		revealed = true;
		
		this.setEnabled(false);
		this.setIcon(new ImageIcon("Images/" + getImage()));
		this.setDisabledIcon(new ImageIcon("Images/" + getImage()));
		this.setBackground(Color.WHITE);
		
		if (value == 0)
		for (int i = row-1; i <= row+1; i++) {
			for (int k = column-1; k <= column+1; k++) {
				if (i >= 0 && i < field.length && k >= 0 && k < field[i].length) {
					if (!field[i][k].getRevealed() && !(field[i][k].getToolTipText() == "flag")) field[i][k].reveal();
				}
			}	
		}
		}
	}

}
