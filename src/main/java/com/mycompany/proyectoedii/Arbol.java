/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectoedii;

import java.io.Serializable;

/**
 *
 * @author micha
 */
public class Arbol implements Serializable{
    private int index;
    private static final int T = 4;
    private Nodo mNodoRaiz;
    private static final int LEFT_CHILD_NODE = 0;
    private static final int RIGHT_CHILD_NODE = 1;

    public Arbol() {
        mNodoRaiz = new Nodo();
        mNodoRaiz.isHoja = true;
    }

    public void B_Insert(int key, Registro registro) {
        Nodo rootNode = mNodoRaiz;
        if (!update(mNodoRaiz, key, registro)) {
            if (rootNode.mNumKeys == (2 * T - 1)) {
                Nodo newRootNode = new Nodo();
                mNodoRaiz = newRootNode;
                newRootNode.isHoja = false;
                mNodoRaiz.mNodosHijos[0] = rootNode;
                B_Split(newRootNode, 0, rootNode); 
                B_InsertNonfull(newRootNode, key, registro); 
            } else {
                B_InsertNonfull(rootNode, key, registro); 
            }
        }
    }
     void B_Split(Nodo parentNode, int i, Nodo node) {
        Nodo newNode = new Nodo();
        newNode.isHoja = node.isHoja;
        newNode.mNumKeys = T - 1;
        for (int j = 0; j < T - 1; j++) { 
            newNode.mKeys[j] = node.mKeys[j + T];
            newNode.mRegistros[j] = node.mRegistros[j + T];
        }
        if (!newNode.isHoja) {
            for (int j = 0; j < T; j++) {
                newNode.mNodosHijos[j] = node.mNodosHijos[j + T];
            }
            for (int j = T; j <= node.mNumKeys; j++) {
                node.mNodosHijos[j] = null;
            }
        }
        for (int j = T; j < node.mNumKeys; j++) {
            node.mKeys[j] = 0;
            node.mRegistros[j] = null;
        }
        node.mNumKeys = T - 1;

        for (int j = parentNode.mNumKeys; j >= i + 1; j--) {
            parentNode.mNodosHijos[j + 1] = parentNode.mNodosHijos[j];
        }
        parentNode.mNodosHijos[i + 1] = newNode;
        for (int j = parentNode.mNumKeys - 1; j >= i; j--) {
            parentNode.mKeys[j + 1] = parentNode.mKeys[j];
            parentNode.mRegistros[j + 1] = parentNode.mRegistros[j];
        }
        parentNode.mKeys[i] = node.mKeys[T - 1];
        parentNode.mRegistros[i] = node.mRegistros[T - 1];
        node.mKeys[T - 1] = 0;
        node.mRegistros[T - 1] = null;
        parentNode.mNumKeys++;
    }

    void B_InsertNonfull(Nodo node, int key, Registro registro) {
        int i = node.mNumKeys - 1;
        if (node.isHoja) {
            // Since node is not a full node insert the new element into its proper place within node.
            while (i >= 0 && key < node.mKeys[i]) {
                node.mKeys[i + 1] = node.mKeys[i];
                node.mRegistros[i + 1] = node.mRegistros[i];
                i--;
            }
            i++;
            node.mKeys[i] = key;
            node.mRegistros[i] = registro;
            node.mNumKeys++;
        } else {
            while (i >= 0 && key < node.mKeys[i]) {
                i--;
            }
            i++;
            if (node.mNodosHijos[i].mNumKeys == (2 * T - 1)) {
                B_Split(node, i, node.mNodosHijos[i]);
                if (key > node.mKeys[i]) {
                    i++;
                }
            }
            B_InsertNonfull(node.mNodosHijos[i], key, registro);
        }
    }
    private boolean update(Nodo node, int key, Registro registro) {
        while (node != null) {
            int i = 0;
            while (i < node.mNumKeys && key > node.mKeys[i]) {
                i++;
            }
            if (i < node.mNumKeys && key == node.mKeys[i]) {
                node.mRegistros[i] = registro;
                return true;
            }
            if (node.isHoja) {
                return false;
            } else {
                node = node.mNodosHijos[i];
            }
        }
        return false;
    }

}
