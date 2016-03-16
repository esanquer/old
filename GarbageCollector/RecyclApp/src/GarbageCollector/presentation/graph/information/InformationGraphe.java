
package GarbageCollector.presentation.graph.information;

import GarbageCollector.presentation.event.StaticHandler;
import GarbageCollector.presentation.graph.GraphePanel;
import GarbageCollector.presentation.menu.IconsBar;
import GarbageCollector.presentation.util.ButtonIcon;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class InformationGraphe  extends VBox implements Information{

        /*##############################
                 ATRIBUTS
        ##############################*/
       
    private final GridPane grid;
    private final TextField longueurText ;
    private final TextField hauteurText ;
    
    private final HBox gridScale;
    private final TextField echelleText ;
    private final TextField affichageText ;
    private final ComboBox affichageBox;
    
    private boolean editionMode = false;
    
    private GraphePanel graphePanel;
    
        /*##############################
                 CONSTRUCTEUR
        ##############################*/
    
    public InformationGraphe(){
        
        this.setPrefSize(230, 400);
        
        grid = new GridPane();
        grid.setVgap(5);
        grid.setPadding(new Insets(20, 0, 0, 10));

            /*==========================
                    Bar d'outil
            ==========================*/

        final ButtonIcon   dropInfoIcon = new ButtonIcon("arrow-right", 50), 
                           gearsIcon = new ButtonIcon("gear", 40);
        
            //Boutton save
        dropInfoIcon.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override 
            public void handle(MouseEvent mouseEvent){
                 StaticHandler.toggleInfoPan();
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
        listIcon.add(gearsIcon);
        
        IconsBar iconBar = new IconsBar(listIcon, 40, "infoBar");
        
            /*==========================
                    TITRE
            ==========================*/
        Label title = new Label("Graphe");
        title.setPrefSize(230, 50);
        title.getStyleClass().addAll("tileInformation","font-Bold","text-big");

        dropInfoIcon.setPrefWidth(50);
        HBox titleBox = new HBox();
        titleBox.getChildren().addAll(dropInfoIcon, title);
        
            /*==========================
                    Longueur
            ==========================*/
        
        Label longueurLabel = new Label("Longueur :   ");
        longueurLabel.getStyleClass().addAll("labelInformation","font-Bold","text-medium");
        
        longueurText = new TextField("100");
        longueurText.setPrefColumnCount(10); 
        longueurText.setMaxWidth(50);
        longueurText.setEditable(false);
        longueurText.getStyleClass().addAll("fieldInformation","font-Regular","text-regular");
        
        Label uniteLongueur  = new Label("m");
        uniteLongueur.getStyleClass().addAll("unitInformation","font-Bold","text-medium");
        
        grid.add(longueurLabel,0,0,1,1);
        grid.add(longueurText,1,0,1,1);
        grid.add(uniteLongueur,2,0,1,1);

            /*==========================
                    Hauteur
            ==========================*/
        
        Label hauteurLabel = new Label("Hauteur :   ");
        hauteurLabel.getStyleClass().addAll("labelInformation","font-Bold","text-medium");
        
        hauteurText = new TextField("100");
        hauteurText.setPrefColumnCount(10); 
        hauteurText.setMaxWidth(50);
        hauteurText.setEditable(false);
        hauteurText.getStyleClass().addAll("fieldInformation","font-Regular","text-regular");
        
        Label uniteHauteur  = new Label("m");
        uniteHauteur.getStyleClass().addAll("unitInformation","font-Bold","text-medium");
        
        grid.add(hauteurLabel,0,1,1,1);
        grid.add(hauteurText,1,1,1,1);
        grid.add(uniteHauteur,2,1,1,1);

            /*==========================
                    Echelle
            ==========================*/
        
        Label echelleLabel = new Label("Echelle :   ");
        echelleLabel.getStyleClass().addAll("labelInformation","font-Bold","text-medium");
        grid.add(echelleLabel,0,2);
        
        
        ImageView   squareIcon = new ImageView(new Image(getClass().getResourceAsStream("/Ressources/Images/icons/square.png"))),
                    arrowIcon = new ImageView(new Image(getClass().getResourceAsStream("/Ressources/Images/icons/arrowLeft.png")));     
        squareIcon.setTranslateY(4);
        arrowIcon.setTranslateY(0);
        
        echelleText = new TextField("1000");
        echelleText.setPrefColumnCount(10); 
        echelleText.setMaxWidth(50);
        echelleText.setEditable(false);
        echelleText.getStyleClass().addAll("fieldInformation","font-Regular","text-regular");
        
        Label uniteEchelle  = new Label("m");
        uniteEchelle.getStyleClass().addAll("unitInformation","font-Bold","text-medium");
        
        gridScale = new HBox();
        gridScale.setSpacing(0);
        gridScale.getChildren().addAll(squareIcon,arrowIcon,echelleText,uniteEchelle);
        gridScale.setTranslateX(45);
        
        grid.add(gridScale,0,3,3,1);
        
        
            /*==========================
                    Affichage
            ==========================*/
        
        Label affichageLabel = new Label("Affichage :  ");
        affichageLabel.getStyleClass().addAll("labelInformation","font-Bold","text-medium");
        
        affichageText = new TextField("nom");
        affichageText.setPrefColumnCount(10); 
        affichageText.setMaxWidth(100);
        affichageText.setEditable(false);
        affichageText.getStyleClass().addAll("fieldInformation","font-Regular","text-regular");
        
        this.affichageBox = new ComboBox();
        affichageBox.getItems().addAll(
            "Nom",
            "Image"
        );
        affichageBox.setTranslateX(11);
        affichageBox.setMaxWidth(110);
        affichageBox.setVisibleRowCount(2);
        affichageBox.setValue("Nom");
        affichageBox.getStyleClass().addAll("colorInformationSetting","font-Regular","text-regular");
        
        grid.add(affichageLabel,0,4,1,1);
        grid.add(affichageText,1,4,2,1);
        
            /*==========================
                Panneau Information
            ==========================*/
        
        this.setSpacing(0);
        this.getChildren().addAll(titleBox, iconBar, grid);
        this.getStyleClass().add("stationInformation");
    }
    
    
        /*##############################
                 GRAPHE
        ##############################*/ 
    
    public void setGraphe(GraphePanel g){
        this.graphePanel = g;
        this.longueurText.setText(g.getLongueurReal()+"");
        this.hauteurText.setText(g.getHauteurReal()+"");
        this.echelleText.setText(g.getGridEchelle()+"");
        if(this.graphePanel.isShowName()){
            this.affichageBox.setValue("Nom");
            this.affichageText.setText("Nom");
        }
        else{
            this.affichageBox.setValue("Image");
            this.affichageText.setText("Image");
        }
    }
    
    public void updateGraphe(){
        this.graphePanel.setLongueurReal(Double.parseDouble(this.longueurText.getText()));
        this.graphePanel.setHauteurReal(Double.parseDouble(this.hauteurText.getText()));
        this.graphePanel.setGridEchelle(Double.parseDouble(this.echelleText.getText()));
     
        try {
            if(this.affichageBox.getValue().equals("Image")){
                this.graphePanel.setShowName(false);
                this.affichageText.setText("Image");
            }
            else{
                this.graphePanel.setShowName(true);
                this.affichageText.setText("Nom");
            }
        } catch (IOException ex) {
            Logger.getLogger(InformationGraphe.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
        /*##############################
                 EDITION MODE
        ##############################*/
    
    @Override
    public void toggleEditionMode() {
        this.longueurText.setEditable(!this.longueurText.editableProperty().getValue());
        this.hauteurText.setEditable(!this.hauteurText.editableProperty().getValue());
        this.affichageText.setEditable(!this.affichageText.editableProperty().getValue());
        this.echelleText.setEditable(!this.echelleText.editableProperty().getValue());
        if(!editionMode){
            this.longueurText.getStyleClass().add("labelInformationSetting");
            this.hauteurText.getStyleClass().add("labelInformationSetting");
            this.echelleText.getStyleClass().add("labelInformationSetting");
            
            this.hauteurText.getStyleClass().remove("fieldInformation");
            this.longueurText.getStyleClass().remove("fieldInformation");
            this.echelleText.getStyleClass().remove("fieldInformation");
            
            grid.getChildren().remove(affichageText);
            grid.add(affichageBox,1,4,2,1);
            
        }
        else{
            this.longueurText.getStyleClass().remove("labelInformationSetting");
            this.hauteurText.getStyleClass().remove("labelInformationSetting");
            this.affichageText.getStyleClass().remove("labelInformationSetting");
            this.echelleText.getStyleClass().remove("labelInformationSetting");
            
            this.affichageText.getStyleClass().add("fieldInformation");
            this.hauteurText.getStyleClass().add("fieldInformation");
            this.longueurText.getStyleClass().add("fieldInformation");
            this.echelleText.getStyleClass().add("fieldInformation");
            
            grid.getChildren().remove(affichageBox);
            grid.add(affichageText,1,4,2,1);
                    
            updateGraphe();
        }
        editionMode = !editionMode;
    }
    
}

