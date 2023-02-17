# Sistemas-y-Concurrencias

[Diferencias entre "%i" y "%d"](https://www.geeksforgeeks.org/difference-d-format-specifier-c-language/)
### Como Usar Debug en linea de comando:
```
gcc -g Main.c programa.c -o nombreEjecutable
gdb nombreEjecutable
//Una vez dentro del debug:
break Main.c:Linea a debug  -> se puede borrar(delete) o bloquear temporalmente (disable breakpoint Numero)
run // Para correr el programa
n // Para siguiente (next)
quit // Para salir
print *lista //Muestra el contenido de la lista
print lista //Muestra la posicion de la lista
``` 


## Punteros:
[Funciones Básicas Punteros](https://github.com/x1n4px/Sistemas-y-Concurrencias/blob/main/temario/PSC-Tema2.pdf)

[Practica 1 2023](https://github.com/x1n4px/Sistemas-y-Concurrencias/tree/main/PSC2023/Practicas/Practica1-2023/Tremor)

[Practica 1 2022](https://github.com/x1n4px/Sistemas-y-Concurrencias/tree/main/PSC2023/Practicas/Practica1-2022/recursos%20practica%201)

[Practica 2 2023](https://github.com/x1n4px/Sistemas-y-Concurrencias/tree/main/PSC2023/Practicas/Practica2-2023/Practica2)



1. Declaración de un puntero:
```ruby
int x = 10;
int *ptr;
ptr = &x;

```
En este ejemplo, x es una variable entera y ptr es un puntero a un entero. La asignación ptr = &x le asigna a ptr la dirección de memoria de x.


2. Uso de un puntero
```ruby
int x = 10;
int *ptr;
ptr = &x;
*ptr = 20;

```
En este ejemplo, se utiliza el operador de indirección * para modificar el valor almacenado en la dirección a la que apunta ptr. Después de ejecutar este código, el valor de x será 20.


3. Arreglos y punteros
```ruby
int arr[5] = {1, 2, 3, 4, 5};
int *ptr;
ptr = arr;
```
En este ejemplo, arr es un arreglo de enteros y ptr es un puntero a un entero. La asignación ptr = arr le asigna a ptr la dirección del primer elemento de arr. Podemos acceder a los elementos del arreglo a través del puntero usando la siguiente sintaxis: *(ptr + i) para acceder al i-ésimo elemento del arreglo


4. Funciones y punteros:
```ruby
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
