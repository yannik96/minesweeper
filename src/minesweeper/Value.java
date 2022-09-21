package minesweeper;

/** A class representing a value, i.e. fields that are not a mine. **/
public class Value extends Field {
	
	private int value = 0;

	public Value(final Minesweeper minesweeper,Position position) {
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
		revealButton();
		this.minesweeper.revealField(this);
	}

	public void increaseValue() {
		value++;
		updateImageFile();
	}
	private void updateImageFile() {
		imageFile = value + ".png";
	}

	public boolean isEmpty() {
		return value == 0;
	}
	
	public int getValue() {
		return value;
	}

}
