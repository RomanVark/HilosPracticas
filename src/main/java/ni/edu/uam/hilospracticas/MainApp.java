package ni.edu.uam.hilospracticas;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class MainApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("MainApp.fxml"));
        Scene scene = new Scene(loader.load(), 960, 680);
        MainAppController controlador = loader.getController();
        stage.setTitle("Laboratorio de concurrencia - Dos ventanillas");
        stage.setScene(scene);
        stage.setMinWidth(750);
        stage.setMinHeight(560);
        stage.setOnCloseRequest(event -> controlador.cerrar());
        stage.show();
    }
}
