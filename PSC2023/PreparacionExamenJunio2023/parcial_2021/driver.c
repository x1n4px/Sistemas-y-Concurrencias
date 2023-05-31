//
// Created by iN4P on 13/06/2021.
//

/*
 * driver.c
 *
 *  Created on: 9 apr. 2021
 *      Author: PSC
 */
#include <stdio.h>
#include "nfv.h"


int main(){
    int r;
    LInfra infraUMA;
    setvbuf(stdout, NULL,_IONBF,0);

    init(&infraUMA, INFRA_NODES, INFRA_MAX_CPU);

    printf("Inicializamos infraestructura y mostramos\n");
    showInfra(infraUMA);

    r = deployVNF(infraUMA, "v1",3);
    r? printf("Insertamos v1 - cpu=3\n") : printf("No se ha insertado v1 - cpu=3\n") ;

    r = deployVNF(infraUMA, "v2",5);
    r? printf("Insertamos v2 - cpu=5\n") : printf("No se ha insertado v2 - cpu=5\n");

    r = deployVNF(infraUMA, "v3",1);
    r? printf("Insertamos v3 - cpu=1\n") : printf("No se ha insertado v3 - cpu=1\n");

    r = deployVNF(infraUMA, "v4",2);
    r? printf("Insertamos v4 - cpu=2\n") : printf("No se ha insertado v4 - cpu=2\n");

    r = deployVNF(infraUMA, "v5",6);
    r? printf("Insertamos v5 - cpu=6\n") : printf("No se ha insertado v5 - cpu=6\n");

    r = deployVNF(infraUMA, "v6",4);
    r? printf("Insertamos v6 - cpu=4\n") : printf("No se ha insertado v6 - cpu=4\n");

    printf("Mostramos despues de las inserciones:\n");
    showInfra(infraUMA);
    printf("Liberamos los recursos de v1 y mostramos\n");
    releaseVNF(infraUMA, "v1");
    showInfra(infraUMA);

    store(infraUMA,"/home/in4p/git/Sistemas-y-Concurrencias/PSC2023/PreparacionExamenJunio2023/parcial_2021/deployedVNFs.dat");
    printf("VNFs almacenadas en deployedVNFs.dat\n");
    destroyInfra(&infraUMA);
    printf("Destruimos la infraestructura y mostramos\n");
    showInfra(infraUMA);



}
