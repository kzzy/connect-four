package connectfour;

import java.util.ArrayList;
import java.util.List;

public class GameGrid {
	private int numberOfRows;
	private int numberOfColumns;
	private List<List<Slot>> gameBoard;
	private int winningPlayer = 0;
	
	public GameGrid(int numberOfRows, int numberOfColumns) {
		this.numberOfRows = numberOfRows;
		this.numberOfColumns = numberOfColumns;
		this.gameBoard = new ArrayList<List<Slot>>(numberOfRows);
		
		// Instantiate the game field
		for(int i=0;i<numberOfRows;i++) {
			gameBoard.add(new ArrayList<Slot>());
		}
		
		for(List<Slot> row : gameBoard) {
			for(int j=0;j<numberOfColumns;j++) {
				row.add(new Slot());
			}
		}
	}
	
	/* Check if there a current winner in the game */
	public int checkForWinner() {
		switch(winningPlayer) {
		case 1:
			return 1;
		case 2:
			return 2;
		default:
			return 0;
		}
	}
	
	/* Determines if the last placed disc results in a win for the player */
	private void checkWinCondition(int discRow, int discColumn, Slot lastPlacedDisc) {
		int discColor = lastPlacedDisc.checkSlot();
		int sameColoredDiscs = 1; // Counts the current disc
		int originalColumn = discColumn;
		int originalRow = discRow;
		
		/* Horizontal Win Check */
		// Count left of the lastPlacedDisc non-inclusive
		discColumn--;
		while(discColumn > -1 && gameBoard.get(discRow).get(discColumn).checkSlot() == discColor) {
			sameColoredDiscs++;
			discColumn--;
		}
		
		discColumn = originalColumn;
		// Count right of the lastPlacedDisc non-inclusive
		discColumn++;
		while(discColumn < numberOfColumns && gameBoard.get(discRow).get(discColumn).checkSlot() == discColor) {
			sameColoredDiscs++;
			discColumn++;
		}
		
		// Check if there are enough sameColoredDiscs to win
		if(sameColoredDiscs == 4) {
			winningPlayer = discColor;
			return;
		}

		discColumn = originalColumn;
		
		/* Vertical Win Check */
		sameColoredDiscs = 1;
		discRow++;
		while(discRow < numberOfRows && gameBoard.get(discRow).get(discColumn).checkSlot() == discColor) {
			sameColoredDiscs++;
			discRow++;
		}
		
		if(sameColoredDiscs == 4) {
			winningPlayer = discColor;
			return;
		}
		discRow = originalRow; 
		
		/* Right Diagonal Win Check */
		sameColoredDiscs = 1;
		
		discRow++;
		discColumn--;
		while(discColumn > -1 && discRow < numberOfRows && gameBoard.get(discRow).get(discColumn).checkSlot() == discColor) {
			sameColoredDiscs++;
			discRow++;
			discColumn--;
		}
		
		discRow = originalRow;
		discColumn = originalColumn;
		
		discRow--;
		discColumn++;
		while(discColumn < numberOfColumns && discRow > -1 && gameBoard.get(discRow).get(discColumn).checkSlot() == discColor) {
			sameColoredDiscs++;
			discRow--;
			discColumn++;
		}
		
		if(sameColoredDiscs == 4) {
			winningPlayer = discColor;
			return;
		}
		
		/* Left Diagonal Win Check */
		sameColoredDiscs = 1;
		discRow = originalRow;
		discColumn = originalColumn;
		
		discRow++;
		discColumn++;
		while(discColumn < numberOfColumns && discRow < numberOfRows && gameBoard.get(discRow).get(discColumn).checkSlot() == discColor) {
			sameColoredDiscs++;
			discRow++;
			discColumn++;
		}
		
		discRow = originalRow;
		discColumn = originalColumn;
		
		discRow--;
		discColumn--;
		while(discColumn > -1 && discRow > -1 && gameBoard.get(discRow).get(discColumn).checkSlot() == discColor) {
			sameColoredDiscs++;
			discRow--;
			discColumn--;
		}
		
		if(sameColoredDiscs == 4) {
			winningPlayer = discColor;
			return;
		}
	}
	
	/* Inserts the player's disc into the game board */
	public boolean insertDisc(int player, int column) {
		boolean successfullyInsertedDisc = false;
		
		column--; // Column count offset
		for(int i=numberOfRows-1;i>=0;i--) {
			if(gameBoard.get(i).get(column).isEmpty()) {
				if(player == 1) {
					gameBoard.get(i).get(column).fillWhite();
				} else {
					gameBoard.get(i).get(column).fillBlack();
				}
				
				successfullyInsertedDisc = true;
				checkWinCondition(i, column, gameBoard.get(i).get(column));
				break;
			}
		}
		
		return successfullyInsertedDisc;
	}
	
	/* Prints a layout of the game status */
	public void printBoard() {
		for(List<Slot> row : gameBoard) {
			for(int i=0;i<numberOfColumns;i++) {
				System.out.print(row.get(i).printSlot());
			}
			System.out.println();
		}
	}
}
