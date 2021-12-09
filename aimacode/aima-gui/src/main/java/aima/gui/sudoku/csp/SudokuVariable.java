package aima.gui.sudoku.csp;

import java.util.Objects;

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(value, x, y);
		return result;
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
