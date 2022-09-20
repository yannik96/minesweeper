package minesweeper;

/** A class representing a value, i.e. fields that are not a mine. **/
public class Value extends Field {
	
	private int value = 0;

	public Value(final Minesweeper minesweeper, final FieldRevealer fieldRevealer, Position position) {
		super(minesweeper, fieldRevealer, position);

		addActionListener(e -> {
			fieldRevealer.reveal(((Field) e.getSource()));
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
		fieldRevealer.reveal(this);
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
