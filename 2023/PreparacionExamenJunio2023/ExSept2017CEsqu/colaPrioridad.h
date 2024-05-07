/*
 * procesos.h
 *
 *  Created on: 19 dic. 2017
 *      Author: mmar
 */

#ifndef COLAPRIORIDAD_H_
#define COLAPRIORIDAD_H_

#define L 10

typedef struct TNodo* Lista;
struct TNodo {
	int id;
	Lista sig;
};
typedef Lista TColaPrio[L];
/**
 * Inicializa el array con todas las listas vacias
 */

void crear(char *nombre, TColaPrio cp);

/**
 * Dada una prioridad y un identificador de proceso,
 * lo añade AL FINAL de la lista que le corresponde.
 */
void nuevoProceso (TColaPrio cp,int p, int id);

/**
 * Elimina de la lista el proceso más prioritario que le corresponde ejecutarse.
 * Se devuelve 0 si se ha podido ejecutar el proceso, y -1, en caso contrario.
 */

//void ejecutarProceso(TColaPrio cp);
int ejecutaProceso(TColaPrio cp);

/**
 * Dado un identificador de proceso devuelve la prioridad de éste.
 * Si el id del proceso no existe se devolverá -1.
 */
int buscar(TColaPrio cp, int id);

/*
 * Recorre la estructura para mostrar los procesos existentes
 * que están disponibles para ejecución ordenados por prioridad.
 *
 */
void mostrar(TColaPrio cp);

/*
 * Se eliminan todos los procesos de la cola de prioridad
 */
void destruir (TColaPrio cp);

#endif /* COLAPRIORIDAD_H_ */
