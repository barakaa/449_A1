//ver2
public class OAlgorithm {

	private int[][] Matrix1;
	private int[][] fullConstrains;
	public int valused;
	public int [] activeList;
	//public int [] returnList= new int[8];// change to 8 for a 8X8 array.Currently 4X4
	public int []activeCordinates_x;
	public int []activeCordinates_y;
	public int[] hashed=new int[8];////^

	public  OAlgorithm(int[][] Array_penalties,int [][] ListC ) {
		Matrix1=Array_penalties;
		fullConstrains=ListC;
		valused=fullConstrains.length;
		activeList=new int[64-valused];// need to change for a 8x8 array to 64. currently doing 4x4 thats why its 16.
		activeCordinates_x=new int[64-valused];//^
		activeCordinates_y=new int[64-valused];//^
	}

	public int [] bloom(int len1) {
		int [] root=new int[8];// change to 8 for a 8x8. currently 4x4.
		int count=0;
		int index=0;
		for(int i=0;i<=len1;i++) {
			if (i==0) {
				count++;
				continue;
			}
			if (i==len1) {root[index]=count;break;}
			if (activeCordinates_x[i]==activeCordinates_x[i-1]) {
				count++;
			}else {
				root[index]=count;
				count=1;
				index++;
			}
			if (index==8) {break;}
		}
		return root;
		
	}

/////////////////////////////////////////////////////////////////////////////////	
	public void turnconstrains() {
		int core1=0;
		boolean flag1=true;
		for (int i=0;i<8;i++) {// change to 8 for a 8x8 matrix
			for(int j=0;j<8;j++) {//^
				for(int h=0;h<fullConstrains.length;h++) {
					if(i == fullConstrains[h][0] && j ==fullConstrains[h][1]) {
						flag1=false;
					}
				}
				if (flag1==true){
					activeList[core1]=Matrix1[i][j];
					activeCordinates_x[core1]=(i);
					activeCordinates_y[core1]=(j);
					core1++;
				
				}
				flag1=true;
			}
		}
		hashed=bloom(64-valused);// change to 64 for a 8x8 matrix.
	}
////////////////////////////////////////////////////////////////////////////////////				
}

