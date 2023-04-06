/*Le but de cette classe c'est lancer les methodes de la classe ImagePanel pour definir les lines a peindre ansi que les peindre*/
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class EcouteurOption implements ActionListener{
	private Carte car;
	private static int a; 
	
	//definition du constructeur 
	public EcouteurOption(Carte c){
		car = c;
	}
	
	//actionPerformed indique les instructions a executer au clic du bouton
    public void actionPerformed(ActionEvent e){
		car.getImagePanel().setCoordonnees(); 
		car.getImagePanel().peindre(); 
	}
}
