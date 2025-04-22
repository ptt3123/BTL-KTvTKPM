package btl.btl.ktvtkpm.model;

import jakarta.validation.constraints.*;
import java.io.Serializable;

public class Car implements Serializable {
    
    private static final long serialVersionUID = 1L;

    private int id;
    
    @NotNull(message = "Tên không được Null!")
    @Size(max = 50, message = "Tên không được quá 50 ký tự!")
    private String name;
    
    @NotNull(message = "Avatar không được Null!")
    @Size(max = 25, message = "avatar không được quá 25 ký tự!")
    private String avatar;
    
    @NotNull(message = "Thương hiệu không được Null!")
    @Size(max = 25, message = "Thương hiệu không được quá 25 ký tự!")
    private String brand;
    
    @NotNull(message = "Loại xe không được Null!")
    private CarType type;
    
    @NotNull(message = "Biển số xe không được Null!")
    @Size(max = 25, message = "biển số xe không được quá 25 ký tự!")
    private String licensePlate;
    
    @Size(max = 255, message = "Detail không được quá 255 ký tự!")
    private String detail;
    
    @NotNull(message = "Giá tiền không được Null!")
    @Positive(message = "Giá phải là số dương!")
    private float price;
    
    @NotNull(message = "Giá thuê xe của đối tác không được Null!")
    @Positive(message = "Giá thuê xe của đối tác phải là số dương!")
    private float partnerRentalPricePerMonth;
    
    @NotNull(message = "Giá thuê xe không được Null!")
    @Positive(message = "Giá thuê xe phải là số dương!")
    private float rentalPricePerDay;
    
    @NotNull(message = "Trạng thái xe không được Null!")
    private CarStatus status;
    
    @NotNull(message = "contractId không được Null!")
    @Min(value = 1, message = "contractId không hợp lệ!")
    private int contractId;
    
    @NotNull(message = "partnerId không được Null!")
    @Min(value = 1, message = "partnerId không hợp lệ!")
    private int partnerId;
    
    public Car() {
        this.status = CarStatus.FREE;
    }
    
    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getAvatar() { return avatar; }
    public void setAvatar(String avatar) { this.avatar = avatar; }
    
    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }
    
    public CarType getType() { return type; }
    public void setType(CarType type) { this.type = type; }
    
    public String getLicensePlate() { return licensePlate; }
    public void setLicensePlate(String licensePlate) { this.licensePlate = licensePlate; }
    
    public String getDetail() { return detail; }
    public void setDetail(String detail) { this.detail = detail; }
    
    public float getPrice() { return price; }
    public void setPrice(float price) { this.price = price; }
    
    public float getPartnerRentalPricePerMonth() { return partnerRentalPricePerMonth; }
    public void setPartnerRentalPricePerMonth(float partnerRentalPricePerMonth) { this.partnerRentalPricePerMonth = partnerRentalPricePerMonth; }
    
    public float getRentalPricePerDay() { return rentalPricePerDay; }
    public void setRentalPricePerDay(float rentalPricePerDay) { this.rentalPricePerDay = rentalPricePerDay; }
    
    public CarStatus getStatus() { return status; }
    public void setStatus(CarStatus status) { this.status = status; }
    
    public int getPartnerId() { return partnerId; }
    public void setPartnerId(int partnerId) { this.partnerId = partnerId; }

    public int getContractId() { return contractId; }

    public void setContractId(int contractId) { this.contractId = contractId; }

    @Override
    public String toString() {
        return "Car{" + "id=" + id + ", name=" + name + ", avatar=" + avatar + ", brand=" + brand + ", type=" + type + ", licensePlate=" + licensePlate + ", detail=" + detail + ", price=" + price + ", partnerRentalPricePerMonth=" + partnerRentalPricePerMonth + ", rentalPricePerDay=" + rentalPricePerDay + ", status=" + status + ", contractId=" + contractId + ", partnerId=" + partnerId + '}';
    }
    
}