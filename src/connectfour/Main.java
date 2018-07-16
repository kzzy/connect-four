package connectfour;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Main {

	static int x_boardSize;
	static int y_boardSize;
	static GameGrid board;
	static boolean gameInProgress = false;
	static String mode = "";
	static int playerTurn = (int)(Math.random()*2+1);
	static Queue<Player> playerTurnQueue = new LinkedList<Player>();
	static Scanner input = new Scanner(System.in);
	
	private static void cyclePlayerTurn() {
			playerTurnQueue.add(playerTurnQueue.poll());
			playerTurn = playerTurnQueue.peek().getPlayerID();
	}
	
	private static void setGameMode() {
		System.out.println("First off, please select a mode: 'PvP' or 'AI'");
		
		while(!mode.equals("pvp") && !mode.equals("ai")) {
			mode = input.next().toLowerCase();
			
			if(!mode.equals("pvp") && !mode.equals("ai")) {
				System.out.println("Invalid input, please type in either 'PvP' or 'AI' for the game");
			}
		}
		
		System.out.println();
	}
	
	private static void setBoardSize() {
		System.out.println("Enter the dimensions of the gamefield by inputting two values separated by a space");
		
		while(x_boardSize < 1 || y_boardSize < 1) {
			x_boardSize = input.nextInt();
			y_boardSize = input.nextInt();
			
			if(x_boardSize < 1 || y_boardSize < 1) {
				System.out.println("Invalid dimension sizes, they must be greater than 0! Please try again");
			}
		}
		
		board = new GameGrid(y_boardSize,x_boardSize);
		System.out.println("Creating a field with size x:" + x_boardSize + " y:" + y_boardSize + "\n");
		System.out.println();
	}
	
	private static void setupPlayers() {
		Player player_1;
		Player player_2;
		
		if(mode.equals("pvp")) {
			player_1 = new Player(1, false);
			player_2 = new Player(2, false);
		} else {
			player_1 = new Player(1, false);
			player_2 = new Player(2, true);
		}
		
		playerTurnQueue.add(player_1);
		playerTurnQueue.add(player_2);
		
		while(playerTurn != playerTurnQueue.peek().getPlayerID()) {
			playerTurnQueue.add(playerTurnQueue.poll());
		}

	}
	
	private static void startGame() {
		gameInProgress = true;
	}
	
	public static void main(String[] args) {
		int pickedColumn = 0;
		
		// Game Initialization
		System.out.println("Welcome to the classic game of Connect Four");
		setGameMode();
		setBoardSize();
		setupPlayers();
		startGame();

		// Game Flow
		while(gameInProgress) {
			board.printBoard();
			
			if(board.checkForWinner() > 0) {
				System.out.println("Player " + board.checkForWinner() + " is the winner!");
				input.close();
				return;
			}
			
			System.out.println("It is Player " + playerTurn + "'s Turn");
			
			if(!playerTurnQueue.peek().checkIfAI()) {
				System.out.print("Enter the column that you wish to place your disc in: ");
				pickedColumn = input.nextInt();
				
				while(!board.insertDisc(playerTurnQueue.peek(), pickedColumn)) {
					System.out.print("The column is full! Pick another column to place your disc in: ");
				}
			} else {
				pickedColumn = board.determineBestMove(playerTurnQueue.peek());
				board.insertDisc(playerTurnQueue.peek(), pickedColumn);
			}
			
			
			cyclePlayerTurn();
			System.out.println();
		}
		
	}

}
