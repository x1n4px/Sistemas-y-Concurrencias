#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "ListaCircular.h"

//crea una lista circular vac�a (sin ning�n nodo)
void crear(TListaCircular *lc){
    *lc=NULL;
}

//inserta un nuevo nodo con el dato nombre al final de la lista circular
void insertar(TListaCircular *lc,char *nombre){
    
}

//recorre la lista circular escribiendo los nombres de los nodos en la
//pantalla
void recorrer(TListaCircular lc){
    TListaCircular ptr = lc;

    while(strcmp(ptr->nombre, lc->nombre) != 0) {
        printf("%s\n", ptr->nombre);
        ptr = ptr->sig;
    }
    printf("%s\n", ptr->nombre);
}

//devuelve el n�mero de nodos de la lista
int longitud(TListaCircular lc){
    if(lc == NULL) {
        return 0;
    }
    int cont = 1;
    TListaCircular aux = lc;
    TListaCircular ptr = lc;
    while(strcmp(ptr->nombre, aux->nombre) != 0) {
        cont++;
        aux = aux->sig;
    }
    return cont;
}

//mueve el puntero exterto de la lista n nodos (siguiendo la direcci�n de la
//lista)
void mover(TListaCircular *lc,int n){
    if(*lc == NULL) {
        return;
    }


    while(n > 0) {
        *lc = (*lc)->sig;
        n--;
    }
}

//elimina el primer nodo de la lista, y devuelve el nombre que contiene
//a trav�s del par�metro nombre
void extraer(TListaCircular *lc,char *nombre){
    TListaCircular anterior;
    TListaCircular ptr = *lc;

    while(strcmp(ptr->nombre, (*lc)->nombre) != 0) {
        anterior = ptr;
        ptr = ptr->sig;
    }
    anterior->sig = ptr->sig;

    (*lc) = anterior;
    strcpy(nombre, anterior->sig->nombre);
}
