package minesweeper;

import minesweeper.enums.Difficulty;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;


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


    private Difficulty difficulty;

    private int numberOfRows;
    private int numberOfColumns;
    private int numberOfMines;

    private Field[][] field;

    public static int WIDTH;
    public static int HEIGHT;

    private FieldExposerImpl fieldExposer;

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

        StartingPositionFinder startingPositionFinder = new StartingPositionFinder(field);
        startingPositionFinder.revealStartingPosition();
    }

    private void createField() {
        // TODO:
        this.fieldExposer = new FieldExposerImpl(new VictoryCheckerImpl(this), new BoundaryChecker(numberOfRows, numberOfColumns), field);
        FieldCreator fieldCreator = new FieldCreator(this.fieldExposer, numberOfRows, numberOfColumns, numberOfMines);

        field = fieldCreator.generate();
        Arrays.stream(field).forEach(fields -> Arrays.stream(fields).forEach(field -> add(field)));

        this.fieldExposer.setField(field);
    }

    /**
     * Announces win and stops game.
     **/
    public void setWon() {
        this.stopGame();
        JOptionPane.showMessageDialog(null, "Congratulations, you have won!", "Information", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Announces loss and stops game.
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
        Arrays.stream(field).forEach(fields -> Arrays.stream(fields).forEach(field -> field.setRunning(running)));
    }

    public Field[][] getField() {
        return field;
    }

    public boolean isRunning() {
        return running;
    }

}
