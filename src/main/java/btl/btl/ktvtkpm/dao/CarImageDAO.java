package btl.btl.ktvtkpm.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;
import btl.btl.ktvtkpm.model.CarImage;

public class CarImageDAO extends DAO {
    
    public boolean create(CarImage carImage) throws SQLException {
        String sql = "INSERT INTO car_image (link, detail, carId) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {
            stmt.setString(1, carImage.getLink());
            stmt.setString(2, carImage.getDetail());
            stmt.setInt(3, carImage.getCarId());

            return stmt.executeUpdate() > 0;
            
        } catch (SQLIntegrityConstraintViolationException e){
            System.out.println("SQLIntegrityException: " + e);
            throw new SQLIntegrityConstraintViolationException("Không tìm thấy CarId!");
            
        } catch (SQLException e){
            System.out.println("Database Error: " + e.getMessage());
            throw new SQLException(e);
            
        }
    }
    
    public List<CarImage> readByCarId(int carId, int page, int pageSize) throws SQLException{
        List<CarImage> images = new ArrayList<>();
        String sql = "SELECT * FROM car_image WHERE carId = ? LIMIT ? OFFSET ?";

        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {

            stmt.setInt(1, carId);
            stmt.setInt(2, pageSize);
            stmt.setInt(3, (page - 1) * pageSize);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                CarImage image = new CarImage();
                image.setId(rs.getInt("id"));
                image.setLink(rs.getString("link"));
                image.setDetail(rs.getString("detail"));
                image.setCarId(rs.getInt("carId"));
                images.add(image);
            }
            
        } catch (SQLException e){
            System.out.println("Database Error: " + e.getMessage());
            throw new SQLException(e);
            
        }
        return images;
    }

}

