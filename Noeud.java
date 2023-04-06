import java.util.*; 
import java.io.*; 

//Cette classe permet d'implémenter un noeud, utile pour repérer toutes les intersections, et batiments de notre carte

public class Noeud{
	private int idNoeud; 
	private double valeur; 
	
	//constructeur
	public Noeud(int idNoeud, double valeur){
		this.idNoeud = idNoeud; 
		this.valeur = valeur;
	}
	public Noeud(){}
	
	//getters
	public int getId(){
		return this.idNoeud; 
	}
	public double getValeur(){
		return this.valeur; 
	}
	
	//setters
	public void setid(int id){
		this.idNoeud = id; 
	}
	public void setVal(double va){
		this.valeur = va; 
	}
	
	//méthode pour trouver les coordonnées du noeud le plus proche de la où on clique sur la carte
	public int getNbNoeudPres(Point p){
		ArrayList<DistanceAuNoeud> listeDistances = new ArrayList<DistanceAuNoeud>(); 
		
		try{
			BufferedReader reader = new BufferedReader(new FileReader(new File("ListePositionsXY.txt"))); 
			String ligne; 
			
			while (reader!= null && (ligne = reader.readLine()) != null){
				String str = ligne; 
				String[] arr = str.split(",",3); 
				double dist = this.calculDistance(p.getX(),p.getY(),Double.parseDouble(arr[0]), Double.parseDouble(arr[1])); 
				listeDistances.add(new DistanceAuNoeud(Integer.parseInt(arr[2]),dist)); 
			}
		}
		catch(Exception e){
			System.err.println("Exception " +e.getMessage()); 
		}
		Collections.sort(listeDistances, comparator);
		return listeDistances.get(0).getNb(); 
	}
	
	//Methode pour trouver le nom du noeud le plus proche a un point P, si le point plus proche, est a une distance superieure a 20 pixels, on 
	//donne a l'utulisateur "Pres du lieux : plus le nom du noeud"
	public String getNomNoeudPres(Point p){
		ArrayList<DistanceAuNoeud> listeDistances = new ArrayList<DistanceAuNoeud>(); 
		
		try{
			BufferedReader reader = new BufferedReader(new FileReader(new File("ListeLieux.txt"))); 
			String ligne; 
			
			while (reader!= null && (ligne = reader.readLine()) != null){
				String str = ligne; 
				String[] arr = str.split(",",4); 
				double dist = this.calculDistance(p.getX(),p.getY(),Double.parseDouble(arr[2]), Double.parseDouble(arr[3])); 
				listeDistances.add(new DistanceAuNoeud(arr[1],dist)); 
			}
		}
		catch(Exception e){
			System.err.println("Exception " +e.getMessage()); 
		}
		Collections.sort(listeDistances, comparator);
		if (listeDistances.get(0).getDistance() < 20){
			return listeDistances.get(0).getNomNoeud();
		}
		return "Pres du lieux : "+ listeDistances.get(0).getNomNoeud();
	}
		
	//méthode pour calculer la distance entre 2 points (noeuds)
	public double calculDistance(double x1, double y1, double x2, double y2){
		return Math.sqrt((y1-y2)*(y1-y2)+(x1-x2)*(x1-x2));
	}
	
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
	
}
