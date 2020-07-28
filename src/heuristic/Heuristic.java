package heuristic;

import main.Common;
import node.Node;

public abstract class Heuristic {
	public abstract String getTitle();
	public abstract int calc(Node node);
	
	protected byte[][] w;
	
	public Heuristic() {
		w = Common._WIN.fromLong();	
	}
}
