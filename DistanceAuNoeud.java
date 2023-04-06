/*A plusieurs moments, nous allons avoir besoin de connaitre le noeud le plus proche d'un certain point
 * pour cela, nous allons trier les distances.
 * Le but de cette classe, c'est de creer un objet "DistanceAuNoeud" qui prend en parametre le numero du noeud ou on veut aller,
 * et la distance que cela nous coute d'aller jusqu'a lui. Une DistanceAuNoeud peut etre declaree aussi comme le point ou on veut aller
 * et la distance, et une DistanceAuNoeud peut etre aussi declaree comme le nom du noeud et la distance
 * */

import java.util.*; 
public class DistanceAuNoeud{
	private int nbNoeud; 
	private double distance; 
	private String nomNoeud; 
	private Point pNoeud; 
	public DistanceAuNoeud(int nbNoeud, double distance){
		this.nbNoeud = nbNoeud; 
		this.distance = distance;
	}
	//surcharge 1
	public DistanceAuNoeud(Point pNoeud, double distance){
		this.pNoeud = pNoeud; 
		this.distance = distance; 	
	}
	//surcharge 2
	public DistanceAuNoeud(String nomNoeud, double distance){
		this.nomNoeud = nomNoeud; 
		this.distance = distance;
	}
	
	//getters
	public double getDistance(){
		return this.distance; 
	}
	public int getNb(){
		return this.nbNoeud; 
	}
	public Point getPNoeud(){
		return pNoeud; 
	}
	public String getNomNoeud(){
		return nomNoeud; 
	}
	
	//Un comparator pour comparer deux DistancesAuNoeud et pouvoir faire le tri a l'aide des Collections
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
	
	public String toString(){
		return ""+this.nbNoeud+","+this.distance; 
	}
	
}
