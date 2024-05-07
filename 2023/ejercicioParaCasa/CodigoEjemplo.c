#include <stdio.h>
#include <string.h>

enum Meses
{
    ENE = 20,
    FEB,
    MAR,
    ABR = 20,
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


int main()
{
    enum Meses mes;
    printf("%d", mes);

}
