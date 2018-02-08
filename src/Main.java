import java.util.ArrayList;

public class Main {

	public static int dimension = 8;
	public static char[] Tasks = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H' };

	public static void main(String[] args) {
		Data data = new Parser().loadData("text1.txt");
		if (data == null) return;
		int[][] forbiddenMachine = data.forbiddenMachineAsArray(); // illegal assignments.
		int [][] forcedPartialAssignemnt = data.forcedPartialAssignemntAsArray();
		int [][] tooNearTask = data.tooNearPenaltiesAsArray();
		//int [][] machinePenalties = data.mach
		int [][]tooNearPenalties = data.tooNearPenaltiesAsArray();
				
				
		OAlgorithm matrixOf = new OAlgorithm(data.machinePenalties, forbiddenMachine);
		matrixOf.turnconstrains();
		OParle_Algorithm Parle = new OParle_Algorithm(matrixOf.activeList, matrixOf.activeCordinates_x, matrixOf.activeCordinates_y, matrixOf.hashed,tooNearTask,tooNearPenalties,forcedPartialAssignemnt);
		Parle.Parle_kid(Parle.numberkey[0], 0);

		System.out.print("Solution: ");
		for (int i = 0; i < Main.dimension; i++) {
			System.out.print(Tasks[Parle.send_Y[i]] + " ");
		}
		System.out.print("penalty:" + (Parle.setValue(Parle.Sendend) + Parle.softsearchNear(Parle.validList)));
	}
}