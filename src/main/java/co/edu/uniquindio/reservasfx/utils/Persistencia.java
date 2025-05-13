package co.edu.uniquindio.reservasfx.utils;

import co.edu.uniquindio.reservasfx.config.Constantes;
import co.edu.uniquindio.reservasfx.modelo.entidades.alojamiento.Servicio;
import co.edu.uniquindio.reservasfx.modelo.entidades.usuario.Deseo;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.*;
import java.util.ArrayList;

public class Persistencia {
    /**
     * Metodo que permite serializar un objeto en un archivo en la ruta especificada
     * @param objeto Objeto a serializar
     * @throws IOException
     */
    public static void serializarObjeto(String ruta, Object objeto) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ruta));
        oos.writeObject(objeto);
        oos.close();
    }

    /**
     * Metodo que permite deserializar un objeto de un archivo en la ruta especificada
     * @return Objeto deserializado
     * @throws Exception
     */
    public static Object deserializarObjeto(String ruta) throws Exception{

        if(!new File(ruta).exists()){
            return null;
        }

        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ruta));
        Object objeto = ois.readObject();
        ois.close();

        return objeto;
    }

}
