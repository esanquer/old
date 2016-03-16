
package GarbageCollector.presentation.util;

import GarbageCollector.presentation.graph.convoyeur.ConvoyeurUI;
import GarbageCollector.presentation.graph.convoyeur.PointConvoyeurUI;
import GarbageCollector.presentation.graph.convoyeur.PointUI;
import GarbageCollector.presentation.graph.equipement.EntreeEquipementUI;
import GarbageCollector.presentation.graph.equipement.EntreeUsineUI;
import GarbageCollector.presentation.graph.equipement.EquipementUI;
import GarbageCollector.presentation.graph.equipement.JonctionUI;
import GarbageCollector.presentation.graph.equipement.SortieEquipementUI;
import GarbageCollector.presentation.graph.equipement.SortieUsineUI;
import GarbageCollector.presentation.graph.equipement.StationUI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;


public class DestringifyUI {
    
public static HashMap<String, Object> parsePropertie(String prop){
       
       boolean nameParsed = false;
       int i = 0;
       String name = "";

       while(!nameParsed && i<prop.length()){
           
           char read = prop.charAt(i);
           if(read!=':'){
                    name = name + read;
                    
           }

           else{
               nameParsed = true;
           }
           i++;
       }
       String valToParse = prop.subSequence(i, prop.length()).toString();
       Object value = parseVal(valToParse);
       HashMap<String,Object> propertie = new HashMap<>();
       propertie.put(name, value);
       return propertie;
   }
   
   public static Object parseVal(String val){
        Object value = new Object();
        boolean continueParsing = true;
        String valBuffer="";
        int i =0;
        
        while(continueParsing && i<val.length()){
            char read = val.charAt(i);

            switch(read){
                default:
                        valBuffer+=read;
                    break;
                case '{':
                    String sequence = getCompleteExpression(val.substring(i),'{','}');
                    value = buildObject(valBuffer, sequence);
                    continueParsing=false;
                    break;
            } 
            i++;
        }
        if(continueParsing){
            value = valBuffer;
        }
        return value;
   }
   
   
   private static Object buildObject(String className, String sequence){
       Object built = new Object();
        switch (className) {
        case "station":
            built=createStation(sequence);
            break;
        case "entreeEquipement":
            built=createEntreeEquipement(sequence);
            break;
        case "sortieEquipement":
            built = createSortieEquipement(sequence);
            break;
        case "matriceRecuperation":
            built = createMatriceRecuperation(sequence);
            break;
        case "list":
            built = createList(sequence.substring(1, sequence.length()-1));
            break;
        case "jonction":
            built = createJonction(sequence);
            break;
        case "entreeUsine":
            built = createEntreeUsine(sequence);
            break;
        case "sortieUsine":
            built = createSortieUsine(sequence);
            break;
        case "convoyeur":
            built = createConvoyeur(sequence);
            break;
        case "pointConvoyeur":
            built = createPointConvoyeur(sequence);
            break;
        case "fluxMatiere":
            built = createFluxMatiere(sequence);
            break;
        case "typeProduit":
            built = createTypeProduit(sequence);
            break;
        case "sortieMatrice":
            built = createSortieMatrice(sequence);
            break;
        case "distributionSortieMatrice":
            built = createDistributionSortieMatrice(sequence);
            break;
        case "graphe":
            built = createGraphe(sequence);
            break;
    }
       return built;
   }
   
   
   private static GrapheDataUI createGraphe(String sequence){
       HashMap<String, Object> attributes = getAttributes(sequence);
       
       GrapheDataUI g = new GrapheDataUI();
       if(attributes.containsKey("equipements"))
             g.setListeEquipements((List<EquipementUI>)attributes.get("equipements"));
       if(attributes.containsKey("convoyeurs"))
             g.setListeConvoyeurs((List<ConvoyeurUI>)attributes.get("convoyeurs"));
       if(attributes.containsKey("largeur"))
             g.setLargeur(Double.parseDouble((String)attributes.get("largeur")));
       if(attributes.containsKey("longueur"))
             g.setLongueur(Double.parseDouble((String)attributes.get("longueur")));
       
       return g;
   }
   
   private static StationUI createStation(String sequence){
       HashMap<String, Object> attributes = getAttributes(sequence);
       StationUI s = new StationUI();
       
        if(attributes.containsKey("id"))
             s.setMyId(Integer.parseInt((String)attributes.get("id")));
        if(attributes.containsKey("x"))
             s.setPosXReal(Double.parseDouble((String)attributes.get("x")));
        if(attributes.containsKey("y"))
             s.setPosYReal(Double.parseDouble((String)attributes.get("y")));
        if(attributes.containsKey("couleur"))
            s.setCouleur((String)attributes.get("couleur"));
        if(attributes.containsKey("entreeEquipement")){
             s.setEntreeEquipement((List<EntreeEquipementUI>)attributes.get("entreeEquipement"));
        }
        if(attributes.containsKey("sortieEquipement"))
             s.setSortieEquipement((List<SortieEquipementUI>)attributes.get("sortieEquipement"));
        if(attributes.containsKey("nbSorties"))
             s.setNbSorties(Integer.parseInt((String)attributes.get("nbSorties")));
        
        s.attacherEntreesEtSorties();
        
        if(attributes.containsKey("largeur"))
             s.setHauteurReal(Double.parseDouble((String)attributes.get("largeur")));
        if(attributes.containsKey("longueur"))
             s.setLongueurReal(Double.parseDouble((String)attributes.get("longueur")));
        if(attributes.containsKey("capaciteMax"))
             s.setCapacite(Double.parseDouble((String)attributes.get("capaciteMax")));
        if(attributes.containsKey("nom"))
             s.setNom((String)attributes.get("nom"));
        if(attributes.containsKey("description"))
             s.setDescription((String)attributes.get("description"));
        if(attributes.containsKey("matriceRecuperation"))
             s.setMatrice((HashMap<Integer,HashMap<String,Double>>)attributes.get("matriceRecuperation"));
       return s;
   }
   
   private static EntreeUsineUI createEntreeUsine(String sequence){
       HashMap<String, Object> attributes = getAttributes(sequence);
       EntreeUsineUI e= new EntreeUsineUI();
       if(attributes.containsKey("id"))
            e.setMyId(Integer.parseInt((String)attributes.get("id")));
       if(attributes.containsKey("x"))
            e.setPosXReal(Double.parseDouble((String)attributes.get("x")));
       if(attributes.containsKey("y"))
            e.setPosYReal(Double.parseDouble((String)attributes.get("y")));
       if(attributes.containsKey("couleur"))
            e.setCouleur((String)attributes.get("couleur"));
        if(attributes.containsKey("fluxEntrants")){
            List<HashMap<String,Double>> listeFlux = (ArrayList<HashMap<String,Double>>) attributes.get("fluxEntrants");
            HashMap<String,Double> flux = new HashMap<>();
           for (HashMap<String, Double> listeFlux1 : listeFlux) {
               flux.putAll(listeFlux1);
           }
            e.setFluxEntrants(flux);
       }
       
       
       if(attributes.containsKey("entreeEquipement"))
            e.setEntreeEquipement((List<EntreeEquipementUI>)attributes.get("entreeEquipement"));
       if(attributes.containsKey("sortieEquipement"))
            e.setSortieEquipement((List<SortieEquipementUI>)attributes.get("sortieEquipement"));
       
       e.attacherEntreesEtSorties();
       
       if(attributes.containsKey("largeur"))
            e.setHauteurReal(Double.parseDouble((String)attributes.get("largeur")));
       if(attributes.containsKey("longueur"))
            e.setLongueurReal(Double.parseDouble((String)attributes.get("longueur")));
       if(attributes.containsKey("capaciteMax"))
            e.setCapacite(Double.parseDouble((String)attributes.get("capaciteMax")));
       if(attributes.containsKey("nom"))
            e.setNom((String)attributes.get("nom"));
       if(attributes.containsKey("description"))
            e.setDescription((String)attributes.get("description"));
       
       return e;
   }
   
   private static SortieUsineUI createSortieUsine(String sequence){
       HashMap<String, Object> attributes = getAttributes(sequence);
       SortieUsineUI s= new SortieUsineUI();
       if(attributes.containsKey("id"))
            s.setMyId(Integer.parseInt((String)attributes.get("id")));
       if(attributes.containsKey("x"))
            s.setPosXReal(Double.parseDouble((String)attributes.get("x")));
       if(attributes.containsKey("y"))
            s.setPosYReal(Double.parseDouble((String)attributes.get("y")));
       if(attributes.containsKey("couleur"))
            s.setCouleur((String)attributes.get("couleur"));
       if(attributes.containsKey("entreeEquipement"))
            s.setEntreeEquipement((List<EntreeEquipementUI>)attributes.get("entreeEquipement"));
       if(attributes.containsKey("sortieEquipement"))
            s.setSortieEquipement((List<SortieEquipementUI>)attributes.get("sortieEquipement"));
       
       s.attacherEntreesEtSorties();
       
       if(attributes.containsKey("largeur"))
            s.setHauteurReal(Double.parseDouble((String)attributes.get("largeur")));
       if(attributes.containsKey("longueur"))
            s.setLongueurReal(Double.parseDouble((String)attributes.get("longueur")));
       if(attributes.containsKey("capaciteMax"))
            s.setCapacite(Double.parseDouble((String)attributes.get("capaciteMax")));
       if(attributes.containsKey("nom"))
            s.setNom((String)attributes.get("nom"));
       if(attributes.containsKey("description"))
            s.setDescription((String)attributes.get("description"));
       return s;
   }
   
   private static JonctionUI createJonction(String sequence){
        HashMap<String, Object> attributes = getAttributes(sequence);
        JonctionUI j = new JonctionUI();
        if(attributes.containsKey("id"))
             j.setMyId(Integer.parseInt((String)attributes.get("id")));
        if(attributes.containsKey("x"))
             j.setPosXReal(Double.parseDouble((String)attributes.get("x")));
        if(attributes.containsKey("y"))
             j.setPosYReal(Double.parseDouble((String)attributes.get("y")));
        if(attributes.containsKey("entreeEquipement"))
             j.setEntreeEquipement((List<EntreeEquipementUI>)attributes.get("entreeEquipement"));
        if(attributes.containsKey("sortieEquipement"))
             j.setSortieEquipement((List<SortieEquipementUI>)attributes.get("sortieEquipement"));
        if(attributes.containsKey("nbEntree"))
            j.setNbEntree(Integer.parseInt((String)attributes.get("nbEntree")));
        j.attacherEntreesEtSorties();
        if(attributes.containsKey("largeur"))
             j.setHauteurReal(Double.parseDouble((String)attributes.get("largeur")));
        if(attributes.containsKey("longueur"))
             j.setLongueurReal(Double.parseDouble((String)attributes.get("longueur")));
        if(attributes.containsKey("capaciteMax"))
             j.setCapacite(Double.parseDouble((String)attributes.get("capaciteMax")));
        if(attributes.containsKey("nom"))
             j.setNom((String)attributes.get("nom"));
        if(attributes.containsKey("description"))
             j.setDescription((String)attributes.get("description"));
        if(attributes.containsKey("couleur"))
            j.setCouleur((String)attributes.get("couleur"));
       return j;
   }
      
   private static PointUI createPointConvoyeur(String sequence){
       HashMap<String, Object> attributes = getAttributes(sequence);
       
       double posXReal = Double.parseDouble((String)attributes.get("x"));
       double posYReal = Double.parseDouble((String)attributes.get("y"));
       
       return new PointConvoyeurUI(posXReal, posYReal, 0, 0, 0, null);
   }
   private static ConvoyeurUI createConvoyeur(String sequence){
       HashMap<String, Object> attributes = getAttributes(sequence);
       
       
       EntreeEquipementUI e = new EntreeEquipementUI();
       e.setMyId(Integer.parseInt((String)attributes.get("idEntreeEquipement")));
       StationUI destination = new StationUI();
       destination.setMyId(Integer.parseInt((String)attributes.get("idEquipementDestination")));
       e.setEquipement(destination);
       
       SortieEquipementUI s = new SortieEquipementUI();
       s.setMyId(Integer.parseInt((String)attributes.get("idSortieEquipement")));
       StationUI source = new StationUI();
       source.setMyId(Integer.parseInt((String)attributes.get("idEquipementSource")));
       s.setEquipement(source);
       
       
       ConvoyeurUI c = new ConvoyeurUI(e,s);
       c.setListPoint((ArrayList<PointUI>)attributes.get("points"));
       c.setMyId(Integer.parseInt((String)attributes.get("id")));
       if(attributes.containsKey("couleur"))
            c.setCouleur((String)attributes.get("idSortieEquipement"));
       
       if(attributes.containsKey("debitMax"))
            c.setCapacite(Double.parseDouble((String)attributes.get("debitMax")));
       
       
       
       return c ;
       
   }
   
   private static EntreeEquipementUI createEntreeEquipement(String sequence){
       HashMap<String, Object> attributes = getAttributes(sequence);
       EntreeEquipementUI e = new EntreeEquipementUI();
       e.setMyId(Integer.parseInt((String)attributes.get("id")));
       
        if(attributes.containsKey("fluxMatiere")){
            List<HashMap<String,Double>> listeFlux = (ArrayList<HashMap<String,Double>>) attributes.get("fluxMatiere");
            HashMap<String,Double> flux = new HashMap<>();
           for (HashMap<String, Double> listeFlux1 : listeFlux) {
               flux.putAll(listeFlux1);
           }
            e.setFluxMatiere(flux);
       }
        if(attributes.containsKey("equipementId")){
            StationUI st = new StationUI();
            st.setMyId(Integer.parseInt((String)attributes.get("equipementId")));
            e.setEquipement(st);
        }
        if(attributes.containsKey("convoyeur")){
            e.setConvoyeur((ConvoyeurUI)attributes.get("convoyeur"));
        }

       return e;
   }
   
   private static SortieEquipementUI createSortieEquipement(String sequence){
       HashMap<String, Object> attributes = getAttributes(sequence);
       SortieEquipementUI s = new SortieEquipementUI();
       s.setMyId(Integer.parseInt((String)attributes.get("id")));
       if(attributes.containsKey("fluxMatiere")){
            List<HashMap<String,Double>> listeFlux = (ArrayList<HashMap<String,Double>>) attributes.get("fluxMatiere");
            HashMap<String,Double> flux = new HashMap<>();
           for (HashMap<String, Double> listeFlux1 : listeFlux) {
               flux.putAll(listeFlux1);
           }
            s.setFluxMatiere(flux);
       }
       if(attributes.containsKey("equipementId")){
            StationUI st = new StationUI();
            st.setMyId(Integer.parseInt((String)attributes.get("equipementId")));
            s.setEquipement(st);
        }
        if(attributes.containsKey("convoyeur")){
            s.setConvoyeur((ConvoyeurUI)attributes.get("convoyeur"));
        }
       return s;
   }
   
   private static HashMap<Integer, HashMap<String, Double>> createMatriceRecuperation(String sequence){
        HashMap<String, Object> attributes = getAttributes(sequence);
        

        HashMap<Integer, HashMap<String, Double>> matrice = new HashMap<>();
        ArrayList<HashMap<Integer, HashMap<String, Double>>> listeSortiesMatrice = (ArrayList<HashMap<Integer, HashMap<String, Double>>> )attributes.get("matrice");
        
        
        for (HashMap<Integer, HashMap<String, Double>> listeSortiesMatrice1 : listeSortiesMatrice) {
            Integer id = (Integer) listeSortiesMatrice1.keySet().toArray()[0];
            matrice.put(id, (HashMap<String, Double>) listeSortiesMatrice1.get(id));
        }

        return matrice;
   }
   
   private static HashMap<Integer, HashMap<String, Double>> createSortieMatrice(String sequence){
        HashMap<String, Object> attributes = getAttributes(sequence);

        HashMap<Integer, HashMap<String, Double>> sortieMatrice = new HashMap<>();
       
        List<HashMap<String, Double>> recuperation = (List<HashMap<String, Double>>)attributes.get("recuperation");
        HashMap<String, Double> recuperationMap = new HashMap<>();
        for (HashMap<String, Double> recuperation1 : recuperation) {
            recuperationMap.putAll(recuperation1);
        }
        sortieMatrice.put(Integer.parseInt((String)attributes.get("id")), recuperationMap);
        return sortieMatrice;
   }
   
   
   private static HashMap<String, Double> createDistributionSortieMatrice(String sequence){
       HashMap<String, Object> attributes = getAttributes(sequence);
       
       HashMap<String, Double> distributionSortieMatrice = new HashMap<>();
       String f = (String)attributes.get("typeProduit");
       distributionSortieMatrice.put(f, Double.parseDouble((String)attributes.get("percent")));
       return distributionSortieMatrice;
   }
   
   
   private static String createTypeProduit(String sequence){
       HashMap<String, Object> attributes = getAttributes(sequence);
       String nom = (String)attributes.get("nom");
       return nom;
   }
   
   private static HashMap<String, Double> createFluxMatiere(String sequence){
        HashMap<String, Object> attributes = getAttributes(sequence);
        String produit = (String)attributes.get("typeProduit");
        Double quantite = Double.parseDouble((String)attributes.get("debitKilogrammeParHeure"));
        
        HashMap<String, Double> fluxMatiere = new HashMap<>();
        
        fluxMatiere.put(produit, quantite);
        
        return fluxMatiere;
   }

   
   private static List<Object> createList(String sequence){
        List<Object> inList = new ArrayList<>();
        String classname="";
        int i =0;
        
        while(i<sequence.length()){
            char read = sequence.charAt(i);

            switch(read){
                default:
                        classname+=read;
                        i++;
                    break;
                case '{':
                    String classSequence = getCompleteExpression(sequence.substring(i),'{','}');
                    inList.add(buildObject(classname, classSequence));
                    i+=classSequence.length();
                    classname="";
                    break;
            } 
            
        }
       return inList;
       
   }
   
   
    private static HashMap<String, Object> getAttributes (String sequence){
        List<String> properties = new ArrayList<>();
        HashMap<String,Object> props = new HashMap<>();
        for(int i = 0 ; i < sequence.length();i++){
            char read = sequence.charAt(i);
            if(read=='['){
                String propertie = getCompleteExpression(sequence.substring(i), '[', ']');
                properties.add(propertie.substring(1, propertie.length()-1));
                i = i+propertie.length()-1;
            }
        }
        for (String propertie : properties) {
            props.putAll(parsePropertie(propertie));
        }
        return props;
    }
   
   
   
    private static String getCompleteExpression(String str, char openBlock, char endBlock){
        Stack<Integer> openedBlock = new Stack();
        int i = 0;
        boolean continueParsing = true;
        while(continueParsing && i<str.length()){
            char read = str.charAt(i);
            if(read == openBlock){
                openedBlock.push(i);
            }
            else if(read==endBlock){
                openedBlock.pop();
                if(openedBlock.size()==0){
                    continueParsing=false;
                }
            }
            i++;

        }
        return str.substring(0, i);
       
    }
    
}
