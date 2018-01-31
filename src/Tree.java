
import java.util.ArrayList;
import java.util.List;

public class Tree {
	private Node root;

    public Tree(rootData) {
        root = new Node();
        root.data = rootData;
        root.children = new ArrayList<Node>();
    }

    public static class Node {
    	private boolean checked = false;
        private Node parent;
        private Triple data;
        private List children;
        private int penalty = data.penalty;
        private int totalPenalty = findTotal();
    }
    
    private int findTotal(){
    return parent.totalPenalty + penalty;
    }
}
