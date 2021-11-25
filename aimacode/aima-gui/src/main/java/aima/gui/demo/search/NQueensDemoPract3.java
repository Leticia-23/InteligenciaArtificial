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
import aima.core.search.local.SimulatedAnnealingSearch;
import aima.core.util.datastructure.XYLocation;

import java.text.DecimalFormat;

public class NQueensDemoPract3 {
private static final int _boardSize = 8;

private static final DecimalFormat df = new DecimalFormat("0.00");
	
	public static void main(String[] args) {

		nQueensHillClimbingSearch_Statistics(10000);
		System.out.println("\n\n");
		nQueensRandomRestartHillClimbing();
		//nQueensSimulatedAnnealingSearch();
		//nQueensGeneticAlgorithmSearch();
	}
	

	private static void nQueensSimulatedAnnealingSearch() {
		System.out.println("\nNQueensDemo Simulated Annealing  -->");
		try {
			Problem problem = new Problem(new NQueensBoard(_boardSize),
					NQueensFunctionFactory.getIActionsFunction(),
					NQueensFunctionFactory.getResultFunction(),
					new NQueensGoalTest());
			SimulatedAnnealingSearch search = new SimulatedAnnealingSearch(
					new AttackingPairsHeuristic());
			SearchAgent agent = new SearchAgent(problem, search);

			System.out.println();
			printActions(agent.getActions());
			System.out.println("Search Outcome=" + search.getOutcome());
			System.out.println("Final State=\n" + search.getLastSearchState());
			printInstrumentation(agent.getInstrumentation());
		} catch (Exception e) {
			e.printStackTrace();
		}
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
			coste_medio_fallos = coste_fallos / numExperiments;
		} 
		if (exitos > 0 ) {
			coste_medio_exitos = coste_exitos / numExperiments;
		} 
				
		System.out.println("Fallos: " +  df.format(fallos));
		System.out.println("Coste medio fallos:" + df.format(coste_medio_fallos));
		System.out.println("Exitos: " + df.format(exitos));
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

	public static void nQueensGeneticAlgorithmSearch() {
		System.out.println("\nNQueensDemo GeneticAlgorithm  -->");
		try {
			NQueensFitnessFunction fitnessFunction = new NQueensFitnessFunction();
			// Generate an initial population
			Set<Individual<Integer>> population = new HashSet<Individual<Integer>>();
			for (int i = 0; i < 50; i++) {
				population.add(fitnessFunction
						.generateRandomIndividual(_boardSize));
			}

			GeneticAlgorithm<Integer> ga = new GeneticAlgorithm<Integer>(
					_boardSize,
					fitnessFunction.getFiniteAlphabetForBoardOfSize(_boardSize),
					0.15);

			// Run for a set amount of time
			Individual<Integer> bestIndividual = ga.geneticAlgorithm(
					population, fitnessFunction, fitnessFunction, 1000L);

			System.out.println("Max Time (1 second) Best Individual=\n"
					+ fitnessFunction.getBoardForIndividual(bestIndividual));
			System.out.println("Board Size      = " + _boardSize);
			System.out.println("# Board Layouts = "
					+ (new BigDecimal(_boardSize)).pow(_boardSize));
			System.out.println("Fitness         = "
					+ fitnessFunction.getValue(bestIndividual));
			System.out.println("Is Goal         = "
					+ fitnessFunction.isGoalState(bestIndividual));
			System.out.println("Population Size = " + ga.getPopulationSize());
			System.out.println("Itertions       = " + ga.getIterations());
			System.out.println("Took            = "
					+ ga.getTimeInMilliseconds() + "ms.");

			// Run till goal is achieved
			bestIndividual = ga.geneticAlgorithm(population, fitnessFunction,
					fitnessFunction, 0L);

			System.out.println("");
			System.out.println("Goal Test Best Individual=\n"
					+ fitnessFunction.getBoardForIndividual(bestIndividual));
			System.out.println("Board Size      = " + _boardSize);
			System.out.println("# Board Layouts = "
					+ (new BigDecimal(_boardSize)).pow(_boardSize));
			System.out.println("Fitness         = "
					+ fitnessFunction.getValue(bestIndividual));
			System.out.println("Is Goal         = "
					+ fitnessFunction.isGoalState(bestIndividual));
			System.out.println("Population Size = " + ga.getPopulationSize());
			System.out.println("Itertions       = " + ga.getIterations());
			System.out.println("Took            = "
					+ ga.getTimeInMilliseconds() + "ms.");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void printInstrumentation(Properties properties) {
		Iterator<Object> keys = properties.keySet().iterator();
		while (keys.hasNext()) {
			String key = (String) keys.next();
			String property = properties.getProperty(key);
			System.out.println(key + " : " + property);
		}

	}

	private static void printActions(List<Action> actions) {
		for (int i = 0; i < actions.size(); i++) {
			String action = actions.get(i).toString();
			System.out.println(action);
		}
	}

}