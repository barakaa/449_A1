//import java.util.Arrays;
public class Algorithm {
	private int[][] Matrix1;
	private int[][] fullConstrains;
	public int valused;
	public int[] activeList;
	public int[] returnList = new int[Main.dimension];// change to 8 for a 8X8 array.Currently 4X4
	public int[] activeCordinates_x;
	public int[] activeCordinates_y;
	public int[] hashed = new int[Main.dimension];//// ^

	public Algorithm(int[][] Array_penalties, int[][] ListC) {
		Matrix1 = Array_penalties;
		fullConstrains = ListC;
		valused = fullConstrains.length;
		activeList = new int[(Main.dimension * Main.dimension) - valused];// need to change for a 8x8 array to 64. currently doing 4x4 thats why its 16.
		activeCordinates_x = new int[(Main.dimension * Main.dimension) - valused];//^
		activeCordinates_y = new int[(Main.dimension * Main.dimension) - valused];//^
	}

	public int[] bloom(int len1) {
		int[] root = new int[Main.dimension];// change to 8 for a 8x8. currently 4x4.
		int count = 0;
		int index = 0;
		for (int i = 0; i <= len1; i++) {
			if (i == 0) {
				count++;
				continue;
			}
			if (i == len1) {
				root[index] = count;
				break;
			}
			if (activeCordinates_x[i] == activeCordinates_x[i - 1]) {
				count++;
			} else {
				root[index] = count;
				count = 1;
				index++;
			}
			if (index == Main.dimension) {
				break;
			}
		}
		return root;

	}

	/////////////////////////////////////////////////////////////////////////////////
	public void turnconstrains() {
		int core1 = 0;
		boolean flag1 = true;
		for (int i = 0; i < Main.dimension; i++) {// change to 8 for a 8x8 matrix
			for (int j = 0; j < Main.dimension; j++) {// ^
				for (int h = 0; h < fullConstrains.length; h++) {
					if (i == fullConstrains[h][0] && j == fullConstrains[h][1]) {
						flag1 = false;
					}
				}
				if (flag1 == true) {
					activeList[core1] = Matrix1[i][j];
					activeCordinates_x[core1] = (i);
					activeCordinates_y[core1] = (j);
					core1++;

				}
				flag1 = true;
			}
		}
		hashed = bloom((Main.dimension * Main.dimension) - valused);// change to 64 for a 8x8 matrix.
	}
	////////////////////////////////////////////////////////////////////////////////////
}
