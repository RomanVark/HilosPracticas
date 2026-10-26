package ni.edu.uam.hilospracticas.tareas;

import ni.edu.uam.hilospracticas.modelo.Contador;
import ni.edu.uam.hilospracticas.modelo.ContadorAtomico;
import ni.edu.uam.hilospracticas.modelo.ContadorCompartido;
import ni.edu.uam.hilospracticas.modelo.ContadorInseguro;
import java.util.function.Supplier;

/** join() se utiliza únicamente desde main de consola. */
public final class PruebaContadores {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Repetición | Inseguro | synchronized | AtomicInteger");
        for (int vuelta = 1; vuelta <= 10; vuelta++) {
            int inseguro = ejecutar(ContadorInseguro::new);
            int seguro = ejecutar(ContadorCompartido::new);
            int atomico = ejecutar(ContadorAtomico::new);
            System.out.printf("%10d | %8d | %12d | %13d%n",
                    vuelta, inseguro, seguro, atomico);
            if (seguro != 2000 || atomico != 2000) {
                throw new AssertionError("El contador seguro perdió operaciones");
            }
        }
        System.out.println("Resultado esperado para cada alternativa segura: 2000");
    }

    private static int ejecutar(Supplier<Contador> fabrica) throws InterruptedException {
        Contador contador = fabrica.get();
        Runnable trabajo = () -> {
            for (int i = 0; i < 1000; i++) contador.incrementar();
        };
        Thread a = new Thread(trabajo, "Prueba-A");
        Thread b = new Thread(trabajo, "Prueba-B");
        a.start();
        b.start();
        a.join();
        b.join();
        return contador.getTotal();
    }
}
