/*
 * Stack.c
 *
 *  Created on: 11 jun. 2019
 *      Author: galvez
 */
#include <stdio.h>
#include <stdlib.h>
#include "Stack.h"

// Creates an empty stack.
T_Stack create()
{
    return NULL;
}

// Returns true if the stack is empty and false in other case.
int isEmpty(T_Stack q)
{
    if (q == NULL)
    {
        return 1;
    }
    return 0;
}

// Inserts a number into the stack.
void push(T_Stack *pq, int operand)
{
    T_Node *newNode = (T_Node *)malloc(sizeof(T_Node));
    if (newNode == NULL)
    {
        printf("Error al reservar memoria");
        exit(-1);
    }
    T_Stack ptr = *pq;
    if (ptr == NULL)
    {
        return;
    }
    else
    {
        while (ptr != NULL)
        {
            ptr = ptr->next;
        }
        newNode->next = NULL;
        newNode->number = operand;
        ptr->next = newNode;
    }
}

// "Inserts" an operator into the stack and operates.
// Returns true if everything OK or false in other case.
int pushOperator(T_Stack *pq, char operator)
{
    if (isEmpty(*pq))
    {
        printf("Error: Stack underflow\n");
        return 0;
    }

    int operand2, operand1;
    if (!pop(pq, &operand2) || !pop(pq, &operand1))
    {
        printf("Error: Operandos insuficientes\n");
        return 0;
    }

    int result;
    switch (operator)
    {
    case '+':
        result = operand1 + operand2;
        break;
    case '-':
        result = operand1 - operand2;
        break;
    case '*':
        result = operand1 * operand2;
        break;
    case '/':
        if (operand2 == 0)
        {
            printf("Error: Division by zero.\n");
            return 0;
        }
        result = operand1 / operand2;
        break;
    default:
        break;
    }

    push(*pq, result);
    return 1;
}

// Puts into data the number on top of the stack, and removes the top.
// Returns true if everything OK or false in other case.
int pop(T_Stack *pq, int *data)
{
    if (isEmpty(*pq))
    {
        printf("Error: Stack underflow\n");
        return 0;
    }

    T_Node *temp = *pq;
    *data = temp->number;
    *pq = temp->next;
    free(temp);
    return 1;
}

// Frees the memory of a stack and sets it to empty.
void destroy(T_Stack *pq)
{
    while (!isEmpty(*pq))
    {
        T_Node *temp = *pq;
        *pq = (*pq)->next;
        free(temp);
    }
}
