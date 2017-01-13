package com.banco.modelo.vo;
/**
 *
 * @author juanjoseadanlopez
 */
public class Transferencia extends Transaccion{
    public static final double LIMITE_TRANSFERENCIA = 5000.00;
    public static final String ERROR_LIMITE = 
            "TRANSFERENCIA FALLIDA: La transferencia excede el límite de $" + LIMITE_TRANSFERENCIA;
    public static final String TRANSFERENCIA_EXITOSA = 
            "TRANSFERENCIA EXITOSA: La transferencia se concretó correctamente.";
    public static final String SALDO_INSUFICIENTE = 
            "SALDO INSUFICIENTE: El saldo de la cuenta no alcanza para completar la transacción.";
    public static final String MISMA_CUENTA = "ERROR DE TRANSFERENCIA: No puedes transferir a tu misma cuenta.";
    
    private String clabeDestino;
    
    public Transferencia(String id, String clabeOrigen, String clabeDestino, double monto) {
        super(id, clabeOrigen, monto);
        this.clabeDestino = clabeDestino;
    }

    public String getClabeDestino() {
        return clabeDestino;
    }

    public void setClabeDestino(String clabeDestino) {
        this.clabeDestino = clabeDestino;
    }

    @Override
    public String toString() {
        return "Transferencia [ Para: " + clabeDestino + ". Monto: " + super.monto + "]";
    }
}
