
package GarbageCollector.presentation.graph.information;

import GarbageCollector.presentation.event.StaticHandler;
import GarbageCollector.presentation.graph.equipement.EntreeEquipementUI;
import GarbageCollector.presentation.menu.IconsBar;
import GarbageCollector.presentation.util.ButtonIcon;
import GarbageCollector.presentation.util.ListeUISetting;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class InformationEntreeEquipement extends VBox {
   
        /*##############################
                 ATRIBUTS
        ##############################*/
    
    private ListeUISetting listUI;
    private EntreeEquipementUI entreeEquipement;
    
    private final Label title;
    private Label matriceRecuperationLabel ;
    private final ScrollPane scroll;
    private final IconsBar iconBar;
    
    
        /*##############################
                 CONSTRUCTEUR
        ##############################*/
    
    public InformationEntreeEquipement(){
        
        this.setPrefSize(230, 400);
            /*==========================
                    Bar d'outil
            ==========================*/

        final ButtonIcon   dropInfoIcon = new ButtonIcon("arrow-right", 50), 
                           matrixIcon = new ButtonIcon("matrix", 40),
                           deleteIcon = new ButtonIcon( "delete", 40);
        
            //Boutton save
        dropInfoIcon.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override 
            public void handle(MouseEvent mouseEvent) 
            {
                 StaticHandler.toggleInfoPan();
            }
        });
        
        matrixIcon.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override 
            public void handle(MouseEvent mouseEvent){
                listUI.toggleAffichage();
                if(listUI.isAfficheTauxRecuperatioMode())
                    matriceRecuperationLabel.setText("Masse en Entree (kg/h) :" );
                else
                    matriceRecuperationLabel.setText("Tauf de Purete (%) :");
            }
        });
                
        deleteIcon.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override 
            public void handle(MouseEvent mouseEvent) 
            {
                StaticHandler.supprimeEntreeEquipement(entreeEquipement);
            }
        });
        
        ArrayList<ButtonIcon> listIcon = new ArrayList<>();
        listIcon.add(dropInfoIcon);
        listIcon.add(matrixIcon);
        listIcon.add(deleteIcon);
        
        iconBar = new IconsBar(listIcon, 40, "infoBar");
        
            /*==========================
                    TITRE
            ==========================*/
        
        title = new Label("Entree");
        title.setPrefSize(230, 50);
        title.getStyleClass().addAll("tileInformation","font-Bold","text-big");

        dropInfoIcon.setPrefWidth(50);
        HBox titleBox = new HBox();
        titleBox.getChildren().addAll(dropInfoIcon, title);
        
        
            /*==========================
                Matrice de récupération
            ==========================*/
        
        matriceRecuperationLabel = new Label("Description des flux :");
        matriceRecuperationLabel.getStyleClass().addAll("labelInformation","font-Bold","text-medium");

        listUI = new ListeUISetting(new HashMap<String, Double>(),null);
        
        scroll = new ScrollPane();
        scroll.setMaxWidth(215);
        scroll.setMinWidth(215);
        this.scroll.setPrefHeight(180);
        scroll.setMaxHeight(200);
        scroll.setContent(listUI);
        scroll.getStyleClass().addAll("matrice","scroolMatrice");
        
        this.setSpacing(0);
        this.getChildren().addAll(titleBox, iconBar, matriceRecuperationLabel, scroll);
        this.getStyleClass().add("stationInformation");
    }
    
    
        /*##############################
                ENTREE EQUIPEMENT
        ##############################*/ 
    
    public void setEntreeEquipement(EntreeEquipementUI e){
        this.entreeEquipement = e;
        this.listUI.setInstance(e.getFluxMatiere(), null, false);
    }
}
