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
		// TODO Auto-generated method stub
		return this.x;
	}

	public int getY() {
		// TODO Auto-generated method stub
		return this.y;
	}

	public int getValue() {
		// TODO Auto-generated method stub
		return this.value;
	}

}
