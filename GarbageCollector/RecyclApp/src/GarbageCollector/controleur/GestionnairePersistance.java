package GarbageCollector.controleur;

import GarbageCollector.domaine.Graphe;
import GarbageCollector.serviceTechnique.ServicePersistance;

public class GestionnairePersistance {

	public static void enregistrerGraphe(Graphe g, String filePath){
            ServicePersistance.enregistrerGraphe(g, filePath);
	}

	public static Graphe chargerGraphe(String filePath){
            return ServicePersistance.chargerGraphe(filePath);	
	}
}