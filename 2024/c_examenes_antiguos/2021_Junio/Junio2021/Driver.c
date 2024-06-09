/*
 ============================================================================
 Name        : ExamCJune2021.c
 Author      : 
 Version     :
 Copyright   : Your copyright notice
 Description : Hello World in C, Ansi-style
 ============================================================================
 */

#include <stdio.h>
#include <stdlib.h>
#include "Vector.h"

int main(void) {
	setvbuf(stdout, NULL,_IONBF,0);
	printf("1st.-------------- Creating vector one. Checking length \n");
	TVector v = create();
	display(v);
	printf("Length of v is: %d\n", length(v));
	putTail(&v, 13);
	putTail(&v, 4);
	putTail(&v, 11);
	putTail(&v, 13);
	display(v);
	printf("Length of v is: %d\n", length(v));

	printf("2nd.-------------- Creating vector two \n");
	TVector w = create();
	display(w);
	putTail(&w, 5);
	putTail(&w, 6);
	putTail(&w, 7);
	putTail(&w, 8);
	display(w);

	printf("3rd.-------------- Checking add \n");
	display(v);
	add(v, w);
	display(v);
	display(w);

	printf("4th.-------------- Checking wrong add \n");
	putTail(&w, 14);
	display(w);
	add(v, w);
	display(v);
	display(w);

	printf("5th.-------------- Checking files \n");
	display(v);
	saveToFile(v, "test.bin");
	destroy(&w);
	display(v);
	display(w);
	w = loadFromFile("test.bin");
	putTail(&w, 0);
	display(v);
	display(w);
	
	printf("6th.-------------- Checking shuffling \n");
	destroy(&w);
	destroy(&v);
	putTail(&v, 20);
	putTail(&v, 35);
	putTail(&v, 40);
	putTail(&v, 15);
	display(v);
	int reorder[] = {2,3,0,1};
	w = shuffle(v, reorder);
	display(v);
	display(w);

	printf("7th.-------------- Checking wrong shuffling \n");
	destroy(&w);
	int reorder2[] = {2,3,1,1};
	w = shuffle(v, reorder2);
	display(w);
	int reorder3[] = {2,3,1,5};
	w = shuffle(v, reorder3);
	display(w);
	int reorder4[] = {2,-1,1,5};
	w = shuffle(v, reorder4);
	display(w);

	printf("8th.-------------- Destroying \n");
	destroy(&v);
	destroy(&w);
	display(v);
	display(w);
	return EXIT_SUCCESS;
}
