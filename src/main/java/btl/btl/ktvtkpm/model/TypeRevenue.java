package btl.btl.ktvtkpm.model;

import java.io.Serializable;

public class TypeRevenue implements Serializable{
    private static final long serialVersionUID = 1L;
    
    private CarType type;
    
    private double revenue;

    public TypeRevenue() {}
 
    public TypeRevenue(CarType type, double revenue) {
        this.type = type;
        this.revenue = revenue;
    }

    public CarType getType() {
        return type;
    }

    public void setType(CarType type) {
        this.type = type;
    }

    public double getRevenue() {
        return revenue;
    }

    public void setRevenue(double revenue) {
        this.revenue = revenue;
    }

    @Override
    public String toString() {
        return "TypeRevenue{" + "type=" + type + ", revenue=" + revenue + '}';
    }
    
}
