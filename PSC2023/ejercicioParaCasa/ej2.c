//Ejercicio 2. Lee por pantalla tu nombre, una coma y tus 
//dos apellidos terminados en punto, e imprime con el siguiente
// formato: Apellidos, Nombre. 
    //Entrada: Joaquin, Ballesteros Gomez.
    //Salida: Ballesteros Gomez, Joaquin.


#include <stdio.h>
#include <string.h>

int main(void) {
    char nombre[100];
    char apellido1[100];
    char apellido2[100];
    char punto;

    printf("Introduce tu nombre, una coma y tus dos apellidos, terminando en punto: ");
    int resultado = scanf("%[^,], %[^ ] %[^.].", nombre, apellido1, apellido2);
    printf("%s %s, %s\n", apellido1 , apellido2, nombre);
    return 0;
}



