/*Le but de cette classe est de trouver le chemin ideal entre une liste des lieux a parcourir
 * la liste n'est pas ordonnée par priorité, il faut juste que le premier élément de la liste soit le point de départ*/

import java.util.*; 
import java.io.*; 

public class Itineraire{
	private ArrayList<Point> lesLieux; //liste des lieux où on veut aller, créée comme une liste de points
	private LinkedList<String> lesLieuxNoms; //liste des lieux comme liste de Strings avec les noms 
	private ArrayList<Point> lesLieuxOrdonnes; //Liste de points ordonnée pour avoir le chemin ideal avec la distance plus petite entre eux
	private LinkedList<String> lesLieuxNomsOrdonnes; //Liste des Strings ordonnée pour avoir la distance la plus petite
	private ArrayList<Integer> lesIntegersDijkstra; //Liste ordonnee selon les numero du Noeud 
	private ArrayList<Point> lesPointsDijkstra = new ArrayList<Point>(); //Liste des points cree utilizant Dijkstra 
	private Dijkstra dij = new Dijkstra(); //Classe Dijkstra pour trouver les chemins entre les noeuds 
	private double distanceTotale = 0; //variable pour determiner la distance totale parcourue
	
	public Itineraire(ArrayList<Point> uneListe){
		lesLieux = uneListe;
		this.setItineraire(); 
		this.setPointsDijkstra(); 
 
	}
	
	//surcharge du constructeur
	public Itineraire(LinkedList<String> listeString){
		lesLieuxNoms = listeString; 
		lesLieuxNomsOrdonnes = new LinkedList<String>(); 
		this.stringToInteger(lesLieuxNoms);
		this.setItineraire();  
		this.setPointsDijkstra(); 

	}
	
	public Itineraire(){}
	
	//getters
	public ArrayList<Point> getPointsItineraireOrdonne(){
		return lesLieuxOrdonnes; 
	}
	public LinkedList<String> getStringItineraireOrdonne(){
		return lesLieuxNomsOrdonnes; 
	}
	public ArrayList<Integer> getIntegersDijkstra(){
		return lesIntegersDijkstra; 
	}
	public ArrayList<Point> getPointsDijkstra(){
		return lesPointsDijkstra; 
	}

	
	//Methode pour passer de la liste des lieux, dont on connait le nom, vers une liste des points, 
	//qu'on va pouvoir trier en utilisant la methode setItineraire()
	public void stringToInteger(LinkedList<String> laListe){
		lesLieux = new ArrayList<Point>(); 
		for (String st : laListe){
			try{
				BufferedReader reader = new BufferedReader(new FileReader(new File("ListeLieux.txt"))); 
				String ligne; 
				
				while (reader!= null && (ligne = reader.readLine()) != null){
					String str = ligne; 
					String[] arr = str.split(",",4); 
					if (st.equals(arr[1])){
						lesLieux.add(new Point(Integer.parseInt(arr[2]),Integer.parseInt(arr[3]))); 
					}
				}
			}
			catch(Exception e){
				System.err.println("Exception " +e.getMessage()); 
			}
		}
	}
	
	//Methode pour trouver le chemin ideal a parcourir entre une liste des lieux (identifies par le numero de noeud)
	public void setItineraire(){
		ArrayList<Point> listeTemporaire = lesLieux; 
		ArrayList<DistanceAuNoeud> listeDistances; 
		ArrayList<Point> listeOrdonne = new ArrayList<Point>(); 
		lesIntegersDijkstra = new ArrayList<Integer>(); 
		
		Point pointInitial = lesLieux.get(0); 
		Point pointActuel = pointInitial; 
		listeOrdonne.add(pointInitial); 
		listeTemporaire.remove(pointActuel);
		
		while(!listeTemporaire.isEmpty()){
			listeDistances = new ArrayList<DistanceAuNoeud>(); 

			for (Point p : listeTemporaire){
				listeDistances.add(new DistanceAuNoeud(p, p.getDijkstraDistance(pointActuel)));							
			}						
			Collections.sort(listeDistances,comparator); 
			pointActuel = listeDistances.get(0).getPNoeud(); 
			listeOrdonne.add(pointActuel);
			listeTemporaire.remove(pointActuel); 
			distanceTotale+=listeDistances.get(0).getDistance(); 
		}
		lesLieuxOrdonnes = listeOrdonne; 
		
		if (!lesLieuxNoms.isEmpty()){ //ce qui veut dire qu'on a initialisé l'itineraire avec une liste des String, on doit maintenant l'ordonner
			for (Point p : lesLieuxOrdonnes){
				try{
					BufferedReader reader = new BufferedReader(new FileReader(new File("ListeLieux.txt"))); 
					String ligne; 
				
					while (reader!= null && (ligne = reader.readLine()) != null){
						String str = ligne; 
						String[] arr = str.split(",",4); 
						if (p.x == Integer.parseInt(arr[2]) && p.y == Integer.parseInt(arr[3])){
							lesLieuxNomsOrdonnes.add(arr[1]); 
							lesIntegersDijkstra.add(Integer.parseInt(arr[0])); 
						}
					}
				}
				catch(Exception e){
					System.err.println("Exception  " +e.getMessage()); 
				}
			}
		}
	}
	
	//méthode pour comparer les distances entre elles
	public static Comparator<DistanceAuNoeud> comparator = new Comparator<DistanceAuNoeud>(){
	@Override 
		public int compare(DistanceAuNoeud d1, DistanceAuNoeud d2){
			if (d1.getDistance() - d2.getDistance() == 0){
				return 0; 
			}else if (d1.getDistance() > d2.getDistance()){
				return 1; 
			}else{
				return -1; 
			}
		}
	};
	
	//méthode pour ajouter des noeuds à la liste de points
	public void setPointsDijkstra(){
		for (int i = 0; i < lesIntegersDijkstra.size()-1; i++){
			List<Point> listeTemporaire = dij.afficherCoordones(lesIntegersDijkstra.get(i), lesIntegersDijkstra.get(i+1)); 
			for(Point p : listeTemporaire){
				lesPointsDijkstra.add(p); 
			}
		}
	}

	
}
