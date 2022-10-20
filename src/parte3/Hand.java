package parte3;

public class Hand {
	//Constants:------------------------------------------------------------------
	public static final String StraightFlush = "Straight flush"; //Escalera color
	public static final String Poker = "Poker"; //4 of a kind
	public static final String FullHouse = "Full house"; //3 and 2 of a kind 
	public static final String Flush = "Flush"; //Color 
	public static final String Straight = "Straight"; //Escalera
	public static final String Trio = "Trio"; //3 of a kind 
	public static final String TwoPair = "Two pair"; //2 and 2 of a kind 
	public static final String Pair = "Pair"; //2 of a kind 
	public static final String HighCard = "High card";
	
	//Attributes:----------------------
	private final String name;
	
	//Constructor:---------------------
	public Hand(String name) {
		this.name = name;
	}
	
	//Getters:-------------------------
	public String getName() {
		return name;
	}
}
