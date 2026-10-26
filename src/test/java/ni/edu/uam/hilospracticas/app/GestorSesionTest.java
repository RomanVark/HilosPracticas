package ni.edu.uam.hilospracticas.app;

import org.junit.jupiter.api.Test;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import static org.junit.jupiter.api.Assertions.*;

class GestorSesionTest {
    @Test
    void terminaAmbasYPermiteReinicio() throws InterruptedException {
        for (int i = 0; i < 2; i++) {
            CountDownLatch aviso = new CountDownLatch(1);
            GestorSesion sesion = new GestorSesion(1000, 0);
            sesion.iniciar(aviso::countDown);
            assertTrue(aviso.await(5, TimeUnit.SECONDS));
            assertTrue(sesion.estaFinalizada());
            assertEquals(2000, sesion.getTotal());
            assertEquals(Thread.State.TERMINATED, sesion.getHiloA().getState());
            assertEquals(Thread.State.TERMINATED, sesion.getHiloB().getState());
        }
    }

    @Test
    void cancelacionCooperativaFinalizaLosDosHilos() throws InterruptedException {
        CountDownLatch aviso = new CountDownLatch(1);
        GestorSesion sesion = new GestorSesion(1000, 100);
        sesion.iniciar(aviso::countDown);
        Thread.sleep(30);
        sesion.detener();
        assertTrue(aviso.await(5, TimeUnit.SECONDS));
        assertTrue(sesion.fueCancelada());
        assertTrue(sesion.estaFinalizada());
        assertTrue(sesion.getTotal() < 2000);
    }
}
