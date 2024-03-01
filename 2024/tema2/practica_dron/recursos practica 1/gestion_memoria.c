#include <stdio.h>
#include <stdlib.h>
#include "gestion_memoria.h"

#define MAX 1000;

void crear(T_Manejador* manejador) {
    *manejador = (T_Manejador) malloc(sizeof(struct T_Nodo));
    (*manejador)->inicio = 0;
    (*manejador)->fin = MAX;
    (*manejador)->sig = NULL;
}

void destruir(T_Manejador* manejador) {
    T_Manejador aux;
    while (*manejador != NULL) {
        aux = *manejador;
        *manejador = (*manejador)->sig;
        free(aux);
    }
}

void obtener(T_Manejador *manejador, unsigned tam, unsigned* dir, unsigned* ok) {
    T_Manejador aux = *manejador;
    *ok = 0;
    while (aux != NULL) {
        if (aux->fin - aux->inicio >= tam) {
            *dir = aux->inicio;
            aux->inicio += tam;
            *ok = 1;
            break;
        }
        aux = aux->sig;
    }
}

void mostrar (T_Manejador manejador) {
    T_Manejador aux = manejador;
    while (aux != NULL) {
        printf("Inicio: %d, Fin: %d\n", aux->inicio, aux->fin);
        aux = aux->sig;
    }
}


void devolver(T_Manejador *manejador,unsigned tam,unsigned dir) {
    T_Manejador aux = *manejador;
    while (aux != NULL) {
        if (aux->inicio == dir + tam) {
            aux->inicio = dir;
            return;
        }
        if (aux->fin == dir) {
            aux->fin = dir + tam;
            return;
        }
        if (aux->inicio > dir) {
            T_Manejador nuevo = (T_Manejador) malloc(sizeof(struct T_Nodo));
            nuevo->inicio = dir;
            nuevo->fin = dir + tam;
            nuevo->sig = aux;
            *manejador = nuevo;
            return;
        }
        if (aux->sig != NULL && aux->sig->inicio < dir) {
            T_Manejador nuevo = (T_Manejador) malloc(sizeof(struct T_Nodo));
            nuevo->inicio = dir;
            nuevo->fin = dir + tam;
            nuevo->sig = aux->sig;
            aux->sig = nuevo;
            return;
        }
        aux = aux->sig;
    }
    T_Manejador nuevo = (T_Manejador) malloc(sizeof(struct T_Nodo));
    nuevo->inicio = dir;
    nuevo->fin = dir + tam;
    nuevo->sig = NULL;
    aux->sig = nuevo;
}