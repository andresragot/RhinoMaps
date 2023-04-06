/* Le but de cette classe est de créer un objet Arete 
 * Une Arete est définie par un l'identifiant du noeud du depart, 
 * l'identifiant du noeud d'arrivée, et le poids (le poids de l'arete est egal à
 * la distance entre les deux noeuds) */

public class Arete{
	private int idNoeudD; //identifiant du noeud de départ
	private int idNoeudA; //identifiant du noeud d'arrivée 
	private int poids; //"distance" entre les noeuds
	
	//constructeur de la classe Arete 
	public Arete(int idDep, int idArr, int poids){
		idNoeudD = idDep; 
		idNoeudA = idArr; 
		this.poids = poids; 
	}
	
	//getters
	public int getIdNoeudDepart(){
		return idNoeudD; 
	}
	public int getIdNoeudArrive(){
		return idNoeudA; 
	}
	public int getPoids(){
		return this.poids; 
	}
	
	//setters
	public void setNoeudDep(int id){
		idNoeudD = id; 
	}
	public void setNoeudArr(int id){
		idNoeudA = id; 
	}
	public void setPoids(int p){
		this.poids = p; 
	}
	
}
