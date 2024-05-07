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
 * Pide un n�mero "tam" al usuario, y
 * crea un fichero binario para escritura con el nombre "nfichero"
 * en que escribe "tam" numeros (unsigned int) aleatorios
 * Se utiliza srand(time(NULL)) para establecer la semilla (de la libreria time.h)
 * y rand()%100 para crear un n�mero aleatorio entre 0 y 99.
 */
void creafichero(char *nfichero)
{

	FILE *fich = fopen(nfichero, "wb");
	int tam;

	printf("Introduce el tamaño del fichero: ");
	scanf("%d", &tam);

	// Establecer la semilla para los números aleatorios
	srand(time(NULL));

	for (int i = 0; i < tam; i++)
	{
		// Generar un número aleatorio entre 0 y 99
		unsigned int numero = rand() % tam;
		// Escribir el número en el archivo binario
		fwrite(&numero, sizeof(unsigned int), 1, fich);
	}

	fclose(fich);
}
/**
 * Muestra por pantalla la lista de n�meros (unsigned int) almacenada
 * en el fichero binario "nfichero"
 */
void muestrafichero(char *nfichero)
{
	FILE *fich = fopen(nfichero, "rb");
	unsigned int numero;

	if (fich == NULL)
	{
		printf("Error al abrir el fichero\n");
		return;
	}

	while (fread(&numero, sizeof(unsigned int), 1, fich))
	{
		printf("%u ", numero);
	}

	fclose(fich);
}

/**
 * Guarda en el arbol "*miarbol" los n�meros almacenados en el fichero binario "nfichero"
 */

void cargaFichero(char *nfichero, T_Arbol *miarbol)
{
	FILE *fich = fopen(nfichero, "rb");
	unsigned int numero;

	if (fich == NULL)
	{
		printf("Error al abrir el fichero\n");
		return;
	}

	while (fread(&numero, sizeof(unsigned int), 1, fich))
	{
		Insertar(miarbol, numero);
	}

	fclose(fich);
}

int main(void)
{
	char nfichero[50];
	printf("Introduce el nombre del fichero binario:\n");
	fflush(stdout);
	scanf("%s", nfichero);
	fflush(stdin);
	creafichero(nfichero);
	printf("\n Ahora lo leemos y mostramos\n");
	muestrafichero(nfichero);
	fflush(stdout);

	printf("\n Ahora lo cargamos en el arbol\n");
	T_Arbol miarbol;
	Crear(&miarbol);
	cargaFichero(nfichero, &miarbol);

	Insertar(&miarbol, 5);
	printf("\n Y lo mostramos ordenado\n");
	
	Mostrar(miarbol);
	fflush(stdout);

	printf("\n Ahora lo guardamos ordenado\n");
	FILE *fich;
	fich = fopen(nfichero, "wb");
	Salvar(miarbol, fich);
	fclose(fich);
	printf("\n Y lo mostramos ordenado\n");
	muestrafichero(nfichero);

	printf("\n Y ahora lo destruimos\n");

	Destruir(&miarbol);

	if(miarbol == NULL)
		printf("Arbol destruido con exito\n");

	return EXIT_SUCCESS;
}
