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

import Rankings.Sklansky;
import aware.LabelAware;

public class LabelPanel extends JPanel implements LabelAware {
	
	//Constants:------------------------------------------------------------------
	private static final List<Character> CardChars = Arrays.asList('A', 'K', 'Q', 'J', 'T', '9', '8', '7', '6', '5', '4', '3', '2');
	private static final int NumLabels = 13*13;
	
	//Attributes:-----------------------------------------------------------------
	private LabelButton[][] lmatrix;
	private JTextField textField; // Instance of textField, to change the text when a button is pressed
	
	private Sklansky matrixSk = new Sklansky(); //Matriz con los valores de el ranking Sklansky
	
	//Progressbar
	
	//Getters:---------------------------------------------------------------------
	public static String coordToString(int c) { return String.valueOf(CardChars.get(c)); }
	public static int charToCoord(char c) { return CardChars.indexOf(c); }
	public LabelButton[][] getMatrix(){ return lmatrix; }
	//Constructor:------------------------------------------------------------------
	public LabelPanel(JTextField textField) {
	    this.setLayout(new GridLayout(13, 13));
	    this.setSize(new Dimension(700, 700));
	    this.setPreferredSize(new Dimension(700, 700));
	    this.textField = textField;
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
            	
            	textField.setText(textRange());
            	Launcher.setValueExternal(LabelButton.getNumSelectedS());
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
    public String textRange() {
    	String rng = "";
    	String rEqual = textRangeEqual();
    	String rSuited = textRangeSuited();
    	String rOffSuited = textRangeOffsuited();
    	
    
    	rng += rEqual;
    	
    	if (rng.length() > 0 && rSuited.length() > 0)
    		rng += ",";
    	
    	rng += rSuited;
    	
    	if (rSuited.length() > 0 && rOffSuited.length() > 0)
    		rng += ",";
    	
    	rng += rOffSuited;
    	
		return rng;
    }
	
	private String printRangeLineEqual(int m1, int m2) {
		if (m1 == -1)
			return "";
		if (m2 == -1)
			return lmatrix[m1][m1].getText();
		if (m2 == 0)
			return lmatrix[m1][m1].getText() + "+";
		return lmatrix[m2][m2].getText() + "-" + lmatrix[m1][m1].getText();
	}
	
	private String printRangeLineSuited(int m1, int m2, int row) {
		if (m1 == -1)
			return "";
		if (m2 == -1) 
			return lmatrix[row][m1].getText();
		if (m2 == row + 1)
			return lmatrix[row][m1].getText() + "+";
		return lmatrix[row][m2].getText() + "-" + lmatrix[row][m1].getText();
	}
	
	private String printRangeLineOffSuited(int m1, int m2, int column) {
		if (m1 == -1)
			return "";
		if (m2 == -1) 
			return lmatrix[m1][column].getText();
		if (m2 == column + 1)
			return lmatrix[m1][column].getText() + "+";
		return lmatrix[m2][column].getText() + "-" + lmatrix[m1][column].getText();
	}
	
	private String textRangeEqual() {
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
			if (i == 0 || !lmatrix[i][i].getSelected()) {
				rng += (!rng.equals("") && marker1 != -1 ? "," : "");
				rng += printRangeLineEqual(marker1, marker2);
				marker1 = -1;
				marker2 = -1;
			}
		}
		return (rng);
	}

	private String textRangeSuited() {
		int marker1 = -1;
		int marker2 = -1;
		String rng = "";
		
		for (int i = 11; i >= 0; i--) {
			for (int j = 12; j > i; j--) {
				if (lmatrix[i][j].getSelected()) {
					if (marker1 == -1)
						marker1 = j;
					else
						marker2 = j;
				}
				if (j == i + 1 || !lmatrix[i][j].getSelected()) {
					rng += (!rng.equals("") && marker1 != -1 ? "," : "");
					rng += printRangeLineSuited(marker1, marker2, i);
					marker1 = -1;
					marker2 = -1;
				}
			}
		}
		
		return rng;
	}

	private String textRangeOffsuited() {
		int marker1 = -1;
		int marker2 = -1;
		String rng = "";
		
		for (int i = 11; i >= 0; i--) {
			for (int j = 12; j > i; j--) {
				if (lmatrix[j][i].getSelected()) {
					if (marker1 == -1)
						marker1 = j;
					else
						marker2 = j;
				}
				if (j == i + 1 || !lmatrix[j][i].getSelected()) {
					rng += (!rng.equals("") && marker1 != -1 ? "," : "");
					rng += printRangeLineOffSuited(marker1, marker2, i);
					marker1 = -1;
					marker2 = -1;
				}
			}
		}
		
		return rng;
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
	@Override
	public void sendSelectionChanged() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void receiveSelectionChanged() {
		// TODO Auto-generated method stub
		
	}
}
