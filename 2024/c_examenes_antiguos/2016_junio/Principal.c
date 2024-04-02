/*
 * Principal.c
 *
 *  Created on: 14/6/2016
 *      Author: esc
 */

#include "ListaJugadores.h"
#include <stdio.h>



// Lee el fichero y lo introduce en la lista
void cargarFichero (char * nombreFich, TListaJugadores *lj)
{
	FILE *file = fopen(nombreFich, "rb");
	if(file == NULL) {
		perror("No se ha podido abrir el fichero");
		return;
	}

	unsigned int id, numGoles;
	
	while(fread(&id, sizeof(unsigned int), 1, file) == 1) {
		insertar(lj, id);
	}

}


int main(){

	TListaJugadores lj;
	crear(&lj);
    unsigned int num_goles;
	cargarFichero ("goles.bin",&lj);
	printf("Hay un total de %d jugadores\n",longitud(lj));
	fflush(stdout);

	recorrer(lj);
	fflush(stdout);
	printf("Introduce un n�mero de goles: \n");
	fflush(stdout);
	scanf("%d",&num_goles);


	eliminar(&lj,num_goles);
	printf("--------------------------------------\n");
	recorrer(lj);
	printf("Hay un total de %d jugadores\n",longitud(lj));
	fflush(stdout);

	printf ("El jugador que m�s goles ha marcado es el que tiene ID: %d",maximo(lj));
	fflush(stdout);
	destruir (&lj);

	return 0;
}
