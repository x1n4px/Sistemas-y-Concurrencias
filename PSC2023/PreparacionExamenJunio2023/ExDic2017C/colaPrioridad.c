//
// Created by in4p on 6/16/23.

#include "colaPrioridad.h"
#include <stdlib.h>
#include <stdio.h>
#include <string.h>



void crear(TColaPrio cp){
    for (int i = 0; i < L; ++i) {
        cp[i] = NULL;
    }
}

/**
 * Dada una prioridad y un identificador de proceso,
 * lo a�ade AL FINAL de la lista que le corresponde.
 */
void nuevoProceso (TColaPrio cp,int p, int id){
    Lista nuevoNodo = (Lista) malloc(sizeof(struct TNodo));
    nuevoNodo->id = id;
    nuevoNodo->sig = NULL;

    if(cp[p] == NULL){
        cp[p] = nuevoNodo;// Si la lista está vacía, el nuevo nodo se convierte en el primer nodo de la lista
    }else{
        Lista nodoActual = nuevoNodo;
        while (nodoActual->sig != NULL) {
            nodoActual = nodoActual->sig;
        }
        nodoActual->sig = nuevoNodo;
    }
}

/**
 * Elimina de la lista el proceso m�s prioritario que le corresponde ejecutarse.
 * Se devuelve 0 si se ha podido ejecutar el proceso, y -1, en caso contrario.
 */

//void ejecutarProceso(TColaPrio cp);
int ejecutaProceso(TColaPrio cp){
    for (int i = 0; i < L; ++i) {
        if (cp[i] != NULL) {
            int procesoEjecutado = cp[i]->id;
            Lista nodoEliminar = cp[i];
            cp[i] = cp[i]->sig;
            free(nodoEliminar);
            return procesoEjecutado;
        }
    }
    return -1;
}

/**
 * Dado un identificador de proceso devuelve la prioridad de �ste.
 * Si el id del proceso no existe se devolver� -1.
 */
int buscar(TColaPrio cp, int id){
    for (int i = 0; i < L; ++i) {
        Lista nodoActual = cp[i];
        while (nodoActual != NULL) {
            if(nodoActual->id == id) {
                return i;
            }
            nodoActual = nodoActual->sig;
        }
    }
    return -1;
}

/*
 * Recorre la estructura para mostrar los procesos existentes
 * que est�n disponibles para ejecuci�n ordenados por prioridad.
 *
 */
void mostrar(TColaPrio cp){
    int i = 0;
    for (int i = 0; i < L ; ++i) {
        Lista nodoActual = cp[i];
        printf("Procesos de prioridad %d: ", i);
        while(nodoActual != NULL) {
            printf("%d", nodoActual->sig);
            nodoActual = nodoActual->sig;
        }
        printf("\n");
    }
}

/*
 * Se eliminan todos los procesos de la cola de prioridad
 */
void destruir (TColaPrio cp){
    for (int i = 0; i < L; ++i) {
        Lista nodoActual = cp[i];
        while (nodoActual != NULL) {
            Lista nodoEliminar = nodoActual;
            nodoActual = nodoActual->sig;
            free(nodoEliminar);
        }
        cp[i] = NULL;
    }
}
