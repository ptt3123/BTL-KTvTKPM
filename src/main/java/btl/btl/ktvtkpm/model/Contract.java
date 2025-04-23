/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package btl.btl.ktvtkpm.model;


import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Date;

public class Contract implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private int id;
    
    @Size(max = 255, message = "Detail không được quá 255 ký tự")
    private String detail;
    
    @NotNull(message = "Ngày tạo không được Null!")
    private Date createDate;
    
    @NotNull(message = "Trạng thái không được Null!")
    private ContractStatus status;
    
    @NotNull(message = "partnerId không được Null!")
    @Min(value = 1, message = "partnerId không hợp lệ!")
    private int partnerId;
    
    public Contract() {}
    
    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getDetail() { return detail; }
    public void setDetail(String detail) { this.detail = detail; }
    
    public Date getCreateDate() { return createDate; }
    public void setCreateDate(Date createDate) { this.createDate = createDate; }
    
    public ContractStatus getStatus() { return status; }
    public void setStatus(ContractStatus status) { this.status = status; }
    
    public int getPartnerId() { return partnerId; }
    public void setPartnerId(int partnerId) { this.partnerId = partnerId; }
    
    @Override
    public String toString() {
        return "Contract{id=" + id + ", detail='" + detail + '\'' + ", createDate=" + createDate +
                ", status=" + status + ", partnerId=" + partnerId + '}';
    }
}
