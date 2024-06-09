package Parcial_2024.esqueleto;

import java.util.*;

public class Torneo {
	private int totalJug; // Numero total de jugadores en el torneo en un momento dado
	private int puntos[]; // Array que guarda los puntos que va obteniendo cada jugador
	private int partido[] = new int[4];// Array que guarda el id de los cuatro jugadores que están jugando un partido
	private Random rnd = new Random();
	private int numJug = 0;

	private boolean jugandoPartido = false;
	private boolean ultimoEnEntra = false;

	public Torneo(int totalJug) {
		this.totalJug = totalJug;
		puntos = new int[totalJug];
		for (int i = 0; i < 4; i++) {
			partido[i] = -1;
		}

		for (int i = 0; i < totalJug; i++) {
			puntos[i] = 0;
		}
	}

	/*
	 * Un jugador llama a este método para entrar a la pista de padel y obtener un
	 * puesto
	 * en el siguiente partido. Tendrá que esperar si ya se está jugando otro
	 * partido.
	 * 
	 * Cuando el jugador consigue un puesto en el siguiente partido registra su id y
	 * tendrá que
	 * esperar hasta que le avisen de que puede continuar. Le avisaran de que puede
	 * continuar por dos
	 * motivos distintos:
	 * 
	 * (1) se ha formado un equipo de cuatro jugadores y pueden jugar el partido. En
	 * este
	 * caso el método devuelve true para indicar que se ha podido formar el equipo
	 * de cuatro, o
	 * 
	 * (2) quedan menos de 4 jugadores en el torneo y el partido no se puede
	 * celebrar porque el torneo ha finalizado. En ese caso devuelve false para
	 * indicar que no se ha
	 * podido formar un equipo de cuatro porque ha finalizado el torneo.
	 * 
	 * Cada vez que llega un jugador se guardará su id. Los dos primeros id son los
	 * de los jugadores que
	 * forman la primera pareja y los otros dos son los que forman la segunda
	 * pareja.
	 * 
	 */
	public synchronized boolean entrarEnPista(int id) throws InterruptedException {
        while (jugandoPartido) {
            wait();
        }

        numJug++;
        partido[numJug - 1] = id; // Se registra el id del jugador en el partido
        System.out.println("El jugador " + id + " llega al partido.");

        if (numJug == 4) {
            jugandoPartido = true;
            notifyAll();
            return true;
        }

        while (numJug < 4 && totalJug >= 4) {
            wait();
        }

        if (numJug < 4 && totalJug < 4) {
            notifyAll();
            return false;
        }

        return true;
    }

	

	/*
	 * Este método lo llama cada jugador cuando finaliza su partido.
	 * 
	 * Tendrá que decidir si quiere seguir jugando el torneo o no. Independiente de
	 * lo que decida tendrá
	 * que hacer lo siguiente:
	 * 
	 * - Si no es el último jugador en salir del partido se esperará hasta que el
	 * último le avise.
	 * 
	 * - Si es el ultimo jugador, hará lo siguiente:
	 * 
	 * (1) informará de qué pareja ha ganado el partido mostrando por pantalla los
	 * identificadores de los
	 * jugadores que han ganado y sumando los puntos correspodientes. El partido lo
	 * ganará de forma aleatoria
	 * la primera o la segunda pareja.
	 * (2) comprobará si el número de jugadores que quedan en el torneo son
	 * suficientes para jugar un partido.
	 * - Si no lo son, entonces informa de que el torneo ha finalizado, averigua
	 * quién ha sido el ganador, informa de
	 * quién ha sido el ganador mostrando la información por pantalla, sale él del
	 * torneo independiente de la decisión
	 * que hubiera tomado antes y avisa al resto de jugadores que aún quedan en el
	 * torneo de que el torneo ha
	 * finalizado y deben salir porque ya no va a ser posible formar otro equipo
	 * para jugar.
	 * - Si el número de jugadores que quedan en el torneo son suficinetes para
	 * jugar un partido, entonces este jugador
	 * avisa al resto de jugadores esperando de que pueden continuar.
	 */
	public synchronized boolean finPartido(int id) throws InterruptedException {
        boolean seguirJugando = seguirJugando(); // Decide si seguir jugando o no
        numJug--;

        if (numJug > 0) { // No es el último jugador
            wait();

            if (totalJug < 4) { // El número de jugadores es menor a 4, por tanto, se acaba el torneo
                totalJug--;
                notifyAll();
                return true; // Debe de salir
            } else {
                if (!seguirJugando) {
                    totalJug--;
                }
                return seguirJugando;
            }

        } else { // El último jugador en salir de la pista
            totalJug--;

            // (1)
            int parejaGanadaora = parejaGanadora();
            if (parejaGanadaora == 0) {
                puntos[partido[0]] += 10;
                puntos[partido[1]] += 10;
                System.out.println("Han ganado los jugadores " + partido[0] + " y " + partido[1]);
            } else {
                puntos[partido[2]] += 10;
                puntos[partido[3]] += 10;
                System.out.println("Han ganado los jugadores " + partido[2] + " y " + partido[3]);
            }

            mostrarPuntos();

            // (2)
            if (totalJug >= 4) {
                notifyAll();
                jugandoPartido = false;
            } else {
                System.out.println("No hay jugadores suficientes: Torneo finalizado!");
                System.out.println("******************************************");
                int ganador = obtenerGanador();

                if (ganador != -1) {
                    System.out.println("El jugador " + ganador + " es el ganador del torneo!");
                } else {
                    System.out.println("Se ha producido un empate");
                }
                notifyAll();
            }

            if (totalJug >= 4) {
                System.out.println("Quedan " + totalJug + " jugadores en el torneo.");
                System.out.println("*********************************************************");
            }

            return true;
        }
    }

	/*
	 * Método privado que de forma aleatoria indica quien ha sido la pareja ganadora
	 * 
	 * Devuelve 0 si ha ganado la primera pareja
	 * Devuelve 1 si ha ganado la segunda pareja
	 * 
	 */
	private int parejaGanadora() {
		return rnd.nextInt(2);
	}

	/*
	 * Método privado que de forma aleatoria indica si un jugador decide seguir
	 * jugando o no
	 * 
	 * Devuelve true si decide salir del juego
	 * Devuelve false en caso contrario
	 * 
	 */
	private boolean seguirJugando() {
		return rnd.nextBoolean();
	}

	/*
	 * Método privado que muestra por pantalla los puntos de
	 * todos los jugadores
	 */
	private void mostrarPuntos() {
		System.out.println("PUNTOS: " + Arrays.toString(puntos));
	}

	private void mostrarJugadores() {
		System.out.println("JUGADORES: " + Arrays.toString(partido));
	} 

	/*
	 * Método privado que obtiene el ganador del torneo.
	 * 
	 * Devuelve el id del ganador en caso de que haya un único jugador con
	 * puntuación máxima
	 * Devuelve -1 si no hay un ganador único y por tanto hay empate
	 * 
	 */
	private int obtenerGanador() {
		int id_max = -1;
		int valor_max = 0;

		for (int i = 0; i < puntos.length; i++) {
			if (puntos[i] > valor_max) {
				valor_max = puntos[i];
				id_max = i;
			} else if (puntos[i] == valor_max) {
				id_max = -1;
			}
		}
		return id_max;
	}
}