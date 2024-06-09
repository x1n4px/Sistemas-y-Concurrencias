/*
 * Vector.h
 *
 *  Created on: 9 jun. 2021
 *      Author: galvez
 */

#ifndef VECTOR_H_
#define VECTOR_H_

typedef struct VectorNode * TVector;
struct VectorNode {
	int value;
	struct VectorNode * ptrNext;
};

/* Crea un vector vacío */
TVector create();

/* Añade un entero 'n' al final del vector */
/* El vector se pasa por referencia */
void putTail(TVector * ptrVector, int n);

/* Devuelve el número de enteros que contiene el vector */
/* El vector se pasa por valor */
int length(TVector vector);

/* Libera la memoria usada por el vector */
/* El vector se resetea al vector vacío */
/* El vector se pasa por referencia */
void destroy(TVector * ptrVector);

/* Muestra en consola el contenido del vector, desde el principio hasta el final */
/* El vector se pasa por valor */
void display(TVector vector);

/* Suma dos vectores */
/* Los enteros del segundo vector 'otherVector' se suman a los
 * valores del primer vector 'targetVector'.
 * Así, el contenido del primer vector queda modificado.
 * Si las longitudes de los vectores son distintas, se muestra un mensaje
 * de error y se retorna.
 */
/* Ambos vectores se pasan por valor */
void add(TVector targetVector, TVector otherVector);

/* Guarda el contenido de un vector en un fichero binario */
/* El primer item del fichero debe ser la longitud del vector y, posteriormente
 * se guardan los valores del vector, desde el primero hasta el último.
 */
/* El vector se pasa por valor */
void saveToFile(TVector vector, char* filename);

/* Carga un fichero con la estructura de la función anterior 'saveToFile' */
/* Se retorna un nuevo fichero con los datos cargados */
TVector loadFromFile(char* filename);

/* Devuelve un nuevo vector con los mismos datos que 'vector' pero en el orden dado por 'reorder' */
/* 'reorder' es un array de tantos enteros como sea la longitud de 'vector' */
/* 'reorder' no puede tener valores repetidos. La primera posición es la 0 */
/* Se retorna un nuevo vector con los datos reordenados */
/* Si el parámetro 'reorder' no tiene el formato adecuado, se emite un mensaje de error y se retorna NULL */
TVector shuffle(TVector vector, int* reorder);



#endif /* VECTOR_H_ */
