
package GarbageCollector;

import GarbageCollector.controleur.Controleur;
import GarbageCollector.presentation.elementBar.Element;
import GarbageCollector.presentation.elementBar.ElementsBar;
import GarbageCollector.presentation.event.StaticHandler;
import GarbageCollector.presentation.footer.Footer;
import GarbageCollector.presentation.graph.GraphUI;
import GarbageCollector.presentation.graph.information.InformationConvoyeur;
import GarbageCollector.presentation.graph.information.InformationEntreeEquipement;
import GarbageCollector.presentation.graph.information.InformationEntreeUsine;
import GarbageCollector.presentation.graph.information.InformationHide;
import GarbageCollector.presentation.graph.information.InformationJonction;
import GarbageCollector.presentation.graph.information.InformationSortieEquipement;
import GarbageCollector.presentation.graph.information.InformationSortieUsine;
import GarbageCollector.presentation.graph.information.InformationStation;
import GarbageCollector.presentation.graph.information.InformationGraphe;
import GarbageCollector.presentation.menu.MenuBar;
import GarbageCollector.presentation.util.ButtonIcon;
import java.io.File;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.text.Font;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;


public class RecyclApp extends Application {
    
        /*######################################
                    ATRIBUTS
        ######################################*/
    
    private MenuBar menuBar; 
    private ElementsBar elementBar;
    private Footer footer;
    private Scene scene;
    private GraphUI grapheUI;
    private BorderPane borderPane ;
    
    private InformationHide infoHide;
    private InformationGraphe infoGraphe;
    private InformationStation infoStation;
    private InformationEntreeUsine infoEntreeUsine;
    private InformationSortieUsine infoSortieUsine;
    private InformationJonction infoJonction;
    private InformationConvoyeur infoConvoyeur;
    private InformationSortieEquipement infoSortieEquipement;
    private InformationEntreeEquipement infoEntreeEquipement;

    
        /*####################################
                    Initialisation
        ######################################*/
    @Override
    public void start(Stage primaryStage) {
        
        Controleur controleur = new Controleur();
        borderPane = new BorderPane();
        grapheUI = new GraphUI();
        footer = new Footer();
        initMenu();
        initElementBar();
        initInfoBar();
        
        borderPane.setCenter(grapheUI);
        borderPane.setTop(menuBar);
        borderPane.setLeft(elementBar);
        borderPane.setRight(infoGraphe);
        borderPane.setBottom(footer);
        
        scene = new Scene(borderPane, 1110, 600);
        
        scene.getStylesheets().add(RecyclApp.class.getResource("presentation/css/styleMenu.css").toExternalForm());
        scene.getStylesheets().add(RecyclApp.class.getResource("presentation/css/styleElementBar.css").toExternalForm());
        scene.getStylesheets().add(RecyclApp.class.getResource("presentation/css/styleInformation.css").toExternalForm());
        scene.getStylesheets().add(RecyclApp.class.getResource("presentation/css/styleFooter.css").toExternalForm());
        scene.getStylesheets().add(RecyclApp.class.getResource("presentation/css/styleUtile.css").toExternalForm());
        scene.getStylesheets().add(RecyclApp.class.getResource("presentation/css/styleGraphe.css").toExternalForm());
        //Par ordre croissant d'intensité
        Font.loadFont(RecyclApp.class.getResource("presentation/font/Pacifico.ttf").toExternalForm(), 20);
       
        primaryStage.setMinWidth(700);
        primaryStage.setMinHeight(600);
        primaryStage.setTitle("Recycl App");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        
        borderPane.setOnKeyPressed(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent keyEvent){
                if(keyEvent.getCode().equals(KeyCode.ENTER)) {
                    StaticHandler.pressEnter();
                    keyEvent.consume();
                }
            }
        });
                
        StaticHandler.setControleur(controleur);
        StaticHandler.setBorderPane(borderPane);
        StaticHandler.setGraphe(grapheUI);
        StaticHandler.setFooter(footer);
        StaticHandler.setInfoHide(infoHide);
        StaticHandler.setInfoGraphe(infoGraphe);
        StaticHandler.setInfoStation(infoStation);
        StaticHandler.setInfoEntreeUsine(infoEntreeUsine);
        StaticHandler.setInfoSortieUsine(infoSortieUsine);
        StaticHandler.setInfoJonction(infoJonction);
        StaticHandler.setInfoConvoyeur(infoConvoyeur);
        StaticHandler.setInfoSortieEquipement(infoSortieEquipement);
        StaticHandler.setInfoEntreeEquipement(infoEntreeEquipement);
        StaticHandler.selectionGraphe();
    }


        /* ####################################
                    LANCEMENT
        ######################################*/
   
    public static void main(String[] args) {
        launch(args);
    }
    
    
        /*####################################
                Initialisation Menu
        ######################################*/
    
    public void initMenu(){
        final ButtonIcon    saveIcon = new ButtonIcon("save", 50),   
                            addIcon = new ButtonIcon("add", 50),
                            loadIcon = new ButtonIcon("load", 50),
                            exportIcon = new ButtonIcon( "export", 50),
                            undoIcon = new ButtonIcon("undo", 50),
                            redoIcon = new ButtonIcon("redo", 50),
                            gridIcon = new ButtonIcon("grid", 50), 
                            magnetIcon = new ButtonIcon("add-list", 50);
        
            //Boutton save
        saveIcon.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override 
            public void handle(MouseEvent mouseEvent) 
            {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Save Graphe");
                File file = fileChooser.showSaveDialog(null);
                if (file != null) {
                    System.out.println(file.getAbsolutePath());
                    StaticHandler.getControleur().sauvegarderGraphe(file.getAbsolutePath());
                }
            }
        });     
        
            //Boutton add
        addIcon.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override 
            public void handle(MouseEvent mouseEvent) {
                StaticHandler.newGraphe();
            };
        });     
        
        final FileChooser fileChooser = new FileChooser();
        
            //Boutton save
        loadIcon.setOnMouseClicked(new EventHandler<MouseEvent>()  {
            @Override 
            public void handle(MouseEvent mouseEvent) 
            {
                File file = fileChooser.showOpenDialog(null);
                if (file != null) {
                    StaticHandler.chargerGraphe(file.getAbsolutePath());
                }
            }
        });
            
            //Boutton export
        exportIcon.setOnMouseClicked(new EventHandler<MouseEvent>()  {
            @Override 
            public void handle(MouseEvent mouseEvent) 
            {
                DirectoryChooser directoryChooser = new DirectoryChooser(); 
                File file = directoryChooser.showDialog(null);
                if(file!=null){
                }
            }
        });
        
            //Boutton undo
        undoIcon.setOnMouseClicked(new EventHandler<MouseEvent>()  {
            @Override 
            public void handle(MouseEvent mouseEvent) {
                StaticHandler.undo();
            }
        });
        
            //Boutton redo
        redoIcon.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override 
            public void handle(MouseEvent mouseEvent){
                StaticHandler.redo();
            }
        });
       
            //Boutton active grille
        gridIcon.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override 
            public void handle(MouseEvent mouseEvent) 
            {
                if(gridIcon.isActive()){
                    gridIcon.getStyleClass().remove("btn-active");
                    magnetIcon.getStyleClass().add("btn-disable");
                }
                else{
                    gridIcon.getStyleClass().add("btn-active");
                    magnetIcon.getStyleClass().remove("btn-disable");
                }
                gridIcon.changeActive();
                grapheUI.activeGrille();
            }
        });
        
            //Boutton active magnet
        magnetIcon.getStyleClass().add("btn-disable");
        magnetIcon.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override 
            public void handle(MouseEvent mouseEvent){
                if(gridIcon.isActive())
                {
                    if(magnetIcon.isActive())
                        magnetIcon.getStyleClass().remove("btn-active");
                    else
                        magnetIcon.getStyleClass().add("btn-active");
                    magnetIcon.changeActive();
                    grapheUI.activeMagne();
                }
            }
        });
        
        
        ArrayList<ButtonIcon> listIcon = new ArrayList<>();
        listIcon.add(saveIcon);
        listIcon.add(addIcon);
        listIcon.add(loadIcon);
        listIcon.add(exportIcon);
        listIcon.add(undoIcon);
        listIcon.add(redoIcon);
        listIcon.add(gridIcon);
        listIcon.add(magnetIcon);
        
        menuBar = new MenuBar(listIcon); 
    }
    

        /*####################################
              Initialisation Bar D'élement
        ####################################*/
    
    private void initElementBar(){
        final Element   stationIcon = new Element("station", 50),   
                        convoyeurIcon = new Element("convoyeur", 50),
                        jonctionIcon = new Element("Jonction", 50),
                        entreeUsineIcon = new Element( "Entrée Usine", 50),
                        sortieUsineIcon = new Element("Sortie Usine", 50);
        
                //Station
        stationIcon.setOnDragDetected(new EventHandler<MouseEvent>(){
                @Override
                public void handle(MouseEvent event) {
                        Dragboard db = stationIcon.startDragAndDrop(TransferMode.ANY);
                        ClipboardContent content = new ClipboardContent();
                        content.putString("Station");
                        db.setContent(content);
                }
            });
         
                //Convoyeur
        convoyeurIcon.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override 
            public void handle(MouseEvent mouseEvent){
                if(convoyeurIcon.isActive()){
                    convoyeurIcon.getStyleClass().remove("element-active");
                    StaticHandler.setCreateConvoyeurActive(false);
                }
                else{
                    convoyeurIcon.getStyleClass().add("element-active");
                    StaticHandler.setCreateConvoyeurActive(true);
                }
                convoyeurIcon.changeActive();
            }
        });
        
                //Jonction
        jonctionIcon.setOnDragDetected(new EventHandler<MouseEvent>(){
                @Override
                public void handle(MouseEvent event) {
                    Dragboard db = stationIcon.startDragAndDrop(TransferMode.ANY);
                    ClipboardContent content = new ClipboardContent();
                    content.putString("Jonction");
                    db.setContent(content);
                }
            });
        
                //Entrée Usine
        entreeUsineIcon.setOnDragDetected(new EventHandler<MouseEvent>(){
                @Override
                public void handle(MouseEvent event) {
                    Dragboard db = stationIcon.startDragAndDrop(TransferMode.ANY);
                    ClipboardContent content = new ClipboardContent();
                    content.putString("Entrée Usine");
                    db.setContent(content);
                }
            });
        
                //Sortie Usine
        sortieUsineIcon.setOnDragDetected(new EventHandler<MouseEvent>(){
                @Override
                public void handle(MouseEvent event) {
                    Dragboard db = stationIcon.startDragAndDrop(TransferMode.ANY);
                    ClipboardContent content = new ClipboardContent();
                    content.putString("Sortie Usine");
                    db.setContent(content);
                }
            });
        
        
        final ArrayList<Element> listElement = new ArrayList<>();
        listElement.add(stationIcon);
        listElement.add(convoyeurIcon);
        listElement.add(jonctionIcon);
        listElement.add(entreeUsineIcon);
        listElement.add(sortieUsineIcon);
        
        this.elementBar = new ElementsBar(listElement); 
    }
        
        /*####################################
              Initialisation Bar D'info
        ####################################*/
    
    public void initInfoBar(){
        infoHide = new InformationHide();
        infoGraphe = new InformationGraphe();
        infoStation = new InformationStation();
        infoEntreeUsine = new InformationEntreeUsine();
        infoSortieUsine = new InformationSortieUsine();
        infoJonction = new InformationJonction();
        infoConvoyeur = new InformationConvoyeur();
        infoSortieEquipement = new InformationSortieEquipement();
        infoEntreeEquipement = new InformationEntreeEquipement();
    }
    
}
