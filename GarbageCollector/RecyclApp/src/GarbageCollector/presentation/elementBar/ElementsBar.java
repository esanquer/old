
package GarbageCollector.presentation.elementBar;

import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.scene.layout.VBox;


public class ElementsBar extends VBox {

    public ElementsBar(ArrayList<Element> listElement) 
    {
        int width = 70;
        
        this.setPrefWidth(width);
        this.setMinWidth(width);
        this.setMaxWidth(width);

        this.setSpacing(25);
        this.setPadding(new Insets(20, 5, 0, 5));
        this.getStyleClass().add("elementsBar");
        for(Element b : listElement)
            this.getChildren().add(b);
    }
}