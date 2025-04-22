package btl.btl.ktvtkpm.model;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class User implements Serializable {

    private static final long serialVersionUID = 1L;
  
    private Integer id;

    @NotNull(message = "Username không được Null!")
    @Size(min = 5, max = 25, message = "Username phải có từ 5 đến 25 ký tự!")
    private String username;

    @NotNull(message = "Password không được Null!")
    @Size(min = 8, max = 64, message = "Password phải có từ 8 đến 64 ký tự!")
    private String password;

    @NotNull(message = "Tên không được Null!")
    @Size(max = 50, message = "Tên không được quá 50 ký tự!")
    private String name;

    @Size(max = 50, message = "Họ không được quá 50 ký tự!")
    private String familyName;

    @NotNull(message = "Số điện thoại không được Null!")
    @Pattern(regexp = "\\d{10}", message = "Số điện thoại phải có 10 chữ số!")
    private String phoneNumber;

    @NotNull(message = "Địa chỉ không được Null!")
    @Size(max = 100, message = "Địa chỉ không được quá 100 ký tự!")
    private String address;

    @Size(max = 255, message = "Đường dẫn ảnh đại diện không được quá 255 ký tự!")
    private String avatar;

    @NotNull(message = "Ngày sinh không được Null!")
    @Past(message = "Ngày sinh phải là ngày trong quá khứ!")
    private Date dateOfBirth;
    
    private boolean isStaff;

    // Constructor không tham số
    public User() {}
    
    // Getter và Setter
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getFamilyName() { return familyName; }
    public void setFamilyName(String familyName) { this.familyName = familyName; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getAvatar() { return avatar; }
    public void setAvatar(String avatar) { this.avatar = avatar; }

    public Date getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(Date dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    public boolean isIsStaff() { return isStaff; }
    public void setIsStaff(boolean isStaff) { this.isStaff = isStaff; }
    
    @Override
    public String toString() {
        return "User{" + "id=" + id + ", username=" + username + ", name=" + name + 
               ", phoneNumber=" + phoneNumber + ", address=" + address + ", dateOfBirth=" + dateOfBirth + '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        User user = (User) obj;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
