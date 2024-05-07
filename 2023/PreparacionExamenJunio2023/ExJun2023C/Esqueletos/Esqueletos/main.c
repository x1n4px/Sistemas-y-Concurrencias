/*
 ============================================================================
 Name        : main.c
 Author      : Profesoras PSC
 Version     :
 Copyright   : Your copyright notice
 Description : Ordinario Mayo 2023 
 
 Para compilar

 			gcc -g main.c Imagenes.c
			
 ============================================================================
 */

#include <assert.h>
#include <stdio.h>
#include "Imagenes.h"
#include <string.h>
#include <stdlib.h>

int main(int argc, char const *argv[])
{

	// Comenta al gusto para probar, recuerda que el main que se usará será el que se da sin tus modificaciones.

	ListaImagenes listaImg; //No hacer caso a la advertencia del IntelliSense de VSCode.
	inicializarListaImagenes(&listaImg);

	assert(listaImg == NULL);

	printf("Inicializado...\n");

	printf("Insertamos una primera imagen\n");
	char *img = malloc(IMAGE_SIZE*sizeof(char));

	//Relleno la imagen con basura aleatoria y el terminador.
	for(int i=0;i<IMAGE_SIZE-1;i++) img[i]=rand()%25+97; 
	img[IMAGE_SIZE-1]='\0'; 

	//Para ir más rápido, nos inventamos el hashcode (100) y los datos de la imagen.
	int res = insertarimagen(&listaImg,100,img);
	assert(res == 0);
	assert(listaImg != NULL);
	assert(listaImg->hashCode == 100);
	assert(strcmp(listaImg->imagen, img)==0); // Son iguales...
	assert(listaImg->imagen != img); // ...y no apuntan al mismo sitio :)

	printf("Insertamos una segunda\n");
	for(int i=0;i<1024-1;i+=rand()%10) img[i]=rand()%25+97; 
	res = insertarimagen(&listaImg,200,img);
	assert(res == 0);
	assert(listaImg != NULL);
	assert(listaImg->sig != NULL);
	assert(listaImg->sig->hashCode == 200);
	assert(strcmp(listaImg->sig->imagen, img)==0); // Son iguales...
	assert(listaImg->sig->imagen != img); // ...y no apuntan al mismo sitio :)

	//Insertamos 8 más
	for (unsigned i = 3; i <=10; i++)
	{
		for(int i=0;i<1024;i+=rand()%10) img[i]=rand()%25+97; 
		int res = insertarimagen(&listaImg,i*100,img);
		assert(res == 0);
		assert(listaImg != NULL);
	}
	
	printf("Insertamos una repetida\n");
	res = insertarimagen(&listaImg,300,img);
	assert(res == 1);
	
	// Mostramos la lista de imágenes
	printf("Mostramos todas las imagenes:\n");
	mostrarImagenes(listaImg,13);

	
	printf("Mostramos solo 5 imagenes:\n");
	mostrarImagenes(listaImg,5);

	// Guardamos en texto
	printf("Guardamos lista de imagenes:\n");
	guardarRegistroImagenes("imagenes.txt", listaImg);

	// Extraemos una imagen:
	Imagen* imgSola = extraercabeza(&listaImg);
	assert(imgSola->hashCode=100);
	assert(listaImg->hashCode == 200);
	free(imgSola);

	printf("Mostramos todas las imagenes:\n");
	mostrarImagenes(listaImg,13);

	//Extraemos una lista
	ListaImagenes listaAux = extraerLista(&listaImg,5);
	printf("Mostramos las 5 imagenes extraidas:\n");
	mostrarImagenes(listaAux,5);
	assert(listaAux != NULL);
	assert(listaAux->sig != NULL);
	assert(listaImg != NULL);
	assert(listaImg->sig != NULL);
	
	assert(listaAux->hashCode == 200);
	assert(listaImg->hashCode == 700);
	free(listaAux);

	printf("Cargamos datos de binario\n");
	cargarRegistroImagenes("imagenes.bin", &listaImg);

	printf("Mostramos lista de imagenes tras cargar de binario:\n");
	mostrarImagenes(listaImg, 100);
	destruirImagenes(&listaImg);
	assert(listaImg == NULL);

	free(img);
	
	return 0;
}
