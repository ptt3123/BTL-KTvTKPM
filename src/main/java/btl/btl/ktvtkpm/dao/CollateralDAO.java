package btl.btl.ktvtkpm.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;
import btl.btl.ktvtkpm.model.Collateral;
import btl.btl.ktvtkpm.model.CollateralStatus;

public class CollateralDAO extends DAO {
    
    public boolean create(Collateral collateral) throws SQLException{
        String sql = "INSERT INTO collateral (name, price, createDate, detail, status, customerId) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {

            stmt.setString(1, collateral.getName());

            if (collateral.getPrice() == 0.0f) {
                stmt.setNull(2, java.sql.Types.FLOAT);
            } else {
                stmt.setFloat(2, collateral.getPrice());
            }

            stmt.setDate(3, new java.sql.Date(collateral.getCreateDate().getTime()));
            stmt.setString(4, collateral.getDetail());
            stmt.setString(5, collateral.getStatus().toString()); 
            stmt.setInt(6, collateral.getCustomerId());

            return stmt.executeUpdate() > 0;

        } catch (SQLIntegrityConstraintViolationException e){
            System.out.println("SQLIntegrityException: " + e);
            throw new SQLIntegrityConstraintViolationException("Không tìm thấy khách hàng!");
            
        } catch (SQLException e){
            System.out.println("Database Error: " + e.getMessage());
            throw new SQLException(e);
            
        }
    }
    
    public boolean isCollateralIdExists(int id){
        String sql = "SELECT id FROM collateral WHERE id = ?";
        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
            
        } catch (SQLException e) {
            System.out.println("Database Error!: " + e);
        }
        
        return false;
    }
    
    public boolean updateCollateralAfterEvaluate(int collateralId, float price) throws Exception{
        String sql = "UPDATE collateral SET price = ?, status = 'RECEIVE SUCCESSFULLY' WHERE id = ? AND status = 'WAITING EVALUATE'";

        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {
            stmt.setFloat(1, price);
            stmt.setInt(2, collateralId);

            if (stmt.executeUpdate() > 0){
                return true;
                
            } else {
                
                if (isCollateralIdExists(collateralId)) {
                    throw new Exception("Không thể thay đổi giá trị sau khi đánh giá!");
                    
                } else {
                    throw new Exception("Vật thế chấp không tồn tại!");
                }
                
            }

        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
            throw new SQLException(e);
        }
    }
    
    public boolean updateCollateralStatusToReturned(int collateralId) throws Exception{
        String sql = "UPDATE collateral SET status = 'RETURN SUCCESSFULLY' WHERE id = ? AND status <> 'SEIZED'";

        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {
            stmt.setInt(1, collateralId);

            if (stmt.executeUpdate() > 0){
                return true;
                
            } else {
                
                if (isCollateralIdExists(collateralId)) {
                    throw new Exception("Vật thế chấp đang bị tịch thu!");
                    
                } else {
                    throw new Exception("Vật thế chấp không tồn tại!");
                }
                
            }

        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
            throw new SQLException(e);
        }
    }
    
    public boolean updateCollateralStatusToSeized(int collateralId) throws Exception{
        String sql = "UPDATE collateral SET status = 'SEIZED' WHERE id = ? AND status == 'RECEIVE SUCCESSFULLY'";

        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {
            stmt.setInt(1, collateralId);

            if (stmt.executeUpdate() > 0){
                return true;
                
            } else {
                
                if (isCollateralIdExists(collateralId)) {
                    throw new Exception("Không thể tịch thu khi chưa đánh giá!");
                    
                } else {
                    throw new Exception("Vật thế chấp không tồn tại!");
                }
                
            }

        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
            throw new SQLException(e);
        }
    }
    
    public boolean updateStatusToWaitingReturn(int collateralId) throws Exception{
        String sql = "UPDATE collateral SET status = 'WAITING RETURN' " +
                     "WHERE id = ? AND (status = 'RECEIVE SUCCESSFULLY' OR status = 'WAITING EVALUATE')";

        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {

            stmt.setInt(1, collateralId);

            if (stmt.executeUpdate() > 0){
                return true;
                
            } else {
                
                if (isCollateralIdExists(collateralId)) {
                    throw new Exception("Vật thế chấp chỉ có thể được trả khi đã được nhận hoặc chờ đánh giá!");
                    
                } else {
                    throw new Exception("Vật thế chấp không tồn tại!");
                }
                
            }

        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
            throw new SQLException(e);
        }
    }
    
    public Collateral read(int collateralId) throws SQLException{
        String sql = "SELECT * FROM collateral WHERE id = ?";

        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {

            stmt.setInt(1, collateralId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToCollateral(rs);
            }
            
        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
            throw new SQLException(e);
        }
        return null;
    }
    
    public List<Collateral> readByCustomerId(int customerId, int page, int pageSize) throws SQLException{
        List<Collateral> collaterals = new ArrayList<>();
        String sql = "SELECT * FROM collateral WHERE customerId = ? " +
                     "ORDER BY FIELD(status, 'WAITING RETURN', 'WAITING EVALUATE', 'RECEIVE SUCCESSFULLY', 'SEIZED', 'RETURN SUCCESSFULLY') " +
                     "LIMIT ? OFFSET ?";

        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {

            stmt.setInt(1, customerId);
            stmt.setInt(2, pageSize);
            stmt.setInt(3, (page - 1) * pageSize);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Collateral collateral = mapResultSetToCollateral(rs);
                collaterals.add(collateral);
            }
            
        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
            throw new SQLException(e);
        }
        return collaterals;
    }

    private Collateral mapResultSetToCollateral(ResultSet rs) throws SQLException {
        Collateral collateral = new Collateral();
        collateral.setId(rs.getInt("id"));
        collateral.setName(rs.getString("name"));
        collateral.setPrice(rs.getFloat("price"));
        collateral.setCreateDate(rs.getDate("createDate"));
        collateral.setDetail(rs.getString("detail"));
        collateral.setStatus(CollateralStatus.valueOf(rs.getString("status")));
        collateral.setCustomerId(rs.getInt("customerId"));
        return collateral;
    }
    
}

