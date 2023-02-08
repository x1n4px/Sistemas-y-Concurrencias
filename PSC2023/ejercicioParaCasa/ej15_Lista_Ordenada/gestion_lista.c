#include <stdlib.h>
#include <stdio.h>
#include "gestion_lista.h"

// crea una lista vacía
void crearLista(Lista *l)
{
    (*l) = NULL;
}
// escribe en la pantalla los elementos de la lista
void recorrerLista(Lista l)
{
    int cont = 0;
    while (l != NULL)
    {
        printf("Elemento $d:  %d\n",cont,  l->elem);
        cont++;
        l->sig = l->sig->sig;
    }
}

// devuelve 0 sii la lista está vacía
int listaVacia(Lista l)
{
    return (l == NULL); // 0 si false
}

// inserta el elemento elem en la lista l de forma que quede ordenada de forma creciente
int insertarLista(Lista *l, int elem)
{

    return 0;
}

// elimina de la lista el elemento elem. Devuelve 1 si se ha podido eliminar y 0, si elem no estaba en la lista.
int extraerLista(Lista *l, int elem)
{

    return 0;
}

// elimina todos los nodos de la lista y la deja vacía
void borrarLista(Lista *l)
{
}