# Informe breve — Concurrencia JavaFX (máximo una página)

**Integrantes y roles:** completar. **JDK:** Temurin 21.0.12. **Herramientas:** Maven, JavaFX y Scene Builder.

1. **Error previo a sincronizar.** El incremento `total++` no es atómico: dos hilos pueden leer un valor y escribir resultados que pierden actualizaciones. Agregar aquí los diez resultados reales de `PruebaContadores`.
2. **JavaFX responsivo.** Los `Runnable` se ejecutan con `Thread.start()`; `join()` solo ocurre en un supervisor. Un `Timeline` actualiza la interfaz en lotes cada 120 ms.
3. **Detención y reinicio.** Cada tarea combina bandera `volatile` con `interrupt()` y respeta `InterruptedException`. El botón Iniciar se rehabilita al confirmarse la finalización de las dos tareas.
4. **Mejoras para 100 000 solicitudes.** Usar lotes de procesamiento, un `ExecutorService`, control de cola y registros agregados para limitar memoria y trabajo de interfaz.

**Conclusiones y evidencias:** completar con pruebas reales antes de entregar.
