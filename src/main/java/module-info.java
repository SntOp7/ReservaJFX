module co.edu.uniquindio.reservasfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires org.simplejavamail.core;
    requires org.simplejavamail;
    requires com.google.zxing;
    requires com.google.zxing.javase;

    opens co.edu.uniquindio.reservasfx to javafx.fxml;
    exports co.edu.uniquindio.reservasfx.app;
    opens co.edu.uniquindio.reservasfx.app to javafx.fxml;
    opens co.edu.uniquindio.reservasfx.controladores to javafx.fxml;
}