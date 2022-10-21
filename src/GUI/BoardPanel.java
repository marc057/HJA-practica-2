package GUI;

import java.awt.GridLayout;

import javax.swing.JPanel;

import Miscelaneous.Constants;

public class BoardPanel extends JPanel {
	//Attributes:----------------------------------------------------
	private BoardButton[][] boardButtons = new BoardButton[13][4];
	
	//Constructor:---------------------------------------------------
	public BoardPanel() {
	    this.setLayout(new GridLayout(13, 4));
		initBoardMatrix();
		this.setVisible(true);
	}
	
	//Setup:----------------------------------------------------------
	public void initBoardMatrix() {
		//Fill matrix of buttons
		for (int i = 0; i < 13; i++) {
			for (int j = 0; j < 4; j++) {
				//Add buttons to the matrix
				boardButtons[i][j] = new BoardButton(i,j, boardButtonListener);
			}
		}
	}
}