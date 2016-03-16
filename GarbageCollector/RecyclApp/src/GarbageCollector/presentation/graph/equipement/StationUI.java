package GarbageCollector.presentation.graph.equipement;

import GarbageCollector.presentation.event.StaticHandler;
import com.sun.javafx.scene.control.skin.LabelSkin;
import com.sun.javafx.scene.control.skin.LabeledText;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javax.imageio.ImageIO;

public class StationUI extends Pane implements EquipementUI, ComposantUI
{
        /*##############################
                 ATRIBUT
         ##############################*/
    
            /*=======================
                    Data 
            =========================*/
    private Integer id;
    private double posXReal;
    private double posYReal;
    private double longueurReal;  //en px
    private double hauteurReal;   //en px
    private String nom;
    private String description;
    private int nbSorties;
    private String couleur;
    private double capacite = 1000;
    private String imageFilePath = "";
    private String imageFileName = "";
    private boolean showName = true, estConforme =false;
    ImageView imageView;
    
    private List<SortieEquipementUI> listeSortie = new ArrayList<>();
    private EntreeEquipementUI entrypoint;

        /*===============================
            Data Composant UI en px
        =================================*/
    //Les atribut de l'objet Graphique DataUI
    private double longueur = 100;
    private double hauteur = 70;
    private double posX = 0;
    private double posY = 0;
    private Label cadre;
    private int zoom;
    private double ratioX, ratioY;
    private HashMap<Integer, HashMap<String, Double>> matrice  = new HashMap();
    private HashMap<String, Double> descriptionEntree = new HashMap();

        /*##############################
                CONSTRUCTEUR
        ##############################*/
    
    public StationUI(){
        cadre = new Label("");
        cadre = new Label("No Name");
        cadre.setStyle("-fx-background-color: "+ couleur);
        cadre.setAlignment(Pos.CENTER);
        cadre.getStyleClass().addAll("nameEquipement","font-Bold");
        this.getChildren().add(cadre);
    }
    
    public StationUI(int zoom, double ratioX, double ratioY, int id, double posXReal, double posYReal, double longueurReal, 
                     double hauteurReal, String nom, String description, 
                     String couleur, double capacite, int nbSorties) {
            /*========================
                Init Variable Data
            =========================*/
        this.id = id;
        this.posXReal = posXReal;
        this.posYReal = posYReal;
        this.longueurReal = longueurReal;
        this.hauteurReal = hauteurReal;
        this.nom = nom;
        this.description = description;
        this.couleur = couleur;
        this.capacite = capacite;
        this.nbSorties = nbSorties;
        this.matrice = new HashMap<>();
        this.ratioX = ratioX;
        this.ratioY = ratioY;
        
            /*====================
                Init Composant
            =====================*/
        
        this.couleur = "#FFFFFF";

        cadre = new Label("No Name");
        cadre.setStyle("-fx-background-color: "+ couleur);
        cadre.setAlignment(Pos.CENTER);
        cadre.getStyleClass().addAll("nameEquipement","font-Bold");
        this.getChildren().add(cadre);
        
            //Entree
        entrypoint = new EntreeEquipementUI(0, this.getHauteurReal()/2,this);
        entrypoint.setPosXInEquipement(0);
        this.getChildren().add(entrypoint);
        
            //Sortie
        for(int i=0; i<this.nbSorties; i++){
            SortieEquipementUI s = new SortieEquipementUI(this.longueur, this.hauteur/2,this);
            this.listeSortie.add(s);
            this.getChildren().add(s);
        }
        
        this.setZoomX(zoom, ratioX);
        this.setZoomY(zoom, ratioY);
        this.attacherEntreesEtSorties();
        
        createEventHandlers();
    }
    
        
        /*############################
                 Init Evenement
        ############################*/
    
    private void createEventHandlers(){
        
        this.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) { 
                Dragboard db = startDragAndDrop(TransferMode.ANY);
                ClipboardContent content = new ClipboardContent();
                content.putString("Deplacement_Station");
                db.setContent(content);
            }
        });  
        
        this.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) { 
                if(mouseEvent.getTarget() instanceof LabeledText ||
                   mouseEvent.getTarget() instanceof LabelSkin){
                    StaticHandler.selectionStation((StationUI)mouseEvent.getSource());
                }
            }
        });  
    }


        /*############################
             Gestion Entree Sortie
        ############################*/

    @Override
    public void supprimeSorties() {
        for(int i=0; i<this.nbSorties; i++){
            StaticHandler.supprimeSortieEquipement(this.listeSortie.get(i));
        }
    }
    
    @Override
    public void supprimeEntrees() {
        StaticHandler.supprimeEntreeEquipement(entrypoint);
    }

    @Override
    public void attacherEntreesEtSorties() {
        this.entrypoint.setEquipement(this);
        for (SortieEquipementUI listeSortie1 : this.listeSortie) {
            listeSortie1.setEquipement(this);
        }
    }
        
    public void revalidatePosSorties(){
        double espacementStation = ratioY*this.zoom*(this.getHauteurReal()/(this.nbSorties+1));
        for(int i=0; i<this.nbSorties; i++){
            this.listeSortie.get(i).setPosXInEquipement(ratioX*this.zoom*(this.getLongueurReal()));
            this.listeSortie.get(i).setPosYInEquipement((i+1)*espacementStation);
        }
    }
    
    
    public void ajouterSortie(SortieEquipementUI s){
        if(this.listeSortie.size()<7){
            this.listeSortie.add(s);
            this.nbSorties++;
            revalidatePosSorties();
            this.getChildren().add(s);
        }
    }
    
    public void supprimerSortie(SortieEquipementUI s){
        if(this.listeSortie.size()>1){
            this.listeSortie.remove(s);
            this.nbSorties--;
            revalidatePosSorties();
            this.getChildren().remove(s);
        }
    }
    
    
    private double calculMasseEntreeTotale(){
        double masseEntreeUsine =0;
        Iterator<String> iterStr = descriptionEntree.keySet().iterator();
        while(iterStr.hasNext()){
            masseEntreeUsine += descriptionEntree.get(iterStr.next());
        }
        return masseEntreeUsine;
    }
    
        /*##############################
                SELECTION
        ##############################*/
    
    @Override
    public void select() {
        cadre.getStyleClass().add("nameEquipement-active");
    }

    @Override
    public void unselect() {
        cadre.getStyleClass().remove("nameEquipement-active");
    }

    @Override
    public void verifieConformite(){
        if(entrypoint.getConvoyeur()==null){
            if(!entrypoint.getStyleClass().contains("equipement-error"))
                entrypoint.getStyleClass().add("equipement-error");
        }
        else{
            entrypoint.getStyleClass().remove("equipement-error");
        }
        
        for (SortieEquipementUI s : this.listeSortie) {
            if(s.getConvoyeur()==null){
                if(!s.getStyleClass().contains("equipement-error"))
                    s.getStyleClass().add("equipement-error");
            }
            else{
                s.getStyleClass().remove("equipement-error");
            }
        }
        
        if(calculMasseEntreeTotale()>this.capacite){
            if(!cadre.getStyleClass().contains("nameEquipement-error"))
                cadre.getStyleClass().add("nameEquipement-error");
            this.estConforme = false;
        }
        else{
            cadre.getStyleClass().remove("nameEquipement-error");
            this.estConforme = true;
        }
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
    public int getNbSorties() {
        return nbSorties;
    }
    public String getCouleur() {
        return couleur;
    }
    public double getCapacite() {
        return capacite;
    }

    public String getImageFilePath() {
        return imageFilePath;
    }
    public String getImageFileName() {
        return imageFileName;
    }

    public HashMap<Integer, HashMap<String, Double>> getMatrice() {
        return matrice;
    }

    public HashMap<String, Double> getDescriptionEntree() {
        return descriptionEntree;
    }

    public boolean isEstConforme() {
        return estConforme;
    }
    
    
    
        /*##############################
                MODIFICATEUR
        ##############################*/

    public void setEstConforme(boolean estConforme) {
        this.estConforme = estConforme;
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
        this.longueur = w;
        this.setWidth(w);
        cadre.setPrefWidth(w);
        if(imageView != null)
            imageView.setFitWidth(w-8);
    }
    @Override
    public void setHauteur(double h){
        this.hauteur = h;
        this.setHeight(h);
        cadre.setPrefHeight(h);
        if(imageView != null)
            imageView.setFitHeight(h-8);
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
        //On replace les entrée
        entrypoint.setPosXInEquipement(0);
        //On replace les sorties
        revalidatePosSorties();
        verifieConformite();
    }
    @Override
    public void setZoomY(int zoom, double ratioY) {
        this.zoom = zoom;
        this.ratioY = ratioY;
        //On replace la station
        this.setHauteur(zoom*ratioY*this.getHauteurReal());
        this.setPosY(zoom*ratioY*this.getPosYReal());

        //On replace les entrée
        entrypoint.setPosYInEquipement(this.zoom*ratioY*(this.getHauteurReal()/2));

        //On replace les sorties
        revalidatePosSorties();
        verifieConformite();
    }

    @Override
    public List<EntreeEquipementUI> getEntreeEquipement() {
        ArrayList<EntreeEquipementUI> l = new ArrayList<>();
        l.add(entrypoint);
        return l;
    }
    @Override
    public List<SortieEquipementUI> getSortieEquipement() {
        return this.listeSortie;
    }
    @Override
    public void setEntreeEquipement(List<EntreeEquipementUI> l) {
        this.entrypoint = l.get(0);
        verifieConformite();
    }
    @Override
    public void setSortieEquipement(List<SortieEquipementUI> l) {
        this.listeSortie = l;
        verifieConformite();
    }
    
    public void setNbSorties(Integer nbSorties) {
        this.nbSorties = nbSorties;
    }
    
    public void setMyId(Integer id) {
        this.id = id;
    }
    public void setNom(String nom) {
        cadre.setText(nom);
        this.nom = nom;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setCouleur(String couleur) {
        cadre.setStyle("-fx-background-color: "+ couleur);
        this.couleur = couleur;
    }
    public void setCapacite(double capacite) {
        this.capacite = capacite;
    }
    public void setPosX(Double posX) {
        this.posX = posX;
    }
    public void setPosY(Double posY) {
        this.posY = posY;
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
    public void setImageFilePath(String imageFilePath) {
        this.imageFilePath = imageFilePath;
    }
    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }
    public void setShowName(boolean showName) throws IOException {
        this.showName = showName;
        if(!showName && !imageFilePath.equals("")){
            File file = new File(imageFilePath);
            BufferedImage bufferedImage = ImageIO.read(file);
            Image image = SwingFXUtils.toFXImage(bufferedImage, null);
            if(imageView == null){
                imageView = new ImageView(image);
                this.getChildren().add(0, imageView);
            }
            else
                imageView.setImage(image);
            imageView.getStyleClass().add("imageStation");
            imageView.setFitWidth(this.longueur-8);
            imageView.setFitHeight(this.hauteur-8);
            imageView.setTranslateX(3);
            imageView.setTranslateY(3);
            cadre.setText("");
            cadre.setStyle("-fx-background-color: transparent");
            
        }
        else{
            cadre.setStyle("-fx-background-color: "+ couleur);
            cadre.setText(this.nom);
            cadre.setGraphic(null);
        }
    }

    public void setMatrice(HashMap<Integer, HashMap<String, Double>> matrice) {
        this.matrice = matrice;
    }

    public void setDescriptionEntree(HashMap<String, Double> descriptionEntree) {
        this.descriptionEntree = descriptionEntree;
        verifieConformite();
    }

        
        /*##############################
                DESCRIPTEUR
        ##############################*/

    @Override
    public String toString() {
        return "StationUI{" + "id=" + id + ", posXReal=" + posXReal + ", posYReal=" + posYReal 
                            + ", longueurReal=" + longueurReal + ", hauteurReal=" + hauteurReal 
                            + ", description=" + description + ", listeSortie=" + listeSortie 
                            + ", entrypoint=" + entrypoint + ", matrice=" + matrice + '}';
    }

}
