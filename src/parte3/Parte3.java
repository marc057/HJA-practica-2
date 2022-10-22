package parte3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import GUI.LabelButton;
import Miscelaneous.Constants;
import elements.Card;
import elements.Hand;
import elements.HandPair;

public class Parte3 {
	
	private static final String COMBINATIONS_STR = "combinations";
	
	public static void solve(LabelButton[][] lmatrix, String board) {
		//Extract selected range:
		List<String> boxes = new ArrayList<>();
		for (int i = 0; i < 13; i++) {
			for (int j = 0; j < 13; j++) {
				LabelButton target = lmatrix[i][j];
				if (target.getSelected()) { boxes.add(target.getText()); }
			}
		}
		
		//Extract the board cards:
		List<Card> boardCards = new ArrayList<>();
		for (int i = 0; i < board.length(); i += 2) {
			boardCards.add(new Card(board.substring(i, i+2)));
		}
		
		//Convert range to pairs
		List<List<Card>> pairs = new ArrayList<>();
		for (String box : boxes) {
			pairs.addAll(boxToCards(box, boardCards));
		}
		
		//Form all possible hands that include at least 1 card from pair
		Map<String, Integer> combinations = new HashMap<>();
		combinations.put(COMBINATIONS_STR, 0);
		int size = boardCards.size();
		List<Card> fromPlayer;
		List<Card> fromBoard; 
		
		for (List<Card> pair : pairs) {
			switch(size) {
			case 3: //Pick exactly 2(out of 2) from pair and 3(out of 3) from board
				fromPlayer = new ArrayList<>();
				fromPlayer.addAll(pair);
				
				fromBoard = new ArrayList<>();
				fromBoard.addAll(boardCards);
				
				processHand(combinations, fromPlayer, fromBoard);
				break;
			case 4: //Pick 1-2(out of 2) from pair and 3-4(out of 4) from board
				//Pick 2 from pair:----------------
				for (int ig = 0; ig < size; ig++) { //Select ignored card from board
					fromPlayer = new ArrayList<>();
					fromPlayer.addAll(pair);
					
					fromBoard = new ArrayList<>();
					for (int i = 0; i < size; i++) {
						if (ig == i) { continue; }
						fromBoard.add(boardCards.get(i));
					}
					processHand(combinations, fromPlayer, fromBoard);
				}
				//Pick 1 from pair:---------------
				for (int i = 0; i < 2; i++) { //Select picked card from pair
					fromPlayer = new ArrayList<>();
					fromPlayer.add(pair.get(i));
					
					fromBoard = new ArrayList<>();
					fromBoard.addAll(boardCards);
					processHand(combinations, fromPlayer, fromBoard);
				}
				break;
			case 5: //Pick 1-2(out of 2) from pair and 3-4(out of 5) from board
				//Pick 2 from pair:----------------
				for (int ig1 = 0; ig1 < size; ig1++) { //Select 2 ignored cards from board
					for (int ig2 = ig1 + 1; ig2 < size; ig2++) {
						fromPlayer = new ArrayList<>();
						fromPlayer.addAll(pair);
						
						fromBoard = new ArrayList<>();
						for (int i = 0; i < size; i++) {
							if (ig1 == i || ig2 == i) { continue; }
							fromBoard.add(boardCards.get(i));
						}
						processHand(combinations, fromPlayer, fromBoard);
					}
				}
				//Pick 1 from pair:---------------
				for (int i = 0; i < 2; i++) { //Select picked card from pair
					for (int ig = 0; ig < size; ig++) { //Select ignored card from board
						fromPlayer = new ArrayList<>();
						fromPlayer.add(pair.get(i));
						
						fromBoard = new ArrayList<>();
						for (int j = 0; j < size; j++) {
							if (j == ig) { continue; }
							fromBoard.add(boardCards.get(j));
						}
						processHand(combinations, fromPlayer, fromBoard);
					}				
				}
				break;
			}
		}
		displayResult(combinations);
	}
	
	private static List<List<Card>> boxToCards(String box, List<Card> boardCards) {
		int nA = box.charAt(0);
		int nB = box.charAt(1);
		boolean sameColor = (nA != nB && box.charAt(2) == 's');
		
		List<List<Card>> output = new ArrayList<>();
		
		for(Character cA : Constants.CardColors) {
			Card a = new Card(nA, cA);
			
			if (boardCards.contains(a)) { continue; }
			
			for(Character cB : Constants.CardColors) {
				if (sameColor == (cA.equals(cB))) { // Discard when not in scope of this box
					Card b = new Card(nB, cB);
					
					if (boardCards.contains(b)) { continue; }
					
					output.add(Arrays.asList(a, b));
				} 
			}
		}
		
		return output;
	}
	
	private static void processHand(Map<String, Integer> combinations, List<Card> fromPlayer, List<Card> fromBoard) {
		fromPlayer.sort(null);
		fromBoard.sort(null);
		
		List<Card> currList = new ArrayList<>();
		currList.addAll(fromPlayer);
		currList.addAll(fromBoard);
		currList.sort(null);
		
		Hand hand = evaluateHand(currList);
		String handName = hand.getName();
		
		if (!handName.equals(Hand.Pair)) {
			increase(combinations, handName);
		}
		else {
			HandPair pairHand = (HandPair) hand;
			int pairNum = pairHand.getNumber();
			
			boolean isPocket = true;
			for (Card c : fromPlayer) {
				if (c.getNumber() != pairNum) { isPocket = false; break; }
			}
			
			//Es de pocket?
			if (fromPlayer.size() == 2 && isPocket) {
				if (pairNum > fromBoard.get(0).getNumber()) { //Es overpair?
					increase(combinations, HandPair.Overpair);
				}
				else {//Es pocket pair below top pair
					increase(combinations, HandPair.PocketBelow);
				}
			}
			else { // No es pocket:
				if (fromBoard.get(0).getNumber() == pairNum) {//Es top pair?
					increase(combinations, HandPair.TopPair);
				}
				else if (fromBoard.get(1).getNumber() == pairNum) { //Es middle pair
					increase(combinations, HandPair.MiddlePair);
				}
				else { // Weak pair
					increase(combinations, HandPair.WeakPair);
				}
			}
		}
	}
	
	private static void displayResult(Map<String, Integer> combinations) {
		String out = "";
		int combos = combinations.get(COMBINATIONS_STR);
		
		for (String key : combinations.keySet()) {
			if (key.equals(COMBINATIONS_STR)) { continue; }
			out += key + ": " + combinations.get(key) + "/" + combos + "\n";
		}
		
		System.out.print(out);
	}
	
	private static void increase(Map<String, Integer> combinations, String name) {
		combinations.putIfAbsent(name, 0);
		combinations.compute(name, (key, val) -> val + 1);
		
		combinations.putIfAbsent(COMBINATIONS_STR, 0);
		combinations.compute(COMBINATIONS_STR, (key, val) -> val + 1);
	}
	
	private static Hand evaluateHand(List<Card> cards) {
		cards.sort(null);
		
		//Precalculations:----------------------------------------------
		int size = cards.size();
		
		//Color precalculation:.................................
		boolean hasColor = true;
		int colorNum = cards.get(0).getColor();
		
		for (int i = 1; i < size; i++) {
			if (colorNum != cards.get(i).getColor()) { hasColor = false; break; }
		}
		
		//Stair precalculation:.................................
		boolean hasStair = true;
		int firstNum = cards.get(0).getNumber();
		for (int i = 1; i < size; i++) {
			if (cards.get(i-1).getNumber() - cards.get(i).getNumber() != 1) { hasStair = false; break; }
		}
		//Edge case: Ace is lowest number in stair
		if (!hasStair && firstNum == 14 && (cards.get(size-1).getNumber() == 2)) {
			hasStair = true;
			for (int i = 2; i < size; i++) {
				if (cards.get(i-1).getNumber() - cards.get(i).getNumber() != 1) { hasStair = false; break; }
			}
		}
		
		//N of a kind precalculation:.................................
		int longestSet = 1;
		boolean hasTwoPair = false;
		int currentSet = 1;
		for (int i = 1; i < size; i++) {
			if (cards.get(i-1).getNumber() == cards.get(i).getNumber()) {
				if (longestSet >= 2 && currentSet < 2) { hasTwoPair = true; }
				currentSet++;
				if (longestSet < currentSet) { longestSet = currentSet; }
			}
			else {
				currentSet = 1;
			}
		}
		
		//Determine result:..............................................
		//Straight flush:
		if (hasColor && hasStair) { return new Hand(Hand.StraightFlush); }
		//Poker:
		if (longestSet == 4) { return new Hand(Hand.Poker); }
		//Full house:
		if (longestSet == 3 && hasTwoPair) { return new Hand(Hand.FullHouse); }
		//Flush:
		if (hasColor) { return new Hand(Hand.Flush); }
		//Straight:
		if (hasStair) { return new Hand(Hand.Straight); }
		//Trio:
		if (longestSet == 3) { return new Hand(Hand.Trio); }
		//Two pair:
		if (hasTwoPair) { return new Hand(Hand.TwoPair); }
		//Pair:
		if (longestSet == 2) { return new Hand(Hand.Pair); }
		//High card:
		return new Hand(Hand.HighCard);
	}
}
