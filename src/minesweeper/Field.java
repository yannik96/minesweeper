package minesweeper;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingUtilities;

/** Class representing a minesweeper field. **/
public abstract class Field extends JButton {
	
	protected Minesweeper minesweeper;

	private String imageFolder = "images/";
	protected String imageFile;

	protected boolean revealed = false;
	protected boolean tagged = false;
	
	protected FieldRevealer fieldRevealer;
	protected Position position;


	public Field(Minesweeper minesweeper, FieldRevealer fieldRevealer, Position position) {
		this.minesweeper = minesweeper;
		this.fieldRevealer = fieldRevealer;
		this.position = position;

		// add listener for mouse clicks
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
		// field can be clicked or is tagged with a flag
		if (taggedField.isEnabled() || taggedField.isTagged()) {
			// if field is clickable, tag it with a flag
			if (taggedField.isEnabled()) {
				taggedField.setEnabled(false);
				ImageIcon flagIcon = new ImageIcon(getClass().getClassLoader().getResource("flag.png"));
				taggedField.setIcon(flagIcon);
				taggedField.setDisabledIcon(flagIcon);
				taggedField.setBackground(Color.WHITE);
				minesweeper.checkVictory();
			// undo tagging with a flag because currently flagged
			} else {
				taggedField.setIcon(new ImageIcon(""));
				taggedField.setDisabledIcon(new ImageIcon(""));
				taggedField.setEnabled(true);
				taggedField.setBackground(Color.GRAY);
			}
			taggedField.changeTagged();
		} 
	}


	/** Different semantics depending on type of field, e.g. Mine or Field. **/
	public abstract void reveal();


	/** Called if the field is being revealed. Display value. **/
	protected void revealButton() {
		setEnabled(false);
		ImageIcon valueIcon = imageFile == null? new ImageIcon("") : new ImageIcon(getClass().getClassLoader().getResource(imageFile));
		setIcon(valueIcon);
		setDisabledIcon(valueIcon);
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
