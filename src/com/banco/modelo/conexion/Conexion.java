/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.banco.modelo.conexion;

import java.sql.*;
/**
 *
 * @author Luis
 */
public class Conexion {
    private static final String driver = "com.mysql.jdbc.Driver";
    private static final String url = "jdbc:mysql://localhost:3306/bancoCB";
    private static final String user = "cbts";
    private static final String password = "cbts";
    
    private static Connection conexion;
    public Conexion(){
        
    }
   
    public static Connection getConexion() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException{
        if(conexion == null || conexion.isClosed()){
            Connection con = null;
                Class.forName(driver).newInstance();
                con = DriverManager.getConnection(url, user, password);
            return con;
        }else{
            return conexion; 
        }           
    }
    
    public static void cerrarConexion() throws SQLException{
        if(conexion != null && !conexion.isClosed())
            conexion.close();
    }
}
