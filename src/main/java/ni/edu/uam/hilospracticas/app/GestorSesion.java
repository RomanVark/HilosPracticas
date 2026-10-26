package ni.edu.uam.hilospracticas.app;

import ni.edu.uam.hilospracticas.modelo.ContadorCompartido;
import ni.edu.uam.hilospracticas.tareas.ProcesoVentanilla;
import java.util.concurrent.ConcurrentLinkedQueue;

/** Controla dos tareas y confirma el fin de ambas en un hilo supervisor. */
public final class GestorSesion {
    private final ContadorCompartido contador = new ContadorCompartido();
    private final ConcurrentLinkedQueue<String> eventos = new ConcurrentLinkedQueue<>();
    private final ProcesoVentanilla tareaA;
    private final ProcesoVentanilla tareaB;
    private final Thread hiloA;
    private final Thread hiloB;
    private volatile boolean iniciada;
    private volatile boolean cancelada;
    private volatile boolean finalizada;

    public GestorSesion(int solicitudesPorVentanilla, long pausaMs) {
        tareaA = new ProcesoVentanilla("A", contador, solicitudesPorVentanilla,
                pausaMs, eventos::offer);
        tareaB = new ProcesoVentanilla("B", contador, solicitudesPorVentanilla,
                pausaMs, eventos::offer);
        hiloA = new Thread(tareaA, "Ventanilla-A");
        hiloB = new Thread(tareaB, "Ventanilla-B");
    }

    public synchronized void iniciar(Runnable alFinalizar) {
        if (iniciada) throw new IllegalStateException("La sesión ya inició");
        iniciada = true;
        hiloA.start();
        hiloB.start();

        // join() ocurre en el supervisor, nunca en el hilo JavaFX.
        Thread supervisor = new Thread(() -> {
            boolean interrumpido = false;
            while (hiloA.isAlive() || hiloB.isAlive()) {
                try {
                    hiloA.join();
                    hiloB.join();
                } catch (InterruptedException e) {
                    interrumpido = true;
                    detener();
                }
            }
            finalizada = true;
            eventos.offer("Ambas ventanillas han terminado. Total: " + contador.getTotal());
            alFinalizar.run();
            if (interrumpido) Thread.currentThread().interrupt();
        }, "Supervisor-sesion");
        supervisor.start();
    }

    public void detener() {
        if (!iniciada || finalizada) return;
        cancelada = true;
        tareaA.detener();
        tareaB.detener();
        hiloA.interrupt();
        hiloB.interrupt();
        eventos.offer("Cancelación solicitada a ambas ventanillas");
    }

    public String extraerEvento() { return eventos.poll(); }
    public int getTotal() { return contador.getTotal(); }
    public Thread getHiloA() { return hiloA; }
    public Thread getHiloB() { return hiloB; }
    public boolean estaFinalizada() { return finalizada; }
    public boolean fueCancelada() { return cancelada; }
}
