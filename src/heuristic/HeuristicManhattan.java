package heuristic;

import main.Common;
import node.Node;

public class HeuristicManhattan extends Heuristic {
	
	@Override
	public String getTitle() {
		return "Manhattan heuristic";
	}
	
	@Override
	public int calc(Node node) {
		int res = 0;
		byte[][] y = node.fromLong();
		
		for (int i = 0; i < Common._ROWS; i++)
			for (int j = 0; j < Common._COLS; j++)
				if (y[i][j] != w[i][j]) {
					int n = y[i][j] == 0 ? Common._ROWS * Common._COLS - 1 : y[i][j] - 1; 
					int r = n / Common._ROWS;
					int c = n % Common._ROWS;
					int md = Math.abs(r - i) + Math.abs(c - j);
					res += md;
				}
		
		return res;
	}
	
}