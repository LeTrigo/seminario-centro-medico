package centromedico.service;

public class TurnoNoDisponibleException extends Exception {

    private static final long serialVersionUID = 1L;

    public TurnoNoDisponibleException(String mensaje) {
        super(mensaje);
    }
}