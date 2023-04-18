#include <stdlib.h>
#include <stdio.h>
#include "colaprio.h"

void Insertar(int id, int prio, TColaPrio *cp){
	TColaPrio new = malloc(sizeof(struct TProc));
	TColaPrio ptr,ant;



	if(new == NULL){
		perror("ERROR: No se pudo reservar memoria");
	}else{
		new->id = id;
		new->prio = prio;
		if(*cp == NULL){
			new->sig = NULL;
			*cp = new;
		}else{
			ptr = *cp;
			ant = NULL;
			while(ptr != NULL && prio <= ptr->prio){
				ant = ptr;
				ptr = ptr->sig;
			}
			if(ant == NULL){
				new->sig = *cp;
				*cp = new;

			}else{
				new->sig = ant->sig;
				ant->sig = new;
			}
		}

	}


}



void Crear_Cola(char *nombre, TColaPrio *cp){
	FILE *f = fopen(nombre, "rb");
	int n,i,id,prio;
	*cp = NULL;
	if(f==NULL){
		perror("ERROR: no se pudo abrir el ficheor");
		exit(-1);
	}else{
		fread(&n,sizeof(int),1,f);

		for (i = 0; i < n; ++i) {
			fread(&id,sizeof(int),1,f);
			fread(&prio,sizeof(int),1,f);
			Insertar(id,prio,cp);
		}
		fclose(f);
	}

}


void Mostrar(TColaPrio cp){
	if(cp == NULL){
		printf("No hay procesos en la lista\n");
	}else{
		while(cp!=NULL){
			printf("%d:%d\n",cp->id,cp->prio);
			cp = cp->sig;
		}
	}


}




void Destruir(TColaPrio *cp){
	TColaPrio ptr;

	while(*cp != NULL){
		ptr = *cp;
		*cp = ptr->sig;
		free(ptr);
	}

}



void Ejecutar_Max_Prio(TColaPrio *cp){
	TColaPrio ptr;
	int prio = (*cp)->prio;

	while((*cp)!=NULL && (*cp)->prio == prio){
		ptr = *cp;
		*cp = (*cp)->sig;
		free(ptr);
	}


}
void Ejecutar(TColaPrio *cp, int prio){
	TColaPrio ptr = *cp,ant=NULL;

	while(ptr != NULL && prio <= ptr->prio){
		if(ptr->prio == prio){
			if(ant==NULL){
				*cp = ptr->sig;
				free(ptr);
				ptr = *cp;
			}else{
				ant->sig = ptr->sig;
				free(ptr);
				ptr = ant->sig;
			}
		}else{
			ant = ptr; 
			ptr = ptr->sig;
		}
	}
}
