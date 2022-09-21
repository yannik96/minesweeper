package minesweeper;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingUtilities;

/** Class representing a minesweeper field. **/
public abstract class Field extends JButton {
	
	protected Minesweeper minesweeper;

	protected String imageFile;


	protected boolean revealed = false;
	protected boolean tagged = false;
	protected Position position;


	public Field(Minesweeper minesweeper, Position position) {
		this.minesweeper = minesweeper;
		this.position = position;

		this.initializeListener();
	}

	private void initializeListener() {
		// add listener for mouse right clicks
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent event) {
				if (SwingUtilities.isRightMouseButton(event)) {
					onRightClick((Field) event.getComponent());
				}
			}
		});
	}


	private void onRightClick(Field taggedField) {
		if (!minesweeper.isRunning()) {
			return;
		}

		// if field is clickable, tag it with a flag; otherwise undo tagging with a flag because currently flagged
		if (taggedField.isEnabled()) {
			taggedField.setEnabled(false);
			ImageIcon flagIcon = new ImageIcon(getClass().getClassLoader().getResource("flag.png"));
			taggedField.setIcon(flagIcon);
			taggedField.setDisabledIcon(flagIcon);
			taggedField.setBackground(Color.WHITE);
			minesweeper.checkVictory();
			taggedField.changeTagged();
		} else if (taggedField.isTagged()){
			taggedField.setIcon(new ImageIcon(""));
			taggedField.setDisabledIcon(new ImageIcon(""));
			taggedField.setEnabled(true);
			taggedField.setBackground(Color.GRAY);
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
	
}
