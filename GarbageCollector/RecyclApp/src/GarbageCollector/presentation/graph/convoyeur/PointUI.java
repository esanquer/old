
package GarbageCollector.presentation.graph.convoyeur;

import GarbageCollector.presentation.graph.equipement.ComposantUI;
import javafx.beans.property.DoubleProperty;


public interface PointUI extends ComposantUI{
    
    public DoubleProperty getPosX();
    public DoubleProperty getPosY();
    
    public double getPosXReal();
    public double getPosYReal();
    
    public int getZoom();
    public double getRatioX();
    public double getRatioY();
 
    public void setPosX(double posX);
    public void setPosY(double posY);
    
    public void setPosXReal(double x);
    public void setPosYReal(double y);
    
    public void setZoomX(int zoom, double ratioX);
    public void setZoomY(int zoom, double ratioY);
    
}
