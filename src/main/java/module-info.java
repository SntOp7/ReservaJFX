module co.edu.uniquindio.reservasfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires org.simplejavamail.core;
    requires org.simplejavamail;
    requires com.google.zxing;
    requires com.google.zxing.javase;
    requires java.desktop;
    requires org.slf4j;

    opens co.edu.uniquindio.reservasfx to javafx.fxml;
    exports co.edu.uniquindio.reservasfx.app;
    exports co.edu.uniquindio.reservasfx.repositorios;
    exports co.edu.uniquindio.reservasfx.servicios.modulo.alojamiento;
    exports co.edu.uniquindio.reservasfx.modelo.enums;
    exports co.edu.uniquindio.reservasfx.modelo.factory;
    exports co.edu.uniquindio.reservasfx.modelo.entidades.alojamiento;
    exports co.edu.uniquindio.reservasfx.modelo.entidades;
    exports co.edu.uniquindio.reservasfx.modelo;
    exports co.edu.uniquindio.reservasfx.servicios;
    exports co.edu.uniquindio.reservasfx.servicios.modulo.comercial;
    exports co.edu.uniquindio.reservasfx.servicios.modulo.usuario;
    exports co.edu.uniquindio.reservasfx.modelo.entidades.reserva;
    opens co.edu.uniquindio.reservasfx.app to javafx.fxml;
    opens co.edu.uniquindio.reservasfx.controladores to javafx.fxml;
}