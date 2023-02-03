#include <stdio.h>
#include <stdlib.h>
#include <errno.h>


//Funcion para desencriptar bloque de 64 bits
void desencriptar(unsigned int *v, unsigned int *k){
	const unsigned int delta = 0x9e3779b9;
	int i, sum= 0xC6EF3720;
	for(i=0; i < 32; i++){
	//<< y >> tienen menos precedencia que +
	// al compilar sale un warning si no se usan parentesis
		v[1] -= ((v[0]<<4) + k[2])^(v[0]+sum) ^ ((v[0]>>5) + k[3]);
		v[0] -= ((v[1]<<4) + k[0])^(v[1]+sum) ^ ((v[1]>>5) + k[1]);
		sum -=delta;
	}
}




/*Hay que pasar el nombre del fichero a descifrar y el del
  fichero descifrado como parametro */

int main(int argc, char *argv[]){

	FILE *src, *dst;
	unsigned int *buffer, dstSize, srcSize;
	int i,result;
	unsigned int k [4]= {128, 129,130,131};
	//Comprobamos que tenemos los nombres de los dos ficheros
	if(argc<3){
		perror("Argumentos insuficientes\n");
		exit(-1);
	}
	//argv[0]: nombre del programa
	//argv[1]: nombre del fichero cifrado -> src
	//argv[2]: nombre del fichero descifrado -> dst

	printf("src: %s dst: %s\n", argv[1], argv[2]);
	// Abrir en modo lectura el fichero a descifrar (src)
	if((src=fopen(argv[1],"rb"))==NULL){
		perror("Error al abrir fichero a descifrar\n");
		exit(-1);
	}
	// Abrir en modo escritura el fichero descifrado (dst)
	if((dst=fopen(argv[2],"wb"))==NULL){
		perror("Error al abrir fichero descifrado\n");
		fclose(src);
		exit(-1);
	}

	//leer tamanio fichero desencriptado -- en bytes
	fread(&dstSize, sizeof(unsigned int),1,src);
	//Reservamos memoria dinamica para cargar el resto del contenido de src
	//El tamanio de src es el multiplo de 8 mayor o igual a dstSize
	srcSize = dstSize + (dstSize%8);
	if((buffer = (unsigned int *)malloc(srcSize))==NULL){
		perror("No se puede reservar memoria\n");
		fclose(src);
		fclose(dst);
		exit(-1);
	}else{
		//Leemos el contenido
		result = fread(buffer, sizeof(char), srcSize,src);
		printf("Almacenamos en buffer %d\n", result);
		//desencriptamos todo
		for(i =0; i< (srcSize/4); i+=2) //srcSize/4 nos da el numero de enteros que se almacenan en buffer
			desencriptar(&(buffer[i]),k);

		//escribimos en dst
		result = fwrite(buffer,sizeof(char),dstSize,dst);
		printf("Almacenamos en dst %d\n", result);
		//Cerrar ficheros
		fclose(src);
		fclose(dst);
		//liberar memoria dinamica
		free(buffer);
	}

}
