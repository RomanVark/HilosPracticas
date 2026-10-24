module ni.edu.uam.hilospracticas {
    requires javafx.controls;
    requires javafx.fxml;


    opens ni.edu.uam.hilospracticas to javafx.fxml;
    exports ni.edu.uam.hilospracticas;
}