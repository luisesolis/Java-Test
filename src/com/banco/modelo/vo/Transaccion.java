package com.banco.modelo.vo;

import java.util.Date;

/**
 *
 * @author juanjoseadanlopez
 */
public class Transaccion {
    public static final String SALDO_INSUFICIENTE = 
            "TRANSACCIÓN NO REALIZADA: La cuenta no cuenta con saldo suficiente";
    public static final String MONTO_NEGATIVO = "EL MONTO DEBE SER MAYOR DE CERO";
    
    protected String id;
    protected String clabe;
    protected Date fecha;
    protected double monto;
    
    public Transaccion(String id, String clabe, double monto) {
        this.id = id;
        this.clabe = clabe;
        fecha = new Date(System.currentTimeMillis());
        this.monto = monto;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClabe() {
        return clabe;
    }

    public void setClabe(String clabe) {
        this.clabe = clabe;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    @Override
    public String toString() {
        return "Transaccion{" + "Id=" + id + ", clabe=" + clabe + ", fecha=" + fecha + ", monto=" + monto + '}';
    }
}
