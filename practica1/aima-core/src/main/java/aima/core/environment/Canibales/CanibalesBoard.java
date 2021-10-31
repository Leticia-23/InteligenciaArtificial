/**
 * @author Leticia Sánchez (760549)
 */

package aima.core.environment.Canibales;

import java.util.Arrays;

import aima.core.agent.Action;
import aima.core.agent.impl.DynamicAction;

public class CanibalesBoard {
	
	public static Action MOVER1C = new DynamicAction("Mover1C");
	
	public static Action MOVER2C = new DynamicAction("Mover2C");
	
	public static Action MOVER1M = new DynamicAction("Mover1M");
	
	public static Action MOVER2M = new DynamicAction("Mover2M");
	
	public static Action MOVER1M1C = new DynamicAction("Mover1M1C");
	
	private int[] state;
	
	public static String IZQ = new String("izq");
	
	public static String DCH = new String("dch");
	
	
	//
	// PUBLIC METHODS
	//
	
	// ESTADO = {Mi, Ci, B, Md, Cd}
	
	// B ∈ [0, 1] indica la posición de la barca, por lo que toma el valor 0 si está
	// en el extremo izquierdo, o 1 si está en el extremo derecho
	
	// Mi, Ci, Md, Cd ∈ [0,..., 3] indican el número de misioneros y caníbales que
	// quedan en el extremo izquierdo y derecho del río, respectivamente

	public CanibalesBoard() {
		state = new int[] { 3, 3, 0, 0, 0 };
	}

	public CanibalesBoard(int[] state) {
		this.state = new int[state.length];
		System.arraycopy(state, 0, this.state, 0, state.length);
	}
	
	public CanibalesBoard(CanibalesBoard copyBoard) {
		this(copyBoard.getState());
	}

	public int[] getState() {
		return state;
	}
	
	//Métodos realizar acciones -> transportar desde orilla x a orilla y
	public void mover1C(String x, String y) {
		if (x.equals(IZQ)) {
			this.state[1] -= 1;
			this.state[4] += 1;
		} else {
			this.state[1] += 1;
			this.state[4] -= 1;
		}
		this.state[2] = this.state[2] == 0 ? 1 : 0;
	}

	public void mover2C(String x, String y) {
		if (x.equals(IZQ)) {
			this.state[1] -= 2;
			this.state[4] += 2;
		} else {
			this.state[1] += 2;
			this.state[4] -= 2;
		}
		this.state[2] = this.state[2] == 0 ? 1 : 0;
	}

	public void mover1M(String x, String y) {
		if (x.equals(IZQ)) {
			this.state[0] -= 1;
			this.state[3] += 1;
		} else {
			this.state[0] += 1;
			this.state[3] -= 1;
		}
		this.state[2] = this.state[2] == 0 ? 1 : 0;
	}

	public void mover2M(String x, String y) {
		if (x.equals(IZQ)) {
			this.state[0] -= 2;
			this.state[3] += 2;
		} else {
			this.state[0] += 2;
			this.state[3] -= 2;
		}
		this.state[2] = this.state[2] == 0 ? 1 : 0;
	}

	public void mover1M1C(String x, String y) {
		if (x.equals(IZQ)) {
			this.state[0] -= 1;
			this.state[1] -= 1;
			this.state[3] += 1;
			this.state[4] += 1;
		} else {
			this.state[0] += 1;
			this.state[1] += 1;
			this.state[3] -= 1;
			this.state[4] -= 1;
		}
		this.state[2] = this.state[2] == 0 ? 1 : 0;
	}
	
	
	//Método comprobar correcta acción a realizar
	public boolean canMoveBoat(Action where, String from) {
		boolean retVal = true;
		
		int Mi = state[0]; // Numero de misioneros en la orilla izquierda
		int Ci = state[1]; // Numero de canibales en la orilla izquierda
		int Md = state[3]; // Numero de misioneros en la orilla derecha
		int Cd = state[4]; // Numero de canibales en la orilla derecha
		boolean B = state[2] == 0; // true-> bote orilla izquierda ; false -> bote orilla derecha

		if (where.equals(MOVER1C)) {
			if (from.equals(IZQ)) {
				retVal = (Ci >= 1) && B && ((Md >= Cd+1) || (Md == 0));
			}
			else if (from.equals(DCH)) {
				retVal = (Cd >= 1) && !B && ((Mi >= Ci+1) || (Mi == 0));
			}
			
		} else if (where.equals(MOVER2C)) {
			if (from.equals(IZQ)) {
				retVal = (Ci >= 2) && B && ((Md >= Cd+2) || (Md == 0));
			}
			else if (from.equals(DCH)) {
				retVal = (Cd >= 2) && !B && ((Mi >= Ci+2) || (Mi == 0));
			}
		} else if (where.equals(MOVER1M)) {
			if (from.equals(IZQ)) {
				retVal = (Mi >= 1) && B && (((Mi >= Ci+1) || (Mi == 1)) && (Md >= Cd-1));
			}
			else if (from.equals(DCH)) {
				retVal = (Md >= 1) && !B && (((Md >= Cd+1) || (Md == 1)) && (Mi >= Ci-1));
			}
		} else if (where.equals(MOVER2M)) {
			if (from.equals(IZQ)) {
				retVal = (Mi >= 2) && B && (((Mi >= Ci+2) || (Mi == 2)) && (Md >= Cd-2));
			}
			else if (from.equals(DCH)) {
				retVal = (Md >= 2) && !B && (((Md >= Cd+2) || (Md == 2)) && (Mi >= Ci-2));
			}
		} else if (where.equals(MOVER1M1C)) {
			if (from.equals(IZQ)) {
				retVal = (Mi >= 1) && (Ci >= 1) && B && (Md >= Cd);
			}
			else if (from.equals(DCH)) {
				retVal = (Md >= 1) && (Cd >= 1) && !B && (Mi >= Ci);
			}
			
		}
		return retVal;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(state);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CanibalesBoard other = (CanibalesBoard) obj;
		return Arrays.equals(state, other.state);
	}

	@Override
	public String toString() {
		String retVal = "RIBERA-IZQ ";
		// Misioneros izquierda
		if (state[0] == 3) {
			retVal += "M M M ";
		} else if (state[0] == 2) {
			retVal += "M M ";
		} else if (state[0] == 1) {
			retVal += "M ";
		}
		// Caníbales izquierda
		if (state[1] == 3) {
			retVal += "C C C ";
		} else if (state[1] == 2) {
			retVal += "C C ";
		} else if (state[1] == 1) {
			retVal += "C ";
		}
		// Posición del bote
		if (state[2] == 0) {
			retVal += "BOTE --RIO-- ";
		} else if (state[2] == 1) {
			retVal += "--RIO-- BOTE ";
		}
		// Misioneros derecha
		if (state[3] == 3) {
			retVal += "M M M ";
		} else if (state[3] == 2) {
			retVal += "M M ";
		} else if (state[3] == 1) {
			retVal += "M ";
		}
		// Caníbales derecha
		if (state[4] == 3) {
			retVal += "C C C ";
		} else if (state[4] == 2) {
			retVal += "C C ";
		} else if (state[4] == 1) {
			retVal += "C ";
		}
		return retVal += "RIBERA-DCH";
	}
	
	
	
}
