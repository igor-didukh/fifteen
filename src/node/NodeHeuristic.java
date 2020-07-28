package node;

public class NodeHeuristic extends Node {
	private int f;

	public NodeHeuristic(Node node, int h) {
		super(node);
		f = super.getDepth() + h;
	}
	
	public int getF() {
		return f;
	}

}
