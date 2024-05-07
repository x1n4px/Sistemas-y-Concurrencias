//
// Created by in4p on 5/28/23.
//
#include "ListaJugadores.h"
#include <stdio.h>
#include <stdlib.h>

// crea una lista vac�a (sin ning�n nodo)
void crear(TListaJugadores *lc)
{
    *lc = NULL; // El puntero se inicializa como NULL, indicando una lista vacía.
}

// inserta un nuevo jugador en la lista de jugadores, poniendo 1 en el n�mero de goles marcados.
// Si ya existe a�ade 1 al n�mero de goles marcados.
void insertar(TListaJugadores *lj, unsigned int id)
{
    if (*lj == NULL)
    { // Si la lista esta vacia
        TListaJugadores nuevo = malloc(sizeof(struct TJugador));
        if (nuevo == NULL)
        {
            exit(-1);
        }
        else
        { // nuevo sera el primer nodo de la lista
            nuevo->id = id;
            nuevo->gole = 1;
            nuevo->sig = NULL;
            *lj = nuevo;
        }
    }else if(id == (*lj)->id) { //Si la lista no esta vacia, pero el primero nodo es nuestro jugador, aumentamos los goles
        (*lj)->gole++;
    }else{ //En cualquier otro caso
        TListaJugadores ptr = *lj;
        while(ptr->sig != NULL && id > ptr->sig->id) { //Mientras que la lista no acabe y el id sea mas pequeño que el id de la lsita
            ptr = ptr->sig; // Avanzamos
        }
        if(ptr->sig != NULL && ptr->sig->id == id) {
            ptr->sig->gole++;
        }else{
            TListaJugadores nuevo = malloc(sizeof(struct TJugador));
            if(nuevo == NULL) {
                exit(-1);
            }else{
                nuevo->id = id;
                nuevo->gole = 1;
                nuevo->sig = ptr->sig;
                ptr->sig = nuevo;
            }
        }
    }
}

// recorre la lista circular escribiendo los identificadores y los goles marcados
void recorrer(TListaJugadores lj)
{
    printf("Lista de jugadores\n");
    while (lj != NULL)
    {
        printf("Jugador->%d Goles->%d \n", lj->id, lj->gole);
        lj = lj->sig;
    }
}

// devuelve el n�mero de nodos de la lista
int longitud(TListaJugadores lj)
{
    int contador = 0;
    while (lj != NULL)
    {
        contador++;
        lj = lj->sig;
    }

    return contador;
}

// Eliminar. Toma un n�mero de goles como par�metro y
// elimina todos los jugadores que hayan marcado menos que ese n�mero de goles
void eliminar(TListaJugadores *lj, unsigned int n)
{
    TListaJugadores actual = *lj;
    TListaJugadores anterior = NULL;

    while (actual != NULL)
    {
        if (actual->gole < n)
        {
            // Si es el primer nodo de la lista, acutalizamos el puntero lj
            if (anterior == NULL)
            { // Es el primero
                *lj = actual->sig;
            }
            else
            {
                anterior->sig = actual->sig;
            }

            // Liberamos la memoria del nodo actual y actualizamos el punterio actual
            TListaJugadores aux = actual;
            actual = actual->sig;
            free(aux);
        }
        else
        {
            // Si el nodo actual no cumple con la condicion, simplemente avanzamos el siguiente
            anterior = actual;
            actual = actual->sig;
        }
    }
}

// Devuelve el ID del m�ximo jugador. Si la lista est� vac�a devuelve 0. Si hay m�s de un jugador con el mismo n�mero de goles que el m�ximo devuelve el de mayor ID
// Hay que devolver el identificador, no el n�mero de goles que ha marcado
unsigned int maximo(TListaJugadores lj)
{
    int id = 0, ok = 0;
    if (lj != NULL)
    {
        TListaJugadores actual = lj;
        for (int i = 0; i < longitud(lj); i++)
        {
            if (actual->gole > ok)
            {
                ok = actual->gole;
                id = actual->id;
            }
            actual = actual->sig;
        }
    }
    return id;
}

// Destruye la lista y libera la memoria)
void destruir(TListaJugadores *lj)
{
    TListaJugadores aux;

    while ((*lj) != NULL)
    {
        aux = (*lj);
        (*lj) = (*lj)->sig;
        free(aux);
    }
}
