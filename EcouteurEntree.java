/*Cette classe a pour but lancer la fenetre Carte*/
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;

public class EcouteurEntree implements ActionListener{
	//déclaration d'un attibut de type fenetre
	Accueil acc;
	
	//definition du constructeur 
	public EcouteurEntree(Accueil a){
		acc = a;
	}
	
	//actionPerformed indique les instructions a executer au clic du bouton
    public void actionPerformed(ActionEvent e){
		Carte car = new Carte ();
		acc.setVisible (false);
	}
}


