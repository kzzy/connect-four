package connectfour;

import java.util.Scanner;

public class Main {

	static int x_boardSize;
	static int y_boardSize;
	static boolean gameInProgress = false;
	static int playerTurn = 0;
	
	private static void switchPlayerTurn() {
		switch(playerTurn) {
			case 1:
				playerTurn = 2;
				break;
			case 2:
				playerTurn = 1;
				break;
		}
	}
	
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		
		// Game Initialization
		System.out.println("Welcome to the classic game of Connect Four\n");
		System.out.println("Enter the dimensions of the gamefield by inputting two values separated by a space");
		
		while(x_boardSize < 1 || y_boardSize < 1) {
			x_boardSize = input.nextInt();
			y_boardSize = input.nextInt();
			
			if(x_boardSize < 1 || y_boardSize < 1) {
				System.out.println("Invalid dimension sizes, they must be greater than 0! Please try again");
			} else {
				break;
			}
		}

		System.out.println();
		System.out.println("Creating a field with size x:" + x_boardSize + " y:" + y_boardSize + "\n");
		GameGrid board = new GameGrid(y_boardSize,x_boardSize);
		gameInProgress = true;
		playerTurn = 1;
		
		// Game Flow
		while(gameInProgress) {
			board.printBoard();
			
			if(board.checkForWinner() > 0) {
				System.out.println("Player " + board.checkForWinner() + " is the winner!");
				input.close();
				return;
			}
			
			System.out.println("It is Player " + playerTurn + "'s Turn");
			System.out.print("Enter the column that you wish to place your disc in: ");
			
			while(!board.insertDisc(playerTurn, input.nextInt())) {
				System.out.print("The column is full! Pick another column to place your disc in: ");
			}
			
			switchPlayerTurn();
			System.out.println();
		}
		
	}

}
