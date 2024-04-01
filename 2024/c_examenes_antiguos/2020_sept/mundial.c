/*
 ============================================================================
 Nombre y Apellidos:
 DNI:
 Titulaci�n y Grupo:
 Ordenador:
 ============================================================================
 */

#include "stdio.h"
#include "stdlib.h"
#include "string.h"

#include "mundial.h"

/**
 * Crea una lista vacia.
 */
Lista Lista_Crear() {
	return NULL;
}

/**
 * Imprime por consola el contenido de cada uno de los nodos de la lista.
 */
void Lista_Imprimir(Lista lista) {
	Lista ptr = NULL;
	ptr = lista;
	printf("***************************\n");
	printf("*ESTADO ACTUAL DE LA LISTA*\n");
	printf("***************************\n");
	if (ptr == NULL) {
		printf("Lista vacia......\n");
	} else {
		while (ptr != NULL) {
			printf("===============================================\n");
			printf("Equipo: %s\n", ptr->nombre);
			printf("Victorias: %d\n", ptr->victorias);
			printf("Goles: %d\n", ptr->goles);
			ptr = ptr->sig;
		}
	}
}

/**
 * Comprueba si en la lista hay un nodo cuyo nombre coincida con
 * el que se pasa como parametro.
 * Devuelve 1 si lo encuentra, 0 en otro caso.
 */
int Esta(Lista lista, char * elem) {
	Lista ptr = lista;
	while (ptr != NULL) {
		if(strcmp(ptr->nombre, elem) == 0) {
			return 1;
		}
	}

	return 0;
}

/**
 * Recibe la informaci�n para insertar un nuevo equipo.
 * Si el equipo no est� en la lista, lo inserta al PRINCIPIO
 * de la lista y se devuelve un 1.
 * En otro caso, no se inserta y se devuelve un 0.
 */
int Lista_Agregar_Al_Principio(Lista *lista, int vict, int goles, char* nom) {
	Lista ptr = *lista;
	while(ptr != NULL && strcmp(ptr->nombre, nom) != 0) {
		ptr = ptr->sig;
	}

	if(ptr == NULL) {
		Lista nuevo = (Lista)malloc(sizeof(struct Nodo));
		nuevo->victorias = vict;
		nuevo->goles = goles;
		strcpy(nuevo->nombre, nom);
		nuevo->sig = *lista;
		*lista = nuevo;
		return 1;
	}else{
		return 0;
	}
}

/**
 * Elimina de la lista el equipo cuyo nombre coincida con el que
 * se pasa como parametro y se devuelve un 1.
 * Si no se encuentra el equipo, no elimina nada y devuelve un 0.
 */
int Eliminar_Equipo(Lista * lista, char * nombre) {
	Lista ptr = *lista;
	while(ptr != NULL && strcmp(ptr->nombre, nombre) != 0) {
		ptr = ptr->sig;
	}

	if(ptr == NULL) {
		return 0;
	}else{
		Lista aux = ptr;
		ptr = ptr->sig;
		free(aux);
		return 1;
	}
}


/**
 * Elimina todos los equipos de la lista, liberando toda la memoria.
 */
void Lista_Destruir(Lista *lista) {
	Lista ptr = *lista;
	while(ptr != NULL) {
		Lista aux = ptr;
		ptr = ptr->sig;
		free(aux);
	}
}

/*
 * HASTA AQUI PARA APROBAR
 */

/**
 * Carga desde el fichero resultadosCuartos.txt
 * en modo texto el contenido de la lista de equipos
 * con los resultados de esa fase.
 */
void Lista_Cargar(Lista *lista) {
	
}

/**
 * Traslada de la lista que recibe como primer parametro
 * a la lista que recibe como segundo parametro, los
 * nodos cuyo valor de victorias sea cero.
 * Adem�s, los elimina de la primera lista.
 */
void Trasladar_Descalificados(Lista * lista, Lista * listaDescalificados) {

}

/*
 * HASTA AQUI PARA NOTABLE
 */

/**
 * Calcula y devuelve el recuento de goles acumulados
 * por el equipo mas anotador de la fase de OCTAVOS.
 */
int Calcular_Maximos_Goles(Lista lista) {

}

/**
 * Genera una nueva lista enlazada de equipos ordenada en base
 * al n�mero de goles anotados, de MENOR a MAYOR.
 * Es decir, el primer nodo ser� el equipo menos goleador y el
 * �ltimo, el equipo m�s goleador.
 * Si hay equipos con el mismo n�mero de goles anotados,
 * aparecer�n consecutivos pero no importa el orden entre ellos.
 * Devuelve la lista generada.
 */
Lista Crear_Lista_Maximos_Goleadores(Lista lista) {

}

// Fin fichero
// ===========

