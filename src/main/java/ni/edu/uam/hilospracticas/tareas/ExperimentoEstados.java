package ni.edu.uam.hilospracticas.tareas;

/** Demuestra NEW, TIMED_WAITING y TERMINATED desde la consola. */
public final class ExperimentoEstados {
    public static void main(String[] args) throws InterruptedException {
        Thread trabajador = new Thread(() -> {
            try {
                Thread.sleep(700); // En ese intervalo: TIMED_WAITING.
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "Hilo-observado");
        System.out.println("Antes: " + trabajador.getState());
        trabajador.start();
        while (trabajador.isAlive()) {
            System.out.println("Durante: " + trabajador.getState());
            Thread.sleep(100); // Esta clase corre en consola, no en JavaFX.
        }
        trabajador.join();
        System.out.println("Después: " + trabajador.getState());
    }
}
