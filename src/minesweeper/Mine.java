package minesweeper;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/** A class representing a mine. **/
public class Mine extends Field {


	public Mine(final Minesweeper minesweeper, final FieldRevealer fieldRevealer, Position position) {
		super(minesweeper, fieldRevealer, position);
		imageFile = "mine.png";
		addActionListener(new ActionListener() {
			  public void actionPerformed(ActionEvent e) {
				  Field field = ((Field) e.getSource());
				  onLeftClick(field); 
			  }
		 });
	}


	public void onLeftClick(Field clickedField) {
		fieldRevealer.reveal(clickedField);
		minesweeper.setLost();
	}


	@Override
	public void reveal() {
		if (!minesweeper.isRunning() || revealed)
			return;
		revealed = true;
		revealButton();
		fieldRevealer.revealEntireField();;
	}	

}
