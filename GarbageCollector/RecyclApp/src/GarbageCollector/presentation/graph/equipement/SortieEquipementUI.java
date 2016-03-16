
package GarbageCollector.presentation.graph.equipement;

import GarbageCollector.presentation.graph.convoyeur.ConvoyeurUI;
import GarbageCollector.presentation.event.StaticHandler;
import GarbageCollector.presentation.graph.convoyeur.PointUI;
import java.util.HashMap;
import java.util.Iterator;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class SortieEquipementUI extends Circle implements ComposantUI, PointUI
{
        /*##############################
                 ATRIBUT
        ##############################*/
    
    private int idInEquipement;
    private final double rayon=5;
    private double posXInEquipement = 0;
    private double posYInEquipement = 0;
    private final DoubleProperty posX = new SimpleDoubleProperty(0);
    private final DoubleProperty posY = new SimpleDoubleProperty(0);
    
    private ConvoyeurUI convoyeur = null;
    private EquipementUI equipement;
    
    private HashMap<String, Double> fluxMatiere = new HashMap<>();
    
        /*##############################
                CONSTRUCTEUR
        ##############################*/
    
    public SortieEquipementUI(){}
    public SortieEquipementUI(double posX, double posY, EquipementUI equipement) {
        
        this.equipement = equipement;
        
        this.fluxMatiere = new HashMap<>();
            /*====================
                Init Composant
            =====================*/
        this.idInEquipement = equipement.getSortieEquipement().size();
        
        setPosXInEquipement(posX);
        setPosYInEquipement(posY);
        
        this.setRadius(rayon);
        this.setFill(Color.WHITE);
        this.setStroke(Color.BLACK);
  
        this.getStyleClass().add("equipement");
        
        final EquipementUI e = equipement;
        this.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                    StaticHandler.selectionSortieEquipement((SortieEquipementUI)mouseEvent.getSource());
            }
        }); 
    }

        /*##############################
                SELECTION
        ##############################*/
    
    @Override
    public void select() {
        this.getStyleClass().add("equipement-active");
    }

    @Override
    public void unselect() {
        this.getStyleClass().remove("equipement-active");
    }    
  
    public void verifieConformite(){
        if(this.getConvoyeur()==null){
            this.getStyleClass().add("equipement-error");
        }
        else{
            this.getStyleClass().remove("equipement-error");
            this.getConvoyeur().verifieConformite();
        }
    }
        
        /*##############################
                 ACCESSEUR 
        ##############################*/
    public HashMap<String, Double> getFluxMatiere() {
        return fluxMatiere;
    }
    @Override
    public DoubleProperty getPosX() {return this.posX;}
    @Override
    public DoubleProperty getPosY() {return this.posY;}
    @Override
    public double getPosXReal() {return 0;}
    @Override
    public double getPosYReal() {return 0;}
    
    public double getPosXInEquipement() {
        return posXInEquipement;
    }
        
    public double getPosYInEquipement() {
        return posYInEquipement;
    }
    
    public double getRayon(){return this.rayon;}
    
    public ConvoyeurUI getConvoyeur() {
        return convoyeur;
    }
    
    public EquipementUI getEquipement() {
        return equipement;
    }

    public int getMyId() {
        return idInEquipement;
    }
    
    @Override
    public int getZoom() {return this.equipement.getZoom();}
    @Override
    public double getRatioX() {return  this.equipement.getRatioX();}
    @Override
    public double getRatioY() {return  this.equipement.getRatioY();}

        /*##############################
                  MODIFICATEUR
        ##############################*/

    public void setFluxMatiere(HashMap<String, Double> fluxMatiere) {
        this.fluxMatiere = fluxMatiere;
    }

    
    public void setPosXInEquipement(double x) {
        this.posXInEquipement = x;
        this.setCenterX(x);
        this.posX.set(equipement.getPosX() + posXInEquipement);
    }
    public void setPosYInEquipement(double y) {
        this.posYInEquipement = y;
        this.setCenterY(y);
        this.posY.set(equipement.getPosY() + posYInEquipement);
    }
    
    public void setEquipement(EquipementUI equipement) {
        this.equipement = equipement;
    }
    public void setMyId(int id) {
        this.idInEquipement = id;
    }
    public void setConvoyeur(ConvoyeurUI convoyeur) {
        this.convoyeur = convoyeur;
        verifieConformite();
    }

    @Override
    public void setPosX(double posX) {}
    @Override
    public void setPosY(double posY) {}
    @Override
    public void setPosXReal(double x) {}
    @Override
    public void setPosYReal(double y) {}
    @Override
    public void setZoomX(int zoom, double ratioX) {}
    @Override
    public void setZoomY(int zoom, double ratioY) {}

    
  
    
    
    public double calculMasseEntreeTotale(){
        double masseEntreeUsine =0;
        Iterator<String> iterStr = fluxMatiere.keySet().iterator();
        while(iterStr.hasNext()){
            masseEntreeUsine += fluxMatiere.get(iterStr.next());
        }
        return masseEntreeUsine;
    }
    

    
}