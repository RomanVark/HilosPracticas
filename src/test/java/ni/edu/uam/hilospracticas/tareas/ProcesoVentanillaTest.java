package ni.edu.uam.hilospracticas.tareas;

import ni.edu.uam.hilospracticas.modelo.ContadorCompartido;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProcesoVentanillaTest {
    @Test
    void detieneElHiloInterrumpido() throws InterruptedException {
        var contador = new ContadorCompartido();
        var tarea = new ProcesoVentanilla("A", contador, 1000, 100, s -> {});
        Thread hilo = new Thread(tarea);
        hilo.start();
        Thread.sleep(30);
        tarea.detener();
        hilo.interrupt();
        hilo.join(2000);
        assertFalse(hilo.isAlive());
        assertTrue(contador.getTotal() < 1000);
    }
}
