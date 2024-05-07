/*
 ============================================================================
 Name        : RPN.c
 Author      : SGR
 Version     :
 Copyright   : Your copyright notice
 Description : Hello World in C, Ansi-style
 ============================================================================
 */

#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>
#include <string.h>
#include "Stack.h"

int process(char * filename);

int main(void) {
	T_Stack q;
	int ok, result;
	q = create();
	if (isEmpty(q)) puts("Now the queue is empty.");
	else puts("Now the queue contains something.");
	push(&q, 3);
	if (isEmpty(q)) puts("Now the queue is empty.");
	else puts("Now the queue contains something.");
	push(&q, 4);
	push(&q, 5);
	ok = pushOperator(&q, '*');
	if (!ok) puts("* cannot operate");
	ok = pushOperator(&q, '+');
	if (!ok) puts("* cannot operate");
	push(&q, 6);
	ok = pushOperator(&q, '+');
	if (!ok) puts("+ cannot operate");
	ok = pop(&q, &result);
	if (!ok) puts("Cannot pop");
	printf("The result is %d.\n", result);
	if (isEmpty(q)) puts("Now the queue is empty.");
	else puts("Now the queue contains something.");

	result = process("/home/in4p/git/Sistemas-y-Concurrencias/PSC2023/PreparacionExamenJunio2023/ExJun2019C/source.calc");
	printf("The result from the file is %d.\n", result);
	return EXIT_SUCCESS;
}

int text2Int(char * text) {
	int value=0, i=0;
	while(isdigit(text[i]))
		value = (value*10)+(text[i++]-'0');
	return value;
}

int isOperator(char * text){
	return !isdigit(text[0]);
}

#define MAX_LENGTH 1024
int process(char * filename) {
    FILE *archivo = fopen(filename, "rb");
    if(archivo == NULL){
        printf("No se pudo abrir el fichero\n");
        return 0;
    }
    T_Stack  stack = create();
    char line[6];
    int result;

    while(fgets(line, sizeof(line), archivo) != NULL) {
        int length = strlen(line);
        if(length > 0 && line[length -1] == '\n'){
            line[length - 1] = '\0'; // Eliminar el carácter de nueva línea
        }
        
        if(isdigit(line[0])){
            int operand = atoi(line);
            push(&stack, operand);
        }else if(strlen(line) == 1){
            int data;
            if(!pop(&stack, &data)){
                printf("Error: la pila no tiene suficientes datos");
                fclose(archivo);
                return 0;
            }

            switch (line[0]) {
                case '+':
                    result = data + stack->next->number;
                    break;
                case '-':
                    result = data - stack->next->number;
                    break;
                case '*':
                    result = data * stack->next->number;
                    break;
                case '/':
                    result = data / stack->next->number;
                    break;
                default:
                    printf("Error: Operador inválido.\n");
                    fclose(archivo);
                    return 0;
            }
            stack->next->number = result;
        }
    }
    if (stack->next != NULL) {
        result = stack->next->number;
        free(stack->next);
        stack->next = NULL;
    } else {
        printf("Error: La pila está vacía.\n");
        fclose(archivo);
        return 0;
    }

    fclose(archivo);
    return result;
}
