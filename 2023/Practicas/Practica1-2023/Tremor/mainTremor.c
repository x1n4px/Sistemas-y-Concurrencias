

// Macro, si es windows incluye una libería, si no, incluye otra.
#ifdef _WIN32
#include <Windows.h>
#else
#include <unistd.h>
#endif

#include <stdio.h>
#include <stdlib.h>
#include <assert.h>
#include "gestion_tremor.h"

int main(void)
{

	int ok;
	T_Lista lista_nuevos;
	T_Lista lista_procesados;
	time_t now_time;
	time_t pass_time;

	iniciar(&lista_nuevos, &lista_procesados);
/**/
	time(&now_time);
	registrar(&lista_nuevos, &now_time, 60, &ok);

// Nos paramos un segundo, para simular un retraso en el registro de nuevos episodios
#ifdef _WIN32
	Sleep(1000);
#else
	sleep(1);
#endif

	assert(lista_nuevos->fecha == now_time);
	assert(lista_nuevos->duracion == 60);
	assert(lista_nuevos->sig == NULL);
	assert(ok == 1);

	time(&now_time);
	registrar(&lista_nuevos, &now_time, 50, &ok);
	assert(ok == 1);
	assert(lista_nuevos->sig->fecha == now_time);
	assert(lista_nuevos->sig->duracion == 50);
	assert(lista_nuevos->sig->sig == NULL);

#ifdef _WIN32
	Sleep(1000);
#else
	sleep(1);
#endif

	// Guardamos este momento para eliminar todos los anteriores
	time(&pass_time);
	time(&now_time);
	registrar(&lista_nuevos, &now_time, 40, &ok);
	assert(ok == 1);
	assert(lista_nuevos->sig->sig->fecha == now_time);
	assert(lista_nuevos->sig->sig->duracion == 40);
	assert(lista_nuevos->sig->sig->sig == NULL);

#ifdef _WIN32
	Sleep(1000);
#else
	sleep(1);
#endif
	mostrar_nuevos2antiguos(lista_nuevos);
	procesar(&lista_nuevos, &pass_time, &lista_procesados);
	mostrar_nuevos2antiguos(lista_nuevos);
	mostrar_nuevos2antiguos(lista_procesados);

	assert(lista_nuevos->sig->sig == NULL);
	assert(lista_procesados->duracion == 40);
	assert(lista_procesados->sig == NULL);

	destruir(&lista_nuevos, &lista_procesados);

	return EXIT_SUCCESS;
}
