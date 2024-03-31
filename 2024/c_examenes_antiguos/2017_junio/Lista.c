#include <stdio.h>
#include <stdlib.h>
#include "lista.h"

/*
 * Inicializa la lista de puntos creando una lista vacía
 *
 */
void crearLista(TLista *lista)
{
    *lista = NULL;
}

/**
 * Inserta el punto de forma ordenada (por el valor de la abscisa x)
 * en la lista siempre que no esté repetida la abscisa.
 *  En ok, se devolverá un 1 si se ha podido insertar, y  0 en caso contrario.
 *  Nota: utiliza una función auxiliar para saber
 *   si ya hay un punto en la lista con la misma abscisa punto.x
 *
 */

// Función auxiliar para verificar si ya existe un punto con la misma abscisa en la lista
int existePunto(TLista lista, float x)
{
    while (lista != NULL)
    {
        if (lista->punto.x == x)
        {
            return 1; // Devuelve 1 si encuentra un punto con la misma abscisa
        }
        lista = lista->sig;
    }
    return 0; // Devuelve 0 si no encuentra ningún punto con la misma abscisa
}

void insertarPunto(TLista *lista, struct Punto punto, int *ok)
{
    if (existePunto(*lista, punto.x))
    {
        *ok = 0; // Indica que no se pudo insertar el punto porque ya existe un punto con la misma abscisa
        return;
    }

    // Crear un nuevo nodo para el punto a insertar
    struct Nodo *nuevoNodo = (struct Nodo *)malloc(sizeof(struct Nodo));
    if (nuevoNodo == NULL)
    {
        printf("Error: No se pudo asignar memoria para el nuevo nodo\n");
        exit(EXIT_FAILURE);
    }
    nuevoNodo->punto = punto;
    nuevoNodo->sig = NULL;

    // Caso especial: lista vacía o el punto debe ir al inicio de la lista
    if (*lista == NULL || (*lista)->punto.x > punto.x)
    {
        nuevoNodo->sig = *lista;
        *lista = nuevoNodo;
        *ok = 1; // Indica que se insertó el punto correctamente
        return;
    }

    // Buscar la posición de inserción del nuevo punto
    struct Nodo *actual = *lista;
    while (actual->sig != NULL && actual->sig->punto.x < punto.x)
    {
        actual = actual->sig;
    }

    // Insertar el nuevo punto después del nodo actual
    nuevoNodo->sig = actual->sig;
    actual->sig = nuevoNodo;

    *ok = 1; // Indica que se insertó el punto correctamente
}

/*
 * Elimina de la lista el punto con abscisa x de la lista.
 * En ok devolverá un 1 si se ha podido eliminar,
 * y un 0 si no hay ningún punto en la lista con abscisa x
 *
 */
void eliminarPunto(TLista *lista, float x, int *ok)
{
    if (*lista == NULL)
    {
        *ok = 0; // Indica que no se pudo eliminar el punto porque la lista está vacía
        return;
    }

    struct Nodo *anterior = NULL;
    struct Nodo *actual = *lista;

    // Buscar el punto con abscisa x en la lista
    while (actual != NULL && actual->punto.x != x)
    {
        anterior = actual;
        actual = actual->sig;
    }

    // Si el punto no se encontró, no se elimina nada
    if (actual == NULL)
    {
        *ok = 0; // Indica que no se pudo eliminar el punto porque no se encontró en la lista
        return;
    }

    // Si el punto se encuentra en el primer nodo de la lista
    if (anterior == NULL)
    {
        *lista = actual->sig;
    }
    else
    {
        anterior->sig = actual->sig;
    }

    free(actual);
    *ok = 1; // Indica que se eliminó el punto correctamente
}

/**
 * Muestra en pantalla el listado de puntos
 */
void mostrarLista(TLista lista)
{
    TLista aux = lista;
    while (aux != NULL)
    {
        printf("Punto: (%f, %f)\n", aux->punto.x, aux->punto.y);
        aux = aux->sig;
    }
}

/**
 * Destruye la lista de puntos, liberando todos los nodos de la memoria.
 */
void destruir(TLista *lista)
{
    struct Nodo *actual = *lista;
    while (actual != NULL)
    {
        struct Nodo *siguiente = actual->sig;
        free(actual);
        actual = siguiente;
    }
    *lista = NULL; // Establecer el puntero de la lista a NULL
}

/*
 * Lee el contenido del archivo binario de nombre nFichero,
 * que contiene una secuencia de puntos de una función polinómica,
 *  y lo inserta en la lista.
 *
 */

// Función auxiliar para insertar un punto al final de la lista
void insertarFinal(TLista *lista, struct Punto punto) {
    // Crear un nuevo nodo para el punto a insertar
    struct Nodo *nuevoNodo = (struct Nodo *)malloc(sizeof(struct Nodo));
    if (nuevoNodo == NULL) {
        printf("Error: No se pudo asignar memoria para el nuevo nodo\n");
        exit(EXIT_FAILURE);
    }
    nuevoNodo->punto = punto;
    nuevoNodo->sig = NULL;

    // Si la lista está vacía, el nuevo nodo se convierte en el primer nodo
    if (*lista == NULL) {
        *lista = nuevoNodo;
        return;
    }

    // Encontrar el último nodo de la lista
    struct Nodo *actual = *lista;
    while (actual->sig != NULL) {
        actual = actual->sig;
    }

    // Insertar el nuevo nodo al final de la lista
    actual->sig = nuevoNodo;
}


void leePuntos(TLista *lista, char *nFichero) {
    // Abrir el archivo binario para lectura en modo binario
    FILE *file = fopen(nFichero, "rb");
    if (file == NULL) {
        printf("Error: No se pudo abrir el archivo %s\n", nFichero);
        exit(EXIT_FAILURE);
    }

    // Leer los puntos del archivo y agregarlos a la lista
    struct Punto punto;
    while (fread(&punto, sizeof(struct Punto), 1, file) == 1) {
        insertarFinal(lista, punto);
    }

    // Cerrar el archivo
    fclose(file);
}