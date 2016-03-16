
package GarbageCollector.presentation.menu;

import GarbageCollector.presentation.util.ButtonIcon;
import java.util.ArrayList;
import java.util.Iterator;
import javafx.geometry.Insets;
import javafx.scene.layout.HBox;

public class IconsBar extends HBox
{  

    public IconsBar(ArrayList<ButtonIcon> listButton, int height)
    {
        this.setPrefHeight(height);
        this.setMinHeight(height);
        this.setMaxHeight(height);

        this.setSpacing(0);
        this.setPadding(new Insets(0, 0, 6, 0));
        Iterator<ButtonIcon> i = listButton.iterator();
        while(i.hasNext())
        {
            this.getChildren().add(i.next()); 
        }
    }
    
    public IconsBar(ArrayList<ButtonIcon> listButton, int height, String cssClass)
    {
        this.setPrefHeight(height);
        this.setMinHeight(height);
        this.setMaxHeight(height);

        this.setSpacing(0);
        this.setPadding(new Insets(0, 0, 6, 0));
        Iterator<ButtonIcon> i = listButton.iterator();
        while(i.hasNext()){
            this.getChildren().add(i.next()); 
        }
        this.getStyleClass().add(cssClass);
    }
}
