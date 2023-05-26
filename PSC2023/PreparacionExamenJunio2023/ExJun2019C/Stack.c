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
    T_Stack nodo = (T_Stack)malloc(sizeof(struct Node));
    if(nodo != NULL) {
        nodo->next = NULL;
        nodo->number = 0;
    }
    return nodo;
}

// Returns true if the stack is empty and false in other case.
int isEmpty(T_Stack q) {
    T_Stack ptr;
    ptr = q;

    if(q == NULL){
        return 1;
    }
    return 0;
}

// Inserts a number into the stack.
void push(T_Stack * pq, int operand) {
    T_Stack nuevoDato = (T_Stack)malloc(sizeof(struct Node));

    if(nuevoDato != NULL) {
        nuevoDato->next = NULL;
        nuevoDato->number = operand;
    }

    if(pq == NULL){
        pq = nuevoDato;
        return;
    }

    T_Stack ptr = pq;
    while(ptr->next != NULL){
        ptr = ptr->next;
    }
    ptr->next = nuevoDato;
}

// "Inserts" an operator into the stack and operates.
// Returns true if everything OK or false in other case.
int pushOperator(T_Stack * pq, char operator) {
    T_Stack ptr = pq;
    if(ptr == NULL || ptr->next == NULL){
        printf("La lista no tiene suficientes elementos.\n");
        return 0;
    }
    int ultimo,penultimo, operacion;

    while(ptr->next->next != NULL){
        ptr = ptr->next;
    }

    ultimo = ptr->next->number;
    penultimo = ptr->number;

    free(ptr->next);
    ptr->next = NULL;

    T_Stack valorOperacion = (T_Stack) malloc(sizeof(struct Node));
    if(valorOperacion == NULL) {
        return 0;
    }


    switch(operator) {
        case '+':
            valorOperacion->next = NULL;
            valorOperacion->number = ultimo + penultimo;
            break;
        case '-':
            valorOperacion->next = NULL;
            valorOperacion->number = ultimo - penultimo;
            break;
        case '*':
            valorOperacion->next = NULL;
            valorOperacion->number = ultimo * penultimo;
            break;
        case '/':
            valorOperacion->next = NULL;
            valorOperacion->number = ultimo / penultimo;
            break;
        default:
            printf("Error al operar.\n");
            return 0;
    }

    //Encontrar el ultimo nodo de la lista
    while(ptr->next != NULL){
        ptr = ptr->next;
    }
    ptr->next = valorOperacion;
    return 1;
}

// Puts into data the number on top of the stack, and removes the top.
// Returns true if everything OK or false in other case.
int pop(T_Stack * pq, int * data) {
    T_Stack ptr = pq;
    if(ptr == NULL || ptr->next == NULL){
        printf("La lista no tiene suficientes elementos.\n");
        return 0;
    }

    while(ptr->next != NULL){
        ptr = ptr->next;
    }

    *data = ptr->number;

    free(ptr);

    return 1;



}

// Frees the memory of a stack and sets it to empty.
void destroy(T_Stack * pq) {
    T_Stack current = *pq;
    T_Stack next;

    while(current != NULL){
        next = current->next;
        free(current);
        current = next;
    }
    *pq = NULL;
}
