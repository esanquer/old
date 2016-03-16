package GarbageCollector.domaine;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class Station extends Equipement  implements java.io.Serializable{
    
        /*##############################
                ATRIBUT
        ##############################*/

    private MatriceRecuperation matriceRecuperation;

        /*##############################
                CONSTRUCTEUR
        ##############################*/
    
    public Station(){
        this.nom="";
        this.description="";
        
        this.matriceRecuperation = new MatriceRecuperation(this);
        this.ajouteEntree(new EntreeEquipement(this));
        this.entreeEquipement.get(0).setId(0);
    }
    
    public Station(Double x,Double y){
        super(x,y);
        this.matriceRecuperation = new MatriceRecuperation(this);
        this.ajouteEntree(new EntreeEquipement(this));
        this.entreeEquipement.get(0).setId(0);
        this.nom="";
        this.description="";
    }
    
    public Station(Double x, Double y, Double largeur, Double longueur){
        this(x,y);
        this.largeur=largeur;
        this.longueur=longueur;
    }
    
    public Station(Double x, Double y, Double largeur, Double longueur, String nom, 
                    String description)
    {
        this(x, y, largeur, longueur);
        this.description=description;
        this.nom=nom;
    }
    
    public Station(Double x, Double y, Double largeur, 
                    Double longueur, String nom, String description, 
                    Double capaciteMax, Integer nbSortie)
    {
        super(x, y,capaciteMax);
        this.description=description;
        this.nom=nom;
        this.longueur=longueur;
        this.largeur=largeur;
        this.nbSortie=nbSortie;
        
        this.matriceRecuperation = new MatriceRecuperation(this);
        this.ajouteEntree(new EntreeEquipement(this));
        this.entreeEquipement.get(0).setId(0);
        for(int i = 0 ; i < nbSortie ; i++)
            ajouteSortie(new SortieEquipement(this));
    }

    
        /*##############################
            GESTION ENTREE / SORTIE
        ##############################*/

    @Override
    public void ajouteSortie(SortieEquipement sortie) {
        super.ajouteSortie(sortie);
        this.matriceRecuperation.initialiserNouvelleSortie(sortie);
    }

    @Override
    public void calculerSorties() {
        this.matriceRecuperation.lireNouveauxFluxEntrants();
        HashMap<SortieEquipement, HashMap<FluxMatiere, Double>> matrice = this.matriceRecuperation.getMatrice();
        List<FluxMatiere> listeFluxEntrants = this.entreeEquipement.get(0).getFluxMatiere();
        Iterator<SortieEquipement> iterSorties = this.sortieEquipement.iterator();
        while(iterSorties.hasNext()){
            SortieEquipement sortie = iterSorties.next();
            sortie.reinitialiserFluxMatiere();
            HashMap<FluxMatiere, Double> correspondancesSortie = matrice.get(sortie);
            Iterator<FluxMatiere> iterFluxEntrants = listeFluxEntrants.iterator();
            while(iterFluxEntrants.hasNext()){
                FluxMatiere flux = iterFluxEntrants.next();
                Double pourcentage = correspondancesSortie.get(flux);
                FluxMatiere fluxSortant = new FluxMatiere(flux.getDebitKilogrammesParHeure()*(pourcentage/100), flux.getTypeProduit());
                sortie.ajouteFluxMatiere(fluxSortant);
            }
        }
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
    

    public MatriceRecuperation getMatriceRecuperation() {
        return this.matriceRecuperation;
    }
        /*##############################
                MODIFICATEUR
        ##############################*/
    

    
    public void setMatriceRecuperation(MatriceRecuperation matriceRecuperation) {
        this.matriceRecuperation = matriceRecuperation;
    }
    @Override
    public void setNbEntree(int nbEntree) {}
    
    @Override
    public void setInstance(Equipement e) {
        super.setInstance(e);
        Station newInstance = (Station)e;
        
        this.setNom(newInstance.getNom());
        this.setDescription(newInstance.getDescription());
        this.setLongueur(newInstance.getLongueur());
        this.setLargeur(newInstance.getLargeur());
        this.setMatriceRecuperation(newInstance.getMatriceRecuperation());
    }

        /*##############################
                DESCRIPTEUR
        ##############################*/

    @Override
    public String toString() {
        return "Station{"  +super.toString()+  " _nom=" + nom + ", _description=" + description + ", _longueur=" + longueur + ", _largeur=" + largeur + ", _matriceRecuperation=" + matriceRecuperation + '}';
    }


}