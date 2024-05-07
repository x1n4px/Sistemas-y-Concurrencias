#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "GestionTren.h"

/* 0.5 ptos: al salir de la cochera el tren está vacío (equivale a inicializar el tren) */
Tren salirCochera()
{
    Tren tren = (Tren)malloc(sizeof(struct Pasajero));
    if (tren != NULL)
    {
        tren->asiento = 0;
        memset(tren->destino, 0, sizeof(tren->destino)); // Inicializar el campo destino como una cadena vacía
        tren->sig = NULL;
    }
    return tren;
}

/* 0.5 ptos: Mostrar por pantalla los pasajeros que están en el tren.
 *          Hay que mostrar el asiento y destino de cada uno */
void mostrarPasajeros(Tren t)
{
    Tren ptr;

    ptr = t;
    while (ptr->sig != NULL)
    {
        printf("El pasajero con asiento %d, tiene destino %s\n", ptr->asiento, ptr->destino);
        ptr = ptr->sig;
    }
}

/* 1 pto: al entrar a la cochera tienen que bajarse todos los pasajeros que haya en ese momento en el tren,
 *       independientemente de cuál sea su destino.
 *       Devuelve el número de pasajeros que se han bajado en la cochera */
int entrarCochera(Tren *t)
{
    int numPasajeros = 0;
    Tren ptr;

    while (t != NULL)
    {
        ptr = *t;
        *t = (*t)->sig;
        free(ptr);
        numPasajeros++;
    }

    return numPasajeros;
}

/* 2 ptos: un pasajero intenta subir al tren.
 *         Si el asiento está disponible se almacena en el tren el asiento y destino del pasajero y se devuelve 1.
 *         Si el asiento está ocupado no se modifica la lista y se devuelve 0. */
int subirPasajero(Tren *t, int asiento, char *destino)
{
    Tren ptr = *t;

    while (ptr != NULL)
    {
        if (ptr->asiento == asiento)
        {
            return 0; // El asiento está ocupado
        }
        ptr = ptr->sig;
    }

    // El asiento esta disponible, se crea un nuevo pasajero y se añade al tren
    Tren nuevoPasajero = (Tren)malloc(sizeof(struct Pasajero));
    if (nuevoPasajero == NULL)
    {
        return 0; // No se pudo asignar memoria
    }

    nuevoPasajero->asiento = asiento;
    strcpy(nuevoPasajero->destino, destino);
    nuevoPasajero->sig = *t;

    *t = nuevoPasajero; // Actualiza el puntero del tren al nuevo pasajero
    return 1;
}

/* 2 ptos: cuando el tren llega a una parada, se bajan del tren (se extraen de la lista)
 *         todos los pasajeros con ese destino.
 *	       La función devuelve el número de pasajeros que se han bajado */
int parada(Tren *t, char *parada)
{
    Tren ptr = *t;
    Tren pasajeroAnterior = NULL;
    int numPasajero = 0;

    while (ptr != NULL)
    {
        if (strcmp(ptr->destino, parada) == 0)
        {
            if (pasajeroAnterior == NULL)
            {
                // El pasajero a eliminar es el primer elemento de la lista
                *t = ptr->sig;
            }
            else
            {
                // El pasajero a eliminar no es el primer elemento de la lista
                pasajeroAnterior->sig = ptr->sig;
            }

            Tren pasajeroAEliminar = ptr;
            ptr = ptr->sig;
            free(pasajeroAEliminar);
            numPasajero++;
        }
        else
        {
            pasajeroAnterior = ptr;
            ptr = ptr->sig;
        }
    }

    return numPasajero;
}

/* 2 ptos: Almacena el estado del tren en un fichero binario, cuyo nombre se recibe como parámetro.
 *         Por cada pasajero del tren hay que almacenar: el número del asiento, la longitud del nombre del
 *         destino, y el nombre del destino (solo los caracteres correspondientes a su longitud). */
void guardarBinario(Tren t, char *fichero)
{
    FILE *archivo = fopen(fichero, "wb");
    if (archivo == NULL)
    {
        printf("Error al abrir el fichero.\n");
        return;
    }
    Tren pasajeroActual = t;
    while (pasajeroActual != NULL) // pasajero1 -> pasajero2 -> pasajero3 -> NULL
    {
        fwrite(&(pasajeroActual->asiento), sizeof(int), 1, archivo);
        int longitudDestino = strlen(pasajeroActual->destino);
        fwrite(&longitudDestino, sizeof(int), 1, archivo);
        fwrite(pasajeroActual->destino, sizeof(char), longitudDestino, archivo);
        pasajeroActual = pasajeroActual->sig;
    }
    fclose(archivo);
}

/* 2 ptos: Crear un tren con los datos almacenado en el fichero binario cuyo nombre se recibe como parámetro.
 *        El fichero tendrá el formato generado por la función guardarBinario, es decir que por cada pasajero hay
 *        que leer su asiento, la longitud del nombre del destino y el nombre del destino (solo los caracteres
 *        correspondientes a su longitud). */
Tren cargarBinario(char *fichero)
{
    FILE *archivo = fopen(fichero, "rb");
    if (archivo == NULL)
    {
        printf("Error al abrir el fichero.\n");
        return;
    }
    Tren tren = NULL;
    Tren pasajeroAnterior = NULL;

    while (!feof(archivo))
    {
        Tren nuevoPasajero = (Tren)malloc(sizeof(struct Pasajero));
        if (nuevoPasajero == NULL)
        {
            printf("Error al asignar memoria para el pasajero.\n");
            break;
        }

        if (fread(&(nuevoPasajero->asiento), sizeof(int), 1, archivo) != 1)
        {
            printf("Error al leer el número de asiento desde el archivo.\n");
            free(nuevoPasajero);
            break;
        }

        int longitudDestino;
        if (fread(&longitudDestino, sizeof(int), 1, archivo) != 1)
        {
            printf("Error al leer la longitud del destino desde el archivo.\n");
            free(nuevoPasajero);
            break;
        }

        nuevoPasajero->destino[longitudDestino] = '\0'; // Asegurar terminación nula
        if (fread(nuevoPasajero->destino, sizeof(char), longitudDestino, archivo) != longitudDestino)
        {
            printf("Error al leer el nombre del destino desde el archivo.\n");
            free(nuevoPasajero);
            break;
        }

        nuevoPasajero->sig = NULL;
        if (tren == NULL)
        {
            tren = nuevoPasajero;
        }
        else
        {
            pasajeroAnterior->sig = nuevoPasajero;
        }

        pasajeroAnterior = nuevoPasajero;
    }
    fclose(archivo);
    return tren;
}
