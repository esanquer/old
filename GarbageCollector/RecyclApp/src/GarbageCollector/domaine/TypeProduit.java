
package GarbageCollector.domaine;

public class TypeProduit implements java.io.Serializable {
    private String nomTypeProduit;
    
    public TypeProduit(String nom){
        this.nomTypeProduit = nom;
    }
    
    public void setNomTypeProduit(String nom){
        this.nomTypeProduit=nom;
    }
    public String getNomTypeProduit(){
        return this.nomTypeProduit;
    }

    @Override
    public String toString() {
        return "TypeProduit{" + "_nomTypeProduit=" + nomTypeProduit + '}';
    }
}
