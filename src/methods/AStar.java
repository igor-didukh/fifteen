package methods;

import java.util.PriorityQueue;
import java.util.Comparator;

import node.Node;
import node.NodeHeuristic;
import heuristic.Heuristic;

public class AStar extends Search {
	private Heuristic heuristic;
	
	public AStar(Heuristic heuristic) {
		this.heuristic = heuristic;
	}
	
	@Override
	public String getTitle() {
		return String.format( "A* method (%s)", heuristic.getTitle() );
	}
	
	@Override
	public Node search(Node initNode) {
		NodeHeuristic res = null;
		
		PriorityQueue<NodeHeuristic> open = new PriorityQueue<NodeHeuristic>( 3, new NodeComparator() );
		
		NodeHeuristic node = new NodeHeuristic( initNode, heuristic.calc(initNode) ); 
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
				if ( !_CLOSED.contains(child.getField()) )
					open.add( new NodeHeuristic( child, heuristic.calc(child) ) );
        }
		
		_DEPTH = node.getDepth();
		return res;
	}
	
	/*
	 * NodeAStar comparator (for PriorityQueue)
	 */
	class NodeComparator implements Comparator<NodeHeuristic> {
		public int compare(NodeHeuristic a, NodeHeuristic b) {
			if ( a.getF() == b.getF() )
				return b.getDepth() - a.getDepth();
			else
				return a.getF() - b.getF();
		}
	}
	
}