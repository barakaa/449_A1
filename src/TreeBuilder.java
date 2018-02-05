package src;

public class TreeBuilder {
	private Tree planted;
	
	public TreeBuilder() {
		planted = new Tree();
		planted.root.data = new Triple(-1, 0, -1);
		planted.root.totalPenalty = 0;
		//for(int i = 0; i < 8; i++) {			//no actual call to parent, so took this out temporarily
		//	Node child = new Node()
		//}
	}
	
	public void grow(Node current) {
		int mach = 0;
		int task = current.data.task2 + 1;
		if(task > 8) {return;} //possible infinite loop creator, but maybe not since changed to return now
		for(int i = 0; i < 8; i++) {
			current.children.set(i, new Node(current));		//create each children node, all have copy of parent's path array
		}
		
		while(mach <= 8) {
			if(current.children.get(mach).path.get(mach) == null) {
				current.children.set(mach, assignCheck(current.children.get(mach))); //no access to assignCheck right now...
				if(!current.children.get(mach).checked) {							//only sets in path if checked is false, which means there is no problem
					current.children.get(mach).path.set(mach, task);
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
				continue;								//want to skip grow() but stay in for loop, incrementing i as usual
			}											//continue might be wrong? 
			grow(current.children.get(i));
		}
		
	}

}
