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
    T_Lista nuevo = (T_Lista)malloc(sizeof(struct T_Nodo));
    if (nuevo == NULL)
    {
        *ok = 0;
        return;
    }
    nuevo->fecha = *fecha;
    nuevo->duracion = duracion;
    nuevo->sig = NULL;

    T_Lista actual = *ptr_lista_nuevos;
    T_Lista anterior = NULL;
    while (actual != NULL && actual->fecha < nuevo->fecha)
    {
        anterior = actual;
        actual = actual->sig;
    }

    if (anterior == NULL)
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
    T_Lista nodo_anterior = NULL;
    T_Lista nodo_actual = *ptr_lista_nuevos;
    T_Lista ultimo_procesado = NULL;

    while (nodo_actual != NULL)
    {
        if (nodo_actual->fecha >= *fecha)
        {
            if (nodo_anterior == NULL)
            {
                *ptr_lista_nuevos = nodo_actual->sig;
            }
            else
            {
                nodo_anterior->sig = nodo_actual->sig;
            }

            nodo_actual->sig = NULL;

            if (*ptr_lista_procesados == NULL)
            {
                *ptr_lista_procesados = nodo_actual;
            }
            else
            {
                ultimo_procesado->sig = nodo_actual;
            }

            ultimo_procesado = nodo_actual;
            nodo_actual = nodo_anterior == NULL ? *ptr_lista_nuevos : nodo_anterior->sig;
        }
        else
        {
            nodo_anterior = nodo_actual;
            nodo_actual = nodo_actual->sig;
        }
    }
}

void destruir(T_Lista *ptr_lista_nuevos, T_Lista *ptr_lista_procesados)
{
    T_Lista aux1 = *ptr_lista_nuevos;
    while (aux1 != NULL)
    {
        T_Lista aux2 = aux1;
        aux1 = aux1->sig;
        free(aux2);
    }
    *ptr_lista_nuevos = NULL;

    aux1 = *ptr_lista_procesados;
    while (aux1 != NULL)
    {
        T_Lista aux2 = aux1;
        aux1 = aux1->sig;
        free(aux2);
    }
    *ptr_lista_procesados = NULL;
}
