package aima.gui.sudoku.csp;

import aima.core.search.csp.Assignment;
import aima.core.search.csp.ImprovedBacktrackingStrategy;
import aima.core.search.csp.SolutionStrategy;

public class SudokuApp {
	
	public static void main(String args[]) {
		String path = "/home/leti/1Universidad/IA/InteligenciaArtificial/TP6-1/";
		Sudoku [] lista = union(union(Sudoku.listaSudokus2(path + "easy50.txt"),
				Sudoku.listaSudokus2(path + "top95.txt")),
				Sudoku.listaSudokus2(path + "hardest.txt"));
		
		int resueltos = 0;
		
		for (Sudoku s : lista) {
			System.out.println("---------");
			s.imprimeSudoku();			
			if (!s.completo()) {
				SolutionStrategy strategy = new ImprovedBacktrackingStrategy(true, true, true, true);		
				System.out.println("SUDOKU INCOMPLETO - Resolviendo");
				double start = System.currentTimeMillis();
				Assignment sol = strategy.solve(new SudokuProblem(s.pack_celdasAsignadas()));
				double end = System.currentTimeMillis();
				System.out.println(sol);
				System.out.println("Time to solve = " + ((end - start) / 1000) + " segundos");
				System.out.println("SOLUCIÓN:");
				Sudoku solved = new Sudoku(sol);
				if (solved.correcto() ) {
					solved.imprimeSudoku();
					System.out.println("Sudoku solucionado correctamente");
					resueltos++;
				}
					
			}
		}
		System.out.println("+++++++++");
		System.out.printf("Se han resuelto %s sudokus\n\n", resueltos);
	}
	
	

	private static Sudoku[] union(Sudoku[] s1, Sudoku[] s2) {
		Sudoku[] lista = new Sudoku[s1.length + s2.length];
		int i = 0;
		for (Sudoku s : s1) {
			lista[i] = s;
			i++;
		}
		for (Sudoku s : s2) {
			lista[i] = s;
			i++;
		}
		return lista;
	}

}
