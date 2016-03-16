package GarbageCollector.controleur;

import GarbageCollector.controleur.utils.Destringify;
import GarbageCollector.controleur.utils.Stringify;
import GarbageCollector.domaine.Graphe;
import GarbageCollector.domaine.Equipement;
import GarbageCollector.domaine.EntreeEquipement;
import GarbageCollector.domaine.EntreeUsine;
import GarbageCollector.domaine.Jonction;
import GarbageCollector.domaine.SortieEquipement;
import GarbageCollector.domaine.SortieUsine;
import GarbageCollector.domaine.Station;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Stack;

public class Controleur {
        
        /*##############################
                 ATRIBUT
         ##############################*/
    
    private static final String nomGraphePrecedent = "precedent";
    private static final String nomGrapheSuivant = "suivant";
    private Stack<String> pileGraphesPrecedants = new Stack();
    private Stack<String> pileGraphesSuivants = new Stack();
    
    public  Graphe graphe;
    
        /*##############################
                 CONSTRUCTEUR
         ##############################*/
    
    public Controleur() {
        graphe = new Graphe();

    }
    
        /*##############################
                  AJOUT
        ##############################*/

    public String ajouterEntreeEquipement(String req) {
        Object o = Destringify.parseVal(req);
        enregistrerPrecedant();
        String responde = GestionnaireEquipement.ajouterEntreeEquipement(o, graphe);
        lancerCalcul();
        return responde;
    }
    
    public String ajouterSortieEquipement(String req) {
        Object o = Destringify.parseVal(req);
        enregistrerPrecedant();
        String responde =  GestionnaireEquipement.ajouterSortieEquipement(o, graphe);
        lancerCalcul();
        return responde;
    }
    
    public String ajouterConvoyeur(String req){
        Object o = Destringify.parseVal(req);
        enregistrerPrecedant();
        String responde =  GestionnaireConvoyeur.ajouterConvoyeur(o, graphe);
        lancerCalcul();
        return responde;
    }
    
    public String ajouterEquipement(String req){
        Object o = Destringify.parseVal(req);
        enregistrerPrecedant();
        String responde =  GestionnaireEquipement.ajouterEquipement(o, graphe);
        lancerCalcul();
        return responde;
    }  
    
        /*##############################
                SUPRESSION
        ##############################*/
    
    public void supprimerEquipement(String req){
        Object o = Destringify.parseVal(req);
        enregistrerPrecedant();
        GestionnaireEquipement.supprimerEquipement(o, graphe);
        lancerCalcul();
    }
    
    public void supprimerEntreeEquipement(String req) {
        Object o = Destringify.parseVal(req);
        enregistrerPrecedant();
        GestionnaireEquipement.supprimerEntreeEquipement(o, graphe);
        lancerCalcul();
    }
    
    public void supprimerSortieEquipement(String req) {
        Object o = Destringify.parseVal(req);
        enregistrerPrecedant();
        GestionnaireEquipement.supprimerSortieEquipement(o, graphe);
        lancerCalcul();
    }
    
    public void supprimerConvoyeur(String req){
        Object o = Destringify.parseVal(req);
        enregistrerPrecedant();
        GestionnaireConvoyeur.supprimerConvoyeur(o, graphe);
        lancerCalcul();
    }
    
    
        /*##############################
                MODIFICATION
        ##############################*/
        
    public String setSizeEquipement(String req) {
        Object o = Destringify.parseVal(req);
        enregistrerPrecedant();
        return GestionnaireEquipement.setSizeEquipement(o,graphe);
    }
    
    public String setPositionEquipement(String req){
        Object o = Destringify.parseVal(req);
        enregistrerPrecedant();
        return GestionnaireEquipement.setPositionEquipement(o,graphe);
    }
    
    public String setInformationStation(String req) {
        Object o = Destringify.parseVal(req);
        enregistrerPrecedant();
        String result = GestionnaireEquipement.setInformationStation(o, graphe);
        lancerCalcul();
        return result;
    }
    
    public String setInformationJonction(String req) {
        Object o = Destringify.parseVal(req);
        enregistrerPrecedant();
        lancerCalcul();
        String result = GestionnaireEquipement.setInformationJonction(o, graphe);
        
        return result;
    }
     public String setInformationEntreeUsine(String req) {
        Object o = Destringify.parseVal(req);
        enregistrerPrecedant();
        String result = GestionnaireEquipement.setInformationEntreeUsine(o, graphe);
        lancerCalcul();
        return result;
    }
    public String setInformationSortieUsine(String req) {
        Object o = Destringify.parseVal(req);
        enregistrerPrecedant();

        String result = GestionnaireEquipement.setInformationSortieUsine(o, graphe);
        lancerCalcul();
        return result;
        
    }

        /*##############################
                RECHERCHE
        ##############################*/
    
    public String getInformationEntreeEquipement(String strEntreeEquipement){
        EntreeEquipement e = (EntreeEquipement)Destringify.parseVal(strEntreeEquipement);
        Equipement eq = findEquipementbyId(e.getEquipement().getId(), graphe);
        boolean found = false;
        String entreeStringified="";
        Iterator<EntreeEquipement> iterEntree= eq.getEntreeEquipement().iterator();
        while(!found && iterEntree.hasNext()){
            EntreeEquipement tmp = iterEntree.next();
            if(tmp.getId() == e.getId()){
                found = true;
                entreeStringified = Stringify.stringify(tmp);
            }
        }
        return entreeStringified;
    }
    
    public String getInformationSortieEquipement(String strSortieEquipement){
        SortieEquipement s = (SortieEquipement)Destringify.parseVal(strSortieEquipement);
        Equipement eq = findEquipementbyId(s.getEquipement().getId(), graphe);
        boolean found = false;
        String sortieStringified="";
        Iterator<SortieEquipement> iterSortie= eq.getSortieEquipement().iterator();
        while(!found && iterSortie.hasNext()){
            SortieEquipement tmp = iterSortie.next();
            if(tmp.getId() == s.getId()){
                found = true;
                sortieStringified = Stringify.stringify(tmp);
            }
        }
        return sortieStringified;
    }
    
    public Equipement findEquipementbyId(int IdEquipement, Graphe graphe){
        return GestionnaireEquipement.findEquipementById(IdEquipement, graphe);
    }

    
        /*##############################
                OPERATION
        ##############################*/
    
    public void lancerCalcul(){
        Iterator<EntreeUsine> iterEntrees = graphe.getEntreesUsine().iterator();
        while(iterEntrees.hasNext()){
            EntreeUsine e = iterEntrees.next();
            e.appliquerCalcul();
        }
    }
        
        /*##############################
                OPERATION
        ##############################*/

    public void sauvegarderGraphe(String filePath){
        GestionnairePersistance.enregistrerGraphe(graphe,filePath);
    }

    public String chargerGraphe(String filePath){
        Graphe g = GestionnairePersistance.chargerGraphe(filePath);
        this.graphe = g;
        this.pileGraphesPrecedants = new Stack<>();
        this.pileGraphesSuivants = new Stack<>();
        return Stringify.stringify(g);
    }

    public String annulerAction(){
        if(!this.pileGraphesPrecedants.empty()){
            String nomSauvegardeSuivante = nomGrapheSuivant.concat(String.valueOf(this.pileGraphesSuivants.size()));

            GestionnairePersistance.enregistrerGraphe(graphe, nomSauvegardeSuivante);
            pileGraphesSuivants.push(nomSauvegardeSuivante);
            this.graphe = GestionnairePersistance.chargerGraphe(pileGraphesPrecedants.pop());
        }
        return Stringify.stringify(this.graphe);
    }

    public String refaireAction() {
        if(!this.pileGraphesSuivants.empty()){
            String nomSauvegardePrecedente = nomGraphePrecedent.concat(String.valueOf(this.pileGraphesPrecedants.size()));

            GestionnairePersistance.enregistrerGraphe(graphe, nomSauvegardePrecedente);
            pileGraphesPrecedants.push(nomSauvegardePrecedente);
            this.graphe = GestionnairePersistance.chargerGraphe(pileGraphesSuivants.pop());
        }
        return Stringify.stringify(this.graphe);
    }
    
    
    public void enregistrerPrecedant(){
          String nomSauvegardePrecedente = nomGraphePrecedent.concat(String.valueOf(this.pileGraphesPrecedants.size()));
          GestionnairePersistance.enregistrerGraphe(graphe, nomSauvegardePrecedente);
          pileGraphesPrecedants.push(nomSauvegardePrecedente);
    }
/*    public static void chargerGraphe(GraphUI gui){
        List<Equipement> le = graphe.getEquipements();
        ArrayList<EquipementUI> leui = new ArrayList<>();
        for(int i = 0 ; i < le.size();i++){
            if(le.get(i) instanceof EntreeUsine){
                EntreeUsine entree = (EntreeUsine)le.get(i);
                EntreeUsineUI ui = new EntreeUsineUI(50,100, GestionnaireEchelle.convertirMetresEnPixels(entree.getPositionX()) , GestionnaireEchelle.convertirMetresEnPixels(entree.getPositionY()));
                leui.add(ui);
            }
            if(le.get(i) instanceof Station){
                Station s = (Station)le.get(i);
                StationUI station = new StationUI(GestionnaireEchelle.convertirMetresEnPixels(s.getLongueur()),
                                                    GestionnaireEchelle.convertirMetresEnPixels(s.getLargeur()),
                                                    GestionnaireEchelle.convertirMetresEnPixels(s.getPositionX()),
                                                    GestionnaireEchelle.convertirMetresEnPixels(s.getPositionY()),
                                                    s.getNom(),
                                                    s.getDescription(),
                                                    s.getCapaciteMax(),
                                                    s.getId());
                station.setNbSorties(s.getSortieEquipement().size());
                leui.add(station);

            } 

        }
        gui.chargerGraphe(leui,new ArrayList<ConvoyeurUI>());
    }
*/
    
        /*##############################
                ACCESSEUR
        ##############################*/
    
    public HashMap<Integer, HashMap<String, Double>> getMatriceRecuperationByIdStation(Integer id){
        return GestionnaireEquipement.getMatriceRecuperationByIdStation(id, graphe);
    }
    public HashMap<String, Double> getDescriptionEntreeByIdStation(Integer id){
        return GestionnaireEquipement.getDescriptionEntreeByIdStation(id,graphe);
    }

    
    public String getInformationStation(String stationStr){
        Station query = (Station)Destringify.parseVal(stationStr);
        Station response = (Station)findEquipementbyId(query.getId(), graphe);
        return Stringify.stringify(response);
    }
    
    public String getInformationJonction(String jonctionStr){
        Jonction query = (Jonction)Destringify.parseVal(jonctionStr);
        Jonction response = (Jonction)findEquipementbyId(query.getId(), graphe);
        return Stringify.stringify(response);
    }
    public String getInformationEntreeUsine(String entreeUsineStr){
        EntreeUsine query = (EntreeUsine)Destringify.parseVal(entreeUsineStr);
        EntreeUsine response = (EntreeUsine)findEquipementbyId(query.getId(), graphe);
        return Stringify.stringify(response);
    }
    public String getInformationSortieUsine(String sortieUsineStr){
        SortieUsine query = (SortieUsine)Destringify.parseVal(sortieUsineStr);
        SortieUsine response = (SortieUsine)findEquipementbyId(query.getId(), graphe);
        return Stringify.stringify(response);
    }
        /*##############################
                MODIFICATEUR
        ##############################*/
    
    
 /*   
    public void setMatriceRecuperationStation(Integer id, HashMap<Integer, HashMap<String, Double>> typedValues) {
        GestionnaireEquipement.setMatriceRecuperationStation(id, typedValues, graphe);
    }
  */
}
