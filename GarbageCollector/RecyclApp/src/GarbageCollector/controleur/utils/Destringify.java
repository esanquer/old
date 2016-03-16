
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;


public class Destringify {
   
   
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
           case "grahe":
               built = createGraphe(sequence);
               break;
       }
       return built;
   }
   
   
   private static Graphe createGraphe(String sequence){
       HashMap<String, Object> attributes = getAttributes(sequence);
       Graphe g = new Graphe();
       if(attributes.containsKey("largeur"))
            g.setLargeur(Double.parseDouble((String)attributes.get("largeur")));
       if(attributes.containsKey("longueur"))
            g.setLongueur(Double.parseDouble((String)attributes.get("longueur")));
       if(attributes.containsKey("equipements"))
            g.setEquipements((List<Equipement>)attributes.get("equipements"));
       if(attributes.containsKey("convoyeurs"))
            g.setConvoyeurs((List<Convoyeur>)attributes.get("convoyeurs"));
       
       return g;
   }
   
   private static Station createStation(String sequence){
       HashMap<String, Object> attributes = getAttributes(sequence);
       Station s = new Station();
       if(attributes.containsKey("id"))
            s.setId(Integer.parseInt((String)attributes.get("id")));
       if(attributes.containsKey("x"))
            s.setPositionX(Double.parseDouble((String)attributes.get("x")));
       if(attributes.containsKey("y"))
            s.setPositionY(Double.parseDouble((String)attributes.get("y")));
       /*if(attributes.containsKey("entreeEquipement"))
            s.setEntreeEquipement((List<EntreeEquipement>)attributes.get("entreeEquipement"));
       if(attributes.containsKey("sortieEquipement"))
            s.setSortieEquipement((List<SortieEquipement>)attributes.get("sortieEquipement"));*/
       if(attributes.containsKey("largeur"))
            s.setLargeur(Double.parseDouble((String)attributes.get("largeur")));
       if(attributes.containsKey("longueur"))
            s.setLongueur(Double.parseDouble((String)attributes.get("longueur")));
       if(attributes.containsKey("capaciteMax"))
            s.setCapaciteMax(Double.parseDouble((String)attributes.get("capaciteMax")));
       if(attributes.containsKey("nom"))
            s.setNom((String)attributes.get("nom"));
       if(attributes.containsKey("couleur"))
            s.setCouleur((String)attributes.get("couleur"));
       if(attributes.containsKey("description"))
            s.setDescription((String)attributes.get("description"));
       if(attributes.containsKey("matriceRecuperation"))
           s.setMatriceRecuperation((MatriceRecuperation)attributes.get("matriceRecuperation"));
       if(attributes.containsKey("nbSorties"))
           s.setNbSortie(Integer.parseInt((String)attributes.get("nbSorties")));
       s.attacherEntreesEtSorties();
       return s;
   }
   
   private static EntreeUsine createEntreeUsine(String sequence){
       HashMap<String, Object> attributes = getAttributes(sequence);
       EntreeUsine e= new EntreeUsine();
       if(attributes.containsKey("id"))
            e.setId(Integer.parseInt((String)attributes.get("id")));
       if(attributes.containsKey("x"))
            e.setPositionX(Double.parseDouble((String)attributes.get("x")));
       if(attributes.containsKey("y"))
            e.setPositionY(Double.parseDouble((String)attributes.get("y")));
       /*if(attributes.containsKey("entreeEquipement"))
            e.setEntreeEquipement((List<EntreeEquipement>)attributes.get("entreeEquipement"));
       if(attributes.containsKey("sortieEquipement"))
            e.setSortieEquipement((List<SortieEquipement>)attributes.get("sortieEquipement"));*/
       if(attributes.containsKey("largeur"))
            e.setLargeur(Double.parseDouble((String)attributes.get("largeur")));
       if(attributes.containsKey("longueur"))
            e.setLongueur(Double.parseDouble((String)attributes.get("longueur")));
       if(attributes.containsKey("capaciteMax"))
            e.setCapaciteMax(Double.parseDouble((String)attributes.get("capaciteMax")));
       if(attributes.containsKey("nom"))
            e.setNom((String)attributes.get("nom"));
       if(attributes.containsKey("description"))
            e.setDescription((String)attributes.get("description"));
      if(attributes.containsKey("fluxEntrants"))
            e.setFluxEntrants((List<FluxMatiere>)attributes.get("fluxEntrants"));
       if(attributes.containsKey("couleur"))
            e.setCouleur((String)attributes.get("couleur"));

       e.attacherEntreesEtSorties();
       return e;
   }
   
   private static SortieUsine createSortieUsine(String sequence){
       HashMap<String, Object> attributes = getAttributes(sequence);
       SortieUsine s= new SortieUsine();
       if(attributes.containsKey("id"))
            s.setId(Integer.parseInt((String)attributes.get("id")));
       if(attributes.containsKey("x"))
            s.setPositionX(Double.parseDouble((String)attributes.get("x")));
       if(attributes.containsKey("couleur"))
            s.setCouleur((String)attributes.get("couleur"));
       if(attributes.containsKey("y"))
            s.setPositionY(Double.parseDouble((String)attributes.get("y")));
       /*if(attributes.containsKey("entreeEquipement"))
            s.setEntreeEquipement((List<EntreeEquipement>)attributes.get("entreeEquipement"));
       if(attributes.containsKey("sortieEquipement"))
            s.setSortieEquipement((List<SortieEquipement>)attributes.get("sortieEquipement"));*/
       if(attributes.containsKey("largeur"))
            s.setLargeur(Double.parseDouble((String)attributes.get("largeur")));
       if(attributes.containsKey("longueur"))
            s.setLongueur(Double.parseDouble((String)attributes.get("longueur")));
       if(attributes.containsKey("capaciteMax"))
            s.setCapaciteMax(Double.parseDouble((String)attributes.get("capaciteMax")));
       if(attributes.containsKey("nom"))
            s.setNom((String)attributes.get("nom"));
       if(attributes.containsKey("description"))
            s.setDescription((String)attributes.get("description"));
       s.attacherEntreesEtSorties();
       return s;
   }
   
   private static Jonction createJonction(String sequence){
        HashMap<String, Object> attributes = getAttributes(sequence);
       Jonction j = new Jonction();
       if(attributes.containsKey("id"))
            j.setId(Integer.parseInt((String)attributes.get("id")));
       if(attributes.containsKey("x"))
            j.setPositionX(Double.parseDouble((String)attributes.get("x")));
       if(attributes.containsKey("couleur"))
            j.setCouleur((String)attributes.get("couleur"));
       if(attributes.containsKey("y"))
            j.setPositionY(Double.parseDouble((String)attributes.get("y")));
      /* if(attributes.containsKey("entreeEquipement"))
            j.setEntreeEquipement((List<EntreeEquipement>)attributes.get("entreeEquipement"));
       if(attributes.containsKey("sortieEquipement"))
            j.setSortieEquipement((List<SortieEquipement>)attributes.get("sortieEquipement"));*/
       if(attributes.containsKey("largeur"))
            j.setLargeur(Double.parseDouble((String)attributes.get("largeur")));
       if(attributes.containsKey("longueur"))
            j.setLongueur(Double.parseDouble((String)attributes.get("longueur")));
       if(attributes.containsKey("capaciteMax"))
            j.setCapaciteMax(Double.parseDouble((String)attributes.get("capaciteMax")));
       if(attributes.containsKey("nom"))
            j.setNom((String)attributes.get("nom"));
       if(attributes.containsKey("description"))
            j.setDescription((String)attributes.get("description"));
       if(attributes.containsKey("nbEntree"))
            j.setNbEntree(Integer.parseInt((String)attributes.get("nbEntree")));
       j.attacherEntreesEtSorties();
       return j;
   }
   
   private static EntreeEquipement createEntreeEquipement(String sequence){
       HashMap<String, Object> attributes = getAttributes(sequence);
       EntreeEquipement e = new EntreeEquipement();
       e.setId(Integer.parseInt((String)attributes.get("id")));
       e.setFluxMatiere((List<FluxMatiere>)attributes.get("fluxMatiere"));
       Equipement eq = new Station();
       eq.setId(Integer.parseInt((String)attributes.get("equipementId")));
       e.setEquipement(eq);
              
       if(attributes.containsKey("convoyeur")){
           e.setConvoyeur((Convoyeur)attributes.get("convoyeur"));
       }
       return e;
   }
   
   private static SortieEquipement createSortieEquipement(String sequence){
       HashMap<String, Object> attributes = getAttributes(sequence);
       SortieEquipement s = new SortieEquipement();
       s.setId(Integer.parseInt((String)attributes.get("id")));
       s.setFluxMatiere((List<FluxMatiere>)attributes.get("fluxMatiere"));
       Equipement e = new Station();
       e.setId(Integer.parseInt((String)attributes.get("equipementId")));
       s.setEquipement(e);
       
       if(attributes.containsKey("convoyeur")){
           s.setConvoyeur((Convoyeur)attributes.get("convoyeur"));
       }
       return s;
   }
   
   private static MatriceRecuperation createMatriceRecuperation(String sequence){
        HashMap<String, Object> attributes = getAttributes(sequence);
        
        MatriceRecuperation m = new MatriceRecuperation();
        HashMap<SortieEquipement, HashMap<FluxMatiere, Double>> matrice = new HashMap<>();
        ArrayList<HashMap<Integer, HashMap<FluxMatiere, Double>>> listeSortiesMatrice = (ArrayList<HashMap<Integer, HashMap<FluxMatiere, Double>>> )attributes.get("matrice");
        
        
       for (HashMap<Integer, HashMap<FluxMatiere, Double>> listeSortiesMatrice1 : listeSortiesMatrice) {
           SortieEquipement s = new SortieEquipement();
           Integer id = (Integer) listeSortiesMatrice1.keySet().toArray()[0];
           s.setId(id);
           matrice.put(s, (HashMap<FluxMatiere, Double>) listeSortiesMatrice1.get(id));
       }
        
        m.setMatrice(matrice);
        return m;
   }
   
   private static HashMap<Integer, HashMap<FluxMatiere, Double>> createSortieMatrice(String sequence){
       HashMap<String, Object> attributes = getAttributes(sequence);
       
       HashMap<Integer, HashMap<FluxMatiere, Double>> sortieMatrice = new HashMap<>();
       
       List<HashMap<FluxMatiere, Double>> recuperation = (List<HashMap<FluxMatiere, Double>>)attributes.get("recuperation");
       HashMap<FluxMatiere, Double> recuperationMap = new HashMap<>();
       for (HashMap<FluxMatiere, Double> recuperation1 : recuperation) {
           recuperationMap.putAll(recuperation1);
       }
       sortieMatrice.put(Integer.parseInt((String)attributes.get("id")), recuperationMap);
       return sortieMatrice;
   }
   
   
   private static HashMap<FluxMatiere, Double> createDistributionSortieMatrice(String sequence){
       HashMap<String, Object> attributes = getAttributes(sequence);
       
       HashMap<FluxMatiere, Double> distributionSortieMatrice = new HashMap<>();
       FluxMatiere f = new FluxMatiere(0.0,new TypeProduit((String)attributes.get("typeProduit")));
       distributionSortieMatrice.put(f, Double.parseDouble((String)attributes.get("percent")));
       return distributionSortieMatrice;
   }
   private static FluxMatiere createFluxMatiere(String sequence){
       HashMap<String, Object> attributes = getAttributes(sequence);
       
       return new FluxMatiere(Double.parseDouble((String)attributes.get("debitKilogrammeParHeure")),(TypeProduit)attributes.get("typeProduit"));
   }
   
   private static TypeProduit createTypeProduit(String sequence){
       HashMap<String, Object> attributes = getAttributes(sequence);
       TypeProduit t = new TypeProduit((String)attributes.get("nom"));
       return t;
   }
   
   private static String createPointConvoyeur(String sequence){
       HashMap<String, Object> attributes = getAttributes(sequence);
       String point = "";
       point+="[x:"+attributes.get("x")+"]";
       point+="[y:"+attributes.get("y")+"]";
       return point;
   }
   
   private static Convoyeur createConvoyeur(String sequence){
       HashMap<String, Object> attributes = getAttributes(sequence);
       Convoyeur c = new Convoyeur();
       if(attributes.containsKey("id"))
          c.setId(Integer.parseInt((String)attributes.get("id")));
       
       Station s = new Station();
       if(attributes.containsKey("idEquipementDestination"))
            s.setId(Integer.parseInt((String)attributes.get("idEquipementDestination")));
       EntreeEquipement entree = new EntreeEquipement(s);
       
       if(attributes.containsKey("idEntreeEquipement"))
            entree.setId(Integer.parseInt((String)attributes.get("idEntreeEquipement")));
       entree.setEquipement(s);
       c.setEntreeEquipement(entree);
       
       Station e = new Station();
       if(attributes.containsKey("idEquipementSource"))
            e.setId(Integer.parseInt((String)attributes.get("idEquipementSource")));
       SortieEquipement sortie = new SortieEquipement(e);
       if(attributes.containsKey("idSortieEquipement"))
            sortie.setId(Integer.parseInt((String)attributes.get("idSortieEquipement")));
       sortie.setEquipement(e);
       c.setSortieEquipement(sortie);
       
       if(attributes.containsKey("couleur"))
            c.setCouleur((String)attributes.get("idSortieEquipement"));
       if(attributes.containsKey("debitMax"))
            c.setDebitMax(Double.parseDouble((String)attributes.get("debitMax")));
       if(attributes.containsKey("points"))
            c.setPoints((List<String>)attributes.get("points"));
       
       return c ;
       
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
