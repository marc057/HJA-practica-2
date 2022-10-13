package GUI;
import java.awt.*;

import javax.swing.*;

public class Launcher {

	public static void main(String[] args) {
		JFrame frame = new JFrame("Frame name");
		frame.setSize(new Dimension(600, 600));
		
		// MainPanel
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		
		// Panel containing the label matrix
		LabelPanel labelPanel = new LabelPanel();
		mainPanel.add(labelPanel, BorderLayout.CENTER);
		
		// Panel containing the textfield and print button
		JPanel southPanel = new JPanel();
		southPanel.setLayout(new FlowLayout());
		mainPanel.add(southPanel, BorderLayout.PAGE_END);
		
		// TextField
		JTextField textField = new JTextField();
		textField.setPreferredSize(new Dimension(120, 30));
		southPanel.add(textField);
		
		// Print Button
		JButton printRangeButton = new JButton("PRINT");
		printRangeButton.addActionListener( e -> {
			try {
				labelPanel.paintRange(textField.getText());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		southPanel.add(printRangeButton);
		
		frame.add(mainPanel);
		mainPanel.setVisible(true);
		frame.setVisible(true);
		
	/*	// Testing, muy crudo todavia
		try {
			labelPanel.paintRange("J3o-J8o,TT+,84o,54s,96s-93s,Q3+");
		} catch (Exception e) {
			System.out.println("Exception: " + e.getMessage() + "\n");
		}*/
	}
}
