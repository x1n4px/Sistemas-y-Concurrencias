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
        printf("Elemento %d:  %d\n", cont, l->elem);
        cont++;
        l = l->sig;
    }
}

// devuelve 0 sii la lista está vacía
int listaVacia(Lista l)
{
    int volo = 0;
    if ((l != NULL))
    {
        volo = 1;
    }
    return volo; // 0 si false
}

// inserta el elemento elem en la lista l de forma que quede ordenada de forma creciente
int insertarLista(Lista *l, int elem)
{
    Lista nuevoNodo = malloc(sizeof(struct NodoLista));
    if (nuevoNodo == NULL)
    {
        return 0;
    }

    nuevoNodo->elem = elem;

    if (*l == NULL || elem <= (*l)->elem)
    {
        nuevoNodo->sig = *l;
        *l = nuevoNodo;
    }
    else
    {
        Lista anterior = *l;
        Lista actual = (*l)->sig;
        while (actual != NULL && elem > actual->elem)
        {
            anterior = actual;
            actual = actual->sig;
        }
        nuevoNodo->sig = actual;
        anterior->sig = nuevoNodo;
    }

    return 1;
}

// elimina de la lista el elemento elem. Devuelve 1 si se ha podido eliminar y 0, si elem no estaba en la lista.
int extraerLista(Lista *l, int elem)
{
    Lista ptr;
    Lista ant;

    if (*l != NULL)
    {
        if ((*l)->elem == elem)
        {
            ptr = *l;
            *l = (*l)->sig;
            free(ptr);
            return 1;
        }
        else
        {
            ant = *l;
            ptr = (*l)->sig;
            while ((ptr != NULL) && (ptr->elem != elem))
            {
                ant = ptr;
                ptr = ptr->sig;
            }
            if (ptr != NULL)
            {
                ant->sig = ptr->sig;
                free(ptr);
                return 1;
            }
        }
    }
    return 0;
}

// elimina todos los nodos de la lista y la deja vacía
void borrarLista(Lista *l)
{
    Lista aux;
    while (*l != NULL)
    {
        aux = *l;
        *l = (*l)->sig; // apunta al siguiente
        free(aux);
    }
}
