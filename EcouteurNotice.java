/*Cette classe a pour but lancer la fenetre Carte*/
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;

public class EcouteurNotice implements ActionListener{
	//déclaration d'un attibut de type fenetre
	Accueil acc; 
	
	
	//definition du constructeur 
	public EcouteurNotice(Accueil a){
		acc = a;
	}
	
	//actionPerformed indique les instructions a executer au clic du bouton
    public void actionPerformed(ActionEvent e){
		Notice notice = new Notice();
		
	}
}
