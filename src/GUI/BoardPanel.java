package GUI;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import Miscelaneous.Constants;


public class BoardPanel extends JPanel {
	//Attributes:----------------------------------------------------
	private BoardButton[][] boardButtons = new BoardButton[13][4];
	
	ActionListener listener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() instanceof BoardButton) {
            	BoardButton target = (BoardButton) e.getSource();
            	target.toggleSelect();
            }
        }
    };
	
	//Constructor:---------------------------------------------------
	public BoardPanel() {
	    this.setLayout(new GridLayout(13, 4));
	    this.setSize(new Dimension(200, 300));
	    this.setPreferredSize(new Dimension(200, 300));
		initBoardMatrix();
		this.setVisible(true);
	}

	public void reset() {
		for (int i = 0; i < 13; i++) {
			for (int j = 0; j < 4; j++) {
				boardButtons[i][j].setSelect(false);
			}
		}
	}

	//Setup:----------------------------------------------------------
	public void initBoardMatrix() {
		
		//Fill matrix of buttons
		for (int i = 0; i < 13; i++) {
			for (int j = 0; j < 4; j++) {
				//Add buttons to the matrix
				boardButtons[i][j] = new BoardButton(i,j, listener);
				this.add(boardButtons[i][j]);
			}
		}
	}
	
	
}
