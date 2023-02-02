/*
Vamos a hacer un multiplicador de números float.

El usuario va a introducir dos números y se le va a dar el resultado de la multiplicación.

Funciones útiles:
int scanf ( const char * format, ... );
https://www.cplusplus.com/reference/cstdio/scanf/?kw=scanf

¿Por qué puedes dar retornos de carro sin salir del while?
¿Qué ocurre cuando se introducen 3 numeros?¿Y se llevan separador de punto para decimales?¿Y de coma?

*/
#include <stdio.h>

int main(int argc, char const *argv[])
{
    float a, b;
    printf("Introduce dos float separados por un espacio, luego un retorno de carro\n");

    while (scanf("") == 2)
        printf("Resultado: %f\n", a * b);

    perror("Se esperaban dos float separados por un espacio y no se han encontrado\n");

    return 0;
}
