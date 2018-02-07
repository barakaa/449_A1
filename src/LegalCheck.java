//functions return true if bad for tree, false if good
public class LegalCheck {

	private Data data;
	private Node nodeCheck;

	public LegalCheck(Data data) {
		this.data = data;
	}

	private boolean conflictCheck(Pair<Integer, Integer> pair) { //maybe tree leaf type instead
		return data.forbiddenMachine.contains(pair);
	}

	private boolean tooClose(Pair<Integer, Integer> pair) {
		int loc;
		int left, right;
		for (int i = 0; i < data.tooNearTask.size(); i++) {
			Pair<Integer, Integer> tempPair = data.tooNearTask.get(i);
			if (tempPair.first == pair.second || tempPair.second == pair.second) {
				loc = nodeCheck.path.indexOf(pair.second);
				left = loc - 1;
				right = loc + 1;
				if (left == -1) left = 7;
				if (right == 8) right = 0;
				if (tempPair.first == nodeCheck.path.get(left) || tempPair.second == nodeCheck.path.get(left) || tempPair.first == nodeCheck.path.get(right) || tempPair.second == nodeCheck.path.get(right)) {
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean forcingPartial(Pair<Integer, Integer> pair) {
		boolean mach = false;
		boolean task = false;
		for (int i = 0; i < data.forcedPartialAssignemnt.size(); i++) {
			Pair tempPair = data.forcedPartialAssignemnt.get(i);
			tempPair = new Pair<Integer, Integer>(pair.first, pair.second - 64);
			if (tempPair.first == pair.first) mach = true;
			if (tempPair.second == pair.second) task = true;
		}
		if ((mach == true && task == true) || (mach == false && task == false)) {
			return false;
		} else {
			return true;
		}
	}

	private void prettyClose(Pair<Integer, Integer> pair) {
		int loc;
		int left, right;
		for (int i = 0; i < data.tooNearPenalties.size(); i++) {
			Triple triple = data.tooNearPenalties.get(i);
			if (triple.task1 == pair.second || triple.task2 == pair.second) {
				loc = nodeCheck.path.indexOf(pair.second);
				left = loc - 1;
				right = loc + 1;
				if (left == -1) left = 7;
				if (right == 8) right = 0;
				if (triple.task1 == nodeCheck.path.get(left) || triple.task2 == nodeCheck.path.get(left) || triple.task1 == nodeCheck.path.get(right) || triple.task2 == nodeCheck.path.get(right)) {
					nodeCheck.data.penalty = triple.penalty;
					break;
				}
			}
		}
	}

	private void penaltyCheck(Pair<Integer, Integer> pair) {
		nodeCheck.data.penalty += data.getPenalty(pair.first, pair.second);
	}

	//takes Node as arg. copies Node into a new node, then returns the new node with updated values
	public Node assignCheck(Node treeNode) {
		nodeCheck = treeNode;
		Pair<Integer, Integer> sched = new Pair<Integer, Integer>(treeNode.data.task1, treeNode.data.task2); // data is Mach, task. not task,task

		if (conflictCheck(sched) || tooClose(sched) || forcingPartial(sched)) {
			treeNode.checked = true;
			return nodeCheck;
		}

		prettyClose(sched);
		penaltyCheck(sched);

		return nodeCheck;
	}
}
