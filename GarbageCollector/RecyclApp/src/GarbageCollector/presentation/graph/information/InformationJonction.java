
package GarbageCollector.presentation.graph.information;

import GarbageCollector.presentation.event.StaticHandler;
import GarbageCollector.presentation.graph.equipement.EntreeEquipementUI;
import GarbageCollector.presentation.graph.equipement.JonctionUI;
import GarbageCollector.presentation.menu.IconsBar;
import GarbageCollector.presentation.util.ButtonIcon;
import GarbageCollector.presentation.util.ListeUISetting;
import GarbageCollector.presentation.util.StringifyUI;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class InformationJonction extends VBox implements Information{

        /*##############################
                 ATRIBUTS
        ##############################*/
    
    private final ListeUISetting listUI;
    private JonctionUI jonction;
    
    private final GridPane grid;
    private final TextField capaciteSortie ;
    private final TextField radiusText;
    private Rectangle valorCouleur;
    private ColorPicker colorPicker ;
    private final ScrollPane scroll;
    private final IconsBar iconBar;
    
    private boolean editionMode = false;
            
    
        /*##############################
                 CONSTRUCTEUR
        ##############################*/
    
    public InformationJonction(){
        
        this.setPrefSize(230, 400);
        
        grid = new GridPane();
        grid.setPadding(new Insets(15, 0, 20, 5));
        grid.setVgap(7);

            /*==========================
                    Bar d'outil
            ==========================*/

        final ButtonIcon   dropInfoIcon = new ButtonIcon("arrow-right", 50), 
                           addEntryIcon = new ButtonIcon("add-list", 40),
                           deleteIcon = new ButtonIcon( "delete", 40),
                           gearsIcon = new ButtonIcon("gear", 40);
        
            //Boutton save
        dropInfoIcon.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override 
            public void handle(MouseEvent mouseEvent){
                 StaticHandler.toggleInfoPan();
            }
        });
        
        
        addEntryIcon.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override 
            public void handle(MouseEvent mouseEvent){
                EntreeEquipementUI e = new EntreeEquipementUI(0,0,jonction);
                jonction.ajouterEntree(e);
                StaticHandler.getControleur().ajouterEntreeEquipement(StringifyUI.stringify(e));
                StaticHandler.selectionJonction(jonction);
            }
        });
        
        deleteIcon.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override 
            public void handle(MouseEvent mouseEvent) {
                StaticHandler.supprimeEquipement(jonction);
            }
        });
        
        gearsIcon.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override 
            public void handle(MouseEvent mouseEvent) 
            {
                toggleEditionMode();
            }
        });
        
        ArrayList<ButtonIcon> listIcon = new ArrayList<>();
        listIcon.add(addEntryIcon);
        listIcon.add(deleteIcon);
        listIcon.add(gearsIcon);
        
        iconBar = new IconsBar(listIcon, 40, "infoBar");
        
            /*==========================
                    TITRE
            ==========================*/
        Label title = new Label("Jonction");
        title.setPrefSize(230, 50);
        title.getStyleClass().addAll("tileInformation","font-Bold","text-big");

        dropInfoIcon.setPrefWidth(50);
        HBox titleBox = new HBox();
        titleBox.getChildren().addAll(dropInfoIcon, title);
        
            /*==========================
                    Rayon
            ==========================*/
        
        Label radiusLabel = new Label("Rayon :   ");
        radiusLabel.getStyleClass().addAll("labelInformation","font-Bold","text-medium");
        
        radiusText = new TextField("100");
        radiusText.setPrefColumnCount(10); 
        radiusText.setMaxWidth(60);
        radiusText.setEditable(false);
        radiusText.getStyleClass().addAll("fieldInformation","font-Regular","text-regular");
        
        Label uniteRadius  = new Label("m");
        uniteRadius.getStyleClass().addAll("unitInformation","font-Bold","text-medium");

        grid.add(radiusLabel,0,0,1,1);
        grid.add(radiusText,1,0,1,1);
        grid.add(uniteRadius,2,0,1,1);
        
            /*==========================
                    Capacité
            ==========================*/
        
        Label capaciteLabel = new Label("Capacite: ");
        capaciteLabel.getStyleClass().addAll("labelInformation","font-Bold","text-medium");
        
        capaciteSortie = new TextField("1");
        capaciteSortie.setPrefColumnCount(10); 
        capaciteSortie.setMaxWidth(60);
        capaciteSortie.setEditable(false);
        capaciteSortie.getStyleClass().addAll("fieldInformation","font-Regular","text-regular");
        
        Label uniteCapacity  = new Label("kg/h");
        uniteCapacity.getStyleClass().addAll("unitInformation","font-Bold","text-medium");
        
        grid.add(capaciteLabel,0,1,1,1);
        grid.add(capaciteSortie,1,1,2,1);
        grid.add(uniteCapacity,2,1,2,1);

            /*==========================
                    Couleur
            ==========================*/
        
        Label couleurLabel = new Label("Couleur : ");
        couleurLabel.getStyleClass().addAll("labelInformation","font-Bold","text-medium");
        
        
        valorCouleur = new Rectangle(17, 17);
        valorCouleur.setStroke(Color.BLACK);
        valorCouleur.setFill(Color.WHITE);
        valorCouleur.getStyleClass().addAll("colorInformation");
                
        colorPicker = new ColorPicker();
        colorPicker.setMinWidth(50);
        colorPicker.setValue(Color.BLUE);
        colorPicker.getStyleClass().addAll("colorInformationSetting","split-button");
        colorPicker.setOnAction(new EventHandler() {
            @Override
            public void handle(Event t) {
                colorPicker.setValue(colorPicker.getValue());
                valorCouleur.setFill(Color.web(colorPicker.getValue().toString()));
            }
        });
        grid.add(couleurLabel,0,2,1,1);
        grid.add(valorCouleur,1,2,2,1);
        
            /*==========================
                Matrice de récupération
            ==========================*/
        
        Label matriceRecuperationLabel = new Label("Description des sorties :");
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
        this.getChildren().addAll(titleBox, iconBar, grid,matriceRecuperationLabel, scroll);
        this.getStyleClass().add("stationInformation");
    }
    
    
        /*##############################
                 JONCTION
        ##############################*/ 
    
    public void setJonction(JonctionUI j){
        this.jonction = j;
        this.radiusText.setText(this.jonction.getLongueurReal()+"");
        this.capaciteSortie.setText(j.getCapacite()+"");
        this.colorPicker.setValue(Color.web(j.getCouleur()));
        this.listUI.setInstance(j.getSortieEquipement().get(0).getFluxMatiere(), null, false);
        scroll.setContent(listUI);
    }
    
    public void updateJonction(){  
        this.jonction.setLongueurReal(Double.parseDouble(this.radiusText.getText()));
        this.jonction.setCapacite(Double.parseDouble(this.capaciteSortie.getText()));
        this.jonction.setCouleur(this.colorPicker.getValue().toString());
        StaticHandler.getControleur().setInformationJonction(StringifyUI.stringify(this.jonction));
    }

    
        /*##############################
                 EDITION MODE
        ##############################*/
    
    @Override
    public void toggleEditionMode() {
        this.radiusText.setEditable(!this.radiusText.editableProperty().getValue());
        this.capaciteSortie.setEditable(!this.capaciteSortie.editableProperty().getValue());
        this.colorPicker.setEditable(!this.colorPicker.editableProperty().getValue());
        
        if(!editionMode){
            this.capaciteSortie.getStyleClass().add("labelInformationSetting");
            this.radiusText.getStyleClass().add("labelInformationSetting");
            
            this.capaciteSortie.getStyleClass().remove("fieldInformation");
            this.radiusText.getStyleClass().remove("fieldInformation");
            
            grid.getChildren().remove(valorCouleur);
            grid.add(colorPicker,1,2,2,1);
        }
        else{
            this.radiusText.getStyleClass().remove("labelInformationSetting");
            this.capaciteSortie.getStyleClass().remove("labelInformationSetting");
            
            this.radiusText.getStyleClass().add("fieldInformation");
            this.capaciteSortie.getStyleClass().add("fieldInformation");
            
            grid.getChildren().remove(colorPicker);
            grid.add(valorCouleur,1,2,2,1);
            updateJonction();
        }
        editionMode = !editionMode;
    }
    
}
