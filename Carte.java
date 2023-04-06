import javax.swing.*;
import java.awt.*;
import java.util.*;
import javax.imageio.*;
import java.awt.image.BufferedImage;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;  
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.*; 
import java.io.*; 

//Cette classe a pour but de créer la deuxième fenetre visible de notre code
// Il s'agit de la carte, qui sert à se repérer sur le campus de la doua  
	
public class Carte extends JFrame {
	
	//Les conteneurs a utiliser
	private JPanel conteneurImage;
	private JPanel conteneurItineraire; 
	private JPanel conteneurPrincipal;
	
	//Les labels et buttons a utiliser 
	private JButton recherche;
	private JButton retirerOption; 
	private JButton validerOption; 
	private JButton choix1; 
	private JButton choix2; 
	private JButton validerLieux; 
	private JButton clearAll; 

	private JLabel aVisiter;
	private JLabel depart = new JLabel(""); 
	private JLabel arrivee = new JLabel ("");
	private JLabel labelLieux; 
	private JLabel labelItineraire; 
	private JLabel labelChoixManuel; 
	private JLabel affichCarte;
	private JCheckBox rainbowMode;
	
	
	//Les elements des listes qui vont gerer les lieux et les itineraires 
	private JList listeLieux; 
	private JList listeItineraire; 
	private String[] lesLieux; 
	private DefaultListModel<Object> model_lieux; 
	private LinkedList<String> itineraireList; 
	private DefaultListModel<Object> model_itineraire; 
		
	//Les elements "image"
	private ImageIcon imgCarte;
	private ImagePanel imgPane; //03
	
	//Variables de la classe 
	private boolean etatBtn1; 
	private boolean etatBtn2; 
	private Noeud noeudObjet; 
 
	//Variables final pour dimensioner les composants
	private final Dimension TAILLE_IMAGE = new Dimension(761,591); 
	private final int IMAGE_HEIGTH = 761; 
	private final int IMAGE_LENGTH = 591; 
	private final int ITINERAIRE_HEIGHT = 200; 
	private final Dimension CARTE_SIZE = new Dimension(1000,IMAGE_LENGTH+60); 
	private final Point ITINERAIRE_POSITION = new Point(IMAGE_LENGTH+180,0);
	private final int ITINERAIRE_LENGTH = 200; 
	
	//Le constructeur de la classe
	public Carte (){
		super ("Carte INSA LYON : trouve ton itinéraire");
		//methodes principales pour JFrame
		setSize(CARTE_SIZE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//Initialization du conteneur principal, on touve dedans le conteneur de l'image de la carte et le conteneur de l'itineraire
		conteneurPrincipal = new JPanel();
		conteneurPrincipal.setBackground(new Color(179,232,176));
		conteneurPrincipal.setLayout(null); 
		
		//Initialization du conteneurItinieraire
		conteneurItineraire = new JPanel();
 
		//Initialization du conteneur de l'image de la carte
		conteneurImage = new JPanel(); 
		conteneurImage.setSize(TAILLE_IMAGE); 
		
		etatBtn1 = false; 
		etatBtn2 = false; 

		
		//Composants pour la liste des lieux
		itineraireList = new LinkedList<String>(); 		
		this.setLieux(); //methode qui "set" la liste statique des lieux, a partir du fichier txt
		model_lieux = new DefaultListModel<Object>(); 
		
		for (String l : lesLieux){
			model_lieux.addElement(l); 
		}
		
		listeLieux = new JList<Object>();	
		labelLieux = new JLabel("Lieux phares de l'INSA"); 
		labelLieux.setAlignmentX(CENTER_ALIGNMENT); 
		listeLieux.setVisibleRowCount(10); 
		listeLieux.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listeLieux.setFixedCellWidth(ITINERAIRE_HEIGHT-30); 
		listeLieux.setModel(model_lieux); 	
		JScrollPane lieuxScroller = new JScrollPane(); 
		lieuxScroller.setViewportView(listeLieux); 
		
		validerLieux = new JButton("Valider lieux"); 
		validerLieux.setPreferredSize(new Dimension(ITINERAIRE_LENGTH,25)); 
		
		//Méthode pour selectioner un lieu, une fois il est selectionné on l'enleve du model de la liste
		validerLieux.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				listeLieux.getSelectedValuesList().stream().forEach((data) ->{
					model_itineraire.addElement(data); 
					model_lieux.removeElement(data); 
					itineraireList.add((String)data);  
					imgPane.setItinerairePanel(getItineraire()); 
					imgPane.setNbLieux(itineraireList.size()); 
				});
			}
		});
		
		//Composants pour l'itineraire 
		listeItineraire = new JList<Object>(); 
		model_itineraire = new DefaultListModel<Object>(); 
		listeItineraire.setModel(model_itineraire); 
		JScrollPane itiScroller = new JScrollPane();
		itiScroller.setViewportView(listeItineraire);
		
		labelItineraire = new JLabel("Votre Itineraire"); 
		labelItineraire.setAlignmentX(CENTER_ALIGNMENT); 
		listeItineraire.setVisibleRowCount(10); 
		listeItineraire.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); 
		listeItineraire.setFixedCellWidth(ITINERAIRE_HEIGHT-30); 
		
		retirerOption = new JButton("Retirer option"); 
		retirerOption.setPreferredSize(new Dimension(ITINERAIRE_LENGTH,25)); 
		
		//On ajoute un listener pour pouvoir enlever un lieu dont on ne veut plus aller, on le re-ajoute dans la liste des lieux disponibles
		retirerOption.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				listeItineraire.getSelectedValuesList().stream().forEach((data) ->{
				model_itineraire.removeElement(data); 
				model_lieux.addElement(data); 
				}); 
			}		
		});
		
		//Labels et buttons de l'interface
		BufferedImage imgCarte;
		noeudObjet = new Noeud(); 
		recherche = new JButton ("Rechercher"); 	
		labelChoixManuel = new JLabel("Valider mon choix manuellement"); 
		labelChoixManuel.setFont(new java.awt.Font(Font.SERIF,Font.ITALIC,14));
		
		validerOption = new JButton("Valider mon choix"); 
		choix1 = new JButton("Choix 1"); 
		choix2 = new JButton("Choix 2"); 
		aVisiter = new JLabel (" appeler les lieux ou on clique");
		recherche.setBackground(new Color(255,120,32));
		recherche.setFont(new java.awt.Font(Font.SERIF,Font.ITALIC,18));
		
		clearAll = new JButton("Tout effacer"); 
		
		rainbowMode = new JCheckBox("RainBowMode"); 
		rainbowMode.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent event) {
			JCheckBox cb = (JCheckBox) event.getSource();
			imgPane.setRainBowMode(cb.isSelected()); 
			}
		});
		
		
		//Try catch pour le choix mannuelle depuis un click dans la carte 
		try{
			try {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			} 
			catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
			}
			
			imgCarte = ImageIO.read(new File("CampusINSA.jpg"));
			imgPane = new ImagePanel(imgCarte);
			imgPane.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					//l'etat du button nous dit s'il a déjà été cliqué, on souhiate diferencier entre le entre le click 1 qui sets le lieux du depart, 
					//et le click 2, qui sets le lieu d'arrivé, les deux btn sont inizialisees comme false
					if (etatBtn1){
						//L'utilisateur veut definir le lieu de depart/debut
						imgPane.setPointDebut(new Point(e.getPoint().x,e.getPoint().y)); 
						itineraireList.add(noeudObjet.getNomNoeudPres(new Point(e.getPoint().x,e.getPoint().y))); 
						model_itineraire.addElement(noeudObjet.getNomNoeudPres(new Point(e.getPoint().x,e.getPoint().y))); 
						depart.setText("Point debut = "+noeudObjet.getNbNoeudPres(imgPane.getPointDebut())); 
						imgPane.setIdDepart(); 
						etatBtn1 = false;	
						//une fois que l'utilisateur a fait son choix, on definis la valeur du btn comme false, l'utilisateur ne peut donc
						//pas continuer a definir le lieu du depart, sauf si il re-clique sur le btn
					} else if (etatBtn2){
						//L'utilisateur veut definir le lieu d'arrivé 
						imgPane.setPointFin(new Point(e.getPoint().x,e.getPoint().y)); 
						arrivee.setText("Point fin =  "+noeudObjet.getNbNoeudPres(imgPane.getPointFin())); 
						itineraireList.add(noeudObjet.getNomNoeudPres(new Point(e.getPoint().x,e.getPoint().y))); 
						model_itineraire.addElement(noeudObjet.getNomNoeudPres(new Point(e.getPoint().x,e.getPoint().y))); 
						imgPane.setIdFin(); 
						etatBtn2 = false; 
						//Même loguique que dans le btn1
					}
				}
			});				
		} catch (IOException ex) {
			Logger.getLogger(Carte.class.getName()).log(Level.SEVERE, null, ex);
		}

		
		imgPane.setBounds(0,0,1000,1000); 
		conteneurImage.add(imgPane); 
		
		conteneurItineraire.setBounds(ITINERAIRE_POSITION.x,ITINERAIRE_POSITION.y,ITINERAIRE_LENGTH, IMAGE_HEIGTH); 
		conteneurItineraire.add(labelLieux); 
		conteneurItineraire.add(lieuxScroller);
		conteneurItineraire.add(validerLieux); 
		conteneurItineraire.add(labelItineraire); 
		conteneurItineraire.add(itiScroller); 
		conteneurItineraire.add(retirerOption); 
		conteneurItineraire.add(labelChoixManuel); 
		
		conteneurItineraire.add(choix1); 
		conteneurItineraire.add(choix2); 
		conteneurItineraire.add(validerOption); 
		conteneurItineraire.add(rainbowMode); 
		conteneurItineraire.add(clearAll); 
		
		conteneurPrincipal.add(conteneurImage); 
		conteneurPrincipal.add(conteneurItineraire); 
	
		//ajout dans la fenetre
		this.add(conteneurPrincipal);
		
		//on lie aux écouteurs
		validerOption.addActionListener(new EcouteurOption(this)); 
		choix1.addActionListener(new EcouteurChoixUn(this,choix1)); 
		choix2.addActionListener(new EcouteurChoixUn(this,choix2));
		clearAll.addActionListener(new EcouteurClearAll(this)); 
		this.setResizable(false);
		setVisible (true); 
	}

	
	//getters
	public ImagePanel getImagePanel(){
		return imgPane; 
	}
	public boolean getEtat1(){
		return etatBtn1; 
	}
	public boolean getEtat2(){
		return etatBtn2; 
	}
	public LinkedList<String> getItineraire(){
		return itineraireList; 
	}
	public DefaultListModel<Object> getModelLieux(){
		return model_lieux; 
	}
	public DefaultListModel<Object> getModelItineraire(){
		return model_itineraire; 
	}
	
	//setters
	public void setTextDepart(String s ){
		depart.setText(s); 
	}
	public void setTextArrive(String s ){
		arrivee.setText(s); 
	}
	public void setEtat1(boolean a){
		etatBtn1 = a; 
	}
	public void setEtat2(boolean a){
		etatBtn2 = a; 
	}
	public void setLieux(){
		lesLieux = new String[59]; 
		try{
			BufferedReader reader = new BufferedReader(new FileReader(new File("ListeLieux.txt"))); 
			String ligne; 
			int i = 0; 
			while (reader!= null && (ligne = reader.readLine()) != null){ 
				String str = ligne; 
				String[] arr = str.split(",",4); 
				lesLieux[i] = arr[1]; 
				i++; 
			}	
		}
		catch(Exception e){
			System.err.println("Exception ff " +e.getMessage()); 
		} 
	}
}
	

