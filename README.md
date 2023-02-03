# Sistemas-y-Concurrencias

## Punteros:
1. Declaración de un puntero:
```
int x = 10;
int *ptr;
ptr = &x;

```
En este ejemplo, x es una variable entera y ptr es un puntero a un entero. La asignación ptr = &x le asigna a ptr la dirección de memoria de x.

2. Uso de un puntero
```
int x = 10;
int *ptr;
ptr = &x;
*ptr = 20;

```
En este ejemplo, se utiliza el operador de indirección * para modificar el valor almacenado en la dirección a la que apunta ptr. Después de ejecutar este código, el valor de x será 20.

3. Arreglos y punteros
```
int arr[5] = {1, 2, 3, 4, 5};
int *ptr;
ptr = arr;
```
En este ejemplo, arr es un arreglo de enteros y ptr es un puntero a un entero. La asignación ptr = arr le asigna a ptr la dirección del primer elemento de arr. Podemos acceder a los elementos del arreglo a través del puntero usando la siguiente sintaxis: *(ptr + i) para acceder al i-ésimo elemento del arreglo

4. Funciones y punteros:
```
void swap(int *a, int *b){
  int temp = *a;
  *a = *b;
  *b = temp;
}
int x = 10;
int y = 20;
swap(&x, &y);
```
En este ejemplo, se define una función swap que toma dos punteros a enteros y los intercambia. Al llamar a la función swap con las direcciones de x e y &x y &y, se intercambian los valores almacenados en x e y. Después de llamar a la función, el valor de x será 20 y el valor de y será 10.

Es importante tener en cuenta que los punteros pueden ser una herramienta poderosa, pero también pueden ser peligrosos si no se utilizan correctamente. Por ejemplo, acceder a una dirección de memoria no válida puede causar errores y comportamientos impredecibles en el programa.

Estos son solo algunos ejemplos básicos de cómo funcionan los punteros en C. Hay muchos otros conceptos y técnicas que puedes aprender y explorar con más profundidad

## Semáforos
```


```

## Monitores
```


```
