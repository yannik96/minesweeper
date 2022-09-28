package minesweeper;

import it.unimi.dsi.fastutil.ints.IntIntImmutablePair;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Class representing a minesweeper field.
 **/
public abstract class Field extends JButton {

    protected FieldExposer fieldExposer;

    protected String imageFile;


    protected boolean revealed = false;
    protected boolean tagged = false;
    protected IntIntImmutablePair position;

    protected boolean isRunning = false;

    public Field(FieldExposer fieldExposer, IntIntImmutablePair position) {
        this.fieldExposer = fieldExposer;
        this.position = position;

        this.initializeListener();
    }

    private void initializeListener() {
        // add listener for mouse right clicks
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent event) {
                if (SwingUtilities.isRightMouseButton(event)) {
                    onRightClick((Field) event.getComponent());
                }
            }
        });
    }

    private void onRightClick(Field taggedField) {
        if (!this.isRunning) {
            return;
        }

        // if field is clickable, tag it with a flag; otherwise undo tagging with a flag because currently flagged
        if (taggedField.isEnabled()) {
            taggedField.setEnabled(false);
            ImageIcon flagIcon = new ImageIcon(getClass().getClassLoader().getResource("flag.png"));
            taggedField.setIcon(flagIcon);
            taggedField.setDisabledIcon(flagIcon);
            taggedField.setBackground(Color.WHITE);
            this.fieldExposer.tagField(taggedField);
            taggedField.changeTagged();
        } else if (taggedField.isTagged()) {
            taggedField.setIcon(new ImageIcon(""));
            taggedField.setDisabledIcon(new ImageIcon(""));
            taggedField.setEnabled(true);
            taggedField.setBackground(Color.GRAY);
            taggedField.changeTagged();
        }
    }

    /**
     * Increases the value of the field.
     **/
    public abstract void increaseValue();

    /**
     * Reveals the field.
     **/
    public abstract void reveal();

    /**
     * Returns if the field is empty.
     */
    public abstract boolean isEmpty();

    /**
     * Called if the field is being revealed. Display value.
     **/
    protected void revealButton() {
        setEnabled(false);
        ImageIcon valueIcon = imageFile == null ? new ImageIcon("") : new ImageIcon(getClass().getClassLoader().getResource(imageFile));
        setIcon(valueIcon);
        setDisabledIcon(valueIcon);
        setBackground(Color.WHITE);
    }

    public void changeTagged() {
        tagged = !tagged;
    }

    public boolean isTagged() {
        return tagged;
    }

    public boolean isRevealed() {
        return revealed;
    }

    public IntIntImmutablePair getPosition() {
        return position;
    }

    public void setRunning(boolean running) {
        this.isRunning = running;
    }

}
