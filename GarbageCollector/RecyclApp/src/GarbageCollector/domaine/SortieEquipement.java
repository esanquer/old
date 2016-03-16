package GarbageCollector.domaine;

import java.util.ArrayList;
import java.util.List;

public class SortieEquipement  implements java.io.Serializable{

        /*##############################
                ATRIBUT
        ##############################*/
    
    private static Integer nextId = 0;
    
    private int id;
    private Equipement equipement;
    private Convoyeur convoyeur;
    private List<FluxMatiere> fluxMatiere;
    private Integer numSortieDansEquipement=0;
    
    
        /*##############################
                CONSTRUCTEUR
        ##############################*/
    
    public SortieEquipement(){
        this.fluxMatiere = new ArrayList<>();
    }
    
    public SortieEquipement(Equipement e){
        this();
        this.id = nextId;
        nextId++;
        this.equipement=e;
        this.id = e.getSortieEquipement().size();
    }

    public SortieEquipement(Equipement e,Convoyeur c){
        this(e);
        this.convoyeur=c;  
    }

    
        /*##############################
                   ACTION
        ##############################*/
    
    public void ajouteFluxMatiere(FluxMatiere f) {
        this.fluxMatiere.add(f);
    }
    
    public void supprimeFluxMatiere(FluxMatiere f) {
        this.fluxMatiere.remove(f);
    }
    
    public void reinitialiserFluxMatiere(){
        this.fluxMatiere = new ArrayList<>();
    }

    public List<FluxMatiere> getFluxMatiere() {
        return fluxMatiere;
    }

    
        /*##############################
                  ACCESSEUR
        ##############################*/
    
    public Convoyeur getConvoyeur() {
            return this.convoyeur;
    }
    public Equipement getEquipement(){
        return this.equipement;
    }

    public int getId() {
        return id;
    }
   
    
        /*##############################
                MODIFICATEUR
        ##############################*/
    
    public void setConvoyeur(Convoyeur c){
        this.convoyeur=c;
    }
    public void setFluxMatiere(List<FluxMatiere> _fluxMatiere) {
        this.fluxMatiere = _fluxMatiere;
    }
    public void setId(int id) {
        this.id = id;
    }

    public void setEquipement(Equipement equipement) {
        this.equipement = equipement;
    }
 
    
}