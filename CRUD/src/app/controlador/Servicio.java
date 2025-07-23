package app.controlador;

import app.modelo.Crud;
import app.modelo.ImplCrud;
import app.modelo.Producto;

import javax.swing.*;
import java.util.Map;

public class Servicio {
    private Crud implementacion = new ImplCrud();

    public Map<Integer, Producto> seleccionarTodo() {
        return implementacion.seleccinarTodo();
    }

    public void insertar(Producto producto) {
        implementacion.insertar(producto);
    }

    public void actualizar(Producto producto) {
        implementacion.actualizar(producto);
    }

    public void eliminar(int id) {
        if (JOptionPane.showConfirmDialog(null, "Está seguro que desea eliminar registro?",
                "WARNING", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            implementacion.eliminar(id);
        }
    }
}