package GarbageCollector.domaine;

import java.util.ArrayList;
import java.util.List;


public class EntreeEquipement  implements java.io.Serializable{
    
        /*##############################
                ATRIBUT
        ##############################*/
    
    private static Integer nextId = 0;
    
    private Equipement equipement;
    private Convoyeur convoyeur;
    private List<FluxMatiere> fluxMatiere;
    private int id;
    
        /*##############################
                CONSTRUCTEUR
        ##############################*/
    public EntreeEquipement(){
        this.fluxMatiere = new ArrayList<>();
    }
    
    public EntreeEquipement(Equipement e){
        this();
        this.id = nextId;
        nextId++;
        this.equipement=e;
    }
    
    public EntreeEquipement(Equipement e,Convoyeur c){
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
    
        /*##############################
                  ACCESSEUR
        ##############################*/

    public Convoyeur getConvoyeur() {
            return this.convoyeur;
    }
    public Equipement getEquipement(){
        return this.equipement;
    }
    public List<FluxMatiere> getFluxMatiere() {
        return fluxMatiere;
    }
    public int getId() {
        return id;
    }
   
    public void setId(int id){
        this.id = id;
    }
        /*##############################
                MODIFICATEUR
        ##############################*/
    
    public void setConvoyeur(Convoyeur c){
        this.convoyeur=c;
    }
    
    public void mettreAJourFluxMatieres(){
        if(this.convoyeur!=null){
            this.fluxMatiere = this.convoyeur.getSortieEquipement().getFluxMatiere();
        }
    }

    public void setFluxMatiere(List<FluxMatiere> fluxMatiere) {
        this.fluxMatiere = fluxMatiere;
    }

    public void setEquipement(Equipement equipement) {
        this.equipement = equipement;
    }
    
    
    
}