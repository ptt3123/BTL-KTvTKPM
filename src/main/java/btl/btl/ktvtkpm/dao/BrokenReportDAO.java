/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package btl.btl.ktvtkpm.dao;

import btl.btl.ktvtkpm.model.BrokenReport;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

public class BrokenReportDAO extends DAO {
    
    public boolean create(BrokenReport brokenReport) throws SQLException{
        
        String sql = "INSERT INTO broken_report (detail, price, carId) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {

            stmt.setString(1, brokenReport.getDetail());
            stmt.setFloat(2, brokenReport.getPrice());
            stmt.setInt(3, brokenReport.getCarId());

            return stmt.executeUpdate() > 0;
            
        } catch (SQLIntegrityConstraintViolationException  e) {
            System.out.println("SQLIntegrityException: " + e);
            throw new SQLIntegrityConstraintViolationException("Car không tồn tại!");
            
        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
            throw new SQLException(e);
        }
    }
    
    public BrokenReport read(int id) throws SQLException{
        String sql = "SELECT * FROM broken_report WHERE id = ?";

        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToBrokenReport(rs);
            }
            
        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
            throw new SQLException(e);
        }
        return null;
    }
    
    public List<BrokenReport> readByCarId(int carId, int page, int pageSize) throws SQLException {
        List<BrokenReport> reports = new ArrayList<>();
        String sql = "SELECT * FROM broken_report WHERE carId = ? ORDER BY id DESC LIMIT ? OFFSET ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, carId);
            stmt.setInt(2, pageSize);
            stmt.setInt(3, (page - 1) * pageSize);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                reports.add(mapResultSetToBrokenReport(rs));
            }
        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
            throw new SQLException(e);
        }
        return reports;
    }
    
    private BrokenReport mapResultSetToBrokenReport(ResultSet rs) throws SQLException{
        BrokenReport brokenReport = new BrokenReport();
        brokenReport.setId(rs.getInt("id"));
        brokenReport.setDetail(rs.getString("detail"));
        brokenReport.setPrice(rs.getFloat("price"));
        brokenReport.setCarId(rs.getInt("carId"));
        return brokenReport;
    }
    
}

