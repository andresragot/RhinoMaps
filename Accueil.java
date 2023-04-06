/* Le but de cette classe c'est d'ouvrir une fenetre de acceuil pour l'utilisateur, a l'aide de l'ecouteur sur le btnEntree, elle lance ensuite 
 * la classe carte */
 
import javax.swing.*;
import java.awt.*;
import javax.imageio.*;

public class Accueil extends JFrame{
	
	JPanel conteneurBas; 
	JPanel conteneurCentral; 
	private JButton btnEntree;
	private JButton btnNotice; 
	private ImageIcon imgIcon1;
	private JLabel jl;	
	private Notice notice; 

	
	public Accueil (){
		super ("RHINO-MAPS");
		setSize(1000,600);

		btnEntree = new JButton ("C'est parti !");
		btnNotice = new JButton ("Comment ca marche"); 
		ImageIcon imgIcon1 = new ImageIcon("logo.jpg");
		jl = new JLabel(imgIcon1);
		
		//creation du conteneur principal pour mettre les onjets graphiques 
		conteneurCentral = new JPanel (new BorderLayout());
		conteneurBas = new JPanel(); 
		conteneurBas.setBackground(new Color(52,146,123));
		

		conteneurCentral.add(conteneurBas, BorderLayout.SOUTH);
		conteneurCentral.add(jl);
		conteneurBas.add(btnEntree);
		conteneurBas.add(btnNotice); 
		
		btnEntree.addActionListener(new EcouteurEntree(this));
		btnNotice.addActionListener(new EcouteurNotice(this)); 
		
		this.add(conteneurCentral);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible (true); 
	}
	public Notice getNotice(){
		return notice; 
	}


}
