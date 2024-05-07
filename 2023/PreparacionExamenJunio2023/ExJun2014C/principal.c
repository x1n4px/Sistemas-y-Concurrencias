#include <stdio.h>
#include <stdlib.h>
#include "colaprio.h"

#define FICHERO "/home/in4p/git/Sistemas-y-Concurrencias/PSC2023/PreparacionExamenJunio2023/ExJun2014C/cola.bin"

int main() {
  TColaPrio cola;
  
  Crear_Cola(FICHERO, &cola);
    printf("Mostrar\n");
  Mostrar(cola);
  printf("\n");
    printf("Ejecutar con prio = 2 \n");
  Ejecutar(&cola, 2);
   Mostrar(cola);
  printf("\n");
    printf("Ejecutar max prio\n");
  Ejecutar_Max_Prio(&cola);
  Mostrar(cola);
  printf("\n");
    printf("Destruir\n");
  Destruir(&cola);
  Mostrar(cola);
  printf("\n");
  return 0;
}