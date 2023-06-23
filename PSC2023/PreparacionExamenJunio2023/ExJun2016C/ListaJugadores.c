//
// Created by in4p on 5/28/23.
//
#include "ListaJugadores.h"
#include <stdio.h>
#include <stdlib.h>



//crea una lista vac�a (sin ning�n nodo)
void crear(TListaJugadores *lc){
    *lc = NULL; // El puntero se inicializa como NULL, indicando una lista vacía.
}

//inserta un nuevo jugador en la lista de jugadores, poniendo 1 en el n�mero de goles marcados.
//Si ya existe a�ade 1 al n�mero de goles marcados.
void insertar(TListaJugadores *lj,unsigned int id){
    TListaJugadores nuevoJugador = (TListaJugadores)malloc(sizeof(struct TJugador));
    nuevoJugador->id = id;
    nuevoJugador->gole = 1;

    if(*lj == NULL || id < (*lj)->id) {
       if(*lj == NULL){
           nuevoJugador->sig = nuevoJugador;
           *lj = nuevoJugador;
       }else{
           nuevoJugador->sig = *lj;
           TListaJugadores aux = *lj;
           while (aux->sig != *lj)  {
               aux = aux->sig;
           }
           aux->sig = nuevoJugador;
           *lj = nuevoJugador;
       }

       TListaJugadores aux = *lj;

       //Buscar la posicion de insercion segun el orden de id
        while (aux->sig != *lj && aux->sig->id <= id) {
            if(aux->sig->id == id) {
                aux->sig->gole++;
                free(nuevoJugador);
                return;
            }
            aux = aux->sig;
        }
        //Insertar un nuevo jugador despues de aux
        nuevoJugador->sig = aux->sig;
        aux->sig = nuevoJugador;
    }

    TListaJugadores aux = *lj;

    while (aux->sig != NULL && aux->sig->id <= id) {
        if(aux->sig->id == id) {
            aux->sig->gole++; //El jugador ya existe, se incrementan los goles
            free(nuevoJugador);
            return;
        }
        aux = aux->sig;
    }

    //Insertar el nuevo jugador despues de aux
    nuevoJugador->sig = aux->sig;
    aux->sig = nuevoJugador;

}

//recorre la lista circular escribiendo los identificadores y los goles marcados
void recorrer(TListaJugadores lj){
    if(lj == NULL){
        printf("La lista de jugadores esta vacia\n");
        return;
    }

    TListaJugadores aux = lj;

    do {
        printf("ID: %u, Goles marcados %u\n", aux->id, aux->gole);
    } while (aux != lj);
}

//devuelve el n�mero de nodos de la lista
int longitud(TListaJugadores lj){
    int contador = 0;
    if (lj == NULL) {
        return 0; // La lista está vacía, longitud = 0
    }
    TListaJugadores aux = lj;

    do {
        contador++;
    } while (aux != lj);

    return contador;
}

//Eliminar. Toma un n�mero de goles como par�metro y
//elimina todos los jugadores que hayan marcado menos que ese n�mero de goles
void eliminar(TListaJugadores *lj,unsigned int n){
    if(*lj == NULL) {
        return;
    }

    TListaJugadores actual = *lj;
    TListaJugadores anterior = NULL;

    do {
        if(actual->gole < n) {

            // Si es el primer nodo de la lista
            if(actual == *lj) {
                *lj = actual->sig; // Actualizar el puntero de inicio
            }
            // Si no es el primer nodo, ajustar el enlace del nodo anterior
            if(anterior != NULL) {
                anterior->sig = actual->sig;
            }

            TListaJugadores siguiente = actual->sig;
            free(actual);
            actual = siguiente;
        }else{
            anterior = actual;
            actual = actual->sig;
        }
    } while (actual != *lj);
}


// Devuelve el ID del m�ximo jugador. Si la lista est� vac�a devuelve 0. Si hay m�s de un jugador con el mismo n�mero de goles que el m�ximo devuelve el de mayor ID
// Hay que devolver el identificador, no el n�mero de goles que ha marcado
unsigned int maximo(TListaJugadores lj){
    if(lj == NULL){
        return 0;
    }

    unsigned int maxID = lj->id;
    unsigned int maxGoles = lj->gole;

    TListaJugadores aux = lj->sig;

    while (aux != lj) {
        if(aux->gole > maxGoles || (aux->gole == maxGoles && aux->id > maxID)) {
            maxID = aux->id;
            maxGoles = aux->gole;
        }
        aux = aux->sig;
    }
    return maxID;
}

//Destruye la lista y libera la memoria)
void destruir(TListaJugadores *lj){
    if(*lj == NULL){
        return;
    }
    TListaJugadores aux = (*lj)->sig;

    while (aux != *lj) {
        TListaJugadores siguiente = aux->sig;
        free(aux);
        aux = siguiente;
    }
    free(*lj);
    *lj = NULL;
}
