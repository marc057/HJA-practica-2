package elements;

import java.util.Arrays;
import java.util.List;

import Miscelaneous.Constants;

public class Card implements Comparable<Card>{	
	//Attributes:-------------------------
	int number;
	int color;
	
	//Getters:---------------------------------------
	public final int getNumber() { return number; }
	public final int getColor() { return color; }
	
	//Constructors:-----------------------------------
	public Card(String input) {
		number = toNumber(input.charAt(0));
		color = toColor(input.charAt(1));
	}
	
	public Card(int number, char color) {
		this.number = number;
		this.color = toColor(color);
	}
	
	//Parse methods:--------------------------------------
	private static int toNumber(char numberChar) {
		return Constants.CardNumbers.indexOf(numberChar);
	}
	
	private static int toColor(char colorChar) {
		return Constants.CardColors.indexOf(colorChar);
	}

	@Override
	public int compareTo(Card other) {
		return this.number - other.number;
	}
}
