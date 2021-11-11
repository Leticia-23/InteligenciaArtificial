package aima.gui.demo.search;

import aima.core.environment.eightpuzzle.*;
import aima.core.search.framework.GraphSearch;
import aima.core.search.framework.Problem;
import aima.core.search.framework.Search;
import aima.core.search.framework.SearchAgent;
import aima.core.search.framework.TreeSearch;
import aima.core.search.uninformed.*;
import aima.core.util.math.Biseccion;
import aima.core.search.informed.*;

/**
 * @author Leticia Sánchez (760549)
 */
public class EightPuzzlePract2 {
	

	public static void main(String[] args){
		
		System.out.println("------------------------------------------------------------------------------------------");
		System.out.format("||%4s||%6s%15s%18s||%18s%2s%19s||\n","", "", "Nodos Generados","", "", "b*", "");
		System.out.format("||%4s||%7s  |%7s  |%7s  |%7s  ||%7s  |%7s  |%7s  |%7s  ||\n","d", "BFS", "IDS", "A*h(1)", "A*h(2)"
				,"BFS", "IDS", "A*h(1)", "A*h(2)");
		System.out.println("------------------------------------------------------------------------------------------");
		System.out.println("------------------------------------------------------------------------------------------");
		
		//Experimentos para profundidad en el intervalo [2-24]
		for (int d = 2; d <= 24; d++) {
			int nG, ng_BFS = 0, ng_IDS = 0, ng_Misplaced = 0, ng_Manhattan = 0;
			double b_BFS = 0.0, b_IDS = 0.0, b_Misplaced = 0.0, b_Manhattan = 0.0;
			EightPuzzleBoard init = null, goal = null;
			
			//100 experimentos para profundidad = d
			for (int i = 0; i < 100; i++) {
				int depth = 0;
				
				do {
					//Generar estado inicial y final
					init = new EightPuzzleBoard(GenerateInitialEightPuzzleBoard.randomIni());
					goal = new EightPuzzleBoard(GenerateInitialEightPuzzleBoard.random(d, init));
					//Comprobar que la solución es óptima (d=pathCost)
					//Se comprueba para una búsqueda A* (tiene que serlo para esa,
					//de esta forma todas las búsquedas se hacen con los mismos estados
					//para poder realizar las comparaciones correctamente)
					depth = check_depth(d, init, goal, depth);	
					
				}while(d!=depth);
				
				
				//BFS
				nG = expNodes(init, goal, new BreadthFirstSearch(new GraphSearch()), d);
				ng_BFS += nG;
				b_BFS += expBiseccion(d,nG);
				
				//IDS
				if (d <=10 ) {
					nG = expNodes(init, goal, new IterativeDeepeningSearch(), d);
					ng_IDS += nG;
					b_IDS += expBiseccion(d,nG);
				}
				//A*h(1) -> MisplacedTilleHeuristicFunction
				nG = expNodes(init, goal,  new AStarSearch(new GraphSearch(),
						new MisplacedTilleHeuristicFunction2()), d);
				ng_Misplaced += nG;
				b_Misplaced += expBiseccion(d,nG);
				
				//A*h(2) -> ManhattanHeuristicFunction
				nG = expNodes(init, goal,  new AStarSearch(new GraphSearch(),
						new ManhattanHeuristicFunction2()), d);
				ng_Manhattan += nG;
				b_Manhattan += expBiseccion(d,nG);
				
			}
			ng_BFS /= 100;
			ng_Misplaced /= 100;
			ng_Manhattan /= 100;
			b_BFS /= 100;
			b_Misplaced /= 100;
			b_Manhattan /= 100;
			if (d <= 10) {
				ng_IDS /= 100;
				b_IDS /= 100;
				System.out.format("||%4s||%7s  |%7s  |%7s  |%7s  ||%7.4s  |%7.4s  |%7.4s  |%7.4s  ||\n",
						d, ng_BFS, ng_IDS, ng_Misplaced, ng_Manhattan, b_BFS, b_IDS, b_Misplaced, b_Manhattan);
			}
			else {
				System.out.format("||%4s||%7s  |%7s  |%7s  |%7s  ||%7.4s  |%7.4s  |%7.4s  |%7.4s  ||\n",
						d, ng_BFS, "---", ng_Misplaced, ng_Manhattan, b_BFS, "---", b_Misplaced, b_Manhattan);
			}
		}
		
		System.out.println("------------------------------------------------------------------------------------------");
	
	}
	
	public static int check_depth(int d, EightPuzzleBoard init, EightPuzzleBoard goal, int depth)  {
		//Comprobar que la solución es óptima (d=pathCost)
		//Se comprueba para una búsqueda A* (tiene que serlo para esa, para poder comparar el resto)
		try {
			Problem problem = new Problem(init, EightPuzzleFunctionFactory
					.getActionsFunction(),EightPuzzleFunctionFactory
					.getResultFunction(), new EightPuzzleGoalTest2(goal));
			
			Search search = new AStarSearch(new GraphSearch(),
					new ManhattanHeuristicFunction2());
			SearchAgent agent = new SearchAgent(problem, search);
			//Obter el coste del camino
			String pathcostM = agent.getInstrumentation().getProperty("pathCost");
			if (pathcostM!=null) depth = (int)Float.parseFloat(pathcostM);
			else depth = 0;
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return depth;			
	}
	
		
	public static int expNodes(EightPuzzleBoard init, EightPuzzleBoard goal, Search search,
			int depth) {
		int nd = 0;
		try {
			
			Problem problem = new Problem(init, EightPuzzleFunctionFactory
					.getActionsFunction(),EightPuzzleFunctionFactory
					.getResultFunction(), new EightPuzzleGoalTest2(goal));
			SearchAgent agent = new SearchAgent(problem, search);

			if(agent.getInstrumentation().getProperty("nodesGenerated") == null) nd =  0;
			else nd =  (int)Float.parseFloat(agent.getInstrumentation().getProperty("nodesGenerated"));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return nd;
		
		
	}
	
	public static double expBiseccion(int depth, int nodesGenerated) {
		Biseccion bis = new Biseccion();
		bis.setDepth(depth);
		bis.setGeneratedNodes(nodesGenerated);
		double b = bis.metodoDeBiseccion(1.000000000001, 4.0, 1E-12);
		return b;
	}

}