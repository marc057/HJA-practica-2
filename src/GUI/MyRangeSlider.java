package GUI;

import javax.swing.JSlider;

@SuppressWarnings("serial")
public class MyRangeSlider extends JSlider{
	private static MyRangeSlider instance = null;
	public static MyRangeSlider getInstance() {
		if (instance == null) {
			instance = new MyRangeSlider();
		}
		return instance;
	}
}
