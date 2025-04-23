/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package btl.btl.ktvtkpm.dao;

import btl.btl.ktvtkpm.model.BrokenReportImage;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

public class BrokenReportImageDAO extends DAO {
    
    public boolean create(BrokenReportImage image) throws SQLException{
        
        String sql = "INSERT INTO broken_report_image (link, detail, brokenReportId) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {

            stmt.setString(1, image.getLink());
            stmt.setString(2, image.getDetail());
            stmt.setInt(3, image.getBrokenReportId());

            return stmt.executeUpdate() > 0;
            
        } catch (SQLIntegrityConstraintViolationException  e) {
            System.out.println("SQLIntegrityException: " + e);
            throw new SQLIntegrityConstraintViolationException("Broken Report không tồn tại!");
            
        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
            throw new SQLException(e);
        }
    }
    
    public List<BrokenReportImage> readByBrokenReportId(int brokenReportId, int page, int pageSize) throws SQLException{
        List<BrokenReportImage> images = new ArrayList<>();
        String sql = "SELECT * FROM broken_report_image WHERE brokenReportId = ? LIMIT ? OFFSET ?";

        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {

            stmt.setInt(1, brokenReportId);
            stmt.setInt(2, pageSize);
            stmt.setInt(3, (page - 1) * pageSize);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                BrokenReportImage image = new BrokenReportImage();
                image.setId(rs.getInt("id"));
                image.setLink(rs.getString("link"));
                image.setDetail(rs.getString("detail"));
                image.setBrokenReportId(rs.getInt("brokenReportId"));
                images.add(image);
            }
            
        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
            throw new SQLException(e);
        }
        return images;
    }

}

