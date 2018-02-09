// program contains 3 classes. this being main that prints the results.
// if 
public class Main {

	public static final int dimension = 8;
	public static char[] Tasks = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H' };

	public static void main(String[] args) {
		Data data = new Parser().loadData(args[0]);
		if (data == null) return;
		int[][] forbiddenMachine = data.forbiddenMachineAsArray(); // illegal assignments.

		Algorithm matrixOf = new Algorithm(data.machinePenalties, forbiddenMachine);
		matrixOf.turnconstrains();
		Parle_Algorithm Parle = new Parle_Algorithm(matrixOf.activeList, matrixOf.activeCordinates_x, matrixOf.activeCordinates_y, matrixOf.hashed, data);
		Parle.Parle_kid(Parle.numberkey[0], 0);

		int penalty = Parle.setValue(Parle.Sendend) + Parle.softsearchNear(Parle.validList);
		if (penalty < 0) {
			System.out.println("No valid solution possible!");
		} else {
			System.out.print("Solution: ");
			for (int i = 0; i < Main.dimension; i++) {
				System.out.print(Tasks[Parle.send_Y[i]] + " ");
			}
			System.out.println("penalty:" + penalty);
		}
	}
}
