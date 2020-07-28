package methods;

import java.util.ArrayList;

import com.carrotsearch.hppc.LongOpenHashSet;

import main.Common;
import node.Node;

public abstract class Search {
	private final static char[] DIRS = {'W', 'A', 'S', 'D'};
	
	public abstract String getTitle();
	public abstract Node search(Node initNode);
	
	protected final long _WIN_FIELD;
	protected LongOpenHashSet _CLOSED;
	protected int _PROCESSED;
	protected int _DEPTH;
	
	private double time0;
	
	public Search() {
		time0 = System.nanoTime();
		
		_WIN_FIELD = Common._WIN.getField();
		_CLOSED = new LongOpenHashSet();
		_PROCESSED = 0;
	}
	
	public String getTimeInfo() {
		return (System.nanoTime() - time0) / 1e6 + " ms.";
	}
	
	public String getClosedInfo() {
		return "" + _CLOSED.size();
	}
	
	public String  getProcessedInfo() {
		return "" + _PROCESSED;
	}
	
	public String  getDepthInfo() {
		return "" + _DEPTH;
	}
	
	/*
	 * Get children
	 */
	protected ArrayList<Node> getChildren(Node node) {
		ArrayList<Node> res = new ArrayList<Node>(3);
		
		Node parent = node.getParent();
		long parentField = parent == null ? 0 : parent.getField();
		
		int m = 0, n = 0;
		byte[][] y = node.fromLong();
		int depth = node.getDepth();
		Node tmpNode;
		
		// fing position of zero element
		for (byte i = 0; i < Common._ROWS; i++)
			for (byte j = 0; j < Common._COLS; j++)
				if (y[i][j] == 0) {
					m = i;
					n = j;
					break;
				}
		
		// fing up to 3 children (4 - parent)
		for (byte k = 0; k < 4; k++) {
			int m1 = m;
			int n1 = n;
			
			switch (DIRS[k]) {
			case 'W':
				if (m1 > 0) 
					m1--;
				else 
					continue;
				break;
			case 'A':
				if (n1 > 0) 
					n1--;
				else 
					continue;
				break;
			case 'S':
				if (m1 < Common._ROWS - 1) 
					m1++;
				else 
					continue;
				break;
			case 'D':
				if (n1 < Common._COLS - 1)
					n1++;
				else 
					continue;
				break;
			}
			
			byte b = y[m1][n1];
			
			y[m][n] = y[m1][n1];
			y[m1][n1] = 0;
			
			tmpNode = new Node(y, node, depth+1);
			if (tmpNode.getField() != parentField)
				res.add(tmpNode);
			
			// Rollback
			y[m1][n1] = b;
			y[m][n] = 0;
		}
		
		return res;
	}
}