
import java.util.ArrayList;

public class Data {

	public String name;
	public ArrayList<Pair<Integer, Character>> forcedPartialAssignemnt;
	public ArrayList<Pair<Integer, Character>> forbiddenMachine;
	public ArrayList<Pair<Integer, Integer>> tooNearTask;
	public int[][] machinePenalties;
	public ArrayList<Triple> tooNearPenalties;

	public Data() {
		forcedPartialAssignemnt = new ArrayList<Pair<Integer, Character>>();
		forbiddenMachine = new ArrayList<Pair<Integer, Character>>();
		tooNearTask = new ArrayList<Pair<Integer, Integer>>();
		machinePenalties = new int[Main.dimension][Main.dimension];
		tooNearPenalties = new ArrayList<Triple>();
	}

	public int getPenalty(int mach, int task) {
		return machinePenalties[mach - 1][task - 1];
	}

	public int[][] forcedPartialAssignemntAsArray() {
		int[][] ret = new int[forcedPartialAssignemnt.size()][2];
		for (int i = 0; i < forcedPartialAssignemnt.size(); i++) {
			Pair<Integer, Character> pair = forcedPartialAssignemnt.get(i);
			ret[i][0] = pair.first - 1;
			ret[i][1] = pair.second - 65;
		}
		return ret;
	}

	public int[][] forbiddenMachineAsArray() {
		int[][] ret = new int[forbiddenMachine.size()][2];
		for (int i = 0; i < forbiddenMachine.size(); i++) {
			Pair<Integer, Character> pair = forbiddenMachine.get(i);
			ret[i][0] = pair.first - 1;
			ret[i][1] = pair.second - 65;
		}
		return ret;
	}

	public int[][] tooNearPenaltiesAsArray() {
		int[][] ret = new int[tooNearPenalties.size()][3];
		for (int i = 0; i < tooNearPenalties.size(); i++) {
			Triple triple = tooNearPenalties.get(i);
			ret[i][0] = triple.task1 - 1;
			ret[i][1] = triple.task2 - 1;
			ret[i][2] = triple.penalty;
		}
		return ret;
	}

	public int[][] tooNearTaskAsArray() {
		int[][] ret = new int[tooNearTask.size()][2];
		for (int i = 0; i < tooNearTask.size(); i++) {
			Pair<Integer, Integer> pair = tooNearTask.get(i);
			ret[i][0] = pair.first - 1;
			ret[i][1] = pair.second - 1;
		}
		return ret;
	}

	public void setPenaltyLine(int line, int[] vals) {
		machinePenalties[line] = vals;
	}

	public Triple hasTooNearPenalty(int task1, int task2) {
		Triple ret = null;
		for (Triple triple : tooNearPenalties) {
			ret = triple;
			if (triple.task1 == task1 && triple.task2 == task2) break;
		}
		return ret;
	}

	public Pair<Integer, Character> getPair(int mach, int task) {
		// 65 = 'A' therefore task + 64 = A-G for 1-8
		char val = (char) (task + 64);
		return new Pair<Integer, Character>(mach, val);
	}
}