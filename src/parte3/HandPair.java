package parte3;

public class HandPair extends Hand {
	//Constants:-------------------------------------
	public static final String Overpair = "Overpair";
	public static final String PocketBelow = "Pocket pair below top pair";
	public static final String TopPair = "Top pair";
	public static final String MiddlePair = "Middle pair";
	public static final String WeakPair = "Weak pair";
	
	//Attributes:-----------------------------------
	private final int number;
	
	//Constructor:----------------------------------
	public HandPair(int number) {
		super(Pair);
		this.number = number;
	}
	
	//Getters:--------------------------------------
	public int getNumber() {
		return number;
	}
}