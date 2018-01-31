
import java.util.ArrayList;
import java.util.List;

public class Tree {
	private Node root;

    public Tree() {
        root = new Node();
        root.children = new ArrayList<Node>();
    }

    public class Node {
    	private boolean checked = false;
        private Node parent;
        private Triple data;
        private ArrayList<Node> children;
        private int totalPenalty;
        private ArrayList<int> path;
        
        public Node(Node parent) {
        	children = new ArrayList<Node>();
        	totalPenalty = parent.totalPenalty + penalty;
        }
        
        public int penalty() {
        	return data.penalty;
        }
    }
}
