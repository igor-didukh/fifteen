package methods;

import java.util.LinkedList;

import node.Node;

public class DFS extends Search {
	private final int MAX_DEPTH = 30;
	private int returnCount;
	
	@Override
	public String getTitle() {
		return "Depth-first search method";
	}
	
	@Override
	public Node search(Node initNode) {
		Node res = null;
		LinkedList<Node> open = new LinkedList<Node>();
		
		Node node = initNode; 
		open.push(node);
		
		returnCount = 0;
		
		while ( !open.isEmpty() ) {
			node = open.pop();
			long field = node.getField();
			
			_PROCESSED++;
			
			if (field == _WIN_FIELD) {
				res = node;
				break;
			}
			
			if ( !_CLOSED.contains(field) )
				_CLOSED.add(field);	
			
			if (node.getDepth() >= MAX_DEPTH) {
				returnCount++;
				continue;
			}
				
			for ( Node child : super.getChildren(node) )
				if ( !_CLOSED.contains( child.getField() ) )
					open.push(child);
        }
		
		_DEPTH = returnCount == 0 ? node.getDepth() : MAX_DEPTH;
		return res;
	}
	
	@Override
	public String getDepthInfo() {
		return String.format("%d\n  (reached %d times)", _DEPTH, returnCount);
	}

}