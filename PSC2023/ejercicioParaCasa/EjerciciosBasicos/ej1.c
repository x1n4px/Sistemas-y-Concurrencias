//Ejercicio 1. Lee por pantalla tu nombre y apellidos, terminando en punto (scanf) e imprimelos por pantalla añadiendo lo siguiente:
//Entrada: Joaquin Ballesteros Gomez.
//Salida: Hola Joaquin, tus apellidos son Ballesteros Gomez


#include <stdio.h>
#include <string.h>

int main(void) {
    char nombre[100];
    char apellido1[100];
    char apellido2[100];

    printf("Introduce tu nombre y apellidos, terminando en punto: ");
    scanf("%s %s %[^.].", nombre, apellido1, apellido2);


    printf("Hola %s, tus apellidos son %s %s\n", nombre, apellido1, apellido2);
    return 0;
}
