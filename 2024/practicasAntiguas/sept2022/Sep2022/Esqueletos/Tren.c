/*
 * Examen Septiembre 2022 PSC - todos los grupos.
 * Implementación Tren.c
 */

#include "Tren.h"
#include <stdlib.h>
#include <stdio.h>
#include <string.h>

/// @brief Inicializa el tren creado en el Principal que tiene maximoVagones (Vagon *tren) con todos los vagones vacío.
/// @param tren Array que representa el tren.
/// @param maximoVagones Número de vagones que tiene el tren (tamaño del array).
/// 0.25 pts
void inicializarTren(Vagon *tren, int maximoVagones)
{
    for (int i = 0; i < maximoVagones; i++)
    {
        tren[i] = NULL;
        tren[i]->num = i;
    }
}

/// @brief Inserta los datos de un nuevo pasajero. Sí el asiento está libre se lo asigna y si está ocupado ignora la petición.
/// @param tren Array con los vagones y pasajeros que tiene el tren actualmente, en el que se debe insertar.
/// @param numeroVagon Vagón en el que se quiere sentar el pasajero.
/// @param numeroAsiento Asiento dentro del vagón en el que se quiere sentar el pasajero.
/// @param nombre Nombre del pasajero.
/// @return Si el asiento ya está ocupado, devuelve -1, si no, devuelve 0.
/// 1.75 pts
int entraPasajero(Vagon *tren, unsigned numeroVagon, unsigned numeroAsiento, char *nombre)
{
    Vagon ptr = (Vagon) malloc (sizeof(struct Asiento));
    int ocupado = 0;
    ptr->num = numeroAsiento;
    strcpy(ptr->nombre, nombre);

    if(tren[numeroVagon] == NULL) 
    {
        ptr->sig = NULL;
        tren[numeroVagon] = ptr;
    }else {
        Vagon aux = tren[numeroVagon];
        Vagon ant = NULL;
        while(aux != NULL && ocupado == 0 && numeroAsiento > aux->num) {
            if(numeroAsiento == aux->num) {
                ocupado = -1;
            }
            ant = aux;
            aux = aux->sig;
        }

        if(aux != NULL && numeroAsiento == aux->num) {
            ocupado = -1;
        }
        if(ocupado == 0 && ant != NULL){
            ant->sig = ptr;
            ptr->sig = aux;
        }else if(ocupado == 0) {
            ptr->sig = aux;
            tren[numeroVagon] = ptr;
        }
    }
    return ocupado;
}

/// @brief Muestra por pantalla los vagones ocupados mostrando en cada línea los datos de un pasajero. Por ejemplo:
/*
Vagon 0:
Asiento 2, Carlos García
Asiento 4, Ismael Canario
Vagon 2:
Asiento 1, Macarena Sol
*/
/// @param tren Array con los vagones y pasajeros que tiene el tren actualmente.
/// @param maximoVagones Máximo de vagones que tiene el tren.
/// 0.75 pt
void imprimeTren(Vagon *tren, unsigned maximoVagones)
{
    for (int i = 0; i < maximoVagones; i++)
    {
        printf("Vagon %d: \n", i);
        Vagon ptr = tren[i];
        while (ptr != NULL)
        {
            printf("Asiento %d, %s \n", ptr->num, ptr->nombre);
            ptr = ptr->sig;
        }
    }
}

/// @brief El pasajero abandona el tren (es eliminado de la estructura).
/// @param tren Array con los vagones y pasajeros que tiene el tren actualmente.
/// @param numeroVagon Vagón en el que se está el pasajero que abandona el tren.
/// @param numeroAsiento Asiento en el que se está el pasajero que abandona el tren.
/// @return Devuelve 0 si el pasajero abandona el tren y -1 si no había pasajero en el vagón y asiento indicados.
/// 1.5 pts
int salePasajero(Vagon *tren, unsigned numeroVagon, unsigned numeroAsiento)
{
    int abandona = -1;

    if(tren[numeroVagon] != NULL) {
        Vagon aux = tren[numeroVagon];
        Vagon ant = NULL;

        while(aux != NULL && aux->num != numeroAsiento) {
            ant = aux;
            aux = aux->sig;
        }
        if(aux != NULL) {
            if(ant != NULL){
                ant->sig = aux->sig;
                free(aux);
            }else{
                tren[numeroVagon] = aux->sig;
                free(aux);
            }
        }
    }
    return abandona;
}

/// @brief Intercambia dos pasajeros. Para ello es suficiente intercambiar el NOMBRE del viajero que está en el asiento \p numeroAsiento1 del vagón\p numeroVagon1, con el del viajero que está en el asiento \p numeroAsiento2 del vagón \p numeroVagon2.
/// @param tren Array con los vagones y pasajeros que tiene el tren actualmente.
/// @param numeroVagon1 Vagón en el que se está el pasajero 1 que quiere cambiarse de sitio.
/// @param numeroAsiento1 Asiento en el que se está el pasajero 1 que quiere cambiarse de sitio.
/// @param numeroVagon2 Vagón en el que se está el pasajero 2 que quiere cambiarse de sitio.
/// @param numeroAsiento2 Asiento en el que se está el pasajero 2 que quiere cambiarse de sitio.
/// @return Si algún asiento no está ocupado, devuelve -1. Si se puede realizar el cambio, devuelve 0.
/// 1.75 pts
int intercambianAsientos(Vagon *tren, unsigned numeroVagon1, unsigned numeroAsiento1, unsigned numeroVagon2, unsigned numeroAsiento2)
{
    int cambio = 0;
    char aux[30];
    Vagon ptrPrimero =tren[numeroVagon1];
    Vagon ptrSegundo = tren[numeroVagon2];

    while(ptrPrimero != NULL && ptrPrimero->num != numeroAsiento1) {
        ptrPrimero = ptrPrimero->sig;
    }

    while(ptrSegundo != NULL && ptrSegundo->num != numeroAsiento1) {
        ptrSegundo = ptrSegundo->sig;
    }

    if(ptrPrimero == NULL || ptrSegundo == NULL) {
        cambio = -1;
    }else{
        strcpy(aux, ptrPrimero->nombre);
        strcpy(ptrPrimero->nombre, ptrSegundo->nombre);
        strcpy(ptrSegundo->nombre, aux);
    }
    return cambio;
}

/// @brief El tren llega a la última parada y bajan todos los pasajeros del tren. El tren debe quedar vacío.
/// @param tren Array con los vagones y pasajeros que tiene el tren actualmente.
/// @param maximoVagones Maximo de vagones que tiene el tren.
/// 1 pt
void ultimaParada(Vagon *tren, unsigned maximoVagones)
{
    for (int i = 0; i < maximoVagones; i++)
    {
        Vagon ptr = tren[i];
        while (tren[i] != NULL)
        {
            tren[i] = tren[i]->sig;
            free(ptr);
            ptr = tren[i];
        }
    }
}

/// @brief Guarda en fichero de TEXTO los datos de los pasajeros en el tren. El formato del fichero de texto será el siguiente, una primera línea con el siguiente texto:
// Vagon;Asiento;Nombre
// Tras esta línea, incluirá una línea por cada pasajero, ordenados por vagón primero y luego por número de asiento.
// 0;2;Carmen Garcia
// 0;3;Pepe Perez
// 1;5;Adela Gamez
// 1;7;Josefa Valverde
/// @param filename Nombre del fichero en el que se van a almacenar los datos de los pasajeros del tren.
/// @param tren Array con los vagones y pasajeros que tiene el tren actualmente.
/// @param maximoVagones Máximo de vagones que tiene el tren.
/// 1.5 pts
void almacenarRegistroPasajeros(char *filename, Vagon *tren, unsigned maximoVagones)
{
    FILE *pfile = fopen(filename, "w");

    if(pfile == NULL) {
        perror("ERROR al abrir el fichero");
        exit(-1);
    }

    fprintf(pfile, "Vagon; Asiento; Nombre\n");
    for(int i = 0; i < maximoVagones; i++) {
        Vagon ptr = tren[i];
        while(ptr != NULL){
            fprintf(pfile, "%u;%u;%s\n", i, ptr->num, ptr->nombre);
            ptr = ptr->sig;
        }
    }
    fclose(pfile);
}

/// @brief Algunas estaciones están automatizadas y proporcionan un fichero con los pasajeros que van a entrar en un vagón en su parada.
// Esta función carga los pasajeros que están en el fichero BINARIO filename en el
// vagón numeroVagon. Se asume que los pasajeros almacenados en el fichero no van a
// sentarse en asientos previamente ocupados.
// El fichero binario almacena la información de cada pasajero con la siguiente
// estructura:
// - Un entero con el número de asiento.
// - La cadena de caracteres con el nombre.
/// @param filename Nombre del fichero que contiene los datos de los pasajeros del vagon.
/// @param tren Array con los vagones y pasajeros que tiene el tren actualmente.
/// @param numeroVagon Vagon del que se van a importar los pasajeros.
/// 1.5 pts
void importarPasajerosVagon(char *filename, Vagon *tren, unsigned numeroVagon)
{
    FILE *pfile = fopen(filename, "rb");
    if(pfile == NULL) {
        perror("ERROR al abrir el fichero");
        exit(-1);
    }
    int asiento;
    char nombre[30];
    while(fread(&asiento, sizeof(int), 1, pfile) != 0) {
        fread(&nombre, sizeof(nombre), 1, pfile);
        entraPasajero(tren, numeroVagon, asiento, nombre);
    }
    fclose(pfile);
}
