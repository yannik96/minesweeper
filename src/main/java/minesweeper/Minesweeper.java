package minesweeper;

import it.unimi.dsi.fastutil.ints.IntIntImmutablePair;
import minesweeper.enums.Difficulty;
import minesweeper.enums.FieldConfig;
import minesweeper.ui.FieldButton;
import minesweeper.ui.ImageManager;
import minesweeper.ui.MineButton;
import minesweeper.ui.ValueButton;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;


/**
 * A class defining the application interface and basic game logic.
 **/
public class Minesweeper extends JFrame implements VictorySetter {

    private final FieldConfig fieldConfig;

    private Field[][] field;
    private FieldRevealer[][] fieldRevealers;

    public static int WIDTH;
    public static int HEIGHT;

    private boolean running;


    public Minesweeper() {
        super("Minesweeper");
        this.fieldConfig = FieldConfig.getEasyConfig();
        initialize();
    }

    public Minesweeper(Difficulty difficulty) {
        super("Minesweeper");
        this.fieldConfig = FieldConfig.getConfig(difficulty);
        initialize();
    }

    private void initialize() {
        setDimension();
        initializeField();
        createWindow();
    }

    public void setDimension() {
        WIDTH = 40 * fieldConfig.numberColumns();
        HEIGHT = 40 * fieldConfig.numberRows();
    }

    private void createWindow() {
        setSize(WIDTH, HEIGHT);
        Container pane = getContentPane();
        pane.setLayout(new GridLayout(fieldConfig.numberRows(), fieldConfig.numberColumns()));
        setLocationRelativeTo(null);
        createMenu();
        setVisible(true);
    }

    private void createMenu() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

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
        IntIntImmutablePair startingPosition = startingPositionFinder.find();
        fieldRevealers[startingPosition.leftInt()][startingPosition.rightInt()].reveal();
    }

    private void createField() {
        FieldExposerImpl fieldExposer;
        ImageManager imageManager = new ImageManager();
        // TODO:
        fieldExposer = new FieldExposerImpl(new VictoryCheckerImpl(this), new BoundaryChecker(fieldConfig.numberRows(), fieldConfig.numberColumns()), field);
        FieldCreator fieldCreator = new FieldCreator(fieldExposer, this.fieldConfig);

        field = fieldCreator.generate();

        FieldRevealer[][] fieldRevealers = new FieldRevealer[fieldConfig.numberRows()][fieldConfig.numberColumns()];

        // create button field
        Arrays.stream(field).forEach(fields -> Arrays.stream(fields).forEach(field -> {
            FieldButton fieldButton = field instanceof Mine ? new MineButton(imageManager, fieldExposer, (Mine) field) : new ValueButton(imageManager, fieldExposer, (Value) field);
            add(fieldButton);
            fieldRevealers[field.position.left()][field.position.rightInt()] = fieldButton;
        }));

        this.fieldRevealers = fieldRevealers;
        fieldExposer.setField(fieldRevealers);
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

    @Override
    public boolean shouldCheckVictory() {
        return this.isRunning();
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
