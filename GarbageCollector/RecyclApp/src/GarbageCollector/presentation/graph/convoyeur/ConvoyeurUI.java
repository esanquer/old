
package GarbageCollector.presentation.graph.convoyeur;

import GarbageCollector.presentation.graph.equipement.ComposantUI;
import GarbageCollector.presentation.graph.equipement.EntreeEquipementUI;
import GarbageCollector.presentation.graph.equipement.SortieEquipementUI;
import java.util.ArrayList;
import java.util.Iterator;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class ConvoyeurUI implements ComposantUI
{

        /*##############################
                 ATRIBUT
        ##############################*/
    
    private ArrayList<PointUI> listPoint = new ArrayList<> ();  
    private ArrayList<LineUI> listLine = new ArrayList<> (); 
     
    private String couleur;
    private double capacite=1000;
    
    private EntreeEquipementUI entree;
    private SortieEquipementUI sortie;
    
    private Integer id;
    private boolean estConforme;
    
        /*##############################
                CONSTRUCTEUR
        ##############################*/
    
    public ConvoyeurUI(EntreeEquipementUI entree, SortieEquipementUI sortie) {
        this.entree = entree;
        this.entree.setConvoyeur(this);
        this.sortie = sortie;
        this.sortie.setConvoyeur(this);
        this.id = -1;
        couleur = "000000";
        listPoint.add(entree);
        listPoint.add(sortie);
        revalidateLine();
    }

        /*##############################
                SELECTION
        ##############################*/
    
    @Override
    public void select() {
        for(LineUI ligne : listLine)
            ligne.select();
        
        for(PointUI point : listPoint)
            point.select();
    }
    
    @Override
    public void unselect() {
        for(LineUI ligne : listLine)
            ligne.unselect();
        
        for(PointUI point : listPoint)
            point.unselect();
    }

    
        /*##############################
                    AJOUT
        ##############################*/
    
    public void ajoutePoint(double x, double y, LineUI line){
        
        int indicePointOut = this.listPoint.indexOf(line.getSortie());
        PointUI pointConvoyeur = new PointConvoyeurUI(x, y, 
                                                    line.getSortie().getZoom(), 
                                                    line.getSortie().getRatioX(),
                                                    line.getSortie().getRatioY(),
                                                    this);
        this.listPoint.add(indicePointOut, pointConvoyeur);
        revalidateLine();
    }
    
    public void addConvoyeurToPane(Pane p){
        for(LineUI ligne : listLine)
            p.getChildren().add(ligne);
        
        for(PointUI point : listPoint){
            if(!(point instanceof EntreeEquipementUI) && !(point instanceof SortieEquipementUI)){
                p.getChildren().add((PointConvoyeurUI)point);
            }
        }
    }
    public void removeConvoyeurToPane(Pane p){
        for(LineUI ligne : listLine)
            p.getChildren().remove(ligne);
        
        for(PointUI point : listPoint){
            if(point instanceof EntreeEquipementUI){
                ((EntreeEquipementUI)point).setConvoyeur(null);
            }
            else if(point instanceof SortieEquipementUI){
                ((SortieEquipementUI)point).setConvoyeur(null);
            }
            else{
                p.getChildren().remove((PointConvoyeurUI)point);
            }
        }
    }
    
    public void revalidateLine(){
        this.listLine = new ArrayList<>();
        for(int i=0; i<listPoint.size()-1;i++){
            LineUI ligne = new LineUI(listPoint.get(i),listPoint.get(i+1),this);
            this.listLine.add(ligne);
        }
    }
    
        /*##############################
                ACCESSEUR
        ##############################*/

    public boolean isEstConforme() {
        return estConforme;
    }
    
    public EntreeEquipementUI getEntree() {
        return this.entree;
    }
    public SortieEquipementUI getSortie() {
        return this.sortie;
    }
    public String getCouleur() {
        return couleur;
    }
    public double getCapacite() {
        return capacite;
    }
    public ArrayList<PointUI> getListPoint() {
        return listPoint;
    }
    
    public Integer getMyId() {
        return id;
    }    

        /*##############################
                MODIFICATEUR
        ##############################*/
    
    public void setListPoint(ArrayList<PointUI> listPoint) {
        this.listPoint = listPoint;
    }
    public void setEntree(EntreeEquipementUI entree) {
        this.entree = entree;
        this.listPoint.set(0,entree);
        verifieConformite();
    }
    public void setCouleur(String couleur) {
        for(LineUI ligne : listLine)
            ligne.setStroke(Color.web(this.couleur));
        
        for(PointUI point : listPoint){
            if(!(point instanceof EntreeEquipementUI) && !(point instanceof SortieEquipementUI)){
                ((PointConvoyeurUI)point).setStroke(Color.web(this.couleur));
            }
        }
        this.couleur = couleur;
    }
    public void setCapacite(double capacite) {
        this.capacite = capacite;
    }

    public void setSortie(SortieEquipementUI sortie) {
        this.sortie = sortie;
        this.listPoint.set(this.listPoint.size()-1,sortie);
    }

    public void setMyId(Integer id) {
        this.id = id;
    }
    
    

    public void verifieConformite() {
        System.out.println("getFluxMatiere() : "+this.getSortie().getFluxMatiere());
        System.out.println("this.capacite : "+this.capacite);
            
        if(this.getEntree().calculMasseEntreeTotale()>this.capacite){
            for(LineUI line : listLine){
                System.out.println("line.getStyleClass() : "+line.getStyleClass());
                if(!line.getStyleClass().contains("convoyeur-error"))
                    line.getStyleClass().add("convoyeur-error");
                this.estConforme = false;
            }
        }
        else{
            for(LineUI line : listLine){
                line.getStyleClass().remove("convoyeur-error");
            }
            this.estConforme = true;
        }
        this.getEntree().verifieConformite();
    }
    
          
    private double calculMasseEntreeTotale(){
        double masseEntreeUsine =0;
        Iterator<String> iterStr = this.getEntree().getFluxMatiere().keySet().iterator();
        while(iterStr.hasNext()){
            masseEntreeUsine += this.getEntree().getFluxMatiere().get(iterStr.next());
        }
        return masseEntreeUsine;
    }
    
}