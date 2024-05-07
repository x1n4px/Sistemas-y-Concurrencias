/*
- Se proporciona el código con tres items rellenos en la lista enlazada, y
 un cuarto, a insertar. ¿Cómo se puede insertar ese cuarto elemento al principio de la lista?

2.-Comenta el código anterior y prueba ahora, a insertarlo al final, pero hazlo bien, usando un bucle,
 asumiendo que el primer elemento no es nulo, pero sin saber la longitud de la lista enlazada
*/

#include <stdio.h>
#include <string.h>
#include <stdlib.h>

enum Meses
{
    ENE,
    FEB,
    MAR,
    ABR,
    MAY,
    JUN,
    JUL,
    AGO,
    SEP,
    OCT,
    NOV,
    DIC
};

typedef struct str_Revisa_Mensual *ptrRevista;

struct str_Revisa_Mensual
{
    char titulo[200];
    enum Meses mes;
    int anyo;
    float precio;
    ptrRevista siguiente;
};

int main(int argc, char const *argv[])
{
    ptrRevista lista = malloc(sizeof(struct str_Revisa_Mensual));
    strcpy(lista->titulo, "Hola");
    lista->mes = ENE;
    lista->anyo = 2023;
    lista->precio = 4.5;
    lista->siguiente = NULL;

    lista->siguiente = malloc(sizeof(struct str_Revisa_Mensual));
    strcpy(lista->siguiente->titulo, "Todo coches");
    lista->siguiente->mes = MAR;
    lista->siguiente->anyo = 2023;
    lista->siguiente->precio = 20.5;
    lista->siguiente->siguiente = NULL;

    lista->siguiente->siguiente = malloc(sizeof(struct str_Revisa_Mensual));
    strcpy(lista->siguiente->siguiente->titulo, "My little pony");
    lista->siguiente->siguiente->mes = FEB;
    lista->siguiente->siguiente->anyo = 2023;
    lista->siguiente->siguiente->precio = 13.5;
    lista->siguiente->siguiente->siguiente = NULL;

    ptrRevista aInsertar = malloc(sizeof(struct str_Revisa_Mensual));
    strcpy(aInsertar->titulo, "Jedi training");
    aInsertar->mes = JUN;
    aInsertar->anyo = 2023;
    aInsertar->precio = 8.5;
    aInsertar->siguiente = NULL;

    aInsertar->siguiente = lista;
    lista = aInsertar;


    while (lista != NULL)
    {
        printf("Titulo: %s\n", lista->titulo);
        printf("Mes: %d\n", lista->mes);
        printf("Año: %d\n", lista->anyo);
        printf("Precio: %f\n", lista->precio);
        printf("\n");
        lista = lista->siguiente;
    }

    return 0;
}