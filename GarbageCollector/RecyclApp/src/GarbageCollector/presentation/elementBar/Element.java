
package GarbageCollector.presentation.elementBar;

import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Element extends Button{
    
    private final ImageView icon;
    private final Tooltip tooltip;
    private boolean active;
            
    public Element(String iconName, int height)
    {
        this.setPrefHeight(height);
        this.setMinHeight(height);
        this.setMaxHeight(height);
        icon= new ImageView(new Image(getClass().getResourceAsStream("/Ressources/Images/icons/"+iconName+".png")));
        this.setGraphic(icon);
        this.tooltip = new Tooltip(""+iconName);
        this.setTooltip(tooltip);
        this.getStyleClass().add("element");
        this.active=false;
    }
    
    public void changeActive(){
        this.active = !this.active;
    }

    public boolean isActive() {
        return active;
    }
    
}
