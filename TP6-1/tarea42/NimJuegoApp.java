package aima.gui.demo.juegos;

import aima.core.search.adversarial.AlphaBetaSearch;
import aima.core.search.adversarial.IterativeDeepeningAlphaBetaSearch;
import aima.core.search.adversarial.MinimaxSearch;

import java.util.List;
import java.util.Scanner;

public class NimJuegoApp {

    public static void main(String[] args) {
        NIMJuego juego = new NIMJuego(20);
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
            if (estado.get(0) == 0) {
                //human
                List<Integer> acciones = juego.getActions(estado);
                Scanner teclado = new Scanner(System.in);
                while (!acciones.contains(accion)) {
                    System.out.println("Jugador Humano: ¿Cuál es tu acción?");
                    accion = teclado.nextInt();
                }
            } else {
                //machine
                System.out.println("Computador elige acción:");
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

    }
}
