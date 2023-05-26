/*
 * Principal.c
 *
 *  Created on: 16 jun. 2017
 *      Author: mmar
 */

#include "Lista.h"
#include <stdio.h>
#include <stdlib.h>
#include <time.h>


float f(float x){
	return x*x -625;
}

/*
int main(){

	srand(time(NULL));

	int ok;
	int i = 0;
	TLista l;
	crearLista(&l);

    int v = 10;

    printf("Número de puntos: %d\n",v);

    struct Punto punto;
    for (i=0;i<v;i++){
    		punto.x = 100 * ((float)rand() / (float)RAND_MAX);
    		punto.y = f(punto.x);
    		printf("Introduce el punto (%.2f, %.2f)\n",punto.x,punto.y);
    		insertarPunto(&l,punto,&ok);
    }

    punto.x = 25;
    punto.y = f(punto.x);
    printf("Introduce el punto (%.2f, %.2f)\n",punto.x,punto.y);
    insertarPunto(&l,punto,&ok);
    mostrarLista(l);

    printf("Vamos a eliminar un punto.\n");
    eliminarPunto(&l,25,&ok);
    mostrarLista(l);
    printf("Vamos a eliminar la lista.\n");

    destruir(&l);
    mostrarLista(l);
	return 0;
}*/

int main(){
    printf("MAIN 2.\n");

    TLista l;
	int ok;
	leePuntos(&l,"/home/in4p/git/Sistemas-y-Concurrencias/PSC2023/PreparacionExamenJunio2023/ExJun2017C/Junio2017EsqC/Puntos.bin");
	mostrarLista(l);
    printf("Eliminamos un punto.\n");
	eliminarPunto(&l,25,&ok);
	mostrarLista(l);
    printf("Destruimos la lista.\n");
	destruir(&l);
	mostrarLista(l);
	return 0;
}
