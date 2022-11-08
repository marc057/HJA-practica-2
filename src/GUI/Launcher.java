package GUI;
import java.awt.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Miscelaneous.Changes;

public class Launcher {
	
	private static Boolean sliderPrintChange = false;
	private static JSlider slider;
	private static JLabel labelPerc;
	
	
	public static void main(String[] args) {
		
		JFrame frame = new JFrame("FakeStove");
		frame.setSize(new Dimension(1400, 800));
		
		// MainPanel
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout(10, 10));
		
		// Panel containing the textfield, print button, and reset
		JPanel northPanel = new JPanel();
		northPanel.setLayout(new FlowLayout());
		mainPanel.add(northPanel, BorderLayout.PAGE_START);
		
		// Panel containing ranking selector 
		JPanel southPanel = new JPanel();
		southPanel.setLayout(new FlowLayout());
		mainPanel.add(southPanel, BorderLayout.PAGE_END);
		
		// TextField
		MyRangeText textField = MyRangeText.getInstance();
		textField.setPreferredSize(new Dimension(600, 30));
		northPanel.add(textField);

		//Slider solo declarado
		slider = new JSlider(0,169,0);//Min 0 Max 169 empieza en 0
		//Label con el porcentaje del slider
		labelPerc = new JLabel();

		// Panel containing the label matrix
		LabelPanel labelPanel = LabelPanel.getInstance();
		mainPanel.add(labelPanel, BorderLayout.WEST);
		
		//Manos Panel
		ManosPanel manosPanel = ManosPanel.getInstance();
		mainPanel.add(manosPanel, BorderLayout.CENTER); //????????????????????????????????
		//Board panel
		BoardPanel boardPanel = BoardPanel.getInstance();
		mainPanel.add(boardPanel, BorderLayout.EAST);
		
		
		// Print Button
		JButton printRangeButton = new JButton("PRINT");
		printRangeButton.addActionListener( e -> {
			try {
				// Pasa la string en el cuadro de texto a la matriz, sin espacios
				labelPanel.reset();
				
				labelPanel.paintRange(textField.getText().replaceAll("\\s+", ""));
				
				int newSliderValue = LabelButton.getNumSelectedS();
				
				setValueExternal(newSliderValue);
				
				
			} catch (Exception exc) {
				JOptionPane.showMessageDialog(frame, exc.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
			}
		});

		
		northPanel.add(printRangeButton);
		
		// Reset Button
		JButton resetButton = new JButton("RESET");
		resetButton.addActionListener( e -> {
			labelPanel.reset();
			boardPanel.reset();
			textField.setText("");
			ManosPanel.getInstance().resetManosPanel();
			slider.setValue(0); //Para que cuando se cambia el rango vuelva a estar en 0%
			
		});
		northPanel.add(resetButton);
		
		
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
				//textField.setText(labelPanel.textRange()); // Actualiza el cuadro de texto
				Changes.updateRange();
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
		
	public static void setValueExternal(int newSliderValue) {
		sliderPrintChange = true; //Para que no se raye al cambiar el valor de slider sin moverlo
		slider.setValue(newSliderValue);
		double perc = newSliderValue / 1.69;
		labelPerc.setText(String.format("%.1f", perc) + "%");
		sliderPrintChange = false;
	}
}
