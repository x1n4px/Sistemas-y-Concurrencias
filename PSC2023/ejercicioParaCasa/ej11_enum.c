

#include <stdio.h>
#include <string.h>




int main(){
   enum dias_semana {LUNES, MARTES, MIERCOLES, JUEVES, VIERNES, SABADO, DOMINGO};

enum dias_semana dia_actual = MIERCOLES;

const char *nombres_dias[] = {"Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado", "Domingo"};

printf("Hoy es %s\n", nombres_dias[dia_actual]);



    return 0;
}