package minesweeper;

import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import minesweeper.enums.Difficulty;

/**
 * a class for modelling a minesweeper game
 * @author Yannik Klein
 */
public class Minesweeper extends JFrame {
	
	/*
	 * the amount of rows, columns and mines for an easy difficulty game
	 */
	public static final int EASY_ROWS = 9;
	public static final int EASY_COLUMNS = 9;
	public static final int EASY_MINES = 10;
	
	/*
	 * the amount of rows, columns and mines for an advanced difficulty game
	 */
	public static final int ADVANCED_ROWS = 16;
	public static final int ADVANCED_COLUMNS = 16;
	public static final int ADVANCED_MINES = 40;
	
	/*
	 * the amount of rows, columns and mines for an expert difficulty game
	 */
	public static final int EXPERT_ROWS = 16;
	public static final int EXPERT_COLUMNS = 32;
	public static final int EXPERT_MINES = 99;
	
	/*
	 * the frame's width and height
	 */
	public static int WIDTH;
	public static int HEIGHT;
	
	/*
	 * the game's difficulty
	 */
	private Difficulty difficulty;
	
	/*
	 * true if the game is currently running
	 */
	private boolean running;
	
	/*
	 * the game's amount of rows, columns and mines
	 */
	private int rows;
	private int columns;
	private int mines;
	
	/**
	 * the game's playing field
	 */
	private Field[][] field;
	
	private ArrayList<Value> emptyValues = new ArrayList<Value>(); 

	/**
	 * standard constructor
	 * creates a minesweeper game with easy difficulty
	 */
	public Minesweeper() {
		super("Minesweeper");
		difficulty = Difficulty.EASY;
		initialize();
	}
	
	/**
	 * constructor
	 * creates a minesweeper game with given difficulty
	 * @param difficulty the game's difficulty
	 */
	public Minesweeper(Difficulty difficulty) {
		super("Minesweeper");
		this.difficulty = difficulty;
		running = initialize();
	}
	
	/**
	 * initializes a minesweeper game
	 */
	public boolean initialize() {
		setDimension();
		field = createField();
		createWindow();
		
		return true;
	}
	
	/**
	 * @return the game's difficulty
	 */
	public Difficulty getDifficulty() {
		return difficulty;
	}

	
	/**
	 * sets the game's amount of rows, columns and mines depending on the difficulty
	 */
	public void setDimension() {
		
		switch(difficulty) {
			case EASY: 
				rows = EASY_ROWS;
				columns = EASY_COLUMNS;
				mines = EASY_MINES;
				break;
			case ADVANCED:
				rows = ADVANCED_ROWS;
				columns = ADVANCED_COLUMNS;
				mines = ADVANCED_MINES;
				break;
			case EXPERT:
				rows = EXPERT_ROWS;
				columns = EXPERT_COLUMNS;
				mines = EXPERT_MINES;
				break;
		}
		
		WIDTH = 40 * columns;
		HEIGHT = 40 * rows;
		
	}
	
	/**
	 * creates the window
	 */
	public void createWindow() {
		
		setSize(WIDTH, HEIGHT);
		
		Container pane = getContentPane();
		pane.setLayout(new GridLayout(rows, columns));
		
		setLocationRelativeTo(null);
		
		createMenu();
		
		setVisible(true);
		
	}
	
	/**
	 * creates the menu
	 */
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
	
	/**
	 * 
	 * @return
	 */
	public Field[][] createField() {
		
		Field[][] field = new Field[rows][columns];
		Set<String> mineSet = new HashSet<String>();
		
		/*
		 * generate the right amount of mines and their position
		 */
		while (mineSet.size() < mines) {
			Random generator = new Random();
			
			int h = generator.nextInt(rows);
			int w = generator.nextInt(columns);
			
			String hw = Integer.toString(h) + Integer.toString(w);
			
			if (!mineSet.contains(hw)) mineSet.add(hw);
		}
		
		/*
		 * setting fields
		 */
		for (int h = 0; h < rows; h++) {
			for (int w = 0; w < columns; w++) {
				String hw = Integer.toString(h) + Integer.toString(w);
				
				if (mineSet.contains(hw)) {
					field[h][w] = new Mine(h, w);
				} else {
					field[h][w] = new Value(h, w);
				}	
				field[h][w].setField(field);
				field[h][w].putClientProperty("field", field[h][w]);
				field[h][w].setBackground(Color.GRAY);
				
				/*
				 * on left click
				 */
				 field[h][w].addActionListener(new ActionListener() {
					  public void actionPerformed(ActionEvent e) {
						  
						  if (running) {
						  
							  JButton f = ((JButton) e.getSource());
						  
							  ((Field) f).reveal();	
						  
							  if (f instanceof Mine) {
								  running = false;
								  JOptionPane.showMessageDialog(null, "You have lost.", "Information", JOptionPane.INFORMATION_MESSAGE);
								  revealField();
							  } else {
								  checkVictory();
							  }
							  
						  }
						  
					  }
		
				 });
				 
				 /*
				  * on right click
				  */
				 field[h][w].addMouseListener(new MouseAdapter() {
					 public void mouseClicked(MouseEvent event) {
						 
						 if (running) {
						 
							 if (SwingUtilities.isRightMouseButton(event) && event.getClickCount() == 1) {	
							 
								 JButton source = (JButton) event.getComponent();
							
								 if (source.isEnabled() || source.getToolTipText() == "flag") {
								 
									if (source.isEnabled()) {
										source.setEnabled(false);
										source.setIcon(new ImageIcon("Images/flag.png"));
										source.setDisabledIcon(new ImageIcon("Images/flag.png"));
										source.setToolTipText("flag");
										source.setBackground(Color.WHITE);
										checkVictory();
									} else {
										source.setIcon(new ImageIcon(""));
										source.setDisabledIcon(new ImageIcon(""));
										source.setEnabled(true);
										source.setToolTipText("");
										source.setBackground(Color.GRAY);
									}
									
								}
					        	
							 }
							 
							 
						 }
						 
					 }   
					 
				 });
				
				add(field[h][w]);
			}
		}
		
		/*
		 * setting field values
		 */
		for (int h = 0; h < rows; h++) {
			for (int w = 0; w < columns; w++) {
				/*
				 * field is a mine
				 */
				if (field[h][w] instanceof Mine) {
					/*
					 * increase value in surrounding fields
					 */
					for (int i = h-1; i <= h+1; i++) {
						for (int u = w-1; u <= w+1; u++) {
							/*
							 * check if field boundaries are correct
							 */
							if (i >= 0 && i < rows && u >= 0 && u < columns) {
								if (field[i][u] instanceof Value) ((Value) field[i][u]).increaseValue();
							}
						}	
					}	
				}
			}	
		}
		
		for (int h = 0; h < rows; h++) {
			for (int w = 0; w < columns; w++) {
				if (field[h][w] instanceof Value && ((Value) field[h][w]).getValue() == 0) {
					emptyValues.add((Value) field[h][w]); 
				}
			}
		}
		
		Random generator = new Random();
		int rev = generator.nextInt(emptyValues.size());
		emptyValues.get(rev).reveal();
		
		return field;
	}
	
	public void revealField() {
		for (int i = 0; i < rows; i++) {
			for (int k = 0; k < columns; k++) {
				field[i][k].reveal();
				if (field[i][k].getToolTipText() == "flag" && !(field[i][k] instanceof Mine)) {
					 field[i][k].setIcon(new ImageIcon("Images/nomine.png"));
					 field[i][k].setDisabledIcon(new ImageIcon("Images/nomine.png"));
				}
			}
		}
	}
	
	public void checkVictory() {
		boolean won = true;
		for (int i = 0; i < rows; i++) {
			for (int e = 0; e < columns; e++) {
				if (field[i][e].isEnabled() == true) won = false;
				if (!(field[i][e] instanceof Mine) && field[i][e].getToolTipText() == "flag") won = false;
			}
		}
		if (won) {
			running = false;
			JOptionPane.showMessageDialog(null, "Congratulations! You rule! ", "Information", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	public void newGame(Difficulty difficulty) {
		new Minesweeper(difficulty);
		this.dispose();
	}
	
}
