package GUI;
import java.awt.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Rankings.Sklansky;

public class Launcher {
	
		private static boolean sliderPrintChange = false;
		
		public static void main(String[] args) {
		
		JFrame frame = new JFrame("Frame name");
		frame.setSize(new Dimension(800, 800));
		
		// MainPanel
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		
		// Panel containing the textfield, print button, ranking selector and reset
		JPanel southPanel = new JPanel();
		southPanel.setLayout(new FlowLayout());
		mainPanel.add(southPanel, BorderLayout.PAGE_END);
		
		// TextField
		JTextField textField = new JTextField();
		textField.setPreferredSize(new Dimension(120, 30));
		southPanel.add(textField);
		
		// Panel containing the label matrix
		LabelPanel labelPanel = new LabelPanel(textField);
		mainPanel.add(labelPanel, BorderLayout.CENTER);
		
		//Slider solo declarado
		JSlider slider = new JSlider(0,169,0);//Min 0 Max 169 empieza en 0
		//Label con el porcentaje del slider
		JLabel labelPerc = new JLabel();
		
		// Print Button
		JButton printRangeButton = new JButton("PRINT");
		printRangeButton.addActionListener( e -> {
			try {
				// Pasa la string en el cuadro de texto a la matriz, sin espacios
				labelPanel.reset();
				labelPanel.paintRange(textField.getText().replaceAll("\\s+", ""));
				
				int newSliderValue = LabelButton.getNumSelected();
				
				sliderPrintChange = true;
				slider.setValue(newSliderValue);
				double perc = newSliderValue / 1.69;
				labelPerc.setText(String.format("%.1f", perc) + "%");
				sliderPrintChange = false;
				
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});
		southPanel.add(printRangeButton);
		
		// Reset Button
		JButton resetButton = new JButton("RESET");
		resetButton.addActionListener( e -> {
			labelPanel.reset();
			slider.setValue(0); //Para que cuando se cambia el rango vuelva a estar en 0%
			
		});
		southPanel.add(resetButton);
		
		
		//Slider
		int minorTickDist = (int) Math.round(10 * 1.69);
		
		slider.setPaintTicks(true);
		slider.setMinorTickSpacing(minorTickDist);
		
		slider.setPaintTrack(true);
		slider.setMajorTickSpacing(minorTickDist * 5);
		
		labelPerc.setPreferredSize(new Dimension(40, 30));
		labelPerc.setText(slider.getValue() + "%"); //Valor inicial
		
		//Label de la seleccion de ranking
		JLabel labelRanking = new JLabel();
		labelRanking.setText("Ranking used:");
				
		//Para elegir que ranking usar
		Choice choice = new Choice();
		choice.add("Sklansky Chubukov");
		choice.add("Heads up Hold'em");
		
		//Crear matrices de los rankings
		
		
		slider.addChangeListener(new ChangeListener() { //Cambia el valor del label para que vaya con el slider
			@Override
			public void stateChanged(ChangeEvent e) {
				if(!sliderPrintChange) {
				double perc = slider.getValue() / 1.69;
				labelPerc.setText(String.format("%.1f", perc) + "%");
				labelPanel.redrawSk(slider.getValue()); //Deselecciona las manos que no sirven
				}
			}
	    });
		
		southPanel.add(slider);
		southPanel.add(labelPerc);
		southPanel.add(labelRanking);
		southPanel.add(choice);
		
		frame.add(mainPanel);
		mainPanel.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
