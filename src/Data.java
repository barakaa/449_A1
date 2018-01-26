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
		machinePenalties = new int[Main.penalties_length][Main.penalties_length];
		tooNearPenalties = new ArrayList<Triple>();
	}

	public int getPenalty(int mach, int task) {
		return machinePenalties[mach - 1][task - 1];
	}

	public void setPenaltyLine(int line, int[] vals) {
		machinePenalties[line] = vals;
	}
	
	public Pair<Integer, Character> getPair(int mach, int task) {
		char val = (char) (task + 64);
		return new Pair<Integer, Character>(mach, val);
	}

	public Pair<Integer, Integer> getIntPair(int mach, int task) {
		return new Pair<Integer, Integer>(mach, task);
	}
}