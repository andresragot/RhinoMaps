import javax.swing.*;
import java.awt.*;
import java.util.*;
import javax.imageio.*;
import java.io.*;

//Cette classe a pour but de créer la fenêtre d'explication de Rhino Maps

public class Notice extends JFrame{
	private  JPanel nouveau; 
	private JTextArea notice;
	
	public Notice (){
		super ("Petit guide pour voyager dans Rhino Maps...");
		
		setSize(650,450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
        
        notice = new JTextArea ("		FONCTIONNEMENT DE LA CARTE : \n\nPour vous promener sur le merveilleux campus de l'INSA, vous pouvez choisir parmi \ndeux methodes selon vos besoins :\n\n	- L'une vous permet d'aller d'un lieu a un autre sans halte. Pour l'utiliser, \ncliquez sur ''Choix 1'' puis sur votre lieu de depart sur la carte puis sur ''Choix 2'' et sur \nvotre lieu d'arrivee sur la carte. Cliquez ensuite sur ''Valider'' et votre itineraire s'affiche \nsur la carte !!\n\n	- La seconde methode vous sera utile si jamais vous avez plus de deux \nlieux a visiter dans votre journee. Elle calcule le chemin le plus court passant par tous \nles lieux choisis. Pour l'utiliser, cliquez dans le menu deroulant sur votre point de depart\npuis sur ''Valider lieux'' et ainsi de suite pour tous les lieux souhaites. Si vous souhaitez \nretirer une etape, il vous suffit simplement de cliquer sur ''Retirer l'option''.\nEnsuite, lorsque votre choix est complet, cliquez sur ''Valider mon choix''\n\nBon trajet avec Rhino Maps !");
      
        notice.setEditable(false);
		nouveau = new JPanel(); 
        nouveau.add(notice);
        nouveau.setBackground(new Color(52,146,123));
        notice.setBackground (new Color(52,146,123));
        notice.setFont(new java.awt.Font("Calibri",Font.BOLD,16));
        notice.setForeground(Color.BLACK);
        this.add(nouveau);   
        this.setVisible(true); 

	}
        public static void main (String[] args){
			new Notice();
		} 
		
}
