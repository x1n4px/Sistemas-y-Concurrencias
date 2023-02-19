#include "gestion_listaCircular.h"
#include <assert.h>
#include <stdlib.h>

void actualizar_ultimo(ptr_lista_entero *ptr_lista, ptr_lista_entero antigua_cabeza)
{
}

void inicializar(ptr_lista_entero *ptr_lista)
{
    *ptr_lista = NULL;
}

void rotarCabeza(ptr_lista_entero *ptr_lista, int numero_rotaciones, int *ok)
{
    if (*ptr_lista == NULL)
    {
        *ok = 0;
        return;
    }
    for (int i = 0; i < numero_rotaciones; i++)
    {
        *ptr_lista = (*ptr_lista)->sig;
    }
    *ok = 1;
}

void destruir(ptr_lista_entero *ptr_lista)
{
    ptr_lista_entero actual = *ptr_lista;
    while (actual != NULL)
    {
        ptr_lista_entero siguiente = actual->sig;
        free(actual);
        actual = siguiente;
    }
    *ptr_lista = NULL;
}

void insertarCabeza(ptr_lista_entero *ptr_lista, int valor, int *ok)
{
    ptr_lista_entero nuevo_elemento = (ptr_lista_entero)malloc(sizeof(struct Lista_Entero));
    if (nuevo_elemento == NULL)
    {
        *ok = 0;
        return;
    }
    nuevo_elemento->numero = valor;
    if (*ptr_lista == NULL)
    {
        nuevo_elemento->sig = nuevo_elemento;
        *ptr_lista = nuevo_elemento;
    }
    else
    {
        nuevo_elemento->sig = (*ptr_lista)->sig;
        (*ptr_lista)->sig = nuevo_elemento;
    }
    *ok = 1;
}

void insertarCola(ptr_lista_entero *ptr_lista, int valor, int *ok)
{
    ptr_lista_entero nuevo_elemento = (ptr_lista_entero)malloc(sizeof(struct Lista_Entero));
    if (nuevo_elemento == NULL)
    {
        *ok = 0;
        return;
    }
    nuevo_elemento->numero = valor;
    if (*ptr_lista == NULL)
    {
        nuevo_elemento->sig = nuevo_elemento;
        *ptr_lista = nuevo_elemento;
    }
    else
    {
        nuevo_elemento->sig = (*ptr_lista)->sig;
        (*ptr_lista)->sig = nuevo_elemento;
        *ptr_lista = nuevo_elemento;
    }
    *ok = 1;
}

void insertarOrdenado(ptr_lista_entero *ptr_lista, int valor, int *ok)
{
    ptr_lista_entero nuevo_nodo = (ptr_lista_entero)malloc(sizeof(struct Lista_Entero));
    if(nuevo_nodo == NULL){
        *ok = 0;
        return;
    }
    nuevo_nodo->numero = valor;
    nuevo_nodo->sig = NULL;

    if(*ptr_lista == NULL || valor <= (*ptr_lista)->numero){
        insertarCabeza(ptr_lista, valor, ok);
        return;
    }
    ptr_lista_entero actual = *ptr_lista;
    while(actual->sig != *ptr_lista && actual->sig->numero < valor){
        actual = actual->sig;
    }
    nuevo_nodo->sig = actual->sig;
    actual->sig = nuevo_nodo;
    *ok = 1;
}

void borrarCabeza(ptr_lista_entero *ptr_lista, int *ok)
{
    if(*ptr_lista == NULL){
        *ok = 0;
        return;
    }
    ptr_lista_entero cabeza = *ptr_lista;
    ptr_lista_entero nueva_cabeza = cabeza->sig;

    if(nueva_cabeza == cabeza){
        *ptr_lista = NULL; // Solo queda un elemento en la lista
    }else{
        ptr_lista_entero ultimo = cabeza;
        while (ultimo->sig != cabeza)
        {
            ultimo = ultimo->sig;
        }
        ultimo->sig = nueva_cabeza;
        *ptr_lista = nueva_cabeza;
    }
    free(cabeza);
    *ok = 1;
}

void borrarCola(ptr_lista_entero *ptr_lista, int *ok)
{
    if(*ptr_lista == NULL){
        *ok = 0;
        return;
    }

    ptr_lista_entero actual = *ptr_lista;
    ptr_lista_entero anterior = NULL;
    while(actual->sig != *ptr_lista){
        anterior = actual;
        actual = actual->sig;
    }
    if(anterior == NULL){
        //Solo queda un elemento en la lista
        *ptr_lista = NULL;
    }else{
        anterior->sig = *ptr_lista;
    }
    free(actual);
    *ok = 1;
}

ptr_lista_entero quitarNCabeza(ptr_lista_entero *ptr_lista, int n, int *m)
{
    if(*ptr_lista == NULL){
        *m = 0;
        return NULL;
    }

    ptr_lista_entero cabeza = *ptr_lista;
    ptr_lista_entero actual = cabeza->sig;
    int i = 1;
    while (i < n && actual != cabeza)
    {
        actual = actual->sig;
        i++;
    }
    *m = i;
    cabeza->sig = actual;

    if(actual == cabeza){
        //Se han extraido todos los elementos de la lista
        *ptr_lista = NULL;
    }else{
        *ptr_lista = actual;
        ptr_lista_entero ultimo = actual;
        while(ultimo->sig != cabeza){
            ultimo = ultimo->sig;
        }
        ultimo->sig = actual;
    }
    return cabeza;
}
