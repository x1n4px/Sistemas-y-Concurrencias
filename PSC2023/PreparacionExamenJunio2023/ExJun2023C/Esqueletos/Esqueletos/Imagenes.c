/*
 ============================================================================
 Name        : Imagenes.c
 Authors     : Profesoras PSC
 Version     : 
 Copyright   : Your copyright notice
 Description : Ordinario Mayo 2023
 ============================================================================
 */


#include "Imagenes.h"
#include <stdlib.h>
#include <stdio.h>
#include <string.h>



/**
 * @brief Inicializa la \p lista de imágenes vacía (0.25 pts.)
 * @param lista Lista de entrada para ser inicializada.
 */
void inicializarListaImagenes(ListaImagenes *lista){
    (*lista) = NULL;
}


/**
 * @brief Se inserta una nueva imagen según orden de llegada, ¡si no está!, con el \p hascode y \p datos dados (1.75 pts). En caso de fallo al solicitar memoria, se sale del programa mostrando antes un mensaje de error.
 * @param lista Lista de imágenes ya existente en el sistema.
 * @param hashcode Identificador único de la imagen.
 * @param datos Datos de la imagen, se deben copiar (incluye el carácter terminador)
 * @return devuelve 0 si se puede insertar, 1 si no se puede insertar (duplicada).
 */
int insertarimagen(ListaImagenes *lista, int hashcode, char *datos){
    ListaImagenes aux = (ListaImagenes) malloc(sizeof(struct Imagen));
    if(aux == NULL) {
        return 0;
    }else{
        aux->sig = NULL;
        aux->hashCode = hashcode;
        strcpy(aux->imagen, datos);
        if(*lista == NULL) {
           *lista = aux;
           return 0;
        }else{
            ListaImagenes ptr = *lista;
            while ( ptr != NULL) {
                if(ptr->hashCode == hashcode) {
                    free(aux);
                    return 1;
                }
                ptr = ptr->sig;
            }
            ptr = *lista;
            while (ptr->sig != NULL){
                ptr = ptr->sig;
            }
            ptr->sig = aux;
            return 0;
        }
    }

}


/**
 * @brief Muestra las \p num primeras imágenes de la lista en orden de inserción (primero la más antigua). Si hay menos de \p num, las mostrará todas. Para cada imagen se debe mostrar el hascode y sólo los primeros 10 caracteres de sus datos (1 pt.).
 * @param lista Lista de imágenes ya existente en el sistema.
 * @param num Número máximo de imágenes a mostrar.
 */
void mostrarImagenes(ListaImagenes lista,int num){
    int contador = 0;
    if(lista == NULL){return;}
    while (lista != NULL && contador < num) {
        printf("Imagen con hascode: %d, %.10s\n", lista->hashCode, lista->imagen);
        contador++;
        lista = lista->sig;
    }
}


/**
 * @brief Libera toda la memoria y deja la \p lista de imágenes vacía (1 pt.).
 * @param lista Lista de imágenes ya existente en el sistema.
 */
void destruirImagenes(ListaImagenes *lista){
    ListaImagenes ptr = *lista;
    ListaImagenes sig = NULL;
    while((*lista)->sig != NULL) {
        sig = ptr->sig;
        free(ptr);
       ptr = sig;

    }
    *lista = NULL;
}



/**
 * @brief Extrae la imagen más antigua y la quita de \p lista (0.5 pt.).
 * @param lista Lista de imágenes ya existente en el sistema.
 * @return Un puntero a la imagen más antigua o NULL si no existe.
 */
Imagen* extraercabeza(ListaImagenes *lista){

    if(*lista == NULL) {
        return NULL;
    }
    ListaImagenes ptr = *lista;
    *lista = ptr->sig;
    ptr->sig = NULL;
    return ptr;


}

/**
 * @brief Extrae una ListaImagenes que contiene las \p num imágenes más antiguas y las quita de \p lista (1.75 pt.).
 * @param lista Lista de imágenes ya existente en el sistema.
 * @param num Número mayor que cero que indica el máximo de imágenes a extraer en la lista.
 * @return NULL si no hay ninguna imagen que extraer o una nueva lista con las, hasta \p num, imágenes más antiguas.
*/
ListaImagenes extraerLista(ListaImagenes *lista, int num){


    if(*lista == NULL || num <= 0){
        return NULL;
    }

    ListaImagenes ptr = *lista;
    ListaImagenes prev = NULL;

    while (ptr != NULL && num > 0) {
        prev = ptr;
        ptr = &(ptr->sig);
        num--;
    }

    if(prev != NULL){
        prev->sig = NULL;
    }
    return ptr;
}


/**
 * @brief Escribe en un fichero de texto parte de los datos de las imágenes almacenadas en la lista (1.75 pts.). El formato del fichero de texto será el siguiente: primero tendrá una cabecera con una descripción de los campos. Tras esta cabecera, una línea por cada imagen, ordenadas por antigüedad (las más antiguas primero). Solo se almacenan los 100 primeros bytes de cada imagen. Ejemplo:
Hashcode;Imagen
1286; fsgf8sddsdfsdfnlnlsdfoisdfolskndflsndfoinlksngm ,m oiw09tuqargnl ... ;
864; aborasenhlakfhg,m ,miohlksndfgnqwoiut80q4760  oiqrnwglkjWR089TQH4OLGRN  ... ;
9874; 09234jfilnalkfngoiahrñktnmp9ur0q914u63091u3504ypihnñFGBP90rueyphnñRKG ... ;
 * @param filename Nombre del fichero para escribir en él.
 * @param lista Lista de imágenes del sistema.
 */
void guardarRegistroImagenes(char *filename, ListaImagenes lista){
    FILE* archivo = fopen(filename, "wb");

    if(archivo == NULL) {
        perror("Error al abrir el fichero");
    }
    fprintf(archivo, "Hashcode;Imagen\n"); //cabecera

    Imagen *actual = lista;
    while (actual != NULL){
        fprintf(archivo, "%d; %.100s\n", actual->hashCode, actual->imagen);
        actual = actual->sig;
    }
    fclose(archivo);
}


/**
 * @brief Lee de fichero binario los datos de imágenes y los carga para su uso. En caso de fallo al solicitar memoria, se sale del programa mostrando antes un mensaje de error. La lista actual puede no estar vacía, recuerda antes borrar todas las imágenes existentes (2.0 pts.).
 *  En el fichero binario se almacena la información de cada imagen con el siguiente formato:
 *      un entero con el hascode de la imagen;
 *      una cadena de longitud IMAGE_SIZE bytes con la imagen. Esta cadena ya incluye el carácter terminador '\0'.
 *  Se asume que las imágenes están guardadas por antigüedad, siendo las primeras las más antiguas.
 * @param filename Nombre del fichero para leer de él.
 * @param lista Lista en la que se almacenan las imágenes leídas.
 */
void cargarRegistroImagenes(char *filename, ListaImagenes *lista){
    FILE* archivo = fopen(filename, "rb");
    if(archivo == NULL) {
        return;
    }
    destruirImagenes(lista);

    int hascode;
    char imagen[IMAGE_SIZE];

    while (fread(&hascode, sizeof (int), 1, archivo) == 1) {
        if(fread(imagen, sizeof (char), IMAGE_SIZE, archivo) != IMAGE_SIZE){
            printf("Error al leer el fichero");
            fclose(archivo);
            return;
        }

        //solicitar memoria para una nueva imagen
        Imagen *nuevaImagen = (Imagen*) malloc(sizeof(Imagen));
        if(nuevaImagen == NULL) {
            printf("ERROR al solicitar memoria\n");
            fclose(archivo);
            return;
        }

        //copiar los datos de la imagen leida a la nueva imagen
        nuevaImagen->hashCode = hascode;
        strncpy(nuevaImagen->imagen, imagen, IMAGE_SIZE);
        nuevaImagen->sig = NULL;

        //Insertar la nueva imagen al final de la lista
        if(*lista == NULL){
            *lista = nuevaImagen;
        }else{
            ListaImagenes ultimo = *lista;
            while (ultimo->sig != NULL) {
                ultimo = ultimo->sig;
            }
            ultimo->sig = nuevaImagen;
        }
    }
    fclose(archivo);
}