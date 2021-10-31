package aima.core.environment.Canibales;

import java.util.LinkedHashSet;
import java.util.Set;

import aima.core.agent.Action;
import aima.core.search.framework.ActionsFunction;
import aima.core.search.framework.ResultFunction;

/**
 * @author Leticia Sánchez (760549)
 */
public class CanibalesFunctionFactory {
	private static ActionsFunction _actionsFunction = null;
	private static ResultFunction _resultFunction = null;

	public static ActionsFunction getActionsFunction() {
		if (null == _actionsFunction) {
			_actionsFunction = new CanibalesActionsFunction();
		}
		return _actionsFunction;
	}

	public static ResultFunction getResultFunction() {
		if (null == _resultFunction) {
			_resultFunction = new CanibalesResultFunction();
		}
		return _resultFunction;
	}

	private static class CanibalesActionsFunction implements ActionsFunction {
		public Set<Action> actions(Object state) {
			CanibalesBoard board = (CanibalesBoard) state;

			Set<Action> actions = new LinkedHashSet<Action>();

			if (board.canMoveBoat(CanibalesBoard.MOVER1C, CanibalesBoard.IZQ) || board.canMoveBoat(CanibalesBoard.MOVER1C, CanibalesBoard.DCH)) {
				actions.add(CanibalesBoard.MOVER1C);
			}
			if (board.canMoveBoat(CanibalesBoard.MOVER2C, CanibalesBoard.IZQ) || board.canMoveBoat(CanibalesBoard.MOVER2C, CanibalesBoard.DCH)) {
				actions.add(CanibalesBoard.MOVER2C);
			}
			if (board.canMoveBoat(CanibalesBoard.MOVER1M, CanibalesBoard.IZQ) || board.canMoveBoat(CanibalesBoard.MOVER1M, CanibalesBoard.DCH)) {
				actions.add(CanibalesBoard.MOVER1M);
			}
			if (board.canMoveBoat(CanibalesBoard.MOVER2M, CanibalesBoard.IZQ) || board.canMoveBoat(CanibalesBoard.MOVER2M, CanibalesBoard.DCH)) {
				actions.add(CanibalesBoard.MOVER2M);
			}
			if (board.canMoveBoat(CanibalesBoard.MOVER1M1C, CanibalesBoard.IZQ) || board.canMoveBoat(CanibalesBoard.MOVER1M1C, CanibalesBoard.DCH)) {
				actions.add(CanibalesBoard.MOVER1M1C);
			}
			return actions;
		}
	}
	
	private static class CanibalesResultFunction implements ResultFunction {
		public Object result(Object s, Action a) {
			CanibalesBoard board = (CanibalesBoard) s;

			if (CanibalesBoard.MOVER1C.equals(a)) {
				CanibalesBoard newBoard = new CanibalesBoard(board);
				if (board.canMoveBoat(CanibalesBoard.MOVER1C, CanibalesBoard.IZQ)) {
					newBoard.mover1C(CanibalesBoard.IZQ,CanibalesBoard.DCH);
				}
				else {
					newBoard.mover1C(CanibalesBoard.DCH,CanibalesBoard.IZQ);
				}
				return newBoard;
			} else if  (CanibalesBoard.MOVER2C.equals(a)) {
				CanibalesBoard newBoard = new CanibalesBoard(board);
				if (board.canMoveBoat(CanibalesBoard.MOVER2C, CanibalesBoard.IZQ)) {
					newBoard.mover2C(CanibalesBoard.IZQ,CanibalesBoard.DCH);
				}
				else {
					newBoard.mover2C(CanibalesBoard.DCH,CanibalesBoard.IZQ);
				}
				return newBoard;
			} else if  (CanibalesBoard.MOVER1M.equals(a)) {
				CanibalesBoard newBoard = new CanibalesBoard(board);
				if (board.canMoveBoat(CanibalesBoard.MOVER1M, CanibalesBoard.IZQ)) {
					newBoard.mover1M(CanibalesBoard.IZQ,CanibalesBoard.DCH);
				}
				else {
					newBoard.mover1M(CanibalesBoard.DCH,CanibalesBoard.IZQ);
				}
				return newBoard;
			} else if  (CanibalesBoard.MOVER2M.equals(a)) {
				CanibalesBoard newBoard = new CanibalesBoard(board);
				if (board.canMoveBoat(CanibalesBoard.MOVER2M, CanibalesBoard.IZQ)) {
					newBoard.mover2M(CanibalesBoard.IZQ,CanibalesBoard.DCH);
				}
				else {
					newBoard.mover2M(CanibalesBoard.DCH,CanibalesBoard.IZQ);
				}
				return newBoard;
			} else if  (CanibalesBoard.MOVER1M1C.equals(a)) {
				CanibalesBoard newBoard = new CanibalesBoard(board);
				if (board.canMoveBoat(CanibalesBoard.MOVER1M1C, CanibalesBoard.IZQ)) {
					newBoard.mover1M1C(CanibalesBoard.IZQ,CanibalesBoard.DCH);
				}
				else {
					newBoard.mover1M1C(CanibalesBoard.DCH,CanibalesBoard.IZQ);
				}
				return newBoard;
			}

			// The Action is not understood or is a NoOp
			// the result will be the current state.
			return s;
		}
	}
}
