

	// ver2: feb 7,2018
	// program contains 3 classes. this being main that prints the results.
	// if 
/*	public class OMainPro {
		
		public static char [] Tasks= {'A','B','C','D','E','F','G','H'};
		public static void main(String[] args) {
			int [] [] matrixUse= {
							{1,1,1,1,1,1,1,1},
							{1,1,1,1,1,1,1,1},
							{1,1,1,1,1,1,1,1},
							{1,1,1,1,1,1,1,1},
							{1,1,1,1,1,1,1,1},
							{1,1,1,1,1,1,1,1},
							{1,1,1,1,1,1,1,1},
							{1,1,1,1,1,1,1,1}				}; // the matrix of machine/task
			int [][] penalties= {}; // illegal assignments.
			int [][] mustbe= {{0,0},{1,1}};// has to contain.
			int [][] softnear= {};// soft too-near constrains
			int [][] nearpen= {{1,2}}; // hard too-near constrains
			OAlgorithm matrixOf=new OAlgorithm(matrixUse,penalties);
			matrixOf.turnconstrains();
			OParle_Algorithm Parle=new OParle_Algorithm( matrixOf.activeList,matrixOf.activeCordinates_x,matrixOf.activeCordinates_y,matrixOf.hashed,nearpen,softnear,mustbe);
			Parle.Parle_kid(Parle.numberkey[0], 0);
			System.out.print("Solution: ");
			boolean Nosolution=false;
			for(int i=0;i<8;i++) {
				if(Parle.Sendend[i]==-1) {Nosolution=true;break;}
				System.out.print(Tasks[Parle.send_Y[i]]+" ");
			}
			if (Nosolution==true) {
				System.out.print("sorry no solution");
			}
			else{System.out.print("Total penalty:" + (Parle.setValue(Parle.Sendend)+Parle.softsearchNear(Parle.send_Y)));}
			

		}
		

	}
*/

