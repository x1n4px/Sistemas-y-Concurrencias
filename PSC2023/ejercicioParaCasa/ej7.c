// Ejercicio 7. Crea una estructura llamada strCoche y define un alias Coche de esa estructura. La estructura
//  strCoche contendrá las siguientes variables: color (char[20]), tipo (caracter, 'd' diesel, 'g' gasolina),
//  peso (float), numeroAsientos (int). Haz un programa que pregunta al usuario y permite almacenar/cambiar
// los datos de un coche. Haz una pregunta por variable e imprime, despúes de cambiar la variable, el contenido del Coche.

#include <stdio.h>
#include <string.h>

typedef struct
{
  char color[20];
  char tipo;
  float peso;
  int numeroAsientos;
} strCoche;

typedef strCoche Coche;

int main()
{
  Coche coche;

  printf("Introduce el color del coche: ");
  scanf("%s", coche.color);

  printf("Introduce el tipo de combustible del coche ('d' diesel, 'g' gasolina): ");
  scanf(" %c", &coche.tipo);

  printf("Introduce el peso del coche: ");
  scanf("%f", &coche.peso);

  printf("Introduce el número de asientos del coche: ");
  scanf("%d", &coche.numeroAsientos);

  printf("Los datos del coche son: \n");
  printf("Color: %s\n", coche.color);
  if(coche.tipo == 'g'){
  printf("Tipo de combustible: gasolina\n");
  }else if(coche.tipo == 'd'){
      printf("Tipo de combustible: diesel\n");
  }else{
      printf("Tipo de combustible: \n");
  }
  printf("Peso: %.2f KG\n", coche.peso);
  printf("Número de asientos: %d\n", coche.numeroAsientos);

  return 0;
}
