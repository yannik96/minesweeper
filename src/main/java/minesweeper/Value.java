package minesweeper;

import it.unimi.dsi.fastutil.ints.IntIntImmutablePair;

import javax.swing.*;

/** A class representing a value, i.e. fields that are not a mine. **/
public class Value extends Field {
	
	private int value = 0;

	public Value(final Minesweeper minesweeper, IntIntImmutablePair position) {
		super(minesweeper, position);

		addActionListener(e -> {
			this.minesweeper.revealField(((Field) e.getSource()));
			minesweeper.checkVictory();
		});
	}

	@Override
	public void reveal() {
		if (revealed || !minesweeper.isRunning()) {
			return;
		}

		revealed = true;

		if (this.isTagged()) {
			setIncorrectlyTagged();
		} else {
			revealButton();
		}

		this.minesweeper.revealField(this);
	}

	private void setIncorrectlyTagged() {
		// TODO: central resource manager
		ImageIcon noMineIcon = new ImageIcon(getClass().getClassLoader().getResource("nomine.png"));
		this.setIcon(noMineIcon);
		this.setDisabledIcon(noMineIcon);
	}

	@Override
	public void increaseValue() {
		value++;
		updateImageFile();
	}
	private void updateImageFile() {
		imageFile = value + ".png";
	}

	@Override
	public boolean isEmpty() {
		return value == 0;
	}

}
