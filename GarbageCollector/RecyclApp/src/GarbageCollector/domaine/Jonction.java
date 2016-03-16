package GarbageCollector.domaine;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Jonction extends Equipement  implements java.io.Serializable{

        /*##############################
                ATRIBUT
        ##############################*/
    

    
        /*##############################
                CONSTRUCTEUR
        ##############################*/
    
    public Jonction(){
        super();
    }
    
    public Jonction(Double x, Double y, Double largeur, 
                    Double longueur, String nom, String description, 
                    Double capaciteMax, Integer nbEntree){
        super(x, y, capaciteMax);
        this.nom=nom;
        this.longueur=longueur;
        this.largeur=largeur;
        this.description=description;
        this.nbEntree=nbEntree;
        this.nbSortie=1;
        for(int i = 0 ; i < nbEntree ; i++)
            this.ajouteEntree(new EntreeEquipement(this));
        
        super.ajouteSortie(new SortieEquipement(this));
    }
    
        /*##############################
            GESTION ENTREE / SORTIE
        ##############################*/
    @Override
    public void calculerSorties() {
        Iterator<EntreeEquipement> iterEntrees = this.entreeEquipement.iterator();
        List<FluxMatiere> fluxSorties = new ArrayList<>();
        while(iterEntrees.hasNext()){
            EntreeEquipement e = iterEntrees.next();
            fluxSorties.addAll(e.getFluxMatiere());
        }
        this.getSortieEquipement().get(0).setFluxMatiere(rassemblerFluxDeMemeMatiere(fluxSorties));
    }
    
    @Override
    public void ajouteSortie(SortieEquipement sortie) {
    }
    
    private List<FluxMatiere> rassemblerFluxDeMemeMatiere(List<FluxMatiere> listeFlux){
        Iterator<FluxMatiere> iterFlux = listeFlux.iterator();
        List<FluxMatiere> listeRassemblee = new ArrayList<>();
        while(iterFlux.hasNext()){
            FluxMatiere f = iterFlux.next();
            boolean trouve = false;
            Iterator<FluxMatiere> iterFluxRassembles = listeRassemblee.iterator();
            while(iterFluxRassembles.hasNext() && !trouve){
                FluxMatiere fluxRassemble = iterFluxRassembles.next();
                if(f.getTypeProduit()== fluxRassemble.getTypeProduit()){
                    fluxRassemble.setDebitKilogrammesParHeure(f.getDebitKilogrammesParHeure()+fluxRassemble.getDebitKilogrammesParHeure());
                    trouve = true;
                }
            }
            if(!trouve){
                listeRassemblee.add(f);
            }
        }
        return listeRassemblee;
    }

    /*##############################
                CONFORME
    ##############################*/
    @Override
    public void appliquerCalcul() {
        this.mettreAJourEntrees();
        this.calculerSorties();
        this.propagerCalcul();
    }

    /*##############################
            ACCESSEUR
    ##############################*/
    
 

    /*##############################
            MODIFICATEUR
    ##############################*/
    
  
    @Override
    public void setNbSortie(int nbSortie) {
        this.nbSortie=nbSortie;
    }
    
    @Override
    public void setInstance(Equipement e) {
        super.setInstance(e);
        Jonction newInstance = (Jonction)e;
        
        this.setNom(newInstance.getNom());
        this.setDescription(newInstance.getDescription());
        this.setLongueur(newInstance.getLongueur());
        this.setLargeur(newInstance.getLargeur());
    }
}