//
// Created by in4p on 5/25/23.3
#include "Lista.h"
#include <stdio.h>
#include <stdlib.h>




/*
 * Inicializa la lista de puntos creando una lista vacía
 *
 */
void crearLista(TLista *lista) {

    (*lista) = NULL;
}

/**
 * Inserta el punto de forma ordenada (por el valor de la abscisa x)
 * en la lista siempre que no esté repetida la abscisa.
 *  En ok, se devolverá un 1 si se ha podido insertar, y  0 en caso contrario.
 *  Nota: utiliza una función auxiliar para saber
 *   si ya hay un punto en la lista con la misma abscisa punto.x
 *
 */
void insertarPunto(TLista *lista, struct Punto punto, int * ok){
    *ok = 0;
    TLista nuevonodo;
    TLista ant, ptr; //Para posicionarnos donde insertar

    nuevonodo = malloc(sizeof(struct Nodo));
    nuevonodo->punto.x = punto.x;
    nuevonodo->punto.y = punto.y;

    if(*lista == NULL){ //lista vacia
        nuevonodo->sig = NULL;
        *lista = nuevonodo;
        *ok = 1;
    }else if(nuevonodo->punto.x <= (*lista)->punto.x) {
        nuevonodo->sig = *lista;
        *lista = nuevonodo;
        *ok = 1;
    }else{
        ant = *lista;
        ptr = (*lista)->sig;
        while ((ptr != NULL) && (nuevonodo->punto.x > ptr->punto.x)){
            ant = ptr;
            ptr = ptr->sig;
        }
        nuevonodo->sig = ptr;
        ant->sig = nuevonodo;
        *ok = 1;
    }

}


void eliminarPrimero(TLista *lista){
    TLista ptr;
    if(lista != NULL){
        ptr = *lista;
        *lista = (*lista)->sig;
        free(ptr);
    }
}

/*
 * Elimina de la lista el punto con abscisa x de la lista.
 * En ok devolverá un 1 si se ha podido eliminar,
 * y un 0 si no hay ningún punto en la lista con abscisa x
 *
 */
void eliminarPunto(TLista *lista,float x,int* ok){
    TLista ptr;
    TLista ant;

    if(*lista != NULL){
        if((*lista)->punto.x == x){
            eliminarPrimero(lista);
        }else{
            ant = *lista;
            ptr = (*lista)->sig;
            while((ptr!=NULL) && (ptr->punto.x != x)) {
                ant = ptr;
                ptr = ptr->sig;
            }
            if(ptr != NULL) {
                ant->sig = ptr->sig;
                free(ptr);
            }
        }
    }
}


/**
* Muestra en pantalla el listado de puntos
*/
void mostrarLista(TLista lista){
    while(lista->sig != NULL){
        printf("(%.2f, %.2f)\n", lista->punto.x, lista->punto.y);
        lista = lista->sig;
    }
}

/**
 * Destruye la lista de puntos, liberando todos los nodos de la memoria.
 */
void destruir(TLista *lista){
    TLista ptr;
    while(*lista != NULL){
        ptr = *lista;
        *lista = (*lista)->sig;
        free(ptr);
    }
}

/*
 * Lee el contenido del archivo binario de nombre nFichero,
 * que contiene una secuencia de puntos de una función polinómica,
 *  y lo inserta en la lista.
 *
 */
void leePuntos(TLista *lista,char *nFichero){
    FILE *archivo = fopen(nFichero, "rb");
    int ok;
    if(archivo == NULL){
        printf("Error al abrir el fichero");
        return;
    }

    struct Punto punto;

    while(fread(&punto, sizeof(punto), 1, archivo) == 1) {
        insertarPunto(lista, punto, &ok);
    }
    fclose(archivo);
}
