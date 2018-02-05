import java.util.ArrayList;
import java.util.List;

public class Tree {
	private Node root;

	public Tree() {
		root = new Node();
		root.children = new ArrayList<Node>();
	}
}
