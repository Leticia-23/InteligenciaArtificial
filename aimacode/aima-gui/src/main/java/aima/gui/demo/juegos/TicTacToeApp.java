package aima.gui.demo.juegos;

import java.util.List;
import java.util.Scanner;

import aima.core.environment.tictactoe.TicTacToeGame;
import aima.core.environment.tictactoe.TicTacToeState;
import aima.core.search.adversarial.AdversarialSearch;
import aima.core.search.adversarial.AlphaBetaSearch;
import aima.core.search.adversarial.MinimaxSearch;
import aima.core.util.datastructure.XYLocation; 

public class TicTacToeApp {
	
	public static void main(String[] args) {
		TicTacToeGame juego = new TicTacToeGame();
		//MINIMAX
		MinimaxSearch<TicTacToeState, XYLocation, String> minimaxSearch =
				MinimaxSearch.createFor(juego);
		//Alfa-beta
		AlphaBetaSearch<TicTacToeState, XYLocation, String> alphabetaSearch =
				AlphaBetaSearch.createFor(juego);
		JuegaSoloMaquina(minimaxSearch, juego, true, "MINI MAX");
		System.out.println("\n\n");
		JuegaSoloMaquina(alphabetaSearch, juego, true, "ALFA-BETA");
		System.out.println("\n\n");
		Scanner teclado = new Scanner(System.in);
		JuegaContraUsuario(minimaxSearch, juego, "MINI MAX", teclado);
		System.out.println("\n\n");
		JuegaContraUsuario(minimaxSearch, juego, "ALFA-BETA", teclado);
		teclado.close();
	
	}
	
	
	public static void JuegaSoloMaquina(
			AdversarialSearch<TicTacToeState, XYLocation> search,
			TicTacToeGame game,
			boolean metrics,
			String Algoritmo) {
		System.out.println("\n" + Algoritmo + "con TIC TAC TOE Jugando solo maquina");
		TicTacToeState estado = game.getInitialState();
		while (!game.isTerminal(estado)) {
			XYLocation accion = search.makeDecision(estado);	
			//System.out.println("La acción elegida es " + accion);
			estado = game.getResult(estado, accion);
			String juega = estado.getValue(accion.getXCoOrdinate(), accion.getYCoOrdinate());
			System.out.println("Juega " + juega);
			if (metrics) {
				System.out.println("Metrics: " +
						search.getMetrics());
			}	
		}	
	}
	
	public static void JuegaContraUsuario(
			AdversarialSearch<TicTacToeState, XYLocation> search,
			TicTacToeGame game, String Algoritmo, Scanner teclado) {
		System.out.println("\n" +  Algoritmo + " DEMO con TIC TAC TOE Jugando contra máquina");
		
		TicTacToeState estado = game.getInitialState();
		
		while (!game.isTerminal(estado)) {
			System.out.println("======================");
			System.out.println(estado + "\n");
			//poner una acción que sí que deje hacer
			XYLocation accion = new XYLocation(-1,-1);
			if (estado.getPlayerToMove() == "X") {//humano
				List<XYLocation> acciones = game.getActions(estado);
				while (!acciones.contains(accion)) {
					System.out.println("Jugador Humano: "+
						"¿Cuál es tu acción?");
					System.out.print("Fila(0-2): ");
					int x = teclado.nextInt();
					System.out.print("Columna(0-2): ");
					int y = teclado.nextInt();
					accion = new XYLocation(y,x);
				}
			} else {
				 //computador
				System.out.println("La máquina juega, y elige:");
				accion = search.makeDecision(estado);
				System.out.println("Acción elegida: Coloco O " + accion);

			}
			estado = game.getResult(estado, accion);
		}
		System.out.println("GAME OVER: ");
		System.out.println(estado + "\n");
		if (estado.getUtility() == 1) {
			System.out.println("¡Gana el humano!");
		} else if (estado.getUtility() == 0) {
			System.out.println("¡Gana el computador!");
		} else {
			System.out.println("Ha habido un empate");
		}
		
	}



}
