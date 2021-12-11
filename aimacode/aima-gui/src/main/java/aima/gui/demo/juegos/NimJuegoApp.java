package aima.gui.demo.juegos;

import aima.core.search.adversarial.AlphaBetaSearch;
import aima.core.search.adversarial.MinimaxSearch;

import java.util.List;
import java.util.Scanner;

public class NimJuegoApp {
	public static void main(String[] args) {
		Scanner teclado = new Scanner(System.in);
		System.out.println("¿Total de cerillas en la caja?");
		int size = teclado.nextInt();
		System.out.println("¿Cuantas cerillas se pueden quitar en cada turno?");
		int cerillas = teclado.nextInt();
		System.out.println("¿Empieza computador o humano? (O:Humano, 1:Computador)");
		int max = teclado.nextInt(); //max será el jugador elegido
		
		NIMJuego juego = new NIMJuego(size, max, cerillas);
		//MINIMAX
		MinimaxSearch<List<Integer>, Integer, Integer> minimaxSearch =
				MinimaxSearch.createFor(juego);
		//Alfa-beta
		AlphaBetaSearch<List<Integer>, Integer, Integer> alphabetaSearch =
				AlphaBetaSearch.createFor(juego);
		
		List<Integer> estado = juego.getInitialState();
		while (!juego.isTerminal(estado)) {
			System.out.println("======================");
			System.out.println(estado);
			int accion = -1;
			if (estado.get(0) == 0) {//humano
				List<Integer> acciones = juego.getActions(estado);
				while (!acciones.contains(accion)) {
					System.out.println("Jugador Humano: "+
						"¿Cuál es tu acción?");
				accion = teclado.nextInt();
				}
			} else {
				 //computador
				System.out.println("Jugador computador, elijo acción.");
				accion = minimaxSearch.makeDecision(estado);
				System.out.println("Metricas para Minimax : " +
				minimaxSearch.getMetrics());
				alphabetaSearch.makeDecision(estado);
				System.out.println("Metricas para Alfa-Beta : " +
				alphabetaSearch.getMetrics());
			}
			System.out.println("La acción elegida es " + accion);
			estado = juego.getResult(estado, accion);
		}
		System.out.print("GAME OVER: ");
		if (estado.get(0) == 0)
			System.out.println("¡Gana el humano!");
		else
			System.out.println("¡Gana el computador!");
		
		teclado.close();
	}
}
