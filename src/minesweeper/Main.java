package minesweeper;

import minesweeper.enums.Difficulty;

/**
 * @author Yannik Klein
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new Minesweeper(Difficulty.EASY);
	}

}
