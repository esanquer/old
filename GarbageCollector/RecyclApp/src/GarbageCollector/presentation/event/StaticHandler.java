
package GarbageCollector.presentation.event;

import GarbageCollector.controleur.Controleur;
import GarbageCollector.presentation.footer.Footer;
import GarbageCollector.presentation.graph.GraphUI;
import GarbageCollector.presentation.graph.GraphePanel;
import GarbageCollector.presentation.graph.equipement.ComposantUI;
import GarbageCollector.presentation.graph.convoyeur.ConvoyeurUI;
import GarbageCollector.presentation.graph.equipement.EntreeEquipementUI;
import GarbageCollector.presentation.graph.equipement.EntreeUsineUI;
import GarbageCollector.presentation.graph.equipement.EquipementUI;
import GarbageCollector.presentation.graph.equipement.JonctionUI;
import GarbageCollector.presentation.graph.equipement.SortieEquipementUI;
import GarbageCollector.presentation.graph.equipement.SortieUsineUI;
import GarbageCollector.presentation.graph.equipement.StationUI;
import GarbageCollector.presentation.graph.information.InformationConvoyeur;
import GarbageCollector.presentation.graph.information.InformationEntreeEquipement;
import GarbageCollector.presentation.graph.information.InformationEntreeUsine;
import GarbageCollector.presentation.graph.information.InformationHide;
import GarbageCollector.presentation.graph.information.InformationJonction;
import GarbageCollector.presentation.graph.information.InformationSortieEquipement;
import GarbageCollector.presentation.graph.information.InformationSortieUsine;
import GarbageCollector.presentation.graph.information.InformationStation;
import GarbageCollector.presentation.graph.information.InformationGraphe;
import GarbageCollector.presentation.util.DestringifyUI;
import GarbageCollector.presentation.util.GrapheDataUI;
import GarbageCollector.presentation.util.StringifyUI;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;


 public class StaticHandler {
 
            //Atribut UI
    private static InformationHide infoHide;
    private static InformationGraphe infoGraphe;
    private static InformationStation infoStation;
    private static InformationEntreeUsine infoEntreeUsine;
    private static InformationSortieUsine infoSortieUsine;
    private static InformationJonction infoJonction;
    private static InformationConvoyeur infoConvoyeur;
    private static InformationSortieEquipement infoSortieEquipement;
    private static InformationEntreeEquipement infoEntreeEquipement;

    private static GraphUI graphe;
    private static GraphePanel graphePanel;
    private static BorderPane borderPane ;
    private static Footer footer;
    private static Controleur controleur;

            //Création d'un controleur
    private static EntreeEquipementUI createConvoyeurEntre;
    private static SortieEquipementUI createConvoyeurSortie;
    private static boolean  createConvoyeurActive = false, 
                            createConvoyeur=false,
                            infoPanHide = false;
    
            //Element courant selectionner
    private static ComposantUI composantSelected = null;
    
        //####################################
        //         Evenement Utilisateur
        //####################################

            //=========================
            //      Information
            //=========================
    
    public static void toggleInfoPan(){
        System.out.println(infoPanHide);
        if(infoPanHide){
            borderPane.setRight(infoGraphe);
        }
        else{
            borderPane.setRight(infoHide);
        }
        infoPanHide = !infoPanHide;
    }
    
    public static void selectionGraphe(){
        if(composantSelected !=null)
            composantSelected.unselect();
        composantSelected =null;
        if(!infoPanHide){
            infoGraphe.setGraphe(graphePanel);
            borderPane.setRight(infoGraphe);
        }
    }
        
    public static void selectionStation(StationUI station){
        if(composantSelected !=null)
            composantSelected.unselect();
        composantSelected = station;
        composantSelected.select(); 
        if(!infoPanHide){
            StationUI toInfo = (StationUI)DestringifyUI.parseVal(controleur.getInformationStation(StringifyUI.stringify(station)));
            station.setCapacite(toInfo.getCapacite());
            station.setCouleur(toInfo.getCouleur());
            station.setDescription(toInfo.getDescription());
            //station.setEntreeEquipement(toInfo.getEntreeEquipement());
            station.setHauteurReal(toInfo.getHauteurReal());
            station.setLongueurReal(toInfo.getLongueurReal());
            station.setMatrice(toInfo.getMatrice());
            station.setDescriptionEntree(toInfo.getEntreeEquipement().get(0).getFluxMatiere());
            station.setNom(toInfo.getNom());
            infoStation.setStation(station);
            borderPane.setRight(infoStation);
        }
    }
     
    public static void selectionEntreeUsine(EntreeUsineUI entreeUsine){
        if(composantSelected !=null)
            composantSelected.unselect();
        composantSelected = entreeUsine;
        composantSelected.select(); 
        if(!infoPanHide){
            EntreeUsineUI toInfo = (EntreeUsineUI)DestringifyUI.parseVal(controleur.getInformationEntreeUsine(StringifyUI.stringify(entreeUsine)));
            entreeUsine.setCapacite(toInfo.getCapacite());
            entreeUsine.setCouleur(toInfo.getCouleur());
            entreeUsine.setDescription(toInfo.getDescription());
            entreeUsine.setHauteurReal(toInfo.getHauteurReal());
            entreeUsine.setLongueurReal(toInfo.getLongueurReal());
            entreeUsine.setFluxEntrants(toInfo.getFluxEntrants());
            entreeUsine.setNom(toInfo.getNom());
            
            infoEntreeUsine.setEntreeUsine(entreeUsine);
            borderPane.setRight(infoEntreeUsine);
        }
    }
    
    public static void selectionSortieUsine(SortieUsineUI sortieUsine){
        System.out.println("Id sortieUsine : " + sortieUsine.getMyId());
        if(composantSelected !=null)
            if(composantSelected !=null)composantSelected.unselect();
        composantSelected = sortieUsine;
        composantSelected.select(); 
        if(!infoPanHide){
            SortieUsineUI toInfo = (SortieUsineUI)DestringifyUI.parseVal(controleur.getInformationSortieUsine(StringifyUI.stringify(sortieUsine)));
            sortieUsine.setCapacite(toInfo.getCapacite());
            sortieUsine.setCouleur(toInfo.getCouleur());
            sortieUsine.setDescription(toInfo.getDescription());
            sortieUsine.setHauteurReal(toInfo.getHauteurReal());
            sortieUsine.setLongueurReal(toInfo.getLongueurReal());
            sortieUsine.setNom(toInfo.getNom());
            infoSortieUsine.setSortieUsine(sortieUsine);
            borderPane.setRight(infoSortieUsine);
        }
    }

    public static void selectionJonction(JonctionUI jonction){
        System.out.println("Id jonction : " + jonction.getMyId());
        if(composantSelected !=null)
            composantSelected.unselect();
        composantSelected = jonction;
        composantSelected.select();       
        if(!infoPanHide){
            JonctionUI toInfo = (JonctionUI)DestringifyUI.parseVal(controleur.getInformationJonction(StringifyUI.stringify(jonction)));
            jonction.setLongueurReal(toInfo.getLongueurReal());
            jonction.setCapacite(toInfo.getCapacite());
            jonction.setCouleur(toInfo.getCouleur());
            infoJonction.setJonction(jonction);
            borderPane.setRight(infoJonction);
        }
    }
    
    public static void selectionEntreeEquipement(EntreeEquipementUI entry){
        if(composantSelected !=null)
            composantSelected.unselect();
        composantSelected = entry;
        composantSelected.select(); 
        if(!infoPanHide){
            EntreeEquipementUI toInfo = (EntreeEquipementUI)DestringifyUI.parseVal(controleur.getInformationEntreeEquipement(StringifyUI.stringify(entry)));
            entry.setFluxMatiere(toInfo.getFluxMatiere());
            infoEntreeEquipement.setEntreeEquipement(entry);
            borderPane.setRight(infoEntreeEquipement);
        }
        if(createConvoyeurActive && entry.getConvoyeur() == null){
            if(createConvoyeur ){
                if(createConvoyeurEntre ==null){
                    if(entry.getEquipement().getMyId() != createConvoyeurSortie.getEquipement().getMyId()){
                        ConvoyeurUI convoyeur = new ConvoyeurUI(entry, createConvoyeurSortie);
                        
                        entry.setConvoyeur(convoyeur);
                        entry.getEquipement().verifieConformite();
                        
                        createConvoyeurSortie.setConvoyeur(convoyeur);
                        createConvoyeurSortie.getEquipement().verifieConformite();
                        
                        graphePanel.ajouteConvoyeur(convoyeur);
                        StaticHandler.createConvoyeurEntre = null;
                        StaticHandler.createConvoyeurSortie = null;
                        createConvoyeur = false;
                    }
                }
            }
            else{
                createConvoyeurEntre = entry;
                createConvoyeur = true;
            }
        }
    }
    
    public static void selectionSortieEquipement(SortieEquipementUI sortie){
            //Etat de selection
        if(composantSelected !=null)
            composantSelected.unselect();
        composantSelected = sortie;
        composantSelected.select(); 
        
            //Information pan
        if(!infoPanHide){
            SortieEquipementUI toInfo = (SortieEquipementUI)DestringifyUI.parseVal(controleur.getInformationSortieEquipement(StringifyUI.stringify(sortie)));
            sortie.setFluxMatiere(toInfo.getFluxMatiere());
            infoSortieEquipement.setSortieEquipement(sortie);
            borderPane.setRight(infoSortieEquipement);
        }
        
            //Creation de convoyeur
        if(createConvoyeurActive && (sortie.getConvoyeur() == null)){
            if(createConvoyeur){
                if(createConvoyeurSortie ==null){
                    if((sortie.getEquipement().getMyId() != createConvoyeurEntre.getEquipement().getMyId())){
                        ConvoyeurUI convoyeur = new ConvoyeurUI(createConvoyeurEntre, sortie);
                        
                        createConvoyeurEntre.setConvoyeur(convoyeur);
                        createConvoyeurEntre.getEquipement().verifieConformite();
                        
                        sortie.setConvoyeur(convoyeur);
                        sortie.getEquipement().verifieConformite();
                        
                        graphePanel.ajouteConvoyeur(convoyeur);
                        createConvoyeur = false;
                        StaticHandler.createConvoyeurEntre = null;
                        StaticHandler.createConvoyeurSortie = null;
                    }
                }
            }
            else{
                createConvoyeurSortie = sortie;
                createConvoyeur = true;
            }
        }
        
    }
    
    public static void selectionConvoyeur(ConvoyeurUI convoyeur){
        SortieEquipementUI sortietoInfo = (SortieEquipementUI)DestringifyUI.parseVal(controleur.getInformationSortieEquipement(StringifyUI.stringify(convoyeur.getSortie())));
        convoyeur.getSortie().setFluxMatiere(sortietoInfo.getFluxMatiere());
        EntreeEquipementUI entreetoInfo = (EntreeEquipementUI)DestringifyUI.parseVal(controleur.getInformationEntreeEquipement(StringifyUI.stringify(convoyeur.getEntree())));
        convoyeur.getEntree().setFluxMatiere(entreetoInfo.getFluxMatiere());
        
        if(composantSelected !=null)
            composantSelected.unselect();
        composantSelected = convoyeur;
        composantSelected.select(); 
        if(!infoPanHide){
            infoConvoyeur.setConvoyeur(convoyeur);
            borderPane.setRight(infoConvoyeur);
        }
    }
    
    public static void pressEnter(){
        if( !(composantSelected instanceof EntreeEquipementUI) &&
            !(composantSelected instanceof SortieEquipementUI))
        {
            if(composantSelected instanceof ConvoyeurUI)
                infoConvoyeur.toggleEditionMode();
            else if(composantSelected instanceof StationUI)
                infoStation.toggleEditionMode();
            else if(composantSelected instanceof EntreeUsineUI)
                infoEntreeUsine.toggleEditionMode();
            else if(composantSelected instanceof SortieUsineUI)
                infoSortieUsine.toggleEditionMode();
            else if(composantSelected instanceof JonctionUI)
                infoJonction.toggleEditionMode();
            else if(composantSelected == null)
                infoGraphe.toggleEditionMode();
        }
    }
            
    public static void setPositionSouris(double x, double y){
        footer.setPositionSouris(x,y);
    }        
            
    
        //####################################
        //           CONVOYEUR
        //####################################
    
    public static void createPossiblePointConvoyeur(double posX, double posY){
        Circle c = new Circle();
        System.out.println("");
        c.setCenterX(posX);
        c.setCenterY(posY);
        c.setRadius(2);
        c.setFill(Color.PINK);
        graphePanel.getChildren().add(c);
    }
    
        //####################################
        //          SUPPRESSION
        //####################################
    
    public static void supprimeEquipement(EquipementUI equipement){
        equipement.supprimeEntrees();
        equipement.supprimeSorties();
        controleur.supprimerEquipement(StringifyUI.stringify(equipement));
        graphePanel.getChildren().remove(equipement);
    }
    
    public static void supprimeConvoyeur(ConvoyeurUI convoyeur){
        controleur.supprimerConvoyeur(StringifyUI.stringify(convoyeur));
        convoyeur.removeConvoyeurToPane(graphePanel);
    }
    
    public static void supprimeSortieEquipement(SortieEquipementUI s){
        if(s.getConvoyeur() != null)
            StaticHandler.supprimeConvoyeur(s.getConvoyeur());
        controleur.supprimerSortieEquipement(StringifyUI.stringify(s));
        if(s.getEquipement() instanceof StationUI){
            ((StationUI)s.getEquipement()).supprimerSortie(s);
        }
    }
   
    public static void supprimeEntreeEquipement(EntreeEquipementUI e){
        if(e.getConvoyeur() != null)
            StaticHandler.supprimeConvoyeur(e.getConvoyeur());
        controleur.supprimerEntreeEquipement(StringifyUI.stringify(e));
        graphePanel.getChildren().remove(e);
        if(e.getEquipement() instanceof JonctionUI){
            ((JonctionUI)e.getEquipement()).supprimeEntree(e);
        }
    }
            
         /*####################################
                    Modificateur
        ######################################*/

    public static Controleur getControleur() {
        return controleur;
    }
    public static void setControleur(Controleur controleur) {
        StaticHandler.controleur = controleur;
    }
    public static void setInfoHide(InformationHide infoHide) {
        StaticHandler.infoHide = infoHide;
    }

    public static void setFooter(Footer footer) {
        StaticHandler.footer = footer;
    }
    public static void setInfoGraphe(InformationGraphe i) {
        infoGraphe= i;
    }
    public static void setInfoStation(InformationStation i) {
        infoStation = i;
    }
    public static void setInfoEntreeUsine(InformationEntreeUsine infoEntreeUsine) {
        StaticHandler.infoEntreeUsine = infoEntreeUsine;
    }
    public static void setInfoSortieUsine(InformationSortieUsine infoSortieUsine) {
        StaticHandler.infoSortieUsine = infoSortieUsine;
    }
    public static void setInfoJonction(InformationJonction infoJonction) {
        StaticHandler.infoJonction = infoJonction;
    }
    public static void setInfoConvoyeur(InformationConvoyeur infoConvoyeur) {
        StaticHandler.infoConvoyeur = infoConvoyeur;
    }
    public static void setInfoSortieEquipement(InformationSortieEquipement infoSortieEquipement) {
        StaticHandler.infoSortieEquipement = infoSortieEquipement;
    }
    public static void setInfoEntreeEquipement(InformationEntreeEquipement infoEntreeEquipement) {
        StaticHandler.infoEntreeEquipement = infoEntreeEquipement;
    }

    public static void setCreateConvoyeurActive(boolean createConvoyeurActive) {
        if(createConvoyeurActive){
            StaticHandler.createConvoyeurEntre = null;
            StaticHandler.createConvoyeurSortie = null;
        }
        StaticHandler.createConvoyeurActive = createConvoyeurActive;
    }
    
    public static void setGraphe(GraphUI graphe) {
        StaticHandler.graphe = graphe;
    }
    public static void setGraphePanel(GraphePanel graphePanel) {
        StaticHandler.graphePanel = graphePanel;
    }
    public static void setBorderPane(BorderPane borderPane) {
        StaticHandler.borderPane = borderPane;
    }
 
    
        /*####################################
                    Chargement Graphe
        ######################################*/
    
    public static void newGraphe(){
        graphe.newGraphe();
    }
    
    public static void chargerGraphe(String filepath){
        String grapheToCharge = controleur.chargerGraphe(filepath);
        GrapheDataUI gdata = (GrapheDataUI)DestringifyUI.parseVal(grapheToCharge);
        graphe.chargerGraphe(gdata.getListeEquipements(), gdata.getListeConvoyeurs(), gdata.getLargeur(), gdata.getLongueur());
    }
    
    public static void undo(){
        GrapheDataUI gdata = (GrapheDataUI)DestringifyUI.parseVal(controleur.annulerAction());
        graphe.chargerGraphe(gdata.getListeEquipements(), gdata.getListeConvoyeurs(),gdata.getLargeur(), gdata.getLongueur());
    }
   
    public static void redo(){
        GrapheDataUI gdata = (GrapheDataUI)DestringifyUI.parseVal(controleur.refaireAction());
        graphe.chargerGraphe(gdata.getListeEquipements(), gdata.getListeConvoyeurs(),gdata.getLargeur(), gdata.getLongueur());
    }
    
 }
