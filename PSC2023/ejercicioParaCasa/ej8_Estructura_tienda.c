/*
Vamos a crear una tienda con 100 artículos máximo, vamos a tener libros y revistas.
*/
#include <stdio.h>
#include <string.h>

struct str_Libro
{
    char titulo[200];
    char autor[200];
    int paginas;
    float precio;
};

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

struct str_Revisa_Mensual
{
    char titulo[200];
    enum Meses mes;
    float precio;
};

typedef struct contador;

union u_Item
{
    struct str_Libro libro;
    struct str_Revisa_Mensual revista;
} item;

void agregar(char *revision, union u_Item *item, int *numArticulosLibro, int *numArticulosRevista)
{

    if ((*numArticulosLibro) == 100)
    {
        printf("No se pueden añadir mas articulos.\n");
        return;
    }

    printf("Introduce el tipo de item (l para libro, r para revista): ");
    scanf("%c", revision);

    if (*revision == 'l')
    {

        // Titulo, Autor, numPaginas, precio
        printf("Introduce el titulo del libro: ");
        scanf("%s", item->libro.titulo);
        printf("Introduce el Autor del libro: ");
        scanf("%s", item->libro.autor);
        printf("Introduce el numero de paginas del libro: ");
        scanf("%d", &item->libro.paginas);
        printf("Introduce el precio del libro: ");
        scanf("%f", &item->libro.precio);

        (*numArticulosLibro)++;
    }
    else if (*revision == 'r')
    {
        // Titulo, mes, precio
        printf("Introduce el titulo del libro: ");
        scanf("%s", item->revista.titulo);
        printf("Introduce el mes del libro: ");
        scanf("%s", &item->revista.mes);
        printf("Introduce el precio del libro: ");
        scanf("%d", &item->revista.precio);

        (*numArticulosRevista)++;
    }
}

void mostrar(const union u_Item *item, const int *numArticulosLibro, const int *numArticulosRevista)
{
    for (int i = 0; i < (*numArticulosLibro); i++)
    {
        printf("Libro Nº: %d | Titulo: %s | Autor: %s | Num Paginas: %d | Precio: %2.f €\n", i, item->libro.titulo, item->libro.autor, item->libro.paginas, item->libro.precio);
    }

    for (int i = 0; i < (*numArticulosRevista); i++)
    {
        printf("Revista Nº: %d | Titulo: %s | Mes: %d | Precio: %2.f €\n", i, item->revista.titulo, item->revista.mes, item->revista.precio);
    }
}

int main()
{
    union u_Item item;
    char revision; // -> r si es revisa, l si es libro y v si no hay nada
    int numArticulosLibro = 0;
    int numArticulosRevista = 0;
    int compr;
    /*
        for (int i = 0; i < 2; i++)
        {
            agregar(&revision, &item, &numArticulosLibro, &numArticulosRevista);
        }

        mostrar(&item, &numArticulosLibro, &numArticulosRevista);
    */
   // Choice variable
    int choice = -1;
    
    // Menu display
    printf("MENU:\n1. Agregar\n2. Mostrar\n3. Exit");

   while(1){
        
        
        // If-else ladder
        if(choice == 1){
            agregar(&revision, &item, &numArticulosLibro, &numArticulosRevista);
            
        }
        else if (choice == 2){
            mostrar(&item, &numArticulosLibro, &numArticulosRevista);
        }
        else if (choice == 3){
            printf("FIN\n");
            // Termination of the Loop using break statement
            break;
        }
        else{
            printf("> Invalid Input\n");
        }
        printf("\nEnter your choice:");
        scanf("%d", &choice);
    }

    return 0;
}
