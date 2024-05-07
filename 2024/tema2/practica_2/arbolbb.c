#include <stdio.h>
#include <stdlib.h>
#include "arbolbb.h"

void Crear(T_Arbol *arbol)
{
    *arbol = NULL; 
}

void Insertar(T_Arbol *arbol, unsigned num)
{
    T_Arbol aux = *arbol;

    if (aux == NULL)
    {
       
        aux = (T_Arbol)malloc(sizeof(struct T_Nodo));
        aux->dato = num;
        aux->der = NULL;
        aux->izq = NULL;
    }
    else
    {
        if(num < aux->dato) {
            Insertar(&(aux->izq), num);
        } else {
            Insertar(&(aux->der), num);
        }
    }

    *arbol = aux;
}

// Función recursiva para realizar el recorrido inorden del árbol
void inOrden(T_Arbol raiz) {
    if (raiz != NULL) {
        inOrden(raiz->izq);
        printf("%u ", raiz->dato);
        inOrden(raiz->der);
    }
}

// Función para mostrar el contenido del árbol en orden (inorden)
void Mostrar(T_Arbol arbol) {
    if (arbol == NULL) {
        printf("El arbol esta vacio.\n");
    } else {
        printf("Contenido del arbol en InOrden: ");
        inOrden(arbol);
        printf("\n");
    }
}


void Destruir(T_Arbol *arbol) {
    if (*arbol != NULL) {
        Destruir(&((*arbol)->izq)); // Destruir el subárbol izquierdo
        Destruir(&((*arbol)->der)); // Destruir el subárbol derecho
        free(*arbol); // Liberar la memoria del nodo actual
        *arbol = NULL; // Establecer el puntero del nodo actual como NULL
    }
}




void Salvar(T_Arbol arbol, FILE *fichero) {
    if (arbol == NULL) {
        return;
    }

    // Escribir el dato del nodo actual en el archivo
    fwrite(&(arbol->dato), sizeof(unsigned), 1, fichero);

    // Llamar recursivamente a Salvar para el subárbol izquierdo y derecho
    Salvar(arbol->izq, fichero);
    Salvar(arbol->der, fichero);
}


void Copiar(T_Arbol a, T_Arbol *ac) {
    if (a == NULL) {
        *ac = NULL; // Si el árbol original es vacío, el árbol copiado también es vacío
    } else {
        // Crear una copia del nodo actual
        *ac = (T_Arbol *)malloc(sizeof(struct T_Nodo));
        (*ac)->dato = a->dato;
        (*ac)->izq = (*ac)->der = NULL;

        // Copiar recursivamente los subárboles izquierdo y derecho
        Copiar(a->izq, &((*ac)->izq));
        Copiar(a->der, &((*ac)->der));
    }
}

void MostrarIndiceRec(T_Arbol a, int *contador) {
    if (a != NULL) {
        MostrarIndiceRec(a->izq, contador);
        (*contador)++;
        printf("Elemento %d: %u\n", *contador, a->dato);
        MostrarIndiceRec(a->der, contador);
    }
}

void MostrarIndice(T_Arbol a) {
    int contador = 0;
    MostrarIndiceRec(a, &contador);
}

