/*Vu que on utilize les object Points assez reguiliarement, on a cree un redefini l'objet point pour pouvoir l'utiliser a notre convenience */
import java.util.*; 

public class Point{
	protected int x; 
	protected int y; 
	private Dijkstra dijk; 
	private int nbPoint; 
	private Noeud noeudObjet3; 
	
	public Point(int x, int y){
		this.x = x; 
		this.y = y; 
		nbPoint = 0; 
		noeudObjet3 = new Noeud();
	}
	
	//getters
	public int getX(){
		return this.x; 
	}
	public int getY(){
		return this.y; 
	}
	public Point getPoint(){
		return this; 
	}
	public int getNbPoint(){
		return nbPoint;
	}
	
	//setters
	public void setX(int a){
		this.x = a;
	}
	public void setY(int a){
		this.y = a;
	}
	
	@Override 
	public String toString(){
		return "("+this.x+","+this.y+")"; 
	}
	
	//méthode pour trouver la distance entre 2 points à vol d'oiseau
	public int distanceTo(Point p){
		return (int)(Math.sqrt((this.y - p.y)*(this.y - p.y)+(this.x - p.x)*(this.x - p.x)));
	}
	
	//Methode pour trouver la distance entre un point et un autre point en passant par les noeuds les plus proches 
	public double getDijkstraDistance(Point p){
		dijk = new Dijkstra(); 
		nbPoint = noeudObjet3.getNbNoeudPres(this); 
		int nbPointFinal = noeudObjet3.getNbNoeudPres(p); 
		List<Point> listeDePoints = dijk.afficherCoordones(nbPoint,nbPointFinal); 
		return dijk.getDistanceTotale(listeDePoints); 
	}
}
