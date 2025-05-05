package prog2.vista;


public class CentralUBException extends Exception {

    public CentralUBException(String missatge) {
        super(missatge);
    }

    public CentralUBException(String missatge, Throwable causa) {
        super(missatge, causa);
    }
}

