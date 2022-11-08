package GUI;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.List;
import java.util.Map;

import javax.swing.*;
import aware.LabelAware;

public class ManosPanel extends JPanel{
	
	private static ManosPanel instance = null;
	
	//Constructor:---------------------------------------------------
		public ManosPanel() {
			this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		    this.setSize(new Dimension(200, 200));
		    this.setPreferredSize(new Dimension(200, 200));
			this.setVisible(true);
		}

    public void setManosPanel(Map<String, List<String>> map) {
    	this.removeAll();
        for(String key : map.keySet()){
        	
        	if(key.equals("combinations"))
        		continue;
        	
            JLabel tipoMano = new JLabel(key);
            tipoMano.setVisible(true);
            this.add(tipoMano);

            JProgressBar pBar = new JProgressBar(0, 100);
            pBar.setValue(map.get(key).size());
            pBar.setString(String.valueOf(map.get(key).size()));
            pBar.setStringPainted(true);
            this.add(pBar);
            String cards = "";
            for (String card : map.get(key)) {
                if (!cards.equals("")) { cards += ","; }
                cards += card;
            }
            JTextArea mano = new JTextArea(cards);
            mano.setSize(new Dimension(100, 100));
            JScrollPane manoScroll = new JScrollPane(mano);
            manoScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
     
            manoScroll.setVisible(true);
            this.add(manoScroll);
            
        }
        this.updateUI();
	}
public static ManosPanel getInstance() {
	if(instance == null)
		instance = new ManosPanel();
	return instance;
}
 public void resetManosPanel() {
	 this.removeAll();
	 this.updateUI();
 }
}
