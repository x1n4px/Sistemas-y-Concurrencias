/*
 ============================================================================
 Name        : Imagenes.h
 Authors     : Profesoras PSC
 Version     : 
 Copyright   : Your copyright notice
 Description : Ordinario Mayo 2023
 ============================================================================
 */

#ifndef IMAGENES_H_
#define IMAGENES_H_

#define IMAGE_SIZE 64000 
typedef struct Imagen *ListaImagenes;
typedef struct Imagen
{
    int hashCode;             // hash único para cada imagen
    char imagen[IMAGE_SIZE];  // datos de la imagen
    ListaImagenes sig;		  // puntero a la siguiente imagen
} Imagen;




/** 
 * @brief Inicializa la \p lista de imágenes vacía (0.25 pts.) 
 * @param lista Lista de entrada para ser inicializada.  
 */ 
void inicializarListaImagenes(ListaImagenes *lista);


/** 
 * @brief Se inserta una nueva imagen según orden de llegada, ¡si no está!, con el \p hascode y \p datos dados (1.75 pts). En caso de fallo al solicitar memoria, se sale del programa mostrando antes un mensaje de error. 
 * @param lista Lista de imágenes ya existente en el sistema.  
 * @param hashcode Identificador único de la imagen. 
 * @param datos Datos de la imagen, se deben copiar (incluye el carácter terminador)  
 * @return devuelve 0 si se puede insertar, 1 si no se puede insertar (duplicada). 
 */ 
int insertarimagen(ListaImagenes *lista, int hashcode, char *datos);


/** 
 * @brief Muestra las \p num primeras imágenes de la lista en orden de inserción (primero la más antigua). Si hay menos de \p num, las mostrará todas. Para cada imagen se debe mostrar el hascode y sólo los primeros 10 caracteres de sus datos (1 pt.). 
 * @param lista Lista de imágenes ya existente en el sistema. 
 * @param num Número máximo de imágenes a mostrar. 
 */ 
void mostrarImagenes(ListaImagenes lista,int num);


/** 
 * @brief Libera toda la memoria y deja la \p lista de imágenes vacía (1 pt.). 
 * @param lista Lista de imágenes ya existente en el sistema. 
 */ 
void destruirImagenes(ListaImagenes *lista);



/** 
 * @brief Extrae la imagen más antigua y la quita de \p lista (0.5 pt.). 
 * @param lista Lista de imágenes ya existente en el sistema. 
 * @return Un puntero a la imagen más antigua o NULL si no existe. 
 */ 
Imagen* extraercabeza(ListaImagenes *lista); 

/** 
 * @brief Extrae una ListaImagenes que contiene las \p num imágenes más antiguas y las quita de \p lista (1.75 pt.). 
 * @param lista Lista de imágenes ya existente en el sistema. 
 * @param num Número mayor que cero que indica el máximo de imágenes a extraer en la lista. 
 * @return NULL si no hay ninguna imagen que extraer o una nueva lista con las, hasta \p num, imágenes más antiguas. 
*/ 
ListaImagenes extraerLista(ListaImagenes *lista, int num);


/** 
 * @brief Escribe en un fichero de texto parte de los datos de las imágenes almacenadas en la lista (1.75 pts.). El formato del fichero de texto será el siguiente: primero tendrá una cabecera con una descripción de los campos. Tras esta cabecera, una línea por cada imagen, ordenadas por antigüedad (las más antiguas primero). Solo se almacenan los 100 primeros bytes de cada imagen. Ejemplo: 
Hashcode;Imagen 
1286; fsgf8sddsdfsdfnlnlsdfoisdfolskndflsndfoinlksngm ,m oiw09tuqargnl ... ; 
864; aborasenhlakfhg,m ,miohlksndfgnqwoiut80q4760  oiqrnwglkjWR089TQH4OLGRN  ... ; 
9874; 09234jfilnalkfngoiahrñktnmp9ur0q914u63091u3504ypihnñFGBP90rueyphnñRKG ... ; 
 * @param filename Nombre del fichero para escribir en él. 
 * @param lista Lista de imágenes del sistema. 
 */ 
void guardarRegistroImagenes(char *filename, ListaImagenes lista);


/** 
 * @brief Lee de fichero binario los datos de imágenes y los carga para su uso. En caso de fallo al solicitar memoria, se sale del programa mostrando antes un mensaje de error. La lista actual puede no estar vacía, recuerda antes borrar todas las imágenes existentes (2.0 pts.).  
 *  En el fichero binario se almacena la información de cada imagen con el siguiente formato: 
 *      un entero con el hascode de la imagen;  
 *      una cadena de longitud IMAGE_SIZE bytes con la imagen. Esta cadena ya incluye el carácter terminador '\0'. 
 *  Se asume que las imágenes están guardadas por antigüedad, siendo las primeras las más antiguas. 
 * @param filename Nombre del fichero para leer de él. 
 * @param lista Lista en la que se almacenan las imágenes leídas. 
 */ 
void cargarRegistroImagenes(char *filename, ListaImagenes *lista);

#endif /* IMAGENES_H_ */
