/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectoedii;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author micha
 */
public class Registro implements Serializable{
    private ArrayList informacion=new ArrayList();
    private int RNN,nextResgistroElim;

    public Registro(int RNN) {
        this.RNN = RNN;
    }
    public Registro() {
        this.RNN=0;
    }

    public ArrayList getInformacion() {
        return informacion;
    }

    public void setInformacion(ArrayList informacion) {
        this.informacion = informacion;
    }

    public int getRNN() {
        return RNN;
    }

    public void setRNN(int RNN) {
        this.RNN = RNN;
    }
     public int getNextResgistroElim() {
        return nextResgistroElim;
    }

    public void setNextResgistroElim(int nextResgistroElim) {
        this.nextResgistroElim = nextResgistroElim;
    }    
    public void addInformacion(Object info){
        this.informacion.add(info);
    }

    @Override
    public String toString() {
        return "Registro{" + "informacion=" + informacion + ", RNN=" + RNN + '}';
    }
    
    
    
    
}
