import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Main {

	public static final int dimension = 8;
	public static char[] Tasks = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H' };

	public static void main(String[] args) {
		StringBuilder sb = new StringBuilder();
		Parser p = new Parser(sb);
		Data data = p.loadData(args[0]);
		if (data != null) {
			int[][] forbiddenMachine = data.forbiddenMachineAsArray(); // illegal assignments.
			int[][] forcedPartialAssignemnt = data.forcedPartialAssignemntAsArray();
			int[][] tooNearTask = data.tooNearTaskAsArray();
			int[][] tooNearPenalties = data.tooNearPenaltiesAsArray();

			Algorithm matrixOf = new Algorithm(data.machinePenalties, forbiddenMachine);
			matrixOf.turnconstrains();
			Parle_Algorithm Parle = new Parle_Algorithm(matrixOf.activeList, matrixOf.activeCordinates_x,
					matrixOf.activeCordinates_y, matrixOf.hashed, tooNearTask, tooNearPenalties,
					forcedPartialAssignemnt);
			Parle.Parle_kid(Parle.numberkey[0], 0);

			int penalty = Parle.setValue(Parle.Sendend) + Parle.softsearchNear(Parle.send_Y);
			if (penalty < 0) {
				sb.append("No valid solution possible!\n");
			} else {
				sb.append("Solution ");
				for (int i = 0; i < Main.dimension; i++) {
					sb.append(Tasks[Parle.send_Y[i]]);
					sb.append(i < Main.dimension - 1 ? " " : "; ");
				}
				sb.append("Quality:" + penalty + "\n");
			}
		}
		try {
			PrintWriter pw = new PrintWriter(args[1]);
			pw.write(sb.toString());
			pw.close();
		} catch (FileNotFoundException e) {
		}
	}
}
