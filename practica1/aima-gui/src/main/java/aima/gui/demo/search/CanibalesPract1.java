package aima.gui.demo.search;

import java.util.List;

import aima.core.agent.Action;
import aima.core.environment.Canibales.CanibalesBoard;
import aima.core.environment.Canibales.CanibalesFunctionFactory;
import aima.core.environment.Canibales.CanibalesGoalTest;
import aima.core.search.framework.GraphSearch;
import aima.core.search.framework.Problem;
import aima.core.search.framework.ResultFunction;
import aima.core.search.framework.Search;
import aima.core.search.framework.SearchAgent;
import aima.core.search.framework.TreeSearch;
import aima.core.search.uninformed.*;

public class CanibalesPract1 {
	static CanibalesBoard initial = new CanibalesBoard(
			new int[] { 3,3,0,0,0});
	
	public static void main(String[] args) {
		//Búsqueda primero en anchura (BFS)
		System.out.println("Misioneros y canibales BFS en grafo -->");
		CanibalesSearch(new BreadthFirstSearch(new GraphSearch()), initial);
		System.out.println("\n\nMisioneros y canibales BFS en árbol -->");
		CanibalesSearch(new BreadthFirstSearch(new TreeSearch()), initial);

		//Búsqueda primero en profundidad (DFS)
		System.out.println("\n\nMisioneros y canibales DFS en grafo -->");
		CanibalesSearch(new DepthFirstSearch(new GraphSearch()), initial);
		System.out.println("\n\nMisioneros y canibales DFS en árbol -->");
		//CanibalesSearch(new DepthFirstSearch(new TreeSearch()), initial);
		//Tras estar ejecutando más de 1 min no encuentra solución y suspendo ejecución
		System.out.println("Ejecución no realizada -> complejidad temporal");
		
		//Búsqueda en profundidad limitada (DLS)
		System.out.println("\n\nMisioneros y canibales DLS(11)-->");
		CanibalesSearch(new DepthLimitedSearch(11), initial);
		
		//Búsqueda en profundidad iterativa (IDS)
		System.out.println("\n\nMisioneros y canibales IDLS -->"); 
		CanibalesSearch(new IterativeDeepeningSearch(), initial);
		
		//Búsqueda con coste uniforme (UCS)
		System.out.println("\n\nMisioneros y canibales UCS en grafo -->");
		CanibalesSearch(new UniformCostSearch(new GraphSearch()), initial);
		System.out.println("\n\nMisioneros y canibales UCS en arbol -->");
		CanibalesSearch(new UniformCostSearch(new TreeSearch()), initial);
		
	}

	private static void CanibalesSearch(Search search, CanibalesBoard initialState) {
		try {	
			Problem problem = new Problem(initialState, CanibalesFunctionFactory
					.getActionsFunction(), CanibalesFunctionFactory
					.getResultFunction(), new CanibalesGoalTest());
			
			long init = System.currentTimeMillis();
			SearchAgent agent = new SearchAgent(problem, search);
			long end = System.currentTimeMillis();
			
			double depth;
			int expandedNodes, queueSize, maxQueueSize;
			
			//Profundidad(coste de la solución)
			String pathcostM = agent.getInstrumentation().getProperty("pathCost");
			if (pathcostM!=null) depth = (int)Float.parseFloat(pathcostM);
			else depth = 0;
			//Expand Nodos expandidos)
			if(agent.getInstrumentation().getProperty("nodesExpanded") == null) expandedNodes = 0;
			else expandedNodes = (int)Float.parseFloat(agent.getInstrumentation().getProperty("nodesExpanded"));
			//Q.size (Tamaño de la frontera)
			if(agent.getInstrumentation().getProperty("queueSize") == null) queueSize = 0;
			else queueSize = (int)Float.parseFloat(agent.getInstrumentation().getProperty("queueSize"));
			//MaxQs (Tamaño máximo de la frontera)
			if(agent.getInstrumentation().getProperty("maxQueueSize") == null) maxQueueSize = 0;
			else maxQueueSize = (int)Float.parseFloat(agent.getInstrumentation().getProperty("maxQueueSize"));
			//Timepo de ejecución (milliseconds)
			long time = end - init;
			
			System.out.println("pathCost: " + depth);
			System.out.println("nodesExpanded: " + expandedNodes);
			System.out.println("queueSize: " + queueSize);
			System.out.println("maxQueueSize: " + maxQueueSize);
			System.out.format("Tiempo: %d mls",time);	
			System.out.println("\nSOLUCIÓN:");
			System.out.println("GOAL STATE");
			int [] objetivo = {0,0,1,3,3};
			System.out.format("%18s %s\n", " ", new CanibalesBoard(objetivo));
			System.out.println("CAMINO ENCONTRADO");
			executeActions(agent.getActions(), problem);	
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}


	public static void executeActions(List<Action> actions, Problem problem) {
        Object initialState = problem.getInitialState();
	    ResultFunction resultFunction = problem.getResultFunction();
	        
	    Object state = initialState;
	    System.out.format("%19s", "ESTADO INICIAL");
	    System.out.println(state);
	        
	    for(Action action : actions) {
	    	 System.out.format("%19s", action.toString());
	         state = resultFunction.result(state, action);
	         System.out.println(state);
	        }
	    }
}
