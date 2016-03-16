
package GarbageCollector.presentation.graph;

public class Grid {

        //En metre
    private double  longueur = 200,     
                    hauteur = 200,
                    echelle = 10;   //VARIABLE (1 carreau = echelle m)

        
    public double getLongueur() {
        return longueur;
    }

    public void setLongueur(double longueur) {
        this.longueur = longueur;
    }

    public double getHauteur() {
        return hauteur;
    }

    public void setHauteur(double hauteur) {
        this.hauteur = hauteur;
    }

    public double getEchelle() {
        return echelle;
    }

    public void setEchelle(double echelle) {
        this.echelle = echelle;
    }
}
