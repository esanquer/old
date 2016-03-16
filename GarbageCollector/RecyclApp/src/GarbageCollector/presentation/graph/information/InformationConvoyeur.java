package GarbageCollector.presentation.graph.information;

import GarbageCollector.presentation.event.StaticHandler;
import GarbageCollector.presentation.graph.convoyeur.ConvoyeurUI;
import GarbageCollector.presentation.menu.IconsBar;
import GarbageCollector.presentation.util.ButtonIcon;
import GarbageCollector.presentation.util.ListeUISetting;
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


public class InformationConvoyeur extends VBox implements Information{
    
        /*##############################
                 ATRIBUT
        ##############################*/

    private ListeUISetting listUI;
    private ConvoyeurUI convoyeurUI;
    
    private final GridPane grid;
    private final Label title;
    private final Label capaciteLabel;
    private final TextField capaciteEntree ;
    private final Label couleurLabel ;
    private Rectangle valorCouleur;
    private ColorPicker colorPicker ;
    private Label matriceRecuperationLabel ;
    private final ScrollPane scroll;
    private final IconsBar iconBar;
    
    private boolean editionMode = false;
    
        /*##############################
                 CONSTRUCTEUR
        ##############################*/
    
    public InformationConvoyeur(){
        
        this.setPrefSize(230, 400);
        
        grid = new GridPane();
        grid.setPadding(new Insets(20, 0, 20, 0));
        
            /*==========================
                    Bar d'outil
            ==========================*/

        final ButtonIcon    dropInfoIcon = new ButtonIcon("arrow-right", 50),  
                            matrixIcon = new ButtonIcon("matrix", 40),
                            deleteIcon = new ButtonIcon( "delete", 40),
                            gearsIcon = new ButtonIcon("gear", 40);

        dropInfoIcon.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override 
            public void handle(MouseEvent mouseEvent) {
                 StaticHandler.toggleInfoPan();
            }
        });   
        
        deleteIcon.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override 
            public void handle(MouseEvent mouseEvent) 
            {
                StaticHandler.supprimeConvoyeur(convoyeurUI);
            }
        });
        
        matrixIcon.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override 
            public void handle(MouseEvent mouseEvent){
                listUI.toggleAffichage();
                if(listUI.isAfficheTauxRecuperatioMode())
                    matriceRecuperationLabel.setText("Masse en sortie (kg/h) :" );
                else
                    matriceRecuperationLabel.setText("Tauf de Purete (%) :");
            }
        });
        
        gearsIcon.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override 
            public void handle(MouseEvent mouseEvent){
                toggleEditionMode();
            }
        });
        
        
        ArrayList<ButtonIcon> listIcon = new ArrayList<>();
        listIcon.add(deleteIcon);
        listIcon.add(matrixIcon);
        listIcon.add(gearsIcon);
        
        iconBar = new IconsBar(listIcon, 40, "infoBar");

            /*==========================
                    TITRE
            ==========================*/
        title = new Label("Convoyeur");
        title.setPrefSize(230, 50);
        title.getStyleClass().addAll("tileInformation","font-Bold","text-big");

        dropInfoIcon.setPrefWidth(50);
        HBox titleBox = new HBox();
        titleBox.getChildren().addAll(dropInfoIcon, title);
        
            /*==========================
                    Capacité
            ==========================*/
        
        capaciteLabel = new Label("Capacite: ");
        capaciteLabel.getStyleClass().addAll("labelInformation","font-Bold","text-medium");
        
        capaciteEntree = new TextField("1");
        capaciteEntree.setPrefColumnCount(10); 
        capaciteEntree.setMaxWidth(110);
        capaciteEntree.setEditable(false);
        capaciteEntree.getStyleClass().addAll("fieldInformation","font-Regular","text-regular");
        
        grid.add(capaciteLabel,0,0);
        grid.add(capaciteEntree,1,0);

            /*==========================
                    Couleur
            ==========================*/
        
        couleurLabel = new Label("Couleur : ");
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
        grid.add(couleurLabel,0,1);
        grid.add(valorCouleur,1,1);

            /*==========================
                Matrice de récupération
            ==========================*/
        
        matriceRecuperationLabel = new Label("Masse en sortie (kg/h) :");
        matriceRecuperationLabel.getStyleClass().addAll("labelInformation","font-Bold","text-medium");

        listUI = new ListeUISetting(new HashMap<String, Double>(), null);
        
        scroll = new ScrollPane();
        scroll.setMaxWidth(215);
        scroll.setMinWidth(215);
        this.scroll.setPrefHeight(180);
        scroll.setMaxHeight(200);
        scroll.setContent(listUI);
        scroll.getStyleClass().addAll("matrice","scroolMatrice");
        
        this.setSpacing(0);
        this.getChildren().addAll(titleBox, iconBar, grid, matriceRecuperationLabel, scroll);
        this.getStyleClass().add("stationInformation");
    }
    
        /*##############################
                 CONVOYEUR
        ##############################*/ 
    
    public void setConvoyeur(ConvoyeurUI c){
        this.convoyeurUI = c;
        this.capaciteEntree.setText(c.getCapacite()+"");
        this.colorPicker.setValue(Color.web(c.getCouleur()));  
        this.listUI.setInstance(c.getSortie().getFluxMatiere(), null, false);
        
        if(!this.convoyeurUI.isEstConforme()){
            if(!this.capaciteEntree.getStyleClass().contains("error-label"))
                this.capaciteEntree.getStyleClass().add("error-label");
        }
        else
            this.capaciteEntree.getStyleClass().remove("error-label");
    }

    public void updateConvoyeur(){
        convoyeurUI.setCapacite(Double.parseDouble(this.capaciteEntree.getText()));
        convoyeurUI.setCouleur(this.colorPicker.getValue().toString());
    }

    
        /*##############################
                 EDITION MODE
        ##############################*/
    
    @Override
    public void toggleEditionMode() {
        this.capaciteEntree.setEditable(!this.capaciteEntree.editableProperty().getValue());
        if(!editionMode){
            this.capaciteEntree.getStyleClass().add("labelInformationSetting");
            this.capaciteEntree.getStyleClass().remove("fieldInformation");
            
            grid.getChildren().remove(valorCouleur);
            grid.add(colorPicker,1,1);
        }
        else{
            this.capaciteEntree.getStyleClass().remove("labelInformationSetting");
            this.capaciteEntree.getStyleClass().add("fieldInformation");
            
            grid.getChildren().remove(colorPicker);
            grid.add(valorCouleur,1,1);
            updateConvoyeur();
        }
        editionMode = !editionMode;
    }

    
}
