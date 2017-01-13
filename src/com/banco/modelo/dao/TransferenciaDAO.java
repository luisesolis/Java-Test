package com.banco.modelo.dao;

import com.banco.modelo.conexion.Conexion;
import com.banco.modelo.vo.Transferencia;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author juanjoseadanlopez
 */
public class TransferenciaDAO {
    
    public int insertaTransferencia(Transferencia transferencia) {
        Connection accesoBD = null;
        PreparedStatement ps = null;
        int resultado = -1;
        try {
            accesoBD = Conexion.getConexion();
            String sql = "INSERT INTO Transferencia(Id, CLABE_Destino) VALUES(?,?)";
            ps = accesoBD.prepareCall(sql);
            
            ps.setString(1, transferencia.getId());
            ps.setString(2, transferencia.getClabeDestino());
            
            resultado = ps.executeUpdate();
        } catch (SQLException | ClassNotFoundException 
               | InstantiationException | IllegalAccessException ex) {
        } finally {
            try {
                if(ps != null)
                    ps.close();
            } catch (SQLException ex) { }
            
            try {
                if(accesoBD != null)
                    Conexion.cerrarConexion();
            } catch (SQLException ex) { }
        }
        return resultado;
    }
    
    public Transferencia buscaTransferencia(String id) {
        Connection accesoBD = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Transferencia transferencia = null;
        try {
            accesoBD = Conexion.getConexion();
            String sql = "SELECT * FROM Transferencia WHERE Id=?";
            ps = accesoBD.prepareCall(sql);
            
            ps.setString(1, id);
            
            rs = ps.executeQuery();
            
            while(rs.next()) {
                transferencia = new Transferencia(
                        rs.getString("Id"),
                        rs.getString("CLABE_Origen"),
                        rs.getString("CLABE_Destino"),
                        rs.getDouble("Monto")
                );
            }
            
        } catch(SQLException | ClassNotFoundException 
               | InstantiationException | IllegalAccessException ex) {
            
        } finally {
            try {
                if(rs != null)
                    rs.close();
            } catch (SQLException ex) { }
            
            try {
                if(ps != null)
                    ps.close();
            } catch (SQLException ex) { }
            
            try {
                if(accesoBD != null)
                    Conexion.cerrarConexion();
            } catch (SQLException ex) { }
        }
        return transferencia;
    }
}
