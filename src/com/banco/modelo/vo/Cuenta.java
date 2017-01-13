package com.banco.modelo.vo;

/**
 *
 * @author Luis
 */
public class Cuenta {
    public static String CUENTA_NO_EXISTE = "CUENTA NO EXISTE";
    
    private String clabe;
    private String titular;
    private double saldo;

    public Cuenta(String clabe, String titular, double saldo) {
        this.clabe = clabe;
        this.titular = titular;
        this.saldo = saldo;
    }

    public String getClabe() {
        return clabe;
    }

    public void setClabe(String clabe) {
        this.clabe = clabe;
    }

    public String getTitular() {
        return titular;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    @Override
    public String toString() {
        return "Cuenta[" + "clabe= " + clabe + ", titular= " + titular + ", saldo= " + saldo + ']';
    }
    
}
