module com.example.cosc461project2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.cosc461project2 to javafx.fxml;
    exports com.example.cosc461project2;
}