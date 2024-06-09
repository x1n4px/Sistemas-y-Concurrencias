#include <stdio.h>
#include <stdlib.h>
#include "GestionTren.h"


/* 0.5 ptos: al salir de la cochera el tren está vacío (equivale a inicializar el tren) */
Tren salirCochera(){
    return NULL;
}

/* 0.5 ptos: Mostrar por pantalla los pasajeros que están en el tren. 
  *          Hay que mostrar el asiento y destino de cada uno */
void mostrarPasajeros(Tren t){
    printf("Pasajeros en el tren:\n");
    while(t != NULL){
        printf("Asiento: %d, Destino: %s\n", t->asiento, t->destino);
        t = t->sig;
    }
    printf("\n");
}

/* 1 pto: al entrar a la cochera tienen que bajarse todos los pasajeros que haya en ese momento en el tren, 
  *       independientemente de cuál sea su destino. 
  *       Devuelve el número de pasajeros que se han bajado en la cochera */
int entrarCochera(Tren *t){
    int cont = 0;
    while(*t != NULL) {
        Tren siguiente = (*t)->sig;
        free(*t);
        *t = siguiente;
        cont++;
    }
    return cont;
}

/* 2 ptos: un pasajero intenta subir al tren. 
 *         Si el asiento está disponible se almacena en el tren el asiento y destino del pasajero y se devuelve 1. 
 *         Si el asiento está ocupado no se modifica la lista y se devuelve 0. */
int subirPasajero(Tren* t, int asiento, char *destino){
    Tren nuevo = malloc(sizeof(struct Pasajero));
    if(nuevo == NULL) return 0;
    nuevo->asiento = asiento;
    strcpy(nuevo->destino, destino);
    nuevo->sig = NULL;

    if(*t == NULL || (*t)->asiento > asiento){
        nuevo->sig = *t;
        *t = nuevo;
        return 1;
    }else{
        Tren temp = *t;
        Tren anterior = NULL;
        while(temp != NULL && (temp->asiento < nuevo->asiento)){
            anterior = temp;
            temp = temp->sig;
        }

        if(temp != NULL && (temp->asiento == nuevo->asiento)){
            free(nuevo);
            return 0;
        }else{
            if(anterior != NULL) {
                nuevo->sig = anterior->sig;
                anterior->sig = nuevo;
            }else{
                nuevo->sig = temp;
                *t = nuevo;
            }

            return 1;
        }

    }
}

/* 2 ptos: cuando el tren llega a una parada, se bajan del tren (se extraen de la lista)
 *         todos los pasajeros con ese destino.
 *	       La función devuelve el número de pasajeros que se han bajado */
int parada(Tren *t, char *parada){
    Tren temp = *t;
    Tren anterior = NULL;
    int cont = 0;
    while(temp != NULL) {
        if(strcmp(temp->destino, parada) == 0) {
            if(anterior == NULL) {
                *t = temp->sig;
                free(temp);
                temp = *t;
            }else{
                anterior->sig = temp->sig;
                Tren siguiente = temp->sig;
                free(temp);
                temp = siguiente;
            }
            cont++;
        }else{
            anterior = temp;
            temp = temp->sig;
        
        }
    }
    return cont;
}

/* 2 ptos: Almacena el estado del tren en un fichero binario, cuyo nombre se recibe como parámetro.
 *         Por cada pasajero del tren hay que almacenar: el número del asiento, la longitud del nombre del 
 *         destino, y el nombre del destino (solo los caracteres correspondientes a su longitud). */
void guardarBinario(Tren t, char *fichero){
    FILE * pfile = fopen(fichero, "wb");
    if(pfile == NULL) return;
    int ansiento, lonDestino;
    char destino;

    while(t != NULL) {
        fwrite(&(t->asiento), sizeof(int), 1, pfile);
        lonDestino = strlen(t->destino)+1;
        fwrite(&lonDestino, sizeof(int), 1, pfile);
        fwrite(t->destino, sizeof(char), lonDestino, pfile);
        t = t->sig;
    }
    fclose(pfile);
}

/* 2 ptos: Crear un tren con los datos almacenado en el fichero binario cuyo nombre se recibe como parámetro.
  *        El fichero tendrá el formato generado por la función guardarBinario, es decir que por cada pasajero hay
  *        que leer su asiento, la longitud del nombre del destino y el nombre del destino (solo los caracteres 
  *        correspondientes a su longitud). */
Tren cargarBinario(char *fichero){
    FILE * pfile = fopen(fichero, "rb");
    if(pfile == NULL) return NULL;
    Tren t = NULL;
    int ansiento, lonDestino;
    char destino[MAX_DESTINO];

    while(fread(&ansiento, sizeof(int), 1, pfile) == 1) {
        fread(&lonDestino, sizeof(int), 1, pfile);
        fread(destino, sizeof(char), lonDestino, pfile);
        destino[lonDestino-1] = '\0';
        subirPasajero(&t, ansiento, destino);
    }
    fclose(pfile);
    return t;
}