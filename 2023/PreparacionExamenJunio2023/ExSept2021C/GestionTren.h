/*
 * GestionTren.h
 *
 */

#ifndef GESTIONTREN_H_
#define GESTIONTREN_H_

#define MAX_DESTINO 20
typedef struct  Pasajero *Tren;

struct Pasajero{
	int asiento;
	char destino[MAX_DESTINO];
	struct Pasajero *sig;
};


/* 0.5 ptos: al salir de la cochera el tren está vacío (equivale a inicializar el tren) */
Tren salirCochera();

/* 0.5 ptos: Mostrar por pantalla los pasajeros que están en el tren. 
  *          Hay que mostrar el asiento y destino de cada uno */
void mostrarPasajeros(Tren t);

/* 1 pto: al entrar a la cochera tienen que bajarse todos los pasajeros que haya en ese momento en el tren, 
  *       independientemente de cuál sea su destino. 
  *       Devuelve el número de pasajeros que se han bajado en la cochera */
int entrarCochera(Tren *t);

/* 2 ptos: un pasajero intenta subir al tren. 
 *         Si el asiento está disponible se almacena en el tren el asiento y destino del pasajero y se devuelve 1. 
 *         Si el asiento está ocupado no se modifica la lista y se devuelve 0. */
int subirPasajero(Tren* t, int asiento, char *destino);

/* 2 ptos: cuando el tren llega a una parada, se bajan del tren (se extraen de la lista)
 *         todos los pasajeros con ese destino.
 *	       La función devuelve el número de pasajeros que se han bajado */
int parada(Tren *t, char *parada);

/* 2 ptos: Almacena el estado del tren en un fichero binario, cuyo nombre se recibe como parámetro.
 *         Por cada pasajero del tren hay que almacenar: el número del asiento, la longitud del nombre del 
 *         destino, y el nombre del destino (solo los caracteres correspondientes a su longitud). */
void guardarBinario(Tren t, char *fichero);

/* 2 ptos: Crear un tren con los datos almacenado en el fichero binario cuyo nombre se recibe como parámetro.
  *        El fichero tendrá el formato generado por la función guardarBinario, es decir que por cada pasajero hay
  *        que leer su asiento, la longitud del nombre del destino y el nombre del destino (solo los caracteres 
  *        correspondientes a su longitud). */
Tren cargarBinario(char *fichero);


#endif /* GESTIONTREN_H_ */
