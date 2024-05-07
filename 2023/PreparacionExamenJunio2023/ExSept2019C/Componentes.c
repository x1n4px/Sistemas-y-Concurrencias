//
// Created by in4p on 5/23/23.
//
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include "Componentes.h"


/*
La rutina Lista_Vacia devuelve 1 si la lista que se le pasa
como parametro esta vacia y 0 si no lo esta.
*/
int Lista_Vacia(Lista lista){
    if(lista == NULL){
        return 1;
    }else{
        return 0;
    }
}

/*Num_Elementos es una funcion a la que se le pasa un puntero a una lista
y devuelve el numero de elementos de dicha lista.
*/
int Num_Elementos(Lista  lista){
    int contador = 0;

    while(lista != NULL) {
        contador++;
       lista = lista->sig;
    }

    return contador;
}

/*
La rutina Adquirir_Componente se encarga de recibir los datos de un nuevo
componente (codigo y texto) que se le introducen por teclado y devolverlos
por los parametros pasados por referencia "codigo" y "texto".
*/
void Adquirir_Componente(long *codigo,char *texto){
    printf("Ingrese el código del componente: ");
    scanf("%ld", codigo);

    printf("Ingrese el texto del fabricante: ");
    scanf("%s", texto);
}

/*
La funcion Lista_Imprimir se encarga de imprimir por pantalla la lista
enlazada completa que se le pasa como parametro.
*/
void Lista_Imprimir( Lista lista){

    if(Lista_Vacia(lista)) {
        printf("Lista vacia\n");
    }else{
        printf("-----------------LISTA DE COMPONENTES-------------------\n");
        while(lista != NULL) {
            printf("Producto %d tiene una descripcion: %s.\n", lista->codigoComponente,  lista->textoFabricante);
           lista = lista->sig;
        }
    }
    printf("\n");
}

/*
La funcion Lista_Salvar se encarga de guardar en el fichero binario
"examen.dat" la lista enlazada completa que se le pasa como parametro.
Para cada nodo de la lista, debe almacenarse en el fichero
el código y el texto de la componente correspondiente.
*/
void Lista_Salvar( Lista  lista) {
    FILE *archivo = fopen("examen.dat", "wb");
    int longitud;

    if(archivo == NULL){
        printf("Error al abrir el fichero.\n");
        return;
    }else{
         while(lista != NULL){
             longitud = strlen(lista->textoFabricante);
            fwrite(&(lista->codigoComponente), sizeof(long), 1, archivo);
             fwrite(&longitud, sizeof(int), 1, ptr);
            fwrite(&(lista->textoFabricante), sizeof(char), longitud, archivo);
             lista = lista->sig;
        }
        printf("Archivo guardado con exito\n");
        fclose(archivo);
    }


 }


/*
La funcion Lista_Crear crea una lista enlazada vacia
de nodos de tipo Componente.
*/
Lista Lista_Crear() {
    Lista lista = NULL;
    return lista;
}

/*
La funcion Lista_Agregar toma como parametro un puntero a una lista,
el código y el texto de un componente y  anyade un nodo al final de
la lista con estos datos.
*/
void Lista_Agregar(Lista *lista, long codigo, char* textoFabricante){
    Lista ptr = *lista;
    Lista nuevo = malloc(sizeof(struct elemLista));

    if(nuevo == NULL){
        perror("ERROR: No se pudo reservar memoria");
    }else{
        nuevo->codigoComponente = codigo;
        strcpy(nuevo->textoFabricante, textoFabricante);
        nuevo->sig = NULL;
        if(*lista == NULL){
            *lista = nuevo;
        }else{
            while(ptr->sig != NULL){
                ptr = ptr->sig;
            }
            ptr->sig = nuevo;
        }

    }


}

/*
Lista_Extraer toma como parametro un puntero a una Lista y elimina el
Componente que se encuentra en su ultima posicion.
*/
void Lista_Extraer(Lista *lista){
    Lista ptr = *lista;
    Lista ant = NULL;

    if(*lista != NULL){
        while(ptr->sig != NULL){
            ant = ptr;
            ptr = ptr->sig;
        }
        ant->sig = NULL;
        free(ptr);
    }
}

/*
Lista_Vaciar es una funcion que toma como parametro un puntero a una Lista
y elimina todos sus Componentes.
*/
void Lista_Vaciar(Lista *lista){
    Lista ptr;

    while(*lista != NULL) {
        ptr = *lista;
        printf("Eliminado el producto con codigo: %d\n", ptr->codigoComponente);
        *lista = (*lista)->sig;
        free(ptr);

    }
}