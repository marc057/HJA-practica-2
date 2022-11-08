package GUI;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import Miscelaneous.Changes;

public class BoardPanel extends JPanel {
	
	//Singleton:---------------------------------------------------
	private static BoardPanel instance = null;
	public static BoardPanel getInstance() {
		if (instance == null) {
			instance = new BoardPanel();
		}
		return instance;
	}
	
	//Attributes:----------------------------------------------------
	private BoardButton[][] boardButtons = new BoardButton[13][4];
	ActionListener listener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() instanceof BoardButton) {
            	BoardButton target = (BoardButton) e.getSource();
            	target.toggleSelect();
            	
            	Changes.updateCombinations();            		
            }
        }
/*TODO!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 FALTA ACTUALIZAR PARA:
 -Rango tabla
 -Slider
 -Print
 -Reset
 
 Hay cosas que se siguen repitiendo cuando coges 4 o 5 cartas en board :D
 */

    };
	
	//Constructor:---------------------------------------------------
	public BoardPanel() {
	    this.setLayout(new GridLayout(13, 4));
	    this.setSize(new Dimension(200, 300));
	    this.setPreferredSize(new Dimension(200, 300));
		initBoardMatrix();
		this.setVisible(true);
	}
	
	//Setup:----------------------------------------------------------
	public void initBoardMatrix() {
		for (int i = 0; i < 13; i++) {
			for (int j = 0; j < 4; j++) {
				//Add buttons to the matrix
				boardButtons[i][j] = new BoardButton(i,j, listener);
				this.add(boardButtons[i][j]);
			}
		}
	}
	
	//Reset:------------------------------------------
	public void reset() {
		for (int i = 0; i < 13; i++) {
			for (int j = 0; j < 4; j++) {
				boardButtons[i][j].setSelect(false);
			}
		}
	}
	
	//Utility:----------------------------------------------------
	public String boardToString() {
		String bs = "";
		for (int i = 0; i < 13; i++) {
			for (int j = 0; j < 4; j++) {
				if(boardButtons[i][j].getSelected())
					bs += boardButtons[i][j].getText();
			}
		}
		return bs;
	}
}
