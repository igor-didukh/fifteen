package output;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import main.Common;
import methods.Search;
import node.Node;

import javax.swing.JTextPane;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.FlowLayout;
import javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;

public class InfoWindow extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private final String FIRST = "FIRST", PREV = "PREV", NEXT = "NEXT", LAST = "LAST", SAVE = "SAVE";
	
	private ArrayList<Node> solution = new ArrayList<Node>();
	private int size;
	private int pos;
	
	private JPanel contentPane;
	private JButton btnFirst, btnPrev, btnNext, btnLast, btnSave;
	private JTextPane textField;
	private JTextField txtPos, txtSize;
	private JTextArea textInfo;
	
	public InfoWindow(Node result, Search method) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle( method.getTitle() );
		setBounds(0, 0, 550, 350);
		setResizable(false);
		
		contentPane = new JPanel();
		contentPane.setBackground(new Color(238, 238, 238));
		contentPane.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panelStep = new JPanel();
		panelStep.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		contentPane.add(panelStep, BorderLayout.NORTH);
		panelStep.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblStep = new JLabel("Step #");
		lblStep.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panelStep.add(lblStep);
		
		txtPos = new JTextField();
		txtPos.setEditable(false);
		txtPos.setFont(new Font("Courier New", Font.PLAIN, 17));
		txtPos.setHorizontalAlignment(SwingConstants.CENTER);
		panelStep.add(txtPos);
		
		JLabel label = new JLabel("/");
		label.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panelStep.add(label);
		
		txtSize = new JTextField();
		txtSize.setEditable(false);
		txtSize.setFont(new Font("Courier New", Font.PLAIN, 17));
		txtSize.setHorizontalAlignment(SwingConstants.CENTER);
		panelStep.add(txtSize);
		
		textField = new JTextPane();
		textField.setEditable(false);
		textField.setFont(new Font("Courier New", Font.BOLD, 42));
		
		JScrollPane scrollPane = new JScrollPane(textField);
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		JPanel panelBottom = new JPanel();
		panelBottom.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		contentPane.add(panelBottom, BorderLayout.SOUTH);
		
		btnFirst = new JButton("<<");
		btnFirst.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnFirst.setEnabled(false);
		btnFirst.setActionCommand(FIRST);
		btnFirst.addActionListener(this);
		panelBottom.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		panelBottom.add(btnFirst);
		
		btnPrev = new JButton("<");
		btnPrev.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnPrev.setActionCommand(PREV);
		btnPrev.addActionListener(this);
		panelBottom.add(btnPrev);
		
		btnNext = new JButton(">");
		btnNext.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnNext.setActionCommand(NEXT);
		btnNext.addActionListener(this);
		panelBottom.add(btnNext);
		
		btnSave = new JButton("Save to file...");
		btnSave.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnSave.setActionCommand(SAVE);
		btnSave.addActionListener(this);
		
		btnLast = new JButton(">>");
		btnLast.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnLast.setEnabled(true);
		btnLast.setActionCommand(LAST);
		btnLast.addActionListener(this);
		panelBottom.add(btnLast);
		panelBottom.add(btnSave);
		
		textInfo = new JTextArea();
		textInfo.setBackground( new Color(240,240,240) );
		contentPane.add(textInfo, BorderLayout.EAST);
		textInfo.setFont(new Font("Courier New", Font.PLAIN, 11));
		
		String s = "";
		s += "  Time      : " + method.getTimeInfo() + "  \n";
		s += "  Processed : " + method.getProcessedInfo() + "  \n";
		s += "  Closed    : " + method.getClosedInfo() + "  \n";
		s += "  Max.depth : " + method.getDepthInfo() + "  ";
		
		textInfo.setText(s);
		
		for ( Node p = result; p != null; p = p.getParent() )
			solution.add(p);
		
		size = solution.size() - 1;
		pos = size;
		
		showField();
		
	}
	
	private void showField() {
		txtPos.setText("" + String.format( "%3d", (size-pos) ) );
		txtSize.setText("" + size);
		textField.setText( "" + solution.get(pos) );
		
		btnFirst.setEnabled(pos != size);
		btnPrev.setEnabled(pos != size);
		btnNext.setEnabled(pos != 0);
		btnLast.setEnabled(pos != 0);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		
		switch (action) {
		
		case FIRST:
			pos = solution.size() - 1;
			break;
		
		case PREV:
			if (pos < solution.size()-1) pos++;
			break;
		
		case NEXT:
			if (pos > 0) pos--;
			break;
			
		case LAST:
			pos = 0;
			break;
			
		case SAVE:
			FileNameExtensionFilter filter = new FileNameExtensionFilter("*.txt","*.*");
	        JFileChooser fc = new JFileChooser();
	        fc.setFileFilter(filter);
	        if ( fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION ) {
	            try ( FileWriter fw = new FileWriter(fc.getSelectedFile() + ".txt") ) {
	            	fw.write( String.format("%2d %2d\n", Common._ROWS, Common._COLS) );
	                fw.write( solution.get(pos).print() );
	                fw.close();
	            }
	            catch ( IOException ex ) {
	                ex.printStackTrace();
	            }
	        }
			break;
			
		}
		
		showField();
	}
	
}