package btl.btl.ktvtkpm.dao;

import java.util.List;
import java.util.ArrayList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.Types;
import btl.btl.ktvtkpm.model.CarType;
import btl.btl.ktvtkpm.model.TypeRevenue;

public class TypeRevenueDAO extends DAO {
    
    public List<TypeRevenue> readRevenueByCarType(Date fromDate, Date toDate) throws SQLException {
        List<TypeRevenue> list = new ArrayList<>();
        String sql = "SELECT c.type, COALESCE(SUM(b.totalPrice), 0) AS revenue " +
                     "FROM car c " +
                     "JOIN rental_order ro ON ro.carId = c.id " +
                     "JOIN bill b ON b.rentalOrderId = ro.id " +
                     "WHERE (? IS NULL OR b.paymentDate >= ?) " +
                     "AND (? IS NULL OR b.paymentDate <= ?) " +
                     "GROUP BY c.type " +
                     "ORDER BY revenue DESC";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            if (fromDate != null) {
                stmt.setDate(1, fromDate);
                stmt.setDate(2, fromDate);
            } else {
                stmt.setNull(1, Types.DATE);
                stmt.setNull(2, Types.DATE);
            }

            if (toDate != null) {
                stmt.setDate(3, toDate);
                stmt.setDate(4, toDate);
            } else {
                stmt.setNull(3, Types.DATE);
                stmt.setNull(4, Types.DATE);
            }

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                TypeRevenue tr = new TypeRevenue();
                tr.setType(CarType.valueOf(rs.getString("type")));
                tr.setRevenue(rs.getDouble("revenue"));
                list.add(tr);
            }
        }
        return list;
    }

}

