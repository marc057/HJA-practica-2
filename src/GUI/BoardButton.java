package GUI;

import java.awt.Color;
import java.awt.event.ActionListener;

import Miscelaneous.Constants;

@SuppressWarnings("serial")
public class BoardButton extends MyButton {
	//Constants:------------------------------------------------------------------
	private static final Color UnselectedSpades = new Color(111, 125, 131); //Bluish grey
	private static final Color UnselectedHearts = new Color(210, 80, 100); //Red
	private static final Color UnselectedClubs = new Color(156, 255, 122); //Green
	private static final Color UnselectedDiamonds = new Color(0,191,255); //Blue
	private static final Color Selected = new Color(252, 255, 0); //Yellow
	
	//Attributes:-----------------------------------------------------------------
	private static int numSelected = 0;
	
	//Getters:-----------------------------------------------------------------------
	@Override
	public int getNumSelected() { return getNumSelectedS(); }
	public static int getNumSelectedS() { return numSelected; }
	
	//Constructors:----------------------------------------
	public BoardButton(int i, int j, ActionListener l) {
		super(i, j, l);
	}
	
	//Setup:-----------------------------------------------
	public void initText() {
		String s = "";
		s += Constants.CardNumbers.get(i);
		s += Constants.CardColors.get(j);
		this.setText(s); //De JButton
	}
	
	//Update:------------------------------------------------------------------------
	@Override
	protected void color() {
		if (selected) {
			this.setBackground(Selected);
		}
		else {
			switch(j) {
			case 0: //Hearts
				this.setBackground(UnselectedHearts); break;
			case 1: //Diamonds
				this.setBackground(UnselectedDiamonds); break;
			case 2: //Spades
				this.setBackground(UnselectedSpades); break;
			default: //Clubs
				this.setBackground(UnselectedClubs);
			}
		}
	}
	
	//Modify:--------------------------
	@Override
	public void sumNumSelected(int amount) {
		BoardButton.numSelected += amount;
	}
}