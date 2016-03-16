
package GarbageCollector.presentation.graph.information;

import GarbageCollector.presentation.event.StaticHandler;
import GarbageCollector.presentation.graph.equipement.EntreeUsineUI;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class InformationEntreeUsine extends VBox implements Information{
    
        /*##############################
                 ATRIBUT
        ##############################*/
    
    private ListeUISetting listUI;
    
    private EntreeUsineUI entreUsine;
    
    private final GridPane grid;
    
    private final TextField nomEntreeUsine ;
    private final TextField longueurText ;
    private final TextField hauteurText ;
    private final TextArea descriptionText ;
    private final TextField capaciteEntree ;
    private Rectangle valorCouleur;
    private ColorPicker colorPicker ;
    private final ScrollPane scroll;
    
    private boolean editionMode = false;
    

        /*##############################
                 CONSTRUCTEUR
        ##############################*/
    
    public InformationEntreeUsine(){
        
        this.setPrefSize(230, 400);
        
        grid = new GridPane();
        grid.setPadding(new Insets(15, 0, 20, 5));
        grid.setVgap(3);
        

            /*==========================
                    Bar d'outil
            ==========================*/

        final ButtonIcon   dropInfoIcon = new ButtonIcon("arrow-right", 50), 
                           addOutIcon = new ButtonIcon("add-list", 40),
                           deleteIcon = new ButtonIcon( "delete", 40),
                           gearsIcon = new ButtonIcon("gear", 40);
        
            //Boutton save
        dropInfoIcon.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override 
            public void handle(MouseEvent mouseEvent) 
            {
                 StaticHandler.toggleInfoPan();
            }
        });     
        
        addOutIcon.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override 
            public void handle(MouseEvent mouseEvent){
                entreUsine.addFluxEntrant("No Name", 0.0);
                listUI.setInstance(entreUsine.getFluxEntrants(), entreUsine, editionMode);
                toggleEditionMode();
            }
        });

        deleteIcon.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override 
            public void handle(MouseEvent mouseEvent) 
            {
                StaticHandler.supprimeEquipement(entreUsine);
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
        listIcon.add(dropInfoIcon);
        listIcon.add(addOutIcon);
        listIcon.add(deleteIcon);
        listIcon.add(gearsIcon);
        
        IconsBar iconBar = new IconsBar(listIcon, 40, "infoBar");

            /*==========================
                    TITRE
            ==========================*/
        Label title = new Label("Entree Usine");
        title.setPrefSize(230, 50);
        title.getStyleClass().addAll("tileInformation","font-Bold","text-semi-big");

        dropInfoIcon.setPrefWidth(50);
        HBox titleBox = new HBox();
        titleBox.getChildren().addAll(dropInfoIcon, title);
        
            /*==========================
                    Nom
            ==========================*/
        
        Label nomLabel  = new Label("Nom : ");
        nomLabel.getStyleClass().addAll("labelInformation","font-Bold","text-medium");
        
        nomEntreeUsine = new TextField("");
        nomEntreeUsine.setMaxWidth(110);
        nomEntreeUsine.setEditable(false);
        nomEntreeUsine.getStyleClass().addAll("fieldInformation","font-Regular","text-regular");
        
        grid.add(nomLabel,0,0,1,1);
        grid.add(nomEntreeUsine,1,0, 2, 1);
  
            /*==========================
                    Capacité
            ==========================*/
        
        Label capaciteLabel = new Label("Capacite: ");
        capaciteLabel.getStyleClass().addAll("labelInformation","font-Bold","text-medium");
        
        capaciteEntree = new TextField("1");
        capaciteEntree.setPrefColumnCount(10); 
        capaciteEntree.setMaxWidth(60);
        capaciteEntree.setEditable(false);
        capaciteEntree.getStyleClass().addAll("fieldInformation","font-Regular","text-regular");
        
        Label uniteCapacity  = new Label("kg/h");
        uniteCapacity.getStyleClass().addAll("unitInformation","font-Bold","text-medium");
        
        grid.add(capaciteLabel,0,1,1,1);
        grid.add(capaciteEntree,1,1,1,1);
        grid.add(uniteCapacity,2,1,1,1);
      
        
            /*==========================
                    Longueur
            ==========================*/
        
        Label longueurLabel = new Label("Longueur :   ");
        longueurLabel.getStyleClass().addAll("labelInformation","font-Bold","text-medium");
        
        longueurText = new TextField("100");
        longueurText.setPrefColumnCount(10); 
        longueurText.setMaxWidth(60);
        longueurText.setMinWidth(60);
        longueurText.setEditable(false);
        longueurText.getStyleClass().addAll("fieldInformation","font-Regular","text-regular");
        
        Label uniteLongueur  = new Label("m");
        uniteLongueur.getStyleClass().addAll("unitInformation","font-Bold","text-medium");
        
        
        grid.add(longueurLabel,0,2,1,1);
        grid.add(longueurText,1,2,1,1);
        grid.add(uniteLongueur,2,2,1,1);

            /*==========================
                    Hauteur
            ==========================*/
        
        Label hauteurLabel = new Label("Hauteur :   ");
        hauteurLabel.getStyleClass().addAll("labelInformation","font-Bold","text-medium");
        
        hauteurText = new TextField("100");
        hauteurText.setPrefColumnCount(10); 
        hauteurText.setMaxWidth(60);
        hauteurText.setMinWidth(60);
        hauteurText.setEditable(false);
        hauteurText.getStyleClass().addAll("fieldInformation","font-Regular","text-regular");
        
        Label uniteHauteur  = new Label("m");
        uniteHauteur.getStyleClass().addAll("unitInformation","font-Bold","text-medium");
        
        grid.add(hauteurLabel,0,3,1,1);
        grid.add(hauteurText,1,3,1,1);
        grid.add(uniteHauteur,2,3,1,1);
        
        
        
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
        grid.add(couleurLabel,0,4,1,1);
        grid.add(valorCouleur,1,4,2,1);
        
            /*==========================
                    Description
            ==========================*/
        
        Label descriptionLabel = new Label("Description :");
        descriptionLabel.getStyleClass().addAll("labelInformation","font-Bold","text-medium");
        
        descriptionText = new TextArea("");
        descriptionText.setWrapText(true);
        descriptionText.setPrefRowCount(3);
        descriptionText.setPrefColumnCount(15);
        descriptionText.setEditable(true);
        descriptionText.setEditable(false);
        descriptionText.setMaxWidth(200);
        descriptionText.getStyleClass().addAll("descriptionInformation","font-Regular","text-regular");
        
        grid.add(descriptionLabel,0,5);
        grid.add(descriptionText,0,6,3,1);
        
            /*==========================
                Matrice de récupération
            ==========================*/
        
        Label matriceRecuperationLabel = new Label("Description des flux :");
        matriceRecuperationLabel.getStyleClass().addAll("labelInformation","font-Bold","text-medium");
      
        listUI = new ListeUISetting(new HashMap<String, Double>(), this.entreUsine);
        
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
                 ENTREE USINE
        ##############################*/ 
    
    public void setEntreeUsine(EntreeUsineUI e){
        this.entreUsine = e;
        this.capaciteEntree.setText(e.getCapacite()+"");
        this.nomEntreeUsine.setText(e.getNom());
        this.longueurText.setText(e.getLongueurReal()+"");
        this.hauteurText.setText(e.getHauteurReal()+"");
        this.descriptionText.setText(e.getDescription());
        this.colorPicker.setValue(Color.web(e.getCouleur()));    
        this.listUI.setInstance(e.getFluxEntrants(), entreUsine, !editionMode);
        this.entreUsine.verifieConformite();
        if(!this.entreUsine.isEstConforme()){
            if(!this.capaciteEntree.getStyleClass().contains("error-label"))
                this.capaciteEntree.getStyleClass().add("error-label");
        }
        else
            this.capaciteEntree.getStyleClass().remove("error-label");
    }

    public void updateEntreeUsine(){
        this.entreUsine.setNom(this.nomEntreeUsine.getText());
        this.entreUsine.setCapacite(Double.parseDouble(this.capaciteEntree.getText()));
        this.entreUsine.setLongueurReal(Double.parseDouble(this.longueurText.getText()));
        this.entreUsine.setHauteurReal(Double.parseDouble(this.hauteurText.getText()));
        this.entreUsine.setDescription(this.descriptionText.getText());
        this.entreUsine.setCouleur(this.colorPicker.getValue().toString());
        this.entreUsine.setFluxEntrants(this.listUI.getFlux());
        
        StaticHandler.getControleur().setInformationEntreeUsine(StringifyUI.stringify(this.entreUsine));
    }
    
        /*##############################
                 EDITION MODE
        ##############################*/
    
    @Override
    public void toggleEditionMode() {
        this.nomEntreeUsine.setEditable(!this.nomEntreeUsine.editableProperty().getValue());
        this.longueurText.setEditable(!this.longueurText.editableProperty().getValue());
        this.hauteurText.setEditable(!this.hauteurText.editableProperty().getValue());
        this.capaciteEntree.setEditable(!this.capaciteEntree.editableProperty().getValue());
        this.colorPicker.setEditable(!this.colorPicker.editableProperty().getValue());
        this.descriptionText.setEditable(!this.descriptionText.editableProperty().getValue());
        this.listUI.toggleEditionMode();
        
        if(!editionMode){
            this.nomEntreeUsine.getStyleClass().add("labelInformationSetting");
            this.longueurText.getStyleClass().add("labelInformationSetting");
            this.hauteurText.getStyleClass().add("labelInformationSetting");
            this.capaciteEntree.getStyleClass().add("labelInformationSetting");
            this.descriptionText.getStyleClass().add("descriptionInformationSetting");
            grid.getChildren().remove(valorCouleur);
            
            this.nomEntreeUsine.getStyleClass().remove("fieldInformation");            
            this.capaciteEntree.getStyleClass().remove("fieldInformation");
            this.longueurText.getStyleClass().remove("fieldInformation");
            this.hauteurText.getStyleClass().remove("fieldInformation");
            this.descriptionText.getStyleClass().remove("descriptionInformation");
            grid.add(colorPicker,1,4,2,1);
        }
        else{
            this.nomEntreeUsine.getStyleClass().remove("labelInformationSetting");
            this.capaciteEntree.getStyleClass().remove("labelInformationSetting");
            this.longueurText.getStyleClass().remove("labelInformationSetting");
            this.hauteurText.getStyleClass().remove("labelInformationSetting");
            this.colorPicker.getStyleClass().remove("labelInformationSetting");
            this.descriptionText.getStyleClass().remove("descriptionInformationSetting");
            grid.getChildren().remove(colorPicker);
            
            this.nomEntreeUsine.getStyleClass().add("fieldInformation");
            this.capaciteEntree.getStyleClass().add("fieldInformation");
            this.longueurText.getStyleClass().add("fieldInformation");
            this.hauteurText.getStyleClass().add("fieldInformation");
            this.descriptionText.getStyleClass().add("descriptionInformation");
            grid.add(valorCouleur,1,4,2,1);
            
            updateEntreeUsine();
            this.listUI.setInstance(entreUsine.getFluxEntrants(), entreUsine, editionMode);
        }
        editionMode = !editionMode;
    }
}
