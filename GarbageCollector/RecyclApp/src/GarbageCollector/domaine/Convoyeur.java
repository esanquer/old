package GarbageCollector.domaine;

import java.util.Iterator;
import java.util.List;

public class Convoyeur  implements java.io.Serializable{
    
        /*##############################
                ATRIBUT
        ##############################*/
    private static Integer nextId =0;
    private static Double debitDefaut;

    private Integer id;
    private String couleur;
    private Double debitMax;
    private Graphe graphe;
    private SortieEquipement sortieEquipement;
    private EntreeEquipement entreeEquipement;
    private List<String> points;
    
        /*##############################
                CONSTRUCTEUR
        ##############################*/

    public Convoyeur(){
        this.debitMax = debitDefaut;
        this.id = nextId;
        nextId++;
    }

    public Convoyeur(SortieEquipement sortie, EntreeEquipement entree){
        this();
        this.entreeEquipement = entree;
        this.sortieEquipement = sortie;
    }
    
        /*##############################
                 CONFORME
        ##############################*/
    
    public boolean estConforme() {
        Double debitEntrant = 0.0;
       Iterator<FluxMatiere> iterFlux = this.entreeEquipement.getFluxMatiere().iterator();

       while(iterFlux.hasNext()){
           debitEntrant+= iterFlux.next().getDebitKilogrammesParHeure();
       }
       return debitEntrant <= this.debitMax;
    }
    
        /*##############################
                MODIFICATEUR
        ##############################*/

    public void setEntreeEquipement( EntreeEquipement e) {
        this.entreeEquipement=e;
        e.setConvoyeur(this);
    }

    public void setSortieEquipement(SortieEquipement s) {
        this.sortieEquipement = s;
        s.setConvoyeur(this);
    }

    public void setCouleur(String c) {
        this.couleur = c;
    }

    public void setDebitMax(Double dm) {
        this.debitMax = dm;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public void setPoints(List<String> points) {
        this.points = points;
    }
    
    
        /*##############################
                ACCESSEUR
        ##############################*/
    
    public String getCouleur() {
        return couleur;
    }
    public Double getDebitMax() {
        return debitMax;
    }
    public Graphe getGraphe() {
        return graphe;
    }
    public EntreeEquipement getEntreeEquipement(){
        return this.entreeEquipement;
    }
    public SortieEquipement getSortieEquipement(){
        return this.sortieEquipement;
    }
    public Integer getId() {
        return id;
    }

    public List<String> getPoints() {
        return points;
    }
    
        /*##############################
                DESCRIPTEUR
        ##############################*/

    @Override
    public String toString() {
        return "Convoyeur{" + "id=" + getId() + ", couleur=" + couleur + ", debitMax=" + debitMax + '}';
    }



        
    
}