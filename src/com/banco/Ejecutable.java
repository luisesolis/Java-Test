package com.banco;

import com.banco.controlador.Controlador;
import com.banco.modelo.dao.CompraDAO;
import com.banco.modelo.dao.CuentaDAO;
import com.banco.modelo.dao.TransaccionDAO;
import com.banco.modelo.dao.TransferenciaDAO;
import com.banco.vista.BancoCBGUI;

/**
 *
 * @author Luis
 */
public class Ejecutable {
    public static void main(String[] args) {
        BancoCBGUI vista = new BancoCBGUI();
        CuentaDAO cuentaDao =  new CuentaDAO();
        CompraDAO compraDao = new CompraDAO();
        TransaccionDAO transaccionDao = new TransaccionDAO();
        TransferenciaDAO transferenciaDao = new TransferenciaDAO();
        Controlador control = new Controlador(vista, cuentaDao, compraDao,transaccionDao, transferenciaDao);
        vista.setVisible(true);
    }
}
