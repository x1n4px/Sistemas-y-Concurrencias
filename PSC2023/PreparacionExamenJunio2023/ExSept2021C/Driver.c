/*
 * Driver.c
 *
 */

#include <stdio.h>
#include "GestionTren.h"


int main(){
	int pasajeros;
	setvbuf(stdout, NULL,_IONBF,0);
	Tren  tren= salirCochera();

	subirPasajero(&tren, 10,"Torremolinos");
	subirPasajero(&tren, 11,"Aeropuerto");
	subirPasajero(&tren, 7,"Fuengirola");
	subirPasajero(&tren, 1,"Aeropuerto");
	subirPasajero(&tren, 3,"Aeropuerto");
	mostrarPasajeros(tren);
	printf("***************************************************\n");

	pasajeros = parada(&tren, "Aeropuerto");
	printf("Ti to ti: Aeropuerto - se han bajado %d pasajeros\n", pasajeros);
	mostrarPasajeros(tren);
	printf("***************************************************\n");

	if (!subirPasajero(&tren, 10, "Plaza_Mayor"))
		printf("El asiento 10 ya estaba ocupado, no se sube el pasajero\n");

	subirPasajero(&tren, 9, "Plaza_Mayor");
	subirPasajero(&tren, 19, "Plaza_Mayor");
	subirPasajero(&tren, 5,"Fuengirola");

	mostrarPasajeros(tren);
	printf("***************************************************\n");

	pasajeros = parada(&tren, "Plaza_Mayor");
	printf("Ti to ti: Plaza_Mayor - se han bajado %d pasajeros\n", pasajeros);
	mostrarPasajeros(tren);
	printf("***************************************************\n");

	pasajeros = parada(&tren, "Los_Alamos");
	printf("Ti to ti: Los_Alamos - se han bajado %d pasajeros\n", pasajeros);
	mostrarPasajeros(tren);
	printf("***************************************************\n");

	pasajeros = parada(&tren, "Torremolinos");
    printf("Ti to ti: Torremolinos - se han bajado %d pasajeros\n", pasajeros);
    mostrarPasajeros(tren);
    printf("***************************************************\n");

    //Vamos a guardar los pasajero que se han quedado sin bajar antes de entrar a la cochera
    guardarBinario(tren, "registro.bin");

    pasajeros = entrarCochera(&tren);
    printf("Tren entrando a cochera - Quedan %d pasajeros\n", pasajeros);
    mostrarPasajeros(tren);
    printf("***************************************************\n");

   tren = cargarBinario("registro.bin");
    printf("Regitro de pasajeros que no bajaron antes de llegar a la cochera:\n ");
    mostrarPasajeros(tren);
    entrarCochera(&tren); //volvemos a borrar la informacion


}
