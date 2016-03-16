
package GarbageCollector.presentation.util;

import GarbageCollector.presentation.graph.equipement.EntreeUsineUI;
import GarbageCollector.presentation.graph.information.Information;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class ListeUISetting extends VBox implements Information{

        /*##############################
                 ATRIBUT
        ##############################*/
    
    private HashMap<String, Double> flux;
    private HashMap<String, Double> tauxPureteFlux = new HashMap<>();
    
    private HashMap<Integer, TextField> listMatiereText = new HashMap<>();
    private HashMap<Integer, TextField> listValueText = new HashMap<>();
    
     private ArrayList<ButtonIcon> listButton = new ArrayList<>();
    
    private double masseEntreeTotale =0;    
    
    private GridPane gridEntry;
    
    private int nbMatiere;
    private EntreeUsineUI entreeUsine;

    private boolean editionMode = false, affichageMasseMode=true;
    
    
        /*##############################
                 CONSTRUCTEUR
        ##############################*/
    

    public ListeUISetting(HashMap<String, Double> descriptionEntre , EntreeUsineUI entreeUsin) {
        this.entreeUsine = entreeUsin;
        this.flux = descriptionEntre;
        this.nbMatiere = flux.size();
        
        this.setPrefSize(200, 20*(nbMatiere+2));
        gridEntry = new GridPane();
        gridEntry.setPadding(new Insets(12, 0, 0, 12));
        
        Iterator<String> nameMatiere = this.flux.keySet().iterator();
        int indice = 0;
        while(nameMatiere.hasNext())
        {
            
            final String name = nameMatiere.next();
            
            TextField matiere = new TextField(name);
            matiere.setPrefSize(120, 20);
            matiere.setMinWidth(120);
            matiere.getStyleClass().addAll("fieldInformation","font-Regular","text-regular");
            gridEntry.add(matiere,0,indice);
            listMatiereText.put(indice, matiere);
            
            TextField quantiteLabel = new TextField(this.flux.get(name)+"");
            quantiteLabel.setPrefSize(70, 20);
            matiere.setMinWidth(70);
            quantiteLabel.getStyleClass().add("fieldInformation");
            gridEntry.add(quantiteLabel,1,indice);
            listValueText.put(indice, quantiteLabel);
            
            ButtonIcon deleteIcon = new ButtonIcon("delete16", 20);
            deleteIcon.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override 
                public void handle(MouseEvent mouseEvent){
                    entreeUsine.removeFluxEntrant(name);
                }
            });
            listButton.add(deleteIcon);
            gridEntry.add(deleteIcon,2,indice);        
            indice++;
        }
        
        this.getChildren().add(gridEntry);
    }

    
        /*##############################
                 Flux
        ##############################*/ 

    public HashMap<String, Double> getFlux() {
        return flux;
    }
    
    
    
    public void updateFlux() {
        this.flux.clear();
        for(int i=0; i<nbMatiere;i++){
            this.flux.put(  this.listMatiereText.get(i).getText(), 
                            (Double.parseDouble(this.listValueText.get(i).getText()))
                         );     
        }
    }
 
       
    private void calculMasseEntreeTotale(){
        this.masseEntreeTotale =0;
        Iterator<String> iterStr = flux.keySet().iterator();
        while(iterStr.hasNext()){
            this.masseEntreeTotale += flux.get(iterStr.next());
        }
    }
    
    private void calculTauxPurete(){
        this.tauxPureteFlux = new HashMap<>();
        calculMasseEntreeTotale();
        Iterator<String> iterStr = flux.keySet().iterator();
        while(iterStr.hasNext()){
            String nameMatiere = iterStr.next();
            double tauxPureteCourant = (flux.get(nameMatiere)/this.masseEntreeTotale)*100;
            this.tauxPureteFlux.put(nameMatiere,tauxPureteCourant);
         }
    }
    
    
    private void afficheMasse(){
        Iterator<String> iterStr = flux.keySet().iterator();
        int i=0;
        while(iterStr.hasNext()){
            String nameMatiere = iterStr.next();
            double masseCourant = flux.get(nameMatiere);
            this.listValueText.get(i).setText(masseCourant+"");
            i++;
         }
    }
        
    private void afficheTauxPurete(){
        
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.HALF_UP);
        calculTauxPurete();
        Iterator<String> iterStr = flux.keySet().iterator();
        int i=0;
        while(iterStr.hasNext()){
            String nameMatiere = iterStr.next();
            double tauxPureteCourant = tauxPureteFlux.get(nameMatiere);
            this.listValueText.get(i).setText(df.format(tauxPureteCourant)+"");
            i++;
         }
    }
    
    public void toggleAffichage(){
        affichageMasseMode = !affichageMasseMode;
        if(affichageMasseMode){
            afficheMasse();
        }
        else{
            afficheTauxPurete();
        }
    }

    public boolean isAfficheTauxRecuperatioMode() {
        return affichageMasseMode;
    }
    
        /*##############################
                 EDITION MODE
        ##############################*/
    
    @Override
    public void toggleEditionMode() {
        for(int i=0; i<nbMatiere;i++){
            this.listValueText.get(i).setEditable(!editionMode);
            this.listMatiereText.get(i).setEditable(!editionMode);
        }
        
        if(!editionMode){
            for(int i=0; i<nbMatiere;i++){
                this.listMatiereText.get(i).getStyleClass().add("labelInformationSetting");
                this.listValueText.get(i).getStyleClass().add("labelInformationSetting");
                
                this.listMatiereText.get(i).getStyleClass().remove("fieldInformation"); 
                this.listValueText.get(i).getStyleClass().remove("fieldInformation"); 
                this.gridEntry.add(listButton.get(i),2,i);
            }
        }
        else{
            for(int i=0; i<nbMatiere;i++){
                this.listMatiereText.get(i).getStyleClass().remove("labelInformationSetting");
                this.listValueText.get(i).getStyleClass().remove("labelInformationSetting");
                
                this.listMatiereText.get(i).getStyleClass().add("fieldInformation"); 
                this.listValueText.get(i).getStyleClass().add("fieldInformation"); 
                
                this.gridEntry.getChildren().remove(listButton.get(i));
            }
            updateFlux();
        }
        editionMode = !editionMode;
    }
    
    
    public void setInstance(HashMap<String, Double> descriptionEntre , EntreeUsineUI entreeUsin, boolean editionMode) {
        this.entreeUsine = entreeUsin;
        this.flux = descriptionEntre;
        this.nbMatiere = flux.size();
        this.listValueText.clear();
        this.listMatiereText.clear();
        this.listButton = new ArrayList<>();
        this.getChildren().remove(gridEntry);
        
        this.setPrefSize(200, 20*(nbMatiere+2));
        gridEntry = new GridPane();
        gridEntry.setPadding(new Insets(12, 0, 0, 12));
        
        Iterator<String> nameMatiere = this.flux.keySet().iterator();
        int indice = 0;
        while(nameMatiere.hasNext())
        {
            
            final String name = nameMatiere.next();
            
            TextField matiere = new TextField(name);
            matiere.setPrefSize(120, 20);
            matiere.setMinWidth(120);
            matiere.getStyleClass().addAll("fieldInformation","font-Regular","text-regular");
            gridEntry.add(matiere,0,indice);
            listMatiereText.put(indice, matiere);
            
            TextField quantiteLabel = new TextField(this.flux.get(name)+"");
            quantiteLabel.setPrefSize(70, 20);
            matiere.setMinWidth(70);
            quantiteLabel.getStyleClass().add("fieldInformation");
            gridEntry.add(quantiteLabel,1,indice);
            listValueText.put(indice, quantiteLabel);
            
            ButtonIcon deleteIcon = new ButtonIcon("delete16", 20);
            deleteIcon.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override 
                public void handle(MouseEvent mouseEvent){
                    entreeUsine.removeFluxEntrant(name);
                }
            });
            listButton.add(deleteIcon);     
            indice++;
        }
        
        this.getChildren().add(gridEntry);
        if(this.editionMode != editionMode){
            this.editionMode = editionMode;
            this.toggleEditionMode();
        }
    }

}
