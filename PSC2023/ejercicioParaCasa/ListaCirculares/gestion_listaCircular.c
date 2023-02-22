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
    if (*ptr_lista == NULL)
    {
        return; // La lista está vacía, no hay nada que eliminar
    }

    ptr_lista_entero nodo_actual = *ptr_lista;
    ptr_lista_entero nodo_siguiente = nodo_actual->sig;

    while (nodo_siguiente != *ptr_lista)
    {
        free(nodo_actual);
        nodo_actual = nodo_siguiente;
        nodo_siguiente = nodo_actual->sig;
    }

    // Eliminar el último nodo de la lista
    free(nodo_actual);

    // Asignar el puntero de la lista a NULL
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
        nuevo_elemento->sig = *ptr_lista;
        ptr_lista_entero actual = *ptr_lista;
        while (actual->sig != *ptr_lista)
        {
            actual = actual->sig;
        }
        actual->sig = nuevo_elemento;
        *ptr_lista = nuevo_elemento;
    }
    *ok = 1;
}

// ptr_lista tiene como variables numero y sig
// Hay que almacenar valor al final de la lista
// Es una lista circular, por tanto, el ultimo apunta al primero
// Valor hay que introducirlo despues del ultimo
// Dada la lista 1 -> 2 , queremos introducir el 5
// La lista debe de quedar 1 -> 2 -> 5
void insertarCola(ptr_lista_entero *ptr_lista, int valor, int *ok)
{
    // Crear un nuevo nodo y asignar el valor que se desea agregar
    ptr_lista_entero nuevo_nodo = malloc(sizeof(struct Lista_Entero));
    if (nuevo_nodo == NULL)
    {
        *ok = 0; // Hubo un error al asignar memoria
        return;
    }
    nuevo_nodo->numero = valor;
    nuevo_nodo->sig = NULL;

    // Si la lista está vacía, hacer que el nuevo nodo sea el primer nodo y que apunte a sí mismo
    if (*ptr_lista == NULL)
    {
        nuevo_nodo->sig = nuevo_nodo;
        *ptr_lista = nuevo_nodo;
        *ok = 1;
        return;
    }

    // Recorrer la lista hasta llegar al último nodo
    ptr_lista_entero ultimo_nodo = *ptr_lista;
    while (ultimo_nodo->sig != *ptr_lista)
    {
        ultimo_nodo = ultimo_nodo->sig;
    }

    // Hacer que el último nodo apunte al nuevo nodo y que el nuevo nodo apunte al primer nodo de la lista
    ultimo_nodo->sig = nuevo_nodo;
    nuevo_nodo->sig = *ptr_lista;
    *ok = 1;
}

void insertarOrdenado(ptr_lista_entero *ptr_lista, int valor, int *ok)
{
    ptr_lista_entero nuevo_nodo = (ptr_lista_entero)malloc(sizeof(struct Lista_Entero));
    if (nuevo_nodo == NULL)
    {
        *ok = 0;
        return;
    }
    nuevo_nodo->numero = valor;
    nuevo_nodo->sig = NULL;

    if (*ptr_lista == NULL || valor <= (*ptr_lista)->numero)
    {
        insertarCabeza(ptr_lista, valor, ok);
        return;
    }
    ptr_lista_entero actual = *ptr_lista;
    while (actual->sig != *ptr_lista && actual->sig->numero < valor)
    {
        actual = actual->sig;
    }
    nuevo_nodo->sig = actual->sig;
    actual->sig = nuevo_nodo;
    *ok = 1;
}

void borrarCabeza(ptr_lista_entero *ptr_lista, int *ok)
{
    if (*ptr_lista == NULL)
    {
        *ok = 0;
        return;
    }
    ptr_lista_entero cabeza = *ptr_lista;
    ptr_lista_entero nueva_cabeza = cabeza->sig;

    if (nueva_cabeza == cabeza)
    {
        *ptr_lista = NULL; // Solo queda un elemento en la lista
    }
    else
    {
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
    if (*ptr_lista == NULL)
    {
        *ok = 0;
        return;
    }

    ptr_lista_entero actual = *ptr_lista;
    ptr_lista_entero anterior = NULL;
    while (actual->sig != *ptr_lista)
    {
        anterior = actual;
        actual = actual->sig;
    }
    if (anterior == NULL)
    {
        // Solo queda un elemento en la lista
        *ptr_lista = NULL;
    }
    else
    {
        anterior->sig = *ptr_lista;
    }
    free(actual);
    *ok = 1;
}

// Dada ptr_lista que es una lista circular con varibles: sig y numero
// quitarNCabeza elimina los n primeros valores de ptr_lista
// Se almacena en m, la cantidad de valores que ha sido capaz de quitar,
// ya que si n es mayor que la lista, no se podran quitar todos
ptr_lista_entero quitarNCabeza(ptr_lista_entero * ptr_lista, int n, int * m) {
    // Inicialización de variables
    ptr_lista_entero extraidos = NULL;
    ptr_lista_entero ptr_extraidos = NULL;
    ptr_lista_entero ptr_nueva_lista = NULL;
    int i;

    // Comprobar si la lista está vacía
    if (*ptr_lista == NULL) {
        *m = 0;
        return NULL;
    }

    // Extraer los primeros n nodos
    ptr_lista_entero ptr_actual = *ptr_lista;
    for (i = 0; i < n && ptr_actual != NULL; i++) {
        // Agregar el nodo extraído a la lista circular
        if (ptr_extraidos == NULL) {
            ptr_extraidos = ptr_actual;
            extraidos = ptr_extraidos;
        } else {
            ptr_extraidos->sig = ptr_actual;
            ptr_extraidos = ptr_extraidos->sig;
        }
        ptr_extraidos->sig = NULL;

        // Actualizar el puntero al siguiente nodo en la lista original
        ptr_actual = ptr_actual->sig;
    }

    // Actualizar el puntero a la lista original
    *ptr_lista = ptr_actual;

    // Actualizar el puntero del último nodo de la sección extraída
    if (ptr_extraidos != NULL) {
        ptr_extraidos->sig = *ptr_lista;
    }

    // Actualizar el puntero a la nueva lista circular
    ptr_nueva_lista = extraidos;

    // Contar cuántos nodos se han extraído
    *m = i;

    // Retornar la lista circular de nodos extraídos
    return ptr_nueva_lista;
}

//Esta copiando la lista ptr_lista en extraidos, pero ptr_lista la deja vacia



