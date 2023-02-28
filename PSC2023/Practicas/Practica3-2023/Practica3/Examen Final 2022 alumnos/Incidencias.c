/*
 ============================================================================
 Name        : Main2022.c
 Authors     : JB,
 Version     :
 Copyright   : Your copyright notice
 Description :
 ============================================================================
 */
#include <stdio.h>
#include "Incidencias.h"

// Contador del número de incidencias, se inicializa a 0.
int contId;

// Inicializa el array de incidencias ya creado en el main (ListaIncidencias *array) con sus elementos a nulo. El array tiene tamaño t.
// Inicializa la variable contId a cero.
void inicializarListaIncidencias(ListaIncidencias *array, int t)
{
    for (int i = 0; i < t; i++)
    {
        array[i] = NULL;
    }
    contId = 0;
}

// Se inserta una nueva incidencia con la prioridad y descripción dada. Asumimos que no van a insertar una duplicada, y que el array tiene longitud para almacenar la prioridad dada. Esta función devuelve el id de la incidencia generada. Recuerda que el array ya esta creado en el main.
int insertarIncidencia(ListaIncidencias *array, int prioridad, char *descripcion)
{
    Incidencia *nuevaIncidencia = (Incidencia *)malloc(sizeof(Incidencia));
    nuevaIncidencia->id = contId++;
    nuevaIncidencia->puntuacion = prioridad;
    nuevaIncidencia->descripcion = descripcion;
    nuevaIncidencia->siguiente = NULL;

    int i = 0;
    while (array[i] != NULL)
    {
        i++;
    }

    array[i] = nuevaIncidencia;

    return nuevaIncidencia->id;
}

// Muestra las incidencias por orden de prioridad, primero las más prioritarias. Se debe mostrar la prioridad, su descripción, y su evaluación.
// [Prioridad0 – id0] Descripción1 Sin Evaluar
// [Prioridad0 – id1] Descripción2 Evaluada: 3
// [Prioridad1 – id2] Descripción3 Evaluada: 4
// t es el tamaño del array.
void mostrarIncidencias(ListaIncidencias *array, int t)
{
    Incidencia *incidenciaActual = (Incidencia *)malloc(sizeof(Incidencia));
    printf("Incidencias:\n");
    for (int prioridad = 0; prioridad < t; prioridad++)
    {
        ListaIncidencias incidenciaActual = array[prioridad];
        while (incidenciaActual != NULL)
        {
            printf("[Prioridad%d – id%d] %s ", prioridad, incidenciaActual->id, incidenciaActual->descripcion);
            if (incidenciaActual->puntuacion == -1)
            {
                printf("Sin Evaluar\n");
            }
            else
            {
                printf("Evaluada: %d\n", incidenciaActual->puntuacion);
            }
            incidenciaActual = incidenciaActual->siguiente;
        }
    }
}

// Libera toda la memoria y deja el array de incidencias vacío. Reinicia contId a 0.
// t es el tamaño del array.
void destruirIncidencias(ListaIncidencias *array, int t)
{
    Incidencia *current = (Incidencia *)malloc(sizeof(Incidencia));

    int i;
    for (i = 0; i < t; i++)
    {
        ListaIncidencias current = array[i];
        while (current != NULL)
        {
            ListaIncidencias next = current->siguiente;
            free(current);
            current = next;
        }
        array[i] = NULL;
    }
    contId = 0;
}

// Cambiar prioridad a incidencia existente. Se recomienda hacer una función auxiliar que devuelva la prioridad de una incidencia dado su id.
// t es el tamaño del array.
// 1.75 pt.

int buscarPrioridad(ListaIncidencias incidencias, int id)
{
    while (incidencias != NULL)
    {
        if (incidencias->id == id)
        {
            return incidencias->puntuacion;
        }
        incidencias = incidencias->siguiente;
    }
    return -1; // Si no se encontró la incidencia, devuelve -1
}

void cambiarPrioridad(ListaIncidencias *array, int id, int nuevaPrioridad, int t)
{
    Incidencia *current = (Incidencia *)malloc(sizeof(Incidencia));

    int i;
    for (i = 0; i < t; i++)
    {
        ListaIncidencias current = array[i];
        while (current != NULL)
        {
            if (current->id == id)
            {
                current->puntuacion = nuevaPrioridad;
                return;
            }
            current = current->siguiente;
        }
    }
}

// Establecer la evaluación con valor valorEvaluacion a la incidencia con id existente.
// Si el árbol está vacío, se devuelve NULL.
// t es el tamaño del array.
// 0.5 pt.
void evaluarIncidencia(ListaIncidencias *array, int id, int valorEvaluacion, int t)
{
    Incidencia *current = (Incidencia *)malloc(sizeof(Incidencia));

    int i;
    for (i = 0; i < t; i++)
    {
        ListaIncidencias current = array[i];
        while (current != NULL)
        {
            if (current->id == id)
            {
                if (current->puntuacion == NULL)
                {
                    current->puntuacion = crearNodo(valorEvaluacion);
                }
                else
                {
                    actualizarNodo(current->puntuacion, valorEvaluacion);
                }
                return;
            }
            current = current->siguiente;
        }
    }
}

// Guarda en un fichero de texto los datos de las incidencias almacenadas en la lista de incidencias. El formato del fichero de texto
// será el siguiente, primero tendrá una cabecera con una descripción de los campos. Tras esta cabecera, una línea por cada incidencia,
// ordenadas por prioridad primero y luego por antigüedad (las más antiguas primero). En caso de no estar evaluada una incidencia, el
// campo valor será -1;

// Prioridad;Descripcion;Puntacion;
// 0;Puerta 002 no cierra correctamente;-1;
// 0;Puerta 004 no cierra correctamente;5;
// 9;Puerta 904 no cierra correctamente;100;

// t es el tamaño del array.
void guardarRegistroIncidencias(char *filename, ListaIncidencias *array, int t)
{
    Incidencia *incidencia = (Incidencia *)malloc(sizeof(Incidencia));

    // Abrir el archivo en modo escritura
    FILE *file = fopen(filename, "w");
    if (!file)
    {
        printf("Error al abrir el archivo %s.\n", filename);
        return;
    }

    // Escribir la cabecera
    fprintf(file, "Prioridad;Descripcion;Puntuacion;\n");

    // Ordenar las incidencias por prioridad primero y luego por antigüedad
    ordenarPorPrioridad(array, t);

    // Escribir una línea por cada incidencia
    for (int i = 0; i < t; i++)
    {
        incidencia = array[i];
        fprintf(file, "%d;%s;%d;\n", incidencia->id, incidencia->descripcion, incidencia->puntuacion);
    }

    // Cerrar el archivo
    fclose(file);
}

// Lee de fichero binario los datos de incidencias y los carga para su uso. El array puede no estar vario, recuerda antes borrar todas las incidencias existentes.
// Cada incidencia es almacenada en el fichero con la siguiente estructura:
//- Un entero con la prioridad de la incidencia.
//- Un entero con la puntuación.
//- Un entero con el tamaño del campo descripción (incluído el carácter terminador).
//- La cadena de caracteres con la descripción, incluido el carácter terminador '\0'.
// Se asume que las incidencias están guardadas por antigüedad, siendo las primeras las más antiguas.
// t es el tamaño del array.
void cargarRegistroIncidencias(char *filename, ListaIncidencias *array, int t)
{
    Incidencia *incidencia = (Incidencia *)malloc(sizeof(Incidencia));

    // Borrar todas las incidencias existentes
    vaciarListaIncidencias(array, t);

    // Abrir el archivo en modo lectura binaria
    FILE *file = fopen(filename, "rb");
    if (!file)
    {
        printf("Error al abrir el archivo %s.\n", filename);
        return;
    }

    // Leer una incidencia a la vez y añadirla al array
    int i = 0;
    while (i < t && !feof(file))
    {
        // Leer los datos de la incidencia del archivo
        int prioridad, puntuacion, tamano_descripcion;
        fread(&prioridad, sizeof(int), 1, file);
        fread(&puntuacion, sizeof(int), 1, file);
        fread(&tamano_descripcion, sizeof(int), 1, file);
        char *descripcion = malloc(tamano_descripcion);
        fread(descripcion, sizeof(char), tamano_descripcion, file);

        // Crear la incidencia y añadirla al array
        incidencia->id = prioridad;
        incidencia->puntuacion = puntuacion;
        strcpy(incidencia->descripcion, descripcion);
        insertarListaIncidencias(&incidencia, array, t);
        free(descripcion);

        i++;
    }

    // Cerrar el archivo
    fclose(file);
}
