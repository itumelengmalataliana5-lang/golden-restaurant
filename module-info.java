module com.example.rastuarent {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.rastuarent to javafx.fxml;
    exports com.example.rastuarent;
}