import java.util.ArrayList;
import java.util.List;

public class Node {
	public boolean checked = false;
	public Node parent;
	public Triple data;
	public ArrayList<Node> children;
	public int totalPenalty;

	public ArrayList<Integer> path;
    
    public Node(Node parent) {
    	children = new ArrayList<Node>();
    	totalPenalty = parent.totalPenalty + penalty();
    }

	public int penalty() {
		return data.penalty;
	}
}
