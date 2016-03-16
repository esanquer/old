package GarbageCollector.domaine;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class Graphe implements java.io.Serializable
{
        /*##############################
                 ATRIBUT
         ##############################*/
    private List<Convoyeur> convoyeurs; 
    private List<Equipement> equipements;

    private Double longueur=0.0;
    private Double largeur=0.0;
        /*##############################
                CONSTRUCTEUR
        ##############################*/

    public Graphe(){
        this.convoyeurs = new ArrayList<>(); // "diamond inference" : pas besoin de specifier le type pour le new
        this.equipements = new ArrayList<>();
    }

        /*##############################
                    AJOUT
        ##############################*/
    
    public int ajouterEquipement(Equipement e){
        this.equipements.add(e);
        return e.getId();
    }
    
    public int ajouterConvoyeur(Convoyeur c){
        this.convoyeurs.add(c);
        return this.convoyeurs.size();
    }
    
        /*##############################
                  SUPRESSION
        ##############################*/
    
    public void supprimerEquipement(Equipement e){
        //suppression des convoyeurs avant de supprimer l'équipement
        List<EntreeEquipement> entreesEquipement = e.getEntreeEquipement();
        for (EntreeEquipement entreesEquipement1 : entreesEquipement) {
            if (entreesEquipement1.getConvoyeur() != null) {
                this.supprimerConvoyeur(entreesEquipement1.getConvoyeur());
            }
        }
        List<SortieEquipement> sortiesEquipement = e.getSortieEquipement();
        for (SortieEquipement sortiesEquipement1 : sortiesEquipement) {
            if (sortiesEquipement1.getConvoyeur() != null) {
                this.supprimerConvoyeur(sortiesEquipement1.getConvoyeur());
            }
        }
        
        this.equipements.remove(e);
    }
    
    public void supprimerConvoyeur(Convoyeur c){
        
        for(int i = 0 ; i < this.convoyeurs.size();i++){
            if(this.convoyeurs.get(i).getId().equals(c.getId())){
                Convoyeur toDelete = this.convoyeurs.get(i);
                toDelete.getEntreeEquipement().setConvoyeur(null);
                toDelete.getSortieEquipement().setConvoyeur(null);
                this.convoyeurs.remove(toDelete);
            }
        }
        

    }

    
        /*##############################
                ACCESSEUR
        ##############################*/

    public Double getLongueur() {
        return longueur;
    }

    public Double getLargeur() {
        return largeur;
    }
    
    
    
    public List<Convoyeur> getConvoyeurs() {
            return this.convoyeurs;
    }

    public List<Equipement> getEquipements() {
            return this.equipements;
    }

    public List<EntreeUsine> getEntreesUsine() {
        ArrayList<EntreeUsine> entreesUsine = new ArrayList<>();
        Iterator<Equipement> i = this.equipements.iterator();
        while( i.hasNext()){
            Equipement e = i.next();
            if(e instanceof EntreeUsine){
                entreesUsine.add((EntreeUsine)e);
            }

        }
        return entreesUsine;
    }
    
    
        /*##############################
                MODIFICATEUR
        ##############################*/

    public void setLongueur(Double longueur) {
        this.longueur = longueur;
    }

    public void setLargeur(Double largeur) {
        this.largeur = largeur;
    }

    public void setEquipements(List<Equipement> equipements) {
        this.equipements = equipements;
    }

    public void setConvoyeurs(List<Convoyeur> convoyeurs) {
        this.convoyeurs = convoyeurs;
    }
    
     
    
    public void setEquipement(Equipement s){            
        for (Equipement equipement : this.equipements) {
            if (equipement.getId().equals(s.getId())) {
                equipement.setInstance(s);
            }
        }
    }
    
    
        /*##############################
                DESCRIPTEUR
        ##############################*/
    @Override
    public String toString() {
        return "Graphe{" + "convoyeurs=" + convoyeurs + ", equipements=" + equipements + '}';
    }

}