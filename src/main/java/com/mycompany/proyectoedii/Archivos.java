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
    private int cantidadRegistros;//
    
    private LinkedList<Registro> availist;
    private LinkedList<Campos>campos;

    public void setAvailist(LinkedList<Registro> availist) {
        this.availist = availist;
    }
    public int getCantidadRegistros() {
        return cantidadRegistros;
    }

    public void setCantidadRegistros(int cantidadRegistros) {
        this.cantidadRegistros = cantidadRegistros;
    }

    public void addCantRegs() {
        this.cantidadRegistros++;
    }
    public void subCantRegs(){
        this.cantidadRegistros--;
    }
    
    
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
    public void eliminarRegistro(Registro registroElim) {
            if (registroElim != null) {
                if (!availist.isEmpty()) {
                    availist.getLast().setNextResgistroElim(registroElim.getRNN());
                }
                registroElim.setNextResgistroElim(-1);
                availist.add(registroElim);
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

    

    public LinkedList<Campos> getCampos() {
        return campos;
    }
    public LinkedList getAvailist() {
        return availist;
    }

    public void setCampos(LinkedList<Campos> campos) {
        this.campos = campos;
    }
    public int getLlavePrimariaIndex() {
        int index = -1;
        for (Campos campo : campos) {
            if (campo.getLlavePrimaria() == 1) {
                index = campos.indexOf(campo);
            }else{
            }
        }
        return index;
    }

    public int getLlaveSecundariaIndex() {
        int index = -1;
        for (Campos campo : campos) {
            if ("1".equals(campo.getLlaveSecundaria())) {
                index = campos.indexOf(campo);
            }
        }
        return index;
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
                        campoAux.setTipo("Cadena");
                    }

                    if (datos[1].equals("Entero")) {
                        campoAux.setTipo("Entero");
                    }

                    if (datos[1].equals("Double")) {
                        campoAux.setTipo("Double");
                    }

                    if (datos[1].equals("Booleano")) {
                        campoAux.setTipo("Booleano");
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
           //prubas sin formato esperado
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
            //leer primera linea
            String primeraLinea = br.readLine();
                System.out.println("hasta q=awdwe");
            String[] partesIniciales = primeraLinea.split("\\|", 3);

            if (partesIniciales.length >= 2) {
                StringBuilder linea = new StringBuilder();
                linea.append(partesIniciales[0]).append("|").append(partesIniciales[1]).append("|");
                System.out.println("ncsndcjkdsn");
                // Añade los campos actuales de la lista `campos` en el formato requerido
                for (Campos campo : campos) {
                    linea.append(campo.getNombre()).append("!");
                    linea.append(campo.getTipoString()).append("!");
                    linea.append(campo.getTam()).append("!");
                    linea.append(campo.getLlave()).append("/");
                }
                System.out.println("Aqui3");
                // Escribe la línea completa en el archivo
                try (FileWriter writer = new FileWriter(nombreArchivo)) {
                    writer.write(linea.toString());
                }
            } else {
                System.out.println("AQEUI");
            }
        } catch (IOException e) {
            System.out.println("ERROR AL ESCRIBIR EN EL ARCHIVO");
        }
    }
       public String getRegistroClose(Registro registro) {
        String registroString = "";
        int num = 0;
        Campos campoAux = new Campos();
        for (Object info : registro.getInformacion()) {
            registroString += info.toString();
            campoAux = campos.get(registro.getInformacion().indexOf(info));
            if (info instanceof String) {
                System.out.println("1T");
                num = (int) campoAux.getTam()- info.toString().length();
            }
            if (info instanceof Integer) {
                System.out.println("2T");
                num = 9 - info.toString().length();
            }
            if (info instanceof Double) {
                System.out.println("3T");
                num = 11 - info.toString().length();
            }
            if (info instanceof Boolean) {
                System.out.println("4T");
                num = 5 - info.toString().length();
            }
            for (int i = 0; i < num; i++) {
                registroString += '_';
            }
            registroString += '/';
        }
        return registroString;
    }
public void setAvailistOpen(String posicion) {

        int posEliminado = 0;
        if (!posicion.equalsIgnoreCase("-1")) {
            try {
                posEliminado = Integer.parseInt(posicion);
                Registro registroTempo = buscarRegistro(posEliminado);
                    while (posEliminado != -1 ) {
                        //System.out.println(registroTempo);
                        availist.add(registroTempo);
                        posEliminado = registroTempo.getNextResgistroElim();
                        //System.out.println(registroTempo);
                        registroTempo = buscarRegistro(posEliminado);
                    }
                    System.out.println("ya");
            } catch (Exception e) {
                System.out.println("Error al convertir string a entero en setAvailistOpen");
                e.printStackTrace();
            }
        }
    }
    public Registro buscarRegistro(int posicion) {
            //areglar aqui
            if (posicion > cantidadRegistros || posicion < 1) {
                return null;
            }

            Registro registroReturn = new Registro();
            registroReturn.setRNN(posicion);
            File archivo = new File(this.path);

            try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
                String linea;
                int numeroLinea = 0;
                String dato = "";

                while ((linea = br.readLine()) != null) {
                    numeroLinea++;
                    if (numeroLinea == posicion + 1) {
                        Iterator<Campos> iterator = campos.iterator();
                        String[] registroToken = linea.split("\\/");
                        for (String datoAux : registroToken) {
                            if (!iterator.hasNext()) {
                                break;
                            }
                            Campos campo = iterator.next();

                            dato = "";
                            for (int j = 0; j < datoAux.length(); j++) {
                                if (datoAux.charAt(j) == '_') {
                                    break;
                                } else {
                                    dato += datoAux.charAt(j);
                                }
                            }

                            if (dato.charAt(0) == '*') {//registro eliminado
                                int posNextRegistro = 0;
                                String aux = "";
                                for (int i = 1; i < dato.length(); i++) {
                                    if (dato.charAt(i) != '+') {
                                        aux += dato.charAt(i);
                                    } else {
                                        break;
                                    }
                                }
                                try {
                                    posNextRegistro = Integer.parseInt(aux);
                                    registroReturn.setNextResgistroElim(posNextRegistro);
                                    registroReturn.addInformacion(dato);
                                } catch (Exception e) {
                                    System.out.println("ERROR EN REGISTRO ELIMINADO en buscarRegistro()");
                                }
                            } else {
                                if (campo.getTipo() instanceof String) {
                                    registroReturn.addInformacion(dato);
                                } else if (campo.getTipo() instanceof Integer) {
                                    try {
                                        int datoTemp = Integer.parseInt(dato);
                                        registroReturn.addInformacion(datoTemp);
                                    } catch (Exception e) {
                                        System.out.println("ERROR AL CONVERTIR STRING A ENTERO en buscarRegistro()");
                                    }
                                } else if (campo.getTipo() instanceof Double) {
                                    try {
                                        double datoTemp = Double.parseDouble(dato);
                                        registroReturn.addInformacion(datoTemp);
                                    } catch (Exception e) {
                                        System.out.println("ERROR AL CONVERTIR STRING A DOUBLE en buscarRegistro()");
                                    }
                                } else if (campo.getTipo() instanceof Boolean) {
                                    boolean datoTemp = true;
                                    if (dato.equalsIgnoreCase("false")) {
                                        datoTemp = false;
                                    }
                                    registroReturn.addInformacion(datoTemp);
                                }
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return registroReturn;
        }

}
