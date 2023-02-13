#include <stdlib.h>
#include <stdio.h>
#include "gestion_tremor.h"

void iniciar(T_Lista *ptr_lista_nuevos, T_Lista *ptr_lista_procesados)
{
    *ptr_lista_nuevos = NULL;
    *ptr_lista_procesados = NULL;
}

void mostrar_nuevos2antiguos(T_Lista lista)
{
    if (lista != NULL)
    { // Lista no vacia
        mostrar_nuevos2antiguos(lista->sig);
        char *fecha = ctime(&(lista->fecha));

        printf("Fecha: %s Duracion: %d\n", fecha, lista->duracion);
    }
}

void registrar(T_Lista *ptr_lista_nuevos, const time_t *fecha, unsigned duracion, unsigned *ok)
{
    T_Lista nuevo = (T_Lista)malloc(sizeof(struct T_Nodo)); // reserva memoria dinámica para un nuevo elemento de tipo "T_Lista" y asigna su dirección a la variable "nuevo"
    if (nuevo == NULL)                                      // comprueba si la operación de "malloc" ha fallado, y en caso afirmativo, se establece el valor de "ok" en 0 y se retorna inmediatamente de la función.
    {
        *ok = 0;
        return;
    }
    nuevo->fecha = *fecha;      //  asigna el valor de "fecha" al miembro "fecha" del elemento "nuevo"
    nuevo->duracion = duracion; // asigna el valor de "duracion" al miembro "duracion" del elemento "nuevo"
    nuevo->sig = NULL;          // establece el puntero "sig" del elemento "nuevo" en "NULL", lo que indica que no hay un elemento siguiente en la lista

    T_Lista actual = *ptr_lista_nuevos; // crea una variable local "actual" y la inicializa con el valor apuntado por "ptr_lista_nuevos"
    T_Lista anterior = NULL;            // crea una variable local "anterior" y la inicializa en "NULL"
    while (actual != NULL && actual->fecha < nuevo->fecha)
    { // se repite mientras "actual" no sea "NULL" y la fecha de "actual" sea menor que la fecha de "nuevo"
        anterior = actual;
        actual = actual->sig;
    }

    if (anterior == NULL) // comprueba si "anterior" es "NULL", lo que indica que "nuevo" debe ser insertado al comienzo de la lista
    {
        nuevo->sig = *ptr_lista_nuevos;
        *ptr_lista_nuevos = nuevo;
    }
    else
    {
        anterior->sig = nuevo;
        nuevo->sig = actual;
    }
    *ok = 1;
}

/*
typedef struct T_Nodo *T_Lista; -> time_t fecha; 	unsigned duracion; 	T_Lista sig;
ptr_lista_nuevos -> Almacena todos sus datos en ptr_lista_procesados por orden(de principio de la lista a final)
ptr_lista_nuevos no puede quedar igualada a NULL, ya que tiene que cumplir que ptr_lista_nuevos->sig->sig == NULL
*/

void procesar(T_Lista *ptr_lista_nuevos, const time_t *fecha, T_Lista *ptr_lista_procesados)
{
    T_Lista nodo_anterior = NULL, nodo_actual = *ptr_lista_nuevos; // Asigna el valor inicial de las variables "nodo_anterior" y "nodo_actual" a NULL y al primer elemento de la lista "ptr_lista_nuevos", respectivamente.
    T_Lista ultimo_procesado = NULL;                               // Asigna el valor inicial de la variable "ultimo_procesado" a NULL.

    while (nodo_actual != NULL) // Bucle que se ejecuta mientras "nodo_actual" no sea nulo.
    {
        if (nodo_actual->fecha >= *fecha) // Si la fecha del nodo actual es mayor o igual a la fecha proporcionada.
        {
            if (nodo_anterior == NULL) // Si el nodo anterior es nulo.
            {
                *ptr_lista_nuevos = nodo_actual->sig; // Asigna al puntero "ptr_lista_nuevos" el valor de la siguiente posición en la lista.
            }
            else
            {
                nodo_anterior->sig = nodo_actual->sig; // Enlaza el nodo anterior con la siguiente posición en la lista, saltándose el nodo actual.
            }

            nodo_actual->sig = NULL; // Asigna el valor nulo al siguiente nodo del nodo actual.

            if (*ptr_lista_procesados == NULL) // Si la lista "ptr_lista_procesados" está vacía.
            {
                *ptr_lista_procesados = nodo_actual; // Asigna al puntero "ptr_lista_procesados" el valor de "nodo_actual".
            }
            else
            {
                ultimo_procesado->sig = nodo_actual; // Enlaza el último procesado con "nodo_actual".
            }

            ultimo_procesado = nodo_actual;                                               // Asigna el valor de "nodo_actual" a "ultimo_procesado".
            nodo_actual = nodo_anterior == NULL ? *ptr_lista_nuevos : nodo_anterior->sig; // Avanza al siguiente nodo en la lista.
        }
        else
        {
            nodo_anterior = nodo_actual;    // Asigna el valor de "nodo_actual" a "nodo_anterior".
            nodo_actual = nodo_actual->sig; // Avanza al siguiente nodo en la lista.
        }
    }
}

void destruir(T_Lista *ptr_lista_nuevos, T_Lista *ptr_lista_procesados)
{
    T_Lista aux1 = *ptr_lista_nuevos; // Se inicializa una variable "aux1" con el primer elemento de la lista de "nuevos"
    while (aux1 != NULL)              // Mientras haya elementos en la lista "nuevos"
    {
        T_Lista aux2 = aux1; // Se inicializa una variable "aux2" con el elemento actual de la lista "nuevos"
        aux1 = aux1->sig;    // Se avanza al siguiente elemento de la lista "nuevos"
        free(aux2);          // Se libera la memoria dinámica del elemento actual de la lista "nuevos"
    }

    *ptr_lista_nuevos = NULL; // Se asigna "NULL" a la lista "nuevos" para indicar que está vacía

    aux1 = *ptr_lista_procesados; // Se inicializa una variable "aux1" con el primer elemento de la lista de "procesados"

    while (aux1 != NULL) // Mientras haya elementos en la lista "procesados"
    {
        T_Lista aux2 = aux1; // Se inicializa una variable "aux2" con el elemento actual de la lista "procesados"
        aux1 = aux1->sig;    // Se avanza al siguiente elemento de la lista "procesados"
        free(aux2);          // Se libera la memoria dinámica del elemento actual de la lista "procesados"
    }
    *ptr_lista_procesados = NULL; // Se asigna "NULL" a la lista "procesados" para indicar que está vacía
}
