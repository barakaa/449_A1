import java.util.ArrayList;

public class Data {

	public String name;
	public ArrayList<Pair<Integer, Character>> forcedPartialAssignemnt;
	public ArrayList<Pair<Integer, Character>> forbiddenMachine;
	public ArrayList<Pair<Integer, Integer>> tooNearTask;
	private int[][] machinePenalties;
	public ArrayList<Triple> tooNearPenalties;

	public Data() {
		forcedPartialAssignemnt = new ArrayList<Pair<Integer, Character>>();
		forbiddenMachine = new ArrayList<Pair<Integer, Character>>();
		tooNearTask = new ArrayList<Pair<Integer, Integer>>();
		machinePenalties = new int[8][8];
		tooNearPenalties = new ArrayList<Triple>();
	}

	public int getPenalty(int mach, int task) {
		return machinePenalties[mach - 1][task - 1];
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