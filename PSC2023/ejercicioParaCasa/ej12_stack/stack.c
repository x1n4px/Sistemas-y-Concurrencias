#include "stack.h"
void stackCreate(Stack *s)
{
    *s=(Stack)malloc(sizeof(struct stack));
    assert(*s!= NULL);
    (*s)->size = 0;
    (*s)->head = NULL;
}
int stackPush(Stack s, int v)
{
   StackNode nuevo = (StackNode)malloc(sizeof(struct stackNode));
   if(nuevo == NULL) return 0;
   nuevo->value = v;
   nuevo->siguiente = s->head;
   s->head = nuevo;
   s->size++;
    return 1;
}
int stackPop(Stack s, int *v)
{
    if(s->head == NULL) return 0;
    StackNode temp = s->head;
    *v = temp->value;
    s->head = temp->siguiente;
    s->size--;
    free(temp);
    return 1;
}
int stackSize(Stack s)
{
    return s->size;
}