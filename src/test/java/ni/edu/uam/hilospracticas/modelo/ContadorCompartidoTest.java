package ni.edu.uam.hilospracticas.modelo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ContadorCompartidoTest {
    @Test
    void noPierdeIncrementosEnDiezRepeticiones() throws InterruptedException {
        for (int repeticion = 0; repeticion < 10; repeticion++) {
            Contador contador = new ContadorCompartido();
            Runnable aumentar = () -> {
                for (int i = 0; i < 1000; i++) contador.incrementar();
            };
            Thread a = new Thread(aumentar);
            Thread b = new Thread(aumentar);
            a.start(); b.start();
            a.join(); b.join();
            assertEquals(2000, contador.getTotal());
        }
    }

    @Test
    void atomicIntegerNoPierdeIncrementos() throws InterruptedException {
        Contador contador = new ContadorAtomico();
        Thread a = new Thread(() -> { for (int i=0;i<1000;i++) contador.incrementar(); });
        Thread b = new Thread(() -> { for (int i=0;i<1000;i++) contador.incrementar(); });
        a.start(); b.start(); a.join(); b.join();
        assertEquals(2000, contador.getTotal());
    }
}
