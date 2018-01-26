package minesweeper;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingUtilities;

public abstract class Field extends JButton {
	
	protected Minesweeper minesweeper;
	
	private String imageFolder = "Images/";
	protected String imageFile;
	
	protected boolean revealed = false;
	protected boolean tagged = false;
	
	protected FieldRevealer fieldRevealer;
	protected Position position;
	
	public Field(Minesweeper minesweeper, FieldRevealer fieldRevealer, Position position) {
		this.minesweeper = minesweeper;
		this.fieldRevealer = fieldRevealer;
		this.position = position;
		addMouseListener(new MouseAdapter() {
			 public void mouseClicked(MouseEvent event) {
				 Field taggedField = (Field) event.getComponent();
				 if (SwingUtilities.isRightMouseButton(event) && event.getClickCount() == 1) 
					 onRightClick(taggedField);
			 }   	 
		 });
	}
	
	public void onRightClick(Field taggedField) {
		if (!minesweeper.isRunning())
			return;
		// in field auslagern
		if (taggedField.isEnabled() || taggedField.isTagged()) {
			if (taggedField.isEnabled()) {
				taggedField.setEnabled(false);
				taggedField.setIcon(new ImageIcon("Images/flag.png"));
				taggedField.setDisabledIcon(new ImageIcon("Images/flag.png"));
				taggedField.setBackground(Color.WHITE);
				minesweeper.checkVictory();
			} else {
				taggedField.setIcon(new ImageIcon(""));
				taggedField.setDisabledIcon(new ImageIcon(""));
				taggedField.setEnabled(true);
				taggedField.setBackground(Color.GRAY);
			}
			taggedField.changeTagged();
		} 
	}
	
	public abstract void reveal();
	
	protected void revealButton() {
		setEnabled(false);
		setIcon(new ImageIcon(imageFolder + imageFile));
		setDisabledIcon(new ImageIcon(imageFolder + imageFile));
		setBackground(Color.WHITE);
	}
	
	public void changeTagged() {
		tagged = !tagged;
	}
	
	public boolean isTagged() {
		return tagged;
	}
	
	public boolean isRevealed() {
		return revealed;
	}
	
	public Position getPosition() {
		return position;
	}
	
	public FieldRevealer getFieldRevealer() {
		return fieldRevealer;
	}
	
}
