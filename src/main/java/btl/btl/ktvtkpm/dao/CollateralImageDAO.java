package btl.btl.ktvtkpm.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import btl.btl.ktvtkpm.model.CollateralImage;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;

public class CollateralImageDAO extends DAO {
    
    public boolean create(CollateralImage image) throws SQLException{
        
        String sql = "INSERT INTO collateral_image (link, detail, collateralId) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {

            stmt.setString(1, image.getLink());
            stmt.setString(2, image.getDetail());
            stmt.setInt(3, image.getCollateralId());

            return stmt.executeUpdate() > 0;
            
        } catch (SQLIntegrityConstraintViolationException  e) {
            System.out.println("SQLIntegrityException: " + e);
            throw new SQLIntegrityConstraintViolationException("Vật thế chấp không tồn tại!");
            
        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
            throw new SQLException(e);
        }
    }
    
    public List<CollateralImage> readAllByCollateralId(int collateralId, int page, int pageSize) throws SQLException{
        String sql = "SELECT * FROM collateral_image WHERE collateralId = ? LIMIT ? OFFSET ?";
        List<CollateralImage> images = new ArrayList<>();

        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {

            stmt.setInt(1, collateralId);
            stmt.setInt(2, pageSize);
            stmt.setInt(3, (page - 1) * pageSize);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                CollateralImage image = new CollateralImage();
                image.setId(rs.getInt("id"));
                image.setLink(rs.getString("link"));
                image.setDetail(rs.getString("detail"));
                image.setCollateralId(rs.getInt("collateralId"));

                images.add(image);
            }
            
        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
            throw new SQLException(e);
        }
        return images;
    }

}

