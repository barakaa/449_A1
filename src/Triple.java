public class Triple {

	public int task1;
	public int task2;
	public int penalty;

	public Triple(int task1, int task2, int penalty) {
		this.task1 = task1;
		this.task2 = task2;
		this.penalty = penalty;
	}
	
	public String toString() {
		return "(" + task1 + "," + task2 + "," + penalty + ")";
	}
}