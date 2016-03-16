package GarbageCollector.presentation.graph.information;

import GarbageCollector.presentation.event.StaticHandler;
import GarbageCollector.presentation.graph.equipement.SortieEquipementUI;
import GarbageCollector.presentation.util.MatriceUI;
import GarbageCollector.presentation.graph.equipement.StationUI;
import GarbageCollector.presentation.menu.IconsBar;
import GarbageCollector.presentation.util.ButtonIcon;
import GarbageCollector.presentation.util.StringifyUI;
import java.io.File;
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
import javafx.stage.FileChooser;


public class InformationStation extends VBox implements Information
{
        /*##############################
                 ATRIBUT
        ##############################*/
    
    private final MatriceUI matriceUI;
    private StationUI station;
    
    private Label imageTextLabel  ;
    private TextField imageTextSetting ;
    private final HBox imageBox;
    
    private final TextField longueurText ;
    private final TextField hauteurText ;
    
    private final GridPane grid;
    
    private final TextField nomStation ;
    private final TextArea descriptionText ;
    private final TextField capaciteEntree ;
    private Rectangle valorCouleur;
    private ColorPicker colorPicker ;
    private  Label matriceRecuperationLabel;
    
    private final ScrollPane scroll;
    
    private boolean editionMode = false;

        /*##############################
                 CONSTRUCTEUR
        ##############################*/
    
    public InformationStation(){
        
        this.setPrefSize(227, 400);
        
        grid = new GridPane();
        grid.setPadding(new Insets(15, 0, 20, 5));
        grid.setVgap(3);

            /*==========================
                    Bar d'outil
            ==========================*/

        final ButtonIcon   dropInfoIcon = new ButtonIcon("arrow-right", 50), 
                           addOutIcon = new ButtonIcon("add-list", 40),
                           deleteIcon = new ButtonIcon("delete", 40),
                           browseIcon = new ButtonIcon("search", 24),
                           matrixIcon = new ButtonIcon("matrix", 40),
                           gearsIcon = new ButtonIcon("gear", 40);
        
        dropInfoIcon.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override 
            public void handle(MouseEvent mouseEvent) {
                StaticHandler.toggleInfoPan();
            }
        });     
        
        addOutIcon.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override 
            public void handle(MouseEvent mouseEvent){
                SortieEquipementUI s = new SortieEquipementUI(0,0,station);
                StaticHandler.getControleur().ajouterSortieEquipement(StringifyUI.stringify(s));
                station.ajouterSortie(s);
            }
        });
        
        browseIcon.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override 
            public void handle(MouseEvent mouseEvent) 
            {
                FileChooser fileChooser = new FileChooser();
                fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                    new FileChooser.ExtensionFilter("PNG", "*.png")
                );
                File file = fileChooser.showOpenDialog(null);
                if (file != null) {
                    station.setImageFileName(file.getName());
                    station.setImageFilePath(file.getAbsolutePath());
                    imageTextLabel.setText(file.getName());
                    imageTextSetting.setText(file.getName());
                }
            }
        });
        
        deleteIcon.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override 
            public void handle(MouseEvent mouseEvent) 
            {
                StaticHandler.supprimeEquipement(station);
            }
        });
        
        matrixIcon.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override 
            public void handle(MouseEvent mouseEvent){
                if(!editionMode){
                    matriceUI.toggleAffichage();
                    if(matriceUI.isAfficheTauxRecuperatioMode())
                        matriceRecuperationLabel.setText("Tauf de Recuperation (%) :");
                    else
                        matriceRecuperationLabel.setText("Masse en sortie (kg/h) :");
                }
            }
        });
        
        
        gearsIcon.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override 
            public void handle(MouseEvent mouseEvent){
                toggleEditionMode();
            }
        });
        
        ArrayList<ButtonIcon> listIcon = new ArrayList<>();
        listIcon.add(addOutIcon);
        listIcon.add(deleteIcon);
        listIcon.add(gearsIcon);
        listIcon.add(matrixIcon);
        
        IconsBar iconBar = new IconsBar(listIcon, 40, "infoBar");

                
            /*==========================
                    TITRE
            ==========================*/
        
        Label title = new Label("Station");
        title.setPrefSize(230, 50);
        title.getStyleClass().addAll("tileInformation","font-Bold","text-big");

        dropInfoIcon.setPrefWidth(50);
        HBox titleBox = new HBox();
        titleBox.getChildren().addAll(dropInfoIcon, title);
                
                
            /*==========================
                    Nom
            ==========================*/
        
        Label nomLabel  = new Label("Nom : ");
        nomLabel.getStyleClass().addAll("labelInformation","font-Bold","text-medium");
        
        nomStation = new TextField("");
        nomStation.setMaxWidth(110);
        nomStation.setEditable(false);
        nomStation.getStyleClass().addAll("fieldInformation","font-Bold","text-medium");

        grid.add(nomLabel,0,0,1,1);
        grid.add(nomStation,1,0, 2, 1);
        
        
        
            /*==========================
                    Capacité
            ==========================*/
        
        Label capaciteLabel = new Label("Capacite: ");
        capaciteLabel.getStyleClass().addAll("labelInformation","font-Bold","text-medium");
        
        capaciteEntree = new TextField("1");
        capaciteEntree.setPrefColumnCount(10); 
        capaciteEntree.setMaxWidth(60);
        capaciteEntree.setMinWidth(60);
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
        colorPicker.setMinWidth(110);
        colorPicker.setMaxHeight(20);
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
                       IMAGE
            ==========================*/
        
        Label imageLabel  = new Label("Image : ");
        imageLabel.getStyleClass().addAll("labelInformation","font-Bold","text-medium");
        
        imageTextLabel = new Label("");
        imageTextLabel.setMaxWidth(110);
        imageTextLabel.getStyleClass().addAll("fieldInformation","font-Regular","text-regular");
        
        imageTextSetting = new TextField("");
        imageTextSetting.setMaxWidth(80);
        imageTextSetting.setMaxHeight(20);
        imageTextSetting.setEditable(true);
        imageTextSetting.setTranslateX(10);
        imageTextSetting.getStyleClass().addAll("labelInformationSetting","font-Regular","text-regular");
        browseIcon.setMaxWidth(25);
        browseIcon.setPrefWidth(25);
        imageBox = new HBox();
        imageBox.getChildren().addAll(imageTextSetting, browseIcon);
        
        
        grid.add(imageLabel,0,5,1,1);
        grid.add(imageTextLabel,1,5,2,1);
        
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
        
        grid.add(descriptionLabel,0,6);
        grid.add(descriptionText,0,7,3,1);


        
            /*==========================
                Matrice de récupération
            ==========================*/
        
        matriceRecuperationLabel = new Label("Description des sorties (%) :");
        matriceRecuperationLabel.getStyleClass().addAll("labelInformation","font-Bold","text-medium");
        
        matriceUI = new MatriceUI(new HashMap<String, Double>(), new HashMap<Integer, HashMap<String, Double>>());
        
        scroll = new ScrollPane();
        scroll.setMaxWidth(215);
        scroll.setMinWidth(215);
        this.scroll.setPrefHeight(180);
        scroll.setMaxHeight(180);
        scroll.setContent(matriceUI);
        scroll.getStyleClass().addAll("matrice","scroolMatrice");
        
        
            /*==========================
                    Station
            ==========================*/

        this.setSpacing(0);
        this.setPadding(new Insets(0, 0, 20, 0));
        this.getChildren().addAll(titleBox, iconBar, grid, matriceRecuperationLabel, scroll);
        this.getStyleClass().add("stationInformation");
    }

    
        /*##############################
                  STATION
        ##############################*/
    
    public void setStation(StationUI s){
        this.station = s;
        this.nomStation.setText(s.getNom());
        this.longueurText.setText(s.getLongueurReal()+"");
        this.hauteurText.setText(s.getHauteurReal()+"");
        this.descriptionText.setText(s.getDescription());
        this.capaciteEntree.setText(s.getCapacite()+"");
        this.valorCouleur.setFill(Color.web(s.getCouleur()));
        this.colorPicker.setValue(Color.web(s.getCouleur()));
        this.imageTextLabel.setText(s.getImageFileName());
        this.imageTextSetting.setText(s.getImageFileName());
        this.matriceUI.setInstance(s.getDescriptionEntree(), s.getMatrice(), !editionMode);
        this.station.verifieConformite();
        if(!this.station.isEstConforme()){
            if(!this.capaciteEntree.getStyleClass().contains("error-label"))
                this.capaciteEntree.getStyleClass().add("error-label");
        }
        else
            this.capaciteEntree.getStyleClass().remove("error-label");
    }
    
    public void updateStation(){
        station.setNom(this.nomStation.getText());
        station.setLongueurReal(Double.parseDouble(this.longueurText.getText()));
        station.setHauteurReal(Double.parseDouble(this.hauteurText.getText()));
        station.setCapacite(Double.parseDouble(this.capaciteEntree.getText()));
        station.setDescription(this.descriptionText.getText());
        station.setCouleur(getCssSpec(colorPicker.getValue()));
        station.setMatrice(matriceUI.getMatrice());
        
        String stationStr = StringifyUI.stringify(station);
        StaticHandler.getControleur().setInformationStation(stationStr);
    }

    
        /*##############################
                 EDITION MODE
        ##############################*/
    
    @Override
    public void toggleEditionMode() {
        this.nomStation.setEditable(!this.nomStation.editableProperty().getValue());
        this.longueurText.setEditable(!this.longueurText.editableProperty().getValue());
        this.hauteurText.setEditable(!this.hauteurText.editableProperty().getValue());
        this.descriptionText.setEditable(!this.descriptionText.editableProperty().getValue());
        this.capaciteEntree.setEditable(!this.capaciteEntree.editableProperty().getValue());
        this.colorPicker.setEditable(!this.colorPicker.editableProperty().getValue());
        this.matriceUI.toggleEditionMode();
        
        if(!editionMode){
                //UI
            this.nomStation.getStyleClass().remove("fieldInformation");
            this.capaciteEntree.getStyleClass().remove("fieldInformation");
            this.longueurText.getStyleClass().remove("fieldInformation");
            this.hauteurText.getStyleClass().remove("fieldInformation");
            this.descriptionText.getStyleClass().remove("descriptionInformation");
            grid.getChildren().remove(valorCouleur);
            grid.getChildren().remove(imageTextLabel);
            
            this.nomStation.getStyleClass().add("labelInformationSetting");
            this.capaciteEntree.getStyleClass().add("labelInformationSetting");
            this.longueurText.getStyleClass().add("labelInformationSetting");
            this.hauteurText.getStyleClass().add("labelInformationSetting");
            this.descriptionText.getStyleClass().add("descriptionInformationSetting");
            grid.add(colorPicker,1,4,2,1);
            grid.add(imageBox,1,5,2,1);
            
        }
        else{
                    //UI
            this.nomStation.getStyleClass().remove("labelInformationSetting");
            this.capaciteEntree.getStyleClass().remove("labelInformationSetting");
            this.longueurText.getStyleClass().remove("labelInformationSetting");
            this.hauteurText.getStyleClass().remove("labelInformationSetting");
            this.descriptionText.getStyleClass().remove("descriptionInformationSetting");
            grid.getChildren().remove(colorPicker);
            grid.getChildren().remove(imageBox);
            
            this.nomStation.getStyleClass().add("fieldInformation");
            this.longueurText.getStyleClass().add("fieldInformation");
            this.hauteurText.getStyleClass().add("fieldInformation");
            this.capaciteEntree.getStyleClass().add("fieldInformation");
            this.descriptionText.getStyleClass().add("descriptionInformation");
            grid.add(valorCouleur,1,4,2,1);
            grid.add(imageTextLabel,1,5,2,1);
            updateStation();
            this.matriceUI.setInstance(station.getDescriptionEntree(), station.getMatrice(), editionMode);
        }
        editionMode = !editionMode;
    }
    
    
    public String getCssSpec(Color c) {
        int r = (int) (c.getRed() * 256) ;
        int g = (int) (c.getGreen() * 256) ;
        int b = (int) (c.getBlue() * 256) ;
        return String.format("rgb(%d, %d, %d)", r, g, b);
    }
}
