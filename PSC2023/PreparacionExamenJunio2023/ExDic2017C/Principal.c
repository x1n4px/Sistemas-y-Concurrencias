/*
 * Principal.c
 *
 *  Created on: 19 dic. 2017
 *      Author: mmar
 */


#include "colaPrioridad.h"
#include <stdio.h>

int main(){


	 TColaPrio colaPrio;
	 crear(colaPrio);
	 mostrar(colaPrio);

	 printf("******************************\n\n");
	 printf("a�adimos nuevos procesos \n\n");
	 printf("******************************\n\n");

	 int i = 0;
	 for (i = 0; i<L; i++){
		 nuevoProceso(colaPrio,i,i);
		 nuevoProceso(colaPrio,i,i+L);
	 }

	 mostrar(colaPrio);

	 printf("******************************\n\n");
	 printf("Ejecutamos el proceso de mayor prioridad \n\n");
	 printf("******************************\n\n");

	 ejecutaProceso(colaPrio);
	 mostrar(colaPrio);

	 printf("******************************\n\n");
	 printf("Ejecutamos el proceso de mayor prioridad \n\n");
	 printf("******************************\n\n");

	 ejecutaProceso(colaPrio);
	 mostrar(colaPrio);


	 printf("******************************\n\n");
	 printf("Buscamos proceso con id 5\n\n");
	 printf("******************************\n\n");

	 int esta=buscar(colaPrio,5);
	 printf(" el proceso de id 5 esta en %d\n\n",esta);

	 printf("******************************\n\n");
	 printf("Buscamos proceso con id 5\n\n");
	 printf("******************************\n\n");

	 esta=buscar(colaPrio,5);
	 printf(" el proceso de id 5 no esta %d\n\n",esta);



	 printf("******************************\n\n");
	 printf("Destruimos todos los procesos \n\n");
	 printf("******************************\n\n");

	 destruir(colaPrio);
	 mostrar(colaPrio);

	 return 0;
}
