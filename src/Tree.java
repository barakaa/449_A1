import java.util.ArrayList;
import java.util.List;

public class Tree {
	public Node root;

	public Tree() {
		root = new Node();
		root.children = new ArrayList<Node>();
	}
}
