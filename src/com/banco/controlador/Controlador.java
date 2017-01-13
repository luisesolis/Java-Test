package com.banco.controlador;

import com.banco.controlador.hilos.ProcesoCompra;
import com.banco.controlador.archivos.Archivo;
import com.banco.modelo.dao.CompraDAO;
import com.banco.modelo.dao.CuentaDAO;
import com.banco.modelo.dao.TransaccionDAO;
import com.banco.modelo.dao.TransferenciaDAO;
import com.banco.modelo.vo.Compra;
import com.banco.modelo.vo.Cuenta;
import com.banco.modelo.vo.Transaccion;
import com.banco.modelo.vo.Transferencia;
import com.banco.vista.BancoCBGUI;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JTextArea;

/**
 *
 * @author Luis
 */
public class Controlador implements ActionListener{
    private Cuenta cuentaUsuario1;
    private Cuenta cuentaUsuario2;
    
    private CuentaDAO cuentaDao;
    private CompraDAO compraDao;
    private TransferenciaDAO transferenciaDao;
    private TransaccionDAO transaccionDao;
    
    private ArrayList<Transaccion> listaTransacciones;
    private BancoCBGUI vista;
    
    public Controlador(BancoCBGUI vista, CuentaDAO cuentaDao, CompraDAO compraDao, 
            TransaccionDAO transaccionDao, TransferenciaDAO transferenciaDao){
        this.cuentaDao = cuentaDao;
        this.compraDao = compraDao;
        this.transferenciaDao = transferenciaDao;
        this.transaccionDao = transaccionDao;
        
        this.vista = vista;
        this.vista.btnAceptar_1.addActionListener(this);
        this.vista.btnAceptar_2.addActionListener(this);
        this.vista.btnExportaTxt.addActionListener(this);
        
        this.vista.rbCompra_1.addActionListener(this);
        this.vista.rbTransferencia_1.addActionListener(this);
        this.vista.rbCompra_2.addActionListener(this);
        this.vista.rbTransferencia_2.addActionListener(this);
        
        this.vista.txtMensaje_Cta1.setEditable(false);
        this.vista.txtMensaje_Cta2.setEditable(false);
        
        cargarDatos();
        
        listaTransacciones = new ArrayList<Transaccion>();
    }
    
    private void cargarDatos(){
        cuentaUsuario1 = cuentaDao.obtenerCuenta("90546");
        cuentaUsuario2 = cuentaDao.obtenerCuenta("90640");
        
        vista.lblCLABE_Cta1.setText(cuentaUsuario1.getClabe());
        vista.lblTitular_Cta1.setText(cuentaUsuario1.getTitular());
        vista.lblSaldo_Cta1.setText("$" + String.valueOf(String.format("%.2f", cuentaUsuario1.getSaldo())));
        
        vista.lblCLABE_Cta2.setText(cuentaUsuario2.getClabe());
        vista.lblTitular_Cta2.setText(cuentaUsuario2.getTitular());
        vista.lblSaldo_Cta2.setText("$" + String.valueOf(String.format("%.2f", cuentaUsuario2.getSaldo())));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object evento = e.getSource();
        if(evento == vista.btnAceptar_1){
            //Usuario 1
            if(vista.rbTransferencia_1.isSelected()){
                //Transferencia
                String clabe = vista.txtCtaDestino_1.getText();
                validaTransferenciaCta(clabe, cuentaUsuario1, vista.txtMensaje_Cta1, vista.txtMonto_1.getText());
            }else{
                //Compra
                iniciarCompra(cuentaUsuario1, vista.txtMonto_1.getText(), vista.txtMensaje_Cta1, vista.btnAceptar_1);
            }
        }else if(evento == vista.btnAceptar_2){
            //Usuario2
            if(vista.rbTransferencia_2.isSelected()){
                //Transferencia
                String clabe = vista.txtCtaDestino_2.getText();
                validaTransferenciaCta(clabe, cuentaUsuario2, vista.txtMensaje_Cta2, vista.txtMonto_2.getText());
            }else{
                //Compra
                iniciarCompra(cuentaUsuario2, vista.txtMonto_2.getText(), vista.txtMensaje_Cta2, vista.btnAceptar_2);
            }
        }else if(evento == vista.btnExportaTxt){
            //Exportar
            Archivo.guardarArchivo(listaTransacciones, vista);
        }else if(evento == vista.rbCompra_1){
            vista.txtCtaDestino_1.setEnabled(false);
            vista.txtCtaDestino_1.setText("");
        }else if(evento == vista.rbTransferencia_1){
            vista.txtCtaDestino_1.setEnabled(true);
        }else if(evento == vista.rbCompra_2){
            vista.txtCtaDestino_2.setEnabled(false);
            vista.txtCtaDestino_2.setText("");
        }else if(evento == vista.rbTransferencia_2){
            vista.txtCtaDestino_2.setEnabled(true);
        }
    }
    
    private void iniciarCompra(Cuenta usuario, String montoS, JTextArea areaMensaje, JButton boton){
        double monto = calcularMonto(montoS);
        if(monto <= 0){
            areaMensaje.append(Compra.MONTO_NEGATIVO + "\n");
        }else{
            double iva  = monto * Compra.IVA;
            double total = monto + iva;
            if(monto <= Compra.LIMITE){
                if(usuario.getSaldo() < total){
                    areaMensaje.append(Compra.SALDO_INSUFICIENTE + "\n");
                    boton.setEnabled(false);
                    ProcesoCompra pc = new ProcesoCompra(usuario, montoS, areaMensaje, boton, this );
                    pc.start();
                }else{
                    String id = generaId();
                    Compra compra = new Compra(id, usuario.getClabe(), monto, iva, total);
                    areaMensaje.append(registrarCompra_Transaccion(usuario, compra) + "\n");
                }
            }else{
                areaMensaje.append(Compra.MONTO_MAYOR_A_LIMITE + "\n");
            }
        } 
        areaMensaje.setCaretPosition(areaMensaje.getDocument().getLength());
    }
    
    
    public double calcularMonto(String m){
        double monto;
        try {
            if(m.length() <= 0) throw new NullPointerException("Campo Vacio");
            monto = Double.parseDouble(m);
        } catch (NumberFormatException e) {
            //Palabra o caracter no digito
            monto = 500.0;
        }catch (NullPointerException e){
            //campo vacio
            monto = 100.0;
        }
        return monto;
    }
    
    public String registrarCompra_Transaccion(Cuenta cuenta, Compra compra){
        String mensaje = "";
        
        Transaccion transaccion = new Transaccion(compra.getId(), cuenta.getClabe(), compra.getMonto());
        
        if(cuentaDao.restarSaldo(cuenta, compra.getTotal()) > 0 && transaccionDao.insertaTransaccion(transaccion) > 0
                && compraDao.insertarCompra(compra) > 0){
                 mensaje = Compra.COMPRA_EXITOSA + " " + compra.toString();
                 listaTransacciones.add(compra);
                 //Actualizar datos en memoria
                 cargarDatos();        
        }
        else
            mensaje = Compra.COMPRA_FALLIDA;
            
        return mensaje;
    }
    
    public String generaId(){
        return System.currentTimeMillis() + String.valueOf(new Random().nextInt(1000000));
    }
        
    private boolean transfiereDinero(Cuenta cuentaOrigen, Cuenta cuentaDestino, double monto) {
        String id = generaId();
        Transaccion transac = new Transaccion(
                id,
                cuentaOrigen.getClabe(),
                monto
        );
        Transferencia transfer = new Transferencia(
                id,
                cuentaOrigen.getClabe(),
                cuentaDestino.getClabe(),
                monto
        );
        if(transaccionDao.insertaTransaccion(transac) == 1 && 
           transferenciaDao.insertaTransferencia(transfer) == 1 && 
           cuentaDao.restarSaldo(cuentaOrigen, monto) == 1 && 
           cuentaDao.sumarSaldo(cuentaDestino, monto) == 1){
            listaTransacciones.add(transfer);
            cargarDatos();
            return true;
        }
        return false;
    }
    
    private void iniciaTransferencia(Cuenta cuentaOrigen, Cuenta cuentaDestino, String montoS, JTextArea areaMensaje) {
        double monto = calcularMonto(montoS);
        if(monto <= 0){
            areaMensaje.append(Compra.MONTO_NEGATIVO + "\n");
        }else{
            if(monto <= Transferencia.LIMITE_TRANSFERENCIA) {
                if(cuentaOrigen.getSaldo() >= monto) {
                    if(transfiereDinero(cuentaOrigen, cuentaDestino, monto)) {
                        areaMensaje.append(Transferencia.TRANSFERENCIA_EXITOSA + "\n");
                    } else {
                        areaMensaje.append("Error desconocido..." + "\n");
                    }
                } else {
                    areaMensaje.append(Transferencia.SALDO_INSUFICIENTE + "\n");
                }
            } else {
                areaMensaje.append(Transferencia.ERROR_LIMITE + "\n");
            }
        }
    }
    
    private void validaTransferenciaCta(String clabe, Cuenta cuentaOrigen, JTextArea mensajes, String txtMonto) {
        if(!cuentaOrigen.getClabe().equals(clabe)) {
            Cuenta cuentaDestino = cuentaDao.obtenerCuenta(clabe);
            if(cuentaDestino != null)
                iniciaTransferencia(cuentaOrigen, cuentaDestino, 
                        txtMonto, mensajes);
            else
                mensajes.append(Cuenta.CUENTA_NO_EXISTE + "\n");
        } else {
            mensajes.append(Transferencia.MISMA_CUENTA + "\n");
        }
        mensajes.setCaretPosition(mensajes.getDocument().getLength());
    }

    public CuentaDAO getCuentaDao() {
        return cuentaDao;
    }

    public void setCuentaDao(CuentaDAO cuentaDao) {
        this.cuentaDao = cuentaDao;
    }
}
