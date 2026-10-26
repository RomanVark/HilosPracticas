# HilosPracticas — Laboratorio guiado de concurrencia

Proyecto JavaFX + Maven con Java 21.

## Entorno
- Eclipse Temurin JDK 21.0.12 o Java 21 compatible
- Maven Wrapper incluido en el repositorio
- JavaFX 21.0.6 administrado por Maven
- Scene Builder para editar `src/main/resources/ni/edu/uam/hilospracticas/MainApp.fxml`

## Ejecución (Windows PowerShell)
```powershell
java -version
.\mvnw.cmd clean test
.\mvnw.cmd javafx:run
```

## Consola
Ejecutar desde IntelliJ como aplicaciones Java independientes:
- `ni.edu.uam.hilospracticas.tareas.ComparacionRunStart`
- `ni.edu.uam.hilospracticas.tareas.ExperimentoEstados`
- `ni.edu.uam.hilospracticas.tareas.PruebaContadores`

## Arquitectura
- `modelo`: contador inseguro, sincronizado y atómico
- `tareas`: Runnable, comparación run/start, estados y condición de carrera
- `app`: monitor de estados y gestor de sesión
- `MainApp`, `MainAppController`, `MainApp.fxml`: aplicación gráfica

Iniciar → dos tareas → contador sincronizado → actualización gráfica cada 120 ms → Detener por interrupt/volatile → supervisor comprueba fin de ambos hilos → nuevo inicio.

## Entrega
Documentar integrantes, decisiones de diseño, diez ejecuciones de contadores, tres capturas reales, tabla de pruebas, ramas y Pull Requests. Las capturas y resultados se generan al ejecutar la aplicación en el equipo.
