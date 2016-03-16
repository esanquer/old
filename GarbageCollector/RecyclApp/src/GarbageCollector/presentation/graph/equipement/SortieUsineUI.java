
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

public class SortieUsineUI extends Pane implements EquipementUI, ComposantUI
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
    
    private EntreeEquipementUI entrypoint;
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
    
    public SortieUsineUI(){
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
        
        entrypoint = new EntreeEquipementUI(0, this.hauteur/2,this);
        this.getChildren().add(entrypoint);
    }
    public SortieUsineUI(int zoom, double ratioX, double ratioY, int id, double posXReal, double posYReal, 
                        double longueurReal, double hauteurReal, String nom, String description, 
                        String couleur, double capacite) {
        
            /*========================
                Init Variable Data
            =========================*/
        this.fluxEntrants = new HashMap<>();
        this.id = id;
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
        
        entrypoint = new EntreeEquipementUI(0, this.hauteur/2,this);
        this.getChildren().add(entrypoint);
        
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
                    // TODO liaison avec le controleur.
                }
                event.consume();
            }
        });
        
        this.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) { 
                if(mouseEvent.getTarget() instanceof Rectangle)
                    StaticHandler.selectionSortieUsine((SortieUsineUI)mouseEvent.getSource());
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
    public void supprimeSorties() {}
    
    @Override
    public void supprimeEntrees() {
        StaticHandler.supprimeEntreeEquipement(entrypoint);
    }

    @Override
    public void attacherEntreesEtSorties() {
        this.entrypoint.setEquipement(this);
    }
    
    
        /*##############################
                   ACCESSEURS
        ##############################*/

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
        ArrayList<EntreeEquipementUI> l = new ArrayList<>();
        l.add(entrypoint);
        return l;
    }
    @Override
    public List<SortieEquipementUI> getSortieEquipement() {
        return new ArrayList<>();
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
    
    public String getNom() {
        return nom;
    }
    public String getDescription() {
        return description;
    }
    @Override
    public int getMyId() {
        return id;
    }
    public double getCapacite() {
        return capacite;
    }
    public String getCouleur() {
        return couleur;
    }

    public HashMap<String, Double> getFluxEntrants() {
        return fluxEntrants;
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
        this.setLongueur(zoom*ratioX*this.getLongueurReal());
        this.setPosX(zoom*ratioX*this.getPosXReal());
        //On replace l'entrée
        entrypoint.setPosXInEquipement(0);
        verifieConformite();
    }
    @Override
    public void setZoomY(int zoom, double ratioY) {
        this.zoom = zoom;
        this.ratioY = ratioY;
        //On replace la station
        this.setHauteur(zoom*ratioY*this.getHauteurReal());
        this.setPosY(zoom*ratioY*this.getPosYReal());
        //On replace l'entrée
        entrypoint.setPosYInEquipement(cadre.getHeight()/2);
        verifieConformite();
    }
    @Override
    public void setEntreeEquipement(List<EntreeEquipementUI> l) {
        this.entrypoint=l.get(0);
    }
    @Override
    public void setSortieEquipement(List<SortieEquipementUI> l) {
        
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
    public void verifieConformite() {
        if(entrypoint.getConvoyeur()==null){
            if(!entrypoint.getStyleClass().contains("equipement-error"))
                entrypoint.getStyleClass().add("equipement-error");
            
        }
        else{
            entrypoint.getStyleClass().remove("equipement-error");
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
        Iterator<String> iterStr = this.getEntreeEquipement().get(0).getFluxMatiere().keySet().iterator();
        while(iterStr.hasNext()){
            masseEntreeUsine += this.getEntreeEquipement().get(0).getFluxMatiere().get(iterStr.next());
        }
        return masseEntreeUsine;
    }
 
     
}
