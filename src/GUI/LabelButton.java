package GUI;

import java.awt.Color;
import javax.swing.JButton;

public class LabelButton extends JButton {
	
	//Constants:------------------------------------------------------------------
	private static final Color UnselectedOffsuit = new Color(191, 205, 211); //Bluish grey
	private static final Color UnselectedSuited = new Color(212, 131, 126); //Red
	private static final Color UnselectedPair = new Color(186, 255, 152); //Green
	private static final Color Selected = new Color(252, 255, 0); //Yellow
	
	//Attributes:-------------------------------
	public int i;
	public int j;
	public boolean selected;
	
	public LabelButton(int i, int j) {
		this.i = i;
		this.j = j;
		selected = false;
		text();
	}
	
	public void color() {
		if (selected) {
			this.setBackground(Selected);
		}
		else {
			if (i < j)      { this.setBackground(UnselectedSuited);  }
			else if (i > j) { this.setBackground(UnselectedOffsuit); }
			else            { this.setBackground(UnselectedPair);    }
		}
	}
	
	private void text() {
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
		
		super.setText(text);
	}
	
	public boolean toggleSelect() {
		selected = !selected;
		color();
		return selected;
	}
}
