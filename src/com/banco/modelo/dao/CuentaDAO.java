package com.banco.modelo.dao;

import com.banco.modelo.conexion.Conexion;
import com.banco.modelo.vo.Cuenta;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 *
 * @author Luis
 */
public class CuentaDAO {
   
    public Cuenta obtenerCuenta(String clabe){
        Cuenta cuenta = null;
        Connection accesoBD;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            accesoBD = Conexion.getConexion();
            String query = "SELECT * FROM cuenta WHERE clabe = ?";
            ps = accesoBD.prepareStatement(query);
            ps.setString(1, clabe);
            rs = ps.executeQuery();
            if(rs.next()){
                cuenta = new Cuenta(rs.getString(1), rs.getString(2),rs.getDouble(3));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            cuenta = null;
        }finally{
            try{ rs.close(); }catch(Exception e){System.out.println(e.getMessage());}
            try{ ps.close(); }catch(Exception e){System.out.println(e.getMessage());}
            try{ Conexion.cerrarConexion(); }catch(Exception e){System.out.println(e.getMessage());}
        }
        return cuenta;
    }
    
    public int sumarSaldo(Cuenta cuenta, double monto){
        int nRegistros = 0;
        Connection accesoBD;
        PreparedStatement psConsulta = null;
        ResultSet rs = null;
        PreparedStatement psUpdate = null;
        try {
            accesoBD = Conexion.getConexion();
            String query = "SELECT saldo FROM cuenta WHERE CLABE = ?";
            psConsulta = accesoBD.prepareStatement(query);
            psConsulta.setString(1, cuenta.getClabe());
            rs = psConsulta.executeQuery();
            if(rs.next()){
                double saldoActual = rs.getDouble(1);
                query = "UPDATE cuenta SET saldo = ? WHERE CLABE = ?";
                psUpdate = accesoBD.prepareCall(query);
                psUpdate.setDouble(1, saldoActual + monto);
                psUpdate.setString(2, cuenta.getClabe());
                nRegistros = psUpdate.executeUpdate();
            }           
        } catch (Exception e) {
            System.out.println(e.getMessage());
            nRegistros = 0;
        }finally{
            try{ psUpdate.close(); }catch(Exception e){System.out.println(e.getMessage());}
            try{ rs.close(); }catch(Exception e){System.out.println(e.getMessage());}
            try{ psConsulta.close(); }catch(Exception e){System.out.println(e.getMessage());}
            try{ Conexion.cerrarConexion(); }catch(Exception e){System.out.println(e.getMessage());}
        }
        return nRegistros;
    }
    
    public int restarSaldo(Cuenta cuenta, double monto){
        int nRegistros = 0;
        Connection accesoBD;
        PreparedStatement psConsulta = null;
        ResultSet rs = null;
        PreparedStatement psUpdate = null;
        try {
            accesoBD = Conexion.getConexion();
            String query = "SELECT saldo FROM cuenta WHERE CLABE = ?";
            psConsulta = accesoBD.prepareStatement(query);
            psConsulta.setString(1, cuenta.getClabe());
            rs = psConsulta.executeQuery();
            if(rs.next()){
                double saldoActual = rs.getDouble(1);
                query = "UPDATE cuenta SET saldo = ? WHERE CLABE = ?";
                psUpdate = accesoBD.prepareCall(query);
                psUpdate.setDouble(1, saldoActual - monto);
                psUpdate.setString(2, cuenta.getClabe());
                nRegistros = psUpdate.executeUpdate();
            }           
        } catch (Exception e) {
            System.out.println(e.getMessage());
            nRegistros = 0;
        }finally{
            try{ psUpdate.close(); }catch(SQLException e){System.out.println(e.getMessage());}
            try{ rs.close(); }catch(SQLException e){System.out.println(e.getMessage());}
            try{ psConsulta.close(); }catch(SQLException e){System.out.println(e.getMessage());}
            try{ Conexion.cerrarConexion(); }catch(SQLException e){System.out.println(e.getMessage());}
        }
        return nRegistros;
    }
}
