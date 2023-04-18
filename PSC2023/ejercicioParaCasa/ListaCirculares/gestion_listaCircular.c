#include "gestion_listaCircular.h"
#include <assert.h>
#include <stdlib.h>

void actualizar_ultimo(ptr_lista_entero *ptr_lista, ptr_lista_entero antigua_cabeza)
{
    ptr_lista_entero nueva_cabeza = *ptr_lista;

    if ((*ptr_lista)->sig == antigua_cabeza)
        (*ptr_lista)->sig = nueva_cabeza;
    else
    {
        // Tenemos mas de un elemento
        ptr_lista_entero iterator = *ptr_lista;
        while (iterator->sig != antigua_cabeza)
            iterator = iterator->sig;
        iterator->sig = nueva_cabeza;
    }
}

void inicializar(ptr_lista_entero *ptr_lista)
{
    *ptr_lista = NULL;
}

void rotarCabeza(ptr_lista_entero *ptr_lista, int numero_rotaciones, int *ok)
{

    assert(numero_rotaciones > 0);
    *ok = 0;
    if (*ptr_lista != NULL)
    {
        while (numero_rotaciones > 0)
        {
            *ptr_lista = (*ptr_lista)->sig;
            numero_rotaciones--;
        }
        *ok = 1;
    }
}

void destruir(ptr_lista_entero *ptr_lista)
{
    if ((*ptr_lista) != NULL)
    {
        ptr_lista_entero aux;
        ptr_lista_entero cabeza = *ptr_lista;
        do
        {
            aux = *ptr_lista;
            *ptr_lista = (*ptr_lista)->sig;
            free(aux);
        } while (*ptr_lista != cabeza);
        *ptr_lista = NULL;
    }
}

void insertarCabeza(ptr_lista_entero *ptr_lista, int valor, int *ok)
{
    ptr_lista_entero nueva_cabeza = malloc(sizeof(struct Lista_Entero));
    *ok = 0;
    if (nueva_cabeza != NULL)
    {
        nueva_cabeza->numero = valor;
        nueva_cabeza->sig = *ptr_lista;

        // Un solo elemento, el siguiente aputna a la cabeza (él mismo)
        if (nueva_cabeza->sig == NULL)
        {
            nueva_cabeza->sig = nueva_cabeza;
        }
        else
        {
            ptr_lista_entero cabeza_antigua = *ptr_lista; // Almacenamos la cabeza antigua
            ptr_lista_entero aux1 = *ptr_lista;
            while (aux1->sig != cabeza_antigua)
                aux1 = aux1->sig;
            aux1->sig = nueva_cabeza;
        }
        *ptr_lista = nueva_cabeza;
        *ok = 1;
    }
}

// ptr_lista tiene como variables numero y sig
// Hay que almacenar valor al final de la lista
// Es una lista circular, por tanto, el ultimo apunta al primero
// Valor hay que introducirlo despues del ultimo
// Dada la lista 1 -> 2 , queremos introducir el 5
// La lista debe de quedar 1 -> 2 -> 5
void insertarCola(ptr_lista_entero *ptr_lista, int valor, int *ok)
{
    ptr_lista_entero nueva_cola = malloc(sizeof(struct Lista_Entero));
    *ok = 0;
    if (nueva_cola != NULL)
    {
        nueva_cola->numero = valor;
        *ok = 1;
        if ((*ptr_lista) == NULL)
        {
            nueva_cola->sig = nueva_cola;
            (*ptr_lista) = nueva_cola;
        }
        else
        {
            ptr_lista_entero iter = *ptr_lista;
            while (iter->sig != (*ptr_lista))
                iter = iter->sig;
            iter->sig = nueva_cola;
            nueva_cola->sig = *ptr_lista;
        }
    }
}

void insertarOrdenado(ptr_lista_entero *ptr_lista, int valor, int *ok)
{
    ptr_lista_entero nuevo_elemento = malloc(sizeof(struct Lista_Entero));
    *ok = 0;
    if (nuevo_elemento != NULL)
    {
        nuevo_elemento->numero = valor;
        *ok = 1;
        if ((*ptr_lista) == NULL)
        {
            nuevo_elemento->sig = nuevo_elemento;
            (*ptr_lista) = nuevo_elemento;
        }
        else
        {
            ptr_lista_entero iter = *ptr_lista;
            while (iter->sig != (*ptr_lista) && valor > iter->sig->numero)
                iter = iter->sig;
            nuevo_elemento->sig = iter->sig;
            iter->sig = nuevo_elemento;
        }
    }
}

/**
 * @brief Borra el elemento que está en la cabeza, si existe.
 * @param ptr_lista Dirección de memoria de la lista a borrar el elemento
 * @param ok
 */
void borrarCabeza(ptr_lista_entero *ptr_lista, int *ok)
{
    ptr_lista_entero cabeza = *ptr_lista;
    *ok = 0;
    if (cabeza != NULL)
    {
        *ptr_lista = (*ptr_lista)->sig;
        free(cabeza); // Ojo, cabeza sigue apuntando a la misma direccion de memoria que el último->siguiente de la lista
        *ok = 1;
        ptr_lista_entero aux = *ptr_lista;
        if (aux != NULL)
        {
            while (aux->sig != cabeza)
                aux = aux->sig;
            aux->sig = *ptr_lista;
        }
    }
}

/**
 * @brief Borra el elemento que está en la cola, si existe.
 * @param ptr_lista Dirección de memoria de la lista a borrar el elemento
 * @param ok
 */
void borrarCola(ptr_lista_entero *ptr_lista, int *ok)
{
    ptr_lista_entero aux = *ptr_lista;
    *ok = 0;
    if (aux != NULL)
    {
        *ok = 1;
        if (aux->sig == *ptr_lista)
        {
            *ptr_lista = NULL;
            free(aux);
        }
        else
        {
            // Necesitamos un puntero que apunte al anterior al último cuando terminamos de iterar.
            // Sabemos que el último es el que tiene su sig a *ptr_lista
            // Vamos a preguntar por el aux->sig->sig == *ptr_lista, cuando se cumpla,
            // el elemento a borrar es: aux->sig
            while (aux->sig->sig != *ptr_lista)
                aux = aux->sig;
            free(aux->sig);
            aux->sig = *ptr_lista;
        }
    }
}

// Dada ptr_lista que es una lista circular con varibles: sig y numero
// quitarNCabeza elimina los n primeros valores de ptr_lista
// Se almacena en m, la cantidad de valores que ha sido capaz de quitar,
// ya que si n es mayor que la lista, no se podran quitar todos
ptr_lista_entero quitarNCabeza(ptr_lista_entero *ptr_lista, int n, int *m)
{
    assert(n > 0);
    *m = 0;
    ptr_lista_entero a_devolver = *ptr_lista;

    if (a_devolver != NULL)
    { // Si hay elementos para quitar
        if (a_devolver->sig == a_devolver)
        { // Solo un elemento, se devuelve, ya que n es 1 o más
            // Hay un solo elemento
            *ptr_lista = NULL;
            *m = 1;
        }
        else
        { // Hay más de 1 elementos
            ptr_lista_entero iter = *ptr_lista;
            *m = 1;
            while (iter->sig != a_devolver && (*m) < n)
            {
                iter = iter->sig;
                ++(*m);
            }

            if (iter->sig == a_devolver)
            {
                // Tenemos que devolver la lista
                *ptr_lista = NULL;
            }
            else
            {
                *ptr_lista = iter->sig;
                iter->sig = a_devolver;
                // Volvemos a iterar en lo que queda para actualizar su cabeza
                iter = *ptr_lista;
                while (iter->sig != a_devolver)
                    iter = iter->sig;
                iter->sig = *ptr_lista;
            }
        }
    }

    return a_devolver;
}
