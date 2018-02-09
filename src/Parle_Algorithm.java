public class Parle_Algorithm {

	public int [] Activeparle;
	public int [] Sendend= {-1,-1,-1,-1,-1,-1,-1,-1};
	public int [] Maxparle= {-1,-1,-1,-1,-1,-1,-1,-1};
	public int [] Active_x;
	public int []Active_y;
	public int [] send_Y={-1,-1,-1,-1,-1,-1,-1,-1};
	public int []checkpath={-1,-1,-1,-1,-1,-1,-1,-1};
	public int []validList= {-1,-1,-1,-1,-1,-1,-1,-1};
	public int parse=0;
	public int [] sumkey=new int[8];
	public int[]numberkey=new int [8];
	public int [][] soft_Too_Near;
	public int[][] Too_Near;
	public int[][] MustCon;
	private boolean setFlag1=false;
	private boolean flagos=true;
		public Parle_Algorithm(int []list,int[]x,int[]y,int[]count,int[][]toonear,int [][] soft,int[][] must) {
			Activeparle=list;
			Active_x=x;
			Active_y=y;
			numberkey=count;
			Too_Near=toonear;
			soft_Too_Near=soft;
			if (must.length>0) {MustCon=must;}
			else {setFlag1=true;}
			getPosition();
		}
		public void Parle_kid(int stop,int start) {
			boolean flagset=false;
			for(int i=start;i<stop;i++) {
				flagset=false;
				if (Checkcontains(validList,Active_y[i])==false  &&  Checkcontains(checkpath,Active_x[i])==false) {
					checkpath[parse]=Active_x[i];
					validList[parse]=Active_y[i];
					Maxparle[parse]=Activeparle[i];
					parse++;
					if (parse!=8) {Parle_kid(sumkey[parse],sumkey[parse-1]);}
				}
				if (parse==8 ) {
					parse=setindex(i);
					flagset=true;
					if (i==sumkey[setindex(i)]-1) {parse=parse-1;}
					boolean checkcompare=search(validList,Too_Near);
					boolean Must_Contain;
					if (setFlag1==true) {Must_Contain=true;}
					else{Must_Contain=containSearch(checkpath,validList,MustCon);}
					if (Checkcontains(validList,-1)==false && Checkcontains(checkpath,-1)==false && Checkcontains(Maxparle,-1)==false && checkcompare==false && Must_Contain==true) {
						int current=setValue(Sendend) + softsearchNear(send_Y);
						int max1=setValue(Maxparle) + softsearchNear(validList);
						if (flagos==true) {	
							Sendend=build(Maxparle);
							send_Y=build(validList);
							flagos=false;
						}
						else if (current > max1) {
							Sendend=build(Maxparle);
							send_Y=build(validList);
						}
					}
					reset(parse);
				}
				if (i==sumkey[setindex(i)]-1 && flagset==false ) {
					parse--;
					reset(parse);
				}
			}
			
		}
		public void reset(int val) {
			if (val<=-1) {val=0;}
			for(;val<sumkey.length;val++) {
				checkpath[val]=-1;
				validList[val]=-1;
				Maxparle[val]=-1;
			}
		}
		public int setValue(int [] useA) {
			int max=0;
			for (int i=0;i<8;++i) {
				max=useA[i]+max;
			}
			return max;
		}
		public int  setindex(int val) {
			int parseval=0;
			for(int i=0;i<sumkey.length;i++) {
				if (val<sumkey[i]) {parseval=i;val=val+sumkey[sumkey.length-1];}
			}
			return parseval;
		}
		public void getPosition() {
			for(int i=0;i<numberkey.length;i++) {
				if (i==0) {sumkey[i]=numberkey[i];continue;}
				sumkey[i]=sumkey[i-1]+numberkey[i];
			}
		}
		public boolean Checkcontains(int[] list,int target) {
			boolean send_r=false;
			for(int i=0;i<list.length;i++) {
				if (list[i]==target) {send_r=true;}
			}
			return send_r;
		}
		public int [] build(int [] passed) {
			int [] newL=new int [passed.length];
			for (int i=0;i<passed.length;i++) {
				newL[i]=passed[i];
			}
			return newL;
		}
		public boolean search(int [] checking,int[][]cons) {
			int stop=cons.length;
			boolean result=false;
			for (int i=0;i<checking.length;i++) {
				for (int j=0;j<stop;j++) {
					if (i==checking.length-1) {if (checking[i]==Too_Near[j][0] && checking[0]==Too_Near[j][1]) {result=true;}}
					else if (checking[i]==Too_Near[j][0] && checking[i+1]==Too_Near[j][1]) {result=true;}
				}
			}
			return result;
		}
		public boolean containSearch(int [] xchecking,int [] ychecking,int[][]cons) {
			int stop=cons.length;
			int count=0;
			boolean result=false;
			for (int i=0;i<xchecking.length;i++) {
				for (int j=0;j<stop;j++) {
					if (xchecking[i]==cons[j][0] && ychecking[i]==cons[j][1]) {count+=1;}
				}
			}
			if(count==stop) {result=true;}
			return result;
		}
		public int softsearchNear(int [] checking) {
			int stop=soft_Too_Near.length;
			int result=0;
			for (int i=0;i<checking.length;i++) {
				for (int j=0;j<stop;j++) {
					if (i==checking.length-1) {
						if (checking[i]==soft_Too_Near[j][0] && checking[0]==soft_Too_Near[j][1]) {result+=soft_Too_Near[j][2];}
					}
					else if (checking[i]==soft_Too_Near[j][0] && checking[i+1]==soft_Too_Near[j][1]) {result+=soft_Too_Near[j][2];}
				}
			}
			return result;
		}
}