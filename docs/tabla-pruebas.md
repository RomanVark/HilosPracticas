# Tabla de pruebas

| Prueba | Resultado esperado | Resultado obtenido | Evidencia |
|---|---|---|---|
| Dos ventanillas | Ambas trabajan y la interfaz responde | Pendiente de comprobar en la GUI | captura-01.png |
| Contador inseguro (10 vueltas) | Puede perder actualizaciones | Registrar salida de consola | salida-consola.txt |
| synchronized (10 vueltas) | 2000 en cada vuelta | Registrar salida de consola | salida-consola.txt |
| AtomicInteger (10 vueltas) | 2000 en cada vuelta | Registrar salida de consola | salida-consola.txt |
| Estados del hilo | NEW, activo y TERMINATED; experimento TIMED_WAITING | Registrar observaciones | captura-02.png |
| Cancelación | Los hilos terminan sin Thread.stop() | Pendiente de comprobar en GUI | captura-03.png |
| Reinicio | No quedan hilos activos de la sesión previa | Pendiente de comprobar en GUI | captura-03.png |

No fabricar evidencias. Repetir los experimentos y registrar los resultados observados.
