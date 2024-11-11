

package com.mycompany.proyectoedii;
import java.io.*;
import java.util.LinkedList;

public class Metadata implements Serializable {
    private static final long serialVersionUID = 1L;

    private String autor;
    private String fechaCreacion;
    private String ultimaModificacion;
    private LinkedList<Nodo> availList; // Para almacenar el availList

    // Constructor para la metadata
    public Metadata(String autor, LinkedList<Nodo> availList) {
        this.autor = autor;
        this.fechaCreacion = new java.util.Date().toString();
        this.ultimaModificacion = new java.util.Date().toString();
        this.availList = availList;
    }

    // Métodos para acceder a los datos de la metadata
    public String getAutor() {
        return autor;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public String getUltimaModificacion() {
        return ultimaModificacion;
    }

    public LinkedList<Nodo> getAvailList() {
        return availList;
    }


    // Guardar la metadata (que contiene el availList) en un archivo
    public void guardarMetadata(String archivo) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(archivo))) {
            out.writeObject(this); // Guarda el objeto Metadata completo, incluyendo el availList
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Leer la metadata desde un archivo
    public static Metadata leerMetadata(String archivo) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(archivo))) {
            return (Metadata) in.readObject(); // Devuelve la metadata leída (con el availList)
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
