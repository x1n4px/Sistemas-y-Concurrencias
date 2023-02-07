/* En C, como en muchos otros lenguajes, existe la posibilidad de usar bibliotecas (librerías)
• Típicamente:
– En un fichero.h se incluyen:
    Declaraciones de tipos, constantes, variables estáticas, declaraciones de funciones
– En un fichero.c se incluye:
    La implementación de todo lo indicado en el .h

La compilación con librerías cambia y requiere que se compile junto con el archivo principal las librerías. Si tenemos un archivo que contiene el main llamado principal.c y una libería implementada en liberia.h y liberia.c, para copilar podemos hacerlo como:
gcc -g principal.c liberia.c

Cuando se definen liberías en C usamos una macro para asegurarnos que no se incluye más de una vez, ya que estaríamos intentando definir varias veces lo mismo y daría error:
#ifndef __LIBRERIA_H__
#define __LIBRERIA_H__

En el *.h se definen los prototipos de las funciones y luego se implementan en el *.c.

Sobre el assert, se usa como testing para pruebas unitarias en C (como se usa en el main), y también para control de errores (como se debe usar en stack.c). Permite definir asertos, cuando algo debe ser verdadero, y no se cumple, para la ejecución y nos avisa. Es interesante para ahorrar if else cuando hay ciertos parámetros que deben, por ejemplo, no ser NULL cuando nos llaman (control de errores en nuestra librería). También se puede usar para generar pruebas que debe pasar la libería, y si no lo pasa, algo va mal. Estas pruebas se escriben generalmente antes que la libería, sólo con el prototipo de la función.

Ejercicio
1.- Implementa la clase stack, en la que se meten y se sacan elementos por la cabeza y comprueba que funciona correctamente. REcuerda modificar el task.json para que compile la liberia junto a este archivo.


*/
#include "stack.h";
#include <assert.h> //Esta duplicado porque no podemos asumir que lo incluyan en stack
int main(int argc, char const *argv[])
{
    Stack myStack;
    int pop;
    // Generamos una lista vacia
    stackCreate(&myStack);
    assert(stackSize(myStack) == 0);
    assert(stackPop(myStack, &pop) == 0);

    // Probamos a insertar un elemento, debe encontrarlo
    stackPush(myStack, 4);
    assert(myStack != NULL);
    assert(myStack->head != NULL);
    assert(myStack->head->value == 4);

    // Insertamos un segundo valor, comprobamos que devuelve 1, que es que puede insertar
    assert(stackPush(myStack, 14) == 1);
    stackPop(myStack, &pop);
    assert(pop == 14);
    assert(stackSize(myStack) == 1);


    return 0;
}
