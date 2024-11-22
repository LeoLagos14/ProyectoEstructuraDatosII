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
public class Campos {
    private String nombre;
    private Object tipo;
    private Object tam;
    private boolean llave_primaria;
    private boolean llave_secundaria;

    public Campos(Object tipo, String nombre, Object tam, boolean llave_primaria, boolean llave_secundaria) {
       this.tipo = tipo; 
       this.nombre = nombre;
       this.tam = tam;
       this.llave_primaria = llave_primaria;
      
    }
     public Campos() {
        this.tipo = null;
        this.nombre = "";
        this.tam = null;
        this.llave_primaria = false;
    }
     public void newCampo(Campos newCampo) {
        this.tipo = newCampo.getTipo();
        this.nombre = newCampo.getNombre();
        this.tam = newCampo.getTam();
        this.llave_primaria = newCampo.isLlavePrimaria();
        this.llave_secundaria = newCampo.isLlaveSecundaria();
    }
      public String getTipoString() {
        return (String)tipo;
    }
       public Object getTipo() {
        return tipo;
    }

    public void setTipo(Object tipo) {
        this.tipo = tipo;
    }

    public boolean isLlaveSecundaria() {
        return llave_secundaria;
    }

    public void setLlaveSecundaria(boolean llaveSecundaria) {
        this.llave_secundaria = llave_secundaria;
    }

    public String getLlave() {
        if (llave_primaria) {
            return "1";
        }
        if (llave_secundaria) {
            return "2";
        }
        return "0";
    }

    public boolean isLlavePrimaria() {
        return llave_primaria;
    }

    public boolean getLlavePrimaria() {
        boolean bandera=true;
        if (llave_primaria) {
          
        }else{
            bandera=false;
        }
        return bandera;
    }

    public String getLlaveSecundaria() {
        if (llave_secundaria) {
            return "1";
        }
        return "0";
    }

    public void setLlavePrimaria(boolean llave) {
        this.llave_primaria = llave;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Object getTam() {
        return tam;
    }

    public void setTam(Object tamanio) {
        this.tam = tamanio;
    }

    @Override
    public String toString() {
        String llave = "No llave";
        if (llave_primaria) {
            llave = "Primaria";
        }
        if (llave_secundaria) {
            llave = "Secundaria";
        }
        return nombre + "  -  " + getTipoString()+ "  -  " + getTam() + "  -  " + llave;
    }
}
