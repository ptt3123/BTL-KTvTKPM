package btl.btl.ktvtkpm.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import btl.btl.ktvtkpm.util.EnvUtil;

public class DAO implements AutoCloseable {
    protected Connection connection;
    
    public DAO() {
        String URL = EnvUtil.get("DB_URL");
        String USER = EnvUtil.get("DB_USER");
        String PASSWORD = EnvUtil.get("DB_PASSWORD");
        
        if (URL == null || USER == null || PASSWORD == null) {
            System.out.println("Env Variable Not Found!");
        }
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection(URL, USER, PASSWORD);

        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver Not Found!: " + e);
            
        } catch (SQLException e) {
            System.out.println("Connect Database Error!: " + e);
            
        }
    }
    
    @Override
    public void close() {
        try {
            if (this.connection != null && !this.connection.isClosed()) {
                this.connection.close();
                System.out.println("Connection Closed!!!!");
            }
            
        } catch (SQLException e) {
            System.out.println("Connection Closed Error!: " + e);
            
        }
    } 
}

