/*
 ============================================================================
 Name        : PracticaListaOrdenada.c
 Author      :
 Version     :
 Copyright   : Your copyright notice
 Description :
 ============================================================================
 */

#include <stdlib.h>
#include <assert.h>
#include "gestion_lista.h"

int main(void)
{
	Lista l;
	crearLista(&l);
	assert(l == NULL);
	assert(listaVacia(l) == 0);

	assert(insertarLista(&l, 2) == 1);
	assert(insertarLista(&l, 4) == 1);
	assert(insertarLista(&l, 1) == 1);
	assert(insertarLista(&l, 5) == 1);
	assert(l->elem == 1);
	assert(l->sig->elem == 2);
	assert(l->sig->sig->elem == 4);
	assert(l->sig->sig->sig->elem == 5);

	recorrerLista(l);

	assert(extraerLista(&l, 0) == 0);
	assert(extraerLista(&l, 1) == 1);
	assert(extraerLista(&l, 4) == 1);
	assert(extraerLista(&l, 5) == 1);

	recorrerLista(l);

	borrarLista(&l);
	assert(l == NULL);

	return EXIT_SUCCESS;
}
