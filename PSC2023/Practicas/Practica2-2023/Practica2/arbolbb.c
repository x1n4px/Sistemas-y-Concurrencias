/*
 * arbolbb.c
 *
 *  Created on: 15 mar. 2021
 *      Author: Laura
 */
#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include "arbolbb.h"

// Inicializa la estructura a NULL.
void Crear(T_Arbol *arbol_ptr)
{
    *arbol_ptr = NULL;
}

// Destruye la estructura utilizada.
void Destruir(T_Arbol *arbol_ptr)
{
    if (*arbol_ptr != NULL)
    {
        Destruir(&((*arbol_ptr)->izq));
        Destruir(&((*arbol_ptr)->dato));
        free(*arbol_ptr);
        *arbol_ptr = NULL;
    }
}

// Inserta num en el arbol
void Insertar(T_Arbol *arbol_ptr, unsigned num)
{
    if (*arbol_ptr == NULL)
    {
        T_Arbol nuevo_nodo = (T_Arbol *)malloc(sizeof(struct T_Nodo));
        nuevo_nodo->dato = num;
        nuevo_nodo->izq = nuevo_nodo->der = NULL;
        *arbol_ptr = nuevo_nodo;
    }
    // Si el número es menor que el dato del nodo actual, insertar en el subárbol izquierdo
    else if (num < (*arbol_ptr)->dato)
    {
        Insertar(&((*arbol_ptr)->izq), num);
    }
    // Si el número es mayor que el dato del nodo actual, insertar en el subárbol derecho
    else if(num > (*arbol_ptr)->dato){
        Insertar(&((*arbol_ptr)->der), num);
    }
        // Si el número es igual al dato del nodo actual, no hacer nada (no se permiten duplicados)

}
// Muestra el contenido del árbol en InOrden
void Mostrar(T_Arbol arbol)
{
    if (arbol != NULL)
    {
        Mostrar(arbol->izq);
        printf("%u ", arbol->dato);
        Mostrar(arbol->der);
    }
}

// Guarda en disco el contenido del arbol - recorrido InOrden
void Salvar(T_Arbol arbol, FILE *fichero)
{
    if(arbol != NULL){
        Salvar(arbol->izq, fichero);
        fwrite(&arbol->dato, sizeof(unsigned), 1 ,fichero);
        Salvar(arbol->der, fichero);
    }
}
