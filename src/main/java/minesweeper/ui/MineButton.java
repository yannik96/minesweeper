package minesweeper.ui;

import minesweeper.FieldExposer;
import minesweeper.Mine;
import minesweeper.enums.FieldImage;

import javax.swing.*;

public class MineButton extends FieldButton {

    private Mine mine;

    public MineButton(ImageManager imageManager, FieldExposer fieldExposer, Mine mine) {
        super(imageManager, fieldExposer, mine);

        this.mine = mine;
    }

    @Override
    public void reveal() {
        if (this.mine.isNotRevealed()) {
            this.revealButton();
            this.mine.reveal();
        }
    }

    @Override
    protected ImageIcon getImageIcon() {
        return this.imageManager.getImage(FieldImage.MINE);
    }

}
