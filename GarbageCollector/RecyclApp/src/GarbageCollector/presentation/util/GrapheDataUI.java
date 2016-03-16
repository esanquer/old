
package GarbageCollector.presentation.util;

import GarbageCollector.presentation.graph.convoyeur.ConvoyeurUI;
import GarbageCollector.presentation.graph.equipement.EquipementUI;
import java.util.List;

public class GrapheDataUI {
    
    
    private List<EquipementUI> listeEquipements ;
    
    private List<ConvoyeurUI> listeConvoyeurs ;
    
    
    private Double largeur;
    private Double longueur;

    public List<EquipementUI> getListeEquipements() {
        return listeEquipements;
    }

    public List<ConvoyeurUI> getListeConvoyeurs() {
        return listeConvoyeurs;
    }

    public Double getLargeur() {
        return largeur;
    }

    public Double getLongueur() {
        return longueur;
    }

    public void setListeEquipements(List<EquipementUI> listeEquipements) {
        this.listeEquipements = listeEquipements;
    }

    public void setListeConvoyeurs(List<ConvoyeurUI> listeConvoyeurs) {
        this.listeConvoyeurs = listeConvoyeurs;
    }

    public void setLargeur(Double largeur) {
        this.largeur = largeur;
    }

    public void setLongueur(Double longueur) {
        this.longueur = longueur;
    }
}
