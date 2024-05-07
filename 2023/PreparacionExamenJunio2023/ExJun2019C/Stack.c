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
T_Stack create() {
    T_Stack stack = NULL;
    return stack;
}

// Returns true if the stack is empty and false in other case.
int isEmpty(T_Stack q) {
    if(q == NULL) {
        return 1;
    }else{
        return 0;
    }
}

// Inserts a number into the stack.
void push(T_Stack * pq, int operand) {
    if(*pq == NULL){
        (*pq)->number = operand;
        (*pq)->next = NULL;
    }else{
        while((*pq)->next != NULL) {
            *pq = (*pq)->next;
        }
        (*pq)->next->number = operand;
        (*pq)->next->next = NULL;
    }
}

// "Inserts" an operator into the stack and operates.
// Returns true if everything OK or false in other case.
int pushOperator(T_Stack * pq, char operator) {
    T_Stack ptr = *pq, ant = NULL;
    int ok = 0, res = 0;

    while(ptr->next != NULL) {
        ant = ptr;
        ptr = ptr->next;
    }

    if(ptr && ant) {
        ok = 1;
        switch (operator) {
            case '+':
                res = ant->number + ptr->number;
                break;
            case '-':
                res = ant->number - ptr->number;
                break;
            case '*':
                res = ant->number * ptr->number;
                break;
            case '/':
                res = ant->number / ptr->number;
                break;
            default: ok = 0;
        }

        if(ok) {
            ant->number = res;
            ant->next = NULL;
            free(ptr);
        }
    }
    return ok;
}

// Puts into data the number on top of the stack, and removes the top.
// Returns true if everything OK or false in other case.
int pop(T_Stack * pq, int * data) {
    int ok = 0;
    T_Stack ptr = *pq, ant = NULL;

    if(!isEmpty(ptr)) {
        while (ptr->next != NULL) {
            ant = ptr;
            ptr = ptr->next;
        }
        *data = ptr->number;
        if(ant == NULL) {
            *pq = NULL;
        }else{
            ant->next = NULL;
        }
        free(ptr);
        ok = 1;
    }
    return ok;
}

// Frees the memory of a stack and sets it to empty.
void destroy(T_Stack * pq) {
    T_Stack ptr;

    while((*pq) != NULL){
        ptr = *pq;
        *pq = (*pq)->next;
        free(ptr);
    }
 }
