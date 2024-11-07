/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.proyectoedii;

/**
 *
 * @author clago
 */
public class Archivos {
    private String path, nombreArchivo;
    
    public Archivos(String nombreArchivo, String path){
        this.nombreArchivo = nombreArchivo;
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }
    
}
