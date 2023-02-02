// Ejercicio 6.  Vamos a hacer el esqueleto de un menú interactivo. El usuario introduce una cadena de
// texto inferior a 100 carácteres que representa una de las siguientes opciones de menú: 
    //     iniciar
    //     llamada1
    //     llamada2
    //     fin

    // Tras leer, imprimimos un mensaje, elige el mensaje que quieras. Cuando se lee un fin, se termina
    // el programa. Si se lee algo que no corresponde con una opción de menú, se avisa al usuario que no se
    // conoce esa opción, y se le muestra lo que ha introducido.
    

    #include <stdio.h>
#include <string.h>

#define MAX_LEN 100

int main(void) {
  char input[MAX_LEN];

  while (1) {
    printf("Introduce una opción (iniciar, llamada1, llamada2, fin): ");
    scanf("%s", input);

    if (strcmp(input, "fin") == 0) {
      break;
    } else if (strcmp(input, "iniciar") == 0) {
      printf("Has elegido la opción 'iniciar'\n");
    } else if (strcmp(input, "llamada1") == 0) {
      printf("Has elegido la opción 'llamada1'\n");
    } else if (strcmp(input, "llamada2") == 0) {
      printf("Has elegido la opción 'llamada2'\n");
    } else {
      printf("Opción '%s' desconocida\n", input);
    }
  }

  return 0;
}
