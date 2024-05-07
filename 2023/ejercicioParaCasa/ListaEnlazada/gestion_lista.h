#ifndef _GESTION_LISTA_
#define _GESTION_LISTA_


typedef struct Lista_Entero * ptr_lista_entero;
struct Lista_Entero{
    int numero;
    ptr_lista_entero sig;
};

/**
 * @brief Inicializa la lista a nulo.
 * @param ptr_lista Dirección de memoria de la lista a inicializar.
 */
void inicializar(ptr_lista_entero * ptr_lista);

/**
 * @brief Se destruye la lista que se pasa por referencia (usa free para liberar).
 * @param ptr_lista Dirección de memoria de la lista a borrar.
 */
void destruir(ptr_lista_entero * ptr_lista);

/**
 * @brief Inserta el elemento @p valor en la cabeza de la lista @p ptr_lista
 * @param ptr_lista Dirección de memoria de la lista a insertar el elemento
 * @param valor Valor a ser insertado, no se controlan duplicados.
 * @param ok Dirección de memoria a un entero que contendrá 0 si no se puede insertar (malloc falla), o 1 si si se puede.
 */
void insertarCabeza(ptr_lista_entero * ptr_lista, int valor, int * ok);

/**
 * @brief Inserta el elemento @p valor en la cola de la lista @p ptr_lista
 * @param ptr_lista Dirección de memoria de la lista a insertar el elemento
 * @param valor Valor a ser insertado, no se controlan duplicados.
 * @param ok Dirección de memoria a un entero que contendrá 0 si no se puede insertar (malloc falla), o 1 si si se puede.
 */
void insertarCola(ptr_lista_entero * ptr_lista, int valor, int * ok);

/**
 * @brief Inserta el elemento en la primera posición en la que todos los anteriores son menores que él. Ojo, que luego se puede volver a desordenar, eso no es tu problema (es por prácticar :))
 * @param ptr_lista Dirección de memoria de la lista a insertar el elemento
 * @param valor Valor a ser insertado, no se controlan duplicados.
 * @param ok Dirección de memoria a un entero que contendrá 0 si no se puede insertar (malloc falla), o 1 si si se puede.
 */
void insertarOrdenado(ptr_lista_entero * ptr_lista, int valor, int * ok);



/**
 * @brief Borra el elemento que está en la cabeza, si existe.
 * @param ptr_lista Dirección de memoria de la lista a borrar el elemento
 * @param ok  
 */
void borrarCabeza(ptr_lista_entero * ptr_lista, int * ok);

/**
 * @brief Borra el elemento que está en la cola, si existe.
 * @param ptr_lista Dirección de memoria de la lista a borrar el elemento
 * @param ok 
 */
void borrarCola(ptr_lista_entero * ptr_lista, int * ok);


/**
 * @brief Intenta extraer los @p n primeros elementos de la @p ptr_lista, en @p m indica cuántos a podido extraer.
 * @param ptr_lista Dirección de memoria de la lista de la que se extraeran los elementos.
 * @param n Número de elementos a extraer mayor que cero.
 * @param m Número de elementos que se han podido extraer
 * @return Dirección de memoria en la que están los @p m elementos extraídos.
 */
ptr_lista_entero quitarNCabeza(ptr_lista_entero * ptr_lista, int n, int * m);

#endif
