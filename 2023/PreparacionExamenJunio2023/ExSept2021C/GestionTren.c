#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "GestionTren.h"

/* 0.5 ptos: al salir de la cochera el tren está vacío (equivale a inicializar el tren) */
Tren salirCochera()
{
    Tren tren = NULL;

    return tren;
}

/* 0.5 ptos: Mostrar por pantalla los pasajeros que están en el tren.
 *          Hay que mostrar el asiento y destino de cada uno */
void mostrarPasajeros(Tren t)
{
    if(t == NULL) {
        return;
    }
    while(t != NULL) {
        printf("Asiento: %d - Destino: %c", t->asiento, t->destino);
        t = t->sig;
    }
}

/* 1 pto: al entrar a la cochera tienen que bajarse todos los pasajeros que haya en ese momento en el tren,
 *       independientemente de cuál sea su destino.
 *       Devuelve el número de pasajeros que se han bajado en la cochera */
int entrarCochera(Tren *t)
{
    int contador = 0;
    Tren ptr;
    while((*t)->sig != NULL) {
        contador++;
        ptr = (*t);
        (*t) = (*t)->sig;
        free(ptr);
    }
    return contador;
}

/* 2 ptos: un pasajero intenta subir al tren.
 *         Si el asiento está disponible se almacena en el tren el asiento y destino del pasajero y se devuelve 1.
 *         Si el asiento está ocupado no se modifica la lista y se devuelve 0. */
int subirPasajero(Tren* t, int asiento, char* destino)
{
    Tren nuevoPasajero = (Tren)malloc(sizeof(struct Pasajero));
    if (nuevoPasajero == NULL) {
        return 0;
    }

    nuevoPasajero->asiento = asiento;
    strcpy(nuevoPasajero->destino, destino);
    nuevoPasajero->sig = NULL;

    if (*t == NULL) {
        // Si la lista está vacía, el nuevo pasajero será el primer elemento
        *t = nuevoPasajero;
        return 1;
    }

    // Si el asiento del nuevo pasajero es menor que el primer asiento de la lista,
    // el nuevo pasajero se convierte en el primer elemento
    if (asiento < (*t)->asiento) {
        nuevoPasajero->sig = *t;
        *t = nuevoPasajero;
        return 1;
    }

    Tren actual = *t;
    while (actual->sig != NULL) {
        // Si el asiento del nuevo pasajero es menor que el siguiente asiento,
        // se inserta en medio de los dos nodos
        if (asiento < actual->sig->asiento) {
            nuevoPasajero->sig = actual->sig;
            actual->sig = nuevoPasajero;
            return 1;
        }
        actual = actual->sig;
    }

    // Si el asiento del nuevo pasajero es mayor que todos los asientos existentes,
    // se inserta al final de la lista
    actual->sig = nuevoPasajero;
    return 1;
}


/* 2 ptos: cuando el tren llega a una parada, se bajan del tren (se extraen de la lista)
 *         todos los pasajeros con ese destino.
 *	       La función devuelve el número de pasajeros que se han bajado */
int parada(Tren *t, char *parada)
{
   int numPasajero = 0;
   Tren ptr = *t;
   Tren pasajeroAnterior = NULL;

    while (ptr != NULL) {
        if(strcmp(ptr->destino, parada) == 0) {
            if(pasajeroAnterior == NULL) {
                //Primero de la lista
                *t = ptr->sig;
            }else{
                //No es el primero de la lista
                pasajeroAnterior->sig = ptr->sig;
            }
            Tren pasajeroAEliminar = ptr;
            ptr = ptr->sig;
            free(pasajeroAEliminar);
            numPasajero++;
        }else{
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
    if(archivo == NULL) {
        return;
    }
    Tren pasajeroActual = t;
    while(pasajeroActual != NULL) {
        fwrite(&(pasajeroActual->asiento), sizeof(int), 1, archivo);
        int longitudDestino = strlen(pasajeroActual->destino);
        fwrite(&longitudDestino, sizeof (int), 1, archivo);
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
    if(archivo == NULL) {
        return NULL;
    }
    Tren tren = NULL;
    Tren pasajeroAnterior = NULL;

    while(!feof(archivo)) {
        Tren nuevoPasajero = (Tren)malloc(sizeof(struct Pasajero));
        if(nuevoPasajero == NULL){
            break;
        }

        if(fread(&(nuevoPasajero->asiento), sizeof(int),1,archivo) != 1) {
            free(nuevoPasajero);
            break;
        }

        int longitudDestino;
        if(fread(&longitudDestino, sizeof(int), 1, archivo) != 1) {
            free(nuevoPasajero);
            break;
        }

        nuevoPasajero->destino[longitudDestino] = '\0';
        if(fread(nuevoPasajero->destino, sizeof (char), longitudDestino, archivo) != longitudDestino) {
            free(nuevoPasajero);
            break;
        }

        nuevoPasajero->sig = NULL;
        if(tren == NULL) {
            tren = nuevoPasajero;
        }else{
            pasajeroAnterior->sig = nuevoPasajero;
        }
        pasajeroAnterior->sig = nuevoPasajero;
    }
    fclose(archivo);
    return tren;
}
