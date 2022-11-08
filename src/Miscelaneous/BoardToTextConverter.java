package Miscelaneous;

import GUI.LabelButton;

public class BoardToTextConverter {
	private static LabelButton[][] lmatrix;
	
    public static String textRange(LabelButton[][] lmatrixIn) {
    	lmatrix = lmatrixIn;
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
	
	private static String textRangeEqual() {
		int marker1 = -1;
		int marker2 = -1;
		String rng = "";
		
		for (int i = 12; i >= 0; i--) { //empieza en la ULTIMA (22) y va a la PRIMERA (AA)
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

	private static String textRangeSuited() {
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

	private static String textRangeOffsuited() {
		int marker1 = -1;
		int marker2 = -1;
		String rng = "";
		
		for (int i = 11; i >= 0; i--) {
			for (int j = 12; j > i; j--) {
				//NOTA: j marca la fila e i la columna en esta funcion
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
	
	
	private static String printRangeLineEqual(int m1, int m2) {
		if (m1 == -1) //Si intervalo vacio: entonces string vacia
			return "";
		if (m2 == -1) //Si intervalo solo tiene una casilla: entonces string de la casilla
			return lmatrix[m1][m1].getText();
		if (m2 == 0) //Si intervalo termina en la carta mas alta
			return lmatrix[m1][m1].getText() + "+";
		//Si intervalo de almenos 2 elementos que no termina en AA
		return lmatrix[m2][m2].getText() + "-" + lmatrix[m1][m1].getText();
	}
	
	private static String printRangeLineSuited(int m1, int m2, int row) {
		if (m1 == -1)
			return "";
		if (m2 == -1) 
			return lmatrix[row][m1].getText();
		if (m2 == row + 1)
			return lmatrix[row][m1].getText() + "+";
		return lmatrix[row][m2].getText() + "-" + lmatrix[row][m1].getText();
	}
	
	private static String printRangeLineOffSuited(int m1, int m2, int column) {
		if (m1 == -1)
			return "";
		if (m2 == -1) 
			return lmatrix[m1][column].getText();
		if (m2 == column + 1)
			return lmatrix[m1][column].getText() + "+";
		return lmatrix[m2][column].getText() + "-" + lmatrix[m1][column].getText();
	}
}
