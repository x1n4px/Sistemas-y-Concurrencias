/*
 ============================================================================
 Name        : Main2022.c
 Authors     : JB,
 Version     :
 Copyright   : Your copyright notice
 Description :
 ============================================================================
 */

#include "Incidencias.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

// Contador del número de incidencias, se inicializa a 0.
int contId;

// Inicializa el array de incidencias ya creado en el main (ListaIncidencias *array) con sus elementos a nulo. El array tiene tamaño t.
// Inicializa la variable contId a cero.
// 0.25 pts.
void inicializarListaIncidencias(ListaIncidencias *array, int t)
{
    contId = 0;
    for (int i = 0; i < t; i++)
    {

        array[i] = NULL;
    }
}

// Se inserta una nueva incidencia con la prioridad y descripción dada. Asumimos que no van a insertar una duplicada, y que el array tiene
// longitud para almacenar la prioridad dada. Esta función devuelve el id de la incidencia generada. Recuerda que el array ya esta creado en el main.
// 1.75 pts.
int insertarIncidencia(ListaIncidencias *array, int prioridad, char *descripcion)
{
    // Crear una nueva incidencia
    Incidencia *nuevaIncidencia = (Incidencia *)malloc(sizeof(Incidencia));
    if (nuevaIncidencia == NULL)
    {
        printf("ERROR: No se pudo asignar memoria para la nueva incidencia\n");
        return -1;
    }

    // Asignar valores a la nueva incidencia
    nuevaIncidencia->id = contId++;   // Incrementa el id y lo asigna
    nuevaIncidencia->puntuacion = -1; // Inicializa la puntuación como no evaluada
    strncpy(nuevaIncidencia->descripcion, descripcion, 49);
    nuevaIncidencia->descripcion[49] = '\0'; // Asegura que la cadena de descripción esté terminada correctamente

    // Insertar la nueva incidencia al final de la lista correspondiente a la prioridad dada
    if (array[prioridad] == NULL)
    {
        // Si la lista para esta prioridad está vacía, simplemente agregamos la nueva incidencia
        array[prioridad] = nuevaIncidencia;
    }
    else
    {
        // Si no está vacía, recorremos hasta el final de la lista y luego agregamos la nueva incidencia
        Incidencia *ptr = array[prioridad];
        while (ptr->siguiente != NULL)
        {
            ptr = ptr->siguiente;
        }
        ptr->siguiente = nuevaIncidencia;
    }

    // Establecer el siguiente de la nueva incidencia como NULL, ya que es la última en la lista
    nuevaIncidencia->siguiente = NULL;

    // Devolver el id de la incidencia generada
    return nuevaIncidencia->id;
}

// Muestra las incidencias por orden de prioridad, primero las más prioritarias. Se debe mostrar la prioridad, su descripción, y su evaluación.
// [Prioridad0 – id0] Descripción1 Sin Evaluar
// [Prioridad0 – id1] Descripción2 Evaluada: 3
// [Prioridad1 – id2] Descripción3 Evaluada: 4
// t es el tamaño del array.
// 1.0 pt.
void mostrarIncidencias(ListaIncidencias *array, int t)
{
    for (int i = 0; i < t; i++)
    {
        printf("Prioridad%d:\n", i);
        Incidencia *ptr = array[i];
        while (ptr != NULL)
        {
            printf("[Priodidad%d - id%d] %s", i, ptr->id, ptr->descripcion);
            if (ptr->puntuacion == -1)
            {
                printf(" Sin evaluar\n");
            }
            else
            {
                printf(" Evaluada: %d\n", ptr->puntuacion);
            }
            ptr = ptr->siguiente;
        }
    }
}

// Libera toda la memoria y deja el array de incidencias vacío. Reinicia contId a 0.
// t es el tamaño del array.
// 1 pt.
void destruirIncidencias(ListaIncidencias *array, int t)
{

    for (int i = 0; i < t; i++)
    {
        ListaIncidencias aux = array[i];
        while (aux != NULL)
        {
            ListaIncidencias temp = aux;
            aux = temp->siguiente;
            free(temp);
        }
        array[i] = NULL;
    }
    contId = 0;
}

// Cambiar prioridad a incidencia existente. Se recomienda hacer una función auxiliar que devuelva la prioridad de una incidencia dado su id.
// t es el tamaño del array.
// 1.75 pt.

int obtenerPrioridadIncidencia(ListaIncidencias *array, int id, int t)
{
    for (int i = 0; i < t; i++)
    {
        ListaIncidencias ptr = array[i];
        while (ptr != NULL)
        {
            if (ptr->id == id)
            {
                return i;
            }
            ptr = ptr->siguiente;
        }
    }
    return -1;
}

void cambiarPrioridad(ListaIncidencias *array, int id, int nuevaPrioridad, int t)
{
    int prioridadActual = obtenerPrioridadIncidencia(array, id, t);
    if (prioridadActual == -1)
    {
        printf("ERROR: La incidencia con id %d no existe.\n", id);
        return;
    }

    Incidencia *ptr = array[prioridadActual];
    Incidencia *anterior = NULL;
    while (ptr != NULL)
    {
        if (ptr->id == id)
        {
            if (anterior == NULL)
            {
                array[prioridadActual] = ptr->siguiente;
            }
            else
            {
                anterior->siguiente = ptr->siguiente;
            }
            break;
        }
        anterior = ptr;
        ptr = ptr->siguiente;
    }

    ptr = array[nuevaPrioridad];
    if (ptr == NULL)
    {
        array[nuevaPrioridad] = ptr; // Primera posicion
    }
    else
    {
        while (ptr->siguiente != NULL)
        {
            ptr = ptr->siguiente;
        }
        ptr->siguiente = ptr;
    }
}

// Establecer la evaluación con valor valorEvaluacion a la incidencia con id existente.
// Si el árbol está vacío, se devuelve NULL.
// t es el tamaño del array.
// 0.5 pt.
void evaluarIncidencia(ListaIncidencias *array, int id, int valorEvaluacion, int t)
{
    for (int i = 0; i < t; i++)
    {
        ListaIncidencias ptr = array[i];
        while (ptr != NULL)
        {
            if (ptr->id == id)
            {
                ptr->puntuacion = valorEvaluacion;
                return;
            }

            ptr = ptr->siguiente;
        }
    }
    printf("ERROR: Las incidencias con id %d no existe.\n");
}

// Guarda en un fichero de texto los datos de las incidencias almacenadas en la lista de incidencias. El formato del fichero de texto será el siguiente, primero tendrá una cabecera con una descripción de los campos. Tras esta cabecera, una línea por cada incidencia, ordenadas por prioridad primero y luego por antigüedad (las más antiguas primero). En caso de no estar evaluada una incidencia, el campo valor será -1;

// Prioridad;Descripcion;Puntacion;
// 0;Puerta 002 no cierra correctamente;-1;
// 0;Puerta 004 no cierra correctamente;5;
// 9;Puerta 904 no cierra correctamente;100;

// t es el tamaño del array.
// 1.75 pts.
void guardarRegistroIncidencias(char *filename, ListaIncidencias *array, int t)
{
    int prioridad, puntuacion, i = 0;
    char descripcion[50];
    FILE *file = fopen(filename, "w");
    if (file == NULL)
    {
        perror("No se ha podido abrir el fichero");
    }

    fprintf(file, "Prioridad;Descripcion;Puntuacion;\n");

    for (int i = 0; i < t; i++)
    {
        ListaIncidencias ptr = array[i];
        while (ptr != NULL)
        {
            fprintf(file, "%d;%s;%d;\n", i, ptr->descripcion, ptr->puntuacion);
            ptr = ptr->siguiente;
        }
    }

    fclose(file);
}

// Lee de fichero binario los datos de incidencias y los carga para su uso. El array puede no estar vario, recuerda antes borrar todas las incidencias existentes.
// Cada incidencia es almacenada en el fichero con la siguiente estructura:
//- Un entero con la prioridad de la incidencia.
//- Un entero con el tamaño del campo descripción.
//- La cadena de caracteres con la descripción, incluido el carácter terminador '\0'.
//- Un entero con la puntuación.
// Se asume que las incidencias están guardadas por antigüedad, siendo las primeras las más antiguas.
// t es el tamaño del array.
// 2.0 pts.
void cargarRegistroIncidencias(char *filename, ListaIncidencias *array, int t)
{

    // Abrir el fichero en modo binario para lectura
    FILE *fichero = fopen(filename, "rb");

    // Si el fichero no se puede abrir, mostrar un mensaje de error y salir
    if (fichero == NULL)
    {
        printf("Error al abrir el fichero %s\n", filename);
        return;
    }

    // Borrar todas las incidencias existentes en el array
    destruirIncidencias(array, t);

    // Variable para almacenar la prioridad de la incidencia
    int prioridad;

    // Variable para almacenar el tamaño del campo descripción
    int tam_descripcion;

    // Variable para almacenar la cadena de caracteres con la descripción
    char descripcion[100];

    // Variable para almacenar la puntuación
    int puntuacion;

    // Bucle para leer las incidencias del fichero
    while (fread(&prioridad, sizeof(int), 1, fichero) == 1)
    {

        // Leer el tamaño del campo descripción
        fread(&tam_descripcion, sizeof(int), 1, fichero);
        printf("Tamaño del campo descripcion: %d", tam_descripcion);
        // Leer la cadena de caracteres con la descripción
        fread(descripcion, sizeof(char), tam_descripcion + 1, fichero);

        // Leer la puntuación
        fread(&puntuacion, sizeof(int), 1, fichero);

        // Añadir la incidencia al array
        insertarIncidencia(array, prioridad, descripcion);
    }

    // Cerrar el fichero
    fclose(fichero);
}
