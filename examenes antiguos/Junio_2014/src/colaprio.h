#ifndef __COLAPRIO__
#define __COLAPRIO__

// Definición del tipo ColaPrio
typedef struct TProc *TColaPrio;

struct TProc{
	int id;
	int prio;
	TColaPrio sig;
};

void Crear_Cola(char *nombre, TColaPrio *cp);
void Mostrar(TColaPrio cp);
void Destruir(TColaPrio *cp);
void Ejecutar_Max_Prio(TColaPrio *cp);
void Ejecutar(TColaPrio *cp, int prio);

#endif
