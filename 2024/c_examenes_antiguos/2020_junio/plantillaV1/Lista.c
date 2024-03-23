#include <stdlib.h>
#include <stdio.h>
#include <string.h>

#include "Lista.h"
#include <stdbool.h>

/** Crea una lista vacia */
void crear(TLista *lista)
{
    *lista = NULL;
}

/** Muestra el contenido de la lista.
 *   - Si la lista esta vacia muestra el mensaje "Lista vacia..."
 *   - Si la lista no esta vacia la muestra en el formato:
 *       <valor>:<palo> <valor>:<palo> <valor>:<palo>
 */
void mostrar(TLista lista)
{
    if (lista == NULL)
    {
        printf("Lista vacia...");
        return;
    }

    TLista ptr = lista;
    while (ptr->sig != NULL)
    {
        printf("<%d>:<%c>  ", ptr->carta.valor, ptr->carta.palo);
        ptr = ptr->sig;
    }
    printf("\n");
}

/* Inserta la carta en la lista ordenada primero por el palo y luego,
 * para las cartas del mismo palo, por el valor.
 * En ambos casos en orden ascendente.
 *
 * Podemos suponer que la carta a insertar no esta en la lista.
 */
void insertarOrdenado(TLista *lista, TCarta carta)
{
    TLista nuevo = (TLista)malloc(sizeof(struct TNodo));
    nuevo->carta = carta;
    nuevo->sig = NULL;

    if (*lista == NULL)
    {
        *lista = nuevo;
        return;
    }

    TLista actual = *lista;
    TLista anterior = NULL;

    while (actual != NULL && (strcmp(&carta.palo, &actual->carta.palo) > 0 ||
                              (strcmp(&carta.palo, &actual->carta.palo) == 0 && carta.valor > actual->carta.valor)))
    {
        anterior = actual;
        actual = actual->sig;
    }

    if (anterior == NULL)
    {
        // Insertar al principio
        nuevo->sig = *lista;
        *lista = nuevo;
    }
    else
    {
        // Insertar después de "anterior"
        nuevo->sig = anterior->sig;
        anterior->sig = nuevo;
    }
}

/* Elimina toda la memoria dinamica reservada para la lista. */
void destruir(TLista *lista)
{
    TLista aux;
    while (*lista != NULL)
    {
        aux = *lista;
        *lista = (*lista)->sig;
        free(aux);
    }
}

/* Borra una carta de la lista, teniendo en cuenta que la lista esta ordenada.
 * Se puede suponer que la carta a borrar estara en la lista.
 */
void borrar(TLista *lista, TCarta carta)
{
    TLista aux = *lista;

    if (aux == NULL)
    {
        return;
    }

    TLista anterior = NULL;

    while (aux != NULL && (strcmp(&carta.palo, &aux->carta.palo) > 0 ||
                           (strcmp(&carta.palo, &aux->carta.palo) == 0 && carta.valor != aux->carta.valor)))
    {
        anterior = aux;
        aux = aux->sig;
    }

    anterior->sig = aux->sig;
}

/* Descarta la primera carta de la lista2 que pueda ser insertada en la lista1
 * siguiendo el siguiente criterio:
 *
 * Para poder descartar la carta (palo, valor) de la lista2 se hara lo siguiente:
 *   - se comprueba si en la lista1 existe una carta con el mismo palo y un valor
 *     inmediatamente anterior o posterior. Es decir, la carta (palo, valor-1) o (palo, valor + 1)
 *   - si existe, la carta se elimina de lista2 y se inserta en lista1, de forma que lista1
       siga estando ordenada.
 *
 *   La funcion devuelve 1 si el jugador ha podido descartarse una carta y 0 en otro caso.
 *   En cada llamada a esta funcion el jugador se descartara una sola carta, aunque haya mas
 *   cartas descartables en la mano del jugador.
 *   Se puede suponer que las cartas de lista2 no están en lista1.
 */

int descartar(TLista *lista1, TLista *lista2)
{
    TLista actual2 = *lista2;
    TLista anterior1 = NULL;
    TLista actual1 = *lista1;

    // Recorrer la lista2
    if (actual1 == NULL)
    {
        return 0;
    }
    // Buscar carta con mismo palo y valor adyacente en lista1
    while (actual1 != NULL && (actual1->carta.palo == actual2->carta.palo &&
                               (actual1->carta.valor - actual2->carta.valor != 1)))
    {
        printf("valor de la resta: %d \n", actual1->carta.valor - actual2->carta.valor);
        anterior1 = actual1;
        actual1 = actual1->sig;
    }

    // Si se encontró una carta adyacente
    if (actual1->carta.valor - actual2->carta.valor == 1)
    {

        *lista2 = actual2->sig;

        insertarOrdenado(lista1, actual2->carta);

        return 1; // Se descartó una carta
    }
    else
    {
        return 0;
    }

    // No se encontró ninguna carta descartable
    return 0;
}

/* Suma y devuelve el valor de todas las cartas de la lista,
 * independientemente de su palo.
 */
int sumar(TLista lista)
{
    int suma = 0;
    TLista aux = lista;
    while (aux != NULL)
    {
        suma += aux->carta.valor;
        aux = aux->sig;
    }
    return suma;
}

/*
void crearDesdeFichero(char *fichero, TLista *mazo, TLista *jugador1, TLista *jugador2) {
  FILE *f = fopen(fichero, "r");
  if (f == NULL) {
    printf("Error al abrir el fichero\n");
    return;
  }

  TCarta carta;
  char palo;
  int valor;
  int fila = 0;

  while (!feof(f)) {
    int caracter = fgetc(f);

    if (caracter == '\n') {
      fila++;
      continue;
    }

    ungetc(caracter, f); // Devolver el caracter leído al flujo

    if (fscanf(f, "%d:%c", &valor, &palo) == 2) {
      carta.valor = valor;
      carta.palo = palo;

      if (fila == 0) {
        insertarOrdenado(mazo, carta);
      } else if (fila == 1) {
        insertarFinal(jugador1, carta);
      } else {
        insertarFinal(jugador2, carta);
      }
    }
  }

  fclose(f);
}*/

