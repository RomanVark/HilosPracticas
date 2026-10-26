package ni.edu.uam.hilospracticas.modelo;

/** Lectura y escritura protegidas por el mismo monitor. */
public final class ContadorCompartido implements Contador {
    private int total;

    @Override
    public synchronized void incrementar() {
        total++;
    }

    @Override
    public synchronized int getTotal() {
        return total;
    }
}
