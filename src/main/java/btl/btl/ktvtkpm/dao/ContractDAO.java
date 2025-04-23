/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package btl.btl.ktvtkpm.dao;

import btl.btl.ktvtkpm.model.Contract;
import btl.btl.ktvtkpm.model.ContractStatus;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

public class ContractDAO extends DAO {
    
    public boolean create(Contract contract) throws SQLException{
        
        String sql = "INSERT INTO contract (detail, createDate, status, partnerId) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {
            stmt.setString(1, contract.getDetail());
            stmt.setDate(2, new java.sql.Date(contract.getCreateDate().getTime()));
            stmt.setString(3, contract.getStatus().toString());
            stmt.setInt(4, contract.getPartnerId());

            return stmt.executeUpdate() > 0;
            
        } catch (SQLIntegrityConstraintViolationException e){
            System.out.println("SQLIntegrityException: " + e);
            throw new SQLIntegrityConstraintViolationException("Không tìm thấy đối tác!");
            
        } catch (SQLException e){
            System.out.println("Database Error: " + e.getMessage());
            throw new SQLException(e);
            
        }
    }
    
    public boolean updateContractStatusToLiquidated(int contractId) throws Exception {
        String sql = "UPDATE contract SET status = 'LIQUIDATED' WHERE id = ? AND status = 'SIGNED'";

        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {
            stmt.setInt(1, contractId);

            if (stmt.executeUpdate() > 0){
                return true;
                
            } else {
                
                if (isContractIdExists(contractId)) {
                    throw new Exception("Hợp đồng đã bị hủy bỏ trước đó!");
                    
                } else {
                    throw new Exception("Hợp đồng không tồn tại!");
                }
                
            }
            
        } catch (SQLException e){
            System.out.println("Database Error: " + e.getMessage());
            throw new SQLException(e);
            
        }
    }
    
    public boolean isContractIdExists(int id){
        String sql = "SELECT id FROM contract WHERE id = ?";
        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
            
        } catch (SQLException e) {
            System.out.println("Database Error!: " + e);
        }
        
        return false;
    }
    
    public boolean isContractOfPartner(int contractId, int partnerId){
        String sql = "SELECT id FROM contract WHERE id = ? and partnerId = ?";
        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {
            stmt.setInt(1, contractId);
            stmt.setInt(1, partnerId);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
            
        } catch (SQLException e) {
            System.out.println("Database Error!: " + e);
        }
        
        return false;
    }
    
    public List<Contract> readListByPartnerIdOrderByCreateDate(int partnerId, int page, int pageSize) throws SQLException {
        List<Contract> contracts = new ArrayList<>();
        String sql = "SELECT * FROM contract WHERE partnerId = ? ORDER BY createDate DESC LIMIT ? OFFSET ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, partnerId);
            stmt.setInt(2, pageSize);
            stmt.setInt(3, (page - 1) * pageSize);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                contracts.add(mapResultSetToContract(rs));
            }
        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
            throw new SQLException(e);
        }
        return contracts;
    }
    
    public Contract read(int contractId) throws SQLException {
        String sql = "SELECT * FROM contract WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, contractId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToContract(rs);
            }
        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
            throw new SQLException(e);
        }
        return null;
    }

    private Contract mapResultSetToContract(ResultSet rs) throws SQLException {
        Contract contract = new Contract();
        contract.setId(rs.getInt("id"));
        contract.setDetail(rs.getString("detail"));
        contract.setCreateDate(rs.getDate("createDate"));
        contract.setStatus(ContractStatus.valueOf(rs.getString("status")));
        contract.setPartnerId(rs.getInt("partnerId"));
        return contract;
    }
}
