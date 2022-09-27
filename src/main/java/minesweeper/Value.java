package minesweeper;

import it.unimi.dsi.fastutil.ints.IntIntImmutablePair;

import javax.swing.*;

/**
 * A class representing a value, i.e. fields that are not a mine.
 **/
public class Value extends Field {

    private int value = 0;

    public Value(FieldExposer fieldExposer, IntIntImmutablePair position) {
        super(fieldExposer, position);

        addActionListener(e -> fieldExposer.revealValue(this));
    }

    @Override
    public void reveal() {
        if (revealed || !this.isRunning) {
            return;
        }

        revealed = true;

        if (this.isTagged()) {
            setIncorrectlyTagged();
        } else {
            revealButton();
        }

        this.fieldExposer.revealValue(this);
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
