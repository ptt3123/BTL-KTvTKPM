package btl.btl.ktvtkpm.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.ResultSet;
import java.util.Date;
import btl.btl.ktvtkpm.model.PartnerPayment;

public class PartnerPaymentDAO extends DAO {
    
    public boolean create(PartnerPayment payment) throws SQLException{
       
        String sql = "INSERT INTO partner_payment (price, createDate, verifyDate, detail, partnerId, staffId) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {
            stmt.setDouble(1, payment.getPrice());
            stmt.setDate(2, new java.sql.Date(payment.getCreateDate().getTime()));

            // Kiểm tra verifyDate có null không
            if (payment.getVerifyDate() != null) {
                stmt.setDate(3, new java.sql.Date(payment.getVerifyDate().getTime()));
            } else {
                stmt.setNull(3, java.sql.Types.DATE);
            }

            stmt.setString(4, payment.getDetail());
            stmt.setInt(5, payment.getPartnerId());
            stmt.setInt(6, payment.getStaffId());

            return stmt.executeUpdate() > 0;
            
        } catch (SQLIntegrityConstraintViolationException e){
            System.out.println("SQLIntegrityException: " + e);
            throw new SQLIntegrityConstraintViolationException(e);
            
        } catch (SQLException e){
            System.out.println("Database Error: " + e.getMessage());
            throw new SQLException(e);
            
        }
    }
    
    public boolean updateVerifyDateById(int paymentId, Date verifyDate) throws Exception{
        
        String sql = "UPDATE partner_payment SET verifyDate = ? WHERE id = ? AND verifyDate IS NULL";
        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {
            stmt.setDate(1, new java.sql.Date(verifyDate.getTime()));
            stmt.setInt(2, paymentId);

            if (stmt.executeUpdate() > 0){
                return true;
                
            } else {
                
                if (isPartnerPaymentIdExists(paymentId)) {
                    throw new Exception("Không thể thay đổi Ngày xác nhận thanh toán!");
                    
                } else {
                    throw new Exception("Partner Payment không tồn tại!");
                }
                
            }
            
        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
            throw new SQLException(e);
        }
    }
    
    public boolean isPartnerPaymentIdExists(int id){
        String sql = "SELECT id FROM partner_payment WHERE id = ?";
        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
            
        } catch (SQLException e) {
            System.out.println("Database Error!: " + e);
        }
        
        return false;
    }
    
    public boolean isPartnerPaymentThisMonth(int partnerId) {
        String sql = "SELECT id FROM partner_payment " +
                     "WHERE partnerId = ? " +
                     "AND MONTH(createDate) = MONTH(CURRENT_DATE()) " +
                     "AND YEAR(createDate) = YEAR(CURRENT_DATE())";

        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {
            stmt.setInt(1, partnerId);
            ResultSet rs = stmt.executeQuery();
            return rs.next(); // Có ít nhất 1 dòng => đã có thanh toán
            
        } catch (SQLException e) {
            System.out.println("Database Error!: " + e);
        }

        return false;
    }

}

