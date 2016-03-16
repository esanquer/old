package GarbageCollector.presentation.graph.equipement;

import GarbageCollector.presentation.event.StaticHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javafx.event.EventHandler;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class JonctionUI extends Pane implements EquipementUI, ComposantUI
{
    
        /*##############################
                 ATRIBUT
         ##############################*/
    
            /*=======================
                    Data 
            =========================*/
    //Les atributs de base d'une Station (A binder !!)
    private Integer id;
    private double posXReal = 0;
    private double posYReal = 0;
    private double longueurReal = 0;  //en px
    private double hauteurReal = 0;   //en px
    private String nom;
    private String description;
    private int nbEntree = 0;
    private String couleur;
    private double capacite;
    
    private List<EntreeEquipementUI> listeEntree = new ArrayList<>();
    private SortieEquipementUI outPoint;
    
    private HashMap<String,Double> fluxSortant ;
    
        /*===============================
            Data Composant UI en px
        =================================*/
    //Les atribut de l'objet Graphique DataUI
    private double longueur = 50;
    private double hauteur = 50;
    private double posX = 0;
    private double posY = 0;
    private final Circle cadre;
    private int zoom = 0;
    private double ratioX = 0, ratioY = 0;

        /*##############################
                CONSTRUCTEUR
        ##############################*/
    
    public JonctionUI(){
    cadre = new Circle();
        cadre.setStroke(Color.BLACK);
        cadre.setStrokeWidth(2);
        cadre.setFill(Color.WHITE);
        cadre.setRadius(hauteur/2);
        cadre.setCenterX(longueur/2);
        cadre.setCenterY(hauteur/2);
        
        cadre.getStyleClass().add("equipement");
        this.getChildren().add(cadre);

        outPoint = new SortieEquipementUI(this.getLongueurReal(), this.getHauteurReal()/2,this);
        this.getChildren().add(outPoint);
    }
    public JonctionUI(int zoom, double ratioX, double ratioY, int id, double posXReal, double posYReal, double longueurReal, 
                     double hauteurReal, String nom, String description, String couleur, double capacite, int nbEntree)
    {
        this.id = id;
            /*========================
                Init Variable Data
            =========================*/
        this.nbEntree = nbEntree;
        this.posXReal = posXReal;
        this.posYReal = posYReal;
        this.longueurReal = longueurReal;
        this.hauteurReal = hauteurReal;
        this.nom = nom;
        this.description = description;
        this.couleur = couleur;
        this.capacite = capacite;
        
        this.ratioX = ratioX;
        this.ratioY = ratioY;
        this.couleur = couleur;
        
            /*====================
                Init Composant
            =====================*/
        
        this.couleur = "#FFFFFF";
        
        cadre = new Circle();
        cadre.setStroke(Color.BLACK);
        cadre.setStrokeWidth(2);
        cadre.setFill(Color.WHITE);
        cadre.setRadius(hauteur/2);
        cadre.setCenterX(longueur/2);
        cadre.setCenterY(hauteur/2);
        
        cadre.getStyleClass().add("equipement");
        this.getChildren().add(cadre);

        outPoint = new SortieEquipementUI(this.getLongueurReal(), this.getHauteurReal()/2,this);
        this.getChildren().add(outPoint);

        for(int i=0; i<this.nbEntree; i++){
            EntreeEquipementUI s = new EntreeEquipementUI(this.getLongueurReal(), this.getHauteurReal()/2,this);
            this.listeEntree.add(s);
            this.getChildren().add(s);
        }
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
                content.putString("Deplacement_Jonction");
                db.setContent(content);
            }
        });  
        
        this.setOnDragDone(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                if (event.getTransferMode() == TransferMode.MOVE) {
                }
                event.consume();
            }
        });
        
        this.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) { 
                if((mouseEvent.getTarget() instanceof Circle) && 
                    !(mouseEvent.getTarget() instanceof EntreeEquipementUI) && 
                    !(mouseEvent.getTarget() instanceof SortieEquipementUI) 
                ){
                    StaticHandler.selectionJonction((JonctionUI)mouseEvent.getSource());
                }
            }
        });  
        this.getStyleClass().add("equipement");
    }

        /*############################
            GESTION ENTREES/SORTIES
        ############################*/
    
        //Recalibrage des sorties
    private void revalidatePosEntrees (){
        if(this.nbEntree == 1){
            this.listeEntree.get(0).setPosXInEquipement(cadre.getCenterX()-this.longueur/2);
            this.listeEntree.get(0).setPosYInEquipement(cadre.getCenterY());
        }
        else{
            double espacementStation = Math.PI/(2*(this.nbEntree/2 + 1));
            int incideEntree=0;
            int borne = this.nbEntree/2+1;
            for(int i=-this.nbEntree/2; i<borne; i++){
                if(i!=0){
                    this.listeEntree.get(incideEntree).setPosXInEquipement(cadre.getCenterX()-(this.getLongueurReal()/2)*Math.cos(i*espacementStation)*zoom*ratioX);
                    this.listeEntree.get(incideEntree).setPosYInEquipement(cadre.getCenterY()+(this.getLongueurReal()/2)*Math.sin(i*espacementStation)*zoom*ratioX);
                    incideEntree++;
                }
                if( (this.nbEntree%2!=0) && i==0){
                    this.listeEntree.get(incideEntree).setPosXInEquipement(cadre.getCenterX()-this.longueur/2);
                    this.listeEntree.get(incideEntree).setPosYInEquipement(cadre.getCenterY());
                    incideEntree++;
                }
                
            }
        }
    }
    
    public void ajouterEntree(EntreeEquipementUI e){
        if(this.listeEntree.size()<7){
            this.listeEntree.add(e);
            this.nbEntree++;
            revalidatePosEntrees();
            this.getChildren().add(e);
        }
    }
        
    public void supprimeEntree(EntreeEquipementUI e){
        if(this.listeEntree.size()>1){
            this.listeEntree.remove(e);
            this.nbEntree--;
            revalidatePosEntrees();
            this.getChildren().remove(e);
        }
    }
    
    @Override
    public void supprimeEntrees() {
        for(int i=0; i<this.nbEntree; i++){
            StaticHandler.supprimeEntreeEquipement(this.listeEntree.get(i));
        }
    }

    @Override
    public void supprimeSorties() {
        StaticHandler.supprimeSortieEquipement(outPoint);
    }

    @Override
    public void attacherEntreesEtSorties() {
        for (EntreeEquipementUI listeEntree1 : this.listeEntree) {
            listeEntree1.setEquipement(this);
        }
        this.outPoint.setEquipement(this);
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

    
        /*##############################
                ACCESSEUR
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
        return this.listeEntree;
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
    @Override
    public int getMyId() {
        return id;
    }
    
    public String getNom() {
        return nom;
    }
    public String getDescription() {
        return description;
    }

    public String getCouleur() {
        return couleur;
    }
    public double getCapacite() {
        return capacite;
    }

    public int getNbEntree() {
        return nbEntree;
    }

    public HashMap<String, Double> getFluxSortant() {
        return fluxSortant;
    }


    
    
        /*##############################
                MODIFICATEUR
        ##############################*/

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
        this.hauteur = w;
        this.setWidth(w);
        this.setHeight(w);
        cadre.setCenterX(this.longueur/2);
        cadre.setCenterY(this.longueur/2);
        cadre.setRadius(this.longueur/2);
    }
    @Override
    public void setHauteur(double h){}
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
        setZoomX(this.zoom, this.ratioX);
    }
    @Override
    public void setHauteurReal(double h) {
        this.hauteurReal = h;
        setZoomY(this.zoom, this.ratioY);
    }
    @Override
    public void setZoomX(int zoom, double ratioX) {
        this.zoom = zoom;
        this.ratioX = ratioX;
        //On replace la station
        this.setLongueur(zoom*ratioX*this.longueurReal);
        this.setPosX(zoom*ratioX*this.getPosXReal());
        
        //On replace la sortie
        outPoint.setPosXInEquipement(this.zoom*ratioX*this.getLongueurReal());
        outPoint.setPosYInEquipement(this.zoom*ratioX*(this.getLongueurReal()/2));
        
        //On replace les entrées
        revalidatePosEntrees();
    }
    @Override
    public void setZoomY(int zoom, double ratioY) {
        this.zoom = zoom;
        this.ratioY = ratioY;
        
        //On replace la sortie
        this.setPosY(zoom*ratioY*this.getPosYReal());
        this.setLongueur(zoom*ratioX*this.getLongueurReal());
        
        //On replace la sortie
        outPoint.setPosXInEquipement(this.zoom*ratioX*this.getLongueurReal());
        outPoint.setPosYInEquipement(this.zoom*ratioX*(this.getLongueurReal()/2));
        
        //On replace les entrées
        revalidatePosEntrees();
    }
    @Override
    public void setEntreeEquipement(List<EntreeEquipementUI> l) {
        this.listeEntree = l;
        this.nbEntree = l.size();
    }
    @Override
    public void setSortieEquipement(List<SortieEquipementUI> l) {
        this.outPoint=l.get(0);
    }
    
    public void setNbEntree(Integer nbEntree) {
        /*if(nbEntree>0)
        {
            int diff = nbEntree - this.nbEntree;

            if(diff>0){  //Ajout
                for(int i=0; i<diff; i++){
                    EntreeEquipementUI s = new EntreeEquipementUI(this.longueur, this.hauteur/2,this);
                    this.listeEntree.add(s);
                    this.getChildren().add(s);
                }
                revalidatePosEntrees();
            }
            else if(diff<0){      //Suppression
                int size = this.getChildren().size()-1;
                for(int i=size; i>size+diff;i--){
                    this.listeEntree.remove(i-2);
                    this.getChildren().remove(i);
                }
                revalidatePosEntrees();
            }
        }*/
        this.nbEntree = nbEntree ;
    }
    public void setMyId(Integer id) {
        this.id = id;
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
    public void setFluxSortant(HashMap<String, Double> fluxSortant) {
        this.fluxSortant = fluxSortant;
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
        
        for (EntreeEquipementUI s : this.listeEntree) {
            if(s.getConvoyeur()==null){
                if(!s.getStyleClass().contains("equipement-error"))
                    s.getStyleClass().add("equipement-error");
            }
            else{
                s.getStyleClass().remove("equipement-error");
            }
        }}
}  
