#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "cifrado.h"

/* (0.5 puntos) funcion necesaria para crear un esquema de cifrado vacio, sin nodos*/
void crearEsquemaDeCifrado(TCifrado *cf)
{
    *cf = NULL;
}

TCifrado crearBox(struct TBox box)
{
    TCifrado aux = (TCifrado)malloc(sizeof(struct TBox));
    aux->bitACambiar = box.bitACambiar;
    aux->esSBox = box.esSBox;
    aux->valorASumar = box.valorASumar;
    aux->sig = NULL;
    return aux;
}

/* (3 puntos) funcion que pone un nodo al final de un esquema de cifrado, si es posible.
 * Se debe devolver en el ultimo parametro un valor logico que sea verdadero si ha sido posible
 * realizar la operacion. No se debe suponer que el valor de box.sig es valido*/
void insertarBox(TCifrado *cf, struct TBox box, unsigned char *ok)
{
    if(*cf == NULL) {
        *ok = box.esSBox;
        if(*ok) {
            *cf = crearBox(box);
        }
    } else {
        TCifrado ptr = *cf;
        while(ptr->sig != NULL) {
            ptr = ptr->sig;
        }
        *ok = ptr->esSBox != box.esSBox;

        if(*ok) {
            ptr->sig = crearBox(box);
        
        }
    }
}

/* (1.5 puntos) funcion que dado un nodo y un valor, devuelve
 * el resultado de aplicar dicho nodo a dicho valor. Deberas
 * de tener en cuenta si el nodo es una SumaBox o una XORBox.
 * En el ultimo caso, necesitaras usar operadores logicos a
 * nivel de bit, como &, |, ^ o bien ~, asi como probablemente
 * usar constantes  numericas. */
unsigned char cambiarBit0(unsigned char valor){
	if((valor &1) == 1){
		return (valor & ~1);
	}else{
		return (valor | 1);
	}
}

unsigned char cambiarBit7(unsigned char valor){
	if(((valor & (1<<7)) >> 7) == 1){
		return (valor & ~(1<<7));
	}else{
		return (valor | (1<<7));
	}
}


unsigned char aplicarBox(struct TBox box, unsigned char valor)
{
    if(box.esSBox) {
        valor += box.valorASumar;
    } else {
        if(box.bitACambiar == 1) {
            valor = cambiarBit0(valor);
        } else {
            valor = cambiarBit7(valor);
        
        }
    }
    return valor;
}

/* (1.5 puntos) funcion que toma un esquema de cifrado y un valor,
 * y devuelve el resultado de aplicar dicho esquema de cifrado a
 * dicho valor, segun el metodo descrito anteriormente.*/
unsigned char aplicarEsquemaDeCifrado(TCifrado cf, unsigned char valor)
{
    struct TBox box;
    while(cf != NULL) {
        box.bitACambiar = cf->bitACambiar;
        box.esSBox = cf->esSBox;
        box.valorASumar = cf->valorASumar;
        valor = aplicarBox(box, valor);
        cf = cf->sig;
    }
    return valor;
}

/* (2.5 puntos) funcion que toma un nombre de fichero, en el que se
 *  escribiran en modo binario los datos correspondientes al esquema
 *  de cifrado que se pasa como parametro, de modo que el al final el
 *  fichero unicamente contenga dichos datos. Si no se puede abrir el
 *  fichero, se debe de mostrar un mensaje de error por pantalla.*/
void escribirAFichero(char *nm, TCifrado cf)
{
    FILE * archivo = fopen(nm, "wb");
    if(archivo == NULL) {
        printf("Error al abrir el archivo\n");
        return;
    }else{
        while(cf != NULL) {
            fwrite(&(cf->bitACambiar), sizeof(unsigned char), 1, archivo);
            fwrite(&(cf->esSBox), sizeof(unsigned char), 1, archivo);
            fwrite(&(cf->valorASumar), sizeof(unsigned char), 1, archivo);
            cf = cf->sig;
        }
        fclose(archivo);
    }
}

/* (1.0 puntos) funcion que destruye un esquema de cifrado y libera la memoria que ocupa*/
void destruirEsquemaDeCifrado(TCifrado *cf)
{
    TCifrado ptr = *cf, ant;

    while(ptr != NULL) {
        ant = ptr;
        ptr = ptr->sig;
        free(ant);
    }
    *cf = NULL;
}
