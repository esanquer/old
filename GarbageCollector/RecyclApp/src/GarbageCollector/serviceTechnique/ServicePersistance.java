package GarbageCollector.serviceTechnique;

import GarbageCollector.domaine.Graphe;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;



public class ServicePersistance {


	public static void enregistrerGraphe(Graphe g , String chemin) {
            try
            {
                try (FileOutputStream fileOut = new FileOutputStream (chemin)) {
                    ObjectOutputStream out = new ObjectOutputStream(fileOut);
                    out.writeObject(g);
                    out.close();
                }
            }catch(IOException i){
                i.printStackTrace();
            }
	}

	public static Graphe chargerGraphe(String chemin) {
            Graphe g = null;
            try
            {
                try (FileInputStream fileIn = new FileInputStream(chemin)) {
                    ObjectInputStream in = new ObjectInputStream(fileIn);
                    g = (Graphe) in.readObject();
                    in.close();
                }
            }catch(IOException i){
               i.printStackTrace();
            }catch(ClassNotFoundException c){
               System.out.println("Graphe class not found");
               c.printStackTrace();
            }
            return g;
	}
}
