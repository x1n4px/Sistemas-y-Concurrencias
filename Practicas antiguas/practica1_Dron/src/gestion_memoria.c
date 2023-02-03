
/*
 * GestionMemoria.c
 *
 *  Created on: dd/mm/aaaa
 *      Author: name
 */
#include <stdio.h>
#include <stdlib.h>
#include "gestion_memoria.h"

#define MAX 1000


/* Crea la estructura utilizada para gestionar la memoria disponible. */
void crear(T_Manejador *manejador_ptr) {
	*manejador_ptr=(T_Manejador)malloc(sizeof(struct T_Nodo));
	/*(*manejador)->inicio=0;
	(*manejador)->fin=MAX-1;
	(*manejador)->sig=NULL;*/
	if(*manejador_ptr!=NULL){ //malloc ha reservado memoria
		(*manejador_ptr)->inicio=0;
		(*manejador_ptr)->fin = 999;
		(*manejador_ptr)->sig = NULL;
	}else{
		perror("Error al inicializar");
		exit(-1); //termina el programa con error generico
	}
}


/* Destruye la estructura utilizada. */
void destruir(T_Manejador* manejador_ptr) {
	/*T_Manejador ptr;
	while (*manejador_ptr != NULL) {
		ptr=(*manejador_ptr)->sig;
		free((void *)*manejador_ptr);
		*manejador_ptr=ptr;
	}*/

	T_Manejador aux;
	while(*manejador_ptr != NULL){
		aux = *manejador_ptr;
		*manejador_ptr = (*manejador_ptr)->sig; //apunta al siguiente
		free(aux);
	}


}


/* Devuelve en “dir” la dirección de memoria donde comienza el
 * trozo de memoria continua de tamaño “tam” solicitada.
 * Si la operación se pudo llevar a cabo, es decir, existe dicho
 * trozo, devolvera TRUE en “ok”; FALSE en otro caso.
 */
void obtener(T_Manejador* manejador_ptr,unsigned tam, unsigned* dir, unsigned* ok) {


//	int tam_actual,restante;
//	T_Manejador ptr,ant,aux;
//
//
//	*ok=0;
//	ant=NULL;
//	ptr=*manejador;
//
//	while ((!(*ok)) && (ptr != NULL)) {
//		tam_actual=ptr->fin-ptr->inicio+1;
//		if (tam_actual>=tam) {
//			*ok=1;
//		}
//		else {
//			ant=ptr;
//			ptr=ptr->sig;
//		}
//	}	/* end-while */
//
//	if (*ok) {
//		*dir=ptr->inicio;
//
//		/* Dividir el bloque */
//		restante=tam_actual-tam;
//		if (restante) {	/* Queda algo de memoria libre, simplemente modificar el contenido */
//			ptr->inicio=ptr->inicio+tam;	/* Nueva direccion de inicio */
//		}
//		else {
//			/* Hay que eliminar el nodo de la lista */
//
//			if (ant==NULL) {	/* Borrar el primer nodo */
//				aux=ptr->sig;
//				free((void *)*manejador);
//				*manejador=aux;
//			}
//			else {
//				/* El bloque esta en medio de la lista */
//				ant->sig=ptr->sig;
//				free((void *)ptr);
//			}
//
//		}	/* end-else dividir bloque */
//	}


	T_Manejador curr, ant;
	//curr: apunta al modo que estamos evaluando si tiene tam suficiente
	//ant: apunta al nodo anterior de curr

	curr = *manejador_ptr;
	ant = NULL;
	while(curr!=NULL && (curr->fin - curr->inicio+1)<tam){
		ant = curr;
		curr = curr->sig;
	}
	if(curr==NULL){//hemos llegado al final de la lista->no hay tam memoria libre disponible
		*ok = 0;//false
	}
	else{
		*ok = 1;//true
		//1)el bloque es mas grande
		if((curr->fin - curr->inicio+1)>tam){
			curr->inicio +=tam;
		}else{ //2) el bloque es de tamaño tam -> eliminamos el nodo de la lista
			//2.1 eliminamos el primer nodo
			if(ant == NULL){
				*manejador = (*manejador)->sig;
				free(curr);
			}else{ //2.2) eliminamos nodo intermedio o final
				ant->sig = curr->sig;
				free(curr);
			}
		}
	}







}


/* Compacta la memoria juntando bloques contiguos */
void compactar(T_Manejador *manejador) {
	T_Manejador ptr,aux;

	if (*manejador != NULL) {	/* Lista no vacia */

		ptr=*manejador;
		while (ptr->sig != NULL) {
			if (ptr->fin+1==ptr->sig->inicio) {	/* Hay que compactar */
				ptr->fin=ptr->sig->fin;
				aux=ptr->sig->sig;
				free((void *)ptr->sig);
				ptr->sig=aux;
			}
			else {
				ptr=ptr->sig;	/* No hay que compactar, avanzar */
			}

		}
	}

}



/* Devuelve el trozo de memoria continua de tamaño “tam” y que
 * comienza en “dir”.
 * Se puede suponer que se trata de un trozo obtenido previamente.
 */
void devolver(T_Manejador* manejador_ptr,unsigned tam,unsigned dir) {
//	T_Manejador ptr,ant,aux;
//
//	/* Buscar posicion a insertar */
//	ptr=*manejador;
//	ant=NULL;
//	while ((ptr != NULL) && (dir>ptr->inicio)) {
//		ant=ptr;
//		ptr=ptr->sig;
//	}
//
//	if (ant==NULL) {
//		/* Insertar al comienzo de la lista */
//		aux=(T_Manejador)malloc(sizeof(struct T_Nodo));
//		aux->inicio=dir;
//		aux->fin=dir+tam-1;
//		aux->sig=*manejador;
//		*manejador=aux;
//	} else {
//		/* Insertar en medio o al final de la lista */
//		aux=(T_Manejador)malloc(sizeof(struct T_Nodo));
//		aux->inicio=dir;
//		aux->fin=dir+tam-1;
//		aux->sig=ptr;
//		ant->sig=aux;
//	}
//
//	/* Hacer compactacion */
//	compactar(manejador);



	T_Manejador nuevo;
	T_Manejador curr,ant;
	curr = *manejador_ptr;
	ant = NULL;

	//crear nuevo nodo
	nuevo = (T_Manejador)malloc(sizeof(struct T_Nodo));
	if(nuevo!=NULL){
		nuevo->inicio = dir;
		nuevo->fin = dir+tam -1;
		nuevo->sig = NULL;
	}else{
		perror("Error al devolver\n");
		return;
	}

	//insertar nuevo nodo en la lista, ordenado por "inicio"
	while(curr!=NULL && curr->inicio<dir){
		ant = curr;
		curr = curr->sig;
	}

	if(ant == NULL){//nuevo es el primero nodo de la lista
		nuevo->sig = curr; //*manejado_ptr
		*manejador_ptr = nuevo;
	}else{//nuevo es intermedio o ultimo nodo de la lista
		nuevo->sig = curr;
		ant->sig = nuevo;
	}


	//compactamos la lista

}



/* Muestra el estado actual de la memoria */
void mostrar (T_Manejador manejador) {
	/*printf("------\n");
	while (manejador != NULL){
		printf("Desde %d a %d: Libre\n", manejador->inicio, manejador->fin);
		manejador = manejador->sig;*/

	T_Manejador aux = manejador;
	int i = 0;
	if(aux = NULL){
		printf("Toda la memoria ocupada\n");
	}
	while(aux != NULL){
		printf("Bloque %d: inicio %d fin %d\n",i,aux->inicio,aux->fin);
		aux = aux->sig;
	}
	}

	fflush(stdout);
}
