#include "gestion_lista.h"
#include <assert.h>
#include <stdlib.h>

int main(int argc, char const *argv[])
{
    ptr_lista_entero lista;
    inicializar(&lista);

    int ok;

    insertarCabeza(&lista,2,&ok);
    assert(lista->numero==2);
    assert(lista->sig==NULL);
    assert(ok==1);

    insertarCabeza(&lista,1,&ok);
    assert(lista->numero==1);
    assert(lista->sig->numero==2);
    assert(ok==1);

    insertarCola(&lista,5,&ok);
    assert(lista->sig->sig->numero==5);
    assert(lista->sig->sig->sig==NULL);
    assert(ok==1);

    insertarCola(&lista,6,&ok);
    assert(lista->sig->sig->sig->numero==6);
    assert(lista->sig->sig->sig->sig==NULL);
    assert(ok==1);

    insertarOrdenado(&lista,4,&ok);
    assert(lista->sig->numero==2);
    assert(lista->sig->sig->numero==4);
    assert(lista->sig->sig->sig->numero==5);
    assert(ok==1);

    insertarOrdenado(&lista,3,&ok);
    assert(lista->sig->numero==2);
    assert(lista->sig->sig->numero==3);
    assert(lista->sig->sig->sig->numero==4);
    assert(ok==1);


    //Probamos a insertar en listas vacías
    destruir(&lista);
    destruir(&lista);

    insertarCola(&lista,4,&ok);
    assert(lista->numero==4);
    assert(lista->sig==NULL);
    assert(ok==1);

    destruir(&lista);

    insertarOrdenado(&lista,3,&ok);
    assert(lista->numero==3);
    assert(lista->sig==NULL);
    assert(ok==1);

    insertarCabeza(&lista,2,&ok);
    assert(ok==1);
    insertarCola(&lista,7,&ok);
    assert(ok==1);
    insertarCola(&lista,8,&ok);
    assert(ok==1);
    insertarCola(&lista,9,&ok);
    assert(ok==1);
    assert(lista->numero==2);
    assert(lista->sig->numero==3);
    assert(lista->sig->sig->numero==7);
    assert(lista->sig->sig->sig->numero==8);
    assert(lista->sig->sig->sig->sig->numero==9);
    assert(ok==1);





    borrarCabeza(&lista,&ok);
    assert(ok==1);
    assert(lista->numero==3);

    borrarCabeza(&lista,&ok);
    assert(ok==1); 
    assert(lista->numero==7);

    borrarCola(&lista,&ok);
    assert(ok==1); 
    assert(lista->numero==7);
    assert(lista->sig->numero==8);

    borrarCola(&lista,&ok);
    assert(ok==1);
    assert(lista->numero==7);
    assert(lista->sig==NULL);

    destruir(&lista);
    borrarCola(&lista,&ok);
    assert(ok==0);

    borrarCabeza(&lista,&ok);
    assert(ok==0);

    //Insertamos de nuevo para probar a dividir la lista
    insertarCabeza(&lista,2,&ok);
    assert(lista->numero==2);
    assert(lista->sig==NULL);
    assert(ok==1);

    insertarCabeza(&lista,1,&ok);
    assert(lista->numero==1);
    assert(lista->sig->numero==2);
    assert(ok==1);

    insertarCola(&lista,5,&ok);
    assert(lista->sig->sig->numero==5);
    assert(lista->sig->sig->sig==NULL);
    assert(ok==1);

    insertarCola(&lista,6,&ok);
    assert(lista->sig->sig->sig->numero==6);
    assert(lista->sig->sig->sig->sig==NULL);
    assert(ok==1);

    insertarOrdenado(&lista,4,&ok);
    assert(lista->sig->numero==2);
    assert(lista->sig->sig->numero==4);
    assert(lista->sig->sig->sig->numero==5);
    assert(ok==1);

    insertarOrdenado(&lista,3,&ok);
    assert(lista->sig->numero==2);
    assert(lista->sig->sig->numero==3);
    assert(lista->sig->sig->sig->numero==4);
    assert(ok==1);
    int ex;

    
    ptr_lista_entero lista_extraidos = quitarNCabeza(&lista,1,&ex);
    assert(lista_extraidos->numero==1);
    assert(lista->numero==2);
    assert(ex==1);

    free(lista_extraidos);
    lista_extraidos=NULL;

    //Deja la lista extaidos con el 2 y 3, y la lista con el 4,5,6.
    lista_extraidos = quitarNCabeza(&lista,2,&ex);  
    assert(lista->numero==4);
    assert(lista_extraidos->sig->numero==3);
    assert(lista_extraidos->sig->sig==NULL);
    assert(ex==2);

    free(lista_extraidos);
    lista_extraidos=NULL;

    //Deja la lista extaidos con el 4,5,6, y la lista con NULL. 
    lista_extraidos = quitarNCabeza(&lista,10,&ex);
    assert(lista==NULL);
    assert(lista_extraidos->sig->numero==5);
    assert(lista_extraidos->sig->sig->sig==NULL);
    assert(ex==3);

    







    return 0;
}
