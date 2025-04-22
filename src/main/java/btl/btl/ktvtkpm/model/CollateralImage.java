package btl.btl.ktvtkpm.model;

import jakarta.validation.constraints.*;
import java.io.Serializable;

public class CollateralImage implements Serializable {
    private static final long serialVersionUID = 1L;
  
    private int id;
    
    @NotNull(message = "Link không được Null!")
    @Size(max = 25, message = "Link không được quá 25 ký tự!")
    private String link;
    
    @Size(max = 255, message = "Detail không được quá 255 ký tự!")
    private String detail;
    
    @NotNull(message = "collateralId không được Null!")
    @Min(value = 1, message = "collateralId không hợp lệ")
    private int collateralId;
    
    public CollateralImage() {}
    
    // Getters and Setters
    public int getId() {
        return id;
    }

    public String getLink() {
        return link;
    }

    public String getDetail() {
        return detail;
    }

    public int getCollateralId() {
        return collateralId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public void setCollateralId(int collateralId) {
        this.collateralId = collateralId;
    }

    @Override
    public String toString() {
        return "CollateralImage{id=" + id + ", link='" + link + "', detail=" + 
                detail + ", collateralId=" + collateralId + '}';
    }
}
