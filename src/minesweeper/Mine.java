package minesweeper;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 * a class for modelling a mine
 * @author Yannik Klein
 */
public class Mine extends Field {

	public Mine(int row, int column) {
		super(row, column);
		image = "mine.png";
	}

	@Override
	public void reveal() {
		revealed = true;
		
		this.setEnabled(false);
		this.setIcon(new ImageIcon("Images/" + getImage()));
		this.setDisabledIcon(new ImageIcon("Images/" + getImage()));
		this.setBackground(Color.WHITE);
	}

}
