package ui;

import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.SwingConstants;

import javax.swing.border.LineBorder;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.border.TitledBorder;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.UIManager;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ItemListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.Dimension;

import elements.Node;
import elements.Transition;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;


public class NodePanel extends JPanel{
	
	private static final long serialVersionUID = 1L;
	
	private JTextField jtf_node_code;
	private JTextArea jta_description;
	//@SuppressWarnings("rawtypes") 
	private JComboBox<String> cb_language;
	
	public Node node;
	
	boolean listener_state = false;

	
	/*
	 *  CONSTRUCTORS
	 */
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public NodePanel(Node node) {
					
		this.node=node;
		
		/*
		 *  new transition listener
		 */
		
		addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if(!node.nodeWF.start_taken && listener_state) {
		        	NodePanel source = (NodePanel) e.getSource();
		        	Node node = source.getNode();
		            System.out.println("Letto: " + node);
		            node.nodeWF.setStart(node);
		            node.nodeWF.start_taken=true;
		            listener_state=false;
		        } 
				else if(node.nodeWF.start_taken && !node.nodeWF.end_taken && listener_state) {
		        	NodePanel source = (NodePanel) e.getSource();
		        	Node node = source.getNode();
		            System.out.println("Letto 2: " + node);
		            node.nodeWF.setEnd(node);
		            node.nodeWF.end_taken=true;
		            for(Node n: node.nodeWF.nodes)
		            n.panel.setListener_state(false);
		            int freccia = -1;
		            for(Transition t : node.nodeWF.transitions) {
		            	if ((t.n1==node.nodeWF.start) && (t.n2 ==node.nodeWF.end)) {
		            		freccia = t.getTranIndex();
		            		System.out.println(freccia);
		            	}
		            }
		            node.nodeWF.transitions.add(new Transition(node.nodeWF.start, node.nodeWF.end, freccia));
		            node.nodeWF.panel.add(node.nodeWF.transitions.get(node.nodeWF.transitions.size()-1).panel);
		            node.nodeWF.transitions.get(node.nodeWF.transitions.size()-1).panel.revalidate();
		        } 
		    }
		});
		
		/*
		 *  PANEL SETTINGS
		 */
				
		setPreferredSize(new Dimension(150, 265));
		setMinimumSize(new Dimension(150, 265));
		setLocation(node.getPos_x(), node.getPos_y());
		setSize(new Dimension(168, 266)); //150, 265
		setVisible(true);
		
		/*
		 * DRAG
		 */
		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent drag) {
				setLocation(drag.getLocationOnScreen());
				node.setPos_x((int) getLocation().getX());
				node.setPos_y((int) getLocation().getY());
				
				//refresh transitions
				for(Transition t: node.nodeWF.transitions) {
					if(node.node_id == t.n1.node_id || node.node_id == t.n2.node_id)
						t.paintTransition();
				}
			}
		});
		
		//yellow panel
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(Color.YELLOW));
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 150, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 264, Short.MAX_VALUE)
		);
		
		//NODE NAME panel
		JPanel panel = new JPanel();
		panel.setMinimumSize(new Dimension(150, 265));
		panel.setBorder(new TitledBorder(null, "Node name", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBackground(Color.YELLOW);
		panel.setForeground(Color.YELLOW);
		
		//LANGUAGE panel
		JPanel panel_2 = new JPanel();
		panel_2.setMinimumSize(new Dimension(150, 265));
		panel_2.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Select language", TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLACK));
		
		//DESCRIPTION panel
		JPanel panel_3 = new JPanel();
		panel_3.setMinimumSize(new Dimension(150, 20));
		panel_3.setBorder(new TitledBorder(null, "Description", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		
		/*
		 * DELETE NODE
		 */
		JButton btnDeleteNode = new JButton("Delete node");
		btnDeleteNode.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				node.nodeWF.deleteNode(node.getNode_code());
			}
		});
		//delete node position
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addComponent(btnDeleteNode)
					.addGap(5))
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGap(1)
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, 147, Short.MAX_VALUE))
				.addComponent(panel_2, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
				.addComponent(panel_3, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGap(5)
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE)
					.addGap(5)
					.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE)
					.addGap(2)
					.addComponent(panel_3, GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE)
					.addGap(2)
					.addComponent(btnDeleteNode)
					.addGap(5))
		);
		
		/*
		 * DESCRIPTION
		 */
		jta_description = new JTextArea();
		jta_description.setText(node.getDesc());
		jta_description.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				node.setDesc(jta_description.getText());
			}
		});
		//description position and properties
		jta_description.setWrapStyleWord(true);
		jta_description.setLineWrap(true);
		GroupLayout gl_panel_3 = new GroupLayout(panel_3);
		gl_panel_3.setHorizontalGroup(
			gl_panel_3.createParallelGroup(Alignment.LEADING)
				.addComponent(jta_description, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
		);
		gl_panel_3.setVerticalGroup(
			gl_panel_3.createParallelGroup(Alignment.LEADING)
				.addComponent(jta_description, GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
		);
		panel_3.setLayout(gl_panel_3);
		
		/*
		 * SELECT LANGUAGE
		 */
		cb_language = new JComboBox();
		cb_language.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				node.setLanguage((String) cb_language.getSelectedItem());
			}
		});
		cb_language.addMouseListener(new MouseAdapter() {
			//aggiungi 
		});
		//language properties and position
		cb_language.setModel(new DefaultComboBoxModel(new String[] {"Italiano\t\t[ITA]", "English\t\t[ENG]", "Espa\u00F1ol, Castellano \t[SPA]", "Fran\u00E7ais, Langue Fran\u00E7aise \t[FRE]", "Deutsch \t[GER]"}));
		cb_language.setToolTipText("Italiano\t[ITA]\r\nEnglish\t[ENG]\r\nEspa\u00F1ol, Castellano [SPA]\r\nFran\u00E7ais, Langue Fran\u00E7aise [FRE]\r\nDeutsch [GER]");
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addComponent(cb_language, 0, 136, Short.MAX_VALUE)
		);
		gl_panel_2.setVerticalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addComponent(cb_language, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panel_2.setLayout(gl_panel_2);
		
		/*
		 * NODE CODE
		 */
		jtf_node_code = new JTextField();
		jtf_node_code.setText(node.getNode_code());
		jtf_node_code.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				node.setNode_code(jtf_node_code.getText());
			}
		});
		jtf_node_code.setHorizontalAlignment(SwingConstants.CENTER);
		jtf_node_code.setFont(new Font("Century Gothic", Font.PLAIN, 18));
		jtf_node_code.setColumns(10);
		//node code position
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(jtf_node_code, GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addComponent(jtf_node_code, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);
		panel_1.setLayout(gl_panel_1);
		setLayout(groupLayout);
	}
	

	public Node getNode() {
		return node;
	}
	public void setNode(Node node) {
		this.node = node;
	}

	public boolean isListener_state() {
		return listener_state;
	}
	public void setListener_state(boolean listener_state) {
		this.listener_state = listener_state;
	}
			
}