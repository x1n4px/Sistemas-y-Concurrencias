#include<stdio.h>
#include<stdlib.h>
#include "ListaJugadores.h"


//crea una lista vacía (sin ningún nodo)
void crear(TListaJugadores *lc){
	*lc = NULL;

}

//inserta un nuevo jugador en la lista de jugadores, poniendo 1 en el número de goles marcados.
//Si ya existe añade 1 al número de goles marcados.
void insertar(TListaJugadores *lj,unsigned int id){
	TListaJugadores new = malloc(sizeof(struct TJugador));
	TListaJugadores ptr = *lj, ant = NULL;
	if(new == NULL){
		perror("ERROR: No se pudo reservar memoria");
	}else{
		if(*lj == NULL){//lista vacia
			new->id = id;
			new->goles = 1;
			new->sig = NULL;
			*lj = new;
		}else{//lista no vacia
			while(ptr != NULL && id > ptr->id){//no acabe la lista
				ant = ptr;
				ptr = ptr->sig;//avanzamos en la lista
			}
			if((ptr!=NULL)&&(ptr->id==id)){ //esta en la lista
				ptr->goles++;//aumentamos los goles
				free(new);
			}else{ //si no esta en la lista
				new->id = id;
				new->goles = 1;
				if(ant == NULL){ //va el primero
					new->sig = *lj;
					*lj = new;
				}else{
					new->sig = ant->sig;
					ant->sig = new;
				}
			}
		}
	}
}

//recorre la lista circular escribiendo los identificadores y los goles marcados
void recorrer(TListaJugadores lj){
	if(lj == NULL){
		printf("\nNo hay jugadores en la lista\n");
	}else{
		while(lj != NULL){
			printf("Jugador: %u \tGoles: %u\n",lj->id,lj->goles);
			lj = lj->sig;
		}
	}
}

//devuelve el número de nodos de la lista
int longitud(TListaJugadores lj){
	int cont = 0;
	while(lj != NULL){
		cont++;
		lj = lj->sig;
	}
	return cont;
}

//Eliminar. Toma un número de goles como parámetro y
//elimina todos los jugadores que hayan marcado menos que ese número de goles
void eliminar(TListaJugadores *lj,unsigned int n){
	TListaJugadores ptr = *lj, ant = NULL;

	while(ptr != NULL){
		if(ptr->goles < n){
			if(ant == NULL){ //borra el primero
				*lj = ptr->sig;
				free(ptr);
				ptr = *lj;
			}else{
				ant->sig = ptr->sig;
				free(ptr);
				ptr = ant->sig;
			}
		}else{
			ant = ptr;
			ptr = ptr->sig;
		}
	}
}


// Devuelve el ID del máximo jugador. Si la lista está vacía devuelve 0. Si hay más de un jugador con el mismo número de goles que el máximo devuelve el de mayor ID
// Hay que devolver el identificador, no el número de goles que ha marcado
unsigned int maximo(TListaJugadores lj){
	unsigned int id = 0, goles = 0;
	if(lj != NULL){
		while(lj != NULL){
			if(lj->goles >= goles){
				id = lj->id;
				goles = lj->goles;
			}
			lj = lj->sig;
		}
	}
	return id;
}

//Destruye la lista y libera la memoria)
void destruir(TListaJugadores *lj){
	TListaJugadores ptr;

	while(*lj != NULL){
		ptr = *lj;
		*lj = ptr->sig;
		free(ptr);
	}


}

