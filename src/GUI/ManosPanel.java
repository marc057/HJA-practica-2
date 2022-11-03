package GUI;

import java.util.List;
import java.util.Map;

import javax.swing.*;
import aware.LabelAware;

public class ManosPanel extends JPanel implements LabelAware{

    private void setManosPanel(Map<String, List<String>> map) {
        for(String key : map.keySet()){
            
            JLabel tipoMano = new JLabel(key);
            tipoMano.setVisible(true);
            this.add(tipoMano);

            JProgressBar pBar = new JProgressBar(0, 20);
            pBar.setValue(map.get(key).size());
            pBar.setStringPainted(true);
            this.add(pBar);
            String cards = "";
            for (String card : map.get(key)) {
                if (!cards.equals("")) { cards += ","; }
                cards += card;
            }

            JLabel mano = new JLabel(cards);
            mano.setVisible(true);
            this.add(mano);
            
            
            //Falta string de los paneles con que mano es la que hace eso lol
        }
		
	}

	@Override
	public void sendSelectionChanged() { // marcos feo
		
	}

	@Override
	public void receiveSelectionChanged() {
		
	}

}
