package GUI;

import java.awt.Color;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.border.LineBorder;

@SuppressWarnings("serial")
public abstract class MyButton extends JButton {
	//Attributes:---------------------------------------------------------------
	protected final int i;
	protected final int j;
	protected boolean selected;
	
	//Getters:---------------------------------------------------------------------
	public int getI() { return i; }
	public int getJ() { return j; }
	public boolean getSelected() { return selected; }
	public abstract int getNumSelected();
	
	//Constructor:----------------------------------------------------------------
	public MyButton(int i, int j, ActionListener l) {
		this.i = i;
		this.j = j;
		this.selected = false;
		this.addActionListener(l);
		this.setBorder(new LineBorder(Color.BLACK));
		initText();
		color();
	}
	
	//Initializers:------------------------------------------------------------------
	protected abstract void initText();
	
	//Changers:--------------------------------------------------------------------
	public abstract void sumNumSelected(int amount);
	
	protected abstract void color();
	
	public void toggleSelect() {
		selected = !selected;
		sumNumSelected(selected ? 1 : -1);
		color();
	}
	
	public void setSelect(boolean newValue) { 
		if(newValue != selected) {
			toggleSelect();
		}
	}
}