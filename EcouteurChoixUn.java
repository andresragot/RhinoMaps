/*Le but de cet ecouteur c'est de activer les btn des choix, pour que l'utilisateur puisse choisir manuellement un lieu ou il veut aller*/

import java.awt.event.*;
import javax.swing.*;

public class EcouteurChoixUn implements ActionListener{
	private Carte car;
	private String text; 
	private JButton btn;
	
	//definition du constructeur 
	public EcouteurChoixUn(Carte c, JButton btn){
		car = c;
		this.btn = btn; 
		text = btn.getText(); 
	}
	
	//actionPerformed indique les instructions a executer au clic du bouton
	//ici, selection d'un lieu pour le départ et/ou l'arrivée
    public void actionPerformed(ActionEvent e){
		if (text == "Choix 1"){
			car.setEtat1(true); 			
		}else if (text == "Choix 2"){
			car.setEtat2(true); 
		}
	}	
}
