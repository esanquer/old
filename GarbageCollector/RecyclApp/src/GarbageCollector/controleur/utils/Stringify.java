
package GarbageCollector.controleur.utils;

import GarbageCollector.domaine.Convoyeur;
import GarbageCollector.domaine.EntreeEquipement;
import GarbageCollector.domaine.EntreeUsine;
import GarbageCollector.domaine.Equipement;
import GarbageCollector.domaine.FluxMatiere;
import GarbageCollector.domaine.Graphe;
import GarbageCollector.domaine.Jonction;
import GarbageCollector.domaine.MatriceRecuperation;
import GarbageCollector.domaine.SortieEquipement;
import GarbageCollector.domaine.SortieUsine;
import GarbageCollector.domaine.Station;
import GarbageCollector.domaine.TypeProduit;
import java.util.HashMap;
import java.util.Iterator;

public class Stringify {
    
    
    public static String stringify(Object o){
        // tester les methodes avec getClass,  pourquoi pas refaire avec les getFields, etc.
        String objStr = "";
        if(o instanceof Station){
            objStr = stationToString((Station)o);
        }
        else if( o instanceof Jonction){
            objStr = jonctionToString((Jonction)o);
        }
        
        else if( o instanceof EntreeUsine){
            objStr = entreeUsineToString((EntreeUsine)o);
        }
        
        else if( o instanceof SortieUsine){
            objStr = sortieUsineToString((SortieUsine)o);
        }
        
        else if( o instanceof Convoyeur){
            objStr = convoyeurToString((Convoyeur)o);
        }
        else if( o instanceof EntreeEquipement){
            objStr = entreeEquipementToString((EntreeEquipement)o);
        }
        else if( o instanceof SortieEquipement){
            objStr = sortieEquipementToString((SortieEquipement)o);
        }
        else if( o instanceof Graphe){
            objStr = grapheToString((Graphe)o);
        }
        return objStr;
    }
    
    public static String grapheToString(Graphe g){
        StringBuilder encoder = new StringBuilder();
        encoder.append("graphe{");
        encoder.append("[largeur:");
        encoder.append(g.getLargeur());
        encoder.append("]");
        encoder.append("[longueur:");
        encoder.append(g.getLongueur());
        encoder.append("]");
        encoder.append("[equipements:list{");
        for(int i = 0 ; i<g.getEquipements().size();i++){
            encoder.append(stringify(g.getEquipements().get(i)));
        }
        encoder.append("}");
        encoder.append("]");
        
        encoder.append("[convoyeurs:list{");
        for(int i = 0 ; i<g.getConvoyeurs().size();i++){
            encoder.append(stringify(g.getConvoyeurs().get(i)));
        }
        encoder.append("}");
        encoder.append("]");
        
        encoder.append("}");
        
        return encoder.toString();
    }
    
    
    private static String equipementToString(Equipement e){
        StringBuilder encoder = new StringBuilder();
        
        encoder.append("[id:");
        encoder.append(e.getId());
        encoder.append("]");
        encoder.append("[x:");
        encoder.append(e.getPositionX());
        encoder.append("]");
        encoder.append("[y:");
        encoder.append(e.getPositionY());
        encoder.append("]");
        encoder.append("[couleur:");
        encoder.append(e.getCouleur());
        encoder.append("]");
        encoder.append("[entreeEquipement:");
        encoder.append("list{");
        for(int i = 0 ; i < e.getEntreeEquipement().size();i++){
            encoder.append(entreeEquipementToString(e.getEntreeEquipement().get(i)));
        }
        encoder.append("}");
        encoder.append("]");
        encoder.append("[sortieEquipement:");
        encoder.append("list{");
        for(int i = 0 ; i < e.getSortieEquipement().size();i++){
            encoder.append(sortieEquipementToString(e.getSortieEquipement().get(i)));
        }
        encoder.append("}");
        encoder.append("]");
        return encoder.toString();
    }
    
    
    private static String entreeEquipementToString(EntreeEquipement e){
        StringBuilder encoder = new StringBuilder();
        encoder.append("entreeEquipement{");
        encoder.append("[id:");
        encoder.append(e.getId());
        encoder.append("]");
        encoder.append("[fluxMatiere:");
        encoder.append("list{");
        for(int i = 0 ; i < e.getFluxMatiere().size();i++){
            encoder.append(fluxMatiereToString(e.getFluxMatiere().get(i)));
        }
        encoder.append("}");
        encoder.append("]");
        
        if(e.getConvoyeur()!=null){
            encoder.append("[convoyeur:");
            encoder.append(convoyeurToString(e.getConvoyeur()));
            encoder.append("]");
        }
        encoder.append("}");
        return encoder.toString();
    }
    
    private static String sortieEquipementToString(SortieEquipement s){
        StringBuilder encoder = new StringBuilder();
        encoder.append("sortieEquipement{");
        encoder.append("[id:");
        encoder.append(s.getId());
        encoder.append("]");
        encoder.append("[fluxMatiere:");
        encoder.append("list{");
        for(int i = 0 ; i < s.getFluxMatiere().size();i++){
            encoder.append(fluxMatiereToString(s.getFluxMatiere().get(i)));
        }
        encoder.append("}");
        encoder.append("]");
        if(s.getConvoyeur()!=null){
            encoder.append("[convoyeur:");
            encoder.append(convoyeurToString(s.getConvoyeur()));
            encoder.append("]");
        }
        encoder.append("}");
        return encoder.toString();
    }
    
    private static String fluxMatiereToString(FluxMatiere f){
        StringBuilder encoder = new StringBuilder();
        encoder.append("fluxMatiere{");
        encoder.append("[typeProduit:");
        encoder.append(typeProduitToString(f.getTypeProduit()));
        encoder.append("]");
        encoder.append("[debitKilogrammeParHeure:");
        encoder.append(f.getDebitKilogrammesParHeure());
        encoder.append("]");
        encoder.append("}");
        
        return encoder.toString();
    }
    
    private static String typeProduitToString(TypeProduit t){
        StringBuilder encoder = new StringBuilder();
        encoder.append("typeProduit{");
        encoder.append("[nom:");
        encoder.append(t.getNomTypeProduit());
        encoder.append("]");
        encoder.append("}");
        return encoder.toString();
    }
    
    private static String stationToString(Station s){
        StringBuilder encoder = new StringBuilder();
        encoder.append("station{");
        
        encoder.append(equipementToString(s));
        
        encoder.append("[largeur:");
        encoder.append(s.getLargeur());
        encoder.append("]");
        encoder.append("[longueur:");
        encoder.append(s.getLongueur());
        encoder.append("]");
        encoder.append("[capaciteMax:");
        encoder.append(s.getCapaciteMax());
        encoder.append("]");
        encoder.append("[nom:");
        encoder.append(s.getNom());
        encoder.append("]");
        encoder.append("[description:");
        encoder.append(s.getDescription());
        encoder.append("]");
        encoder.append("[nbSorties:");
        encoder.append(s.getNbSortie());
        encoder.append("]");
        encoder.append("[matriceRecuperation:");
        encoder.append(matriceRecuperationToString(s.getMatriceRecuperation()));
        encoder.append("]");
        encoder.append("}");
        return encoder.toString();
    }
    
    private static String matriceRecuperationToString(MatriceRecuperation m){
         StringBuilder encoder = new StringBuilder();
         encoder.append("matriceRecuperation{");
         HashMap <SortieEquipement,HashMap<FluxMatiere,Double>> matrice =  m.getMatrice();
         encoder.append("[matrice:");
         encoder.append("list{");
         Iterator<SortieEquipement> iterSortie = matrice.keySet().iterator();
         while(iterSortie.hasNext()){
             SortieEquipement s = iterSortie.next();
             encoder.append("sortieMatrice{");
             encoder.append("[id:");
             encoder.append(s.getId());
             encoder.append("]");
             encoder.append("[recuperation:");
             encoder.append("list{");
             Iterator<FluxMatiere> iterFlux = matrice.get(s).keySet().iterator();
             while(iterFlux.hasNext()){
                 FluxMatiere f = iterFlux.next();
                 Double repartition = matrice.get(s).get(f);
                 encoder.append("distributionSortieMatrice{[typeProduit:");
                 encoder.append(f.getTypeProduit().getNomTypeProduit());
                 encoder.append("]");
                 encoder.append("[percent:");
                 encoder.append(repartition);
                 encoder.append("]");
                 encoder.append("}");
             }
             encoder.append("}");
             encoder.append("]");
             encoder.append("}");
         }
         
         encoder.append("}");
         encoder.append("]");
         encoder.append("}");
        return encoder.toString();
    }
    
    private static String convoyeurToString(Convoyeur c){
        StringBuilder encoder = new StringBuilder();
        encoder.append("convoyeur{");
        encoder.append("[id:");
        encoder.append(c.getId());
        encoder.append("]");
        encoder.append("[idEquipementSource:");
        encoder.append(c.getSortieEquipement().getEquipement().getId());
        encoder.append("]");
        
        encoder.append("[idSortieEquipement:");
        encoder.append(c.getSortieEquipement().getId());
        encoder.append("]");
        
        encoder.append("[idEquipementDestination:");
        encoder.append(c.getEntreeEquipement().getEquipement().getId());
        encoder.append("]");
        encoder.append("[idEntreeEquipement:");
        encoder.append(c.getSortieEquipement().getId());
        encoder.append("]");
        
        encoder.append("[couleur:");
        encoder.append(c.getCouleur());
        encoder.append("]");
        
        encoder.append("[debitMax:");
        encoder.append(c.getDebitMax());
        encoder.append("]");
        encoder.append("[points:");
        encoder.append("list{");
            for(int i = 0 ; i < c.getPoints().size();i++){
                encoder.append("pointConvoyeur{");
                encoder.append(c.getPoints().get(i));
                encoder.append("}");
            }
        encoder.append("}");
        encoder.append("]");
        encoder.append("}");
        
        return encoder.toString();
    }
    
    private static String entreeUsineToString(EntreeUsine e){
        StringBuilder encoder = new StringBuilder();
        encoder.append("entreeUsine{");
        
        encoder.append(equipementToString(e));
        encoder.append("[largeur:");
        encoder.append(e.getLargeur());
        encoder.append("]");
        encoder.append("[longueur:");
        encoder.append(e.getLongueur());
        encoder.append("]");
        encoder.append("[capaciteMax:");
        encoder.append(e.getCapaciteMax());
        encoder.append("]");
        encoder.append("[nom:");
        encoder.append(e.getNom());
        encoder.append("]");
        encoder.append("[description:");
        encoder.append(e.getDescription());
        encoder.append("]");
        encoder.append("[fluxEntrants:list{");
        for(int i=0 ; i<e.getFluxEntrants().size();i++){
            encoder.append(fluxMatiereToString(e.getFluxEntrants().get(i)));
        }
        encoder.append("}");
        encoder.append("]");
        encoder.append("}");
        
        return encoder.toString();
    }
    
    
    private static String sortieUsineToString(SortieUsine e){
        StringBuilder encoder = new StringBuilder();
        encoder.append("sortieUsine{");
        
        encoder.append(equipementToString(e));
        encoder.append("[largeur:");
        encoder.append(e.getLargeur());
        encoder.append("]");
        encoder.append("[longueur:");
        encoder.append(e.getLongueur());
        encoder.append("]");
        encoder.append("[capaciteMax:");
        encoder.append(e.getCapaciteMax());
        encoder.append("]");
        encoder.append("[nom:");
        encoder.append(e.getNom());
        encoder.append("]");
        encoder.append("[description:");
        encoder.append(e.getDescription());
        encoder.append("]");
        encoder.append("}");
        
        return encoder.toString();
    }
    
    private static String jonctionToString(Jonction j){
        StringBuilder encoder = new StringBuilder();
        encoder.append("jonction{");
        
        encoder.append(equipementToString(j));
        encoder.append("[largeur:");
        encoder.append(j.getLargeur());
        encoder.append("]");
        encoder.append("[longueur:");
        encoder.append(j.getLongueur());
        encoder.append("]");
        encoder.append("[capaciteMax:");
        encoder.append(j.getCapaciteMax());
        encoder.append("]");
        encoder.append("[nom:");
        encoder.append(j.getNom());
        encoder.append("]");
        encoder.append("[description:");
        encoder.append(j.getDescription());
        encoder.append("]");
        encoder.append("[nbEntree:");
        encoder.append(j.getNbEntree());
        encoder.append("]");
        encoder.append("}");
        return encoder.toString();
    }
}
