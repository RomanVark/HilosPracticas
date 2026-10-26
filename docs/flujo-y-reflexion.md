# Flujo del laboratorio y preguntas de análisis

```text
Botón Iniciar (JavaFX Application Thread)
  ├─ validar parámetros y deshabilitar Iniciar
  ├─ preparar dos Runnable y dos Thread
  ├─ llamar start() para A y B
  ├─ Timeline visual (cada 120 ms): estados, contador y registros
  └─ Botón Detener: bandera volatile + interrupt()
        └─ Supervisor fuera de JavaFX realiza join()
              └─ Platform.runLater(): Detenido/Finalizado, habilitar Iniciar
```

## Análisis técnico
- `run()` ejecuta en el hilo que lo invoca; `start()` inicia un hilo independiente y regresa sin esperar la ejecución.
- `RUNNABLE` no garantiza que el hilo se encuentre físicamente ejecutándose. `getState()` solo observa una instantánea.
- `TIMED_WAITING` puede verse mientras un hilo duerme con `Thread.sleep()`. `BLOCKED` y `WAITING` no tienen por qué aparecer.
- `synchronized` evita carreras al incrementar y leer el contador; no autoriza modificar controles JavaFX fuera de JavaFX Application Thread.
- Una interrupción es una solicitud cooperativa, no una garantía de que el hilo terminó; el supervisor espera su terminación mediante `join()`.

## Colaboración GitHub
La práctica exige cinco ramas (`main` y cuatro ramas por integrante), roles verificables y Pull Requests revisados. La rama `Roman` es una rama de trabajo individual y no sustituye por sí sola las evidencias de los otros tres integrantes.
