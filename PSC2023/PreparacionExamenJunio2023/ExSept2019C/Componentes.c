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
    Lista ptr ;
    ptr = lista;

    if(ptr == NULL){
        return 1;
    }

    return  0;
}

/*Num_Elementos es una funcion a la que se le pasa un puntero a una lista
y devuelve el numero de elementos de dicha lista.
*/
int Num_Elementos(Lista  lista){
    Lista ptr;
    ptr = lista;
    int contador = 0;

    while(ptr != NULL) {
        contador++;
       ptr = ptr->sig;
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
    Lista ptr;
    ptr = lista;


    while(ptr->sig != NULL) {
        printf("Producto con código %d tiene una descripcion: %s.\n", ptr->codigoComponente, ptr->textoFabricante);
        ptr = ptr->sig;
    }
}

/*
La funcion Lista_Salvar se encarga de guardar en el fichero binario
"examen.dat" la lista enlazada completa que se le pasa como parametro.
Para cada nodo de la lista, debe almacenarse en el fichero
el código y el texto de la componente correspondiente.
*/
void Lista_Salvar( Lista  lista) {
    FILE *archivo = fopen("examen.dat", "wb");

    if(archivo == NULL){
        printf("Error al abrir el fichero.\n");
        return;
    }

    Lista nodoActual = lista;
    while(nodoActual != NULL){
        fwrite(&nodoActual->codigoComponente, sizeof(long), 1, archivo);
        fwrite(&nodoActual->textoFabricante, sizeof(char), MAX_CADENA, archivo);
        nodoActual = nodoActual->sig;
    }
    printf("Archivo guardado con exito\n");
    fclose(archivo);
 }


/*
La funcion Lista_Crear crea una lista enlazada vacia
de nodos de tipo Componente.
*/
Lista Lista_Crear() {
    Lista lista = (Lista)malloc(sizeof(struct elemLista));
    if(lista != NULL){
        lista->codigoComponente = 0;
        memset(lista->textoFabricante, 0, sizeof(lista->textoFabricante));
        lista->sig = NULL;
    }
    return lista;
}

/*
La funcion Lista_Agregar toma como parametro un puntero a una lista,
el código y el texto de un componente y  anyade un nodo al final de
la lista con estos datos.
*/
void Lista_Agregar(Lista *lista, long codigo, char* textoFabricante){
    Lista ptr = *lista;
    while(ptr != NULL){
        if(ptr->codigoComponente == codigo){
            return ;
        }
        ptr = ptr->sig;
    }

    Lista nuevo = (Lista)malloc(sizeof(struct elemLista));
    if(nuevo == NULL){
        return ;
    }

    nuevo->codigoComponente = codigo;
    strcpy(nuevo->textoFabricante, textoFabricante);
    nuevo->sig = *lista;

    *lista = nuevo;

}

/*
Lista_Extraer toma como parametro un puntero a una Lista y elimina el
Componente que se encuentra en su ultima posicion.
*/
void Lista_Extraer(Lista *lista){
    Lista ptr ;
    ptr = *(lista);

    if(ptr == NULL || ptr->sig == NULL){
        return;
    }

    Lista penultimo = *(lista);

    while(penultimo->sig->sig != NULL) {
        penultimo = penultimo->sig;
    }

    Lista ultimo = penultimo->sig;
    penultimo->sig = NULL;

    free(ultimo);
}

/*
Lista_Vaciar es una funcion que toma como parametro un puntero a una Lista
y elimina todos sus Componentes.
*/
void Lista_Vaciar(Lista *lista){
    Lista ptr = lista;

    while(ptr->sig != NULL) {

        ptr = *lista;
        printf("Eliminado el producto con codigo: %d\n", ptr->codigoComponente);

        *lista = (*lista)->sig;
        free(ptr);
    }
}