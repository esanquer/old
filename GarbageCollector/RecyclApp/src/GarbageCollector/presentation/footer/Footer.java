
package GarbageCollector.presentation.footer;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;


public class Footer extends BorderPane{
    
    private final Label position;
    private final SliderZoom sliderZoom;
    
        /*##############################
                 CONSTRUCTEUR
        ##############################*/
    
    public Footer(){
        this.setPrefSize(1100, 20);
        this.setMinHeight(20);
        this.setMaxHeight(20);
        

        position = new Label("x= 0 m  | y= 0 m");
        position.setTranslateX(5);
        position.setTranslateY(3);
        position.getStyleClass().add("position");
        
        sliderZoom = new SliderZoom(this.getPrefHeight());
        
        this.getStyleClass().add("footer");
        this.setLeft(position);
        this.setRight(sliderZoom);
    }
         
    public void setPositionSouris(double x, double y){
        position.setText("x= "+(int)x+" m  | y= "+(int)y+" m");
    } 
    
    
}
