package GUI;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JTextField;

import Miscelaneous.BoardToTextConverter;
import Miscelaneous.Changes;
import Miscelaneous.Constants;
import Rankings.Sklansky;
import aware.LabelAware;

public class LabelPanel extends JPanel {
	
	//Singleton:------------------------------------------------------------
	private static LabelPanel instance = null;
	
	public static LabelPanel getInstance() {
		if (instance == null) {
			instance = new LabelPanel();
		}
		return instance;
	}
	
	//Attributes:-----------------------------------------------------------------
	private LabelButton[][] lmatrix;
	
	private Sklansky matrixSk = new Sklansky(); //Matriz con los valores de el ranking Sklansky
	
	//Getters:---------------------------------------------------------------------
	public static String numToString(int c) { return String.valueOf(Constants.CardNumbers.get(c)); }
	public static int charToNum(char c) { return Constants.CardNumbers.indexOf(c); }
	public LabelButton[][] getMatrix(){ return lmatrix; }
	
	//Constructor:------------------------------------------------------------------
	public LabelPanel() {
	    this.setLayout(new GridLayout(13, 13));
	    this.setSize(new Dimension(700, 700));
	    this.setPreferredSize(new Dimension(700, 700));
	    initLabelMatrix();
		this.setVisible(true);
	}
	
	//Setup:------------------------------------------------------------------------
	private void initLabelMatrix() {
		lmatrix = new LabelButton[13][13];
		
		for (int i = 0; i < 13; i++) {
			for (int j = 0; j < 13; j++) {
				lmatrix[i][j] = new LabelButton(i, j, listener);
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
            	
            	Changes.updateRange();
            }
        }
    };

	public void reset() {
		for (int i = 0; i < 13; i++) {
			for (int j = 0; j < 13; j++) {
				setSelect(i,j, false);
			}
		}
	}
	
	private void setSelect(int i, int j, boolean value) {
		LabelButton target = lmatrix[i][j];
		
		target.setSelect(value);
	}
	    
	//Esto lee la matriz y actualiza el texto del rango corresponde
	//Lo separa en 3 partes (parejas, suited y offsuited) y luego une las tres partes
	//Añade comas para separar entre partes cuando son necesarias
	
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
		
	}
	
	private void paintSuperiorEqual(String pair) {
		int pos[] = stringToPos(pair);
		
		for (int i = pos[0]; i >= 0; i--) {
			setSelect(i, i, true);
			
		}
	}
	
	private void paintSuperiorDiff(String pair) {
		int pos[] = stringToPos(pair);
		int sup;
		
		if (pos[0] < pos[1]) {
			sup = pos[0] + 1;
			for (int i = pos[1]; i >= sup; i--) {
				setSelect(pos[0], i, true);
				
			}
		}
		
		else {
			sup = pos[1] + 1;
			for (int i = pos[0]; i >= sup; i--) {
				setSelect(i, pos[1], true);
			
			}
		}
	}
	
	private void paintBetween(String pair) throws Exception {
		List<String> pairs = Arrays.asList(pair.split("-"));
		int pos1[];
		int pos2[];
		int tmp = -1;
		
		if (pairs.size() != 2) {
			throw new Exception("Paint bewtween exception: Number of args incorrect");
		}
		pos1 = stringToPos(pairs.get(0));
		pos2 = stringToPos(pairs.get(1));
		
		// If equal
		if (pos1[0] == pos1[1]) {
			// We dont need to check if they are aligned.
			// We swap the elements if the first pair is lower on the table
			if (pos1[0] > pos2[0]) {
				tmp = pos1[0];
				pos1[0] = pos2[0];
				pos2[0] = tmp;
			}
			
			for (int i = pos1[0]; i <= pos2[0]; i++) {
				setSelect(i, i, true);
			}
		}
		
		// If suited
		else if (pos1[0] < pos1[1]) {
			
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
			
			}
		}
		
		// If off-suited
		else if (pos1[0] > pos1[1]) {
			
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
	
			}
		}
	}
	
	private int[] stringToPos(String str) {
		int[] pos = {-1, -1};
		
		if (charToNum(str.charAt(0)) > charToNum(str.charAt(1)))
			str = swapPairString(str);
		
		if (str.length() == 3) {
			if (str.charAt(2) == 's') {
				pos[0] = charToNum(str.charAt(0));
				pos[1] = charToNum(str.charAt(1));
			}
			if (str.charAt(2) == 'o') {
				pos[1] = charToNum(str.charAt(0));
				pos[0] = charToNum(str.charAt(1));
			}
		}
		if (str.length() == 2) {
			pos[0] = charToNum(str.charAt(0));
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
	
	//Se llama cuando el slider pide que se seleccionen las N mejores casillas
	//Segun el ranking seleccionado
	public void redrawSk(int n) { 
	//Mira todas las manos, si estan seleccionadas y si deberian seguir estandolo, si no las quita de select y si deberian estarlo las vuelve a poner
		List<Double> list = matrixSk.matrixToList();
		
		Collections.sort(list);
		Collections.reverse(list);
		
		List<Double> listSelectedNew = new ArrayList<>(); //Lista auxiliar que solo contiene los que van a quedarse, creo que esta está bien

		for(int i = 0; i < n; i++) {
			listSelectedNew.add(list.get(i));
		}
		for (int i = 0; i < 13; i++) {
			for (int j = 0; j < 13; j++) {
				boolean newValue = listSelectedNew.contains(matrixSk.getNum(i, j));
				setSelect(i, j, newValue);
				listSelectedNew.remove(matrixSk.getNum(i, j));
			}
		}
		
	}
}
