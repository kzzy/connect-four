package connectfour;

public class Slot {
	private int filledSlot = -1;
	
	/* Return the slot status */
	public int checkSlot() {
		return filledSlot;
	}
	
	/* Helper method to print the slot */
	public String printSlot() {
		switch(filledSlot) {
		case 1:
			return "|x|";
		case 2:
			return "|o|";
		default:
			return "| |";
		}
	}
	
	/* Check if the slot is empty */
	public boolean isEmpty() {
		if(filledSlot != -1) {
			return false;
		}
		return true;
	}

	public void fillSlot(Player player) {
		if(player.getPlayerID() == 1) {
			filledSlot = 1;
		} else {
			filledSlot = 2;
		}
	}
	
	public void removeSlot() {
		filledSlot = -1;
	}
}
