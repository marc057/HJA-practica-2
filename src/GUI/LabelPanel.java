package GUI;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.swing.*;

import Rankings.Sklansky;

public class LabelPanel extends JPanel {
	
	//Constants:------------------------------------------------------------------
	private static final List<Character> CardChars = Arrays.asList('A', 'K', 'Q', 'J', 'T', '9', '8', '7', '6', '5', '4', '3', '2');
	private static final int NumLabels = 13*13;
	
	//Attributes:-----------------------------------------------------------------
	private LabelButton[][] lmatrix;
	private JTextField textField; // Instance of textField, to change the text when a button is pressed
	
	private Sklansky matrixSk = new Sklansky(); //Matriz con los valores de el ranking Sklansky
	List<Double> listSelected = new ArrayList<>(); //Guarda todas las manos que hay seleccionadas
	
	//Getters:---------------------------------------------------------------------
	public static String coordToString(int c) { return String.valueOf(CardChars.get(c)); }
	public static int charToCoord(char c) { return CardChars.indexOf(c); }
	
	//Constructor:------------------------------------------------------------------
	public LabelPanel(JTextField textField) {
	    this.setLayout(new GridLayout(13, 13));
	    this.textField = textField;
	    initLabelMatrix();
		this.setVisible(true);
	}
	
	//Setup:------------------------------------------------------------------------
	private void initLabelMatrix() {
		lmatrix = new LabelButton[13][13];
		
		for (int i = 0; i < 13; i++) {
			for (int j = 0; j < 13; j++) {
				lmatrix[i][j] = new LabelButton(i, j);
				lmatrix[i][j].addActionListener(listener);
				this.add(lmatrix[i][j]);
			}
		}
	}
	
	ActionListener listener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() instanceof LabelButton) {
            	LabelButton target = (LabelButton) e.getSource();
            	target.toggleSelect();
            	
            	textRange();
            	//TODO: Quitar este codigo:::::::::::::::::::::::
	            	int i = target.getI(), j = target.getJ();
	            	//Si despues de el toggle es amarillo lo añade a la lista de amarillos. Si deja de serlo lo quita
	            	if(lmatrix[i][j].getSelected())
	            		listSelected.add(matrixSk.getNum(i, j));
	            	else
	            		listSelected.remove(matrixSk.getNum(i, j));
            	//::::::::::::::::::::::::::::::::::::::::::::::::
            }
        }
    };
    
    public void textRange() {
    	textRangeEqual();
    	textRangeSuited();
    	textRangeOffsuited();
    }

	public void reset() {
		for (int i = 0; i < 13; i++) {
			for (int j = 0; j < 13; j++) {
				setSelect(i,j, false);
				listSelected.remove(matrixSk.getNum(i,j)); //Quita todas las manos de la lista de manos seleccionadas
			}
		}
	}
	
	private void toggleSelect(int x, int y) {
		lmatrix[x][y].toggleSelect();	
	}
	private void setSelect(int i, int j, boolean value) {
		LabelButton target = lmatrix[i][j];
		
		if (target.getSelected() != value) { target.toggleSelect(); }
	}
	
	private String printRangeLine(int m1, int m2) {
		if (m1 == -1)
			return "";
		if (m2 == -1) {
			return lmatrix[m1][m1].getText();
		}
		return "";
	}
	
	private void printRangeLine(int[] m1, int[] m2) {
	}
	
	private void textRangeEqual() {
		int marker1 = -1;
		int marker2 = -1;
		String rng = "";
		
		for (int i = 12; i >= 0; i--) {
			if (lmatrix[i][i].getSelected()) {
				if (marker1 == -1)
					marker1 = i;
				else
					marker2 = i;
			}
			else {
				rng += "," + printRangeLine(marker1, marker2);
				marker1 = -1;
				marker2 = -1;
			}
		}
		textField.setText(rng);
	}

	private void textRangeSuited() {
		// TODO Auto-generated method stub
		
	}

	private void textRangeOffsuited() {
		// TODO Auto-generated method stub
		
	}
	
	public void paintRange(String range) throws Exception {
		List<String> pairs = Arrays.asList(range.split(","));
		for (int i = 0; i < pairs.size(); i++) {	
			String p = pairs.get(i);
			
			// The length is > 4 if and ONLY IF the pair is like XX-XX 
			if (p.length() > 4) {
				paintBetween(p);
			}
			
			if (p.length() == 4) {
				paintSuperiorDiff(p.substring(0, 3));
			}
			
			if (p.length() == 3) {
				if (p.charAt(2) == '+')
					paintSuperiorEqual(p.substring(0, 2));
				else
					paintSingleSquare(p);
			}
			if (p.length() == 2) {
				paintSingleSquare(p);
			}
		}
	}
	
	private void paintSingleSquare(String pair) {
		int pos[] = stringToPos(pair);
		setSelect(pos[0], pos[1], true);
		
		listSelected.add(matrixSk.getNum(pos[0], pos[1])); //Añade la mano a la lista
		
	}
	
	private void paintSuperiorEqual(String pair) {
		int pos[] = stringToPos(pair);
		
		for (int i = pos[0]; i >= 0; i--) {
			setSelect(i, i, true);
			
			listSelected.add(matrixSk.getNum(i, i)); //Añade la mano a la lista
			
		}
	}
	
	private void paintSuperiorDiff(String pair) {
		int pos[] = stringToPos(pair);
		int sup;
		
		if (pos[0] < pos[1]) {
			sup = pos[0] + 1;
			for (int i = pos[1]; i >= sup; i--) {
				setSelect(pos[0], i, true);
				
				listSelected.add(matrixSk.getNum(pos[0], i)); //Añade la mano a la lista
				
			}
		}
		
		else {
			sup = pos[1] + 1;
			for (int i = pos[0]; i >= sup; i--) {
				setSelect(i, pos[1], true);
				
				listSelected.add(matrixSk.getNum(i, pos[1])); //Añade la mano a la lista
				
			}
		}
	}
	
	private void paintBetween(String pair) throws Exception {
		List<String> pairs = Arrays.asList(pair.split("-"));
		int pos1[];
		int pos2[];
		int tmp = 0;
		
		if (pairs.size() != 2) {
			throw new Exception("Paint bewtween exception: Number of args incorrect");
		}
		pos1 = stringToPos(pairs.get(0));
		pos2 = stringToPos(pairs.get(1));
		
		// If suited
		if (pos1[0] < pos1[1]) {
			
			// If not aligned, throw exception
			if (pos2[0] != pos1[0] || pos2[0] > pos2[1])
				throw new Exception("Paint bewtween exception: Elements not aligned");
			
			// If y coordinate of pos1 is greater than the one of pos2, we swap the elements
			if (pos1[1] > pos2[1]) {
				tmp = pos1[1];
				pos1[1] = pos2[1];
				pos2[1] = tmp;
			}
			
			for (int i = pos1[1]; i <= pos2[1]; i++) {
				setSelect(pos1[0], i, true);
				
				listSelected.add(matrixSk.getNum(pos1[0], i)); //Añade la mano a la lista
				
			}
		}
		
		// If off-suited
		if (pos1[0] > pos1[1]) {
			
			// If not aligned, throw exception
			if (pos2[1] != pos1[1] || pos2[0] < pos2[1])
				throw new Exception("Paint bewtween exception: Elements not aligned");
			
			// If x of pos1 is greater than the one of pos2, we swap the elements
			if (pos1[0] > pos2[0]) {
				tmp = pos1[0];
				pos1[0] = pos2[0];
				pos2[0] = tmp;
			}
			
			for (int i = pos1[0]; i <= pos2[0]; i++) {
				setSelect(i, pos1[1], true);
				
				listSelected.add(matrixSk.getNum(i, pos1[1])); //Añade la mano a la lista
				
			}
		}
	}
	
	private int[] stringToPos(String str) {
		int[] pos = {-1, -1};
		
		if (charToCoord(str.charAt(0)) > charToCoord(str.charAt(1)))
			str = swapPairString(str);
		
		if (str.length() == 3) {
			if (str.charAt(2) == 's') {
				pos[0] = charToCoord(str.charAt(0));
				pos[1] = charToCoord(str.charAt(1));
			}
			if (str.charAt(2) == 'o') {
				pos[1] = charToCoord(str.charAt(0));
				pos[0] = charToCoord(str.charAt(1));
			}
		}
		if (str.length() == 2) {
			pos[0] = charToCoord(str.charAt(0));
			pos[1] = pos[0];
		}
		return pos;
	}
	
	private static String swapPairString(String str) {
		// String are immutable, so we need a char array to swap the pairs
		char carr[] = str.toCharArray();
		char tmp;
		
		tmp = carr[0];
		carr[0] = carr[1];
		carr[1] = tmp;
		
		return new String(carr);
	}
	
	public Integer getSelectedPercentage(int percentage) {//Devuelve el numero de manos que hay que mantener seleccionadas
		
		double d = listSelected.size() * ((double) percentage) / 100;
		
		int num  = (int)Math.round(d);
		
		return num;
	}
	
	public void redrawSk(int n) { 
	//Mira todas las manos, si estan seleccionadas y si deberian seguir estandolo, si no las quita de select y si deberian estarlo las vuelve a poner
		
		Collections.sort(listSelected);
		Collections.reverse(listSelected); //Espero que ordenar esta lista no provoque nada malo xd
		
		List<Double> listSelectedNew = new ArrayList<>(); //Lista auxiliar que solo contiene los que van a quedarse, creo que esta está bien
		
		for(int i = 0; i < n; i++) {
			listSelectedNew.add(listSelected.get(i));
		}
		
		for (int i = 0; i < 13; i++) {
			for (int j = 0; j < 13; j++) {
				boolean newValue = listSelectedNew.contains(matrixSk.getNum(i, j));
				setSelect(i, j, newValue);			
			}
		}
		
		/*
		 FALTA:
		 que sea mas exacto cuando se quitan (en vez de round otra cosa)
		 
		 */
	}
	public double getListSelectedSize() {
		
		return listSelected.size();
	}
	
}
