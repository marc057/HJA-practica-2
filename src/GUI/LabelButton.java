package GUI;

import java.awt.Color;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class LabelButton extends MyButton {
	
	//Constants:------------------------------------------------------------------
	private static final Color UnselectedOffsuit = new Color(191, 205, 211); //Bluish grey
	private static final Color UnselectedSuited = new Color(212, 131, 126); //Red
	private static final Color UnselectedPair = new Color(186, 255, 152); //Green
	private static final Color Selected = new Color(252, 255, 0); //Yellow
	
	//Attributes:-----------------------------------------------------------------
	private static int numSelected = 0;
	
	//Getters:-----------------------------------------------------------------------
	@Override
	public int getNumSelected() { return getNumSelectedS(); }
	public static int getNumSelectedS() { return numSelected; }
	
	//Constructor:------------------------------------------------------------------
	public LabelButton(int i, int j, ActionListener l) {
		super(i, j, l);
	}
	
	//Setup:------------------------------------------------------------------------
	protected void initText() {
		String iStr = LabelPanel.coordToString(i);
		String jStr = LabelPanel.coordToString(j);
		String text;
		
		if (j >= i) {
			text = iStr + jStr;
			if (j > i) {
				text += "s";
			}
		}
		else {
			text = jStr + iStr + "o";
		}
		
		this.setText(text); //De JButton
	}
	
	//Update:------------------------------------------------------------------------
	@Override
	protected void color() {
		if (selected) {
			this.setBackground(Selected);
		}
		else {
			if (i < j)      { this.setBackground(UnselectedSuited);  }
			else if (i > j) { this.setBackground(UnselectedOffsuit); }
			else            { this.setBackground(UnselectedPair);    }
		}
	}
	
	//Modify:-------------------------------------------------------------------------
	@Override
	public void sumNumSelected(int amount) {
		LabelButton.numSelected += amount;	
	}
}