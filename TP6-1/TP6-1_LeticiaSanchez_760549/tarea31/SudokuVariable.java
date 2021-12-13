package aima.gui.sudoku.csp;

import aima.core.search.csp.Variable;

public class SudokuVariable extends Variable{
	private int x;
	private int y;
	private int value;
	

	public SudokuVariable(String name, int x, int y) {
		super(name);
		this.x = x;
		this.y = y;
		this.value = 0;
	}

	public void setValue(int value) {
		this.value = value;	
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public int getValue() {
		return this.value;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		SudokuVariable other = (SudokuVariable) obj;
		return value == other.value && x == other.x && y == other.y;
	}
	

}
