package GarbageCollector.controleur;


import static GarbageCollector.controleur.GestionnaireEquipement.findEquipementById;
import GarbageCollector.controleur.utils.Stringify;
import GarbageCollector.domaine.Graphe;
import GarbageCollector.domaine.Convoyeur;
import GarbageCollector.domaine.EntreeEquipement;
import GarbageCollector.domaine.Equipement;
import GarbageCollector.domaine.SortieEquipement;
import java.util.Iterator;
import java.util.List;


public class GestionnaireConvoyeur {
	
        /*##############################
                  AJOUT
        ##############################*/
    
    static String ajouterConvoyeur(Object o, Graphe graphe) {
        
        Convoyeur c = (Convoyeur)o;
        Convoyeur cId = new Convoyeur();
        c.setId(cId.getId());
        Equipement  equiSource = findEquipementById(c.getSortieEquipement().getEquipement().getId(),graphe),
                    equiDest = findEquipementById(c.getEntreeEquipement().getEquipement().getId(),graphe);

        Iterator<SortieEquipement> iterSortie= equiSource.getSortieEquipement().iterator();
        while(iterSortie.hasNext()){
            SortieEquipement s = iterSortie.next();
            if(s.getId() == c.getSortieEquipement().getId()){
                c.setSortieEquipement(s);
                s.setConvoyeur(c);
            }
        }
        
        Iterator<EntreeEquipement> iterEntree= equiDest.getEntreeEquipement().iterator();
        while(iterEntree.hasNext()){
            EntreeEquipement e = iterEntree.next();
            if(e.getId() == c.getEntreeEquipement().getId()){
                c.setEntreeEquipement(e);
                e.setConvoyeur(c);
            }
        }
        
        graphe.ajouterConvoyeur(c);
        String result =Stringify.stringify(c);
        return result;
    }
    
    
        /*##############################
                SUPRESSION
        ##############################*/

    public static void supprimerConvoyeur(Object o, Graphe graphe){
        Convoyeur c = (Convoyeur)o;
        graphe.supprimerConvoyeur(c);
    }


        /*##############################
                MODICATION
        ##############################*/
    
    public void modifierConvoyeur(Equipement equip1, Equipement equip2, int idSortieEquipement, 
                                    int idEntreeEquipement, Double debitmax, Graphe graphe){
    }
        
    
        /*##############################
                RECHERCHE
        ##############################*/
    
    public Convoyeur findConvoyeurBySource(SortieEquipement aSortieEquipement_source, Graphe graphe){
        Convoyeur c=new Convoyeur();
        List<Convoyeur> listeConvoyeur = graphe.getConvoyeurs();
        Iterator<Convoyeur> i = listeConvoyeur.iterator();
        while(i.hasNext()){
            //TODO
            if(i.next().getSortieEquipement()==aSortieEquipement_source){
                c=i.next();
            }
        }
        return c;
    }
}