package btl.btl.ktvtkpm.model;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Date;

public class Collateral implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private int id;
    
    @NotNull(message = "Tên không được Null!")
    @Size(max = 50, message = "Tên không được quá 50 ký tự!")
    private String name;
    
    @Positive(message = "Giá phải là số dương!")
    private float price;
    
    @NotNull(message = "Ngày tạo không được Null!")
    private Date createDate;
    
    @Size(max = 255, message = "Detail không được quá 255 ký tự!")
    private String detail;
    
    @NotNull(message = "Trạng thái không được Null!")
    private CollateralStatus status;
    
    @NotNull(message = "customerId không được Null!")
    @Min(value = 1, message = "customerId không hợp lệ")
    private int customerId;
    
    public Collateral() {
        this.status = CollateralStatus.WAITING_EVALUATE;
    }
    
//    Getters và Setters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public float getPrice() {
        return price;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public String getDetail() {
        return detail;
    }

    public CollateralStatus getStatus() {
        return status;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public void setStatus(CollateralStatus status) {
        this.status = status;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
    
    
    @Override
    public String toString() {
        return "Collateral{id=" + id + ", name='" + name + "', price=" + price + ", createDate=" + createDate +
                ", detail='" + detail + "', status=" + status + ", customerId=" + customerId + '}';
    }
}
