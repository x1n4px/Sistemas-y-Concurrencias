#include <stdlib.h>
#include <stdio.h>
#include "gestion_memoria.h"

/* Crea la estructura utilizada para gestionar la memoria disponible. Inicialmente, solo un nodo desde 0 a MAX */
void crear(T_Manejador *manejador)
{
    *manejador = (T_Manejador)malloc(sizeof(struct T_Nodo));
    if (*manejador != NULL)
    { // Si malloc ha reservado memoria
        (*manejador)->inicio = 0;
        (*manejador)->fin = 999;
        (*manejador)->sig = NULL;
    }
    else
    {
        perror("Error al inicializar");
        exit(-1);
    }
}

/* Destruye la estructura utilizada (libera todos los nodos de la lista. El parametro manejador debe terminar apuntando a NULL */
void destruir(T_Manejador *manejador)
{
    T_Manejador aux;
    while (*manejador != NULL)
    {
        aux = *manejador;
        *manejador = (*manejador)->sig;
        free(aux);
    }
}

/* Devuelve en 'dir' la direccion de memoria 'simulada' (unsigned) donde comienza el trozo de memoria continua de tamaño 'tam' solicitada.
Si la operacion se pudo llevar a cabo, es decir, existe un trozo con capacidad suficiente, devolvera TRUE (1) en 'ok'; FALSE (0) en otro caso.
 */
void obtener(T_Manejador *manejador, unsigned tam, unsigned *dir, unsigned *ok){
    T_Manejador curr, ant;
    curr = *manejador;
    ant = NULL;
    while (curr != NULL && (curr->fin - curr->inicio+1)<tam)
    {
        ant = curr;
        curr = curr->sig;
    }
    if(curr  == NULL){ //Hemos llegado al final de la lista
        *ok = 0; //false
    }else{
        *ok = 1; //true
        if((curr->fin - curr->inicio+1) > tam){
            curr->inicio += tam;
        }else{//2) el bloque es de tama�o tam -> eliminamos el nodo de la lista
			//2.1 eliminamos el primer nodo
            if(ant == NULL){
                *manejador = (*manejador)->sig;
                free(curr);
            }else{//2.2) eliminamos nodo intermedio o final
                ant->sig = curr->sig;
                free(curr); 
            }

        }
    }
    
}

/* Muestra el estado actual de la memoria, bloques de memoria libre */
void mostrar(T_Manejador manejador){
    T_Manejador aux = manejador;
    int i = 0;
    if(aux == NULL){
        printf("Toda la memoria ocupada\n");
    }
    while(aux != NULL){
        printf("Bloque %d: inicio %d fin %d\n", i, aux->inicio, aux->fin);
        aux = aux->sig;
    }
    fflush(stdout);
}

/*
// Compacta la memoria juntando bloques contiguos 
void compactar(T_Manejador *manejador) {
	T_Manejador ptr,aux;

	if (*manejador != NULL) {	//9 Lista no vacia 

		ptr=*manejador;
		while (ptr->sig != NULL) {
			if (ptr->fin+1==ptr->sig->inicio) {	// Hay que compactar 
				ptr->fin=ptr->sig->fin;
				aux=ptr->sig->sig;
				free((void *)ptr->sig);
				ptr->sig=aux;
			}
			else {
				ptr=ptr->sig;	// No hay que compactar, avanzar 
			}

		}
	}

}

*/

/* Devuelve el trozo de memoria continua de tamano 'tam' y que
 * comienza en 'dir'.
 * Se puede suponer que se trata de un trozo obtenido previamente.
 */
void devolver(T_Manejador *manejador, unsigned tam, unsigned dir){
    //Variables auxiliares
    T_Manejador nuevo;
    T_Manejador curr, ant;

    curr = *manejador;
    //Variable ant(anterior)
    ant = NULL;

    //Crerar nuevo nodo
    nuevo = (T_Manejador)malloc(sizeof(struct T_Nodo));

    if(nuevo != NULL){
        nuevo->inicio = dir;
        nuevo->fin = dir+tam-1;
        nuevo->sig = NULL;
    }else{
        perror("Error al devolver\n");
        exit(-1);
    }
    if(ant == NULL){//Nuevo es el primer nodo de la lista
        nuevo->sig = curr;
        *manejador = nuevo;
    }else{
        nuevo->sig = curr;
        ant->sig = nuevo;
    }
}

/*

curr = manejador -> Asigna el valor de manejador a curr, ambos apuntan a la misma dir de memoria
curr = *manejador ->  Accede al objeto que apunta manejador
*curr = manejador -> ERROR
*curr = manejador -> Similar a curr = *manejador, pero mejor usar curr = *manejador
**manejador -> Accede al objeto al que apunta el puntero al que apunta manejador



*/