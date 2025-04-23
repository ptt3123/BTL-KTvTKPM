
package btl.btl.ktvtkpm.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

public class Bill implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private int id;
    
    @Size(max = 255, message = "Detail không được quá 255 ký tự!")
    private String detail;
    
    @NotNull(message = "Ngày tạo không được Null!")
    private Date createDate;
    
    @NotNull(message = "Tổng giá tiền không được Null!")
    @Positive(message = "Tổng giá tiền phải dương!")
    private float rentalPrice;
    
    @NotNull(message = "Tổng giá tiền không được Null!")
    @Positive(message = "Tổng giá tiền phải dương!")
    private float totalPrice;
    
    @NotNull(message = "Phí trả xe trễ không được Null!")
    @Min(value = 0, message = "Phí trả xe trễ không thể âm!")
    private float lateFee;
    
    @NotNull(message = "Phí xửa xe trễ không được Null!")
    @Min(value = 0, message = "Phí xửa xe trễ không thể âm!")
    private float repairFee;
    
    private Date paymentDate;
    
    @NotNull(message = "rentalOrderId không được Null!")
    @Min(value = 1, message = "rentalOrderId không hợp lệ")
    private int rentalOrderId;
    
    @NotNull(message = "staffId không được Null!")
    @Min(value = 1, message = "staffId không hợp lệ")
    private Integer staffId;
    
    @NotNull(message = "customerUserId không được Null!")
    @Min(value = 1, message = "customerUserId không hợp lệ")
    private int customerId;
    
    public Bill() {}
    
//    Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public float getLateFee() {
        return lateFee;
    }

    public void setLateFee(float lateFee) {
        this.lateFee = lateFee;
    }

    public float getRepairFee() {
        return repairFee;
    }

    public void setRepairFee(float repairFee) {
        this.repairFee = repairFee;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public int getRentalOrderId() {
        return rentalOrderId;
    }

    public void setRentalOrderId(int rentalOrderId) {
        this.rentalOrderId = rentalOrderId;
    }

    public Integer getStaffId() {
        return staffId;
    }

    public void setStaffId(Integer staffId) {
        this.staffId = staffId;
    }

    public int getCustomerUserId() {
        return customerId;
    }

    public void setCustomerUserId(int customerId) {
        this.customerId = customerId;
    }

    public float getRentalPrice() {
        return rentalPrice;
    }

    public void setRentalPrice(float rentalPrice) {
        this.rentalPrice = rentalPrice;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    @Override
    public String toString() {
        return "Bill{" + "id=" + id + ", detail=" + detail + ", createDate=" + createDate + ", rentalPrice=" + rentalPrice + ", totalPrice=" + totalPrice + ", lateFee=" + lateFee + ", repairFee=" + repairFee + ", paymentDate=" + paymentDate + ", rentalOrderId=" + rentalOrderId + ", staffId=" + staffId + ", customerId=" + customerId + '}';
    }
    
}
