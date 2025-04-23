/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package btl.btl.ktvtkpm.dao;

import btl.btl.ktvtkpm.model.Video;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

public class VideoDAO extends DAO {
    
    public boolean create(Video video) throws SQLException {
        String sql = "INSERT INTO video (link, detail, carId) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {
            stmt.setString(1, video.getLink());
            stmt.setString(2, video.getDetail());
            stmt.setInt(3, video.getCarId());

            return stmt.executeUpdate() > 0;
            
        } catch (SQLIntegrityConstraintViolationException e){
            System.out.println("SQLIntegrityException: " + e);
            throw new SQLIntegrityConstraintViolationException("Không tìm thấy CarId!");
            
        } catch (SQLException e){
            System.out.println("Database Error: " + e.getMessage());
            throw new SQLException(e);
            
        }
    }
    public List<Video> readVideosByCarId(int carId, int page, int pageSize) throws SQLException {
        List<Video> videos = new ArrayList<>();
        String sql = "SELECT * FROM video WHERE carId = ? LIMIT ? OFFSET ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, carId);
            stmt.setInt(2, pageSize);
            stmt.setInt(3, (page - 1) * pageSize);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                videos.add(mapResultSetToVideo(rs));
            }
        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
            throw new SQLException(e);
        }
        return videos;
    }

    private Video mapResultSetToVideo(ResultSet rs) throws SQLException {
        Video video = new Video();
        video.setId(rs.getInt("id"));
        video.setLink(rs.getString("link"));
        video.setDetail(rs.getString("detail"));
        video.setCarId(rs.getInt("carId"));
        return video;
    }
    
}

