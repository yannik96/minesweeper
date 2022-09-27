package minesweeper;

import it.unimi.dsi.fastutil.ints.IntIntImmutablePair;
import minesweeper.enums.Difficulty;

import javax.swing.*;
import java.awt.*;


/**
 * A class defining the application interface and basic game logic.
 **/
public class Minesweeper extends JFrame {

    /**
     * General difficulty settings.
     **/
    public static final int EASY_ROWS = 9;
    public static final int EASY_COLUMNS = 9;
    public static final int EASY_MINES = 10;

    public static final int ADVANCED_ROWS = 16;
    public static final int ADVANCED_COLUMNS = 16;
    public static final int ADVANCED_MINES = 40;

    public static final int EXPERT_ROWS = 16;
    public static final int EXPERT_COLUMNS = 32;
    public static final int EXPERT_MINES = 99;


    /**
     * Chosen difficulty settings.
     **/
    private Difficulty difficulty;

    private int numberOfRows;
    private int numberOfColumns;
    private int numberOfMines;

    private Field[][] field;

    public static int WIDTH;
    public static int HEIGHT;

    private FieldExposerImpl fieldRevealer;

    private boolean running;


    public Minesweeper() {
        super("Minesweeper");
        difficulty = Difficulty.EASY;
        initialize();
    }

    public Minesweeper(Difficulty difficulty) {
        super("Minesweeper");
        this.difficulty = difficulty;
        initialize();
    }

    private void initialize() {
        setDimension();
        initializeField();
        createWindow();
    }

    public void setDimension() {
        switch (difficulty) {
            case EASY:
                numberOfRows = EASY_ROWS;
                numberOfColumns = EASY_COLUMNS;
                numberOfMines = EASY_MINES;
                break;
            case ADVANCED:
                numberOfRows = ADVANCED_ROWS;
                numberOfColumns = ADVANCED_COLUMNS;
                numberOfMines = ADVANCED_MINES;
                break;
            case EXPERT:
                numberOfRows = EXPERT_ROWS;
                numberOfColumns = EXPERT_COLUMNS;
                numberOfMines = EXPERT_MINES;
                break;
        }
        WIDTH = 40 * numberOfColumns;
        HEIGHT = 40 * numberOfRows;
    }

    private void createWindow() {
        setSize(WIDTH, HEIGHT);
        Container pane = getContentPane();
        pane.setLayout(new GridLayout(numberOfRows, numberOfColumns));
        setLocationRelativeTo(null);
        createMenu();
        setVisible(true);
    }

    private void createMenu() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JMenuBar menuBar = new JMenuBar();
        JMenu menuNew = new JMenu("New Game");

        menuBar.add(menuNew);

        JMenuItem menuItemNewEasy = new JMenuItem("Easy");
        JMenuItem menuItemNewAdvanced = new JMenuItem("Advanced");
        JMenuItem menuItemNewExpert = new JMenuItem("Expert");
        menuNew.add(menuItemNewEasy);
        menuNew.add(menuItemNewAdvanced);
        menuNew.add(menuItemNewExpert);

        menuItemNewEasy.addActionListener(e -> newGame(Difficulty.EASY));
        menuItemNewAdvanced.addActionListener(e -> newGame(Difficulty.ADVANCED));
        menuItemNewExpert.addActionListener(e -> newGame(Difficulty.EXPERT));

        setJMenuBar(menuBar);
    }

    private void initializeField() {
        createField();
        this.setFieldsRunning(true);
        this.running = true;
        revealStartingPosition();
    }

    private void createField() {
        // TODO:
        this.fieldRevealer = new FieldExposerImpl(new VictoryCheckerImpl(this), new BoundaryChecker(numberOfRows, numberOfColumns), field);
        FieldCreator fieldCreator = new FieldCreator(this.fieldRevealer, numberOfRows, numberOfColumns, numberOfMines);

        field = fieldCreator.generate();
        for (int row = 0; row < numberOfRows; row++) {
            for (int column = 0; column < numberOfColumns; column++) {
                add(field[row][column]);
            }
        }

        this.fieldRevealer.setField(field);
    }

    private void revealStartingPosition() {
        StartingPositionFinder startingPositionFinder = new StartingPositionFinder(field);
        IntIntImmutablePair startingPosition = startingPositionFinder.find();
        Field startingField = getFieldByPosition(startingPosition);
        startingField.reveal();
    }

    private Field getFieldByPosition(IntIntImmutablePair position) {
        return field[position.leftInt()][position.rightInt()];
    }

    /**
     * Announce win and stop game
     **/
    public void setWon() {
        this.stopGame();
        JOptionPane.showMessageDialog(null, "Congratulations, you have won!", "Information", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Stops the game if it is lost.
     **/
    public void setLost() {
        this.stopGame();
        JOptionPane.showMessageDialog(null, "You have lost.", "Information", JOptionPane.INFORMATION_MESSAGE);
    }

    private void stopGame() {
        this.running = false;
        this.setFieldsRunning(false);
    }

    public void newGame(Difficulty difficulty) {
        new Minesweeper(difficulty);
        this.dispose();
    }

    private void setFieldsRunning(boolean running) {
        for (Field[] fields : field) {
            for (Field field : fields) {
                field.setRunning(running);
            }
        }
    }

    public Field[][] getField() {
        return field;
    }

}
