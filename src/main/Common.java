package main;

import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Window;
import java.util.Scanner;

import node.Node;

public class Common {
	public static int _ROWS, _COLS;
	public static Node _WIN;
	
	/*
	 * Show frame on the center of screen 
	 */
	public static void showFrame(Window frame) {
		int screenWidth = 0, screenHeight = 0;
		
		GraphicsDevice[] screenDevices = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
        for (GraphicsDevice graphicsDevice : screenDevices) {
            screenWidth = graphicsDevice.getDefaultConfiguration().getBounds().width;
            screenHeight = graphicsDevice.getDefaultConfiguration().getBounds().height;
        }
		
        Rectangle r = frame.getBounds();
		
		int frameWidth = r.width, frameHeight = r.height;
		int posX = (screenWidth - frameWidth) / 2;
		int posY = (screenHeight - frameHeight) / 2 - 40;
		
		frame.setPreferredSize(new Dimension(frameWidth, frameHeight));
		frame.setBounds(posX, posY, r.width, r.height);
		
		frame.setVisible(true);
	}
	
	/*
	 * Init common vars
	 */
	public static void setVars(String[] dim) {
		_ROWS = parseInt(dim[0]);
		_COLS = parseInt(dim[1]);
		
		byte[][] x = new byte[_ROWS][_COLS];
		byte k = 1;
		int max = _ROWS * _COLS;
		
		for (int i = 0; i < _ROWS; i++)
			for (int j = 0; j < _COLS; j++) {
				x[i][j] = (byte) ((byte) k % max);
				k++;
			}
				
		_WIN = new Node(x);
	}
	
	/*
	 * Convert string to int
	 */
	public static int parseInt(String s) {
		int n = 0;
		try {
			n = Integer.parseInt(s);
		} catch (Exception e) {}
		return n;
	}
	
	/**
	 * Allow to make user choice
	 */
	public static char prompt(String prompt, String allowed, String byDefault) {
		boolean ok = false;
		String s = byDefault;
		allowed = allowed.toUpperCase();
		
		@SuppressWarnings("resource")
		Scanner kb = new Scanner(System.in);
		
		do {
			System.out.print( String.format("%s [%s]: ", prompt, byDefault) );
			String snum = kb.nextLine();
			
			if (snum.length() != 0) {
				if ( snum.length() > 1 )
					s = snum.substring(0, 1);
				else
					s = snum;
			}
			s = s.toUpperCase();
			
			if ( allowed.contains(s) )
				ok = true;
		} while (!ok);
		
		return s.charAt(0);
	}
	
}