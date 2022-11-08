package Miscelaneous;

import GUI.BoardButton;
import GUI.BoardPanel;
import GUI.LabelPanel;
import GUI.ManosPanel;
import GUI.MyRangeSlider;
import GUI.MyRangeText;
import parte3.Parte3;

public final class Changes {
	//References:---------------------------------------
	private static final LabelPanel rangePanel = LabelPanel.getInstance();
	private static final MyRangeText rangeText = MyRangeText.getInstance();
	private static final MyRangeSlider rangeSlider = MyRangeSlider.getInstance();
	private static final BoardPanel boardPanel = BoardPanel.getInstance();
	private static final ManosPanel handPanel = ManosPanel.getInstance();
	
	//Methods:--------------------------------------------
	public static void updateRange() {
		//TODO
		//rangeText.updateRange();
		//rangeSlider.updateRange();
		updateCombinations();
	}
	
	public static void updateCombinations() {
		final int boardSize = BoardButton.getNumSelectedS();
		if (boardSize >= 3 && boardSize <= 5) {
			var map = Parte3.solve(rangePanel.getMatrix(), boardPanel.boardToString());
			handPanel.setManosPanel(map);
		}
		else {
			handPanel.resetManosPanel();
		}
		
	}
}
