package ni.edu.uam.hilospracticas.modelo;

import java.util.concurrent.atomic.AtomicInteger;

public final class ContadorAtomico implements Contador {
    private final AtomicInteger total = new AtomicInteger();

    @Override
    public void incrementar() {
        total.incrementAndGet();
    }

    @Override
    public int getTotal() {
        return total.get();
    }
}
