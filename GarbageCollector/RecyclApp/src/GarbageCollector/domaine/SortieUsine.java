package GarbageCollector.domaine;



public class SortieUsine extends Equipement  implements java.io.Serializable{

        /*##############################
                ATRIBUT
        ##############################*/
    
    
        /*##############################
                CONSTRUCTEUR
        ##############################*/
 
    public SortieUsine(){
        super();
        this.nbSortie=0;
        this.nbEntree=1;
        super.ajouteEntree(new EntreeEquipement(this));
    }
    
    public SortieUsine(Double x, Double y, Double largeur, 
                    Double longueur, String nom, String description, 
                    Double capaciteMax)
    {
        super(x, y,capaciteMax);
        this.description=description;
        this.nom=nom;
        this.nbSortie=0;
        this.nbEntree=1;
        this.longueur=longueur;
        this.largeur=largeur;
        super.ajouteEntree(new EntreeEquipement(this));
    }


        /*##############################
            GESTION ENTREE / SORTIE
        ##############################*/

    @Override
    public void calculerSorties(){}
    
        /*##############################
                    CONFORME
        ##############################*/
    
    @Override
    public void appliquerCalcul() {
        this.mettreAJourEntrees();
    }
    
    
        /*##############################
                ACCESSEUR
        ##############################*/
    


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
        SortieUsine newInstance = (SortieUsine)e;
        
        this.setNom(newInstance.getNom());
        this.setDescription(newInstance.getDescription());
        this.setLongueur(newInstance.getLongueur());
        this.setLargeur(newInstance.getLargeur());
    }

        /*##############################
                DESCRIPTEUR
        ##############################*/
    @Override
    public String toString() {
        super.toString();
        return "SortieUsine{"  +super.toString()+ '}';
    }
}
