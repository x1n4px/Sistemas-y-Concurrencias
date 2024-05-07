/*
 ============================================================================
 Name        : Practica2B.c
 Author      : esc
 Version     :
 Copyright   : Your copyright notice
 Description : Hello World in C, Ansi-style
 ============================================================================
 */

#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include "arbolbb.h"

/**
 * Pide un número "tam" al usuario, y
 * crea un fichero binario para escritura con el nombre "nfichero"
 * en que escribe "tam" numeros (unsigned int) aleatorios
 * Se utiliza srand(time(NULL)) para establecer la semilla (de la libreria time.h)
 * y rand()%100 para crear un número aleatorio entre 0 y 99.
 */
void creafichero(char* nfichero, int TAM)
{
	FILE *f;
	int i = 0;
	int resultado = 0;
	int buffer[TAM];
	srand(time(NULL));
	//generar numeros aleatorios
	for(i=0;i<TAM;i++){
		buffer[i] = rand() % TAM;
	}
	//abrir fichero
	if((fopen(nfichero, "wb"))!= NULL){//se ha abierto con exito
			resultado = fwrite(buffer,sizeof(int),TAM,f);
			if(resultado != TAM){
				perror("No se han guardado todos los numero aleatorios");
			}
			fclose(f);
	}else{
		perror("Error al crear fichero aleatorios");
		exit(-1);
	}



}
/**
 * Muestra por pantalla la lista de números (unsigned int) almacenada
 * en el fichero binario "nfichero"
 */
void muestrafichero(char* nfichero)
{

	FILE *f;
	int num, resultado;
	//abrimos fichero
	if((f=fopen(nfichero,"rb"))!=NULL){ //abierto correctamente
		//leemos numeros 1 a 1 y mostramos
			resultado = fread(&num,sizeof(int),1,f);
			while(resultado) //while(resultado == 1)
			{
				printf("%d\t",num);
				resultado = fread(&num,sizeof(int),1,f);
			}
			printf("\n");
			//cerramos fichero
			fclose(f);
	}else{
		perror("Fichero a mostrar no encontrado");
		exit(-1);
	}








}

/**
 * Guarda en el arbol "*miarbol" los números almacenados en el fichero binario "nfichero"
 */

void cargaFichero(char* nfichero, T_Arbol* miarbol)
{

	FILE *f;
	int num,resultado;
	if((f=fopen(nfichero,"rb"))!=NULL){
		resultado = fread(&num, sizeof(int),1,f);
		while(resultado){
			Insertar(miarbol, num);
			resultado = fread(&num, sizeof(int),1,f);
		}
	}else{
		perror("Error al cargar el fichero");
		exit(-1);
	}



}

int main(void) {
	char nfichero[50];
	printf ("Introduce el nombre del fichero binario:\n");
	fflush(stdout);
	scanf ("%s",nfichero);
	fflush(stdin);
	creafichero(nfichero,10);
	printf("\n Ahora lo leemos y mostramos\n");
	muestrafichero(nfichero);
	fflush(stdout);


	printf ("\n Ahora lo cargamos en el arbol\n");
	T_Arbol miarbol;
	Crear (&miarbol);
	cargaFichero(nfichero,&miarbol);
	printf ("\n Y lo mostramos ordenado\n");
	Mostrar(miarbol);
	fflush(stdout);
	printf("\n Ahora lo guardamos ordenado\n");
	FILE * fich;
	fich = fopen (nfichero, "wb");
	Salvar (miarbol, fich);
	fclose (fich);
	printf("\n Y lo mostramos ordenado\n");
	muestrafichero(nfichero);
	Destruir (&miarbol);

	return EXIT_SUCCESS;
}
