package ni.edu.uam.hilospracticas.tareas;

import ni.edu.uam.hilospracticas.modelo.ContadorCompartido;

/** Ejecutar como clase Java normal; NO dentro de un botón JavaFX. */
public final class ComparacionRunStart {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("run() directo: bloquea el hilo actual y no crea otro");
        var contador1 = new ContadorCompartido();
        new ProcesoVentanilla("A", contador1, 5, 500, System.out::println).run();
        new ProcesoVentanilla("B", contador1, 5, 500, System.out::println).run();
        System.out.println("Total run(): " + contador1.getTotal());

        System.out.println("start(): crea dos hilos reales (repetir tres veces)");
        var contador2 = new ContadorCompartido();
        Thread a = new Thread(new ProcesoVentanilla("A", contador2, 5, 500,
                System.out::println), "Ventanilla-A");
        Thread b = new Thread(new ProcesoVentanilla("B", contador2, 5, 500,
                System.out::println), "Ventanilla-B");
        System.out.println("Antes: " + a.getState() + " / " + b.getState());
        a.start();
        b.start();
        System.out.println("El hilo main continúa sin esperar a las ventanillas");
        a.join(); // Aquí está permitido: no estamos en JavaFX.
        b.join();
        System.out.println("Total start(): " + contador2.getTotal());
    }
}
