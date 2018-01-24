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
		machinePenalties = new int[Main.penalties_length][Main.penalties_length];
		tooNearPenalties = new ArrayList<Triple>();
	}
}