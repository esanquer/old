package GarbageCollector.presentation.graph;

import GarbageCollector.presentation.event.StaticHandler;
import GarbageCollector.presentation.graph.convoyeur.ConvoyeurUI;
import GarbageCollector.presentation.graph.convoyeur.PointUI;
import GarbageCollector.presentation.graph.equipement.EntreeUsineUI;
import GarbageCollector.presentation.graph.equipement.EquipementUI;
import GarbageCollector.presentation.graph.equipement.JonctionUI;
import GarbageCollector.presentation.graph.equipement.SortieUsineUI;
import GarbageCollector.presentation.graph.equipement.StationUI;
import GarbageCollector.presentation.util.DestringifyUI;
import GarbageCollector.presentation.util.StringifyUI;
import java.io.IOException;
import java.util.Iterator;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;


public class GraphePanel extends Pane 
{
    
        /*##############################
                 ATRIBUT
        ##############################*/
    
    private double width, height;               //Taille de la fenetre qui contient le panneau
    private double longueurReal, hauteurReal;   //Taille du centre de tri réel
    private double ratioX, ratioY;              //Ratio entre la taille du graphe et le taille du centre de tri
    private int curentZoom;
    
    
    private final Grid grid = new Grid();         //Grid en taille réel
    private boolean showGrid = false;
    private double spacingX, spacingY;
    private final Canvas canvas = new Canvas();
    
    
    private boolean showName = true;
    
        /*##############################
                CONSTRUCTEUR
        ##############################*/
    
    public GraphePanel(double width, double height, double longueurReal, double hauteurReal){
        this.ratioX = 1;
        this.ratioY = 1;
        this.setLongeur(width);
        this.setHauteur(height);
        
        this.longueurReal = longueurReal;
        this.hauteurReal = hauteurReal;

        canvas.setWidth(width);
        canvas.setHeight(height);
        grid.setLongueur(this.longueurReal);
        grid.setHauteur(this.hauteurReal);
        this.getChildren().add(canvas);

        drawGrid();
        this.getStyleClass().add("matrice");
    }
    
        /*##############################
                CONSTRUCTEUR
        ##############################*/
    
     public void setZoom(int d){
        curentZoom = d;
        
            //Le panneau
        this.setWidth(width*d);
        this.setPrefWidth(width*d);
        this.setHeight(height*d);
        this.setPrefHeight(height*d);

            //La grille
        this.canvas.setWidth(this.getWidth());
        this.canvas.setHeight(this.getHeight());
        drawGrid();
        
            //La position des Elements
        Iterator<Node> iterEquipements = this.getChildren().iterator();
        while(iterEquipements.hasNext()){
            Node n = iterEquipements.next();
            if(n instanceof EquipementUI){
                ((EquipementUI)n).setZoomX(d, ratioX);
                ((EquipementUI)n).setZoomY(d, ratioY);
            }
            if(n instanceof PointUI){
                ((PointUI)n).setZoomX(d, ratioX);
                ((PointUI)n).setZoomY(d, ratioY);
            }
        }
    }
     

        /*##############################
                DESSINER GRILLE
        ##############################*/
     
    private void drawGrid(){
        if(this.showGrid){
            this.spacingX = grid.getEchelle();
            this.spacingY = grid.getEchelle();
            GraphicsContext g = canvas.getGraphicsContext2D();
            g.setFill(Color.WHITE);
            g.setStroke(Color.BLACK);
            g.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
            g.setStroke(Color.BLACK);

            for (double x = this.spacingX; x < grid.getLongueur(); x += this.spacingX)
                g.strokeLine(x*this.ratioX*curentZoom, 0, x*this.ratioX*curentZoom, this.getHeight());
            for (double y = this.spacingY; y < grid.getHauteur(); y += this.spacingY)
                g.strokeLine(0, y*this.ratioY*curentZoom, this.getWidth(), y*this.ratioY*curentZoom);
        }
        else{
            GraphicsContext g = canvas.getGraphicsContext2D();
            g.setFill(Color.WHITE);
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
        }
    }
    

        /*##############################
                  AJOUT
        ##############################*/
    
    public void ajouterStation(double x, double y ){
        
            //Récuperation des données
        double longueur = 100;
        double hauteur = 70;
        String nom = "No Name";
        String description = "No Description";
        int capaciteMax = 1000;
        int nbSortie = 1;
        
            //Envoie des données au controleur
        StationUI s = new StationUI(this.curentZoom, this.ratioX, ratioY, 1, x, y, longueur, hauteur, 
                                                nom, description, "#FFFFFF", capaciteMax, nbSortie);
        String stationStr = StringifyUI.stringify(s);
        String stationCreatedStr = StaticHandler.getControleur().ajouterEquipement(stationStr);
        
            //Récuperation des données depuis le model et instanciation de l'equivalent UI
        s = (StationUI) DestringifyUI.parseVal(stationCreatedStr);
        StationUI stationUI = new StationUI(this.curentZoom, this.ratioX, ratioY, s.getMyId(), s.getPosXReal(), s.getPosYReal(), s.getLongueurReal(), s.getHauteurReal(), 
                                                s.getNom(), s.getDescription(), "#FFFFFF", s.getCapacite(), s.getNbSorties());
        
        
        //Mise a jour des references et ID des entrees sorties sauvegarder dans l'UI
        stationUI.getEntreeEquipement().get(0).setMyId(s.getEntreeEquipement().get(0).getMyId());
        stationUI.getEntreeEquipement().get(0).setEquipement(stationUI);
            
        for(int i=0; i<s.getSortieEquipement().size(); i++){
            stationUI.getSortieEquipement().get(i).setMyId(s.getSortieEquipement().get(i).getMyId());
            stationUI.getSortieEquipement().get(i).setEquipement(stationUI);
        }
        
        this.getChildren().add(stationUI);
    }
    
    public void ajouterEntreeUsine(double x, double y ){
        
            //Récuperation des données
        double longueur = 50;
        double hauteur = 100;
        String nom = "No Name";
        String description = "No Description";
        int capaciteMax = 1000;
        
            //Envoie des données au controleur
        EntreeUsineUI e = new EntreeUsineUI(this.curentZoom, this.ratioX, ratioY, 1, x, y,  
                                                longueur, hauteur, nom, description, "#FFFFFF", capaciteMax);
        String entreeUsineStr = StringifyUI.stringify(e);
        String entreeUsineCreated = StaticHandler.getControleur().ajouterEquipement(entreeUsineStr);
        
            //Récuperation des données depuis le model et instanciation de l'equivalent UI
        e = (EntreeUsineUI)DestringifyUI.parseVal(entreeUsineCreated);
        EntreeUsineUI toAdd = new EntreeUsineUI(this.curentZoom, this.ratioX, ratioY, e.getMyId(), e.getPosXReal(), e.getPosYReal(),  
                                                e.getLongueurReal(), e.getHauteurReal(), e.getNom(), e.getDescription(), "#FFFFFF", e.getCapacite());
        
        //Mise a jour des references et ID des entrees sorties sauvegarder dans l'UI
        toAdd.getSortieEquipement().get(0).setMyId(e.getSortieEquipement().get(0).getMyId());
        toAdd.getSortieEquipement().get(0).setEquipement(toAdd);
        
        this.getChildren().add(toAdd);
        
        
    }
        
    public void ajouterSortieUsine(double x, double y ){
        
            //Récuperation des données
        double longueur = 50;
        double hauteur = 100;
        String nom = "No Name";
        String description = "No Description";
        int capaciteMax = 1000;

            //Envoie des données au controleur
        SortieUsineUI s = new SortieUsineUI(this.curentZoom, this.ratioX, ratioY, 1, x, y, longueur, hauteur, 
                                                nom, description, "#FFFFFF", capaciteMax);
        String sortieUsineStr = StringifyUI.stringify(s);
        String sortieUsineCreated = StaticHandler.getControleur().ajouterEquipement(sortieUsineStr);
        
            //Récuperation des données depuis le model et instanciation de l'equivalent UI
        s = (SortieUsineUI)DestringifyUI.parseVal(sortieUsineCreated);
        SortieUsineUI toAdd=  new SortieUsineUI(this.curentZoom, this.ratioX, ratioY, s.getMyId(), s.getPosXReal(), s.getPosYReal(),  
                                                s.getLongueurReal(), s.getHauteurReal(), s.getNom(), s.getDescription(), "#FFFFFF", s.getCapacite());
        
        
        //Mise a jour des references et ID des entrees sorties sauvegarder dans l'UI
        toAdd.getEntreeEquipement().get(0).setMyId(s.getEntreeEquipement().get(0).getMyId());
        toAdd.getEntreeEquipement().get(0).setEquipement(toAdd);
        
        
        this.getChildren().add(toAdd);
    }
        
    public void ajouterJonction(double x, double y ){
        
            //Récuperation des données
        double longueur = 20;
        double hauteur = 20;
        String nom = "No Name";
        String description = "No Description";
        int capaciteMax = 1000;
        int nbEntree = 1;

            //Envoie des données au controleur
        JonctionUI j = new JonctionUI(this.curentZoom, this.ratioX, this.ratioY, 1, x, y, 
                                            longueur, hauteur,  nom, description, "#FFFFFF", capaciteMax, nbEntree);
        String jonctionStr = StringifyUI.stringify(j);
        String nouvelleJonctionStr = StaticHandler.getControleur().ajouterEquipement(jonctionStr);
        
        
            //Récuperation des données depuis le model et instanciation de l'equivalent UI
        j = (JonctionUI)DestringifyUI.parseVal(nouvelleJonctionStr);
        JonctionUI toAdd = new JonctionUI(this.curentZoom, this.ratioX, this.ratioY, j.getMyId(), j.getPosXReal(), j.getPosYReal(), 
                                            j.getLongueurReal(), j.getHauteurReal(),  j.getNom(), j.getDescription(), "#FFFFFF", j.getCapacite(), j.getNbEntree());
        
        
            //Mise a jour des references et ID des entrees sorties sauvegarder dans l'UI
        for(int i=0; i<j.getEntreeEquipement().size(); i++){
            toAdd.getEntreeEquipement().get(i).setMyId(j.getEntreeEquipement().get(i).getMyId());
            toAdd.getEntreeEquipement().get(i).setEquipement(toAdd);
        }
        
        toAdd.getSortieEquipement().get(0).setMyId(j.getSortieEquipement().get(0).getMyId());
        toAdd.getSortieEquipement().get(0).setEquipement(toAdd);
        
        this.getChildren().add(toAdd);
    }

    public void ajouteConvoyeur(ConvoyeurUI c) {
        String convoyeurStr = StringifyUI.stringify(c);
        String convoyeurCreatedStr = StaticHandler.getControleur().ajouterConvoyeur(convoyeurStr);
        ConvoyeurUI cModel =(ConvoyeurUI) DestringifyUI.parseVal(convoyeurCreatedStr);
        c.setMyId(cModel.getMyId());
        c.addConvoyeurToPane(this);
    }
    
    
            /*##############################
                  AJOUT
        ##############################*/
    
    public void setPositionStation(double x, double y ){
        
            //Récuperation des données
        double longueur = 100;
        double hauteur = 70;
        String nom = "No Name";
        String description = "No Description";
        int capaciteMax = 1000;
        int nbSortie = 1;
        
            //Envoie des données au controleur
        StationUI s = new StationUI(this.curentZoom, this.ratioX, ratioY, 1, x, y, longueur, hauteur, 
                                                nom, description, "#FFFFFF", capaciteMax, nbSortie);
        String stationStr = StringifyUI.stringify(s);
        String stationCreatedStr = StaticHandler.getControleur().ajouterEquipement(stationStr);
        
            //Récuperation des données depuis le model et instanciation de l'equivalent UI
        s = (StationUI) DestringifyUI.parseVal(stationCreatedStr);
        StationUI stationUI = new StationUI(this.curentZoom, this.ratioX, ratioY, s.getMyId(), s.getPosXReal(), s.getPosYReal(), s.getLongueurReal(), s.getHauteurReal(), 
                                                s.getNom(), s.getDescription(), "#FFFFFF", s.getCapacite(), s.getNbSorties());
        
        
        //Mise a jour des references et ID des entrees sorties sauvegarder dans l'UI
        stationUI.getEntreeEquipement().get(0).setMyId(s.getEntreeEquipement().get(0).getMyId());
        stationUI.getEntreeEquipement().get(0).setEquipement(stationUI);
            
        for(int i=0; i<s.getSortieEquipement().size(); i++){
            stationUI.getSortieEquipement().get(i).setMyId(s.getSortieEquipement().get(i).getMyId());
            stationUI.getSortieEquipement().get(i).setEquipement(stationUI);
        }
        
        this.getChildren().add(stationUI);
    }
    
    public void setPositionEntreeUsine(double x, double y ){
        
            //Récuperation des données
        double longueur = 50;
        double hauteur = 100;
        String nom = "No Name";
        String description = "No Description";
        int capaciteMax = 1000;
        
            //Envoie des données au controleur
        EntreeUsineUI e = new EntreeUsineUI(this.curentZoom, this.ratioX, ratioY, 1, x, y,  
                                                longueur, hauteur, nom, description, "#FFFFFF", capaciteMax);
        String entreeUsineStr = StringifyUI.stringify(e);
        String entreeUsineCreated = StaticHandler.getControleur().setPositionEquipement(entreeUsineStr);
        
            //Récuperation des données depuis le model et instanciation de l'equivalent UI
        e = (EntreeUsineUI)DestringifyUI.parseVal(entreeUsineCreated);
        EntreeUsineUI toAdd = new EntreeUsineUI(this.curentZoom, this.ratioX, ratioY, e.getMyId(), e.getPosXReal(), e.getPosYReal(),  
                                                e.getLongueurReal(), e.getHauteurReal(), e.getNom(), e.getDescription(), "#FFFFFF", e.getCapacite());
        
        //Mise a jour des references et ID des entrees sorties sauvegarder dans l'UI
        toAdd.getSortieEquipement().get(0).setMyId(e.getSortieEquipement().get(0).getMyId());
        toAdd.getSortieEquipement().get(0).setEquipement(toAdd);
        
        this.getChildren().add(toAdd);
        
        
    }
        
    public void setPositionJonction(double x, double y ){
        
            //Récuperation des données
        double longueur = 20;
        double hauteur = 20;
        String nom = "No Name";
        String description = "No Description";
        int capaciteMax = 1000;
        int nbEntree = 1;

            //Envoie des données au controleur
        JonctionUI j = new JonctionUI(this.curentZoom, this.ratioX, this.ratioY, 1, x, y, 
                                            longueur, hauteur,  nom, description, "#FFFFFF", capaciteMax, nbEntree);
        String jonctionStr = StringifyUI.stringify(j);
        String nouvelleJonctionStr = StaticHandler.getControleur().ajouterEquipement(jonctionStr);
        
        
            //Récuperation des données depuis le model et instanciation de l'equivalent UI
        j = (JonctionUI)DestringifyUI.parseVal(nouvelleJonctionStr);
        JonctionUI toAdd = new JonctionUI(this.curentZoom, this.ratioX, this.ratioY, j.getMyId(), j.getPosXReal(), j.getPosYReal(), 
                                            j.getLongueurReal(), j.getHauteurReal(),  j.getNom(), j.getDescription(), "#FFFFFF", j.getCapacite(), j.getNbEntree());
        
        
            //Mise a jour des references et ID des entrees sorties sauvegarder dans l'UI
        for(int i=0; i<j.getEntreeEquipement().size(); i++){
            toAdd.getEntreeEquipement().get(i).setMyId(j.getEntreeEquipement().get(i).getMyId());
            toAdd.getEntreeEquipement().get(i).setEquipement(toAdd);
        }
        
        toAdd.getSortieEquipement().get(0).setMyId(j.getSortieEquipement().get(0).getMyId());
        toAdd.getSortieEquipement().get(0).setEquipement(toAdd);
        
        this.getChildren().add(toAdd);
    }

    public void setPositionConvoyeur(ConvoyeurUI c) {
        String convoyeurStr = StringifyUI.stringify(c);
        String convoyeurCreatedStr = StaticHandler.getControleur().ajouterConvoyeur(convoyeurStr);
        ConvoyeurUI cModel =(ConvoyeurUI) DestringifyUI.parseVal(convoyeurCreatedStr);
        c.setMyId(cModel.getMyId());
        c.addConvoyeurToPane(this);
    }
    
    
        /*##############################
                ACTIVER/DESACTIVER
        ##############################*/
    
    public void toogleGrid(){
        this.showGrid = !this.showGrid;
        drawGrid();
    }
    
    
    
        /*##############################
                ACCESSEUR
        ##############################*/

    public double getRatioX() {
        return ratioX;
    }
    public double getRatioY() {
        return ratioY;
    }

    public double getLongueurReal() {
        return longueurReal;
    }
    public double getHauteurReal() {
        return hauteurReal;
    }
    public double getGridEchelle(){return grid.getEchelle();}

    public boolean isShowName() {
        return showName;
    }

    public int getCurentZoom() {
        return curentZoom;
    }


    
        /*##############################
                MODIFICATEUR
        ##############################*/
    
    public void setLongeur (double width){
        this.width = width;
        this.ratioX = this.width/this.longueurReal;
        this.setWidth(this.width*ratioX);
        this.setPrefWidth(this.width*ratioX);
        
        this.canvas.setWidth(this.getWidth());
        this.canvas.setHeight(this.getHeight());
    }
    public void setHauteur(double height){
        this.height = height;
        this.ratioY = this.height/this.hauteurReal;
        this.setHeight(this.height*ratioY);
        this.setPrefHeight(this.height*ratioY);
    }
    
    public void setLongueurReal(double width) {
        longueurReal = width;
        grid.setLongueur(longueurReal);
        this.ratioX = this.width/this.longueurReal;
        setZoom(curentZoom);
    }
    public void setHauteurReal(double height) {
        hauteurReal = height;
        grid.setHauteur(hauteurReal);
        this.ratioY = this.height/this.hauteurReal;
        setZoom(curentZoom);
    }
    public void setGridEchelle(double echelle){
        grid.setEchelle(echelle);
        drawGrid();
    }
    public void setShowName(boolean showName) throws IOException {
        this.showName = showName;
        //La position des Elements
        Iterator<Node> iterEquipements = this.getChildren().iterator();
        while(iterEquipements.hasNext()){
            Node n = iterEquipements.next();
            if(n instanceof StationUI){
                ((StationUI)n).setShowName(showName);
            }
        }
    }
    
    public void resetCanvas(){

        canvas.setWidth(width);
        canvas.setHeight(height);

        this.getChildren().add(canvas);
    }
}