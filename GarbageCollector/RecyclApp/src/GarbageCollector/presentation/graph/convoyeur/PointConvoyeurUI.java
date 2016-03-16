
package GarbageCollector.presentation.graph.convoyeur;

import GarbageCollector.presentation.event.StaticHandler;
import GarbageCollector.presentation.graph.equipement.ComposantUI;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.EventHandler;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class PointConvoyeurUI extends Circle implements PointUI, ComposantUI{
    
        /*##############################
                 ATRIBUT
        ##############################*/
    
    private double posXReal, 
                   posYReal;
    private final double ratioX,
                        ratioY;
    
    private final int zoom;
    
    private final DoubleProperty posX = new SimpleDoubleProperty(0), 
                           posY = new SimpleDoubleProperty(0);
    
    private ConvoyeurUI convoyeur;
    
        /*##############################
                CONSTRUCTEUR
        ##############################*/
    
    public PointConvoyeurUI(double posXReal, double posYReal, int zoom, double ratioX, double ratioY, ConvoyeurUI c){
        
        convoyeur =c;
        
        this.posXReal = posXReal;
        this.posYReal = posYReal;
        this.zoom= zoom;
        this.ratioX = ratioX;
        this.ratioY = ratioY;
        this.posX.set(posXReal*zoom*ratioX);
        this.posY.set(posYReal*zoom*ratioY);
        this.setCenterX(this.posX.get());
        this.setCenterY(this.posY.get());
        this.setRadius(5);
        this.setFill(Color.BLACK);
        
        this.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) { 
                Dragboard db = startDragAndDrop(TransferMode.ANY);
                ClipboardContent content = new ClipboardContent();
                content.putString("Deplacement_PointConvoyeur");
                db.setContent(content);
            }
        });  
        
        this.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) { 
                StaticHandler.selectionConvoyeur(convoyeur);
            }
        }); 
        
        this.getStyleClass().add("equipement");
                
    }

        /*##############################
                SELECTION
        ##############################*/
    
    @Override
    public void select() {
        this.getStyleClass().add("convoyeur-active");
    }
    
    @Override
    public void unselect() {
        this.getStyleClass().remove("convoyeur-active");
    }

    
        /*##############################
                ACCESSEUR
        ##############################*/
    
    @Override
    public DoubleProperty getPosX() {
        return posX;
    }
    @Override
    public DoubleProperty getPosY() {
        return posY;
    }
    @Override
    public double getPosXReal() {
        return posXReal;
    }

    @Override
    public double getPosYReal() {
        return posYReal;
    }
    
    @Override
    public int getZoom() {
        return this.zoom;
    }

    @Override
    public double getRatioX() {
        return this.ratioX;
    }

    @Override
    public double getRatioY() {
        return this.ratioY;
    }
    
        /*##############################
                MODIFICATEUR
        ##############################*/
    
    @Override
    public void setPosX(double posX) {
        this.posX.set(posX);
        this.setCenterX(posX);
    }
    @Override
    public void setPosY(double posY) {
        this.posY.set(posY);
        this.setCenterY(posY);
    }
    
    @Override
    public void setPosXReal(double x) {
        this.posXReal = x;
        this.setPosX(x*zoom*ratioX);
    }
    @Override
    public void setPosYReal(double y) {
        this.posYReal = y;
        this.setPosY(y*zoom*ratioX);
    }
    @Override
    public void setZoomX(int zoom, double ratioX) {
        this.setPosX(posXReal*zoom*ratioX);
    }
    @Override
    public void setZoomY(int zoom, double ratioY) {
        this.setPosY(posYReal*zoom*ratioY);
    }

    
}
