// program contains 3 classes. this being main that prints the results.
// if 
public class Main {

	public static int dimension = 4;
	public static char[] Tasks = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H' };

	public static void main(String[] args) {
		Data data = new Parser().loadData(args[0]);
		if (data == null) return;
		int[][] forbiddenMachine = data.forbiddenMachineAsArray(); // illegal assignments.
		int[][] forcedPartialAssignemnt = data.forcedPartialAssignemntAsArray(); // has to contain.
		int[][] tooNearPenalties = data.tooNearPenaltiesAsArray();// soft too-near constrains
		int[][] tooNearTask = data.tooNearTaskAsArray(); // hard too-near constrains

		Algorithm matrixOf = new Algorithm(data.machinePenalties, forbiddenMachine);
		matrixOf.turnconstrains();
		Parle_Algorithm Parle = new Parle_Algorithm(matrixOf.activeList, matrixOf.activeCordinates_x, matrixOf.activeCordinates_y, matrixOf.hashed, tooNearTask, tooNearPenalties, forcedPartialAssignemnt);
		Parle.Parle_kid(Parle.numberkey[0], 0);

		System.out.print("Solution: ");
		for (int i = 0; i < 4; i++) {
			System.out.print(Tasks[Parle.send_Y[i]] + " ");
		}
		System.out.print("penalty:" + (Parle.setValue(Parle.Sendend) + Parle.softsearchNear(Parle.validList)));
	}
}
