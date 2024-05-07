/*
 * Mprocesos.h
 *
 *  Created on: 01/09/2016
 *      Author: luisll
 */

#ifndef MPROCESOS_H_
#define MPROCESOS_H_

/* Defincion de tipos*/
typedef struct TProc *LProc;

struct TProc{
	unsigned int id;
	LProc sig;
};



void Crear(LProc *lroundrobin);

void AnadirProceso(LProc *lroundrobin, int idproc);

void EjecutarProcesos(LProc lroundrobin);


void EliminarProceso(int id, LProc *lista);

void EscribirFichero (char * nomf, LProc *lista);


#endif /* MPROCESOS_H_ */
