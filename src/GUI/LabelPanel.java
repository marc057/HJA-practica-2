package GUI;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

import javax.swing.*;
import javax.swing.border.LineBorder;

public class LabelPanel extends JPanel {
	
	//Constants:------------------------------------------------------------------
	private static final Color UnselectedOffsuit = new Color(191, 205, 211); //Bluish grey
	private static final Color UnselectedSuited = new Color(212, 131, 126); //Red
	private static final Color UnselectedPair = new Color(186, 255, 152); //Green
	private static final Color Selected = new Color(252, 255, 0); //Yellow
	
	//Attributes:------------------------------
	private JButton[][] lmatrix;
	
	public LabelPanel() {
	    this.setLayout(new GridLayout(13, 13));
	    initLabelMatrix();
		this.setVisible(true);
	}
	
	private void initLabelMatrix() {
		lmatrix = new JButton[13][13];
		
		for (int i = 0; i < 13; i++) {
			for (int j = 0; j < 13; j++) {
				lmatrix[i][j] = new JButton(posToString(i, j));
				lmatrix[i][j].setBorder(new LineBorder(Color.BLACK));
				if (i > j)
					lmatrix[i][j].setBackground(UnselectedOffsuit);
				if (j > i)
					lmatrix[i][j].setBackground(UnselectedSuited);
				if (i == j)
					lmatrix[i][j].setBackground(UnselectedPair);
				lmatrix[i][j].addActionListener(listener);
				
				this.add(lmatrix[i][j]);
			}
		}
	}
	
	ActionListener listener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() instanceof JButton) {
            	int pos[] = stringToPos(((JButton) e.getSource()).getText());
            	toggleYellow(pos[0], pos[1]);
            }
        }
    };
    
    public void reset() {
		for (int i = 0; i < 13; i++) {
			for (int j = 0; j < 13; j++) {
				if (lmatrix[i][j].getBackground().equals(Selected))
					toggleYellow(i, j);
			}
		}
	}
	
	private void toggleYellow(int x, int y) {
		Color Y = Selected;
		// If the label is yellow
		if (lmatrix[x][y].getBackground().equals(Y)) {
			if (x > y)
				lmatrix[x][y].setBackground(UnselectedOffsuit);
			if (x < y)
				lmatrix[x][y].setBackground(UnselectedSuited);
			if (x == y)
				lmatrix[x][y].setBackground(UnselectedPair);
		}
		else
			lmatrix[x][y].setBackground(Selected);
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
		lmatrix[pos[0]][pos[1]].setBackground(Selected);
	}
	
	private void paintSuperiorEqual(String pair) {
		int pos[] = stringToPos(pair);
		
		for (int i = pos[0]; i >= 0; i--) {
			lmatrix[i][i].setBackground(Selected);
		}
	}
	
	private void paintSuperiorDiff(String pair) {
		int pos[] = stringToPos(pair);
		int sup;
		
		if (pos[0] < pos[1]) {
			sup = pos[0] + 1;
			for (int i = pos[1]; i >= sup; i--) {
				lmatrix[pos[0]][i].setBackground(Selected);
			}
		}
		
		else {
			sup = pos[1] + 1;
			for (int i = pos[0]; i >= sup; i--) {
				lmatrix[i][pos[1]].setBackground(Selected);
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
				lmatrix[pos1[0]][i].setBackground(Selected);
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
				lmatrix[i][pos1[1]].setBackground(Selected);
			}
		}
	}
	
	private String posToString(int x, int y) {
		String text = "";
		text = text.concat(coordToString(x)).concat(coordToString(y));
		if (y > x)
			return text.concat("s");
		if (x > y)
			return text.concat("o");
		return text;
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
	
	private String swapPairString(String str) {
		// String are immutable, so we need a char array to swap the pairs
		char carr[] = str.toCharArray();
		char tmp;
		
		tmp = carr[0];
		carr[0] = carr[1];
		carr[1] = tmp;
		
		return new String(carr);
	}
	
	private String coordToString(int c) {
		switch (c) {
		case 0:
			return "A";
		case 1:
			return "K";
		case 2:
			return "Q";
		case 3:
			return "J";
		case 4:
			return "T";
		case 5:
			return "9";
		case 6:
			return "8";
		case 7:
			return "7";
		case 8:
			return "6";
		case 9:
			return "5";
		case 10:
			return "4";
		case 11:
			return "3";
		case 12:
			return "2";
		default:
			return "E";
		}
	}
	
	private int charToCoord(char c) {
		switch (c) {
		case 'A':
			return 0;
		case 'K':
			return 1;
		case 'Q':
			return 2;
		case 'J':
			return 3;
		case 'T':
			return 4;
		case '9':
			return 5;
		case '8':
			return 6;
		case '7':
			return 7;
		case '6':
			return 8;
		case '5':
			return 9;
		case '4':
			return 10;
		case '3':
			return 11;
		case '2':
			return 12;
		default:
			return -1;
		}
	}
}
