package minesweeper.ui;

import minesweeper.Field;
import minesweeper.FieldExposer;
import minesweeper.FieldRevealer;
import minesweeper.enums.FieldImage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public abstract class FieldButton extends JButton implements FieldRevealer {

    protected ImageManager imageManager;

    private FieldExposer fieldExposer;

    protected Field field;

    protected boolean tagged = false;

    public FieldButton(ImageManager imageManager, FieldExposer fieldExposer, Field field) {
        this.imageManager = imageManager;
        this.fieldExposer = fieldExposer;
        this.field = field;

        this.initializeListener();
    }

    private void initializeListener() {
        addActionListener(e -> this.reveal());

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent event) {
                if (SwingUtilities.isRightMouseButton(event)) {
                    onRightClick();
                }
            }
        });
    }

    private void onRightClick() {
        if (!this.field.isRunning()) {
            return;
        }

        if (this.isEnabled()) {
            this.tagField();
        } else if (this.isTagged()) {
            this.untagField();
        }
    }

    public void tagField() {
        this.setIcon(this.imageManager.getImage(FieldImage.FLAG));
        this.setEnabled(false);
        this.setBackground(Color.WHITE);
        this.setTagged(true);

        this.fieldExposer.taggedField();
        this.field.setTagged(true);
    }

    public void untagField() {
        this.setIcon(this.imageManager.getImage(FieldImage.EMPTY));
        this.setEnabled(true);
        this.setBackground(Color.GRAY);
        this.setTagged(false);

        this.field.setTagged(false);
    }

    private void setImageIcon(ImageIcon icon) {
        this.setIcon(icon);
        this.setDisabledIcon(icon);
    }

    public void revealButton() {
        this.setImageIcon(this.getImageIcon());
        setEnabled(false);
        setBackground(Color.WHITE);
    }

    protected abstract ImageIcon getImageIcon();

    public abstract void reveal();

    public void setTagged(boolean tagged) {
        this.tagged = tagged;
    }

    public boolean isTagged() {
        return tagged;
    }

}
