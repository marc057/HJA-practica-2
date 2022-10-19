package parte3;

import java.util.Arrays;
import java.util.List;

public class Card implements Comparable<Card>{
	//Constants:----------------------------
	public static final List<Character> colorChars = Arrays.asList('h', 'd', 's', 'c');
	
	//Attributes:-------------------------
	int number;
	int color;
	
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
		switch(numberChar) {
			case 'A': return 14;
			case 'K': return 13;
			case 'Q': return 12;
			case 'J': return 11;
			case 'T': return 10;
			default:  return numberChar - '0';
		}
	}
	
	private static int toColor(char colorChar) {
		return colorChars.indexOf(colorChar);
	}

	@Override
	public int compareTo(Card other) {
		return this.number - other.number;
	}
}
