package GarbageCollector.domaine;

public class FluxMatiere  implements java.io.Serializable{
	private Double debitKilogrammesParHeure; //en kg/heure
	private TypeProduit typeProduit;

    public FluxMatiere(Double _debitKilogrammesParHeure, TypeProduit _typeProduit) {
        this.debitKilogrammesParHeure = _debitKilogrammesParHeure;
        this.typeProduit = _typeProduit;
    }

    public Double getDebitKilogrammesParHeure() {
        return debitKilogrammesParHeure;
    }


    public void setDebitKilogrammesParHeure(Double _debitKilogrammesParHeure) {
        this.debitKilogrammesParHeure = _debitKilogrammesParHeure;
    }


    public TypeProduit getTypeProduit() {
        return typeProduit;
    }


    public void setTypeProduit(TypeProduit _typeProduit) {
        this.typeProduit = _typeProduit;
    }

    @Override
    public String toString() {
        return "FluxMatiere{" + "_debitKilogrammesParHeure=" + debitKilogrammesParHeure + ", _typeProduit=" + typeProduit + '}';
    }
        
    
        
        

}