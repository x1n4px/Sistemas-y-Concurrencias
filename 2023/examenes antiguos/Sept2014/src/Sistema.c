#include<stdlib.h>
#include<stdio.h>
#include "Sistema.h"

//Crea una lista vacia
void Crear(LSistema *l){
	*l = NULL;
}
//Inserta un proceso por orden de llegada.
void InsertarProceso ( LSistema *ls, int numproc){
	LSistema ptr = *ls;
	LSistema new = malloc(sizeof(struct TProc));

	if(new == NULL){
		perror("ERROR: no se pudo reservar memoria");
		exit(-1);
	}else{
		new->num = numproc;
		new->hebra = NULL;
		new->sig = NULL;
		if(*ls = NULL){
			*ls = new;
		}else{
			while(ptr->sig != NULL){
				ptr = ptr->sig;
			}
			ptr->sig = new;
		}
	}
}
//Inserta una hebra en el proceso numproc teniendo en cuenta el orden de prioridad (mayor a menor)
void InsertarHebra (LSistema *ls, int numproc, char *idhebra, int priohebra){
	LSistema ptr = *ls;
	Hebra new = malloc(sizeof(struct THebra));
	Hebra ant, prox;

	if(new == NULL){
		perror("No se ha podido reservar memoria");
		exit(-1);

	}else{
		while(ptr->num != numproc){
			ptr = ptr->sig;
		}
		if(!ptr){
			perror("Error.El proceso no existe");
		}else{
			strcpy(new->id,idhebra);
			new->pri = priohebra;

			if(ptr->hebra == NULL){
				new->sig = NULL;
				ptr->hebra = new;
			}else{
				ant = NULL;
				prox = ptr->hebra;
				while(prox != NULL && priohebra < prox->pri){
					ant = prox;
					prox = prox->sig;
				}
				if(ant == NULL){
					new->sig = prox;
					ptr->hebra = new;
				}else if(ptr == NULL){
					new->sig = NULL;
					ant->sig = new;
				}else{
					ant->sig = new;
					new->sig = prox;
				}
			}

		}
	}



}
//Muestra el contenido del sistema
void Mostrar (LSistema ls){
	Hebra h;
	if(ls == NULL){
		printf("\nNo hay procesos en el sistema");
	}else{
		while(ls != NULL){
			h = ls->hebra;
			printf("\nProceso %i:",ls->num);
			while(h !=NULL){
				printf("[Hebra %s, Prioridad %i]",h->id,h->pri);
				if(h->sig){
					printf("->");
				}
				h = h->sig;
			}
			ls = ls->sig;
		}
	}

}
//Elimina del sistema el proceso con número numproc liberando la memoria de éste y de sus hebras.
void EliminarProc (LSistema *ls, int numproc){
	LSistema ant = NULL, ptr = *ls;
	Hebra h;

	while(ptr->num != numproc){
		ant = ptr;
		ptr = ptr->sig;
	}
	if(ptr == NULL){
		perror("EL proceso no existe");
	}else{
		if(ptr == *ls){
			*ls = ptr->sig;
		}else{
			ant->sig = ptr->sig;
		}
		while(ptr->hebra != NULL){
			h = ptr->hebra;
			ptr->hebra = ptr->hebra->sig;
			free(h);
		}
		free(ptr);
	}







}
//Destruye toda la estructura liberando su memoria
void Destruir (LSistema *ls){
	LSistema ptr;
	while(*ls != NULL){
		ptr = *ls;
		*ls = (*ls)->sig;
		EliminarProc(&ptr, ptr->num);
	}


}
