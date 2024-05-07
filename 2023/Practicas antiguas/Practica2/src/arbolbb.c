#include <stdio.h>
#include <stdlib.h>
#include "arbolbb.h"

// Crea la estructura utilizada para gestionar la memoria disponible.
	void Crear(T_Arbol* arbol){
		(*arbol) = NULL;
	}

// Destruye la estructura utilizada.
	void Destruir(T_Arbol* arbol_ptr){
		if((*arbol_ptr)!=NULL){ //arbol no vacío
			Destruir(&(*arbol_ptr)->izq);
			Destruir(&(*arbol_ptr)->der);
			free(*arbol_ptr);
			*arbol_ptr = NULL;
		}
	}

	// Inserta num en el arbol
	void Insertar(T_Arbol* arbol_ptr,unsigned num){
		if((*arbol_ptr)==NULL){
			*arbol_ptr = (T_Arbol)malloc(sizeof(struct T_Nodo));
			(*arbol_ptr)->dato = num;
			(*arbol_ptr)->izq = NULL;
			(*arbol_ptr)->der = NULL;
		}else{
			if((*arbol_ptr)->dato < num){
				Insertar(&(*arbol_ptr)->der,num);
			}else if((*arbol_ptr)->dato > num){
				Insertar(&(*arbol_ptr)->izq, num);
			}
		}
	}
	// Muestra el contenido del árbol en InOrden
	void Mostrar(T_Arbol arbol){
		if(arbol!=NULL){
			Mostrar(arbol->izq);
			printf("%d\t",arbol->dato);
			Mostrar(arbol->der);

		}
		printf("\n");

	}
	// Guarda en disco el contenido del arbol
	void Salvar(T_Arbol arbol, FILE* fichero){
		if(arbol!=NULL){
			Salvar(arbol->izq,fichero);
			fwrite(&(arbol->dato),sizeof(int),1,fichero);
			Salvar(arbol->der,fichero);
		}


	}
