//functions return true if bad for tree, false if good
public class LegalCheck {
	
	private boolean conflictCheck(Pair<Integer,Integer> pair) { //maybe tree leaf type instead
		return d.forbiddenMachine.contains(pair);
	}
	
	
	private boolean tooClose(Pair<Integer,Integer> pair) { 
		int loc;
		int left, right;
		for(int i = 0; i < d.tooNearTask.length(); i++) {
			if(d.tooNearTask[i].first == pair.second || d.tooNearTask[i].second == pair.second) {
				loc = nodeCheck.path.indexOf(pair.second);
				left = loc-1;
				right = loc+1;
				if(left == -1) {left = 7;}
				if(right == 8) {right = 0;}
				if(d.tooNearTask[i].first == nodeCheck.path.get(left) || d.tooNearTask[i].second == nodeCheck.path.get(left) || d.tooNearTask[i].first == nodeCheck.path.get(right) || d.tooNearTask[i].second == nodeCheck.path.get(right)) {
					return true;
				}
				
			}
			
		}
		return false;
	}
	
	
	private boolean forcingPartial(Pair<Integer,Integer> pair) {
		boolean mach = false;
		boolean task = false;
		for(int i = 0; i < d.forcedPartialAssignment.length(); i++) {
			if(d.forcedPartialAssignment[i].first == pair.first) {
				mach = true;
			}
			if(d.forcedPartialAssignment[i].second == pair.second) {
				task = true;
			}
		}
		if((mach == true && task == true) || (mach == false && task == false)) {
			return false;
		}
		else {
			return true;
		}
	}
	
	
	private void prettyClose(Pair<Integer,Integer> pair) {
		int loc;
		int left, right;
		for(int i = 0; i < d.tooNearPenalties.length(); i++) {
			if(d.tooNearPenalties[i].task1 == pair.second || d.tooNearPenalties[i].task2 == pair.second) {
				loc = nodeCheck.path.indexOf(pair.second);
				left = loc-1;
				right = loc+1;
				if(left == -1) {left = 7;}
				if(right == 8) {right = 0;}
				if(d.tooNearTask[i].first == nodeCheck.path.get(left) || d.tooNearTask[i].second == nodeCheck.path.get(left) || d.tooNearTask[i].first == nodeCheck.path.get(right) || d.tooNearTask[i].second == nodeCheck.path.get(right)) {
					nodeCheck.data.penalty = d.tooNearPenalties[i].penalty;
							break;
				}
				
			}
			
		}
	}
	
	private void penaltyCheck(Pair<Integer,Integer> pair) {
		nodeCheck.data.penalty += d.getPenalty(pair.first,pair.second);
	}
	
	private Node nodeCheck;
	
	//takes Node as arg. copies Node into a new node, then returns the new node with updated values
	public Node assignCheck(Node treeNode) {
		nodeCheck = treeNode;
		Pair<Integer,Integer> sched = new Pair(treeNode.data.task1, treeNode.data.task2); //data is Mach, task. not task,task
		
		if(conflictCheck(sched) || tooClose(sched) || forcingPartial(sched)) {
			treeNode.checked = true;
			return nodeCheck;
		}
		
		prettyClose(sched);
		penaltyCheck(sched);
		
		
		return nodeCheck;
	}
	
}
