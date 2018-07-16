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
	
	private void setWinner(int discColor) {
		winningPlayer = discColor;
	}
	
	/* Determines if the last placed disc results in a win for the player */
	private int checkWinCondition(int discRow, int discColumn, Slot lastPlacedDisc, boolean AI_calculateOptimalMove) {
		int discColor = lastPlacedDisc.checkSlot();
		int sameColoredDiscs = 1; // Counts the current disc
		int bestDiscsRecord = 0;
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
			if(!AI_calculateOptimalMove) {
				setWinner(discColor);
			} else {
				return 4;
			}
		} else if(sameColoredDiscs > bestDiscsRecord) {
			bestDiscsRecord = sameColoredDiscs;
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
			if(!AI_calculateOptimalMove) {
				setWinner(discColor);
			} else {
				return 4;
			}
		} else if(sameColoredDiscs > bestDiscsRecord) {
			bestDiscsRecord = sameColoredDiscs;
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
			if(!AI_calculateOptimalMove) {
				setWinner(discColor);
			} else {
				return 4;
			}
		} else if(sameColoredDiscs > bestDiscsRecord) {
			bestDiscsRecord = sameColoredDiscs;
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
			if(!AI_calculateOptimalMove) {
				setWinner(discColor);
			} else {
				return 4;
			}
		} else if(sameColoredDiscs > bestDiscsRecord) {
			bestDiscsRecord = sameColoredDiscs;
		}
		return bestDiscsRecord;
	}
	
	public int determineBestMove(Player bot) {
		int column = 0;
		int curCombo;
		int largestSlotCombo = 0;
		int largestSlotComboColumn = 0;
		
		for(int j=column;j<numberOfColumns-1;j++) {
			for(int i=numberOfRows-1;i>=0;i--) {
				if(gameBoard.get(i).get(j).isEmpty()) {
					gameBoard.get(i).get(j).fillSlot(bot);
					
					curCombo = checkWinCondition(i, j, gameBoard.get(i).get(j), true);
					if(curCombo > largestSlotCombo) {
						largestSlotCombo = curCombo;
						largestSlotComboColumn = j;
					}
					gameBoard.get(i).get(j).removeSlot();
				}
			}
		}
		return largestSlotComboColumn+1;
	}
	
	/* Inserts the player's disc into the game board */
	public boolean insertDisc(Player player, int column) {
		boolean successfullyInsertedDisc = false;
		
		column--; // Column count offset
		for(int i=numberOfRows-1;i>=0;i--) {
			if(gameBoard.get(i).get(column).isEmpty()) {
				gameBoard.get(i).get(column).fillSlot(player);
				
				successfullyInsertedDisc = true;
				checkWinCondition(i, column, gameBoard.get(i).get(column), false);
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
