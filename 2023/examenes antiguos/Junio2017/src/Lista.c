#include<stdlib.h>
#include<stdio.h>
#include "Lista.h"

/*
 * Inicializa la lista de puntos creando una lista vacía
 *
 */
void crearLista(TLista *lista){
	lista = NULL;
}


/**
 * Inserta el punto de forma ordenada (por el valor de la abscisa x)
 * en la lista siempre que no esté repetida la abscisa.
 *  En ok, se devolverá un 1 si se ha podido insertar, y  0 en caso contrario.
 *  Nota: utiliza una función auxiliar para saber
 *   si ya hay un punto en la lista con la misma abscisa punto.x
 *
 */
void insertarPunto(TLista *lista, struct Punto punto, int * ok){
	TLista new = malloc(sizeof(struct Nodo));
	*ok = 0;

	if(new == NULL){
		perror("Error.memoria insuficiente");

	}else{
		if(*lista == NULL){
			new->punto.x = punto.x;
			new->punto.y = punto.y;
			new->sig = NULL;
			*lista = new;
			*ok = 1;
		}else{
			TLista ptr, ant;
			ptr = *lista;
			ant = NULL;
			new->punto.x = punto.x;
			new->punto.y = punto.y;

			while(ptr != NULL && (punto.x > ptr->punto.x)){
				ant = ptr;
				ptr = ptr->sig;
			}
			if(ant == NULL){//el primero
				new->sig = ptr;
				*lista = new;
				*ok = 1;
			}else if(ptr == NULL){//el ultimo
				new->sig = NULL;
				ant->sig = new;
				*ok = 1;
			}else{//se para en medio
				if(punto.x == ptr->punto.x){//no se crea
					*ok = 0;
				}else{
					new->sig = ptr;
					ant->sig = new;
					*ok =1;
				}
			}
		}
	}


}


/*
 * Elimina de la lista el punto con abscisa x de la lista.
 * En ok devolverá un 1 si se ha podido eliminar,
 * y un 0 si no hay ningún punto en la lista con abscisa x
 *
 */
void eliminarPunto(TLista *lista,float x,int* ok){
	TLista ptr, ant;
	ptr = *lista;
	ant = NULL;
	*ok = 0;

	while(ptr != NULL && (x>ptr->punto.x)){
		ant = ptr;
		ptr = ptr->sig;
	}
	if(ptr != NULL && (x==ptr->punto.x)){
		//encontrado
		ant->sig = ptr->sig;
		free(ptr);
		*ok = 1;
	}
}


 /**
 * Muestra en pantalla el listado de puntos
 */
void mostrarLista(TLista lista){
	printf("\nLos puntos introducidos son:\n");

	if(lista == NULL){
		printf("La lista esta vacia");
	}else{
		while(lista != NULL){
			printf("(%.2f,%.2f)\n",lista->punto.x,lista->punto.y);
			lista = lista->sig;
		}
	}


}

/**
 * Destruye la lista de puntos, liberando todos los nodos de la memoria.
 */
void destruir(TLista *lista){
	TLista ptr;
	while(*lista != NULL){
		ptr = *lista;
		*lista = (*lista)->sig;
		free(ptr);
	}
}

/*
 * Lee el contenido del archivo binario de nombre nFichero,
 * que contiene una secuencia de puntos de una función polinómica,
 *  y lo inserta en la lista.
 *
 */
void leePuntos(TLista *lista,char * nFichero){
	FILE *f = fopen(nFichero, "rb");

	if(f==NULL){
		perror("No se ha podido abrir el fichero");
	}else{
		struct Punto p;
		int ok;
		while(fread(&p,sizeof(struct Punto),1,f)){
			insertarPunto(lista, p, &ok);
		}
		fclose(f);
	}


}

