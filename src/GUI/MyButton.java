package GUI;

import java.awt.event.ActionListener;
import javax.swing.JButton;

public abstract class MyButton extends JButton {
	//Attributes
	protected final int i;
	protected final int j;
	protected final ActionListener l;
	protected final boolean selected;
	
	public MyButton(int i, int j, ActionListener l) {
		this.i = i;
		this.j = j;
		this.l = l;
		this.selected = false;
	}
	
	public abstract void initText();
	
	public abstract void toggleSelected();
	
	public void setSelect(boolean newValue) { 
		if(newValue != selected) {
			toggleSelected();
		}
	}
}