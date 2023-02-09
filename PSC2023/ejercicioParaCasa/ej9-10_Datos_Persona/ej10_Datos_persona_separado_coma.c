/*
Usa la estructura str_persona para almacenar los datos de una persona (edad, primer apellido, segundo apellido). Haz un programa
 interactivo que permita leer de teclado los datos de una persona separados por coma y los introduzca en la estructura: edad; primer apellido;segundo apellido;
Prueba con las siguientes entradas:
55; Ballesteros; Gomez;
20; Perez; De la Cruz;
BONUS: intenta hacerlo sin necesidad de variables auxiliares para almacenar los datos de la persona.
*/


#include <stdio.h>
#include <string.h>

struct str_Persona
{
    int edad;
    char primerApellido[100];
    char segundoApellido[100];
};



void completarDatos(struct str_Persona *persona){
   
    printf("Introduce edad, primer apellido y segundo apellido (separado por ;): ");
    scanf("%d; %[^;]; %[^;];", &persona->edad, persona->primerApellido, persona->segundoApellido);
   
}

void mostrarDatos(const struct str_Persona *persona){
    printf("Edad: %d | Primer apellido: %s | Segundo Apellido: %s \n", persona->edad, persona->primerApellido, persona->segundoApellido);
}



int main(){
    struct str_Persona persona1 = {.primerApellido=0};
    
    completarDatos(&persona1);
    mostrarDatos(&persona1);    


    return 0;
}