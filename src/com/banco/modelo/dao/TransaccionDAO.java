package com.banco.modelo.dao;

import com.banco.modelo.conexion.Conexion;
import com.banco.modelo.vo.Transaccion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author juanjoseadanlopez
 */
public class TransaccionDAO {
    
    public int insertaTransaccion(Transaccion transaccion) {
        Connection accesoBD = null;
        PreparedStatement ps = null;
        int resultado = -1;
        try {
            accesoBD = Conexion.getConexion();
            String sql = "INSERT INTO Transaccion(Id, CLABE, Fecha, Monto) VALUES(?, ?, ?, ?)";
            ps = accesoBD.prepareCall(sql);
            
            ps.setString(1, transaccion.getId());       //Id
            ps.setString(2, transaccion.getClabe());    //CLABE
            ps.setDate(3, new java.sql.Date(transaccion.getFecha().getTime()));     //Fecha
            ps.setDouble(4, transaccion.getMonto());    //Monto
            resultado = ps.executeUpdate();
        } catch(SQLException | ClassNotFoundException 
               | InstantiationException | IllegalAccessException ex) {
            System.out.println(ex.getMessage());
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
    
    public Transaccion buscaTransaccion(String id) {
        Connection accesoBD = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Transaccion transaccion = null;
        try{
            accesoBD = Conexion.getConexion();
            String sql = "SELECT * FROM Transaccion VALUES WHERE Id=?";
            ps = accesoBD.prepareCall(sql);
            
            ps.setString(1, id);
            
            rs = ps.executeQuery();
            
            while(rs.next()) {
                transaccion = new Transaccion(
                        rs.getString("Id"),
                        rs.getString("CLABE"),
                        rs.getDouble("Monto")
                );
                transaccion.setFecha(rs.getDate("Fecha"));
            }
        } catch (SQLException | ClassNotFoundException 
               | InstantiationException | IllegalAccessException ex) {
            transaccion = null;
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
        return transaccion;
    }
}
