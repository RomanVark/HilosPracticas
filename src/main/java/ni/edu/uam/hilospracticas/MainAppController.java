package ni.edu.uam.hilospracticas;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.util.Duration;
import ni.edu.uam.hilospracticas.app.GestorSesion;
import ni.edu.uam.hilospracticas.app.MonitorEstados;

public class MainAppController {
    @FXML private Button btnIniciar;
    @FXML private Button btnDetener;
    @FXML private Label etiquetaEstado;
    @FXML private Label etiquetaContador;
    @FXML private Label etiquetaHiloA;
    @FXML private Label etiquetaHiloB;
    @FXML private TextField campoOperaciones;
    @FXML private TextField campoPausa;
    @FXML private TextArea areaRegistro;

    private GestorSesion sesion;
    private MonitorEstados monitor;
    private Timeline actualizador;

    @FXML
    private void initialize() {
        etiquetaEstado.setText("En espera");
        etiquetaContador.setText("0");
        etiquetaHiloA.setText("NEW (sin sesión)");
        etiquetaHiloB.setText("NEW (sin sesión)");
        btnDetener.setDisable(true);
        actualizador = new Timeline(new KeyFrame(Duration.millis(120), e -> refrescar()));
        actualizador.setCycleCount(Timeline.INDEFINITE);
        actualizador.play();
        agregarRegistro("Interfaz lista. Las tareas usarán hilos independientes.");
    }

    @FXML
    private void onIniciar() {
        if (sesion != null && !sesion.estaFinalizada()) return;
        int operaciones;
        int pausa;
        try {
            operaciones = Integer.parseInt(campoOperaciones.getText().trim());
            pausa = Integer.parseInt(campoPausa.getText().trim());
            if (operaciones < 1 || operaciones > 100_000 || pausa < 0 || pausa > 1_000) {
                throw new NumberFormatException("Fuera de rango");
            }
        } catch (NumberFormatException e) {
            agregarRegistro("Valores inválidos: operaciones 1-100000, pausa 0-1000 ms.");
            return;
        }

        GestorSesion nueva = new GestorSesion(operaciones, pausa);
        sesion = nueva;
        monitor = new MonitorEstados();
        areaRegistro.clear();
        etiquetaContador.setText("0");
        etiquetaEstado.setText("Ejecutando");
        btnIniciar.setDisable(true);
        btnDetener.setDisable(false);
        agregarRegistro("ANTES de start(): " + nueva.getHiloA().getState()
                + " / " + nueva.getHiloB().getState());
        monitor.observar(nueva.getHiloA(), nueva.getHiloB())
               .forEach(this::agregarRegistro);

        nueva.iniciar(() -> Platform.runLater(() -> {
            if (sesion != nueva) return;
            refrescar();
            etiquetaEstado.setText(nueva.fueCancelada() ? "Detenido" : "Finalizado");
            btnIniciar.setDisable(false);
            btnDetener.setDisable(true);
            agregarRegistro("Es seguro iniciar otra sesión; ambos hilos terminaron.");
        }));
        refrescar();
    }

    @FXML
    private void onDetener() {
        if (sesion == null || sesion.estaFinalizada()) return;
        etiquetaEstado.setText("Deteniendo");
        btnDetener.setDisable(true);
        sesion.detener();
    }

    private void refrescar() {
        if (sesion == null) return;
        etiquetaContador.setText(Integer.toString(sesion.getTotal()));
        etiquetaHiloA.setText(sesion.getHiloA().getState().name());
        etiquetaHiloB.setText(sesion.getHiloB().getState().name());
        if (monitor != null) {
            monitor.observar(sesion.getHiloA(), sesion.getHiloB())
                    .forEach(this::agregarRegistro);
        }
        StringBuilder lote = new StringBuilder();
        String mensaje;
        int procesados = 0;
        while (procesados < 100 && (mensaje = sesion.extraerEvento()) != null) {
            lote.append(mensaje).append('\n');
            procesados++;
        }
        if (!lote.isEmpty()) agregarRegistro(lote.toString().stripTrailing());
    }

    private void agregarRegistro(String texto) {
        if (areaRegistro.getLength() > 14_000) {
            areaRegistro.deleteText(0, areaRegistro.getLength() - 9_000);
        }
        areaRegistro.appendText(texto + System.lineSeparator());
    }

    public void cerrar() {
        if (actualizador != null) actualizador.stop();
        if (sesion != null && !sesion.estaFinalizada()) sesion.detener();
    }
}
