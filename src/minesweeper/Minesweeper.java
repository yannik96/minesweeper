package minesweeper;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import minesweeper.enums.Difficulty;

public class Minesweeper extends JFrame {
	
	public static final int EASY_ROWS = 9;
	public static final int EASY_COLUMNS = 9;
	public static final int EASY_MINES = 10;
	
	public static final int ADVANCED_ROWS = 16;
	public static final int ADVANCED_COLUMNS = 16;
	public static final int ADVANCED_MINES = 40;
	
	public static final int EXPERT_ROWS = 16;
	public static final int EXPERT_COLUMNS = 32;
	public static final int EXPERT_MINES = 99;
	
	public static int WIDTH;
	public static int HEIGHT;
	
	private Difficulty difficulty;
	
	private boolean running;
	
	private int numberOfRows;
	private int numberOfColumns;
	private int numberOfMines;

	private Field[][] field;

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
	
	public void initialize() {
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
	
	public void createWindow() {
		setSize(WIDTH, HEIGHT);
		Container pane = getContentPane();
		pane.setLayout(new GridLayout(numberOfRows, numberOfColumns));
		setLocationRelativeTo(null);
		createMenu();
		setVisible(true);
	}
	
	public void createMenu() {
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
		
		menuItemNewEasy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				newGame(Difficulty.EASY);
			}
		});
		
		menuItemNewAdvanced.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				newGame(Difficulty.ADVANCED);
			}
		});
		
		menuItemNewExpert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				newGame(Difficulty.EXPERT);
			}
		});
		
		setJMenuBar(menuBar);
	}
	
	public void initializeField() {
		createField();
		running = true;
		revealStartingPosition();
	}

	public void createField() {
		FieldCreator fieldCreator = new FieldCreator(this, numberOfRows, numberOfColumns, numberOfMines);
		field = fieldCreator.generate();
		for (int row = 0; row < numberOfRows; row++)
			for (int column = 0; column < numberOfColumns; column++)
				add(field[row][column]);
	}
	
	public void revealStartingPosition() {
		StartingPositionFinder startingPositionFinder = new StartingPositionFinder(field);
		Position startingPosition = startingPositionFinder.find();
		Field startingField = getFieldByPosition(startingPosition);
		startingField.reveal();
	}
	
	public Field getFieldByPosition(Position position) {
		return field[position.row][position.column];
	}
	
	public void checkVictory() {
		boolean won = true;
		for (int i = 0; i < numberOfRows; i++) {
			for (int e = 0; e < numberOfColumns; e++) {
				if (field[i][e].isEnabled() == true) won = false;
				if (!(field[i][e] instanceof Mine) && field[i][e].getToolTipText() == "flag") won = false;
			}
		}
		if (won) {
			running = false;
			JOptionPane.showMessageDialog(null, "Congratulations! You rule! ", "Information", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	public void setLost() {
		JOptionPane.showMessageDialog(null, "You have lost.", "Information", JOptionPane.INFORMATION_MESSAGE);
		running = false;
	}
	
	public void newGame(Difficulty difficulty) {
		new Minesweeper(difficulty);
		this.dispose();
	}
	
	public Difficulty getDifficulty() {
		return difficulty;
	}
	
	public boolean isRunning() {
		return running;
	}
	
}
