package aima.gui.demo.search;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.Set;

import aima.core.agent.Action;
import aima.core.environment.nqueens.AttackingPairsHeuristic;
import aima.core.environment.nqueens.NQueensBoard;
import aima.core.environment.nqueens.NQueensFitnessFunction;
import aima.core.environment.nqueens.NQueensFunctionFactory;
import aima.core.environment.nqueens.NQueensGoalTest;
import aima.core.search.framework.Problem;
import aima.core.search.framework.SearchAgent;
import aima.core.search.local.GeneticAlgorithm;
import aima.core.search.local.HillClimbingSearch;
import aima.core.search.local.Individual;
import aima.core.search.local.Scheduler;
import aima.core.search.local.SimulatedAnnealingSearch;
import aima.core.util.datastructure.XYLocation;

import java.text.DecimalFormat;

/**
 * @author Leticia Sánchez
 */
public class NQueensLocal {
private static final int _boardSize = 8;

private static final DecimalFormat df = new DecimalFormat("0.00");
	
	public static void main(String[] args) {

		nQueensHillClimbingSearch_Statistics(10000);
		System.out.println("\n\n");
		nQueensRandomRestartHillClimbing();
		System.out.println("\n\n");
		nQueensSimulatedAnnealing_Statistics(1000);
		System.out.println("\n\n");
		nQueensHillSimulatedAnnealingRestart();
		System.out.println("\n\n");
		nQueensGeneticAlgorithmSearch();

	}
	
	//Método para generar aleatoriamente un tablero inicial con numReinas reinas
	public static NQueensBoard generateRamdomNQueensBoard (int numReinas) {
		NQueensBoard board = new NQueensBoard(numReinas);
		for (int i = 0; i < numReinas; i++) {
			board.addQueenAt(new XYLocation(i, new Random().nextInt(numReinas)));
		}
		return board;
	}
	
	//Método para generar un hash con numElementos boards inciciales con numReinas reinas distintos
	public static Set<NQueensBoard> generateSetNQueensBoard(int numReinas, int numElementos) {
	    Set <NQueensBoard> set = new HashSet <NQueensBoard>();
	    while (set.size() < numElementos) {
	        set.add(generateRamdomNQueensBoard(numReinas));
	    }
		return set;
	}
	
	//Método para generar numExperimentos veces la búsqueda Hill Climbing 
	public static void nQueensHillClimbingSearch_Statistics(int numExperiments) {
		System.out.format("\nNQueensDemo HillClimbing con %s estados iniciales diferentes --> \n", numExperiments);
		
		double fallos = 0.0, exitos = 0.0, coste_fallos = 0.0, coste_exitos = 0.0;
		
		Set <NQueensBoard> set = generateSetNQueensBoard(8, numExperiments);
		NQueensBoard [] array = set.toArray(new NQueensBoard[set.size()]);
		
		for (int i = 0; i < numExperiments; i++) {
			try {
				Problem problem = new Problem(array[i],
						NQueensFunctionFactory.getCActionsFunction(),
						NQueensFunctionFactory.getResultFunction(),
						new NQueensGoalTest());
				HillClimbingSearch search = new HillClimbingSearch(
						new AttackingPairsHeuristic());
				SearchAgent agent = new SearchAgent(problem, search);
				
				if (search.getOutcome().toString().equals("SOLUTION_FOUND")) {
					exitos ++;
					coste_exitos += agent.getActions().size();
				}
				else {
					fallos ++;
					coste_fallos += agent.getActions().size();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		double coste_medio_fallos = 0.0, coste_medio_exitos = 0.0;
		
		if (fallos > 0 ) {
			fallos = fallos / numExperiments * 100;
			coste_medio_fallos = coste_fallos / numExperiments;
		} 
		if (exitos > 0 ) {
			exitos = exitos / numExperiments * 100;
			coste_medio_exitos = coste_exitos / numExperiments;
		} 
				
		System.out.println("Fallos: " +  df.format(fallos) + "%");
		System.out.println("Coste medio fallos:" + df.format(coste_medio_fallos));
		System.out.println("Exitos: " + df.format(exitos) + "%");
		System.out.println("Coste medio exitos: " + df.format(coste_medio_exitos));
		
	}
	
	public static void nQueensRandomRestartHillClimbing() {
		int fallos = 0;
		double coste_fallos = 0.0, coste_exito = 0.0;
		boolean done = false;
		HillClimbingSearch search = null;
		SearchAgent agent = null;

		while (!done) {
			try {
				Problem problem = new Problem(generateRamdomNQueensBoard(8),
						NQueensFunctionFactory.getCActionsFunction(),
						NQueensFunctionFactory.getResultFunction(),
						new NQueensGoalTest());
				search = new HillClimbingSearch(
						new AttackingPairsHeuristic());
				agent = new SearchAgent(problem, search);
				
				if (search.getOutcome().toString().equals("SOLUTION_FOUND")) {
					done = true;
					coste_exito = agent.getActions().size();
				} else {
					fallos ++;
					coste_fallos += agent.getActions().size();
				}				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		double coste_medio_fallos = 0.0;
		
		if (fallos > 0) {
			coste_medio_fallos = (coste_fallos / (double)(fallos));
		}
		
		System.out.println("Search Outcome = " + search.getOutcome().toString());
		System.out.println("Final State =\n" + search.getLastSearchState());
		System.out.println("Número de intentos: " + (fallos + 1));
		System.out.println("Fallos: " + fallos);
		System.out.println("Coste medio fallos: " + df.format(coste_medio_fallos));
		System.out.println("Coste éxito: " + df.format(coste_exito));
		System.out.println("Coste medio exitos: " + df.format(coste_exito/1));		
	}
	
	
	
	//Método para generar numExperimentos veces la búsqueda Simulated Annealing
	public static void  nQueensSimulatedAnnealing_Statistics(int numExperiments) {
		System.out.format("\nNQueensDemo  Simulated Annealing con %s estados iniciales diferentes --> \n", numExperiments);
		int k = 10;
		double lam = 0.1;
		int T = 1000;
		System.out.println("Parámetros Scheduler: Scheduler (" + k + "," + lam + "," + T + ");");
		
		double fallos = 0.0, exitos = 0.0, coste_fallos = 0.0, coste_exitos = 0.0;
		
		Set <NQueensBoard> set = generateSetNQueensBoard(8, numExperiments);
		NQueensBoard [] array = set.toArray(new NQueensBoard[set.size()]);
		
		for (int i = 0; i < numExperiments; i++) {
			try {
				Problem problem = new Problem(new NQueensBoard(_boardSize),
						NQueensFunctionFactory.getIActionsFunction(),
						NQueensFunctionFactory.getResultFunction(),
						new NQueensGoalTest());
				Scheduler scheduler = new Scheduler(k,lam,T);
				SimulatedAnnealingSearch search = new SimulatedAnnealingSearch(
						new AttackingPairsHeuristic(), scheduler);
				SearchAgent agent = new SearchAgent(problem, search);

				
				if (search.getOutcome().toString().equals("SOLUTION_FOUND")) {
					exitos ++;
					coste_exitos += agent.getActions().size();
				}
				else {
					fallos ++;
					coste_fallos += agent.getActions().size();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		double coste_medio_fallos = 0.0, coste_medio_exitos = 0.0;
		
		if (fallos > 0 ) {
			fallos = fallos / numExperiments * 100;
			coste_medio_fallos = coste_fallos / numExperiments;
		} 
		if (exitos > 0 ) {
			exitos = exitos / numExperiments * 100;
			coste_medio_exitos = coste_exitos / numExperiments;
		}
				
		System.out.println("Fallos: " +  df.format(fallos) + "%");
		System.out.println("Coste medio fallos:" + df.format(coste_medio_fallos));
		System.out.println("Exitos: " + df.format(exitos) + "%");
		System.out.println("Coste medio exitos: " + df.format(coste_medio_exitos));
		
	}
	
	public static void  nQueensHillSimulatedAnnealingRestart() {
		int fallos = 0;
		double coste_fallos = 0.0, coste_exito = 0.0;
		boolean done = false;
		int k = 10;
		double lam = 0.1;
		int T = 1000;
		SimulatedAnnealingSearch search = null;
		SearchAgent agent = null;
		

		while (!done) {
			try {
				Problem problem = new Problem(new NQueensBoard(_boardSize),
						NQueensFunctionFactory.getIActionsFunction(),
						NQueensFunctionFactory.getResultFunction(),
						new NQueensGoalTest());
				Scheduler scheduler = new Scheduler(k,lam,T);
				search = new SimulatedAnnealingSearch(
						new AttackingPairsHeuristic(), scheduler);
				agent = new SearchAgent(problem, search);
				
				if (search.getOutcome().toString().equals("SOLUTION_FOUND")) {
					done = true;
					coste_exito = agent.getActions().size();
				} else {
					fallos ++;
					coste_fallos += agent.getActions().size();
				}				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("Search Outcome = " + search.getOutcome().toString());
		System.out.println("Final State =\n" + search.getLastSearchState());
		System.out.println("Número de intentos: " + (fallos + 1));
		System.out.println("Fallos: " + fallos);
		System.out.println("Coste éxito: " + df.format(coste_exito));
	}

	public static void nQueensGeneticAlgorithmSearch() {
		System.out.println("\nGeneticAlgorithm  -->");
		double poblacion_inicial = 50;
		double probabilidad_mutacion = 0.15; 
		try {
			NQueensFitnessFunction fitnessFunction = new NQueensFitnessFunction();
			// Generate an initial population
			Set<Individual<Integer>> population = new HashSet<Individual<Integer>>();
			for (int i = 0; i < poblacion_inicial; i++) {
				population.add(fitnessFunction
						.generateRandomIndividual(_boardSize));
			}

			GeneticAlgorithm<Integer> ga = new GeneticAlgorithm<Integer>(
					_boardSize,
					fitnessFunction.getFiniteAlphabetForBoardOfSize(_boardSize),
					probabilidad_mutacion);

			// Run till goal is achieved
			Individual<Integer> bestIndividual = ga.geneticAlgorithm(
					population, fitnessFunction, fitnessFunction, 0L);

			System.out.println("Parámetros iniciales -> Poblacion: " + poblacion_inicial +	
					", Probabilidad mutación: " + probabilidad_mutacion);
			System.out.println("Mejor individuo =\n"
					+ fitnessFunction.getBoardForIndividual(bestIndividual));
			System.out.println("Tamaño tablero      = " + _boardSize);
			System.out.println("Fitness             = "
					+ fitnessFunction.getValue(bestIndividual));
			System.out.println("Es objetivo         = "
					+ fitnessFunction.isGoalState(bestIndividual));
			System.out.println("Tamaño de población = " + ga.getPopulationSize());
			System.out.println("Iteraciones         = " + ga.getIterations());
			System.out.println("Tiempo              = "
					+ ga.getTimeInMilliseconds() + "ms.");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
