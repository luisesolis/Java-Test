package com.banco.modelo.vo;

/**
 *
 * @author Luis
 */
public class Compra extends Transaccion{
    public static final String MONTO_MAYOR_A_LIMITE = "EL MONTO SUPERA EL LIMITE ESTABLECIDO";
    public static final String COMPRA_EXITOSA = "COMPRA REALIZADA CON EXITO";
    public static final String COMPRA_FALLIDA = "COMPRA NO REALIZADA CON EXITO";
    String cadena;
    
    public static final double IVA = 0.16;
     public static final double LIMITE = 2000.0;
    
    private double iva;
    private double total;

    public Compra(String id, String clabe, double monto, double iva, double total) {
        super(id, clabe, monto);
        this.iva = iva;
        this.total = total;
    }

    public double getIva() {
        return iva;
    }

    public void setIva(double iva) {
        this.iva = iva;
    }
    

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }  

    @Override
    public String toString() {
        return "Compra[ monto: " + super.monto + " iva: " + String.format("%.2f", iva) + " total: " + String.format("%.2f", total) + ']';
    }
    
    public String toFullString() {
        return "Compra[ id: " + super.id + " clabe: " + super.clabe + " monto: " + 
                super.monto + " iva: " + String.format("%.2f", iva) + " total: " + String.format("%.2f", total) + ']';
    }
}
