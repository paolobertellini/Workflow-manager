package ui;

import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import javax.swing.JTabbedPane;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import elements.Description;
import elements.Node;
import elements.Transition;

import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.SwingConstants;
import java.awt.Insets;
import javax.swing.LayoutStyle.ComponentPlacement;

// TODO: Auto-generated Javadoc
/**
 *  Il pannello relativo ad un nodo.
 */
public class NodePanel extends JPanel {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The text field. */
	private JTextField textField;
	
	/** La posizione in cui si trova il pannello. */
	public Point location;
	
	/** L'evento pressione del mouse (necessario per drag and drop). */
	public MouseEvent pressed;
	
	/** Il nodo associato al pannello. */
	private Node node;

	/** La tabbed pane delle descrizioni associate al nodo. */
	private JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
	
	/** Il listener state per la selezione dei nodi. */
	static boolean listenerState = false;

	/**
	 * Crea un node panel.
	 *
	 * @param node il nodo associato
	 */
	public NodePanel(Node node) {
		
		this.node=node;
		
		// proprietà del pannello
		setPreferredSize(new Dimension(200, 300));
		setMinimumSize(new Dimension(200, 300));
		setLocation(node.getPosX(), node.getPosY());
		setSize(new Dimension(200, 300)); //150, 265
		setVisible(true);
		setBorder(new LineBorder(new Color(255, 255, 0), 2));
		
		// pannello giallo (solo estetico)
		JPanel yellowPanel = new JPanel();
		yellowPanel.setBorder(new TitledBorder(null, "Node code", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		yellowPanel.setBackground(Color.YELLOW);
		
		
		/*
		 *  LOGO PANEL (contiene i listener per:
		 *  				- la selezione del nodo
		 *  				- il drag and drop)
		 */
		JPanel logoPanel = new JPanel();
		
		// LISTENER PRESSIONE SUL LOGO PANEL
		
		logoPanel.addMouseListener(new MouseAdapter() {
			
			public void mousePressed(MouseEvent arg0) {
				
				// DRAG and DROP inizio dello spostamento
				if(!listenerState) {
					pressed = arg0;
				}
				
				// selezione PRIMO nodo della NUOVA TRANSIZIONE
				else {
					
				Node nodeStart = null;
				
				if(!node.nodeWF.isStartTaken() && listenerState) {
					
					// assegnazione nodo
					JPanel source = (JPanel) arg0.getSource();
		        	NodePanel np = (NodePanel) source.getParent();
		        	nodeStart = np.getNode();
		        	
		        	// sistemazione arrow panel
		        	node.nodeWF.getPanel().ap = new ArrowPanel(nodeStart.getCenter(nodeStart.getPosX(), nodeStart.getPosY(), nodeStart.getPanel().getWidth(), nodeStart.getPanel().getHeight()),nodeStart.getCenter(nodeStart.getPosX(), nodeStart.getPosY(), nodeStart.getPanel().getWidth(), nodeStart.getPanel().getHeight()) );
		            node.nodeWF.getPanel().add(node.nodeWF.getPanel().ap);
		            node.nodeWF.getPanel().ap.setSize(node.nodeWF.panel.getSize());
		            
		            // attivazione listener per secondo nodo
		        	WorkFlowPanel.setListenerState(true);
		        	
		        	node.nodeWF.getToolTextField().setText("Select the logo of the second node to link with, ESC to undo");
		        	
		        	// salvataggio nodo di partenza
		            node.nodeWF.setStart(nodeStart);
		            node.nodeWF.setStartTaken(true);    
		        } 
			
				
				// selezione SECONDO nodo della NUOVA TRANSIZIONE
				else if(node.nodeWF.isStartTaken() && !node.nodeWF.isEndTaken() && listenerState) {
					
					// nascondo freccia provvisoria
					node.nodeWF.panel.ap.setVisible(false);
					
					// disattivo listener
					WorkFlowPanel.setListenerState(false);
					
					// assegnazione nodo
					JPanel source = (JPanel) arg0.getSource();
					NodePanel np = (NodePanel) source.getParent();
		        	Node nodeEnd = np.getNode();
		           
		        	// salvataggio nodo di arrivo
		            node.nodeWF.setEnd(nodeEnd);
		            node.nodeWF.setEndTaken(true);
		            listenerState = false;
		            
		            // ERRORE nodo di partenza e di arrivo coincidono
		            if(node.nodeWF.getStart().equals(node.nodeWF.getEnd())) {
		        		node.nodeWF.getToolTextField().setText("Cannot link with the starting node; choose another one, ESC to undo");
		        		node.nodeWF.setEndTaken(false);
			            listenerState = true;
			        	node.nodeWF.panel.ap.setVisible(true);
						WorkFlowPanel.setListenerState(true);
						return;
		        	}
		            
		            // controllo esistenza della freccia (già altre transizioni tra i due nodi)
		            int freccia = -1;
		            for(Transition t : node.nodeWF.transitions) {
		            	if (((t.n1==node.nodeWF.getStart()) && (t.n2 ==node.nodeWF.getEnd()))||((t.n2==node.nodeWF.getStart()) && (t.n1 ==node.nodeWF.getEnd())) ) {
		            		freccia = t.getTranIndex();
		            		continue;
		            		
		            	}
		            }
		            
		            // creazione della NUOVA TRANSIZIONE
		            Transition t= new Transition(node.nodeWF.getStart(), node.nodeWF.getEnd(), freccia);
		            node.nodeWF.transitions.add(t);
		            t.setTranIndex(node.nodeWF.transitions.size() - 1);
		            node.nodeWF.panel.add(node.nodeWF.transitions.get(node.nodeWF.transitions.size()-1).panel);
		            node.nodeWF.getPanel().moveToFront(node.nodeWF.transitions.get(node.nodeWF.transitions.size()-1).panel);
		            node.nodeWF.transitions.get(node.nodeWF.transitions.size()-1).panel.revalidate();
		            node.nodeWF.getPanel().revalidate();
		        	node.nodeWF.getToolTextField().setText("Insert transition code to add new nodes or transitions");
		        	
		        	// aggiornamento stato del WF
		        	node.nodeWF.setSaved(false);
					node.nodeWF.setEditable(false);
				}
			}
		}
	});
		
		
		// LISTENER DRAG and DROP fine trascinamento
		
		logoPanel.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent arg0) {
				
				// controllo di non stare selezionando i nodi di una transizione
				if(!listenerState) {
					
					// calcolo posizione
					location = getLocation();
					int x = location.x - 25  + arg0.getX();
			        int y = location.y - 30 + arg0.getY();
			        setLocation(x, y);
			        
			        // aggiorno gli attributi del nodo associato
			        node.setPosX(x);
			        node.setPosY(y);
			       
			        // aggiorno le frecce collegate
				    for(Transition t: node.nodeWF.transitions) {
						if(node.getNodeCode() == t.n1.getNodeCode()) 
							t.paintFirstArrow();
						if(node.getNodeCode() == t.n2.getNodeCode()) {
							t.paintSecondArrow();
						}
					}
			        
					//  controllo dimensioni della vista e del WF
					
					// controllo LARGHEZZA
					if (getLocation().getX() > node.nodeWF.getPanel().getPreferredSize().width - 200) {
						setLocation(x+200, y);
						node.nodeWF.getPanel().apply(new Dimension(x + 500, node.nodeWF.getPanel().getPreferredSize().height));
						
					}
					
					// controllo ALTEZZA
					if (getLocation().getY() > node.nodeWF.getPanel().getPreferredSize().height - 300) {
						setLocation(x, y + 200);
						node.nodeWF.getPanel().apply(new Dimension(node.nodeWF.getPanel().getPreferredSize().width, y + 500));
					}
					
					// controllo bordo superiore
					if(getLocation().getY() <= 0.0 ) {
						setLocation(x,0);
						node.setPosY(0);
						if(getLocation().getX() <= 0.0) {
							setLocation(0,0);
							node.setPosX(0);
							node.setPosY(0);
						}
					}
					// controllo bordo sinistro
					else if(getLocation().getX() <= 0.0 ) {
						setLocation(0,y);
						node.setPosX(0);
					}
					
					// aggiornamento dimensioni frecce
					for(Transition t: node.nodeWF.transitions) {
						t.getArrow1().setSize(t.tranWF.panel.getSize());
						t.getArrow2().setSize(t.tranWF.panel.getSize());
					}
					
					// AUTOSCROLL
					node.nodeWF.getPanel().scrollRectToVisible(new Rectangle(x, y, 200,100));
				}
			}
		});
		
		/*
		 *  bottone NEW DESCRIPTION
		 */
		JPanel descriptionPanel = new JPanel();
		JButton btnNewLanguage = new JButton("New description");
		btnNewLanguage.setMargin(new Insets(2, 8, 2, 8));
		btnNewLanguage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Description d = new Description(tabbedPane, "","", node);
				node.getDescriptions().add(d);
				tabbedPane.addTab(d.getLanguage(), null, d.getPanel(), null);
				tabbedPane.setSelectedIndex(tabbedPane.indexOfTab("--"));
				tabbedPane.setSelectedComponent(d.getPanel());
			}
		});
		
		
		/*
		 *  bottone DELETE NODE
		 */
		JButton btnDelete = new JButton("Delete node");
		btnDelete.setMargin(new Insets(2, 8, 2, 8));
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Non posso eliminare i nodi origine o destinazione di transizioni
				for(Transition t: node.nodeWF.transitions) {
					if(node.equals(t.n1)|| node.equals(t.n2)) {
						node.nodeWF.getToolTextField().setText("Cannot delete transition source or destination nodes");
						return;
					}
				}
				if(node.nodeWF.getStart() != null) {
					if (node.nodeWF.getStart().equals(node)) {
						node.nodeWF.getToolTextField().setText("Cannot delete transition source or destination nodes");
						return;
					}
				}
				node.nodeWF.deleteNode(node.getNodeCode());
				node.nodeWF.getToolTextField().setText("Select N to add a node, T to add a transition");
			}
		});
		
		
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(yellowPanel, GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE)
							.addGap(1)
							.addComponent(logoPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(14)
							.addComponent(btnNewLanguage, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE)
							.addGap(5)
							.addComponent(btnDelete, GroupLayout.PREFERRED_SIZE, 78, GroupLayout.PREFERRED_SIZE)))
					.addGap(12))
				.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
					.addGap(4)
					.addComponent(descriptionPanel, GroupLayout.PREFERRED_SIZE, 188, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(0, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(yellowPanel, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE)
						.addComponent(logoPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(descriptionPanel, GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnDelete)
						.addComponent(btnNewLanguage))
					.addGap(5))
		);
		yellowPanel.setLayout(new GridLayout(1, 0, 0, 0));
		
		/*
		 *  pannello NODE CODE
		 */
		textField = new JTextField();
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		if(node.isDef())
			textField.setEditable(false);
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				node.setNodeCode(textField.getText());
				node.nodeWF.getToolTextField().setText("Press enter to confirm node code");
				if(arg0.getKeyChar() =='\n' ) {
					for (Node n: node.nodeWF.nodes) {	
						if((n.getNodeCode().equals(node.getNodeCode()) && !(n.equals(node)))){
							textField.setEditable(true);
							n.nodeWF.getToolTextField().setText(new String("Node code already existing"));
							break;
						}
						else {
							textField.setEditable(false);
							node.setDef(true);
							node.nodeWF.setEditable(true);
							n.nodeWF.getToolTextField().setText("Press nodes or transitions grey section to move them");
					    }
					}
				}
			}
		});
		textField.setFont(new Font("Calibri", Font.PLAIN, 21));
		textField.setText(node.getNodeCode());
		yellowPanel.add(textField);
		textField.setColumns(1);
		
		// TABBED PANEL descrizioni
		descriptionPanel.setLayout(new GridLayout(1, 0, 0, 0));
		descriptionPanel.add(tabbedPane);
		logoPanel.setLayout(new GridLayout(0, 1, 0, 0));
		
		// LOGO abaco
		JLabel lblNewLabel = new JLabel("");
		logoPanel.add(lblNewLabel);
		Image logo = new ImageIcon(this.getClass().getResource("/abacook.png")).getImage();
		lblNewLabel.setIcon(new ImageIcon(logo));
		setLayout(groupLayout);
	}
	
	/**
	 * GETTERS e SETTERS
	 */

	public JTextField getTextField() {
		return textField;
	}

	public void setTextField(JTextField textField) {
		this.textField = textField;
	}

	public Node getNode() {
		return node;
	}

	public void setNode(Node node) {
		this.node = node;
	}

	public JTabbedPane getTabbedPane() {
		return tabbedPane;
	}

	public void setTabbedPane(JTabbedPane tabbedPane) {
		this.tabbedPane = tabbedPane;
	}
	
}
