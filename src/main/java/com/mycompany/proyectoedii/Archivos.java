/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.proyectoedii;

import java.util.LinkedList;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
/**
 *
 * @author clago
 */
public class Archivos {
    private String path, nombreArchivo;
    private LinkedList<Nodo>listaEnlazada= new LinkedList<>();
    private LinkedList<Campos>campos;
    
    public Archivos(String nombreArchivo, String path){
        this.nombreArchivo = nombreArchivo;
        this.path = path;
        campos = new LinkedList<>();
    }
    public void modificarCampo(Campos campoM, int campo_Seleccionado) {
        if (campo_Seleccionado >= 0 && campo_Seleccionado < campos.size()) {
            campos.set(campo_Seleccionado, campoM);
            System.out.println("campo modificado en la posición " + campo_Seleccionado);
        } else {
            System.out.println("Índice fuera de rango. No se puede modificar.");
        }
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

    public LinkedList<Nodo> getListaEnlazada() {
        return listaEnlazada;
    }

    public void setListaEnlazada(LinkedList<Nodo> listaEnlazada) {
        this.listaEnlazada = listaEnlazada;
    }

    public LinkedList<Campos> getCampos() {
        return campos;
    }

    public void setCampos(LinkedList<Campos> campos) {
        this.campos = campos;
    }
     public void setCamposOpen(String linea) {
        if (linea != null) {
            try {
                String[] camposToken = linea.split("\\/");
                for (int i = 0; i < camposToken.length; i++) {
                    Campos campoAux = new Campos();
                    String[] datos = camposToken[i].split("!");

                    campoAux.setNombre(datos[0]);

                    if (datos[1].equals("Cadena")) {
                        campoAux.setTipo("");
                    }

                    if (datos[1].equals("Entero")) {
                        campoAux.setTipo(0);
                    }

                    if (datos[1].equals("Double")) {
                        campoAux.setTipo(0.0);
                    }

                    if (datos[1].equals("Booleano")) {
                        campoAux.setTipo(true);
                    }

                    try {
                        // Intenta convertir la cadena a un número decimal
                        double numero = Double.parseDouble(datos[2]);

                        // Verifica si el número es un entero o no
                        if (numero % 1 == 0) {
                            campoAux.setTam(Integer.valueOf(datos[2]));
                        } else {
                            campoAux.setTam(numero);
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Error convirtiendo el tamanio a int o double");
                    }

                    campoAux.setLlavePrimaria(false);
                    campoAux.setLlaveSecundaria(false);
                    if (datos[3].equals("1")) {
                        campoAux.setLlavePrimaria(true);
                    }
                    if (datos[3].equals("2")) {
                        campoAux.setLlaveSecundaria(true);
                    }

                    campos.add(campoAux);
                    System.out.println("FUNCIONA");
                }
            } catch (Exception e) {
                System.out.println("ERROR EN ABRIR LOS CAMPOS");
            }
        }
    }
       public String getCamposClose() {
        StringBuilder linea = new StringBuilder();

        try {
            for (Campos campo : campos) {
                linea.append(campo.getNombre()).append("!");
                linea.append(campo.getTipoString()).append("!");
                linea.append(campo.getTam()).append("!");
                linea.append(campo.getLlave()).append("/");
            }
        } catch (Exception e) {
            System.out.println("ERROR AL OBTENER LOS CAMPOS ");
        }
        return linea.toString();
    }
       public void escribirCamposEnArchivo(String nombreArchivo) {
//        try (FileWriter writer = new FileWriter(nombreArchivo)) {
//            for (Campos campo : campos) {
//                writer.write(campo.getNombre() + "!");
//                writer.write(campo.getTipoString() + "!");
//                writer.write(campo.getTam() + "!");
//                writer.write(campo.getLlave() + "/");
//            }
//        } catch (IOException e) {
//            System.out.println("ERROR AL ESCRIBIR EN EL ARCHIVO");
//        }
            try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {
        // Lee la primera línea del archivo
        String primeraLinea = br.readLine();
        
        // Extrae los primeros dos elementos (separados por "|")
        String[] partesIniciales = primeraLinea.split("\\|", 3);
        
        // Si la línea contiene al menos tres partes, continúa
        if (partesIniciales.length >= 2) {
            // Construye el contenido inicial de la línea a escribir
            StringBuilder linea = new StringBuilder();
            linea.append(partesIniciales[0]).append("|").append(partesIniciales[1]).append("|");
            
            // Añade los campos actuales de la lista `campos` en el formato requerido
            for (Campos campo : campos) {
                linea.append(campo.getNombre()).append("!");
                linea.append(campo.getTipoString()).append("!");
                linea.append(campo.getTam()).append("!");
                linea.append(campo.getLlave()).append("/");
            }
            
            // Escribe la línea completa en el archivo
            try (FileWriter writer = new FileWriter(nombreArchivo)) {
                writer.write(linea.toString());
            }
        } else {
            System.out.println("La primera línea no tiene el formato esperado.");
        }
    } catch (IOException e) {
        System.out.println("ERROR AL ESCRIBIR EN EL ARCHIVO");
    }
    }

    
    
}
