//import java.util.Arrays;
// program contains 3 classes. this being main that prints the results.
// if 
public class MainPro {
	public static int val=9;
	public static char [] Tasks= {'A','B','C','D','E','F','G','H'};
	public static void main(String[] args) {
		int [] [] matrixUse= {
						{1,2,3,4},
						{2,4,6,3},
						{3,6,9,2},
						{4,1,3,2}}; // the matrix of machine/task
		int [][] penalties= {{0,3},{1,1},{2,2},{3,0}}; // illegal assignments.
		int [][] mustbe= {{0,1},{1,2}};// has to contain.
		int [][] softnear= {{1,0,20}};// soft too-near constrains
		int [][] nearpen= {{3,2}}; // hard too-near constrains
		Algorithm matrixOf=new Algorithm(matrixUse,penalties);
		matrixOf.turnconstrains();
		Parle_Algorithm Parle=new Parle_Algorithm( matrixOf.activeList,matrixOf.activeCordinates_x,matrixOf.activeCordinates_y,matrixOf.hashed,nearpen,softnear,mustbe);
		Parle.Parle_kid(Parle.numberkey[0], 0);
		System.out.print("Solution: ");
		for(int i=0;i<4;i++) {
			System.out.print(Tasks[Parle.send_Y[i]]+" ");
		}
		
		System.out.print("penalty:"+(Parle.setValue(Parle.Sendend)+Parle.softsearchNear(Parle.validList)));
		

	}
	

}
