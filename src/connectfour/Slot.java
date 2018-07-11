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
	
	/* Place player 1's disc */
	public void fillWhite() {
		filledSlot = 1;
	}
	
	/* Place player 2's disc */
	public void fillBlack() {
		filledSlot = 2;
	}
}
