package co.edu.uniquindio.reservasfx.modelo.entidades.alojamiento;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class ServicioSeleccionable {

    private final Servicio servicio;
    private final BooleanProperty seleccionado;

    public ServicioSeleccionable(Servicio servicio, boolean seleccionado) {
        this.servicio = servicio;
        this.seleccionado = new SimpleBooleanProperty(seleccionado);
    }

    public Servicio getServicio() {
        return servicio;
    }

    public BooleanProperty seleccionadoProperty() {
        return seleccionado;
    }

    public boolean isSeleccionado() {
        return seleccionado.get();
    }

    public void setSeleccionado(boolean seleccionado) {
        this.seleccionado.set(seleccionado);
    }
}
