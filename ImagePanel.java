/*Le but de cette classe c'est d'afficher la carte de l'INSA, ansi que de peindre l'itineraire donnée par l'utilisateur */

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import javax.imageio.*;
import java.io.*;
import java.lang.*;
import java.util.List;
import java.awt.event.*;

public class ImagePanel extends JPanel {
	private List<Point> points= new ArrayList<>();
	private List<ObjetLine> listePointsComplete = new ArrayList<ObjetLine>(); 
	private int nbLieux; 
	private ArrayList<Point> pointsDijkstra; 
	private Point pointDebut; 
	private Point pointFin; 
	private int pDebut; 
	private int pFin; 
	private Noeud noeudObjet2; 
	private BufferedImage img;
	private Itineraire litineraire; 
	private boolean rainBowModeOn; 
	private ArrayList<Color> rainBowList; 
	
	public ImagePanel(BufferedImage img) {
		this.img = img;
		this.setCoordonnees(); 
		this.noeudObjet2 = new Noeud();  
		litineraire = new Itineraire(); 
		rainBowModeOn = false; 
		this.setRainBowColors(); 
	}
  
	public Dimension getPreferredSize() {
		return img == null ? super.getPreferredSize() : new Dimension(img.getWidth(), img.getHeight());
	}
	
	//Getters
	public List<Point> getListePoints(){
		return this.points;
	}
	public List<ObjetLine> getListePointsComplete(){
		return this.listePointsComplete; 
	}
	public ArrayList<Point> getListePointsDij(){
		return this.pointsDijkstra; 
	}
	public Point getPointDebut(){
		return this.pointDebut; 
	}
	public Point getPointFin(){
		return this.pointFin; 
	}
	public int getPointDebutInt(){
		return this.pDebut; 
	}
	public int getPointFinInt(){
		return this.pFin;
	}
	
	//Setters
	public void setListePoints(List<Point> liste){
		points = liste; 
	}
	public void setPointDebut(Point p){
		pointDebut = p; 
	}
	public void setPointFin(Point p ){
		pointFin = p; 
	}
	public void setPointDebutInt(int i ){
		pDebut = i; 
	}
	public void setPointFinInt(int i ){
		pFin = i; 
	}
	public void setItinerairePanel(LinkedList<String> uneListe){
		litineraire = new Itineraire(uneListe); 
		pointsDijkstra = litineraire.getPointsDijkstra();  
	}

	public void setNbLieux(int nbL){
		nbLieux = nbL; 
	}
	
	public void setIdFin(){
		pFin   = noeudObjet2.getNbNoeudPres(pointFin); 
	}
	public void setIdDepart(){
		pDebut = noeudObjet2.getNbNoeudPres(pointDebut); 
	}
	public void setCoordonnees(){
		Dijkstra d = new Dijkstra(); 
		points = d.afficherCoordones(pDebut, pFin); 
	}
	public void setRainBowMode(boolean b){
		rainBowModeOn = b; 
	}
	
	public Point toImageContext(Point p) {
		Point imgLocation = getImageLocation();
		Point relative = p;
		relative.x -= imgLocation.x;
		relative.y -= imgLocation.y;
		return relative;
	}
	
	//méthode pour afficher le trajet à parcourir sur la carte
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g.create();
		if (img != null) {
			Point p = getImageLocation();
			g.drawImage(img, 0, 0, this);
		}
		Color color = new Color(0,90,240,180); 
		g2d.setColor(color);
		g2d.setStroke(new BasicStroke(4f)); 
		
		if (pointsDijkstra == null){
			for (int i  = 0; i < points.size()-1; i++){
				g2d.drawLine(points.get(i).getX(), points.get(i).getY(),points.get(i+1).getX(), points.get(i+1).getY()); 
			}
		}else{
			if (rainBowModeOn){
				for (int i = 0; i <pointsDijkstra.size()-1 ; i++){
					g2d.setColor(rainBowList.get(i)); 
					g2d.drawLine(pointsDijkstra.get(i).getX(), pointsDijkstra.get(i).getY(),pointsDijkstra.get(i+1).getX(), pointsDijkstra.get(i+1).getY()); 
				}
			}else {
				for (int i = 0; i <pointsDijkstra.size()-1 ; i++){
					g2d.drawLine(pointsDijkstra.get(i).getX(), pointsDijkstra.get(i).getY(),pointsDijkstra.get(i+1).getX(), pointsDijkstra.get(i+1).getY()); 
				}
			} 	

			
			
		g2d.dispose();
		}
	}
	public void peindre(){
		this.repaint();
	}

	//Get la position de l'image par rapport a la fenetre
	protected Point getImageLocation() {
		Point p = null;
		if (img != null) {
			int x = (getWidth() - img.getWidth()) / 2;
			int y = (getHeight() - img.getHeight()) / 2;
			p = new Point(x, y);
		}
		return p;
	}
	
	
	//méthode pour avoir une couleur aléatoire
	public Color getRandomColor(){
		int r = (int)(Math.random()*255); 
		int g = (int)(Math.random()*255); 
		int b = (int)(Math.random()*255);
		Color randomColor = new Color(r,g,b,255); 
		return randomColor; 
	}
	
	//methode pour definir toutes les couleurs disponibles pour quand on active le rainbow mode 
	public void setRainBowColors(){
		rainBowList = new ArrayList<Color>(); 

		for (int i = 0; i < 20 ; i++){
			rainBowList.add(new Color(255,0,0)); 
			rainBowList.add(new Color(255,127,0)); 
			rainBowList.add(new Color(255,255,0)); 
			rainBowList.add(new Color(127,255,0));
			rainBowList.add(new Color(0,255,0));
			rainBowList.add(new Color(0,255,127));
			rainBowList.add(new Color(0,255,255)); 
			rainBowList.add(new Color(0,127,255)); 
			rainBowList.add(new Color(0,0,255)); 
			rainBowList.add(new Color(127,0,255));
			rainBowList.add(new Color(255,0,255));
			rainBowList.add(new Color(255,0,127));
		}
	}
}
