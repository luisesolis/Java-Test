/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.banco.controlador.hilos;

import com.banco.controlador.Controlador;
import com.banco.controlador.Controlador;
import com.banco.modelo.vo.Compra;
import com.banco.modelo.vo.Cuenta;
import javax.swing.JButton;
import javax.swing.JTextArea;

/**
 *
 * @author Luis
 */
public class ProcesoCompra extends  Thread{
    Cuenta cuenta;
    String montoString;
    JTextArea mensajes;
    JButton boton;
    Controlador controlador;

    public ProcesoCompra(Cuenta cuenta, String monto, JTextArea mensajes, JButton boton, Controlador controlador){
        this.cuenta = cuenta;
        this.montoString = monto;
        this.mensajes = mensajes;
        this.controlador = controlador;
        this.boton = boton;
    }

    @Override
    public void run() {
        double monto = controlador.calcularMonto(this.montoString);
        double iva  = monto * Compra.IVA;
        double total = monto + iva;
        boolean completo = false;
        for (int i = 0; i < 7; i++) {
            try {
                double saldoActual = controlador.getCuentaDao().obtenerCuenta(cuenta.getClabe()).getSaldo();
                if(saldoActual>= total){
                    String id = controlador.generaId();
                    Compra compra = new Compra(id, cuenta.getClabe(), monto, iva, total);
                    mensajes.append(controlador.registrarCompra_Transaccion(cuenta, compra));
                    completo = true;
                    break;
                }else{
                    mensajes.append("En Espera (Intento " + (i+1) +"/ 7)");
                    for(int c=0; c<3; c++) {
                        mensajes.append(".");
                        Thread.sleep(500);
                    }
                    mensajes.append("\n");
                    mensajes.setCaretPosition(mensajes.getDocument().getLength());
                }
            } catch (InterruptedException ex) {
                System.out.println(ex.getMessage());
            }
        }
        if(!completo) mensajes.append(Compra.COMPRA_FALLIDA + "\n");
        boton.setEnabled(true);
    }
}
