package connectfour;

public class Player {
	private int player;
	private boolean isAI;
	
	public Player(int player, boolean isAI) {
		this.player = player;
		this.isAI = isAI;
	}
	
	public int getPlayerID() {
		return player;
	}
	
	public boolean checkIfAI() {
		return isAI;
	}
}
