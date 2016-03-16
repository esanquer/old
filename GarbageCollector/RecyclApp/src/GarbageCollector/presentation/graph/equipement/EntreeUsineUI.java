
package GarbageCollector.presentation.graph.equipement;

import GarbageCollector.presentation.event.StaticHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javafx.event.EventHandler;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class EntreeUsineUI extends Pane implements EquipementUI, ComposantUI
{
        /*##############################
                 ATRIBUT
         ##############################*/
    
            /*=======================
                    Data 
            =========================*/
    //Les atributs de base d'une Sorite d'usine (A binder !!)
    private Integer id;
    private double posXReal;
    private double posYReal;
    private double longueurReal;  //en px
    private double hauteurReal;   //en px
    private String nom;
    private String description;
    private String couleur;
    private double capacite;
    
    private SortieEquipementUI outPoint;
    
    private HashMap<String,Double> fluxEntrants = new HashMap<>();
    
    
            /*===============================
                Data Composant UI en px
            =================================*/
    //Les atribut de l'objet Graphique DataUI
    private double longueur = 100;
    private final double hauteur = 70;
    private double posX = 0;
    private double posY = 0;
    private final Rectangle cadre;
    private int zoom;
    private double ratioX, ratioY;
    private boolean estConforme;
    

        /*##############################
                CONSTRUCTEUR
        ##############################*/
    
    public EntreeUsineUI(){
    cadre = new Rectangle();
    cadre.setFill(Color.web("#FFFFFF"));
        cadre.setStroke(Color.BLACK);
        cadre.setStrokeWidth(2);
        cadre.setArcHeight(30);
        cadre.setArcWidth(30);
        cadre.setWidth(longueur);
        cadre.setHeight(hauteur);
        cadre.getStyleClass().add("equipement");
        this.getChildren().add(cadre);
        
        outPoint = new SortieEquipementUI(0,this.hauteur/2,this);
    }
    public EntreeUsineUI(int zoom, double ratioX, double ratioY, int id, double posXReal, double posYReal, 
                        double longueurReal, double hauteurReal, String nom, String description, 
                        String couleur, double capacite) {
        this.id = id;
            /*========================
                Init Variable Data
            =========================*/
        this.posXReal = posXReal;
        this.posYReal = posYReal;
        this.longueurReal = longueurReal;
        this.hauteurReal = hauteurReal;
        this.nom = nom;
        this.description = description;
        this.capacite = capacite;
        
        this.ratioX = ratioX;
        this.ratioY = ratioY;
        this.couleur = couleur;
        this.fluxEntrants = new HashMap<>();
        
            /*====================
                Init Composant
            =====================*/

        cadre = new Rectangle();
        cadre.setFill(Color.web(couleur));
        cadre.setStroke(Color.BLACK);
        cadre.setStrokeWidth(2);
        cadre.setArcHeight(30);
        cadre.setArcWidth(30);
        cadre.setWidth(longueur);
        cadre.setHeight(hauteur);
        cadre.getStyleClass().add("equipement");
        this.getChildren().add(cadre);
        
        outPoint = new SortieEquipementUI(0,this.hauteur/2,this);
        this.getChildren().add(outPoint);
        
        this.setZoomX(zoom, ratioX);
        this.setZoomY(zoom, ratioY);
        
        this.attacherEntreesEtSorties();
        
            /*====================
                Init Evenement
            =====================*/
        
        this.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) { 
                Dragboard db = startDragAndDrop(TransferMode.ANY);
                ClipboardContent content = new ClipboardContent();
                content.putString("Deplacement_Station");
                db.setContent(content);
            }
        });  
        
        this.setOnDragDone(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                if (event.getTransferMode() == TransferMode.MOVE) {
                    // TODO liaison avec le controleur
                }
                event.consume();
            }
        });
        
        this.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) { 
                if(mouseEvent.getTarget() instanceof Rectangle)
                    StaticHandler.selectionEntreeUsine((EntreeUsineUI)mouseEvent.getSource());
            }
        });  
        
        
    }

    
        /*##############################
                SELECTION
        ##############################*/
        
    @Override
    public void select() {
        cadre.getStyleClass().add("equipement-active");
    }
    @Override
    public void unselect() {
        cadre.getStyleClass().remove("equipement-active");
    }    

    
    
        /*############################
            GESTION ENTREES/SORTIES
        ############################*/
    
    @Override
    public void supprimeSorties() {
        StaticHandler.supprimeSortieEquipement(outPoint);
    }
    @Override
    public void supprimeEntrees() {}
    @Override
    public void attacherEntreesEtSorties() {
        this.outPoint.setEquipement(this);
    }
    
    
    
        /*##############################
                ACCESSEUR
        ##############################*/
    
    public HashMap<String, Double> getFluxEntrants() {
        return fluxEntrants;
    }

    public String getNom() {
        return nom;
    }
    public String getDescription() {
        return description;
    }
    public double getCapacite() {
        return capacite;
    }
    public String getCouleur() {
        cadre.setFill(Color.web(couleur));
        return couleur;
    }
    
    @Override
    public int getMyId() {
        return id;
    }
    @Override
    public double getPosX() {return this.posX;}
    @Override
    public double getPosY() {return this.posY;}
    @Override
    public double getLongueur(){return this.longueur;}
    @Override
    public double getHauteur(){return this.hauteur;}
    @Override
    public double getPosXReal() {return this.posXReal;}
    @Override
    public double getPosYReal() {return this.posYReal;}
    @Override
    public double getLongueurReal(){return this.longueurReal;}
    @Override
    public double getHauteurReal(){return this.hauteurReal;}
    @Override
    public List<EntreeEquipementUI> getEntreeEquipement() {
        return new ArrayList<>();
    }
    @Override
    public List<SortieEquipementUI> getSortieEquipement() {
       ArrayList<SortieEquipementUI> l = new ArrayList<>();
       l.add(outPoint);
       return l;
    }
    @Override
    public int getZoom() {
        return zoom;
    }
    @Override
    public double getRatioX() {
        return ratioX;
    }
    @Override
    public double getRatioY() {
        return ratioY;
    }

    public boolean isEstConforme() {
        return estConforme;
    }
    
    
    
        /*##############################
                MODIFICATEUR
        ##############################*/

    public void setFluxEntrants(HashMap<String, Double> fluxEntrants) {
        this.fluxEntrants = fluxEntrants;
        verifieConformite();
    }
    
    public void addFluxEntrant(String nom, Double quantite){
        this.fluxEntrants.put(nom, quantite);
        verifieConformite();
    }
    public void removeFluxEntrant(String nom){
        this.fluxEntrants.remove(nom);
        verifieConformite();
    }
    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCouleur(String couleur) {
        cadre.setFill(Color.web(couleur));
        this.couleur = couleur;
    }

    public void setCapacite(double capacite) {
        this.capacite = capacite;
        verifieConformite();
    }

    public void setZoom(int zoom) {
        this.zoom = zoom;
    }

    public void setRatioX(double ratioX) {
        this.ratioX = ratioX;
    }

    public void setRatioY(double ratioY) {
        this.ratioY = ratioY;
    }

    public void setMyId(Integer id) {
        this.id = id;
    }
    
    
    @Override
    public void setPosX(double x) {
        this.posX = x;
        this.setLayoutX(x);
    }
    @Override
    public void setPosY(double y) {
        this.posY = y;
        this.setLayoutY(y);
    }
    @Override
    public void setLongueur(double w){
        //On met à jour la longueur
        this.longueur = w;
        this.setWidth(w);
        cadre.setWidth(w);
    }
    @Override
    public void setHauteur(double h){
        //On met à jour la hauteur
        this.longueur = h;
        this.setHeight(h);
        cadre.setHeight(h);
        

    }
    @Override
    public void setPosXReal(double x) {
         this.posXReal = x;
    }
    @Override
    public void setPosYReal(double y) {
        this.posYReal = y;
    }
    @Override
    public void setLongueurReal(double l) {
        this.longueurReal = l;
        this.setZoomX(zoom, ratioX);
    }
    @Override
    public void setHauteurReal(double h) {
        this.hauteurReal = h;
        this.setZoomY(zoom, ratioY);
    }
    @Override
    public void setZoomX(int zoom, double ratioX) {
        this.zoom = zoom;
        this.ratioX = ratioX;
        //On replace la station
        this.setLongueur(zoom*this.ratioX*this.getLongueurReal());
        this.setPosX(zoom*this.ratioX*this.getPosXReal());
        //On replace la sortie
        outPoint.setPosXInEquipement(cadre.getWidth());
        verifieConformite();
    }
    @Override
    public void setZoomY(int zoom, double ratioY) {
        this.zoom = zoom;
        this.ratioY = ratioY;
        //On replace la station
        this.setHauteur(zoom*this.ratioY*this.getHauteurReal());
        this.setPosY(zoom*this.ratioY*this.getPosYReal());
        //On replace la sortie
        outPoint.setPosYInEquipement(cadre.getHeight()/2);
        verifieConformite();
    }
    @Override
    public void setEntreeEquipement(List<EntreeEquipementUI> l) {
    }
    @Override
    public void setSortieEquipement(List<SortieEquipementUI> l) {
        this.outPoint = l.get(0);
    }

    @Override
    public void verifieConformite() {
        if(outPoint.getConvoyeur()==null){
            if(!outPoint.getStyleClass().contains("equipement-error"))
                outPoint.getStyleClass().add("equipement-error");
        }
        else{
            outPoint.getStyleClass().remove("equipement-error");
        }
        
        if(calculMasseEntreeTotale()>this.capacite){
            if(!cadre.getStyleClass().contains("equipement-error"))
                cadre.getStyleClass().add("equipement-error");
            this.estConforme = false;
        }
        else{
            cadre.getStyleClass().remove("equipement-error");
            this.estConforme = true;
        }
    }
    
       
    private double calculMasseEntreeTotale(){
        double masseEntreeUsine =0;
        Iterator<String> iterStr = fluxEntrants.keySet().iterator();
        while(iterStr.hasNext()){
            masseEntreeUsine += fluxEntrants.get(iterStr.next());
        }
        return masseEntreeUsine;
    }

}
