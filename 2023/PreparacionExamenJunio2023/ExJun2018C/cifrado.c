//
// Created by in4p on 5/24/23.
//
#include "cifrado.h"
#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>


/* (0.5 puntos) funcion necesaria para crear un esquema de cifrado vacio, sin nodos*/
void crearEsquemaDeCifrado (TCifrado *cf){
    *cf = (TCifrado)malloc(sizeof(struct TBox));
    (*cf)->esSBox = 0;
    (*cf)->bitACambiar = 0;
    (*cf)->valorASumar = 0;
    (*cf)->sig = NULL;}

/* (3 puntos) funcion que pone un nodo al final de un esquema de cifrado, si es posible.
 * Se debe devolver en el ultimo parametro un valor logico que sea verdadero si ha sido posible
 * realizar la operacion. No se debe suponer que el valor de box.sig es valido*/
void insertarBox(TCifrado *cf, struct TBox box, unsigned char *ok) {
    *ok = false;
    TCifrado ptr = *cf;

    if (ptr == NULL) {
        return;
    }

    while (ptr->sig != NULL) {
        ptr = ptr->sig; // Obtenemos la última posición
    }
    TCifrado nuevoNodo = (TCifrado)malloc(sizeof(struct TBox));

    if (box.esSBox && !ptr->esSBox) { // Si es una SumaBox
         nuevoNodo->esSBox = box.esSBox;
        nuevoNodo->bitACambiar = CAMBIA_BIT_POS_0; // No se utiliza en SumaBox, se establece a cero
        nuevoNodo->valorASumar = box.valorASumar;
        nuevoNodo->sig = NULL;
         ptr->sig = nuevoNodo;
        *ok = true;
    } else if(!box.esSBox && ptr->esSBox){ // Si es una XORBox

            nuevoNodo->esSBox = false;
            nuevoNodo->bitACambiar = box.bitACambiar;
            nuevoNodo->sig = NULL;

            ptr->sig = nuevoNodo;
            *ok = true;

    }
}

/* (1.5 puntos) funcion que dado un nodo y un valor, devuelve
 * el resultado de aplicar dicho nodo a dicho valor. Deberas
 * de tener en cuenta si el nodo es una SumaBox o una XORBox.
 * En el ultimo caso, necesitaras usar operadores logicos a
 * nivel de bit, como &, |, ^ o bien ~, asi como probablemente
 * usar constantes  numericas. */
unsigned char aplicarBox (struct TBox box, unsigned char valor){
    int resultado;

    if(box.esSBox){ // SumaBox
        resultado = box.valorASumar + valor;
    }else{ // XORBox
        if(box.bitACambiar == CAMBIA_BIT_POS_7){
            resultado = valor ^ ( 1 << 7);
        }else{ // CAMBIA_BIT_POS_1
            resultado = valor ^ ( 1 << 0);
        }
    }
    return resultado;
}

/* (1.5 puntos) funcion que toma un esquema de cifrado y un valor,
 * y devuelve el resultado de aplicar dicho esquema de cifrado a
 * dicho valor, segun el metodo descrito anteriormente.*/
unsigned char aplicarEsquemaDeCifrado(TCifrado cf, unsigned char valor){
    int resultado = valor;
    TCifrado ptr = cf;
    while(ptr->sig != NULL){
        resultado = aplicarBox(*ptr, resultado);
        ptr = ptr->sig;
    }

    return resultado;

}

/* (2.5 puntos) funcion que toma un nombre de fichero, en el que se
 *  escribiran en modo binario los datos correspondientes al esquema
 *  de cifrado que se pasa como parametro, de modo que el al final el
 *  fichero unicamente contenga dichos datos. Si no se puede abrir el
 *  fichero, se debe de mostrar un mensaje de error por pantalla.*/
void escribirAFichero(char *nm, TCifrado cf){
    FILE *fichero  = fopen(nm, "wb");

    if(fichero == NULL || cf == NULL){
        return;
    }

    while(cf!= NULL){
        if(cf->esSBox){
            fwrite(&(cf->esSBox), sizeof(unsigned char), 1, fichero);
            fwrite(&(cf->bitACambiar), sizeof(unsigned char), 1, fichero);
            fwrite(&(cf->valorASumar), sizeof(unsigned char), 1, fichero);
        }else{
            fwrite(&(cf->esSBox), sizeof(unsigned char), 1, fichero);
            fwrite(&(cf->bitACambiar), sizeof(unsigned char), 1, fichero);
        }
      cf = cf->sig;
    }
    fclose(fichero);
}

/* (1.0 puntos) funcion que destruye un esquema de cifrado y libera la memoria que ocupa*/
void destruirEsquemaDeCifrado (TCifrado *cf){
    TCifrado actual = *cf;
    TCifrado siguiente = NULL;
    while(actual->sig != NULL){
        siguiente = actual->sig;
        free(actual);
        actual = siguiente;
    }
    *cf = NULL;
}