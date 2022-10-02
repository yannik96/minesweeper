package minesweeper.ui;

import minesweeper.FieldExposer;
import minesweeper.Value;
import minesweeper.enums.FieldImage;

import javax.swing.*;

public class ValueButton extends FieldButton {

    private Value value;

    private ImageIcon imageIcon;

    public ValueButton(ImageManager imageManager, FieldExposer fieldExposer, Value value) {
        super(imageManager, fieldExposer, value);

        this.value = value;
        this.imageIcon = this.imageManager.getImage(this.getFieldImage(value.getValue()));
    }

    @Override
    public void reveal() {
        if (this.value.isNotRevealed()) {
            // TODO: not working yet
            if (this.value.isIncorrectlyTagged()) {
                this.setIncorrectlyTagged();
            }

            this.revealButton();
            this.value.reveal();
        }
    }

    private void setIncorrectlyTagged() {
        this.imageIcon = this.imageManager.getImage(FieldImage.NO_MINE);
    }

    @Override
    protected ImageIcon getImageIcon() {
        return this.imageIcon;
    }

    private FieldImage getFieldImage(int value) {
        switch (value) {
            case 1:
                return FieldImage.ONE;
            case 2:
                return FieldImage.TWO;
            case 3:
                return FieldImage.THREE;
            case 4:
                return FieldImage.FOUR;
            case 5:
                return FieldImage.FIVE;
            case 6:
                return FieldImage.SIX;
            default:
                return FieldImage.EMPTY;
        }
    }

}
