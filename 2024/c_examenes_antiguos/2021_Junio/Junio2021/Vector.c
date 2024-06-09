/*
 * Vector.c
 *
 *  Created on: 9 jun. 2021
 *      Author: galvez
 */
#include <stdio.h>
#include <stdlib.h>
#include "Vector.h"

/* Crea un vector vac�o */
TVector create()
{
    return NULL;
}

/* A�ade un entero 'n' al final del vector */
/* El vector se pasa por referencia */
void putTail(TVector *ptrVector, int n)
{
    TVector nuevo = malloc(sizeof(struct VectorNode));
    if(nuevo == NULL)  {
        return ;
    }

    nuevo->value = n;
    nuevo->ptrNext = NULL;

    if(*ptrVector == NULL){
        *ptrVector = nuevo; 
    }else{
        TVector temp = *ptrVector;
        TVector anterior = NULL;

        while(temp != NULL) {
            anterior = temp;
            temp = temp->ptrNext;

        }
        anterior->ptrNext = nuevo;
    }
}

/* Devuelve el n�mero de enteros que contiene el vector */
/* El vector se pasa por valor */
int length(TVector vector)
{
    int cont = 0;
    while (vector != NULL)
    {
        cont++;
        vector = vector->ptrNext;
    }
    return cont;
    
}

/* Libera la memoria usada por el vector */
/* El vector se resetea al vector vac�o */
/* El vector se pasa por referencia */
void destroy(TVector *ptrVector)
{
    while (*ptrVector != NULL)
    {
        TVector siguiente = (*ptrVector)->ptrNext;
        free(*ptrVector);
        *ptrVector = siguiente;
    }
    
}

/* Muestra en consola el contenido del vector, desde el principio hasta el final */
/* El vector se pasa por valor */
void display(TVector vector)
{
    if(vector == NULL){
        printf("Vector vacio\n");
    }else{
        printf("Vector: ");
        while(vector != NULL) {
            if(vector->ptrNext == NULL) {
                printf("%d\n", vector->value);
                break;
            }else{
                printf("%d, ", vector->value);
                vector = vector->ptrNext;
            
            }
        }
    }
}

/* Suma dos vectores */
/* Los enteros del segundo vector 'otherVector' se suman a los
 * valores del primer vector 'targetVector'.
 * As�, el contenido del primer vector queda modificado.
 * Si las longitudes de los vectores son distintas, se muestra un mensaje
 * de error y se retorna.
 */
/* Ambos vectores se pasan por valor */
void add(TVector targetVector, TVector otherVector)
{
    if(length(targetVector) != length(otherVector)){
        printf("Los vectores no tienen la misma longitud\n");
        return;
    }

    while(targetVector != NULL){
        targetVector->value += otherVector->value;
        targetVector = targetVector->ptrNext;
        otherVector = otherVector->ptrNext;
    }
}

/* Guarda el contenido de un vector en un fichero binario */
/* El primer item del fichero debe ser la longitud del vector y, posteriormente
 * se guardan los valores del vector, desde el primero hasta el �ltimo.
 */
/* El vector se pasa por valor */
void saveToFile(TVector vector, char *filename)
{
    FILE * pfile = fopen(filename, "wb");
    if(pfile == NULL) return;

    int longitudVec = length(vector);
    fwrite(&longitudVec, sizeof(int), 1, pfile);
    while (vector != NULL)
    {
        int numero = vector->value;
        fwrite(&numero, sizeof(int), 1, pfile);
        vector = vector->ptrNext;
    }
    fclose(pfile);
}

/* Carga un fichero con la estructura de la funci�n anterior 'saveToFile' */
/* Se retorna un nuevo fichero con los datos cargados */
TVector loadFromFile(char *filename)
{
    FILE * pfile = fopen(filename, "rb");
    if(pfile == NULL) return NULL;

    TVector v = create();
    int longitud, numero;

    fread(&longitud, sizeof(int), 1, pfile);
    while (fread(&numero, sizeof(int), 1, pfile) == 1)
    {
        putTail(&v, numero);
        fread(&numero, sizeof(int), 1, pfile);
        putTail(&v, numero);
    }
     fclose(pfile);
    return v;
    
}

/* Devuelve un nuevo vector con los mismos datos que 'vector' pero en el orden dado por 'reorder' */
/* 'reorder' es un array de tantos enteros como sea la longitud de 'vector' */
/* 'reorder' no puede tener valores repetidos. La primera posici�n es la 0 */
/* Se retorna un nuevo vector con los datos reordenados */
/* Si el par�metro 'reorder' no tiene el formato adecuado, se emite un mensaje de error y se retorna NULL */
TVector shuffle(TVector vector, int *reorder)
{
    int reorderSize = 4;
    for (int i = 0; i < 4; i++)
    {
        if(reorder[i] < 0 || reorder[3] > 3) {
            return NULL;
        }
    }

    if(length(vector) != reorderSize) {
        return NULL;
    }


    TVector ordenado = create();
    TVector inicio = vector;

    for (int i = 0; i < reorderSize; i++)
    {
        vector = inicio;
        int j = 0;
        while(vector != NULL) {
            if(j == reorder[i]){
                putTail(&ordenado, vector->value);
                break;
            }
            j++;
            vector = vector->ptrNext;
        }
    }
    return ordenado;
    
}