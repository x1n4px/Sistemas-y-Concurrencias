/*
============================================================================
 Name        : gestion_memoria.h
 Author      :
 Version     :
 Copyright   : Your copyright notice
 Description :
 ============================================================================
 */

#ifndef _GESTION_TREMOR_
#define _GESTION_TREMOR_

#include <time.h>
typedef struct T_Nodo *T_Lista;

/*
Se van ha almacenar por orden cronológico, primero los más antiguos.
*/
struct T_Nodo
{
	time_t fecha;
	unsigned duracion;
	T_Lista sig;
};

/// @brief Inicializa unas listas vacias.
/// @param ptr_lista_nuevos Lista nuevos elementos.
/// @param ptr_lista_procesados Lista elementos procesados.
void iniciar(T_Lista *ptr_lista_nuevos, T_Lista *ptr_lista_procesados);

/// @brief Muestra los episodios de tremor que ha tenido el usuario del más nuevo al más antiguo. Usa ctime(&(lista->fecha)) 
///para conseguir la fecha en formato texto (char *). Se valora la eficiencia, usa recursividad
/// @param lista Lista con los elementos nuevos a mostrar.
void mostrar_nuevos2antiguos(T_Lista lista);

/// @brief Registra un episodio de tremor, con su fecha y duración, primero van los mas antiguos.
/// @param ptr_lista_nuevos Lista de entrada en la que insertar el nuevo episodio.
/// @param fecha Fecha (por referencia) del nuevo episodio.
/// @param duracion Duración del nuevo episodio.
/// @param ok Parámetro de salida que vale 1 si se ha podido insertar y 0 si no.
void registrar(T_Lista *ptr_lista_nuevos, const time_t *fecha, unsigned duracion, unsigned *ok);

/// @brief Pasa a la lista de procesados todos los episodios de la lista nuevos que son posteriores a la fecha dada. Ojo, la lista de procesados puede no estar vacia.
/// @param ptr_lista_nuevos Lista de eventos sin procesar.
/// @param fecha Fecha (por referencia) en la que se va a dividir la lista.
/// @param ptr_lista_procesados Lista en la que se van a insertar los extraidos de @ptr_lista_nuevos
/// @return
void procesar(T_Lista *ptr_lista_nuevos, const time_t *fecha, T_Lista *ptr_lista_procesados);

/// @brief Destruye las estructuras utilizadas (libera todos los nodos de las listas. Debe terminar apuntando a NULL *
/// @param ptr_lista_nuevos
/// @param ptr_lista_procesados
void destruir(T_Lista *ptr_lista_nuevos, T_Lista *ptr_lista_procesados);

#endif
