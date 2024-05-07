#include <stdlib.h>
#include <stdio.h>
#include "cifrado.h"



/* (0.5 puntos) funcion necesaria para crear un esquema de cifrado vacio, sin nodos*/
void crearEsquemaDeCifrado (TCifrado *cf){
	cf = NULL;
}

/* (3 puntos) funcion que pone un nodo al final de un esquema de cifrado, si es posible.
 * Se debe devolver en el ultimo parametro un valor logico que sea verdadero si ha sido posible
 * realizar la operacion. No se debe suponer que el valor de box.sig es valido*/
void insertarBox (TCifrado * cf, struct TBox box, unsigned char *ok){
	TCifrado new = malloc(sizeof(struct TBox));
	*ok = 0;

	if(new == NULL){
		perror("ERROR: memoria insuficiente");
		exit(-1);
	}else{
		if(*cf == NULL){//si esta vacio
			if(box.esSBox ){//y es sumatorio
				new->esSBox = box.esSBox;
				new->valorASumar = box.valorASumar;
				new->sig = NULL;
				*cf = new;
				*ok = 1;
			}else{	//no esta vacio
				TCifrado ptr= *cf;

				while(ptr->sig != NULL){
					ptr = ptr->sig;
				}
				if((ptr->esSBox) != box.esSBox){
					if(box.esSBox){
						new->esSBox = box.esSBox;
						new->valorASumar = box.valorASumar;
					}else{
						new->esSBox = box.esSBox;
						new->bitACambiar = box.bitACambiar;
					}
					new->sig = NULL;
					ptr->sig = new;
					*ok = 1;
				}
			}

		}


	}



}

/* (1.5 puntos) funcion que dado un nodo y un valor, devuelve
 * el resultado de aplicar dicho nodo a dicho valor. Deberas
 * de tener en cuenta si el nodo es una SumaBox o una XORBox.
 * En el ultimo caso, necesitaras usar operadores logicos a
 * nivel de bit, como &, |, ^ o bien ~, asi como probablemente
 * usar constantes  numericas. */
unsigned char aplicarBox (struct TBox box, unsigned char valor){
	unsigned char res;
	if(box.esSBox){
		res = box.valorASumar + valor;
	}else{
		if(!box.bitACambiar){
			res = valor ^1;
		}else{
			res = valor ^ (1<<7);
		}
	}
	return res;
}

/* (1.5 puntos) funcion que toma un esquema de cifrado y un valor,
 * y devuelve el resultado de aplicar dicho esquema de cifrado a
 * dicho valor, segun el metodo descrito anteriormente.*/
unsigned char aplicarEsquemaDeCifrado(TCifrado cf, unsigned char valor){
	while(cf != NULL){
		valor = aplicarBox(*cf, valor);
		cf = cf->sig;
	}
	return valor;
}

/* (2.5 puntos) funcion que toma un nombre de fichero, en el que se
 *  escribiran en modo binario los datos correspondientes al esquema
 *  de cifrado que se pasa como parametro, de modo que el al final el
 *  fichero unicamente contenga dichos datos. Si no se puede abrir el
 *  fichero, se debe de mostrar un mensaje de error por pantalla.*/
void escribirAFichero(char *nm, TCifrado cf){
	FILE *f = fopen(nm, "wb");
	if(f!= NULL){
		while(cf!= NULL){
			fwrite(cf->esSBox ? &(cf->valorASumar): &(cf->bitACambiar),sizeof(unsigned char),1,f);
			cf = cf->sig;
		}
		fclose(f);
	}else{
		perror("No se pudo escribir a fichero");
	}
}

/* (1.0 puntos) funcion que destruye un esquema de cifrado y libera la memoria que ocupa*/
void destruirEsquemaDeCifrado (TCifrado *cf){
	TCifrado ptr;
	while(*cf != NULL){
		ptr = *cf;
		*cf = (*cf)->sig;
		free(ptr);
	}
}
