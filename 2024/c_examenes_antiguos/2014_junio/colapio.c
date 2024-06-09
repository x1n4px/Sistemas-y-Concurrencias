#include <stdio.h>
#include <stdlib.h>
#include "colaprio.h"

void insertar(TColaPrio *cp, unsigned int id, unsigned int prio)
{
    TColaPrio nuevo = malloc(sizeof(struct Nodo));
    nuevo->idproceso = id;
    nuevo->prioridad = prio;

    if (*cp = NULL)
    {
        nuevo->sig = NULL;
        *cp = nuevo;
    }
    else
    {
        TColaPrio ant = *cp;
        if(prio < ant->prioridad) {
            nuevo->sig = ant;
            *cp = nuevo;
        }else{
            while(ant->sig != NULL && ant->sig->prioridad < prio) {
                ant = ant->sig;
            }

            if(ant->sig != NULL) {
                nuevo->sig = ant->sig;
                ant->sig = nuevo;
            }else{
                ant = ant->sig;

                if(ant->sig != NULL) {
                    while(ant->sig->prioridad == prio && ant->sig != NULL) {
                        ant = ant->sig;
                    }
                    nuevo->sig = ant->sig;
                    ant->sig = nuevo;
                }
            }
        }
    }
}

void Crear_Cola(char *nombre, TColaPrio *cp)
{
    FILE *archivo = fopen(nombre, "r");

    if (archivo == NULL)
    {
        printf("Error al abrir el archivo\n");
        exit(1);
    }
    else
    {
        *cp = NULL;
        int num, i = 0, id, prio;

        fread(&num, sizeof(unsigned), 1, archivo);
        while (i < num)
        {
            fread(&id, sizeof(unsigned), 1, archivo);
            fread(&prio, sizeof(unsigned), 1, archivo);
            insertar(cp, id, prio);
            i++;
        }
        fclose(archivo);
    }
}

void Mostrar(TColaPrio cp)
{
    if(cp == NULL) {
        printf("Cola vacía\n");
    }else{
        while(cp != NULL) {
            printf("ID: %d, Prioridad: %d\n", cp->idproceso, cp->prioridad);
            cp = cp->sig;
        }
    }
}

void Destruir(TColaPrio *cp)
{
    if(*cp != NULL) {
        TColaPrio aux;
        while(*cp != NULL) {
            aux = *cp;
            *cp = (*cp)->sig;
            free(aux);
        }
    
    }
}

void Ejecutar_Max_Prio(TColaPrio *cp)
{
    int max = 0;

    if(*cp != NULL) {
        TColaPrio ant = *cp;
        TColaPrio ptr = (*cp)->sig;

        while(*cp != NULL) {
            if((*cp)->prioridad > max) {
                max = (*cp)->prioridad;
            }
            *cp = (*cp)->sig;
        }
        *cp = ant;

        while(ptr != NULL && ptr->prioridad < max) {
            ant = ptr;
            ptr = ptr->sig;
        
        }

        if(ptr != NULL) {
            while(ptr != NULL) {
                ant->sig = ptr->sig;
                free(ptr);
                ptr = ant->sig;
            }
        }
    }
}

void Ejecutar(TColaPrio *cp, int prio)
{
    if(*cp != NULL) {
        TColaPrio ant = *cp;
        TColaPrio ptr = (*cp)->sig;

        while(ptr != NULL && ptr->prioridad != prio) {
            ant = ant->sig;
            ptr = ptr->sig;
        }

        if(ptr != NULL) {
            ant->sig = ptr->sig;
            free(ptr);
            ptr = ant->sig;
        }
    }
}
