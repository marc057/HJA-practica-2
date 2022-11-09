package GUI;
import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Miscelaneous.Changes;
import Rankings.*;

public class Launcher {
	
	private static Boolean sliderPrintChange = false;
	private static JSlider slider;
	private static JLabel labelPerc;
	
	private static Map<String, Ranking> rankings = new HashMap<>();	
	
	public static void main(String[] args) {
		
		//Initialization:------
		rankings.put("Sklansky ranking", new Sklansky());
		rankings.put("Pushbot small blind", new PushbotSmallBlind());
		rankings.put("Pushbot big blind", new PushbotBigBlind());
		//--------------------
		
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
		mainPanel.add(manosPanel, BorderLayout.CENTER);
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
				
				Changes.updateCombinations();
				
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
			Changes.updateRange();
			
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
		JComboBox<String> choice = new JComboBox<String>();
		for(String key : rankings.keySet()) {
			choice.addItem(key);
		}
		choice.addActionListener(
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						Ranking selectedRanking = rankings.get(choice.getSelectedItem() );
						labelPanel.setRanking(selectedRanking);
					}
				}
			);
		
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
