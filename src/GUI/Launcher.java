package GUI;
import java.awt.*;

import javax.swing.*;

public class Launcher {

	public static void main(String[] args) {
		JFrame frame = new JFrame("Frame name");
		frame.setSize(new Dimension(600, 600));
		
		LabelPanel labelPanel = new LabelPanel();
		frame.add(labelPanel);
		
		frame.setVisible(true);
		
		// Testing, muy crudo todavia
		labelPanel.paintRange("AA,55,JJ+");
	}
}
