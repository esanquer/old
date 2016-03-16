package GarbageCollector.controleur;

import GarbageCollector.controleur.utils.Stringify;
import GarbageCollector.domaine.EntreeEquipement;
import GarbageCollector.domaine.EntreeUsine;
import GarbageCollector.domaine.Equipement;
import GarbageCollector.domaine.FluxMatiere;
import GarbageCollector.domaine.Graphe;
import GarbageCollector.domaine.SortieEquipement;
import GarbageCollector.domaine.Station;
import GarbageCollector.domaine.Jonction;
import GarbageCollector.domaine.MatriceRecuperation;
import GarbageCollector.domaine.SortieUsine;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


public class GestionnaireEquipement {

        /*##############################
                  AJOUT
        ##############################*/

    public static String ajouterEquipement(Object o, Graphe graphe){
        String result ="";
        if(o instanceof Station){
            Station s = (Station)o;
            Station nouvelle = new Station(s.getPositionX(), s.getPositionY(),s.getLargeur(), s.getLongueur(), s.getNom(), s.getDescription(), s.getCapaciteMax(),s.getNbSortie());
            nouvelle.setCouleur(s.getCouleur());
            graphe.ajouterEquipement(nouvelle);
            result = Stringify.stringify(nouvelle);
        }
        if(o instanceof Jonction){
            Jonction j = (Jonction)o;
            Jonction nouvelle = new Jonction(j.getPositionX(), j.getPositionY(), j.getLargeur(), j.getLongueur(), j.getNom(), j.getDescription(), j.getCapaciteMax(), j.getNbEntree());
            nouvelle.setCouleur(j.getCouleur());
            graphe.ajouterEquipement(nouvelle);
            result = Stringify.stringify(nouvelle);
        }
        if(o instanceof EntreeUsine){
            EntreeUsine e = (EntreeUsine)o;
            EntreeUsine nouvelle = new EntreeUsine(e.getPositionX(), e.getPositionY(), e.getLargeur(), e.getLongueur(), e.getNom(), e.getDescription(), e.getCapaciteMax());
            nouvelle.setCouleur(e.getCouleur());
            graphe.ajouterEquipement(nouvelle);
            result = Stringify.stringify(nouvelle);
        }
        if(o instanceof SortieUsine){
            SortieUsine s = (SortieUsine)o;
            SortieUsine nouvelle = new SortieUsine(s.getPositionX(), s.getPositionY(), s.getLargeur(), s.getLongueur(), s.getNom(), s.getDescription(), s.getCapaciteMax());
            nouvelle.setCouleur(s.getCouleur());
            graphe.ajouterEquipement(nouvelle);
            result = Stringify.stringify(nouvelle);
        }
        return result;
    }
    
    public static String ajouterEntreeEquipement(Object o, Graphe graphe) {
        EntreeEquipement entree = (EntreeEquipement)o;
        Equipement equip = findEquipementById(entree.getEquipement().getId(),graphe);
        entree.setEquipement(equip);
        equip.ajouteEntree(entree);
        return Stringify.stringify(entree);
    }
        
    public static String ajouterSortieEquipement(Object o, Graphe graphe){
        SortieEquipement sortie = (SortieEquipement)o;
        Equipement equip = findEquipementById(sortie.getEquipement().getId(),graphe);
        sortie.setEquipement(equip);
        equip.ajouteSortie(sortie);
        return Stringify.stringify(sortie);
    }
 
    
        /*##############################
                SUPRESSION
        ##############################*/

    public static void supprimerEquipement(Object o, Graphe graphe){
        Equipement e = findEquipementById(((Equipement)o).getId(), graphe);

        graphe.supprimerEquipement(e);
    }
    
    public static void supprimerEntreeEquipement(Object o, Graphe graphe){
        EntreeEquipement entree = (EntreeEquipement)o;
        Equipement equip = findEquipementById(entree.getEquipement().getId(),graphe);
        equip.supprimeEntree(entree);
    }
        
    public static void supprimerSortieEquipement(Object o, Graphe graphe) {
        SortieEquipement sortie = (SortieEquipement)o;
        Equipement equip = findEquipementById(sortie.getEquipement().getId(),graphe);
        equip.supprimeSortie(sortie);
    }
    

        /*##############################
                MODIFICATION
        ##############################*/
   
    public static String setSizeEquipement(Object o, Graphe g){
        String result = "";
        if(o instanceof Equipement){
            Equipement e = findEquipementById(((Equipement)o).getId(),g);
            e.setLargeur(((Equipement)o).getLargeur());
            e.setLongueur(((Equipement)o).getLongueur());
            g.setEquipement(e);
            result = Stringify.stringify(e);
        }
        return result;
    }
    
    public static String setPositionEquipement(Object o, Graphe g){
        String result = "";
        if(o instanceof Equipement){
            Equipement e = findEquipementById(((Equipement)o).getId(),g);
            e.setPositionX(((Equipement)o).getPositionX());
            e.setPositionY(((Equipement)o).getPositionY());
            g.setEquipement(e);
            result = Stringify.stringify(e);
        }
        return result;
    }
    
    public static String setInformationEquipement(Object o,Graphe g){
         String result = "";
        if(o instanceof Equipement){
            Equipement val = (Equipement)o;
            Equipement e = findEquipementById(((Equipement)o).getId(),g);
            e.setPositionX(val.getPositionX());
            e.setPositionY(val.getPositionY());
            e.setCapaciteMax(val.getCapaciteMax());
            e.setDescription(val.getDescription());
            e.setNom(val.getNom());
            e.setCouleur(val.getCouleur());
            //e.setEntreeEquipement(val.getEntreeEquipement());
            //e.setSortieEquipement(val.getSortieEquipement());
            e.setLargeur(val.getLargeur());
            e.setLongueur(val.getLongueur());
            g.setEquipement(e);
            result = Stringify.stringify(e);
        }
        return result;
    }

    public static String setInformationStation(Object o, Graphe graphe) {
        Station sVal = (Station)o;
        System.out.println(sVal.getMatriceRecuperation());
        setInformationEquipement(o,graphe);
        Station s = (Station)findEquipementById(sVal.getId(), graphe);
        s.setNom(sVal.getNom());
        s.setNbSortie(sVal.getNbSortie());
        setMatriceRecuperationStation(s,sVal.getMatriceRecuperation(),graphe);
        graphe.setEquipement(s);
        return Stringify.stringify(s);
    
    }
    
    public static String setInformationJonction(Object o, Graphe graphe) {
        Jonction sVal = (Jonction)o;
        setInformationEquipement(o,graphe);
        Jonction s = (Jonction)findEquipementById(sVal.getId(), graphe);
        s.setNbEntree(sVal.getNbEntree());
        graphe.setEquipement(s);
        return Stringify.stringify(s);
    }
    
    public static String setInformationEntreeUsine(Object o, Graphe graphe) {
        EntreeUsine sVal = (EntreeUsine)o;
        setInformationEquipement(o,graphe);
        EntreeUsine e = (EntreeUsine)findEquipementById(sVal.getId(), graphe);
        e.setFluxEntrants(sVal.getFluxEntrants());
        graphe.setEquipement(e);
        return Stringify.stringify(e);
    }
       
    public static String setInformationSortieUsine(Object o, Graphe graphe) {
        SortieUsine sVal = (SortieUsine)o;
        setInformationEquipement(o,graphe);
        SortieUsine e = (SortieUsine)findEquipementById(sVal.getId(), graphe);
        return Stringify.stringify(e);
    }
    
    public static void setMatriceRecuperationStation(Station s,MatriceRecuperation mUI, Graphe graphe){

        MatriceRecuperation m = s.getMatriceRecuperation();
        HashMap<SortieEquipement,HashMap<FluxMatiere, Double>> matriceDomain =  m.getMatrice();
        HashMap<SortieEquipement,HashMap<FluxMatiere, Double>> matriceUI =  mUI.getMatrice();
        Iterator<SortieEquipement> iterSortiesDomain = matriceDomain.keySet().iterator();
        Iterator<SortieEquipement> iterSortiesUI = matriceUI.keySet().iterator();
        while(iterSortiesUI.hasNext()){
            SortieEquipement sortieUI = iterSortiesUI.next();
            Integer numeroSortie = sortieUI.getId();
            SortieEquipement sortieDomain = null;
            boolean trouve = false;
            while(!trouve && iterSortiesDomain.hasNext()){
                SortieEquipement tmp = iterSortiesDomain.next();
                if(tmp.getId()==numeroSortie){
                    sortieDomain=tmp;
                    trouve = true;
                }
            }
            if(!trouve){
                m.initialiserNouvelleSortie(sortieUI);
                matriceDomain = m.getMatrice();
                sortieDomain = sortieUI;
            }

            HashMap<FluxMatiere, Double> flux = matriceDomain.get(sortieDomain);
            HashMap<FluxMatiere, Double> fluxUI = matriceUI.get(sortieUI);

            
            
            Iterator<FluxMatiere> iterFlux = flux.keySet().iterator();
            Iterator<FluxMatiere> iterFluxUI = fluxUI.keySet().iterator();
            while(iterFlux.hasNext()){
                FluxMatiere f = iterFlux.next();
                boolean trouveFlux = false;
                while(!trouveFlux && iterFluxUI.hasNext()){
                    FluxMatiere tmp = iterFluxUI.next();
                    if(tmp.getTypeProduit().getNomTypeProduit().equals(f.getTypeProduit().getNomTypeProduit())){
                        s.getMatriceRecuperation().setPourcentageFluxSurSortie(sortieDomain,f,fluxUI.get(tmp));
                        trouveFlux = true;
                    }
                }
            }
        }
        graphe.setEquipement(s);
    }
    
    
        /*##############################
                RECHERCHE
        ##############################*/
    
    public static Equipement findEquipementById(int idEquipement, Graphe graphe){
        Equipement e = null;
        List<Equipement> listeEquipment =  graphe.getEquipements();   
        Iterator<Equipement> i = listeEquipment.iterator();
        boolean found = false;
        while(i.hasNext() && !found){
            Equipement ie = i.next();
            if(ie.getId()==idEquipement){
                e=ie;
                found=true;
            }
        }
        return e;
    }

    
        /*##############################
                ACCESSEUR
        ##############################*/
    
    public static HashMap<Integer, HashMap<String, Double>> getMatriceRecuperationByIdStation(Integer id, Graphe graphe){
        HashMap<Integer, HashMap<String, Double>> matriceUI = new HashMap<>();
        Station s = (Station)findEquipementById(id, graphe);
        MatriceRecuperation m = s.getMatriceRecuperation();
        HashMap<SortieEquipement,HashMap<FluxMatiere, Double>> matriceDomain =  m.getMatrice();
        Iterator<SortieEquipement> iterSorties = matriceDomain.keySet().iterator();
        while(iterSorties.hasNext()){
            SortieEquipement sortie = iterSorties.next();
            HashMap<FluxMatiere, Double> flux = matriceDomain.get(sortie);
            HashMap<String, Double> fluxUI = new HashMap<>();
            
            Iterator<FluxMatiere> iterFlux = flux.keySet().iterator();
            while(iterFlux.hasNext()){
                FluxMatiere f = iterFlux.next();
                fluxUI.put(f.getTypeProduit().getNomTypeProduit(), flux.get(f));
            }
            matriceUI.put(sortie.getId(), fluxUI);
            
        }
        return matriceUI;
    }
    
    public static HashMap<String, Double> getDescriptionEntreeByIdStation(Integer id, Graphe graphe) {
        HashMap<String, Double> descriptionEntree = new HashMap<>();
        Station s = (Station)findEquipementById(id, graphe);
        if(s.getEntreeEquipement().size()>0){
            List<FluxMatiere> fluxEntrants = s.getEntreeEquipement().get(0).getFluxMatiere();
            Iterator<FluxMatiere> iterFlux = fluxEntrants.iterator();
            while(iterFlux.hasNext()){
                FluxMatiere f = iterFlux.next();
                descriptionEntree.put(f.getTypeProduit().getNomTypeProduit(), f.getDebitKilogrammesParHeure());
            }
        }
        return descriptionEntree;
    }

}
