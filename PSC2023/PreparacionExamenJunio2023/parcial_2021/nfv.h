/*
 * nfv.h
 *
 *  Created on: 9 apr. 2021
 *      Author: PSC
 */
#define INFRA_NODES 5
#define INFRA_MAX_CPU 5


#ifndef NFV_H_

typedef struct Vnf *LVnf;

struct Vnf{ // Almacena los datos de una vnf
    char id[10];
    int cpu;    // cpus necesita/utilizada la vnf
    LVnf next;
};

typedef struct NodeInfra *LInfra;
struct NodeInfra{ // Almacena los datos de un nodo de infraestructura
    int dispCpu;  // cpus disponibles en el nodo
    LVnf vnfs;    // lista de vnfs desplegadas en el nodo
    LInfra next;
};


/* (1.5 pts) Inicializa la infraestructura con size nodos y cada nodo con
   initCpu disponibles */
void init(LInfra *infra,int size, int initCpu);

/* (2 pts) Despliega (inserta) la vnf en el primer nodo que tenga suficiente
   cpu disponible. La nueva vnf será la primera de la lista de vnfs asociadas
   al nodo.
   Devuelve 1 si ha podido insertar la vnf.
   Devuelve 0 si no ha podido insertarla en ningún nodo. */
int deployVNF(LInfra infra, char *vnfId, int cpu);


/* (1 pt) Muestra por pantalla, para todos los nodos infra, la cpu disponible
   y las VNFs desplegadas. */
void showInfra(LInfra LInfra);


/* (2 pts) Busca en la infraestructura la vnf con el id dado y la elimina de
   la infra, liberando la cpu correspondiente. */
void releaseVNF(LInfra infra, char *vnfId);

/* (1.5 pts) Destruye todos los nodos de la infraestructura, incluidas las
   VNFs que estén desplegadas. */
void destroyInfra(LInfra *infra);

/* (2 pts) Guarda en un fichero binario la información de las vnfs que se
   encuentran desplegadas en la infraestructura. Para cada vnf el fichero
   binario contiene: <longitud del id><id><cpu> */
void store(LInfra infra, char *filename);



#define NFV_H_



#endif /* NFV_H_ */
