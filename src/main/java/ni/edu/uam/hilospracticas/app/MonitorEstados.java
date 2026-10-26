package ni.edu.uam.hilospracticas.app;

import java.util.ArrayList;
import java.util.List;

/** Muestra únicamente transiciones observadas, sin bloquear la interfaz. */
public final class MonitorEstados {
    private Thread.State ultimoA;
    private Thread.State ultimoB;

    public List<String> observar(Thread hiloA, Thread hiloB) {
        List<String> cambios = new ArrayList<>();
        Thread.State nuevoA = hiloA.getState();
        Thread.State nuevoB = hiloB.getState();
        if (nuevoA != ultimoA) {
            cambios.add(hiloA.getName() + ": " + nuevoA);
            ultimoA = nuevoA;
        }
        if (nuevoB != ultimoB) {
            cambios.add(hiloB.getName() + ": " + nuevoB);
            ultimoB = nuevoB;
        }
        return cambios;
    }
}
