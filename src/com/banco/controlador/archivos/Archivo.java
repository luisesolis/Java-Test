/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.banco.controlador.archivos;

import com.banco.modelo.vo.Compra;
import com.banco.modelo.vo.Transaccion;
import com.banco.modelo.vo.Transferencia;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Luis
 */
public class Archivo {
    public static void guardarArchivo(ArrayList<Transaccion> listaTransacciones, JFrame vista) {
        try
        {
            JFileChooser ventana = new JFileChooser();
            FileNameExtensionFilter filtroTexto = new FileNameExtensionFilter("TXT","txt");
            ventana.setFileFilter(filtroTexto);
            ventana.showSaveDialog(vista);
            File archivo = ventana.getSelectedFile();
            
            if(archivo !=null)
            {
                String ruta = archivo.toString();
                if(!ruta.contains(".txt"))
                    ruta += ".txt";
                try (FileWriter archivoDD = new FileWriter(ruta)) {
                    String txt = "";
                    for (Transaccion item : listaTransacciones) {
                        if(item instanceof Compra){
                            //Es compra
                            Compra aux = (Compra) item;
                            txt += "Compra: \n" +
                                    "\t[" + aux.getFecha() + "] : [" + aux.getClabe() + "]\n" +
                                    " \tMonto: " + aux.getMonto() + " IVA: " + aux.getIva() + " Total: " + aux.getTotal() + "\n";
                            
                        }else if (item instanceof Transferencia){
                            //Es Tranferencia
                            Transferencia aux = (Transferencia) item;
                            txt += "Transferencia: \n" +
                                    "\t[" + new Date(aux.getFecha().toString()) + "] : [" + aux.getClabe() + "]\n" +
                                    "\tClabe Destino: " + aux.getClabeDestino() + " Monto: " + aux.getMonto() + "\n";
                        }
                    }
                    archivoDD.write(txt);
                }
                listaTransacciones.clear();
                JOptionPane.showMessageDialog(null, "EL ARCHIVO SE HA GUARDADO CORRECTAMENTE",
                          "INFORMACIÓN",JOptionPane.INFORMATION_MESSAGE);
            }
        }catch(IOException ex){
            JOptionPane.showMessageDialog(null,
               "EL ARCHIVO NO SE HA GUARDADO",
                  "ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
        }
    }
}
