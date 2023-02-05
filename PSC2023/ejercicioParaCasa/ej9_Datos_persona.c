/*
Genera una estructura str_persona para almacenar los datos de una persona (edad, primer apellido, segundo apellido). 
Haz el sigueinte procedimeinto:

void descendiente(struct str_persona p1, struct str_persona p2, struct str_persona* des);


Que cree descendientes, que dado dos personas p1 y p2, devuelva una tercera, des, que tenga como primer
 apellido el apellido primero de p1, y como segundo el primero de p2 (y de edad 0).
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
    printf("Introduce edad: ");
    scanf("%d", &persona->edad);
    printf("Introduce el primer apellido: ");
    scanf("%s", persona->primerApellido);
    printf("Introduce el segundo apellido: ");
    scanf("%s", persona->segundoApellido);
    printf("\n");
}

void descendiente(const struct str_Persona *p1, const struct str_Persona *p2, struct str_Persona* des){
    printf("edad: %d | Primer apellido: %s | Segundo apellido: %s", 0, p1->primerApellido, p2->segundoApellido);
}


int main(){
    struct str_Persona persona1, persona2, persona3;

    completarDatos(&persona1);
    completarDatos(&persona2);
    descendiente(&persona1, &persona2, &persona3);



    return 0;
}