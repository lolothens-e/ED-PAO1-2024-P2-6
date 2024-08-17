module com.mycompany.adivinaanimal {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;

    opens com.mycompany.grupo_06_adivina to javafx.fxml;
    exports com.mycompany.grupo_06_adivina;
}
