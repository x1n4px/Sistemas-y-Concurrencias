//Ejercicio 5. Lee por pantalla tu nombre, una coma, espacio y tus dos apellidos separados por espacio 
//terminados en punto y calcula el número de carácteres de tu nombre completo.
    //Entrada: Joaquin, Ballesteros Gomez.
    //Salida: Tu nombre completo tiene 23 carácteres.

#include <stdio.h>

int main(void) {
    char nombre[100];
    char apellido1[100];
    char apellido2[100];

    printf("Introduce tu nombre y apellidos, terminando en punto: ");
    scanf("%s %s %[^.].", nombre, apellido1, apellido2);

    int longitud = strlen(nombre) + strlen(apellido1) + strlen(apellido2) -1;


    printf("Tu nombre completo tiene %d carácteres\n", longitud);
    return 0;
}