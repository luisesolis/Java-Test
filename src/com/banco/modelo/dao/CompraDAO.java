package com.banco.modelo.dao;

import com.banco.modelo.conexion.Conexion;
import com.banco.modelo.vo.Compra;
import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 *
 * @author Luis
 */
public class CompraDAO {
    public int insertarCompra(Compra compra){
        int nRegistros = 0;
        Connection accesoBD;
        PreparedStatement ps = null;
        try {
            accesoBD = Conexion.getConexion();
            String query = "INSERT INTO compra VALUES (?,?,?)";
            ps = accesoBD.prepareCall(query);
            ps.setString(1, compra.getId());
            ps.setDouble(2, compra.getIva());
            ps.setDouble(3, compra.getTotal());
            nRegistros = ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return 0;
        }
        finally{
            try{ ps.close(); }catch(Exception e){System.out.println(e.getMessage());}
            try{ Conexion.cerrarConexion(); }catch(Exception e){System.out.println(e.getMessage());}
        }
        return nRegistros;
    }
}
