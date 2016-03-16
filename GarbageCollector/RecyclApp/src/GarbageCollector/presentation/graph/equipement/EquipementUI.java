package GarbageCollector.presentation.graph.equipement;

import java.util.List;

public interface EquipementUI{
    
        /*##############################
                Supression
        ##############################*/
    
    public void supprimeSorties();
    public void supprimeEntrees();
    
    public void verifieConformite();
    
        /*##############################
                ACCESSEUR
        ##############################*/
    
    public int getMyId();
    public double getPosX();
    public double getPosY();
    public double getLongueur();
    public double getHauteur();
    
    public double getPosXReal();
    public double getPosYReal();
    public double getLongueurReal();
    public double getHauteurReal();
    
    public int getZoom();
    public double getRatioX();
    public double getRatioY();
    
    public List<EntreeEquipementUI> getEntreeEquipement();
    public List<SortieEquipementUI> getSortieEquipement();
    
        /*##############################
                MODIFICATEUR
        ##############################*/
    
    public void setPosX(double x);
    public void setPosY(double y);
    public void setLongueur(double l);
    public void setHauteur(double h);
    
    public void setPosXReal(double x);
    public void setPosYReal(double y);
    public void setLongueurReal(double l);
    public void setHauteurReal(double h);

    public void setEntreeEquipement(List<EntreeEquipementUI> l);
    public void setSortieEquipement(List<SortieEquipementUI> l);
    
    public void setZoomX(int zoom, double ratioX);
    public void setZoomY(int zoom, double ratioY);
    
    public void attacherEntreesEtSorties();

}
