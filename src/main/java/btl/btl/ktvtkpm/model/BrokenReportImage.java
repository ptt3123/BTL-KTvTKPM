/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package btl.btl.ktvtkpm.model;

import jakarta.validation.constraints.*;
import java.io.Serializable;

public class BrokenReportImage implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int id;
    
    @NotNull(message = "Link không được Null!")
    @Size(max = 25, message = "Link không được quá 25 ký tự!")
    private String link;
    
    @Size(max = 255, message = "Detail không được quá 255 ký tự!")
    private String detail;
    
    @NotNull(message = "brokenReportId không được Null!")
    @Min(value = 1, message = "brokenReportId không hợp lệ")
    private int brokenReportId;
    
    public BrokenReportImage() {}
    
//    Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public int getBrokenReportId() {
        return brokenReportId;
    }

    public void setBrokenReportId(int brokenReportId) {
        this.brokenReportId = brokenReportId;
    }

    @Override
    public String toString() {
        return "BrokenReportImage{" + "id=" + id + ", link=" + link + ", detail=" + detail + ", brokenReportId=" + brokenReportId + '}';
    }
    
    
}
