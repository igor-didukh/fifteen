package output;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import main.Common;
import node.Node;

import javax.swing.JTextPane;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.FlowLayout;
import java.awt.GridLayout;

public class PlayWindow extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private final String PREV = "A", NEXT = "D", UP = "W", DOWN= "S", SAVE = "SAVE";
	
	private int step = 0;
	private Node node;
	
	private JPanel contentPane;
	private JButton btnPrev, btnNext, btnUp, btnDown, btnSave;
	private JTextPane textPane;
	private JTextField txtStep;
	private JLabel label;
	private JLabel label_1;
	private JLabel label_2;
	
	public PlayWindow(Node node) {
		this.node = node;
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("Let's play");
		setBounds(0, 0, 320, 400);
		setResizable(false);
		
		contentPane = new JPanel();
		contentPane.setBackground(new Color(238, 238, 238));
		contentPane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panelStep = new JPanel();
		contentPane.add(panelStep, BorderLayout.NORTH);
		panelStep.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblStep = new JLabel("Step #");
		lblStep.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panelStep.add(lblStep);
		
		txtStep = new JTextField();
		txtStep.setEditable(false);
		txtStep.setFont(new Font("Courier New", Font.PLAIN, 17));
		txtStep.setHorizontalAlignment(SwingConstants.CENTER);
		panelStep.add(txtStep);
		
		textPane = new JTextPane();
		textPane.setEditable(false);
		textPane.setFont(new Font("Courier New", Font.BOLD, 42));
		
		JScrollPane scrollPane = new JScrollPane(textPane);
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		JPanel panelButtons = new JPanel();
		contentPane.add(panelButtons, BorderLayout.SOUTH);
		panelButtons.setLayout(new GridLayout(0, 3, 0, 0));
		
		btnUp = new JButton("^");
		btnUp.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnUp.setActionCommand(UP);
		btnUp.addActionListener(this);
		
		label = new JLabel("");
		panelButtons.add(label);
		panelButtons.add(btnUp);
		
		btnDown = new JButton("v");
		btnDown.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnDown.setActionCommand(DOWN);
		btnDown.addActionListener(this);
		
		btnNext = new JButton(">");
		btnNext.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnNext.setActionCommand(NEXT);
		btnNext.addActionListener(this);
		
		btnSave = new JButton("Save...");
		btnSave.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnSave.setActionCommand(SAVE);
		btnSave.addActionListener(this);
		
		btnPrev = new JButton("<");
		btnPrev.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnPrev.setActionCommand(PREV);
		btnPrev.addActionListener(this);
		
		label_1 = new JLabel("");
		panelButtons.add(label_1);
		panelButtons.add(btnPrev);
		panelButtons.add(btnSave);
		panelButtons.add(btnNext);
		
		label_2 = new JLabel("");
		panelButtons.add(label_2);
		panelButtons.add(btnDown);
		
		showInfo();
		
	}
	
	private void showInfo() {
		txtStep.setText("" + String.format( "%2d", step ) );
		textPane.setText( node.toString() );
	}
	
	/*
	 * For human play
	 */
	public void oneMove(char direction) {
		byte rows = (byte) Common._ROWS;
		byte cols = (byte) Common._COLS;
		byte m = 0, n = 0;
		
		byte[][] y = node.fromLong();
		
		for (byte i = 0; i < rows; i++)
			for (byte j = 0; j < cols; j++)
				if (y[i][j] == 0) {
					m = i;
					n = j;
				}
		
		byte m1 = m;
		byte n1 = n;
		
		switch (direction) {
		case 'W':
			if (m1 > 0) m1--;
			break;
		case 'A':
			if (n1 > 0) n1--;
			break;
		case 'S':
			if (m1 < Common._ROWS - 1) m1++;
			break;
		case 'D':
			if (n1 < Common._COLS - 1) n1++;
			break;
		}
		
		if ( (m1 == m) && (n1 == n) )
			return;
		
		y[m][n] = y[m1][n1];
		y[m1][n1] = 0;
		
		node.setField( node.toLong(y) );
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		
		if (action == SAVE) {
			FileNameExtensionFilter filter = new FileNameExtensionFilter("*.txt","*.*");
	        JFileChooser fc = new JFileChooser();
	        fc.setFileFilter(filter);
	        if ( fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION ) {
	            try ( FileWriter fw = new FileWriter(fc.getSelectedFile() + ".txt") ) {
	            	fw.write( String.format("%2d %2d\n", Common._ROWS, Common._COLS) );
	                fw.write( node.toString() );
	                fw.close();
	            }
	            catch ( IOException ex ) {
	                ex.printStackTrace();
	            }
	        }
		} else {
			long bField = node.getField();
			oneMove( action.charAt(0) );
			long workField = node.getField(); 
			
			if (workField != bField) {
				step++;
				showInfo();
			}
				
		}
	}
	
}