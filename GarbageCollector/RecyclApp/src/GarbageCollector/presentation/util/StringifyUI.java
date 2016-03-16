
package GarbageCollector.presentation.util;

import GarbageCollector.presentation.graph.convoyeur.ConvoyeurUI;
import GarbageCollector.presentation.graph.equipement.EntreeEquipementUI;
import GarbageCollector.presentation.graph.equipement.EntreeUsineUI;
import GarbageCollector.presentation.graph.equipement.EquipementUI;
import GarbageCollector.presentation.graph.equipement.JonctionUI;
import GarbageCollector.presentation.graph.equipement.SortieEquipementUI;
import GarbageCollector.presentation.graph.equipement.SortieUsineUI;
import GarbageCollector.presentation.graph.equipement.StationUI;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;



public class StringifyUI {

    
    public static String stringify(Object o){
        // tester les methodes avec getClass,  pourquoi pas refaire avec les getFields, etc.
        String objStr = "";
        if(o instanceof StationUI){
            objStr = stationToString((StationUI)o);
        }
        else if( o instanceof JonctionUI){
            objStr = jonctionToString((JonctionUI)o);
        }
        
        else if( o instanceof EntreeUsineUI){
            objStr = entreeUsineToString((EntreeUsineUI)o);
        }
        
        else if( o instanceof SortieUsineUI){
            objStr = sortieUsineToString((SortieUsineUI)o);
        }
        
        else if( o instanceof ConvoyeurUI){
            objStr = convoyeurToString((ConvoyeurUI)o);
        }
        else if( o instanceof EntreeEquipementUI){
            objStr = entreeEquipementToString((EntreeEquipementUI)o);
        }
        else if( o instanceof SortieEquipementUI){
            objStr = sortieEquipementToString((SortieEquipementUI)o);
        }
        else if(o instanceof HashMap){
            objStr = matriceRecuperationToString((HashMap<Integer,HashMap<String,Double>>)o);
        }
        return objStr;
    }
    
    
    
    private static String equipementToString(EquipementUI e){
        StringBuilder encoder = new StringBuilder();
        
        encoder.append("[id:");
        encoder.append(e.getMyId());
        encoder.append("]");
        encoder.append("[x:");
        encoder.append(e.getPosXReal());
        encoder.append("]");
        encoder.append("[y:");
        encoder.append(e.getPosYReal());
        encoder.append("]");

        /*encoder.append("[entreeEquipement:");
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
        encoder.append("]");*/
        return encoder.toString();
    }
    
    
    private static String entreeEquipementToString(EntreeEquipementUI e){
        StringBuilder encoder = new StringBuilder();
        encoder.append("entreeEquipement{");
        encoder.append("[equipementId:");
        encoder.append(e.getEquipement().getMyId());
        encoder.append("]");
        encoder.append("[id:");
        encoder.append(e.getMyId());
        encoder.append("]");
        encoder.append("[fluxMatiere:");
        encoder.append("list{");
        Iterator<Entry<String,Double>> iterFlux = e.getFluxMatiere().entrySet().iterator();
        while(iterFlux.hasNext()){
            Entry<String,Double> entry = iterFlux.next();
            encoder.append(fluxMatiereToString(entry.getKey(), entry.getValue()));
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
    
    private static String sortieEquipementToString(SortieEquipementUI s){
        StringBuilder encoder = new StringBuilder();
        encoder.append("sortieEquipement{");
        encoder.append("[equipementId:");
        encoder.append(s.getEquipement().getMyId());
        encoder.append("]");
        encoder.append("[id:");
        encoder.append(s.getMyId());
        encoder.append("]");
        encoder.append("[fluxMatiere:");
        encoder.append("list{");
        Iterator<Entry<String,Double>> iterFlux = s.getFluxMatiere().entrySet().iterator();
        while(iterFlux.hasNext()){
            Entry<String,Double> entry = iterFlux.next();
            encoder.append(fluxMatiereToString(entry.getKey(), entry.getValue()));
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
    
    
    private static String stationToString(StationUI s){
        StringBuilder encoder = new StringBuilder();
        encoder.append("station{");
        
        encoder.append(equipementToString(s));
        encoder.append("[largeur:");
        encoder.append(s.getHauteurReal());
        encoder.append("]");
        encoder.append("[longueur:");
        encoder.append(s.getLongueurReal());
        encoder.append("]");
        encoder.append("[capaciteMax:");
        encoder.append(s.getCapacite());
        encoder.append("]");
        encoder.append("[couleur:");
        encoder.append(s.getCouleur());
        encoder.append("]");
        encoder.append("[nom:");
        encoder.append(s.getNom());
        encoder.append("]");
        encoder.append("[description:");
        encoder.append(s.getDescription());
        encoder.append("]");
        encoder.append("[nbSorties:");
        encoder.append(s.getNbSorties());
        encoder.append("]");
        encoder.append("[matriceRecuperation:");
        encoder.append(matriceRecuperationToString(s.getMatrice()));
        encoder.append("]");
        encoder.append("}");
        return encoder.toString();
    }
    
    private static String matriceRecuperationToString(HashMap<Integer, HashMap<String, Double>> m){
         StringBuilder encoder = new StringBuilder();
         encoder.append("matriceRecuperation{");
         encoder.append("[matrice:");
         encoder.append("list{");
         Iterator<Integer> iterSortie = m.keySet().iterator();
         while(iterSortie.hasNext()){
             Integer s = iterSortie.next();
             encoder.append("sortieMatrice{");
             encoder.append("[id:");
             encoder.append(s);
             encoder.append("]");
             encoder.append("[recuperation:");
             encoder.append("list{");
             Iterator<String> iterFlux = m.get(s).keySet().iterator();
             while(iterFlux.hasNext()){
                 String f = iterFlux.next();
                 Double repartition = m.get(s).get(f);
                 encoder.append("distributionSortieMatrice{[typeProduit:");
                 encoder.append(f);
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
    
    private static String convoyeurToString(ConvoyeurUI c){
        StringBuilder encoder = new StringBuilder();
        encoder.append("convoyeur{");
        encoder.append("[id:");
        encoder.append(c.getMyId());
        encoder.append("]");
        encoder.append("[idEquipementSource:");
        encoder.append(c.getSortie().getEquipement().getMyId());
        encoder.append("]");
        
        encoder.append("[idSortieEquipement:");
        encoder.append(c.getSortie().getMyId());
        encoder.append("]");
        
        encoder.append("[idEquipementDestination:");
        encoder.append(c.getEntree().getEquipement().getMyId());
        encoder.append("]");
        encoder.append("[idEntreeEquipement:");
        encoder.append(c.getEntree().getMyId());
        encoder.append("]");
        
        encoder.append("[couleur:");
        encoder.append(c.getCouleur());
        encoder.append("]");
        
        encoder.append("[debitMax:");
        encoder.append(c.getCapacite());
        encoder.append("]");
        encoder.append("[points:");
        encoder.append("list{");
            for(int i = 0 ; i<c.getListPoint().size();i++){
                encoder.append("pointConvoyeur{");
                encoder.append("[x:");
                encoder.append(c.getListPoint().get(i).getPosXReal());
                encoder.append("]");
                encoder.append("[y:");
                encoder.append(c.getListPoint().get(i).getPosYReal());
                encoder.append("]");
                encoder.append("}");
            }
        encoder.append("}");
        encoder.append("]");
        encoder.append("}");
        
        return encoder.toString();
    }
    
    private static String entreeUsineToString(EntreeUsineUI e){
        StringBuilder encoder = new StringBuilder();
        encoder.append("entreeUsine{");
        
        encoder.append(equipementToString(e));
        encoder.append("[largeur:");
        encoder.append(e.getHauteurReal());
        encoder.append("]");
        encoder.append("[longueur:");
        encoder.append(e.getLongueurReal());
        encoder.append("]");
        encoder.append("[capaciteMax:");
        encoder.append(e.getCapacite());
        encoder.append("]");
        encoder.append("[couleur:");
        encoder.append(e.getCouleur());
        encoder.append("]");
        encoder.append("[nom:");
        encoder.append(e.getNom());
        encoder.append("]");
        encoder.append("[description:");
        encoder.append(e.getDescription());
        encoder.append("]");
        encoder.append("[fluxEntrants:list{");
        Iterator<Entry<String,Double>> iterFlux = e.getFluxEntrants().entrySet().iterator();
        while(iterFlux.hasNext()){
            Entry<String,Double> entry = iterFlux.next();
            encoder.append(fluxMatiereToString(entry.getKey(), entry.getValue()));
        }
        encoder.append("}");
        encoder.append("]");
        encoder.append("}");
        
        return encoder.toString();
    }
    
    private static String fluxMatiereToString(String nom, Double quantite){
        return "fluxMatiere{[typeProduit:typeProduit{[nom:"+nom+"]}][debitKilogrammeParHeure:"+quantite.toString()+"]}";
    }
    
    private static String sortieUsineToString(SortieUsineUI e){
        StringBuilder encoder = new StringBuilder();
        encoder.append("sortieUsine{");
        
        encoder.append(equipementToString(e));
        encoder.append("[largeur:");
        encoder.append(e.getHauteurReal());
        encoder.append("]");
        encoder.append("[longueur:");
        encoder.append(e.getLongueurReal());
        encoder.append("]");
        encoder.append("[capaciteMax:");
        encoder.append(e.getCapacite());
        encoder.append("]");
        encoder.append("[nom:");
        encoder.append(e.getNom());
        encoder.append("]");
        encoder.append("[couleur:");
        encoder.append(e.getCouleur());
        encoder.append("]");
        encoder.append("[description:");
        encoder.append(e.getDescription());
        encoder.append("]");
        encoder.append("}");
        
        return encoder.toString();
    }
    
    private static String jonctionToString(JonctionUI j){
        StringBuilder encoder = new StringBuilder();
        encoder.append("jonction{");
        
        encoder.append(equipementToString(j));
        encoder.append("[largeur:");
        encoder.append(j.getHauteurReal());
        encoder.append("]");
        encoder.append("[longueur:");
        encoder.append(j.getLongueurReal());
        encoder.append("]");
        encoder.append("[capaciteMax:");
        encoder.append(j.getCapacite());
        encoder.append("]");
        encoder.append("[couleur:");
        encoder.append(j.getCouleur());
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

