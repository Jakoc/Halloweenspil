module com.example.halloweenspil {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.halloweenspil to javafx.fxml;
    exports com.example.halloweenspil;
}