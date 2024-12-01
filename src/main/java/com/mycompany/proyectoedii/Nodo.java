package com.mycompany.proyectoedii;

import java.io.Serializable;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author micha
 */
public class Nodo implements Serializable{
    private static final long serialVersionUID = 766;
     public int mNumKeys = 0;
    public int[] mKeys = new int[2 * 4 - 1];
    public Registro[] mRegistros = new Registro[2 * 4 - 1];
    public Nodo[] mNodosHijos = new Nodo[2 * 4];
    public boolean isHoja;
    
    public Registro[] getKeys(){
        return mRegistros;
    }
    
    public Nodo[] getChildren(){
        return mNodosHijos;
    }

    int binarySearch(int key) {
        int leftIndex = 0;
        int rightIndex = mNumKeys - 1;

        while (leftIndex <= rightIndex) {
            final int middleIndex = leftIndex + ((rightIndex - leftIndex) / 2);
            if (mKeys[middleIndex] < key) {
                leftIndex = middleIndex + 1;
            } else if (mKeys[middleIndex] > key) {
                rightIndex = middleIndex - 1;
            } else {
                return middleIndex;
            }
        }

        return -1;
    }
     void remove(int index, int leftOrRightChild) {
        if (index >= 0) {
            int i;
            for (i = index; i < mNumKeys - 1; i++) {
                mKeys[i] = mKeys[i + 1];
                mRegistros[i] = mRegistros[i + 1];
                if (!isHoja) {
                    if (i >= index + leftOrRightChild) {
                        mNodosHijos[i] = mNodosHijos[i + 1];
                    }
                }
            }
            mKeys[i] = 0;
            mRegistros[i] = null;
            if (!isHoja) {
                if (i >= index + leftOrRightChild) {
                    mNodosHijos[i] = mNodosHijos[i + 1];
                }
                mNodosHijos[i + 1] = null;
            }
            mNumKeys--;
        }
    }
void shiftRightByOne() {
        if (!isHoja) {
            mNodosHijos[mNumKeys + 1] = mNodosHijos[mNumKeys];
        }
        for (int i = mNumKeys - 1; i >= 0; i--) {
            mKeys[i + 1] = mKeys[i];
            mRegistros[i + 1] = mRegistros[i];
            if (!isHoja) {
                mNodosHijos[i + 1] = mNodosHijos[i];
            }
        }
    }

    int subtreeRootNodeIndex(int key) {
        for (int i = 0; i < mNumKeys; i++) {
            if (key < mKeys[i]) {
                return i;
            }
        }
        return mNumKeys;
    }

    
}
