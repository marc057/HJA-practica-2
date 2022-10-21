package GUI;

import java.awt.event.ActionListener;
import Miscelaneous.Constants;

public class BoardButton extends MyButton {
	//Constructors:----------------------------------------
	public BoardButton(int i, int j, ActionListener l) {
		super(i, j, l);
	}
	
	//Setup:-----------------------------------------------
	public void initText() {
		String s = "";
		s += Constants.CardNumbers.get(i);
		s += Constants.CardColors.get(j);
		this.setText(s);
	}
}