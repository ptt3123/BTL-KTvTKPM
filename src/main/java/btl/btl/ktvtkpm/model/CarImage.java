package btl.btl.ktvtkpm.model;

import jakarta.validation.constraints.*;
import java.io.Serializable;

public class CarImage implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private int id;
    
    @NotNull(message = "Link không được Null!")
    @Size(max = 25, message = "Link không được quá 25 ký tự!")
    private String link;
    
    @Size(max = 255, message = "Mô tả không được quá 255 ký tự!")
    private String detail;
    
    @NotNull(message = "carId không được Null!")
    @Min(value = 1, message = "carId không hợp lệ!")
    private int carId;
    
    public CarImage() {}
    
    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getLink() { return link; }
    public void setLink(String link) { this.link = link; }
    
    public String getDetail() { return detail; }
    public void setDetail(String detail) { this.detail = detail; }
    
    public int getCarId() { return carId; }
    public void setCarId(int carId) { this.carId = carId; }
    
    @Override
    public String toString() {
        return "CarImage{id=" + id + ", link='" + link + "', detail='" + detail + "', carId=" + carId + '}';
    }
}

