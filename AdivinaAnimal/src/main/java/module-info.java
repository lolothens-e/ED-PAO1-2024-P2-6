module com.mycompany.adivinaanimal {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.mycompany.adivinaanimal to javafx.fxml;
    exports com.mycompany.adivinaanimal;
}
