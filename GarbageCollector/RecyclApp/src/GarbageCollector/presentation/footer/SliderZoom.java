
package GarbageCollector.presentation.footer;

import GarbageCollector.presentation.util.ButtonIcon;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.control.Slider;
import javafx.scene.control.SliderBuilder;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;


public class SliderZoom extends HBox{
    
    
    final ButtonIcon zoomPlusIcon, zoomMinusIcon;
    final Slider slider;
    
        /*##############################
                 CONSTRUCTEUR
        ##############################*/
    
    public SliderZoom(double h){
        
        this.setPrefHeight(h);

        slider = SliderBuilder.create() 
                                .min(1).max(5)
                                .value(1)
                                .minorTickCount(0)
                                .majorTickUnit(1)
                                .showTickLabels(false)
                                .showTickMarks(false)
                              .build();
        slider.setBlockIncrement(1);
        slider.setSnapToTicks(true);
        slider.setMaxWidth(80);
        slider.setOrientation(Orientation.HORIZONTAL);
        slider.setTranslateY(3);
        
        slider.valueProperty().addListener(new ChangeListener(){
            @Override public void changed(ObservableValue o, Object oldVal, Object newVal){
                revalidateButton();
            }
        });
        slider.getStyleClass().add("slider");
        
        
        zoomPlusIcon = new ButtonIcon("zoomIn", (int)h);
        zoomMinusIcon = new ButtonIcon("zoomOut", (int)h);
        revalidateButton();
        zoomPlusIcon.setMaxWidth(h);
        zoomPlusIcon.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override 
            public void handle(MouseEvent mouseEvent) {
                slider.increment();
                revalidateButton();
            }
        });     
        
        zoomMinusIcon.setMaxWidth(h);
        zoomMinusIcon.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override 
            public void handle(MouseEvent mouseEvent) 
            {
                slider.decrement();
                revalidateButton();
            }
        });
        

        
        
        this.getChildren().addAll(zoomMinusIcon, slider, zoomPlusIcon);
    }
         
    private void revalidateButton(){
        if(slider.getValue()<2){
            zoomMinusIcon.getStyleClass().add("btn-disable");
            zoomPlusIcon.getStyleClass().remove("btn-disable");
        }
        else if(slider.getValue()>4){
            zoomMinusIcon.getStyleClass().remove("btn-disable");
            zoomPlusIcon.getStyleClass().add("btn-disable");
        }
        else{
            zoomMinusIcon.getStyleClass().remove("btn-disable");
            zoomPlusIcon.getStyleClass().remove("btn-disable");
        }
    }
    
}