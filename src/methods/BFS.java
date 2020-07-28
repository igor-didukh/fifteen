package methods;

import java.util.LinkedList;

import node.Node;

public class BFS extends Search {
	
	@Override
	public String getTitle() {
		return "Breadth-first search method";
	}
	
	@Override
	public Node search(Node initNode) {
		Node res = null;
		LinkedList<Node> open = new LinkedList<Node>();
		
		Node node = initNode;
		open.add(node);
		
		while ( !open.isEmpty() ) {
			node = open.poll();
			long field = node.getField();
			
			_PROCESSED++;
			
			if (field == _WIN_FIELD) {
				res = node;
				break;
			}
			
			if ( !_CLOSED.contains(field) )
				_CLOSED.add(field);	
			
			for ( Node child : super.getChildren(node) )
				if ( !_CLOSED.contains( child.getField() ) )
					open.add(child);
        }
		
		_DEPTH = node.getDepth();
		return res;
	}
	
}