package minesweeper;

/** A class representing a mine. **/
public class Mine extends Field {

	public Mine(final Minesweeper minesweeper, Position position) {
		super(minesweeper, position);

		this.imageFile = "mine.png";
		addActionListener(e -> onLeftClick(((Field) e.getSource())));
	}

	public void onLeftClick(Field clickedField) {
		this.minesweeper.revealField(clickedField);
		this.minesweeper.setLost();
	}

	@Override
	public void reveal() {
		if (!this.minesweeper.isRunning() || this.revealed) {
			return;
		}

		this.revealed = true;
		this.revealButton();
		this.minesweeper.revealEntireField();;
	}	

}
