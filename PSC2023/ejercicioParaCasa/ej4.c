 //Ejercicio 4. Lee por pantalla tu edad (en numero entero), el mes en numero en el que naciste, separado por punto y coma. 
 //Tras eso, ALMACENA en un array de carácteres "Naciste el mes X, y tienes Y anyos.". Usa el modo debug para ver que es 
 //correcto, ¡no uses printf trampos@!

// gcc -g ej4.c -o file
//gbd file

#include <stdio.h>

int main(void) {
    int edad, mes;

   
    scanf("%d;%d", &edad, &mes);
    printf("Naciste el mes %d, y tienes %d años.\n", mes, edad);

    return 0;
}


