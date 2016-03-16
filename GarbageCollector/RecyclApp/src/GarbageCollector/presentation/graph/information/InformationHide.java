
package GarbageCollector.presentation.graph.information;

import GarbageCollector.presentation.event.StaticHandler;
import GarbageCollector.presentation.util.ButtonIcon;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class InformationHide extends VBox{
    
    public InformationHide() 
    {
        int width = 50;
        
        this.setPrefWidth(width);
        this.setMinWidth(width);
        this.setMaxWidth(width);
        
        ButtonIcon showInformation = new ButtonIcon("arrow-left", 40);
        showInformation.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override 
            public void handle(MouseEvent mouseEvent) 
            {
                StaticHandler.toggleInfoPan();
            }
        });    
                
        this.getChildren().add(showInformation);
        this.getStyleClass().add("stationInformation");
    }
    
}
