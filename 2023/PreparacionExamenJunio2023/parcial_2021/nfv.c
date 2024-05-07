//
// Created by iN4P on 13/06/2021.
//
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "nfv.h"


/* (1.5 pts) Inicializa la infraestructura con size nodos y cada nodo con
   initCpu disponibles */
void init(LInfra *infra,int size, int initCpu){
    LInfra  ptr;
    LInfra aux;
    *infra = NULL;
    int i=0;
    while(i<size){
        aux = malloc(sizeof(struct NodeInfra));
        if(aux == NULL) {
            printf("NO se ha podido alojar memoria\n");
            exit(-1);
        }
        aux->dispCpu = initCpu;
        aux->vnfs = NULL;
        aux->next = NULL;
        if(i==0){
            *infra = aux;
            ptr = aux;
        }else{
            ptr->next = aux;
            ptr = ptr->next;
        }
        ++i;
    }
}

/* (2 pts) Despliega (inserta) la vnf en el primer nodo que tenga suficiente
   cpu disponible. La nueva vnf será la primera de la lista de vnfs asociadas
   al nodo.
   Devuelve 1 si ha podido insertar la vnf.
   Devuelve 0 si no ha podido insertarla en ningún nodo. */
int deployVNF(LInfra infra, char *vnfId, int cpu){
    if(cpu <=0){
        printf("NUmero de CPU requeridas invalido\n");
        return 0;
    }
    LInfra ptr = infra;
    LVnf aux = malloc(sizeof(struct Vnf));
    int res = 0;
    if(aux==NULL){
        printf("No se ha podido alojar memoria\n");
        return 0;
    }
    aux->cpu = cpu;
    strcpy(aux->id,vnfId);
    aux->next=NULL;
    if(ptr!=NULL){
        while(ptr!=NULL&&ptr->dispCpu<cpu){
            ptr = ptr->next;
        }
        if(ptr != NULL){
            ptr->dispCpu = ptr->dispCpu-cpu;
            LVnf ptrV = ptr->vnfs;
            if(ptrV == NULL){
                ptr->vnfs = aux;
            }else{
                aux->next = ptrV;
                ptr->vnfs = aux;
            }
            res = 1;
        }
    }
    if(!res){
        free(aux);
        aux = NULL;
    }
    return res;

}


/* (1 pt) Muestra por pantalla, para todos los nodos infra, la cpu disponible
   y las VNFs desplegadas. */
void showInfra(LInfra LInfr){
    LInfra ptr = LInfr;
    if(ptr == NULL) printf("No hay nodos en la infraestructura\n");
    else{
        int cont = 0;
        while(ptr != NULL) {
            printf("Infraestructura %d con CPU disponibles %d y vnfs: ",cont,ptr->dispCpu);
            if(ptr->vnfs != NULL){
                LVnf ptrV = ptr->vnfs;
                while(ptrV != NULL){
                    printf("(%s,%d)",ptrV->id,ptrV->cpu);
                    ptrV = ptrV->next;
                }
            }
            printf("\n");
            ++cont;
            ptr = ptr->next;
        }
    }
}


/* (2 pts) Busca en la infraestructura la vnf con el id dado y la elimina de
   la infra, liberando la cpu correspondiente. */
void releaseVNF(LInfra infra, char *vnfId){
    LInfra ptr = infra;
    short int var = 1;
    if(ptr!=NULL){
        while(ptr != NULL && var){
            if(ptr->vnfs!=NULL){
                LVnf ptrV = ptr->vnfs;
                LVnf ant;
                while(ptrV!=NULL&& strcmp(ptrV->id,vnfId)){
                    ant = ptrV;
                    ptrV = ptrV->next;
                }
                if(ptrV != NULL){
                    if(ptrV==ptr->vnfs){
                        ptr->vnfs=ptrV->next;
                    }else if(ptrV!=NULL){
                        ant->next = ptrV->next;
                    }
                    ptr->dispCpu = ptr->dispCpu + ptrV->cpu;
                    free(ptrV);
                    ptrV == NULL;
                    var = 0;
                }
            }
            ptr = ptr->next;
        }
    }


}

/* (1.5 pts) Destruye todos los nodos de la infraestructura, incluidas las
   VNFs que estén desplegadas. */
void destroyInfra(LInfra *infra){
    LInfra ptr = *infra;
    LInfra aux;
    while(ptr != NULL){
        if(ptr->vnfs != NULL){
            LVnf ptrV = ptr-> vnfs;
            LVnf auxV;
            while(ptrV!=NULL){
                auxV = ptrV;
                ptrV = ptrV->next;
                free(aux);
                auxV = NULL;
            }
            ptr->vnfs = NULL;
        }
        aux = ptr;
        ptr= ptr->next;
        free(aux);
        aux = NULL;
    }
    *infra = NULL;
}

/* (2 pts) Guarda en un fichero binario la información de las vnfs que se
   encuentran desplegadas en la infraestructura. Para cada vnf el fichero
   binario contiene: <longitud del id><id><cpu> */
void store(LInfra infra, char *filename){
    FILE *f;
    if((f=fopen(filename,"wb"))==NULL){
        printf("Error al crear el fichero\n");
        exit(-1);
    }
    LInfra ptr = infra;
    while(ptr != NULL){
        if(ptr->vnfs!= NULL){
            LVnf ptrV = ptr->vnfs;
            int idSize;
            unsigned tam,idStr,escrCpu;
            while(ptrV!=NULL){
                idSize = strlen(ptrV->id);
                tam = fwrite(&idSize,sizeof (char),idSize,f);
                idStr = fwrite(ptrV->id,sizeof (char),idSize,f);
                escrCpu = fwrite(&ptrV->cpu,sizeof (int),1,f);
                if((tam+idStr+escrCpu)!=(idSize+2)){
                    printf("Error en la escritura\n");
                    exit(-1);

                }
                ptrV = ptrV->next;
            }
        }
        ptr = ptr->next;
    }
    fclose(f);

}



























