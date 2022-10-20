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
		
		for(Character cA : Card.colorChars) {
			Card a = new Card(nA, cA);
			
			if (boardCards.contains(a)) { continue; }
			
			for(Character cB : Card.colorChars) {
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
		combinations.computeIfPresent(COMBINATIONS_STR, (key, val) -> val + 1);
		
		if (!handName.equals("pair")) {
			combinations.putIfAbsent(handName, 0);
			combinations.computeIfPresent(handName, (key, val) -> val + 1 );
		}
		else {
			HandPair pairHand = (HandPair) hand;
			int pairNum = pairHand.getPairNum();
			
			boolean isPocket = true;
			for (Card c : fromPlayer) {
				if (c.number != pairNum) { isPocket = false; break; }
			}
			
			//Es de pocket?
			if (fromPlayer.size() == 2 && isPocket) {
				if (pairNum > fromBoard.get(0).number) { //Es overpair?
					combinations.putIfAbsent("Overpair", 0);
					combinations.computeIfPresent("Overpair", (key, val) -> val + 1 );
				}
				else {//Es pocket pair below top pair
					combinations.putIfAbsent("Pocket pair below top pair", 0);
					combinations.computeIfPresent("Pocket pair below top pair", (key, val) -> val + 1 );
				}
			}
			else { // No es pocket:
				if (fromBoard.get(0).number == pairNum) {//Es top pair?
					combinations.putIfAbsent("Top pair", 0);
					combinations.computeIfPresent("Top pair", (key, val) -> val + 1 );
				}
				else if (fromBoard.get(1).number == pairNum) { //Es middle pair
					combinations.putIfAbsent("Middle pair", 0);
					combinations.computeIfPresent("Middle pair", (key, val) -> val + 1 );
				}
				else { // Weak pair
					combinations.putIfAbsent("Weak pair", 0);
					combinations.computeIfPresent("Weak pair", (key, val) -> val + 1 );
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
}
