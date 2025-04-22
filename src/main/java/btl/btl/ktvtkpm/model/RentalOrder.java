package btl.btl.ktvtkpm.model;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Date;

public class RentalOrder implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private int id;
    
    @NotNull(message = "Ngày tạo không được Null!")
    private Date createDate;
    
    @NotNull(message = "Ngày bắt đầu thuê không được Null!")
    private Date rentalBeginDate;
    
    @NotNull(message = "Ngày kết thúc thuê không được Null!")
    private Date rentalEndDate;
    
    @NotNull(message = "Giá tiền không được Null!")
    @Positive(message = "Giá tiền phải dương!")
    private double price;
    
    @NotNull(message = "Trạng thái không được Null!")
    private RentalOrderStatus status;
    
    private Date receiveDate;
    
    private Date returnDate;
    
    @NotNull(message = "carId không được Null!")
    @Min(value = 1, message = "carId không hợp lệ")
    private int carId;
    
    @NotNull(message = "carId không được Null!")
    @Min(value = 1, message = "staffId không hợp lệ")
    private Integer staffId;
    
    @NotNull(message = "carId không được Null!")
    @Min(value = 1, message = "customerId không hợp lệ")
    private int customerId;
    
    @Min(value = 1, message = "brokenReportId không hợp lệ")
    private Integer brokenReportId;
    
    @Min(value = 1, message = "collateralId không hợp lệ")
    private Integer collateralId;
    
    public RentalOrder() {
        this.status = RentalOrderStatus.WAITING_VERIFY_ORDER;
    }
    
//    Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getRentalBeginDate() {
        return rentalBeginDate;
    }

    public void setRentalBeginDate(Date rentalBeginDate) {
        this.rentalBeginDate = rentalBeginDate;
    }

    public Date getRentalEndDate() {
        return rentalEndDate;
    }

    public void setRentalEndDate(Date rentalEndDate) {
        this.rentalEndDate = rentalEndDate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public RentalOrderStatus getStatus() {
        return status;
    }

    public void setStatus(RentalOrderStatus status) {
        this.status = status;
    }

    public Date getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(Date receiveDate) {
        this.receiveDate = receiveDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public Integer getStaffId() {
        return staffId;
    }

    public void setStaffId(Integer staffId) {
        this.staffId = staffId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public Integer getBrokenReportId() {
        return brokenReportId;
    }

    public void setBrokenReportId(Integer brokenReportId) {
        this.brokenReportId = brokenReportId;
    }

    public Integer getCollateralId() {
        return collateralId;
    }

    public void setCollateralId(Integer collateralId) {
        this.collateralId = collateralId;
    }

    @Override
    public String toString() {
        return "RentalOrder{" + "id=" + id + ", createDate=" + createDate + ", rentalBeginDate=" + rentalBeginDate + ", rentalEndDate=" + rentalEndDate + ", price=" + price + ", status=" + status + ", receiveDate=" + receiveDate + ", returnDate=" + returnDate + ", carId=" + carId + ", staffId=" + staffId + ", customerId=" + customerId + ", brokenReportId=" + brokenReportId + ", collateralId=" + collateralId + '}';
    }
    
}
