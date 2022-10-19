package parte3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import GUI.LabelButton;

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
		List<Card> currList;
		
		for (List<Card> pair : pairs) {
			switch(size) {
			case 3: //Pick exactly 2(out of 2) from pair and 3(out of 3) from board
				currList = new ArrayList<>();
				currList.addAll(pair);
				currList.addAll(boardCards);
				
				processHand(combinations, currList);
				break;
			case 4: //Pick 1-2(out of 2) from pair and 3-4(out of 4) from board
				//Pick 2 from pair:----------------
				for (int ig = 0; ig < size; ig++) { //Select ignored card from board
					currList = new ArrayList<>();
					currList.addAll(pair);
					for (int i = 0; i < size; i++) {
						if (ig == i) { continue; }
						currList.add(boardCards.get(i));
					}
					processHand(combinations, currList);
				}
				//Pick 1 from pair:---------------
				for (int i = 0; i < 2; i++) { //Select picked card from pair
					currList = new ArrayList<>();
					currList.add(pair.get(i));
					currList.addAll(boardCards);
					processHand(combinations, currList);
				}
				break;
			case 5: //Pick 1-2(out of 2) from pair and 3-4(out of 5) from board
				//Pick 2 from pair:----------------
				for (int ig1 = 0; ig1 < size; ig1++) { //Select 2 ignored cards from board
					for (int ig2 = ig1 + 1; ig2 < size; ig2++) {
						currList = new ArrayList<>();
						currList.addAll(pair);
						for (int i = 0; i < size; i++) {
							if (ig1 == i || ig2 == i) { continue; }
							currList.add(boardCards.get(i));
						}
						processHand(combinations, currList);
					}
				}
				//Pick 1 from pair:---------------
				for (int i = 0; i < 2; i++) { //Select picked card from pair
					for (int ig = 0; ig < size; ig++) { //Select ignored card from board
						currList = new ArrayList<>();
						currList.add(pair.get(i));
						
						for (int j = 0; j < size; j++) {
							if (j == ig) { continue; }
							currList.add(boardCards.get(j));
						}
						processHand(combinations, currList);
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
		
		for(Character cA : Card.colorChars) {
			Card a = new Card(nA, cA);
			for(Character cB : Card.colorChars) {
				if (sameColor != (cA.equals(cB))) { continue; } // Discard when not in scope of this box
				Card b = new Card(nB, cB);
				output.add(Arrays.asList(a, b));
			}
		}
		
		return output;
	}
	
	private static void processHand(Map<String, Integer> combinations, List<Card> currList) {
		Hand hand = evaluateHand(currList);
		String handName = hand.getName();
		combinations.putIfAbsent(handName, 0);
		combinations.computeIfPresent(handName, (key, val) -> val + 1 );
		combinations.computeIfPresent(COMBINATIONS_STR, (key, val) -> val + 1);
	}
	
	private static void displayResult(Map<String, Integer> combinations) {
		String out = "";
		int combos = combinations.get(COMBINATIONS_STR);
		
		for (String key : combinations.keySet()) {
			out += key + ": " + combinations.get(key) + "/" + combos + "\n";
		}
		
		System.out.print(out);
	}
}
