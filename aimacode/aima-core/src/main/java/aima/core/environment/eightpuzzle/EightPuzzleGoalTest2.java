package aima.core.environment.eightpuzzle;

import aima.core.search.framework.GoalTest;

/**
 * @author Leticia Sánchez 
 */
public class EightPuzzleGoalTest2 implements GoalTest {
	static EightPuzzleBoard goal;
	
	public EightPuzzleGoalTest2() {
		goal = new EightPuzzleBoard(new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8 });
	}
	
	public EightPuzzleGoalTest2(EightPuzzleBoard goalState) {
		goal = goalState;
	}
	
	public boolean isGoalState(Object state) {
		EightPuzzleBoard board = (EightPuzzleBoard) state;
		return board.equals(goal);
	}
	
	static public EightPuzzleBoard getGoal() {
		return goal;
	}
}