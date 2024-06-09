#include <stdio.h>
#include <stdlib.h>
#include "Mprocesos.h"

void Crear(LProc *lroundrobin)
{
    *lroundrobin = NULL;
}

void AnadirProceso(LProc *lroundrobin, int idproc)
{
    LProc nuevo = (LProc)malloc(sizeof(struct T_Nodo));
    nuevo->pid = idproc;
    nuevo->sig = NULL;

    if (*lroundrobin == NULL)
    {
        *lroundrobin = nuevo;
        nuevo->sig = *lroundrobin;
    }
    else
    {
        nuevo->sig = (*lroundrobin)->sig;
        (*lroundrobin)->sig = nuevo;
        *lroundrobin = nuevo;
    }
}

void EjecutarProcesos(LProc lroundrobin)
{
    LProc ptr;
    if (lroundrobin == NULL)
    {
        printf("No hay procesos en la lista\n");
    }
    else
    {
        ptr = lroundrobin->sig;
        printf("Procesos en la lista: ");
        do
        {
            printf("%d ", ptr->pid);
            ptr = ptr->sig;
        } while (ptr != lroundrobin->sig);
        printf("\n");
    }
}

void EliminarProceso(int id, LProc *lista)
{
    if (*lista != NULL)
    {
        LProc ptr = (*lista)->sig;
        LProc ant = *lista;

        while (ptr->pid != id)
        {
            ant = ptr;
            ptr = ptr->sig;
        }

        if (ant == ptr)
        {
            *lista = NULL;
            free(ptr);
        }
        else
        {
            ant->sig = ptr->sig;
            if (ptr == *lista)
            {
                *lista = ant;
            }

            free(ptr);
        }
    }
}

void EscribirFichero(char *nomf, LProc *lista)
{
    FILE *archivo = fopen(nomf, "w");
    if (archivo == NULL)
    {
        printf("Error al abrir el archivo\n");
        exit(1);
    }
    else
    {
        LProc ptr = (*lista)->sig;
        while(*lista != NULL) {
            if(ptr != NULL && ptr != *lista) {
                fprintf(archivo, "%d\n", ptr->pid);
                (*lista)->sig = ptr->sig;
                free(ptr);
                ptr = (*lista)->sig;
            } else {
                fprintf(archivo, "%d\n", ptr->pid);
                free(ptr);
                *lista = NULL;
            }
        }
        fclose(archivo);
    }
}