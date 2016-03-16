package GarbageCollector.presentation.menu;

import GarbageCollector.presentation.util.ButtonIcon;
import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class MenuBar extends HBox {

    public MenuBar(ArrayList<ButtonIcon> listButton) 
    {
        int height = 50;
        
        this.setPrefHeight(height);
        this.setMinHeight(height);
        this.setMaxHeight(height);
                
                    //LOGO
        ImageView logoview = new ImageView(new Image(getClass().getResourceAsStream("/Ressources/Images/logo.png")));
        logoview.setFitWidth(30);
        logoview.setPreserveRatio(true);
        logoview.setSmooth(true);
        logoview.setCache(true);
        logoview.getStyleClass().add("logo");
        this.getChildren().add(logoview); 
        
                    //TITRE
        Label title = new Label("Recycl App");
        title.setPrefWidth(150);
        title.setMinWidth(150);
        title.setMaxWidth(150);
        title.getStyleClass().addAll("titleApp", "font-Black", "text-big" );
        this.getChildren().add(title);

                //Button
        IconsBar iconBar = new IconsBar(listButton, height);
        
        
        this.setSpacing(10);
        this.setPadding(new Insets(0, 0, 0, 5));
        this.getChildren().add(iconBar);
        this.getStyleClass().add("tool-bar");
          
    }
    
}