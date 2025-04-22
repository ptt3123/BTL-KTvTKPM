package btl.btl.ktvtkpm.model;

import jakarta.validation.constraints.*;
import java.io.Serializable;

public class Partner implements Serializable {
    
    private static final long serialVersionUID = 1L;
  
    private int id;
    
    @NotNull(message = "Tên không được Null!")
    @Size(min = 5, max = 50, message = "Tên phải có ít nhất 5, nhiều nhất 50 ký tự")
    private String name;
    
    @Size(max = 255, message = "Detail có nhiều nhất 255 ký tự!")
    private String detail;
    
    public Partner() {}
    
    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getDetail() { return detail; }
    public void setDetail(String detail) { this.detail = detail; }
    
    @Override
    public String toString() {
        return "Partner{id=" + id + ", name='" + name + '\'' + ", detail='" + detail + "'}";
    }
}
