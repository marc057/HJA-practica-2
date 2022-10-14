package GUI;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

import javax.swing.*;
import javax.swing.border.LineBorder;

public class LabelPanel extends JPanel {
	
	private static final List<Character> CardChars = Arrays.asList('A', 'K', 'Q', 'J', 'T', '9', '8', '7', '6', '5', '4', '3', '2');
	private static final int NumLabels = 13*13;
	
	//Attributes:------------------------------
	private LabelButton[][] lmatrix;
	private int numSelected = 0;
	
	public LabelPanel() {
	    this.setLayout(new GridLayout(13, 13));
	    initLabelMatrix();
		this.setVisible(true);
	}
	
	private void initLabelMatrix() {
		lmatrix = new LabelButton[13][13];
		
		for (int i = 0; i < 13; i++) {
			for (int j = 0; j < 13; j++) {
				lmatrix[i][j] = new LabelButton(i, j);
				lmatrix[i][j].setBorder(new LineBorder(Color.BLACK));
				lmatrix[i][j].color();
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
            	toggleYellow(target.i, target.j);
            }
        }
    };
    
    public void reset() {
		for (int i = 0; i < 13; i++) {
			for (int j = 0; j < 13; j++) {
				unselect(i,j);
			}
		}
	}
	
	private void toggleYellow(int x, int y) {
		//Swap selection
		boolean newValue = lmatrix[x][y].toggleSelect();
		
		//Update stats
		numSelected += newValue ? 1 : -1;
	}
	
	private void select(int i, int j) {
		if (!lmatrix[i][j].selected) {
			toggleYellow(i, j);
		}
	}
	private void unselect(int i, int j) {
		if (lmatrix[i][j].selected) {
			toggleYellow(i,j);
		}
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
		select(pos[0], pos[1]);
	}
	
	private void paintSuperiorEqual(String pair) {
		int pos[] = stringToPos(pair);
		
		for (int i = pos[0]; i >= 0; i--) {
			select(i, i);
		}
	}
	
	private void paintSuperiorDiff(String pair) {
		int pos[] = stringToPos(pair);
		int sup;
		
		if (pos[0] < pos[1]) {
			sup = pos[0] + 1;
			for (int i = pos[1]; i >= sup; i--) {
				select(pos[0], i);
			}
		}
		
		else {
			sup = pos[1] + 1;
			for (int i = pos[0]; i >= sup; i--) {
				select(i, pos[1]);
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
				select(pos1[0], i);
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
				select(i, pos1[1]);
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
	
	public static String coordToString(int c) {
		return String.valueOf(CardChars.get(c));
	}
	
	public static int charToCoord(char c) {
		return CardChars.indexOf(c);
	}
	
	private String getSelectedPercentage() {
		double d = ((double) numSelected ) / NumLabels;
		
		return Double.toString(d);
	}
}
