package main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;

import heuristic.HeuristicHamming;
import heuristic.HeuristicManhattan;
import methods.AStar;
import methods.BFS;
import methods.DFS;
import methods.Search;
import node.Node;
import output.InfoWindow;
import output.PlayWindow;

public class Solution {
	/*
	 * Use table from file
	 */
	public Solution() {
		String s = getTaskStr( "D:\\Temp\\15.txt" );
		if (s != null)
			solve(s);
	}
	
	/*
	 * Use generated table
	 */
	public Solution(String info) {
		solve(info);
	}
	
	/*
	 * Main method
	 */
	private void solve(String info) {
		Node taskNode = createTaskNode(info);
		
		if (taskNode == null) {
			System.out.println("Error parsing task info!");
			return;
		}
		
		System.out.println("Target placement:");
		System.out.println(Common._WIN);
		
		System.out.println("\nInitial placement:");
		System.out.println(taskNode);
		
		long workField = taskNode.getField();
		
		///*
		if ( !checkSolvability(taskNode) ) {
			System.out.println("\n=== This placement has no solution! ===");
			return;
		}
		//*/
		
		if (workField == Common._WIN.getField()) {
			System.out.println("!!! YOU ARE LUCKY: Winning alignement is generated!!!");
			return;
		}
		
		while (true) {
			System.out.println("\n=== Select option: ===\n");
			System.out.println("1. Solve with 'Breadth-first search method'");
			System.out.println("2. Solve with 'Depth-first search method'");
			System.out.println("3. Solve with 'A* method (Manhattan heuristic)'");
			System.out.println("4. Solve with 'A* method (Hamming heuristic)'");
			System.out.println("5. Play the game");
			System.out.println("6. Exit");
			
			char ch = Common.prompt("\nYour choice (1..5)", "123456", "6");
			if (ch == '6') break;
			
			if (ch == '5') {
				Common.showFrame( new PlayWindow(taskNode) );
				continue;
			}
			
			Search method = null;
			
			switch (ch) {
			case '1':
				method = new BFS();
				break;
			case '2':
				method = new DFS();
				break;
			case '3':
				method = new AStar( new HeuristicManhattan() );
				break;
			case '4':
				method = new AStar( new HeuristicHamming() );
				break;
			}
			
			System.out.println( String.format( "\nSolve with '%s':", method.getTitle() ) );
			System.out.println("Search for solution (wait, please)...\n");
			
			Node result = method.search(taskNode);
			
			System.out.println( "Time      : " + method.getTimeInfo() );
			System.out.println( "Processed : " + method.getProcessedInfo() );
			System.out.println( "Closed    : " + method.getClosedInfo() );
			System.out.println( "Max.depth : " + method.getDepthInfo() );
			
			if (result != null) {
				System.out.println("\nResult stored into window...");
				Common.showFrame( new InfoWindow(result, method) );
			} else 
				System.out.println("=== No solution! ===");
		}
		
		System.out.println("Bye...");
		//*/
	}
	
	/*
	 * Read text file to string 
	 */
	private String getTaskStr(String source) {
		String res = null;
		
		try ( FileInputStream fr = new FileInputStream(source) ) {
			byte[] str = new byte[ fr.available() ];
			fr.read(str);
			fr.close();
			res = new String(str);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return res;
	}
	
	/*
	 * Parse source string with placement to long value
	 */
	private Node createTaskNode(String info) {
		String[] lines = info.split("\n");
		String[] dim = lines[0].trim().replaceAll("( |\t)+", " ").split(" ");
		
		if (dim.length < 2) {
			System.out.println("Wrong dimensions count!");
			return null;
		}
		
		Common.setVars(dim);
		
		int rows = Common._ROWS;
		int cols = Common._COLS;
		
		if (rows * cols == 0) {
			System.out.println("Zero dimension(s)!");
			return null;
		}
		
		if (rows > lines.length - 1) {
			System.out.println("Wrong number of rows!");
			return null;
		}
		
		if ( (rows < 2) || (rows > 4) ) {
			System.out.println("Allowed number of rows - 2, 3 or 4!");
			return null;
		}
		
		if ( (cols < 2) || (cols > 4) ) {
			System.out.println("Allowed number of cols - 2, 3 or 4!");
			return null;
		}
		
		boolean res = true;
		
		int maxValue = rows * cols - 1;
		HashSet<Integer> hashSet = new HashSet<>();
		byte[][] x = new byte[rows][cols];
		
        for (int i = 0; i < rows; i++) {
            String line = lines[i+1].trim().replaceAll("( |\t)+", " ");
            String[] coefs  = line.split(" ");
            
            if (coefs.length < cols) {
            	System.out.println( String.format("Wrong number of elements in row %d!", i+1) );
            	res = false;
            	break;
            }
            
            for (int j = 0; j < cols; j++) {
            	int elem = Common.parseInt( coefs[j] );
            	
            	if ( hashSet.contains(elem) ) {
            		System.out.println( String.format("Duplicates found (%d)!", elem) );
            		res = false;
                	break;
            	}
            	
            	if ( (elem < 0) || (elem > maxValue) ) {
            		System.out.println( String.format("Wrong value found (%d)!", elem));
            		res = false;
                	break;
            	}
            	
            	hashSet.add(elem);
            	x[i][j] = (byte) elem;
            }
        }
        
        return res ? new Node(x) : null;
	}
	
	/*
	 * Check if solution exists
	 */
	private boolean checkSolvability(Node node) {
		int rows = Common._ROWS;
		int cols = Common._COLS;
		
		if ( (rows != 4) || (cols != 4) ) return true;  
		
		// for 4x4 puzzle only
		int dim = rows * cols;
		byte[] x = new byte[dim];
		
		int sk = 0;
		byte[][] y = node.fromLong();
		for (byte i = 0; i < rows; i++)
			for (byte j = 0; j < cols; j++)
				if (y[i][j] == 0)
					sk = i + 1;
		
		for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
            	x[i * cols + j] = y[i][j];
            
		
		for (int i = 0; i < dim; i++)
			if ( x[i] != 0)
				for (int j = 0; j < i; j++)
					if (x[j] > x[i])
						sk++;
		
		return sk % 2 == 0;
	}

}