package node;

import main.Common;

public class Node {
	private long field;
	private Node parent;
	private int depth;
	
	public Node(Node node) {
		field = node.getField();
		parent = node.getParent();
		depth = node.getDepth();
	}
	
	public Node(byte[][] x) {
		field = toLong(x);
		parent = null;
		depth = 0;
	}
	
	public Node(byte[][] x, Node parent, int depth) {
		field = toLong(x);
		this.parent = parent;
		this.depth = depth;
	}
	
	/*
	 * Pack array to long
	 */
	public long toLong(byte[][] x) {
		long res = 0;
		for (int i = 0; i < Common._ROWS; i++)
			for (int j = 0; j < Common._COLS; j++)
				res = (res << 4) | x[i][j];
		return res;
	}
	
	/*
	 * Unpack long to array
	 */
	public byte[][] fromLong() {
		long packed = field;  
		
		byte[][] y = new byte[Common._ROWS][Common._COLS];
		
		for (int i = Common._ROWS * Common._COLS - 1; i >= 0; i--) {
			int t = (int) packed & 0xF;
			y[i / Common._COLS][i % Common._COLS] = (byte) t;
			packed >>= 4;
		}
		
		return y;
	}
	
	public long getField() {
		return field;
	}
	
	public void setField(long fld) {
		field = fld;
	}

	public Node getParent() {
		return parent;
	}
	
	public int getDepth() {
		return depth;
	}
	
	/*
	 * For printing (space on zero position)
	 */
	@Override
	public String toString() {
		return print().replace(" 0", "  ");
	}
	
	/*
	 * For file writing (with zero)
	 */
	public String print() {
		String s = "";
		byte[][] y = fromLong();
		
		for (int i = 0; i < Common._ROWS; i++) {
			for (int j = 0; j < Common._COLS; j++)
				s += String.format("%2d ", y[i][j]);
					
			s += "\n";
		}
            
		return s.substring(0, s.length()-2);
	}
	
}