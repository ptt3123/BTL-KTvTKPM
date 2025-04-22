package btl.btl.ktvtkpm.model;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Date;

public class PartnerPayment implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private int id;
    
    @NotNull(message = "Giá không được Null!")
    @Positive(message = "Giá phải dương!")
    private double price;
    
    @NotNull(message = "Ngày tạo không được Null!")
    private Date createDate;
    
    private Date verifyDate;
    
    @Size(max = 255, message = "Detail không được quá 255 ký tự!")
    private String detail;
    
    @NotNull(message = "partnerId không được Null!")
    @Min(value = 1, message = "partnerId không hợp lệ!")
    private int partnerId;
    
    @NotNull(message = "partnerId không được Null!")
    @Min(value = 1, message = "partnerId không hợp lệ!")
    private int staffId;
    
    public PartnerPayment() {}
    
    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    
    public Date getCreateDate() { return createDate; }
    public void setCreateDate(Date createDate) { this.createDate = createDate; }
    
    public Date getVerifyDate() { return verifyDate; }
    public void setVerifyDate(Date verifyDate) { this.verifyDate = verifyDate; }
    
    public String getDetail() { return detail; }
    public void setDetail(String detail) { this.detail = detail; }
    
    public int getPartnerId() { return partnerId; }
    public void setPartnerId(int partnerId) { this.partnerId = partnerId; }

    public int getStaffId() { return staffId; }
    public void setStaffId(int staffId) { this.staffId = staffId; }
 
    @Override
    public String toString() {
        return "PartnerPayment{id=" + id + ", price=" + price + ", createDate=" + createDate +
                ", verifyDate=" + verifyDate + ", detail='" + detail + "', partnerId=" + partnerId + '}';
    }
}
