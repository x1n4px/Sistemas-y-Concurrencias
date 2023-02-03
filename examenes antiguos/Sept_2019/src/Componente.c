#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "componentes.h"

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

/*Num_Elementos es una funcion a la que se le
pasa un puntero a una lista y devuelve el
numero de elementos de dicha lista.
*/
int Num_Elementos(Lista  lista){
	int cont = 0;
	while(lista != NULL){
		lista = lista->siguiente;
		cont++;
	}
	return cont;
}

/*
La rutina Adquirir_Componente se encarga de
recibir los datos de un nuevo componente (codigo y texto) que se le
introducen por teclado y devolverlos por los parametros pasados
por referencia "codigo" y "texto".
*/
 void Adquirir_Componente(long *codigo,char *texto){
	 printf("Introduzca el codigo del componente:\n");
	 scanf("%ld",codigo);
	 printf("Introduzca el texto del fabricante (maximo 32 caracteres y sin espacios en blanco):\n");
	 scanf("%s",texto);
 }

/*
La funcion Lista_Imprimir se encarga de
imprimir la lista enlazada completa que
se le pasa como parametro.
*/
void Lista_Imprimir( Lista lista){
	if(Lista_Vacia(lista)){
		printf("No hay componentes en la lista\n");

	}else{
		printf("----------LISTA DE COMPONENTES---------\n");
		while(lista!=NULL){
			printf("Componente %ld: %s\n",lista->codigoComponente);
			lista->textoFabricante;
			lista = lista->siguiente;
		}
	}
	printf("\n");

}

/*
La funcion Lista_Salvar se encarga de
guardar en el fichero "examen.dat" la
lista enlazada completa que se le pasa
como parametro. Para cada nodo de la lista, debe almacenarse en el fichero
el código y el texto de la componente correspondiente.
*/
void Lista_Salvar( Lista  lista){
	FILE *ptr = fopen("examen.dat","wb");
	int longitud;

	if(ptr==NULL){
		perror("ERROR:no se pudo abrir el fichero");

	}else{
		while(lista!=NULL){
			longitud = strlen(lista->textoFabricante);
			fwrite(&(lista->codigoComponente),sizeof(long),1,ptr);
			fwrite(&longitud,sizeof(int),1,ptr);
			fwrite(lista->textoFabricante,sizeof(char),longitud,ptr);
			lista = lista->siguiente;
		}
		fclose(ptr);
	}
}


/*
La funcion Lista_Crear crea una lista enlazada vacia
de nodos de tipo Componente.
*/
Lista Lista_Crear(){
	Lista lista = NULL;
	return lista;
}

/*
La funcion Lista_Agregar toma como parametro un puntero a una lista,
el código y el texto de un componente y  anyade un nodo al final de la lista con
estos datos.
*/
void Lista_Agregar(Lista *lista, long codigo, char* textoFabricante){
	Lista new = malloc(sizeof(struct elemLista));
	Lista ptr = *lista;

	if(new == NULL){
		perror("ERROR:no se pudo reservar memoria");
	}else{
		new->codigoComponente = codigo;
		strcpy(new->textoFabricante,textoFabricante);
		new->siguiente = NULL;
		if(*lista = NULL){
			*lista = new;
		}else{
			while(ptr->siguiente!=NULL){
				ptr = ptr->siguiente;
			}
			ptr->siguiente = new;
		}
	}


}

/*
Lista_Extraer toma como parametro un
puntero a una Lista y elimina el
Componente que se encuentra en
su ultima posicion.
*/
void Lista_Extraer(Lista *lista){
	Lista ptr = *lista;
	Lista ant = NULL;
	if(*lista != NULL){
		while(ptr->siguiente!=NULL){
			ant = ptr;
			ptr = ptr->siguiente;
		}
		ant->siguiente = NULL;
		free(ptr);
	}
}

/*
Lista_Vaciar es una funcion que toma como
parametro un puntero a una Lista
y elimina todos sus Componentes.
*/
void Lista_Vaciar(Lista *lista){
	Lista ptr;
	while(*lista != NULL){
		ptr = *lista;
		*lista = (*lista)->siguiente;
		free(ptr);
	}
}
