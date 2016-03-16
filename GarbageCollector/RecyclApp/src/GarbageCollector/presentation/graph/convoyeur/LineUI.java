
package GarbageCollector.presentation.graph.convoyeur;

import GarbageCollector.presentation.event.StaticHandler;
import GarbageCollector.presentation.graph.equipement.ComposantUI;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;


public class LineUI extends Line implements ComposantUI
{

        /*##############################
                 ATRIBUT
        ##############################*/

    private final PointUI entree;
    private final PointUI sortie;

    private ConvoyeurUI convoyeur;
    
        /*##############################
                CONSTRUCTEUR
        ##############################*/
    
    public LineUI(PointUI entre, PointUI sorti, ConvoyeurUI c) {
        
        convoyeur =c;
        
        this.setStroke(Color.BLACK);
        this.entree = entre;
        this.sortie = sorti;
        
        this.setStartX(this.sortie.getPosX().get());
        this.setStartY(this.sortie.getPosY().get());
        this.setEndX(this.entree.getPosX().get());
        this.setEndY(this.entree.getPosY().get());
        
        this.sortie.getPosX().addListener(new ChangeListener(){
            @Override public void changed(ObservableValue o, Object oldVal, Object newVal){
                setStartX((double)newVal);
            }
        });
        this.sortie.getPosY().addListener(new ChangeListener(){
            @Override public void changed(ObservableValue o, Object oldVal, Object newVal){
                setStartY((double)newVal);
            }
        });
        this.entree.getPosX().addListener(new ChangeListener(){
            @Override public void changed(ObservableValue o, Object oldVal, Object newVal){
                setEndX((double)newVal);
            }
        });
        this.entree.getPosY().addListener(new ChangeListener(){
            @Override public void changed(ObservableValue o, Object oldVal, Object newVal){
                setEndY((double)newVal);
            }
        });
        
        this.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) { 
                Dragboard db = startDragAndDrop(TransferMode.ANY);
                ClipboardContent content = new ClipboardContent();
                content.putString("Creation_PointConvoyeur");
                System.out.println("DRAG " + mouseEvent.getX());
                db.setContent(content);
            }
        });  
        
        this.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) { 
                
                StaticHandler.selectionConvoyeur(convoyeur);
            }
        }); 
        
        this.getStyleClass().add("convoyeur");
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
    
    public PointUI getEntree() {
        return entree;
    }
    public PointUI getSortie() {
        return sortie;
    }

    public ConvoyeurUI getConvoyeur() {
        return convoyeur;
    }
    
}