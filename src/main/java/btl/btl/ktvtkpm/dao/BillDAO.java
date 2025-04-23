/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package btl.btl.ktvtkpm.dao;

import btl.btl.ktvtkpm.model.Bill;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class BillDAO extends DAO {
    
    public boolean create(Bill bill) throws SQLException {
        String sql = "INSERT INTO Bill (detail, createDate, rentalPrice, lateFee, repairFee, totalPrice, paymentDate, rentalOrderId, staffId, customerId) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {
            stmt.setString(1, bill.getDetail());
            stmt.setDate(2, new Date(bill.getCreateDate().getTime()));
            stmt.setFloat(3, bill.getRentalPrice());
            stmt.setFloat(4, bill.getLateFee());
            stmt.setFloat(5, bill.getRepairFee());
            stmt.setFloat(6, bill.getTotalPrice());

            
            if (bill.getPaymentDate() != null) {
                stmt.setDate(7, new Date(bill.getPaymentDate().getTime()));
            } else {
                stmt.setNull(7, Types.DATE);
            }

            stmt.setInt(8, bill.getRentalOrderId());
            stmt.setInt(9, bill.getStaffId());
            stmt.setInt(10, bill.getCustomerId());

            return stmt.executeUpdate() > 0;
            
        } catch (SQLIntegrityConstraintViolationException  e) {
            System.out.println("SQLIntegrityException: " + e);
            throw new SQLIntegrityConstraintViolationException();
            
        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
            throw new SQLException(e);
        }
    }
    
    public boolean updatePaymentDate(int billId, Date paymentDate) throws Exception{
        String sql = "UPDATE Bill SET paymentDate = ? WHERE id = ? AND paymentDate IS NULL";

        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {
            stmt.setDate(1, new Date(paymentDate.getTime()));
            stmt.setInt(2, billId);

            if (stmt.executeUpdate() > 0){
                return true;
                
            } else {
                
                if (isBillIdExists(billId)) {
                    throw new Exception("Không thể thay đổi ngày xác nhận thanh toán!");
                    
                } else {
                    throw new Exception("Bill không tồn tại!");
                }
                
            }
            
        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
            throw new SQLException(e);
        }
    }
    
    public boolean isBillIdExists(int id){
        String sql = "SELECT id FROM bill WHERE id = ?";
        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
            
        } catch (SQLException e) {
            System.out.println("Database Error!: " + e);
        }
        
        return false;
    }
    
    public Bill read(int id) throws SQLException{
        String sql = "SELECT * FROM Bill WHERE id = ?";
        
        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToBill(rs);
            }
            
        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
            throw new SQLException(e);
        }
        return null;
    }
    
    public List<Bill> readAllBills(int page, int pageSize) throws SQLException {
        List<Bill> bills = new ArrayList<>();
        String sql = "SELECT * FROM Bill ORDER BY (paymentDate IS NOT NULL), createDate DESC LIMIT ? OFFSET ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, pageSize);
            stmt.setInt(2, (page - 1) * pageSize);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                bills.add(mapResultSetToBill(rs));
            }
            
        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
            throw new SQLException(e);
        }
        return bills;
    }

    public List<Bill> readBillsByCustomerId(int customerId, int page, int pageSize) throws SQLException {
        List<Bill> bills = new ArrayList<>();
        String sql = "SELECT * FROM Bill WHERE customerId = ? ORDER BY (paymentDate IS NOT NULL), createDate DESC LIMIT ? OFFSET ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, customerId);
            stmt.setInt(2, pageSize);
            stmt.setInt(3, (page - 1) * pageSize);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                bills.add(mapResultSetToBill(rs));
            }
            
        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
            throw new SQLException(e);
        }
        
        return bills;
    }
    
    private Bill mapResultSetToBill(ResultSet rs) throws SQLException {
        Bill bill = new Bill();
        bill.setId(rs.getInt("id"));
        bill.setDetail(rs.getString("detail"));
        bill.setCreateDate(rs.getDate("createDate"));
        bill.setRentalPrice(rs.getFloat("rentalPrice"));
        bill.setLateFee(rs.getFloat("lateFee"));
        bill.setRepairFee(rs.getFloat("repairFee"));
        bill.setTotalPrice(rs.getFloat("totalPrice"));

        Date paymentDate = rs.getDate("paymentDate");
        if (paymentDate != null) {
            bill.setPaymentDate(paymentDate);
        }

        bill.setRentalOrderId(rs.getInt("rentalOrderId"));
        bill.setStaffId(rs.getInt("staffId"));
        bill.setCustomerId(rs.getInt("customerId"));

        return bill;
    }

}

