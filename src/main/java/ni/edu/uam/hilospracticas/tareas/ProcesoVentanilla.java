package ni.edu.uam.hilospracticas.tareas;

import ni.edu.uam.hilospracticas.modelo.Contador;
import java.util.Objects;
import java.util.function.Consumer;

public final class ProcesoVentanilla implements Runnable {
    private final String nombre;
    private final Contador contador;
    private final int solicitudes;
    private final long pausaMs;
    private final Consumer<String> registrar;
    private volatile boolean activo = true;

    public ProcesoVentanilla(String nombre, Contador contador, int solicitudes,
                            long pausaMs, Consumer<String> registrar) {
        if (solicitudes < 1 || pausaMs < 0) {
            throw new IllegalArgumentException("Parámetros de ventanilla no válidos");
        }
        this.nombre = Objects.requireNonNull(nombre);
        this.contador = Objects.requireNonNull(contador);
        this.solicitudes = solicitudes;
        this.pausaMs = pausaMs;
        this.registrar = Objects.requireNonNull(registrar);
    }

    public void detener() {
        activo = false;
    }

    @Override
    public void run() {
        registrar.accept(nombre + " inicia en " + Thread.currentThread().getName());
        int realizadas = 0;
        int intervalo = Math.max(1, solicitudes / 10);
        for (int i = 1; i <= solicitudes; i++) {
            if (!activo || Thread.currentThread().isInterrupted()) break;
            try {
                if (pausaMs > 0) Thread.sleep(pausaMs);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Conservar la solicitud de cancelación.
                break;
            }
            if (!activo || Thread.currentThread().isInterrupted()) break;
            contador.incrementar(); // Una solicitud se cuenta como una unidad completa.
            realizadas++;
            if (i == 1 || i % intervalo == 0 || i == solicitudes) {
                registrar.accept(nombre + " completó " + i + "/" + solicitudes);
            }
        }
        registrar.accept(nombre + " terminó con " + realizadas + " solicitudes");
    }
}
