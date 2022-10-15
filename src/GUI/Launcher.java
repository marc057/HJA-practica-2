package GUI;
import java.awt.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Rankings.Sklansky;

public class Launcher {

	public static void main(String[] args) {
		JFrame frame = new JFrame("Frame name");
		frame.setSize(new Dimension(800, 800));
		
		// MainPanel
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		
		// Panel containing the label matrix
		LabelPanel labelPanel = new LabelPanel();
		mainPanel.add(labelPanel, BorderLayout.CENTER);
		
		// Panel containing the textfield, print button, ranking selector and reset
		JPanel southPanel = new JPanel();
		southPanel.setLayout(new FlowLayout());
		mainPanel.add(southPanel, BorderLayout.PAGE_END);
		
		// TextField
		JTextField textField = new JTextField();
		textField.setPreferredSize(new Dimension(120, 30));
		southPanel.add(textField);
		
		//Slider solo declarado
		JSlider slider = new JSlider(0,100,100);//Min 0 Max 100 empieza en 100
		
		// Print Button
		JButton printRangeButton = new JButton("PRINT");
		printRangeButton.addActionListener( e -> {
			try {
				// Pasa la string en el cuadro de texto a la matriz, sin espacios
				labelPanel.paintRange(textField.getText().replaceAll("\\s+", ""));
				slider.setValue(100); //Para que cuando se cambia el rango vuelva a estar en 100%
				
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		southPanel.add(printRangeButton);
		
		// Reset Button
		JButton resetButton = new JButton("RESET");
		resetButton.addActionListener( e -> {
			labelPanel.reset();
		});
		southPanel.add(resetButton);
		
		
		//Slider
		slider.setPaintTicks(true);
		slider.setMinorTickSpacing(10);
		
		slider.setPaintTrack(true);
		slider.setMajorTickSpacing(25);
		
		slider.setPaintLabels(true);
		
		//Label con el porcentaje del slider
		JLabel labelPerc = new JLabel();
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
		Sklansky matrixSk = new Sklansky();
		
		slider.addChangeListener(new ChangeListener() { //Cambia el valor del label para que vaya con el slider
			@Override
			public void stateChanged(ChangeEvent e) {
				labelPerc.setText(slider.getValue() + "%");
				//Aqui faltaria que la matriz cambie al mover el slider
			}
	    });
		
		/*
		 Dudas actuales:
		 Como asignar a cada casilla un valor distinto en cada ranking
		 Como calcular con el porcentaje que casillas se mantienen en amarillo y cuales no
		 
		 Tiene una seleccion de casillas en amarillo, tiene que calcular cuantas de esas casillas pertenecen al porcentaje dado (coger el x% mas alto)
		 
		 Estructura:
		 	fors que traversen toda la matriz
		 	Si color == selected && Con nuevo porcentaje no debe
		 	toggleSelect()
		 	
		 */
		
		southPanel.add(slider);
		southPanel.add(labelPerc);
		southPanel.add(labelRanking);
		southPanel.add(choice);
		
		frame.add(mainPanel);
		mainPanel.setVisible(true);
		frame.setVisible(true);
	}
}
