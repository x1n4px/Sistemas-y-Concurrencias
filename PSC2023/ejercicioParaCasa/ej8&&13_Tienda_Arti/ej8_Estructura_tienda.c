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
        printf("\n");
        (*numArticulosLibro)++;
    }
    else if (*revision == 'r')
    {
        // Titulo, mes, precio
        printf("Introduce el titulo del libro: ");
        scanf("%s", item->revista.titulo);
        printf("Introduce el mes del libro: ");
        scanf("%i", &(item->revista.mes));
        printf("Introduce el precio del libro: ");
        scanf("%f", &item->revista.precio);
        printf("\n");
        (*numArticulosRevista)++;
    }
}

void mostrar(const union u_Item *item, const int *numArticulosLibro, const int *numArticulosRevista)
{
    enum Meses mes;
    for (int i = 0; i < (*numArticulosLibro); i++)
    {
        printf("Libro Nº: %d | Titulo: %s | Autor: %s | Num Paginas: %d | Precio: %2.f €\n", i, item->libro.titulo, item->libro.autor, item->libro.paginas, item->libro.precio);
    }

    for (int i = 0; i < (*numArticulosRevista); i++)
    {

        printf("Revista Nº: %d\n", i);
        printf("Titulo: %s\n", item->revista.titulo);
        switch ((item->revista.mes))
        {
        case 0:
            printf("Mes: Enero (%d)\n", item->revista.mes);
            break;
        case 1:
            printf("Mes: Febrero (%d)\n", item->revista.mes);
            break;
        case 2:
            printf("Mes: Marzo (%d)\n", item->revista.mes);
            break;
        case 3:
            printf("Mes: Abril (%d)\n", item->revista.mes);
            break;
        case 4:
            printf("Mes: Mayo (%d)\n", item->revista.mes);
            break;
        case 5:
            printf("Mes: Junio (%d)\n", item->revista.mes);
            break;
        case 6:
            printf("Mes: Julio (%d)\n", item->revista.mes);
            break;
        case 7:
            printf("Mes: Agosto (%d)\n", item->revista.mes);
            break;
        case 8:
            printf("Mes: Septiembre (%d)\n", item->revista.mes);
            break;
        case 9:
            printf("Mes: Octubre (%d)\n", item->revista.mes);
            break;
        case 10:
            printf("Mes: Noviembre (%d)\n", item->revista.mes);
            break;
        case 11:
            printf("Mes: Diciembre (%d)\n", item->revista.mes);
            break;
        default:
            break;
        }
        printf("Precio: %2.f €\n", item->revista.precio);
    }
}

int main()
{
    union u_Item item;
    char revision; // -> r si es revisa, l si es libro y v si no hay nada
    int numArticulosLibro = 0;
    int numArticulosRevista = 0;
    int compr;

    int veces = 0;

    printf("Cuantos articulos quieres añadir?: ");
    scanf("%d", &veces);

    for (int i = 0; i <= veces; i++)
    {
        agregar(&revision, &item, &numArticulosLibro, &numArticulosRevista);
        
    }

    mostrar(&item, &numArticulosLibro, &numArticulosRevista);

    return 0;
}
