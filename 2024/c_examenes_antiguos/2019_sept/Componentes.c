#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "componentes.h"

int Lista_Vacia(Lista lista)
{
    if (lista == NULL)
    {
        return 1;
    }
    else
    {
        return 0;
    }
}

int Num_Elementos(Lista lista)
{
    int contador = 0;

    while (lista != NULL)
    {
        contador++;
        lista = lista->sig;
    }

    return contador;
}

Lista Lista_Crear()
{
    return NULL;
}

void Adquirir_Componente(long *codigo, char *texto)
{
    printf("Introduzca el codigo del componente: ");
    scanf("%ld", codigo);
    printf("Introduzca el texto del fabricante: ");
    scanf("%s", texto);
}

void Lista_Imprimir(Lista lista)
{
    printf("Lista de componentes: \n");
    Lista aux = lista;
    while(aux != NULL) {
        printf("Codigo: %ld |  Texto: %s \n", aux->codigoComponente, aux->textoFabricante);
        aux = aux->sig;
    }
}


void Lista_Salvar( Lista  lista){
    FILE *file = fopen("examen.dat", "wb");
    if(file == NULL){
        printf("Error al abrir el fichero\n");
        return;
    }

    fprintf(file, "codigo;texto;\n");
    Lista aux = lista;
    while(aux != NULL){
        fprintf(file, "%ld;%s;\n", aux->codigoComponente, aux->textoFabricante);
        aux = aux->sig;
    }
    fclose(file);
}

void Lista_Agregar(Lista *lista, long codigo, char* textoFabricante){
    Lista nuevo = (Lista)malloc(sizeof(Componente));
    nuevo->codigoComponente = codigo;
    strcpy(nuevo->textoFabricante, textoFabricante);
    nuevo->sig = NULL;

    if(*lista == NULL){
        *lista = nuevo;
    } else {
        Lista aux = *lista;
        while(aux->sig != NULL){
            aux = aux->sig;
        }
        aux->sig = nuevo;
    }
}

void Lista_Extraer(Lista *lista){
    Lista ptr = *lista;

    while(ptr->sig->sig != NULL) {
        ptr = ptr->sig;
    } 
    ptr->sig = NULL;
}

void Lista_Vaciar(Lista *lista){
    Lista aux = *lista;
    Lista aux2;

    while(aux != NULL){
        aux2 = aux;
        aux = aux->sig;
        free(aux2);
    }
    *lista = NULL;
}