#include<stdio.h>
#include<stdlib.h>
#include "Mprocesos.h"

void Crear(LProc *lroundrobin){
	lroundrobin = NULL;
}

void AnadirProceso(LProc *lroundrobin, int idproc){
	LProc new = malloc(sizeof(struct TProc));
	LProc ant = *lroundrobin;
	if(new == NULL){
		perror("Error.memoria insuficiente");
		exit(-1);
	}else{
		new->id = idproc;
		if(ant == NULL){
			new->sig = new;
		}else{
			new->sig = (ant)->sig;
			(ant)->sig = new;
		}
		ant = new;

	}

}

void EjecutarProcesos(LProc lroundrobin){
	if(lroundrobin == NULL){
		printf("No hay procesos que ejecutar.\n");
	}else{
		LProc ptr = lroundrobin;
		printf("\n----EJECUCION EN PROCESO----\n");
		do{
			ptr = ptr->sig;
			printf("Ejecutandose el proceso %d \n",ptr->id);
		}while(ptr != lroundrobin);
		printf("\n");
	}



}


void EliminarProceso(int id, LProc *lista){
	LProc ptr, ant;
	ptr = (*lista)->sig;
	ant = *lista;

	while(id!=ptr->id){
		ant = ptr;
		ptr = ptr->sig;
	}
	ant->sig = ptr->sig;
	if(ptr->id == (*lista)->id){
		*lista = (*lista)->sig;
	}
	free(ptr);
}

void EscribirFichero (char * nomf, LProc *lista){
	FILE *f = fopen(nomf,"wb");

	if(f == NULL){
		perror("No se ha podido abrir el fichero");
		exit(-1);
	}else{
		int vuelta = 0;
		int contador = 0;

		unsigned int ult = (*lista)->id;
		while(!vuelta){
			*lista = (*lista)->sig;
			contador++;
			if((*lista)->id==ult){
				vuelta = 1;
			}
			fwrite(&contador, sizeof(int),1,f);

			while(!vuelta){
				fwrite((*lista),sizeof(int),1,f);
				*lista = (*lista)->sig;

				if((*lista)->id == ult){
					vuelta = 1;
				}
			}
		}
	}



}
