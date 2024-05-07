#include "stack.h"
void stackCreate(Stack *s)
{
    *s=(Stack)malloc(sizeof(struct stack)); //Pide memoria para la pila
    assert(*s!= NULL); //True -> no hace nada | False -> Da una error
    (*s)->size = 0; //Tamaño es 0, ya que se acaba de crear
    (*s)->head = NULL; //La cabecera no apunta a nada
}


/*
                                3
                      2         2
 _____   __1___    ___1___   ___1___
 empty    push       push      push
*/
int stackPush(Stack s, int v)
{
   StackNode nuevo = (StackNode)malloc(sizeof(struct stackNode)); //Agrega tamaño a la pila, ya que vamos a añadir un valor nuevo a la pila de tamaño 0
   if(nuevo == NULL) return 0;
   nuevo->value = v; //Agregamos el valor a la posicion i a una nueva pila
   nuevo->siguiente = s->head; //Con la siguiente posicion de la nueva pila, miramos a la posicion de la pila
   s->head = nuevo; //Con la posicion de la pila, le añadimos la nueva pila
   s->size++; //aumentamos el tamaño de la pila
    return 1;
}



/*
   3                            
   2       2                    
 __1__   __1___    ___1___   _______
  pop     pop        pop      empty
*/

int stackPop(Stack s, int *v)
{
    if(s->head == NULL) return 0;
    StackNode temp = s->head; // Creamos una pila temporal que le añadimos la cabecera de la pila
    *v = temp->value; //Con una variable int le agregamos el valor de la pila temporal
    s->head = temp->siguiente; //Con la cabecera de la pila miramos al siguiente de la pila temporal
    s->size--; // Decrementamos la pila
    free(temp); // Elimina la memoria agregada al puntero
    return 1;
}
int stackSize(Stack s)
{
    return s->size; //Devuelve el valor de la pila
}