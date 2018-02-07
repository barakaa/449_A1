import java.util.ArrayList;
import java.util.List;

public class Node {
	public boolean checked = false;
	public Node parent;
	public Triple data;
	public ArrayList<Node> children;
	public int totalPenalty;

	public ArrayList<Integer> path;
    
    public Node(Node dad) {
    	children = new ArrayList<Node>();
    	parent = dad;
    	totalPenalty = dad.totalPenalty + penalty();
    }

	public int penalty() {
		return data.penalty;
	}
}
