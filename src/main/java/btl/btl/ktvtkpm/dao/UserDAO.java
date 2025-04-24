/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package btl.btl.ktvtkpm.dao;


import btl.btl.ktvtkpm.util.PasswordUtil;
import btl.btl.ktvtkpm.model.User;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserDAO extends DAO{
    
    public boolean create(User user) throws SQLException{
        
        String sql = "INSERT INTO user (username, password, name, familyName, phoneNumber, address, avatar, dateOfBirth) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, PasswordUtil.hashPassword(user.getPassword())); // Mã hóa mật khẩu
            stmt.setString(3, user.getName());
            stmt.setString(4, user.getFamilyName());
            stmt.setString(5, user.getPhoneNumber());
            stmt.setString(6, user.getAddress());
            stmt.setString(7, user.getAvatar());
            stmt.setDate(8, new java.sql.Date(user.getDateOfBirth().getTime()));

            return stmt.executeUpdate() > 0;
            
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("SQLIntegrityException: " + e);
            String err = "";
            if(this.isUsernameExists(user.getUsername())){
                err += "Username, ";
            }
            if(this.isPhoneNumberExists(user.getPhoneNumber())){
                err += "PhoneNumber, ";
            }
            if(err.endsWith(", ")){
                err = err.substring(0, err.length() - 2) + " đã tồn tại!";
            }
            throw new SQLIntegrityConstraintViolationException(err);
            
        } catch (SQLException e) {
            System.out.println("Database Error!: " + e);
            throw new SQLException("Database Error!: " + e);
        } 
    }
    
    private boolean isUsernameExists(String username) {
        String sql = "SELECT id FROM user WHERE username = ?";
        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            return rs.next(); // Nếu có kết quả, username đã tồn tại
            
        } catch (SQLException e) {
            System.out.println("Database Error!: " + e);
        }
        
        return false;
    }
    
    private boolean isPhoneNumberExists(String phonenumber) {
        String sql = "SELECT id FROM user WHERE phoneNumber = ?";
        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {
            stmt.setString(1, phonenumber);
            ResultSet rs = stmt.executeQuery();
            return rs.next(); // Nếu có kết quả, phoneNumber đã tồn tại
            
        } catch (SQLException e) {
            System.out.println("Database Error!: " + e);
        }
        
        return false;
    }
    
    public User readByUsername(String username) throws SQLException{
        User user = null;
        
        String sql = "SELECT * FROM user WHERE username = ?";
        
        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {
            
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                user = new User();
                user.setId(rs.getInt("id"));
                user.setPassword(rs.getString("password"));
                user.setIsStaff(rs.getBoolean("isStaff"));
            }
            
        } catch (SQLException e) {
            System.out.println("Database Error!: " + e);
            throw new SQLException("Database Error!: " + e);
        }
        
        return user;
    }
    
    public List<User> readAllCustomers(int page, int pageSize) throws SQLException{
        List<User> customers = new ArrayList<>();
        String sql = "SELECT * FROM user WHERE isStaff = 0 LIMIT ? OFFSET ?";

        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {
            stmt.setInt(1, pageSize);
            stmt.setInt(2, (page - 1) * pageSize);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    customers.add(mapUser(rs));
                }
            }
        } catch (SQLException e) {
            System.out.println("Database Error!: " + e);
            throw new SQLException("Database Error!: " + e);
        }
        return customers;
    }
    
    public List<User> readAllStaff(int page, int pageSize) throws SQLException{
        List<User> staffList = new ArrayList<>();
        String sql = "SELECT * FROM user WHERE isStaff = 1 LIMIT ? OFFSET ?";

        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {
            stmt.setInt(1, pageSize);
            stmt.setInt(2, (page - 1) * pageSize);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    staffList.add(mapUser(rs));
                }
            }
        } catch (SQLException e) {
            System.out.println("Database Error!: " + e);
            throw new SQLException("Database Error!: " + e);
        }
        return staffList;
    }
    
    // Chuyển ResultSet thành User
    private User mapUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setUsername(rs.getString("username"));
        user.setName(rs.getString("name"));
        user.setFamilyName(rs.getString("familyName"));
        user.setPhoneNumber(rs.getString("phoneNumber"));
        user.setAddress(rs.getString("address"));
        user.setAvatar(rs.getString("avatar"));
        user.setDateOfBirth(rs.getDate("dateOfBirth"));
        return user;
    }
    
    // Hàm chung đếm số lượng user theo isStaff
    public int getTotalUsersByType(boolean isStaff) throws SQLException {
        String sql = "SELECT COUNT(*) FROM user WHERE isStaff = ?";
        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {
            stmt.setBoolean(1, isStaff);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                } else {
                    return 0;
                }
            }
        } catch (SQLException e) {
            System.out.println("Database Error!: " + e);
            throw new SQLException("Database Error!: " + e);
        }
    }
}
