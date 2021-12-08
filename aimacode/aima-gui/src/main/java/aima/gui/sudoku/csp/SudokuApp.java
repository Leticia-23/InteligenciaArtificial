package aima.gui.sudoku.csp;

public class SudokuApp {
	
	public static void main() {
		String path = "/home/leti/1Universidad/IA/InteligenciaArtificial/TP6-1";
		Sudoku [] lista = union(union(Sudoku.listaSudokus2(path + "easy50.txt"),
				Sudoku.listaSudokus2(path + "top95.txt")),
				Sudoku.listaSudokus2(path + "hardest.txt"));
	}
	
	

	private static Sudoku[] union(Sudoku[] s1, Sudoku[] s2) {
		return null;
	}

}
