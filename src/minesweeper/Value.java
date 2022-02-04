package minesweeper;

/** A class representing a value, i.e. fields that are not a mine. **/
public class Value extends Field {
	
	private int value = 0;


	public Value(final Minesweeper minesweeper, final FieldRevealer fieldRevealer, Position position) {
		super(minesweeper, fieldRevealer, position);
		addActionListener(e -> {
			Field field = ((Field) e.getSource());
			fieldRevealer.reveal(field);
			minesweeper.checkVictory();
		});
	}


	@Override
	public void reveal() {
		if (revealed)
			return;
		if (!minesweeper.isRunning())
			return;
		revealed = true;
		revealButton();
		fieldRevealer.reveal(this);
	}


	public void increaseValue() {
		value++;
		updateImageFile();
	}
	
	public int getValue() {
		return value;
	}
	
	public void setValue(int value) {
		this.value = value;
		updateImageFile();
	}
	
	private void updateImageFile() {
		imageFile = value + ".png";
	}

}
