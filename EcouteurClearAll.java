
import java.awt.event.*;
import javax.swing.*;

public class EcouteurClearAll implements ActionListener{
	private Carte car;
	private String text; 
	private JButton btn;
	
	//definition du constructeur 
	public EcouteurClearAll(Carte c){
		car = c;
	}
	
	//actionPerformed indique les instructions a executer au clic du bouton
	//ici, selection d'un lieu pour le départ et/ou l'arrivée
    public void actionPerformed(ActionEvent e){
		car.setEtat1(false); 
		car.setEtat2(false); 
		for (int i = 0; i < car.getModelItineraire().getSize(); i++) {
            car.getModelLieux().addElement(String.valueOf(car.getModelItineraire().getElementAt(i)));
        }
		car.getModelItineraire().removeAllElements(); 
		car.getItineraire().clear(); 
		car.getImagePanel().getListePointsDij().clear(); 
	}	
}
