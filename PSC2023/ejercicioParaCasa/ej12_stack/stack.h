#ifndef __STACK_H__
#define __STACK_H__
#include <stdio.h>
#include <stdlib.h>
#include <assert.h>
typedef struct stackNode *StackNode;
struct stackNode
{
    int value;
    StackNode siguiente;
};
typedef struct stack *Stack;
struct stack
{
    int size;
    StackNode head;
};
// Ojo, pasamos un puntero a un puntero, para poder modificar esa variable.
void stackCreate(Stack *s);

// Devuelve 0 si no se puede, 1 si si
// s se espera que sea distinto de NULL
int stackPush(Stack s, int v);

// Devuelve 0 si no se puede, 1 si si
// s se espera que sea distinto de NULL
int stackPop(Stack s, int *v);

// Devuelve el tamaño
// s se espera que sea distinto de NULL
int stackSize(Stack s);
#endif