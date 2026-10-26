package ni.edu.uam.hilospracticas.modelo;

/** Solo para demostrar una condición de carrera. NO usar en producción. */
public final class ContadorInseguro implements Contador {
    private int total;

    @Override
    public void incrementar() {
        int anterior = total;
        Thread.yield(); // Amplía la oportunidad de intercalado; no asegura el fallo.
        total = anterior + 1;
    }

    @Override
    public int getTotal() {
        return total; // Se lee solo después de join() en la prueba de consola.
    }
}
