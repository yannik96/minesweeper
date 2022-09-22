package minesweeper;

import java.awt.Container;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import it.unimi.dsi.fastutil.ints.IntIntImmutablePair;
import minesweeper.enums.Difficulty;

/** A class defining the application interface and basic game logic. **/
public class Minesweeper extends JFrame {

	/** General difficulty settings. **/
	public static final int EASY_ROWS = 9;
	public static final int EASY_COLUMNS = 9;
	public static final int EASY_MINES = 10;
	
	public static final int ADVANCED_ROWS = 16;
	public static final int ADVANCED_COLUMNS = 16;
	public static final int ADVANCED_MINES = 40;
	
	public static final int EXPERT_ROWS = 16;
	public static final int EXPERT_COLUMNS = 32;
	public static final int EXPERT_MINES = 99;


	/** Chosen difficulty settings. **/
	private Difficulty difficulty;
	
	private int numberOfRows;
	private int numberOfColumns;
	private int numberOfMines;

	private Field[][] field;

	public static int WIDTH;
	public static int HEIGHT;

	private FieldRevealer fieldRevealer;

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
		switch(difficulty) {
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
		running = true;
		revealStartingPosition();
	}

	private void createField() {
		FieldCreator fieldCreator = new FieldCreator(this, numberOfRows, numberOfColumns, numberOfMines);

		field = fieldCreator.generate();
		for (int row = 0; row < numberOfRows; row++) {
			for (int column = 0; column < numberOfColumns; column++) {
				add(field[row][column]);
			}
		}

		this.fieldRevealer = new FieldRevealer(field);
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

	public void checkVictory() {
		if (this.isGameWon()) {
			setWon();
		}
	}

	/** Checks whether the game is won. **/
	public boolean isGameWon() {
		for (int i = 0; i < numberOfRows; i++) {
			for (int e = 0; e < numberOfColumns; e++) {
				// there exists a field that has not been clicked yet (right or left)
				if (field[i][e].isEnabled()) {
					return false;
				}

				// there exists a field that is not a mine but has been flagged as such
				if (!(field[i][e] instanceof Mine) && field[i][e].getToolTipText() == "flag") {
					return false;
				}
			}
		}

		return true;
	}

	/** Announce win and stop game **/
	public void setWon() {
		running = false;
		JOptionPane.showMessageDialog(null, "Congratulations, you have won!", "Information", JOptionPane.INFORMATION_MESSAGE);
	}

	/** Stops the game if it is lost. **/
	public void setLost() {
		JOptionPane.showMessageDialog(null, "You have lost.", "Information", JOptionPane.INFORMATION_MESSAGE);
		running = false;
	}

	public void newGame(Difficulty difficulty) {
		new Minesweeper(difficulty);
		this.dispose();
	}

	public void revealField(Field field) {
		this.fieldRevealer.reveal(field);
	}

	public void revealEntireField() {
		this.fieldRevealer.revealEntireField();
	}

	public boolean isRunning() {
		return running;
	}
	
}
