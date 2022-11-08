package GUI;

import javax.swing.JTextField;

public class MyRangeText extends JTextField{
	//Singleton:---------------------------------------
	private static MyRangeText instance = null;
	public static MyRangeText getInstance() {
		if (instance == null) {
			instance = new MyRangeText();
		}
		return instance;
	}
}
