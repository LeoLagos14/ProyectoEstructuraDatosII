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
     public ArrayList<Registro> getAllKeys(){
        return getKeys(mNodoRaiz);
    }
    
    private ArrayList<Registro> getKeys(Nodo node) {
        ArrayList<Registro> array = new ArrayList<Registro>();
        if (node != null) {
            if (node.isHoja) {
                for (int i = 0; i < node.mNumKeys; i++) {
                    array.add(node.mRegistros[i]);
                }
            } else {
                int i;
                for (i = 0; i < node.mNumKeys; i++) {
                    array.addAll(getKeys(node.mNodosHijos[i]));
                    array.add(node.mRegistros[i]);
                }
                array.addAll(getKeys(node.mNodosHijos[i]));
            }
        }
        return array;
    }
     public Registro B_Buscar(int key) {
        return search(mNodoRaiz, key);
    }
     public Registro search(Nodo node, int key) {
        int i = 0;
        while (i < node.mNumKeys && key > node.mKeys[i]) {
            i++;
        }
        if (i < node.mNumKeys && key == node.mKeys[i]) {
            return node.mRegistros[i];
        }
        if (node.isHoja) {
            return null;
        } else {
            return search(node.mNodosHijos[i], key);
        }
    }

    public void B_Insert(int key, Registro registro) {
        Nodo rootNode = mNodoRaiz;
        if (mNodoRaiz == null) {
            mNodoRaiz = new Nodo(); // Inicializa la raíz si está vacía
        }
        if (!update(mNodoRaiz, key, registro)) { // Si no puede insertar en el nodo actual
            if (rootNode.mNumKeys == (2 * T - 1)) { // if Full
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
        
          for (int i = 0; i < this.getAllKeys().size(); i++) {
            System.out.println("Impriminedo el registro en " + i);
            System.out.println(this.getAllKeys().get(i));
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
            if (node != null && node.mNodosHijos[i].mNumKeys == (2 * T - 1)) {
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
    public void B_Delete(int key) {
        delete(mNodoRaiz, key);
    }

    public void delete(Nodo node, int key) {
        if (node.isHoja) { // 1. si la llave esta en el nodo y si el nodo es hoja, lo elimina del nodo
            int i;
            if ((i = node.binarySearch(key)) != -1) { // si es diferente -1 entonces este nodo contiene la llave
                node.remove(i, LEFT_CHILD_NODE);
            }
        } else {
            int i;
            if ((i = node.binarySearch(key)) != -1) { // 2. Si el nodo es un nodo interno y si contiene la llave                   
                Nodo leftChildNode = node.mNodosHijos[i];
                Nodo rightChildNode = node.mNodosHijos[i + 1];
                if (leftChildNode.mNumKeys >= T) { // 2a. si el nodo predecedor tiene al menos T llaves...
                    Nodo predecessorNode = leftChildNode;
                    Nodo erasureNode = predecessorNode; // Verificar si no eliminamos una llave del nodo con solo T - 1 elementos.
                    while (!predecessorNode.isHoja) { 
                        erasureNode = predecessorNode;
                        predecessorNode = predecessorNode.mNodosHijos[node.mNumKeys - 1];
                    }
                    node.mKeys[i] = predecessorNode.mKeys[predecessorNode.mNumKeys - 1];
                    node.mRegistros[i] = predecessorNode.mRegistros[predecessorNode.mNumKeys - 1];
                    delete(erasureNode, node.mKeys[i]);
                } else if (rightChildNode.mNumKeys >= T) { // 2b. si el hijo sucesor tiene T llaves...
                    Nodo successorNode = rightChildNode;
                    Nodo erasureNode = successorNode; // Verificar si no eliminamos una llave del nodo con solo T - 1 elementos.
                    while (!successorNode.isHoja) { 
                        erasureNode = successorNode;
                        successorNode = successorNode.mNodosHijos[0];
                    }
                    node.mKeys[i] = successorNode.mKeys[0];
                    node.mRegistros[i] = successorNode.mRegistros[0];
                    delete(erasureNode, node.mKeys[i]);
                } else { // 2c. si ambos nodos hijos el predecedor y el sucesor tienen solo T -1 llaves
                    int medianKeyIndex = mergeNodes(leftChildNode, rightChildNode);
                    moveKey(node, i, RIGHT_CHILD_NODE, leftChildNode, medianKeyIndex); 
                    delete(leftChildNode, key);
                }
            } else { 
                i = node.subtreeRootNodeIndex(key);
                Nodo childNode = node.mNodosHijos[i];                             
                if (childNode.mNumKeys == T - 1) {
                    Nodo leftChildSibling = (i - 1 >= 0) ? node.mNodosHijos[i - 1] : null;
                    Nodo rightChildSibling = (i + 1 <= node.mNumKeys) ? node.mNodosHijos[i + 1] : null;
                    if (leftChildSibling != null && leftChildSibling.mNumKeys >= T) {
                        childNode.shiftRightByOne();
                        childNode.mKeys[0] = node.mKeys[i - 1]; 
                        childNode.mRegistros[0] = node.mRegistros[i - 1];
                        if (!childNode.isHoja) {
                            childNode.mNodosHijos[0] = leftChildSibling.mNodosHijos[leftChildSibling.mNumKeys];
                        }
                        childNode.mNumKeys++;

                        node.mKeys[i - 1] = leftChildSibling.mKeys[leftChildSibling.mNumKeys - 1];
                        node.mRegistros[i - 1] = leftChildSibling.mRegistros[leftChildSibling.mNumKeys - 1];

                        leftChildSibling.remove(leftChildSibling.mNumKeys - 1, RIGHT_CHILD_NODE);
                    } else if (rightChildSibling != null && rightChildSibling.mNumKeys >= T) { 
                        childNode.mKeys[childNode.mNumKeys] = node.mKeys[i];
                        childNode.mRegistros[childNode.mNumKeys] = node.mRegistros[i];
                        if (!childNode.isHoja) {
                            childNode.mNodosHijos[childNode.mNumKeys + 1] = rightChildSibling.mNodosHijos[0];
                        }
                        childNode.mNumKeys++;

                        node.mKeys[i] = rightChildSibling.mKeys[0];
                        node.mRegistros[i] = rightChildSibling.mRegistros[0];

                        rightChildSibling.remove(0, LEFT_CHILD_NODE);
                    } else { 
                        if (leftChildSibling != null) {
                            int medianKeyIndex = mergeNodes(childNode, leftChildSibling);
                            moveKey(node, i - 1, LEFT_CHILD_NODE, childNode, medianKeyIndex); 
                        } else if (rightChildSibling != null) {
                            int medianKeyIndex = mergeNodes(childNode, rightChildSibling);
                            moveKey(node, i, RIGHT_CHILD_NODE, childNode, medianKeyIndex);
                        }
                    }
                }
                delete(childNode, key);
            }
        }
    }

    int mergeNodes(Nodo dstNode, Nodo srcNode) {
        int medianKeyIndex;
        if (srcNode.mKeys[0] < dstNode.mKeys[dstNode.mNumKeys - 1]) {
            int i;
            if (!dstNode.isHoja) {
                dstNode.mNodosHijos[srcNode.mNumKeys + dstNode.mNumKeys + 1] = dstNode.mNodosHijos[dstNode.mNumKeys];
            }
            for (i = dstNode.mNumKeys; i > 0; i--) {
                dstNode.mKeys[srcNode.mNumKeys + i] = dstNode.mKeys[i - 1];
                dstNode.mRegistros[srcNode.mNumKeys + i] = dstNode.mRegistros[i - 1];
                if (!dstNode.isHoja) {
                    dstNode.mNodosHijos[srcNode.mNumKeys + i] = dstNode.mNodosHijos[i - 1];
                }
            }

            medianKeyIndex = srcNode.mNumKeys;
            dstNode.mKeys[medianKeyIndex] = 0;
            dstNode.mRegistros[medianKeyIndex] = null;

            for (i = 0; i < srcNode.mNumKeys; i++) {
                dstNode.mKeys[i] = srcNode.mKeys[i];
                dstNode.mRegistros[i] = srcNode.mRegistros[i];
                if (!srcNode.isHoja) {
                    dstNode.mNodosHijos[i] = srcNode.mNodosHijos[i];
                }
            }
            if (!srcNode.isHoja) {
                dstNode.mNodosHijos[i] = srcNode.mNodosHijos[i];
            }
        } else {
            medianKeyIndex = dstNode.mNumKeys;
            dstNode.mKeys[medianKeyIndex] = 0;
            dstNode.mRegistros[medianKeyIndex] = null;

            int offset = medianKeyIndex + 1;
            int i;
            for (i = 0; i < srcNode.mNumKeys; i++) {
                dstNode.mKeys[offset + i] = srcNode.mKeys[i];
                dstNode.mRegistros[offset + i] = srcNode.mRegistros[i];
                if (!srcNode.isHoja) {
                    dstNode.mNodosHijos[offset + i] = srcNode.mNodosHijos[i];
                }
            }
            if (!srcNode.isHoja) {
                dstNode.mNodosHijos[offset + i] = srcNode.mNodosHijos[i];
            }
        }
        dstNode.mNumKeys += srcNode.mNumKeys;
        return medianKeyIndex;
    }

    void moveKey(Nodo srcNode, int srcKeyIndex, int childIndex, Nodo dstNode, int medianKeyIndex) {
        dstNode.mKeys[medianKeyIndex] = srcNode.mKeys[srcKeyIndex];
        dstNode.mRegistros[medianKeyIndex] = srcNode.mRegistros[srcKeyIndex];
        dstNode.mNumKeys++;

        srcNode.remove(srcKeyIndex, childIndex);

        if (srcNode == mNodoRaiz && srcNode.mNumKeys == 0) {
            mNodoRaiz = dstNode;
        }
    }

}
