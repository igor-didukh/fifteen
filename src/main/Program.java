package main;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

@SuppressWarnings("unused")
public class Program {
	
	private final static int ROWS = 4;
	private final static int COLS = 4;
	
	public static void main(String[] args) {
		String fmt = "%2d";
		String info = String.format(fmt + " " + fmt + "\n", ROWS, COLS);
		
		ArrayList<Integer> lst = new ArrayList<Integer>(); 
		for (int i = 0; i < ROWS * COLS; i++)
			lst.add(i);
		
		for (int i = 1; i <= ROWS; i++) {
			for (int j = 1; j <= COLS; j++) {
				int ind = (int)( Math.random() * lst.size() );
				
				int elem = lst.get(ind);
				info += String.format(fmt, elem) + (j < COLS ? " " : "");
				lst.remove(ind);
			}
			
			info += i < ROWS ? "\n" : "";
		}
		
		//new Solution(info);
		new Solution();
		
		/*
		try ( FileWriter fw = new FileWriter("D:\\Temp\\15.txt") ) {
            fw.write(info);
            fw.close();
            new Solution();
        }
        catch ( IOException e ) {
            System.out.println("Error saving info!");
        }
        */
        
		
	}

}