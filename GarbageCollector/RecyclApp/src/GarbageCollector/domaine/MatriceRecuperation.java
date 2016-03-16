package GarbageCollector.domaine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class MatriceRecuperation  implements java.io.Serializable{
    private static Integer numeroSortieParDefaut = 0;
    private HashMap<SortieEquipement, HashMap<FluxMatiere, Double>> matrice ;  // à chaque sortie doit correspondre un pourcentage d'un flux disponible en entrée      
    private List<String> nomFluxConnus;
    private Station station;

    public MatriceRecuperation() {
        this.matrice = new HashMap<>();
        nomFluxConnus = new ArrayList<>();
    }

    
    public MatriceRecuperation(Station station) {
        this();
        this.station = station;
        initialiserMatrice();
    }

    public HashMap<SortieEquipement, HashMap<FluxMatiere, Double>> getMatrice() {
        return matrice;
    }

    public void setMatrice(HashMap<SortieEquipement, HashMap<FluxMatiere, Double>> matrice) {
        this.matrice = matrice;
    }
    
    
    public void setPourcentageFluxSurSortie(SortieEquipement sortie, FluxMatiere flux, Double pourcentage){
        HashMap<FluxMatiere,Double> repartitionSortieCorrespondante = this.matrice.get(sortie);
        repartitionSortieCorrespondante.put(flux, pourcentage);
        this.matrice.put(sortie, repartitionSortieCorrespondante);
    }
    

    private void initialiserMatrice(){
        for(int i = 0 ; i < station.getSortieEquipement().size();i++){
            initialiserNouvelleSortie(station.getSortieEquipement().get(i));
        }
    }
    
    public void initialiserNouvelleSortie(SortieEquipement sortie){
        Iterator<FluxMatiere> fluxEntrantsStation = station.getEntreeEquipement().get(0).getFluxMatiere().iterator();
        HashMap<FluxMatiere,Double> repartitionSortieCorrespondante = new HashMap<>();
        Double pourcentage = (station.getSortieEquipement().lastIndexOf(sortie)==numeroSortieParDefaut)?100.0:0.0;
        while(fluxEntrantsStation.hasNext()){
            repartitionSortieCorrespondante.put(fluxEntrantsStation.next(), pourcentage);
        }
        matrice.put(sortie, repartitionSortieCorrespondante);
    }
    
    
    public void ajouterNouveauFluxEntrant(FluxMatiere flux){
        Iterator<SortieEquipement> IterSortiesStation = station.getSortieEquipement().iterator();
        while(IterSortiesStation.hasNext()){
            SortieEquipement sortie = IterSortiesStation.next();
            Double pourcentage = (station.getSortieEquipement().lastIndexOf(sortie)==numeroSortieParDefaut)?100.0:0.0;
            setPourcentageFluxSurSortie(sortie,flux,pourcentage);
        }
        memoriserNomNouveauFlux(flux);
    }
    
    public void lireNouveauxFluxEntrants(){
        List<FluxMatiere> fluxEnrants =  station.getEntreeEquipement().get(0).getFluxMatiere();
        ajouterNouveauxFluxEntrantsSiNessaire();
        Iterator<SortieEquipement> iterSorties = this.station.sortieEquipement.iterator();
        while(iterSorties.hasNext()){
            SortieEquipement sortie = iterSorties.next();
            HashMap<FluxMatiere,Double> repartitionSortieCorrespondante = matrice.get(sortie);
            HashMap<FluxMatiere,Double> repartitionAvecNouveauxFlux = new HashMap<>();
            Set<FluxMatiere> listeFluxARemplacer = repartitionSortieCorrespondante.keySet();
            Iterator<FluxMatiere> iterFlux = listeFluxARemplacer.iterator();
            while(iterFlux.hasNext()){
                FluxMatiere ancien = iterFlux.next();
                FluxMatiere nouveau = trouverFluxParNomMatiere(fluxEnrants,ancien);
                Double pourcentage = repartitionSortieCorrespondante.get(ancien);
                repartitionAvecNouveauxFlux.put(nouveau, pourcentage);
            }
            matrice.put(sortie, repartitionAvecNouveauxFlux);
        }
        
        
    }
    
    private FluxMatiere trouverFluxParNomMatiere(List<FluxMatiere> listeFlux, FluxMatiere critere){
        FluxMatiere flux = null;
        Iterator<FluxMatiere> iterFlux = listeFlux.iterator();
        boolean atrouve = false;
        while(iterFlux.hasNext() && !atrouve){
            FluxMatiere f = iterFlux.next();
            if(f.getTypeProduit().getNomTypeProduit().equals(critere.getTypeProduit().getNomTypeProduit())){
                flux = f;
                atrouve = true;
            }
        }
        return flux;
    } 
    private void ajouterNouveauxFluxEntrantsSiNessaire(){
         List<FluxMatiere> fluxEnrants =  station.getEntreeEquipement().get(0).getFluxMatiere();
         Iterator<FluxMatiere> iterFluxEntrants = fluxEnrants.iterator();
         while(iterFluxEntrants.hasNext()){
             FluxMatiere f = iterFluxEntrants.next();
             if(!this.nomFluxConnus.contains(f.getTypeProduit().getNomTypeProduit())){
                 ajouterNouveauFluxEntrant(f);
                 memoriserNomNouveauFlux(f);
             }
         }   
    }
    
    private void memoriserNomNouveauFlux(FluxMatiere f){
        if(!this.nomFluxConnus.contains(f.getTypeProduit().getNomTypeProduit())){
            this.nomFluxConnus.add(f.getTypeProduit().getNomTypeProduit());
        }
    }
    
    @Override
    public String toString() {
        return "MatriceRecuperation{}";
    }
        
        
}