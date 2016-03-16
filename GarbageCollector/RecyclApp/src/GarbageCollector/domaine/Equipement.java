package GarbageCollector.domaine;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;



public abstract class Equipement  implements java.io.Serializable{
    
        /*##############################
                ATRIBUT
        ##############################*/
    
    private static Integer nextId = 0;
    protected Integer id;

    protected Double positionX ; // en mètres
    protected Double positionY ; // en mètres
    protected Double capaciteMax ;
    
    protected Integer nbSortie;
    protected Integer nbEntree;

    protected Graphe graphe;

    protected List<EntreeEquipement> entreeEquipement;
    protected List<SortieEquipement> sortieEquipement;

    protected String nom ;
    protected String description;
    protected Double longueur;
    protected Double largeur;
    
    protected String couleur;
        /*##############################
                CONSTRUCTEUR
        ##############################*/
    
    public Equipement(){
        this.entreeEquipement = new ArrayList<>();
        this.sortieEquipement = new ArrayList<>();
    }

    public Equipement(Double x, Double y)
    {
        this();
        this.positionX=x;
        this.positionY=y;
    }
    
    public Equipement(Double x, Double y, Double capaciteMax,
                        Integer nbSortie, Integer nbEntree)
    {
        this();
        this.positionX=x;
        this.positionY=y;
        this.nbSortie=nbSortie;
        this.nbEntree=nbEntree;
        this.capaciteMax=capaciteMax;
    }
    
    public Equipement(Double x, Double y, Double capaciteMax)
    {
        this();
        this.id = nextId;
        nextId++;
        this.positionX=x;
        this.positionY=y;
        this.capaciteMax=capaciteMax;
    }

    
        /*##############################
            GESTION ENTREE / SORTIE
        ##############################*/
    
    public void ajouteEntree(EntreeEquipement entree) {
        this.getEntreeEquipement().add(entree);
    }

    public void ajouteSortie(SortieEquipement sortie) {
        this.getSortieEquipement().add(sortie);
    }

    public void supprimeSortie(SortieEquipement sortie) {
        this.getSortieEquipement().remove(sortie);
    }

    public void supprimeEntree(EntreeEquipement entree) {
        this.getEntreeEquipement().remove(entree);
    }
    
    public void mettreAJourEntrees(){
        Iterator<EntreeEquipement> iterEntrees = this.entreeEquipement.iterator();
        while(iterEntrees.hasNext()){
            EntreeEquipement e = iterEntrees.next();
            e.mettreAJourFluxMatieres();
        }
    }

    
        /*##############################
                    CONFORME
        ##############################*/
 
    public boolean estConforme(){
       Double debitEntrant = 0.0;
       Iterator<FluxMatiere> iterFlux = this.entreeEquipement.get(0).getFluxMatiere().iterator();

       while(iterFlux.hasNext()){
           debitEntrant+= iterFlux.next().getDebitKilogrammesParHeure();
       }
       return debitEntrant <= this.capaciteMax;
    }
    
    public void propagerCalcul(){
        Iterator<SortieEquipement> iterSorties = this.sortieEquipement.iterator();
        while(iterSorties.hasNext()){

            SortieEquipement s = iterSorties.next();
            if(s.getConvoyeur()!=null){
                s.getConvoyeur().getEntreeEquipement().getEquipement().appliquerCalcul();
            }
        }
    }
    
    public abstract void calculerSorties();
    
    public abstract void appliquerCalcul();
    
        
       /*##############################
                ACCESSEUR
        ##############################*/
    
    public Double getPositionX() {
        return positionX;
    }
    public Double getPositionY() {
        return positionY;
    }
    public Integer getId(){
        return this.id;
    }
    public Double getCapaciteMax() {
        return capaciteMax;
    }

    public List<EntreeEquipement> getEntreeEquipement() {
        return entreeEquipement;
    }
    public List<SortieEquipement> getSortieEquipement() {
        return sortieEquipement;
    }


    public Integer getNbSortie() {
        return nbSortie;
    }

    public Integer getNbEntree() {
        return nbEntree;
    }

    public String getNom() {
        return nom;
    }

    public String getDescription() {
        return description;
    }

    public Double getLongueur() {
        return longueur;
    }

    public Double getLargeur() {
        return largeur;
    }

    public String getCouleur() {
        return couleur;
    }

    
    
        /*##############################
                MODIFICATEUR
        ##############################*/

    public void setId(Integer id) {
        this.id = id;
    }

    public void setCouleur(String couleur) {
        this.couleur=couleur;
    }

    
    public void setEntreeEquipement(List<EntreeEquipement> entreeEquipement) {
        this.entreeEquipement = entreeEquipement;
    }

    public void setSortieEquipement(List<SortieEquipement> sortieEquipement) {
        this.sortieEquipement = sortieEquipement;
    }
    
    
    
    public void setPosition(Double x, Double y) {
        this.positionX=x;
        this.positionY=y;
    }
    public void setPositionX(Double positionX) {
        this.positionX=positionX;
    }
    public void setPositionY(Double positionY) {
        this.positionY=positionY;
    }
    public void setCapaciteMax(Double capaciteMax) {
        this.capaciteMax=capaciteMax;
    }
    public void setNbSortie(int nbSortie) {
        this.nbSortie=nbSortie;
    }
    public void setNbEntree(int nbEntree) {
        this.nbEntree=nbEntree;
    } 
    public void setPosition(double x, double y){
        this.positionX=x;
        this.positionX=y;
    }
    public void setInstance(Equipement e){
        this.setPosition(e.getPositionX(), e.getPositionY());
        this.setCapaciteMax(e.getCapaciteMax());
    }

    public void setNom(String nom) {
        this.nom=nom;
    }

    public void setDescription(String description) {
        this.description=description;
    }

    public void setLongueur(Double longueur) {
        this.longueur=longueur;
    }

    public void setLargeur(Double largeur) {
        this.largeur=largeur;
    }
    
    
    
        /*##############################
                DESCRIPTEUR
        ##############################*/
    @Override
    public String toString() {
        return "Equipement{" + "_id=" + id + ", _positionX=" + positionX + ", _positionY=" + positionY + '}';
    }

    
    public void attacherEntreesEtSorties(){
        for (EntreeEquipement entreeEquipement1 : this.entreeEquipement) {
            entreeEquipement1.setEquipement(this);
        }
        for (SortieEquipement sortieEquipement1 : this.sortieEquipement) {
            sortieEquipement1.setEquipement(this);
        }
    }
}