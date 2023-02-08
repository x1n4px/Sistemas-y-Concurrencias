
#include <stdio.h>
#include <string.h>
#include <stdlib.h>

void swap(int *x, int *y)
{
    int temp;
    temp = *x;
    *x = *y;
    *y = temp;
}

int main(int argc, char constargv[])
{
    int x, y;
    x = 10;
    y = 5;
    printf("X = %i    Y = %i\n", x, y);
    swap(&x, &y);
    printf("X = %i    Y = %i\n", x, y);
    return 0;
}