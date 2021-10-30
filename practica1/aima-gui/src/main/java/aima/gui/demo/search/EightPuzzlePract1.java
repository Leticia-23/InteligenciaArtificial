package aima.gui.demo.search;

import aima.core.environment.eightpuzzle.EightPuzzleBoard;
import aima.core.environment.eightpuzzle.EightPuzzleFunctionFactory;
import aima.core.environment.eightpuzzle.EightPuzzleGoalTest;
import aima.core.search.framework.GraphSearch;
import aima.core.search.framework.Problem;
import aima.core.search.framework.Search;
import aima.core.search.framework.SearchAgent;
import aima.core.search.framework.TreeSearch;
import aima.core.search.uninformed.*;

/**
 * @author Ravi Mohan
 * 
 */

public class EightPuzzlePract1 {
	static EightPuzzleBoard boardWithThreeMoveSolution = new EightPuzzleBoard(
			new int[] { 1, 2, 5, 3, 4, 0, 6, 7, 8 });;

	static EightPuzzleBoard random1 = new EightPuzzleBoard(new int[] { 1, 4, 2,
			7, 5, 8, 3, 0, 6 });

	static EightPuzzleBoard extreme = new EightPuzzleBoard(new int[] { 0, 8, 7,
			6, 5, 4, 3, 2, 1 });

	public static void main(String[] args) {
		System.out.format("%8s|%11s|%11s|%11s|%11s|%11s\n", "Problema", "Profundidad", "Expand", "Q.Size", "MasQS",
				"tiempo");
		
		//Solución a 3 pasos
		//Búsqueda en anchura (BFS)
		eightPuzzleSearch(new BreadthFirstSearch(new GraphSearch()), boardWithThreeMoveSolution, "BFS-G-3");
		eightPuzzleSearch(new BreadthFirstSearch(new TreeSearch()), boardWithThreeMoveSolution, "BFS-T-3");
		//Búsqueda en profundidad (DFS)
		eightPuzzleSearch(new DepthFirstSearch(new GraphSearch()), boardWithThreeMoveSolution, "DFS-G-3");
		System.out.format("%8s|%11s|%11s|%11s|%11s|%11s\n", "DFS-T-3", "---", "---", "---","---", "(1)");
		//Búsqueda en profundidad limitada (DLS)
		eightPuzzleSearch(new DepthLimitedSearch(9), boardWithThreeMoveSolution, "DLS-9-3");
		eightPuzzleSearch(new DepthLimitedSearch(3), boardWithThreeMoveSolution, "DLS-3-3");
		//Búsqueda en profundidad iterativa (IDS)
		eightPuzzleSearch(new IterativeDeepeningSearch(), boardWithThreeMoveSolution, "IDS-3");
		//Búsqueda en coste uniforme (UCS)
		eightPuzzleSearch(new UniformCostSearch(new GraphSearch()), boardWithThreeMoveSolution, "UCS-G-3");
		eightPuzzleSearch(new UniformCostSearch(new TreeSearch()), boardWithThreeMoveSolution, "UCS-T-3");
		
		//Solución a 9 pasos
		//Búsqueda en anchura (BFS)
		eightPuzzleSearch(new BreadthFirstSearch(new GraphSearch()), random1, "BFS-G-9");
		eightPuzzleSearch(new BreadthFirstSearch(new TreeSearch()), random1, "BFS-T-9");
		//Búsqueda en profundidad (DFS)
		eightPuzzleSearch(new DepthFirstSearch(new GraphSearch()), random1, "DFS-G-9");
		System.out.format("%8s|%11s|%11s|%11s|%11s|%11s\n", "DFS-T-9", "---", "---", "---","---", "(1)");
		//Búsqueda en profundidad limitada (DLS)
		eightPuzzleSearch(new DepthLimitedSearch(9), random1, "DLS-9-9");
		eightPuzzleSearch(new DepthLimitedSearch(3), random1, "DLS-3-9");
		//Búsqueda en profundidad iterativa (IDS)
		eightPuzzleSearch(new IterativeDeepeningSearch(), random1, "IDS-9");
		//Búsqueda en coste uniforme (UCS)
		eightPuzzleSearch(new UniformCostSearch(new GraphSearch()), random1, "UCS-G-9");
		eightPuzzleSearch(new UniformCostSearch(new TreeSearch()), random1, "UCS-T-9");
		
		//Solución a 30 pasos
		//Búsqueda en anchura (BFS)
		eightPuzzleSearch(new BreadthFirstSearch(new GraphSearch()), extreme, "BFS-G-30");
		System.out.format("%8s|%11s|%11s|%11s|%11s|%11s\n", "DFS-T-30", "---", "---", "---","---", "(2)");
		//Bísqueda en profundidad (DFS)
		eightPuzzleSearch(new DepthFirstSearch(new GraphSearch()), extreme, "DFS-G-30");
		System.out.format("%8s|%11s|%11s|%11s|%11s|%11s\n", "DFS-T-30", "---", "---", "---","---", "(1)");
		//Búsqueda en profundidad limitada (IDS)
		eightPuzzleSearch(new DepthLimitedSearch(9), extreme, "DLS-9-30");
		eightPuzzleSearch(new DepthLimitedSearch(3), extreme, "DLS-3-30");
		//Búsqueda en profundidad iterativa
		System.out.format("%8s|%11s|%11s|%11s|%11s|%11s\n", "IDS-30", "---", "---", "---","---", "(1)");
		//Búsqueda en coste uniforme
		eightPuzzleSearch(new UniformCostSearch(new GraphSearch()), extreme, "UCS-G-30");
		System.out.format("%8s|%11s|%11s|%11s|%11s|%11s\n", "UCS-T-30", "---", "---", "---","---", "(2)");
		

	}
	
	private static void eightPuzzleSearch(Search search, EightPuzzleBoard initialState, String strategy) {
		try {
			Problem problem = new Problem(initialState, EightPuzzleFunctionFactory
					.getActionsFunction(), EightPuzzleFunctionFactory
					.getResultFunction(), new EightPuzzleGoalTest());
			
			long init = System.currentTimeMillis();
			SearchAgent agent = new SearchAgent(problem, search);
			long end = System.currentTimeMillis();
			
			//Visualizar métricas de la búsqueda
			int depth, expandedNodes, queueSize, maxQueueSize;
			
			//Profundidad(coste de la solución)
			String pathcostM = agent.getInstrumentation().getProperty("pathCost");
			if (pathcostM!=null) depth = (int)Float.parseFloat(pathcostM);
			else depth = 0;
			//Expand Nodos expandidos)
			if (agent.getInstrumentation().getProperty("nodesExpanded")==null) expandedNodes= 0;
			else expandedNodes =
					(int)Float.parseFloat(agent.getInstrumentation().getProperty("nodesExpanded"));
			//Q.size (Tamaño de la frontera)
			if (agent.getInstrumentation().getProperty("queueSize")==null) queueSize=0;
			else queueSize =
					(int)Float.parseFloat(agent.getInstrumentation().getProperty("queueSize"));
			//MaxQs (Tamaño máximo de la frontera)
			if (agent.getInstrumentation().getProperty("maxQueueSize")==null) maxQueueSize= 0;
			else maxQueueSize =
					(int)Float.parseFloat(agent.getInstrumentation().getProperty("maxQueueSize"));
			//Timepo de ejecución (milliseconds)
			long time = end - init;
			
			System.out.format("%8s|%11s|%11s|%11s|%11s|%11s\n", strategy, depth, expandedNodes, queueSize,
					maxQueueSize, time);

		} catch (Exception e) {
			e.printStackTrace();
		}
			
	}

}