package btl.btl.ktvtkpm.dao;

import java.util.List;
import java.util.ArrayList;
import btl.btl.ktvtkpm.model.PartnerStatistics;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.Types;

public class PartnerStatisticsDAO extends DAO {
    public List<PartnerStatistics> readList(String keyword, Date fromDate, Date toDate, int page, int pageSize) throws SQLException {
        List<PartnerStatistics> list = new ArrayList<>(); 
        String sql = "SELECT p.*, COALESCE(t1.revenue, 0) AS revenue\n" +
                        "FROM partner p\n" +
                        "LEFT JOIN (\n" +
                        "    SELECT c.partnerId, SUM(t.totalPrice) AS revenue\n" +
                        "    FROM car c\n" +
                        "    LEFT JOIN (\n" +
                        "        SELECT ro.carId, SUM(b.totalPrice) AS totalPrice\n" +
                        "        FROM bill b\n" +
                        "        LEFT JOIN rental_order ro ON b.rentalOrderId = ro.id\n" +
                        "        WHERE \n" +
                        "            (? IS NULL OR b.paymentDate >= ?) AND\n" +
                        "            (? IS NULL OR b.paymentDate <= ?)\n" +
                        "        GROUP BY ro.carId\n" +
                        "    ) t ON t.carId = c.id\n" +
                        "    GROUP BY c.partnerId\n" +
                        ") t1 ON t1.partnerId = p.id\n" +
                        "WHERE LOWER(name) LIKE ?\n" +
                        "ORDER BY revenue DESC\n" +
                        "LIMIT ? OFFSET ?;";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            
            if (fromDate != null){
                stmt.setDate(1, fromDate); 
                stmt.setDate(2, fromDate);
            } else {
                stmt.setNull(1, Types.DATE);
                stmt.setNull(2, Types.DATE);
            }
            
            if (toDate != null){
                stmt.setDate(3, toDate); 
                stmt.setDate(4, toDate);
            } else {
                stmt.setNull(3, Types.DATE);
                stmt.setNull(4, Types.DATE);
            }
            
            stmt.setString(5, "%" + keyword.toLowerCase() + "%");
            
            stmt.setInt(6, pageSize);
            stmt.setInt(7, (page - 1) * pageSize);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(mapResultSetToPartnerStatistics(rs));
            }
            
        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
            throw new SQLException(e);
        }
        return list;
    }
    
    private PartnerStatistics mapResultSetToPartnerStatistics(ResultSet rs) throws SQLException {
        PartnerStatistics ps = new PartnerStatistics();
        ps.setId(rs.getInt("id"));
        ps.setName(rs.getString("name"));
        ps.setDetail(rs.getString("detail"));
        ps.setRevenue(rs.getFloat("revenue"));
        return ps;
    }
}

