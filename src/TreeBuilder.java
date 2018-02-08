package src;

public class TreeBuilder {
	private Tree planted;
	private LegalCheck legalCheck;
	
	public TreeBuilder(Data constraints) {
		planted = new Tree();
		planted.root.data = new Triple(-1, 0, -1);
		planted.root.totalPenalty = 0;
		this.legalCheck = new LegalCheck(constraints);
		
		//for(int i = 0; i < 8; i++) {			//no actual call to parent, so took this out temporarily
		//	Node child = new Node()
		//}
	}
	
	public void grow(Node current) {
		int mach = 0;
		int task = current.data.task2 + 1;
		if(task > 8) {return;} 								//possible infinite loop creator, but maybe not since changed to return now
		for(int i = 0; i < 8; i++) {
			current.children.set(i, new Node(current));		//create each children node, all have copy of parent's path array
			current.children.get(i).path = current.path;
		}
		
		
		while(mach < 8) {									//setting all the children. IF ILLEGAL ASSIGNMENT: children.path becomes null, and children.checked becomes true
			if(current.children.get(mach).path.get(mach) == null) {
				current.children.get(mach).path.set(mach, task);
				current.children.get(mach).data = new Triple(mach, task, 0);		//sets data into the current child being looked at
				
				current.children.set(mach, legalCheck.assignCheck(current.children.get(mach))); //assignCheck will edit totalPenalty and penalty, as well as set checked if illegal assignment
				if(current.children.get(mach).checked) {							//only sets in path if checked is false, which means there is no problem
					current.children.get(mach).path = null;							//if illegal assignment, erases path, and sets to checked
					current.children.get(mach).checked = true;
					}
				}
			else {
				current.children.get(mach).path = null;			//setting 2 tasks to same machine, erases path, and sets to checked
				current.children.get(mach).checked = true;
			}
			mach++;
		}
		
		
		//if(!planted.root.parent.data == null) {
			
		for(int i = 0; i < 8; i++) {
			if(current.children.get(i).checked) {
				continue;								
			}											
			grow(current.children.get(i));
		}
		
	}

}
