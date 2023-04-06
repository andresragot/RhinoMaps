/*Le but de cette classe, c'est de nous permetre calculer les chemins les plus courts entre deux points de la carte
 * les informationes necesaires pour l'algorithme sont dispoinibles sous des fichiers txt principalement le fichier : ListeArretesAvecDistances.txt
 * Notre implementation de l'algorithme Dijkstra, trouve le chemin le plus court entre deux "noeuds" qui sont identifies par un chiffre (int) */

import java.util.*; 
import java.io.*; 

public class Dijkstra{
	
	private static final double EPS = 1e-6;	//valeur pour comparer deux noeuds 
	private static final int NB_NOEUDS = 145; //nombre des noeuds dans le graphe
	private double[] dist; //Matrice des distances entre les noeuds
	private Integer[] prece; //Tableau avec les noeuds anterieures 
	private List<List<Arete>> leGraphe; //Liste 2D des aretes
	private static final double COEFF_DE_PROPORTIONALITE = 1.47;// coefficient de proportionalite pour faire le lien entre la distance mesure en pixels et la distance reel en metres 
	
	//constructeur 
	
	public Dijkstra(){
		this.creerGraphe(); 
		this.ajouterToutes(); 
	} 
	
	//Methode qui nous permet d'ajouter une arete au graphe, on rapelle que c'est une arete non directionne
	public void ajouterArete(int depuis, int jusqua, int poids){
		leGraphe.get(depuis).add(new Arete(depuis, jusqua, poids)); 
		leGraphe.get(jusqua).add(new Arete(jusqua, depuis, poids)); 
	}
	
	//Graphe des aretes (matrice)
	public List<List<Arete>> getGraphe(){
		return leGraphe; 
	}
	
	//Cette méthode a pour but de reconstruire le chemin parcouru depuis le début du lancement de l'algorithme
	//le chemin suivi par l'algorithme est retenu, par le numéro de chaque noeud
	//Puis il le reparcourt en chemin inverse afin de l'afficher sur la carte
	public List<Integer> reconstruirLeChemin(int debut, int fin){
		if (fin < 0 || debut >= NB_NOEUDS){
			System.out.println("Noeud donne invalide"); 
		}
		if (debut < 0 || fin >= NB_NOEUDS){
			System.out.println("Noeud donne invalide"); 
		}
		double distance = cheminDijkstra(debut,fin); 
		List<Integer> chemin = new ArrayList<>(); 
		if (distance == Double.POSITIVE_INFINITY){
			return chemin; 
		}
		for (Integer at = fin; at != null ; at = prece[at]){
			chemin.add(at); 
		}
		Collections.reverse(chemin); 
		return chemin; 	
	}
	
	//création du graphe
	public void creerGraphe(){
		leGraphe = new ArrayList<>(NB_NOEUDS); 
		for (int i = 0; i < NB_NOEUDS; i++){
			leGraphe.add(new ArrayList<>()); 
		}
	}
	
	//Comparateur des noeuds, ça va nous permetre comparer la distance qui nous a pris pour arriver jusqu'a ce noeud 
	private Comparator<Noeud> comparator = new Comparator<Noeud>(){
		@Override 
		public int compare(Noeud noeudA, Noeud noeudB){
			if (Math.abs(noeudA.getValeur() - noeudB.getValeur()) < EPS){
				return 0; 
			}else if (noeudA.getValeur() > noeudB.getValeur()){
				return 1; 
			}else{
				return -1; 
			}
		}
	};
	
	//Parcours de la carte afin de trouver le chemin le plus court
	//depuis le point de départ jusqu'à l'arrivée
	public double cheminDijkstra(int start, int end){
		dist = new double[NB_NOEUDS];
		Arrays.fill(dist, Double.POSITIVE_INFINITY);
		dist[start] = 0;
		
		//Garde en file d'attente le prochain noeud le mieux à visiter 
		PriorityQueue<Noeud> pq = new PriorityQueue<>(2 * NB_NOEUDS, comparator);
		pq.offer(new Noeud(start, 0));
		
		//Tableau utilisé pour suivre les noeuds qui ont déjà été visités
		boolean[] visited = new boolean[ NB_NOEUDS];
		prece = new Integer[ NB_NOEUDS];
		
		//Boucle while afin de parcourir toute la carte
		while (!pq.isEmpty()) {
			Noeud noeud = pq.poll();
			visited[noeud.getId()] = true;
			
			//Vérification: regarder si un meilleur chemin à été trouvé, 
			//avant de traiter ce noeud, afin de l'ignorer par la suite si non
			if (dist[noeud.getId()] < noeud.getValeur()) {
				continue;
			}
			
			List<Arete> lesAretes = leGraphe.get(noeud.getId());
			for (int i = 0; i < lesAretes.size(); i++) {
				Arete arete = lesAretes.get(i);
			
				//Vérification si on ne peut pas obtenir un chemin plus court
				//en passant par un noeud déjà visité avant
				if (visited[arete.getIdNoeudArrive()]) continue;
			
				// Si oui : mise à jour du cout minimum pour le chemin à parcourir
				double newDist = dist[arete.getIdNoeudDepart()] + arete.getPoids();
				
				if (newDist < dist[arete.getIdNoeudArrive()]) {
					prece[arete.getIdNoeudArrive()] = arete.getIdNoeudDepart();
					dist[arete.getIdNoeudArrive()] = newDist;
					pq.offer(new Noeud(arete.getIdNoeudArrive(), dist[arete.getIdNoeudArrive()]));
				}
			}
			
			//Quand on aura visité tous les noeuds jusqu'à la fin
			//On peut renvoyer la valeur de la distance minimale entre 
			//noeud du début et de fin car elle ne pourra pas diminuer
			if (noeud.getId() == end) {
				return dist[end];
			}
		}
		//Si le noeud final n'est pas accessible
		return Double.POSITIVE_INFINITY;
	}
	
	//Méthode qui permet d'ajouter la liste d'aretes (sous forme de liste txt)
	//sur la carte et de les relier à chaque noeud
	public void ajouterToutes(){
		try{
			BufferedReader reader = new BufferedReader(new FileReader(new File("ListeAretesAvecDistances.txt"))); 
			String ligne; 
			
			while (reader!= null && (ligne = reader.readLine()) != null){
				String str = ligne; 
				String[] arr = str.split(",",3); 
				int depuis = Integer.parseInt(arr[0]); 
				int jusqua = Integer.parseInt(arr[1]); 
				int p = Integer.parseInt(arr[2]); 
				this.ajouterArete(depuis,jusqua,p); 
			}
		}
		catch(Exception e){
			System.err.println("Exception " +e.getMessage()); 
		}
	}
	
	//méthode pour afficher le chemin final sur la carte
	public void afficherChemin(int depart, int arrive){
		System.out.println(this.reconstruirLeChemin(depart,arrive)); 
	}
	
	//Méthode pour afficher les coordonnées des noeuds parcourus pour faire le chemin voulu
	public List<Point> afficherCoordones(int depart, int arrive){
		List<Integer> listeTemp = this.reconstruirLeChemin(depart,arrive);
		List<Point> listePoint = new ArrayList<Point>();  
		Point pointXY; 
		
		for (Integer i : listeTemp){
			try{
				BufferedReader reader = new BufferedReader(new FileReader(new File("ListePositionsXY.txt"))); 
				String ligne; 
				
				while (reader!= null && (ligne = reader.readLine()) != null){
					String str = ligne; 
					String[] arr = str.split(",",3); 
					if (Integer.parseInt(arr[2]) == i){
						listePoint.add(new Point(Integer.parseInt(arr[0]), Integer.parseInt(arr[1]))); 
					}
				}
			}
			catch(Exception e){
				System.err.println("Exception " +e.getMessage()); 
			}
		}
		return listePoint;
	}
	
	//méthode pour calculer la distance totale reel entre tous les points parcourus, 
	public double getDistanceTotale(List<Point> uneListe){
		int distTotale = 0; 
		for (int i = 0; i < uneListe.size()-1; i++){
			distTotale += (uneListe.get(i)).distanceTo(uneListe.get(i+1)); 
		}
		return distTotale*COEFF_DE_PROPORTIONALITE; 
	}
}
