#include "gestion_lista.h"
#include <assert.h>
#include <stdlib.h>


/**
 * @brief Inicializa la lista a nulo.
 * @param ptr_lista Dirección de memoria de la lista a inicializar.
 */
void inicializar(ptr_lista_entero * ptr_lista){
    *ptr_lista = NULL;
}

/**
 * @brief Se destruye la lista que se pasa por referencia (usa free para liberar).
 * @param ptr_lista Dirección de memoria de la lista a borrar.
 */
void destruir(ptr_lista_entero * ptr_lista){
    ptr_lista_entero aux = *ptr_lista;
    while(aux != NULL){
        *ptr_lista = (*ptr_lista)->sig;
        free(aux);
        aux = *ptr_lista;
    }
}


/**
 * @brief Inserta el elemento @p valor en la cabeza de la lista @p ptr_lista
 * @param ptr_lista Dirección de memoria de la lista a insertar el elemento
 * @param valor Valor a ser insertado, no se controlan duplicados.
 * @param ok Dirección de memoria a un entero que contendrá 0 si no se puede insertar (malloc falla), o 1 si si se puede.
 */
void insertarCabeza(ptr_lista_entero * ptr_lista, int valor, int *ok){
    ptr_lista_entero aux = malloc(sizeof(struct Lista_Entero));
    *ok = 0;
    if(aux != NULL){
        aux->numero = valor;
        aux->sig = *ptr_lista;
        *ptr_lista = aux;
        *ok = 1;
    }
}

/**
 * @brief Inserta el elemento @p valor en la cola de la lista @p ptr_lista
 * @param ptr_lista Dirección de memoria de la lista a insertar el elemento
 * @param valor Valor a ser insertado, no se controlan duplicados.
 * @param ok Dirección de memoria a un entero que contendrá 0 si no se puede insertar (malloc falla), o 1 si si se puede.
 */
void insertarCola(ptr_lista_entero * ptr_lista, int valor, int *ok){
    ptr_lista_entero aux = malloc(sizeof(struct Lista_Entero));
    *ok = 0;
    if(aux != NULL){
        aux->numero = valor;
        aux->sig = NULL;
        *ok = 1;
        if((*ptr_lista) == NULL){
            (*ptr_lista) = aux;
        }else{
            ptr_lista_entero iter = *ptr_lista;
            while(iter->sig != NULL) iter = iter->sig;
            iter->sig = aux;
        }
    }
}

/**
 * @brief Inserta el elemento en la primera posición en la que todos los anteriores son menores que él. Ojo, que luego se puede volver a desordenar, eso no es tu problema (es por prácticar :))
 * @param ptr_lista Dirección de memoria de la lista a insertar el elemento
 * @param valor Valor a ser insertado, no se controlan duplicados.
 * @param ok Dirección de memoria a un entero que contendrá 0 si no se puede insertar (malloc falla), o 1 si si se puede.
 */
void insertarOrdenado(ptr_lista_entero * ptr_lista, int valor, int *ok){
    ptr_lista_entero aux = malloc(sizeof(struct Lista_Entero));
    *ok = 0;
    if(aux != NULL){
        aux->numero = valor;
        *ok = 1;
        if((*ptr_lista) == NULL || (*ptr_lista)->numero > valor){
            aux->sig = *ptr_lista;
            *ptr_lista = aux;
        }else{
            ptr_lista_entero iter = *ptr_lista;
            while(iter->sig != NULL && valor > iter->sig->numero) iter = iter->sig;
            aux->sig = iter->sig;
            iter->sig = aux;
        }
    }
}

/**
 * @brief Borra el elemento que está en la cabeza, si existe.
 * @param ptr_lista Dirección de memoria de la lista a borrar el elemento
 * @param ok  
 */
void borrarCabeza(ptr_lista_entero * ptr_lista, int * ok){  
    ptr_lista_entero aux = *ptr_lista;
    *ok = 0;
    if(aux != NULL){
        *ptr_lista = (*ptr_lista)->sig;
        free(aux);
        *ok = 1;
    }
}

/**
 * @brief Borra el elemento que está en la cola, si existe.
 * @param ptr_lista Dirección de memoria de la lista a borrar el elemento
 * @param ok 
 */
void borrarCola(ptr_lista_entero * ptr_lista, int * ok){
    ptr_lista_entero aux = *ptr_lista;
    *ok = 0;
    if(aux != NULL){
        *ok = 1;
        if(aux->sig == NULL){ // Un solo elemento
            *ptr_lista = NULL;
            free(aux);
        }else{
            //Necesitamos un puntero que apunte al anterior al último cuando terminamos de iterar.
            //Sabemos que el último es el que tiene su sig a NULL
            //Vamos a preguntar por el aux->sig->sig == NULL, cuando se cumpla, 
            //el elemento a borrar es: aux->sig
            while(aux->sig->sig != NULL) aux = aux->sig;
            free(aux->sig);
            aux->sig = NULL;
        }
    }
}


/**
 * @brief Intenta extraer los @p n primeros elementos de la @p ptr_lista, en @p m indica cuántos a podido extraer.
 * @param ptr_lista Dirección de memoria de la lista de la que se extraeran los elementos.
 * @param n Número de elementos a extraer mayor que cero.
 * @param m Número de elementos que se han podido extraer
 * @return Dirección de memoria en la que están los @p m elementos extraídos.
 */
ptr_lista_entero quitarNCabeza(ptr_lista_entero * ptr_lista, int n, int * m){
   assert(n > 0);
   *m = 0;
   ptr_lista_entero a_devolver = *ptr_lista;

   if(a_devolver != NULL) {
    //Si hay elementos para quitar

    if(n == 1){
        //Si me piden un elemento, quito el primerop y he terminado (me da igual cuantos tenga)
        *ptr_lista = (*ptr_lista)->sig;
        a_devolver->sig = NULL;
        *m = 1;
    }else{
        //Si me piden más de un elemento
        if(a_devolver->sig == NULL){
            //Pero solo hay un elemento...devuelvo ese
            *ptr_lista = (*ptr_lista)->sig;
            a_devolver->sig = NULL;
            *m = 1;
        }else{
            //Me piden mas de un elemento, y hay mas de un elemento, a iterar!
            *m = 1;
            ptr_lista_entero aux = *ptr_lista;
            while((aux->sig != NULL) && (*m < n)){
                //En aux->sig, está donde debo cortar
                (*m)++;
                aux = aux->sig;
            }
            *ptr_lista = aux->sig;
            aux->sig = NULL;
        }
    }
    return a_devolver;
   }
   return NULL;
}


