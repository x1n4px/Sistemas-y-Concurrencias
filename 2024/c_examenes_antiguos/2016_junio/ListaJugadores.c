#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "ListaJugadores.h"

// crea una lista vac�a (sin ning�n nodo)
void crear(TListaJugadores *lc)
{
    *lc = NULL;
}

// inserta un nuevo jugador en la lista de jugadores, poniendo 1 en el n�mero de goles marcados.
// Si ya existe a�ade 1 al n�mero de goles marcados.
void insertar(TListaJugadores *lj, unsigned int id)
{
    TListaJugadores ptr = *lj, ant = NULL;
    TListaJugadores nuevoJugador = malloc(sizeof(struct Jugador));

    if (nuevoJugador == NULL)
    {
        perror("No se ha podido reservar memoria");
    }
    else
    {
        if (*lj == NULL)
        { // lista vacia
            nuevoJugador->id = id;
            nuevoJugador->numGoles = 1;
            nuevoJugador->siguiente = NULL;
            *lj = nuevoJugador;
        }
        else
        {
            while (ptr != NULL && id > ptr->id)
            {
                ant = ptr;
                ptr = ptr->siguiente;
            }
            if ((ptr != NULL) && (ptr->id == id))
            { // Esta en la lista, por tanto se le suma un gol
                ptr->numGoles++;
                free(nuevoJugador);
            }
            else
            {
                nuevoJugador->id = id;
                nuevoJugador->numGoles = 1;
                if(ant == NULL) { // va el primero
                    nuevoJugador->siguiente = *lj;
                    *lj = nuevoJugador;
                }else{
                    nuevoJugador->siguiente = ant->siguiente;
                    ant->siguiente = nuevoJugador;
                }
            }
        }
    }
}

// recorre la lista circular escribiendo los identificadores y los goles marcados
void recorrer(TListaJugadores lj)
{
    if (lj == NULL)
    {
        printf("No hay jugadores en la lista\n");
    }
    while (lj != NULL)
    {
        printf("Jugador %u ha marcado %u goles\n", lj->id, lj->numGoles);
        lj = lj->siguiente;
    }
}

// devuelve el n�mero de nodos de la lista
int longitud(TListaJugadores lj)
{
    int numNodos = 0;
    while (lj != NULL)
    {
        numNodos++;
        lj = lj->siguiente;
    }

    return numNodos;
}

// Eliminar. Toma un n�mero de goles como par�metro y
// elimina todos los jugadores que hayan marcado menos que ese n�mero de goles
void eliminar(TListaJugadores *lj, unsigned int n)
{
    TListaJugadores ptr = *lj, ant = NULL;

    while (ptr != NULL)
    {
        if (ptr->numGoles < n)
        {
            if (ant == NULL)
            { // Borra el primero
                *lj = ptr->siguiente;
                free(ptr);
                ptr = *lj;
            }
            else
            {
                ant->siguiente = ptr->siguiente;
                free(ptr);
                ptr = ant->siguiente;
            }
        }
        else
        {
            ant = ptr;
            ptr = ptr->siguiente;
        }
    }
}

// Devuelve el ID del m�ximo jugador. Si la lista est� vac�a devuelve 0. Si hay m�s de un jugador con el mismo n�mero de goles que el m�ximo devuelve el de mayor ID
// Hay que devolver el identificador, no el n�mero de goles que ha marcado
unsigned int maximo(TListaJugadores lj)
{
    unsigned int id = 0, goles = 0;
    if (lj != NULL)
    {
        while (lj != NULL)
        {
            if (lj->numGoles >= goles)
            {
                id = lj->id;
                goles = lj->numGoles;
            }
        }
    }
    return id;
}

// Destruye la lista y libera la memoria)
void destruir(TListaJugadores *lj)
{
    TListaJugadores ptr;
    while (*lj != NULL)
    {
        ptr = *lj;
        *lj = ptr->siguiente;
        free(ptr);
    }
}
