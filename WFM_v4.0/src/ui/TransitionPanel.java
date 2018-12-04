package ui;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.TitledBorder;

import elements.Transition;

import javax.swing.JTextField;
import java.awt.Point;
import javax.swing.JComboBox;

import javax.swing.JTextPane;


// TODO: Auto-generated Javadoc
/**
 * The Class TransitionPanel.
 */
public class TransitionPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;

	private JTextField textField;
	private JTextField jtf_node_code;
	private JTextArea jta_description;
	private JComboBox cb_language;
	
	/** The node size. */
	private Dimension node_size;
	
	// position
	public int pos_x;
	public int pos_y;
		
	public Point p1;
	public Point p2;
	
	public Transition transition;

	public TransitionPanel(Transition transition) {
		super();
		
		
		setLocation(transition.getPos_x(), transition.getPos_y());
		setSize(225, 100); //225, 100
		setVisible(true);
		repaint();
		revalidate();
		
		
		/*
		 *  DRAG
		 
		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent drag) {
				setLocation(drag.getLocationOnScreen());
				transition.setPos_x((int) getLocation().getX());
				transition.setPos_y((int) getLocation().getY());
			}
		});
		*/
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.YELLOW);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Description", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(5)
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE))
		);
		
		JTextPane textPane = new JTextPane();
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addComponent(textPane, GroupLayout.DEFAULT_SIZE, 213, Short.MAX_VALUE)
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addComponent(textPane, GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
		);
		panel_1.setLayout(gl_panel_1);
		
		textField = new JTextField();
		textField.setColumns(10);
		
		JComboBox comboBox = new JComboBox();
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(5)
					.addComponent(textField)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(comboBox, 0, 118, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(5)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(25))
		);
		panel.setLayout(gl_panel);
		setLayout(groupLayout);

	}

	public TransitionPanel() {
		super();
		
	}
	
	
}





		