
package GarbageCollector.presentation.graph;

import GarbageCollector.presentation.event.StaticHandler;
import GarbageCollector.presentation.graph.convoyeur.ConvoyeurUI;
import GarbageCollector.presentation.graph.convoyeur.LineUI;
import GarbageCollector.presentation.graph.convoyeur.PointConvoyeurUI;
import GarbageCollector.presentation.graph.equipement.EntreeEquipementUI;
import GarbageCollector.presentation.graph.equipement.EntreeUsineUI;
import GarbageCollector.presentation.graph.equipement.EquipementUI;
import GarbageCollector.presentation.graph.equipement.JonctionUI;
import GarbageCollector.presentation.graph.equipement.SortieEquipementUI;
import GarbageCollector.presentation.graph.equipement.SortieUsineUI;
import GarbageCollector.presentation.graph.equipement.StationUI;
import GarbageCollector.presentation.util.StringifyUI;
import java.util.Iterator;
import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;


public class GraphUI extends Pane
{
        /*##############################
                 ATRIBUT
        ##############################*/
    
    private int echelle = 1;
    private final int width = 650, height = 500;

    private GraphePanel graphePanel ;
    
    private boolean magnetOn = false;
    private boolean fistMove =true;
    private double curentTargetX, curentTargetY;
        
        /*##############################
                CONSTRUCTEUR
        ##############################*/
    
    public GraphUI(){
        
        this.setHeight(height);
        this.setWidth(width);
        this.setPrefSize(width,height);
        
        graphePanel = new GraphePanel(this.getWidth(), this.getHeight(), 500, 300);
        StaticHandler.setGraphePanel(graphePanel);
        
        affichagePositionSouris();
        final GraphUI self = this;
        this.widthProperty().addListener(new ChangeListener(){
            @Override public void changed(ObservableValue o, Object oldVal, Object newVal){
                graphePanel.setLongeur(self.getWidth());
                graphePanel.setZoom(echelle);
            }
        });
        this.heightProperty().addListener(new ChangeListener(){
            @Override public void changed(ObservableValue o, Object oldVal, Object newVal){
                graphePanel.setHauteur(self.getHeight());
                graphePanel.setZoom(echelle);
            }
        });
        
                    /*===================
                            ZOOM
                    =====================*/
        // recuperer la position du clic dans la fenetre puis sur le graphe en %
        // appliquer la nouvelle échelle
        // calculer la position du point de zoom sur la nouvelle echelle
        // centrer la fenetre sur ce point avec les translates
        
        this.setOnScroll(new EventHandler<ScrollEvent>() {
            @Override public void handle(ScrollEvent event) 
            {
                int delta = (event.getDeltaY()>0) ? 1 : -1;
                if(echelle+delta>=1 && echelle+delta<=5){ 
                    double xFenetre = event.getX();
                    double yFenetre = event.getY();
                    double xGraphe = xFenetre + Math.abs(graphePanel.getTranslateX());
                    double yGraphe = yFenetre + Math.abs(graphePanel.getTranslateY());
                    double pourcentX = xGraphe/graphePanel.getWidth();
                    double pourcentY = yGraphe/graphePanel.getHeight();


                    echelle = echelle +delta;
                    graphePanel.setZoom(echelle);

                    double xNouveau = pourcentX * graphePanel.getWidth();
                    double yNouveau = pourcentY * graphePanel.getHeight();
                    double translateX = -xNouveau+self.getWidth()/2;
                    double translateY = -yNouveau+self.getHeight()/2;

                    if(translateX>0){
                        translateX = 0;
                    }
                    if(graphePanel.getWidth()+translateX < self.getWidth()){
                        translateX = -graphePanel.getWidth()+self.getWidth();
                    }

                    if(translateY>0){
                        translateY=0;
                    }
                    if(graphePanel.getHeight()+translateY < self.getHeight()){
                        translateY = -graphePanel.getHeight()+self.getHeight();
                    }
                    graphePanel.setTranslateX(translateX);
                    graphePanel.setTranslateY(translateY);
                }
                event.consume();
            }
        });

                /*=================================
                  DEPLACEMENT EQUIPEMENT ET GRAPHE
                ===================================*/

        //L'utilisateur bouge la souris sur la panneau en ayant le bouton gauche appuyé
        //On vérifie si on est en train de deplacer un élément.
        //SI oui on le deplace.
        this.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                
                double xGraphe = (event.getX() + Math.abs(graphePanel.getTranslateX())) /  (echelle*graphePanel.getRatioX());
                double yGraphe = (event.getY() + Math.abs(graphePanel.getTranslateY())) /  (echelle*graphePanel.getRatioY());

                if(event.getGestureSource() instanceof EquipementUI){
                    if(fistMove){
                        curentTargetX = (xGraphe - ((EquipementUI)event.getGestureSource()).getPosXReal());
                        curentTargetY= (yGraphe - ((EquipementUI)event.getGestureSource()).getPosYReal());
                        fistMove = false;
                    }
                }
                if (event.getGestureSource() != self && event.getDragboard().hasString()) {
                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                }
                
                if( event.getDragboard().getString().equals("Deplacement_Station") ||
                    event.getDragboard().getString().equals("Deplacement_Jonction") || 
                    event.getDragboard().getString().equals("Deplacement_Sortie_Usine") || 
                    event.getDragboard().getString().equals("Deplacement_Entree_Usine"))
                {
                    ((EquipementUI)event.getGestureSource()).setPosXReal(xGraphe-curentTargetX);
                    ((EquipementUI)event.getGestureSource()).setPosYReal(yGraphe-curentTargetY);
                    graphePanel.setZoom(echelle);
                }
                
                if( event.getDragboard().getString().equals("Deplacement_PointConvoyeur"))
                {
                    ((PointConvoyeurUI)event.getGestureSource()).setPosXReal(xGraphe);
                    ((PointConvoyeurUI)event.getGestureSource()).setPosYReal(yGraphe);
                    graphePanel.setZoom(echelle);
                }
                event.consume();
            }
        });
        
        /* Lacher le bouton de la souris dans la zone de graphe */
        /* Si l'étiquette sur l'évenement possède des instructions */
        /* On les recuperent et on les appliquent*/
        this.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                boolean success = false;
                
                if (db.hasString()) 
                {
                    double xGraphe = (event.getX() + Math.abs(graphePanel.getTranslateX())) /  (echelle*graphePanel.getRatioX());
                    double yGraphe = (event.getY() + Math.abs(graphePanel.getTranslateY())) /  (echelle*graphePanel.getRatioY());

                    switch(db.getString()){
                        case "Station":
                            if(magnetOn)
                                graphePanel.ajouterStation(getMagnetPosXInGraphe(xGraphe), getMagnetPosXInGraphe(yGraphe));
                            else
                                graphePanel.ajouterStation(xGraphe,yGraphe);
                            break;
                            
                        case "Jonction" :
                            if(magnetOn)
                                graphePanel.ajouterJonction(getMagnetPosXInGraphe(xGraphe), getMagnetPosXInGraphe(yGraphe));
                            else
                                graphePanel.ajouterJonction(xGraphe,yGraphe);
                            break;
                            
                        case "Sortie Usine":
                            if(magnetOn)
                                graphePanel.ajouterSortieUsine(getMagnetPosXInGraphe(xGraphe), getMagnetPosXInGraphe(yGraphe));
                            else
                                graphePanel.ajouterSortieUsine(xGraphe,yGraphe);
                            break;
                            
                        case "Entrée Usine":
                            if(magnetOn)
                                graphePanel.ajouterEntreeUsine(getMagnetPosXInGraphe(xGraphe), getMagnetPosXInGraphe(yGraphe));
                            else
                                graphePanel.ajouterEntreeUsine(xGraphe,yGraphe);
                            break;
                            
                        case "Deplacement_Sortie_Usine" :
                        case "Deplacement_Entree_Usine":
                        case "Deplacement_Station" :
                        case "Deplacement_Jonction" :
                            if(magnetOn){
                                ((EquipementUI)event.getGestureSource()).setPosXReal(getMagnetPosXInGraphe(xGraphe-curentTargetX) );
                                ((EquipementUI)event.getGestureSource()).setPosYReal(getMagnetPosYInGraphe(yGraphe-curentTargetY) );
                            }
                            else{
                                ((EquipementUI)event.getGestureSource()).setPosXReal(xGraphe-curentTargetX);
                                ((EquipementUI)event.getGestureSource()).setPosYReal(yGraphe-curentTargetY);
                            }
                            
                            String equipementStr = StringifyUI.stringify(((EquipementUI)event.getGestureSource()));
                            StaticHandler.getControleur().setPositionEquipement(equipementStr);
                            graphePanel.setZoom(echelle);
                            fistMove = true;
                            break;
                    }
                    
                   success = true;
                }
                
                /* Dire au graphe que l'évenement c'est bien déroulé*/
                event.setDropCompleted(success);
                event.consume();
             }
        });
        
        
         this.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) { 
                double xGraphe = (mouseEvent.getX() + Math.abs(graphePanel.getTranslateX())) /  (echelle*graphePanel.getRatioX());
                double yGraphe = (mouseEvent.getY() + Math.abs(graphePanel.getTranslateY())) /  (echelle*graphePanel.getRatioY());

                if(mouseEvent.getTarget() instanceof Canvas){
                    StaticHandler.selectionGraphe();
                }
                if((mouseEvent.getTarget() instanceof LineUI) && mouseEvent.isControlDown()){
                    LineUI ligne = (LineUI)mouseEvent.getTarget();
                    ligne.getConvoyeur().removeConvoyeurToPane(graphePanel);
                    ligne.getConvoyeur().ajoutePoint(xGraphe,yGraphe,ligne);
                    ligne.getConvoyeur().addConvoyeurToPane(graphePanel);
                }
            }
        }); 
         
        this.getChildren().add(graphePanel);
    }

        /*##############################
                    CONTROLE
        ##############################*/

    private void affichagePositionSouris(){
        this.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                double xGraphe = (event.getX() + Math.abs(graphePanel.getTranslateX())) /  (echelle*graphePanel.getRatioX());
                double yGraphe = (event.getY() + Math.abs(graphePanel.getTranslateY())) /  (echelle*graphePanel.getRatioY());
                StaticHandler.setPositionSouris(xGraphe,yGraphe);
            }
        });
    }
    
        /*##############################
                    GRILLE
        ##############################*/
    
    public void activeGrille() {
        this.graphePanel.toogleGrid();
    }

        /*##############################
                   MAGNETISME
        ##############################*/
    
    public void activeMagne() {
        this.magnetOn = !this.magnetOn;
    }

    public double getMagnetPosXInGraphe(double xGraphe){
        double nbCarreauX = xGraphe/graphePanel.getGridEchelle();
        if(nbCarreauX-((int)nbCarreauX) >0.5)
            return graphePanel.getGridEchelle()*(((int)nbCarreauX)+1);
        else
            return graphePanel.getGridEchelle()*((int)nbCarreauX);
    }
    
    public double getMagnetPosYInGraphe(double yGraphe){
        double nbCarreauY = yGraphe/graphePanel.getGridEchelle();
        if(nbCarreauY-((int)nbCarreauY) >0.5)
            return graphePanel.getGridEchelle()*((int)nbCarreauY+1);
        else
            return graphePanel.getGridEchelle()*((int)nbCarreauY);
    }
    
        /*##############################
                  IN/OUT
        ##############################*/
    
    public void newGraphe(){
        this.graphePanel.getChildren().removeAll(this.graphePanel.getChildren());
        this.graphePanel.resetCanvas();
    }
    
    public void chargerGraphe(List<EquipementUI> listEquipementUI,List<ConvoyeurUI> listConvoyeurUI, double largeur, double longueur){
        //this.graphePanel = new GraphePanel(width, height, longueur, largeur);
        //StaticHandler.setGraphePanel(graphePanel);
        this.graphePanel.getChildren().removeAll(this.graphePanel.getChildren());
        this.graphePanel.resetCanvas();
        Iterator<EquipementUI> iterE = listEquipementUI.iterator();
        while(iterE.hasNext()){
            EquipementUI e = iterE.next();
            if(e instanceof StationUI){
                StationUI val = (StationUI)e;
                StationUI toAdd= new StationUI(graphePanel.getCurentZoom(), graphePanel.getRatioX(), graphePanel.getRatioY(), val.getMyId(),val.getPosXReal() , val.getPosYReal(), val.getLongueurReal(), val.getHauteurReal(), 
                                                val.getNom(), val.getDescription(), val.getCouleur(), val.getCapacite(), val.getNbSorties());
                
                    //Entree Equipement
                int i=0;
                for(EntreeEquipementUI entry : toAdd.getEntreeEquipement()){
                    entry.setMyId(val.getEntreeEquipement().get(i).getMyId());
                    i++;
                }
                
                    //Sortie Equipement
                i=0;
                for(SortieEquipementUI out : toAdd.getSortieEquipement()){
                    out.setMyId(val.getSortieEquipement().get(i).getMyId());
                    i++;
                }
                
                
                    //Matrice
                toAdd.setMatrice(val.getMatrice());
                this.graphePanel.getChildren().add(toAdd);
            }
            else if(e instanceof JonctionUI){
                JonctionUI val = (JonctionUI)e;
                JonctionUI toAdd= new JonctionUI(graphePanel.getCurentZoom(), graphePanel.getRatioX(), graphePanel.getRatioY(), val.getMyId(),val.getPosXReal() , val.getPosYReal(), val.getLongueurReal(), val.getHauteurReal(), 
                                                val.getNom(), val.getDescription(), val.getCouleur(), val.getCapacite(), val.getNbEntree());

                    //Entree Equipement
                int i=0;
                for(EntreeEquipementUI entry : toAdd.getEntreeEquipement()){
                    entry.setMyId(val.getEntreeEquipement().get(i).getMyId());
                    i++;
                }
                
                    //Sortie Equipement
                i=0;
                for(SortieEquipementUI out : toAdd.getSortieEquipement()){
                    out.setMyId(val.getSortieEquipement().get(i).getMyId());
                    i++;
                }
                
                this.graphePanel.getChildren().add(toAdd);
            }
                
            else if(e instanceof SortieUsineUI){
                SortieUsineUI val = (SortieUsineUI)e;
                SortieUsineUI toAdd = new SortieUsineUI(graphePanel.getCurentZoom(), graphePanel.getRatioX(), graphePanel.getRatioY(), val.getMyId(),val.getPosXReal() , val.getPosYReal(), val.getLongueurReal(), val.getHauteurReal(), 
                                                val.getNom(), val.getDescription(), val.getCouleur(), val.getCapacite());
                
                    //Entree Equipement
                int i=0;
                for(EntreeEquipementUI entry : toAdd.getEntreeEquipement()){
                    entry.setMyId(val.getEntreeEquipement().get(i).getMyId());
                    i++;
                }
                
                
                this.graphePanel.getChildren().add(toAdd);
            }
            else if(e instanceof EntreeUsineUI){
                EntreeUsineUI val = (EntreeUsineUI)e;
                EntreeUsineUI toAdd = new EntreeUsineUI(graphePanel.getCurentZoom(), graphePanel.getRatioX(), graphePanel.getRatioY(), val.getMyId(),val.getPosXReal() , val.getPosYReal(), val.getLongueurReal(), val.getHauteurReal(), 
                                                val.getNom(), val.getDescription(), val.getCouleur(), val.getCapacite());
                
                    //Sortie Equipement
                int i=0;
                for(SortieEquipementUI out : toAdd.getSortieEquipement()){
                    out.setMyId(val.getSortieEquipement().get(i).getMyId());
                    i++;
                }
                
                toAdd.setFluxEntrants(val.getFluxEntrants());
                this.graphePanel.getChildren().add(toAdd);
            }
        }
        
        Iterator<ConvoyeurUI> iterC = listConvoyeurUI.iterator();
        while(iterC.hasNext()){
            ConvoyeurUI c = iterC.next();
            ConvoyeurUI toAdd = attacherConvoyeurAuxEquipement(c);
            toAdd.addConvoyeurToPane(this.graphePanel);
        }
    }
    
    private ConvoyeurUI attacherConvoyeurAuxEquipement(ConvoyeurUI c){
        EquipementUI source = findEquipementUIById(c.getSortie().getEquipement().getMyId());
        EquipementUI dest = findEquipementUIById(c.getEntree().getEquipement().getMyId());
        EntreeEquipementUI entree = findEntreeEquipementInEquipementById(dest,c.getEntree().getMyId());
        SortieEquipementUI sortie = findSortieEquipementInEquipementById(source,c.getSortie().getMyId());
            
        return  new ConvoyeurUI(entree, sortie);
    }
    
    public EquipementUI findEquipementUIById(Integer id){
        EquipementUI result = null;
        boolean found = false;
        Iterator<Node> iterChild = this.graphePanel.getChildren().iterator();
        while(iterChild.hasNext() && !found){
            Node n = iterChild.next();
            if(n instanceof EquipementUI){
                EquipementUI e = (EquipementUI)n;
                if(e.getMyId() == id){
                    found = true;
                    result = e;
                }
            }
        }
        return result;
    }
    public SortieEquipementUI findSortieEquipementInEquipementById(EquipementUI e, Integer id){
        SortieEquipementUI result = null;
        boolean found = false;
        Iterator<SortieEquipementUI> iterSortie = e.getSortieEquipement().iterator();
        while(iterSortie.hasNext() && !found){
            SortieEquipementUI n = iterSortie.next();
            if(n.getMyId() == id){
                found = true;
                result = n;
            }  
        }
        return result;
    }
    public EntreeEquipementUI findEntreeEquipementInEquipementById(EquipementUI e, Integer id){
        EntreeEquipementUI result = null;
        boolean found = false;
        Iterator<EntreeEquipementUI> iterEntree = e.getEntreeEquipement().iterator();
        while(iterEntree.hasNext() && !found){
            EntreeEquipementUI n = iterEntree.next();
            if(n.getMyId() == id){
                found = true;
                result = n;
            }  
        }
        return result;
    }
}
