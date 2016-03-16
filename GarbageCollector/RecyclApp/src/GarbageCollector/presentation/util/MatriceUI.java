
package GarbageCollector.presentation.util;

import GarbageCollector.presentation.graph.information.Information;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Iterator;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;



public class MatriceUI extends VBox implements Information{

        /*##############################
                 ATRIBUT
        ##############################*/
    
    private HashMap<Integer, HashMap<String, Double>> matriceTauxRecuperation,matriceMasse;
    private HashMap<String, Double> descriptionEntree;
    private int nbsortie, nbMatiere;

    private HashMap<Integer, HashMap<String, TextField>> valeurMatrice = new HashMap<>();
    private GridPane gridEntry, gridMatrice ;
    Separator separator;
    
    private boolean editionMode = false, afficheTauxRecuperatioMode=true;
    private double masseEntreeUsine =0;    
    
    
        /*##############################
                 CONSTRUCTEUR
        ##############################*/
    
    public MatriceUI(HashMap<String, Double> descriptionEntree, HashMap<Integer, HashMap<String, Double>> matriceTauxRecuperation){
        
        this.matriceTauxRecuperation = matriceTauxRecuperation;
        this.descriptionEntree = descriptionEntree;
        this.nbsortie = matriceTauxRecuperation.size();
        this.nbMatiere = descriptionEntree.size();
        
        this.setPrefSize(225, 20*(nbsortie+2));
        gridEntry = new GridPane();
        HashMap<String, Integer> numerosMatiere = new HashMap();
        
                //Description des entrées
        Label volumeLabel = new Label("Volume : ");
        volumeLabel.setPrefSize(80, 10);
        volumeLabel.setMinWidth(80);
        volumeLabel.getStyleClass().addAll("labelMatrice","headMatrice");
        gridEntry.add(volumeLabel,0,1);
            
        Iterator<String> nameMatiere = this.descriptionEntree.keySet().iterator();
        int indice = 1;
        while(nameMatiere.hasNext())
        {
            String name = nameMatiere.next();
            numerosMatiere.put(name, indice);
            Label matiere = new Label(name);
            matiere.setPrefSize(70, 15);
            matiere.setMinWidth(70);
            matiere.getStyleClass().addAll("labelMatrice","font-Regular","text-regular");
            gridEntry.add(matiere,indice,0);
            
            Label quantiteEntreeLabel = new Label(this.descriptionEntree.get(name)+"");
            quantiteEntreeLabel.setPrefSize(70, 15);
            matiere.setMinWidth(70);
            quantiteEntreeLabel.getStyleClass().add("labelMatrice");
            gridEntry.add(quantiteEntreeLabel,indice,1);
            indice++;
        }
        
        separator = new Separator();
        separator.setOrientation(Orientation.HORIZONTAL);
        
            //Matrice
        gridMatrice = new GridPane();
        Iterator<Integer> iterSortie = matriceTauxRecuperation.keySet().iterator();
        while(iterSortie.hasNext()){
            //On recupere la ligne et la liste des flux pour cette ligne
            Integer numSortie = iterSortie.next();
            HashMap<String, Double> fluxRecupSortie = matriceTauxRecuperation.get(numSortie);
            
            HashMap<String, TextField> ligneCourante = new HashMap<>();
            
            //Pour la ligne courante on parcours toute les colonne
            Iterator<String> iterStr = fluxRecupSortie.keySet().iterator();
            while(iterStr.hasNext()){
                //Mise en place element UI
                String nomMat = iterStr.next();
                Double valor = fluxRecupSortie.get(nomMat);
                TextField valorText = new TextField(valor.toString());
                valorText.getStyleClass().addAll("fieldInformation","dataMatrice","font-Regular","text-regular");
                valorText.setMinHeight(20);
                valorText.setMinWidth(70);
                gridMatrice.add(valorText,numerosMatiere.get(nomMat)+1,numSortie);
                
                //Sauvegarde des data
                ligneCourante.put(nomMat, valorText);
                
            }
            Label sortieLabel = new Label("Sortie "+numSortie+":");
            sortieLabel.setPrefSize(70, 15);
            sortieLabel.getStyleClass().addAll("labelMatrice", "headMatrice","font-Regular","text-regular");
            gridMatrice.add(sortieLabel,0,numSortie);
            this.valeurMatrice.put(numSortie, ligneCourante);
        }

        this.getChildren().addAll(gridEntry, separator,gridMatrice);
    }
 
    
       
        /*##############################
                 Flux
        ##############################*/ 

    public HashMap<Integer, HashMap<String, Double>> getMatrice() {
        return matriceTauxRecuperation;
    }
    
    public void updateMatrice() {
        this.matriceTauxRecuperation.clear();
        for(int i=0; i<nbsortie;i++){
            HashMap<String, Double> ligneCourante = new HashMap<>();
            
            Iterator<String> iterStr = this.valeurMatrice.get(i).keySet().iterator();
            while(iterStr.hasNext()){
               String nameMatiere = iterStr.next();
               ligneCourante.put(   
                                    nameMatiere, 
                                    (Double.parseDouble(this.valeurMatrice.get(i).get(nameMatiere).getText()))
                                );
            }
            this.matriceTauxRecuperation.put(i, ligneCourante);
        }   
    }
 
    
        /*##############################
                 EDITION MODE
        ##############################*/
    
    @Override
    public void toggleEditionMode() {
        for(int i=0; i<nbsortie;i++){
            Iterator<String> iterStr = this.valeurMatrice.get(i).keySet().iterator();
            while(iterStr.hasNext()){
               this.valeurMatrice.get(i).get(iterStr.next()).setEditable(!editionMode);
            }
        }
        
        if(!editionMode){
            for(int i=0; i<nbsortie;i++){
                Iterator<String> iterStr = this.valeurMatrice.get(i).keySet().iterator();
                while(iterStr.hasNext()){
                    String nameFlux = iterStr.next();
                    this.valeurMatrice.get(i).get(nameFlux).getStyleClass().add("labelInformationSetting");
                    this.valeurMatrice.get(i).get(nameFlux).getStyleClass().remove("fieldInformation");
                    this.afficheTauxRecuperation();
                    this.afficheTauxRecuperatioMode = true;
                }
            }
        }
        else{
            for(int i=0; i<nbsortie;i++){
                Iterator<String> iterStr = this.valeurMatrice.get(i).keySet().iterator();
                while(iterStr.hasNext()){
                   String nameFlux = iterStr.next();
                   this.valeurMatrice.get(i).get(nameFlux).getStyleClass().remove("labelInformationSetting");
                   this.valeurMatrice.get(i).get(nameFlux).getStyleClass().add("fieldInformation");
                }
            }
            updateMatrice();
        }
        editionMode = !editionMode;
    }
    
    
        /*##############################
                MODIFICATEUR
        ##############################*/
    
    public void setInstance(HashMap<String, Double> descriptionEntree, 
                            HashMap<Integer, HashMap<String, Double>> matriceTauxRecuperation, 
                            boolean editionMode) {
        
        this.valeurMatrice.clear();
        this.getChildren().removeAll(this.gridEntry, this.separator, this.gridMatrice);
        
        this.matriceTauxRecuperation = matriceTauxRecuperation;
        this.descriptionEntree = descriptionEntree;
        this.nbsortie = matriceTauxRecuperation.size();
        this.nbMatiere = descriptionEntree.size();
        
        this.setPrefSize(70*(nbMatiere+2), 20*(nbsortie+2));
        gridEntry = new GridPane();
        HashMap<String, Integer> numerosMatiere = new HashMap();
        
                //Description des entrées
        Label volumeLabel = new Label("Volume : ");
        volumeLabel.setPrefSize(80, 10);
        volumeLabel.setMinWidth(80);
        volumeLabel.getStyleClass().addAll("labelMatrice","headMatrice");
        gridEntry.add(volumeLabel,0,1);
            
        Iterator<String> nameMatiere = this.descriptionEntree.keySet().iterator();
        int indice = 1;
        while(nameMatiere.hasNext())
        {
            String name = nameMatiere.next();
            numerosMatiere.put(name, indice);
            Label matiere = new Label(name);
            matiere.setPrefSize(70, 15);
            matiere.setMinWidth(70);
            matiere.getStyleClass().addAll("labelMatrice","font-Regular","text-regular");
            gridEntry.add(matiere,indice,0);
            
            Label quantiteEntreeLabel = new Label(this.descriptionEntree.get(name)+"");
            quantiteEntreeLabel.setPrefSize(70, 15);
            matiere.setMinWidth(70);
            quantiteEntreeLabel.getStyleClass().add("labelMatrice");
            gridEntry.add(quantiteEntreeLabel,indice,1);
            indice++;
        }
        
        separator = new Separator();
        separator.setOrientation(Orientation.HORIZONTAL);
        
            //Matrice
        gridMatrice = new GridPane();
        Iterator<Integer> iterSortie = matriceTauxRecuperation.keySet().iterator();
        while(iterSortie.hasNext()){
            //On recupere la ligne et la liste des flux pour cette ligne
            Integer numSortie = iterSortie.next();
            HashMap<String, Double> fluxRecupSortie = matriceTauxRecuperation.get(numSortie);
            
            HashMap<String, TextField> ligneCourante = new HashMap<>();
            
            //Pour la ligne courante on parcours toute les colonne
            Iterator<String> iterStr = fluxRecupSortie.keySet().iterator();
            while(iterStr.hasNext()){
                //Mise en place element UI
                String nomMat = iterStr.next();
                Double valor = fluxRecupSortie.get(nomMat);
                TextField valorText = new TextField(valor.toString());
                valorText.getStyleClass().addAll("fieldInformation","dataMatrice","font-Regular","text-regular");
                valorText.setMinHeight(20);
                valorText.setMinWidth(70);
                valorText.setMaxWidth(70);
                valorText.setAlignment(Pos.CENTER);
                gridMatrice.add(valorText,numerosMatiere.get(nomMat)+1,numSortie);
                
                //Sauvegarde des data
                ligneCourante.put(nomMat, valorText);
                
            }
            Label sortieLabel = new Label("Sortie "+numSortie+":");
            sortieLabel.setMinSize(70, 15);
            sortieLabel.getStyleClass().addAll("labelMatrice", "headMatrice","font-Regular","text-regular");
            gridMatrice.add(sortieLabel,0,numSortie);
            this.valeurMatrice.put(numSortie, ligneCourante);
        }

        this.getChildren().addAll(gridEntry, separator,gridMatrice);
    }
    
    
    private void calculMasseEntreeUsine(){
        this.masseEntreeUsine =0;
        Iterator<String> iterStr = descriptionEntree.keySet().iterator();
        while(iterStr.hasNext()){
            this.masseEntreeUsine += descriptionEntree.get(iterStr.next());
        }
    }
    
    private void calculMasse(){
        this.matriceMasse = new HashMap<>();
        calculMasseEntreeUsine();
        for(int i=0; i<nbsortie;i++){
            HashMap<String, Double> ligneCourante = new HashMap<>();
            
            Iterator<String> iterStr = this.matriceTauxRecuperation.get(i).keySet().iterator();
            while(iterStr.hasNext()){
               String nameMatiere = iterStr.next();
               double masseProduit = (matriceTauxRecuperation.get(i).get(nameMatiere)*descriptionEntree.get(nameMatiere))/100;
               ligneCourante.put(nameMatiere,masseProduit);
            }
            this.matriceMasse.put(i, ligneCourante);
        }   
    }
    
    
    private void afficheMasse(){
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.HALF_UP);
        
        calculMasse();
        for(int i=0; i<nbsortie;i++){
            Iterator<String> iterStr = this.valeurMatrice.get(i).keySet().iterator();
            while(iterStr.hasNext()){
               String nameMatiere = iterStr.next();
               double tauxCourant = this.matriceMasse.get(i).get(nameMatiere);
               this.valeurMatrice.get(i).get(nameMatiere).setText(df.format(tauxCourant)+"");
            }
        }
    }
    
    private void afficheTauxRecuperation(){
        for(int i=0; i<nbsortie;i++){
            Iterator<String> iterStr = this.valeurMatrice.get(i).keySet().iterator();
            while(iterStr.hasNext()){
               String nameMatiere = iterStr.next();
               double pourcentage = this.matriceTauxRecuperation.get(i).get(nameMatiere);
               this.valeurMatrice.get(i).get(nameMatiere).setText(pourcentage+"");
            }
        }
    }
    
    public void toggleAffichage(){
        afficheTauxRecuperatioMode = !afficheTauxRecuperatioMode;
        if(afficheTauxRecuperatioMode){
            afficheTauxRecuperation();
        }
        else{
            afficheMasse();
        }
    }

    public boolean isAfficheTauxRecuperatioMode() {
        return afficheTauxRecuperatioMode;
    }
    
    
}
