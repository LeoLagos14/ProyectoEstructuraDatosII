/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectoedii;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 *
 * @author micha
 */
public class AdminArbol implements Serializable{
    private static final long serialVersionUID = 1L;
    private File archivo = null;

    public AdminArbol(String path) {
        archivo = new File(path);
    }

    public File getArchivo() {
        return archivo;
    }

    public void setArchivo(File archivo) {
        this.archivo = archivo;
    }

    public Arbol cargarArchivo() {
        Arbol arbolb = null;
        try {            
            if (archivo.exists()) {
                FileInputStream entrada = new FileInputStream(archivo.getName());
                ObjectInputStream objeto  = new ObjectInputStream(entrada);
                try {
                    arbolb = (Arbol)objeto.readObject();
                } catch (Exception e) {
                    System.out.println("dd");
                }
                objeto.close();
                entrada.close();
            }            
        } catch (Exception ex) {
            System.out.println("Error en cargarArchivo bin");
            ex.printStackTrace();
        }
        return arbolb;
    }

    public void escribirArchivo(Arbol arbolb) {
        FileOutputStream fw = null;
        ObjectOutputStream bw = null;
        try {
            fw = new FileOutputStream(archivo.getName());
            bw = new ObjectOutputStream(fw);
            bw.writeObject(arbolb);
            bw.flush();
        } catch (Exception ex) {
            System.out.println("Error en eescribirArchivo bin");
        } finally {
            try {
                bw.close();
                fw.close();
            } catch (Exception ex) {
                 System.out.println("Error en escribirArchivo bin");
            }
        }
    }

}
