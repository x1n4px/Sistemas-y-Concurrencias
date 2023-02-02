//Ejercicio 3. Lee por pantalla tu edad (en numero entero), el mes en numero en el que naciste, separado por punto y coma. 
//Tras eso, imprime por pantalla "Naciste el mes X, y tienes Y anyos."
//Entrada: 39;12
//Salida: Naciste el mes 12, y tienes 39 años.

#include <stdio.h>

int main(void) {
    int edad, mes;

    printf("Introduce dos números separados por ;: ");
    scanf("%d;%d", &edad, &mes);
    printf("Naciste el mes %d, y tienes %d años.\n", mes, edad);

    return 0;
}
