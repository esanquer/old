package GarbageCollector.domaine;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class EntreeUsine extends Equipement  implements java.io.Serializable{

       /*##############################
                ATRIBUT
        ##############################*/
    
    private List<FluxMatiere> fluxEntrants;
    
    
    
        /*##############################
                CONSTRUCTEUR
        ##############################*/
    public EntreeUsine(){
        
        super();
        this.fluxEntrants = new ArrayList<>();
        this.nbSortie=1;
        this.nbEntree=0;
        this.sortieEquipement.add(new SortieEquipement(this));
    }
    
    public EntreeUsine(Double x, Double y, Double largeur, 
                    Double longueur, String nom, String description, 
                    Double capaciteMax)
    {
        super(x, y,capaciteMax);
        this.description=description;
        this.nom=nom;
        this.nbSortie=1;
        this.nbEntree=0;
        this.longueur=longueur;
        this.largeur=largeur;
        this.fluxEntrants = new ArrayList<>();
        super.ajouteSortie(new SortieEquipement(this));
    }


        /*##############################
            GESTION ENTREE / SORTIE
        ##############################*/

    public void ajouterFluxEntrant(FluxMatiere f){
        this.fluxEntrants.add(f);
    }
    @Override
    public void ajouteEntree(EntreeEquipement entree) {}
    @Override
    public void ajouteSortie(SortieEquipement sortie) {}
    @Override
    public void supprimeEntree(EntreeEquipement entree) {}

    @Override
    public void calculerSorties() {
      this.sortieEquipement.get(0).reinitialiserFluxMatiere();
      Iterator<FluxMatiere> iterFlux = this.fluxEntrants.iterator();
      while(iterFlux.hasNext()){
          this.sortieEquipement.get(0).ajouteFluxMatiere(iterFlux.next());
      }
    }

        /*##############################
                    CONFORME
        ##############################*/
    @Override
    public void appliquerCalcul() {
           this.calculerSorties();
           this.propagerCalcul();
    }

    
        /*##############################
                ACCESSEUR
        ##############################*/
    
   
    public List<FluxMatiere> getFluxEntrants() {
        return fluxEntrants;
    }
    

        /*##############################
                MODIFICATEUR
        ##############################*/
    
   
    @Override
    public void setNbEntree(int nbEntree) {}
    @Override
    public void setNbSortie(int nbEntree) {}
    @Override
    public void setInstance(Equipement e) {
        super.setInstance(e);
        EntreeUsine newInstance = (EntreeUsine)e;
        
        this.setNom(newInstance.getNom());
        this.setDescription(newInstance.getDescription());
        this.setLongueur(newInstance.getLongueur());
        this.setLargeur(newInstance.getLargeur());
    }

    public void setFluxEntrants(List<FluxMatiere> fluxEntrants) {
        this.fluxEntrants = fluxEntrants;
    }
    
    
        /*##############################
                DESCRIPTEUR
        ##############################*/
    @Override
    public String toString() {
        
        return "EntreeUsine{" +super.toString()+ " fluxEntrants=" + fluxEntrants + '}';
    }
    
}