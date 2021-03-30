package ui;

import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.JTabbedPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.border.TitledBorder;

import elements.Description;
import elements.Transition;

import javax.swing.border.LineBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.SwingConstants;
import java.awt.Font;

// TODO: Auto-generated Javadoc
/**
 *  Il pannello relativo a una transizione.
 */
public class TransitionPanel extends JPanel {

	/**Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** textField del pannello della transizione in cui verrà inserito il codice*/
	private JTextField textField;
	
	/** Locazione della transizione nel pannello */
	private Point location;
	
	/** MouseEvent pressed: permette di prendere la posizione su cui è stata cliccato il mouse all'interno dell'area del pannello
	 * che permette di muovere la transizione nel WorkFlowPanel*/
	private MouseEvent pressed;
	
	/** Riferimento alla transizione associata al pannello*/
	private Transition transition;

	/** In ogni pannello di transizioni è contenuto un tabbed pane per la gestione delle varie descrizioni (ad ogni descrizione è
	 * associata una scheda del tabbed pane) */
	private JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
	
	/** Stato del listener del pannello della transizione*/
//	static boolean listenerState = false;
	
	/**
	 * Costruttore del TransitionPanel che riceve come parametro la transizione
	 */
	public TransitionPanel(Transition transition) {
		
		this.setTransition(transition);
		
		setPreferredSize(new Dimension(320, 150));
		setMinimumSize(new Dimension(320, 150));
		setLocation(transition.getPosX(), transition.getPosY());
		setSize(new Dimension(320, 150)); //150, 265
		setVisible(true);
		
		setBorder(new LineBorder(Color.YELLOW, 2));
		
		JPanel panel = new JPanel();
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Transition code", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBackground(Color.YELLOW);
		
		JPanel panel_2 = new JPanel();
		
		JPanel panel_3 = new JPanel();
		panel_3.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent arg0) {
				//prendo la posizione del pannello nel WorkflowPanel
				location = getLocation();
				int x = location.x - pressed.getX() + arg0.getX();
		        int y = location.y - pressed.getY() + arg0.getY();		
		        setLocation(x, y);
		        transition.setPosX(x);
		        transition.setPosY(y);
		     // blocco il movimento del pannello della transizione: non può andare oltre il punto (0,0)
		        if(getLocation().getY() <= 0.0 ) {
					setLocation(x,0);
					transition.setPosY(0);
					if(getLocation().getX() <= 0.0) {
						setLocation(0,0);
						transition.setPosX(0);
						transition.setPosY(0);
					}
				}
				else if(getLocation().getX() <= 0.0 ) {
					setLocation(0,y);
					transition.setPosX(0);
				}
		      
		        //ridisegno i pannelli che rappresentano le frecce associate alla transizione
		        transition.paintFirstArrow();
				transition.paintSecondArrow();
				
				/* Se il pannello della transizione va in una posizione che supera la larghezza del pannello del WorkFlow, 
				 * aumento la dimensione di quest'ultimo */
				
				if (getLocation().getX() > transition.tranWF.getPanel().getPreferredSize().width - 200) {
					setLocation(x+200, y);
					transition.tranWF.getPanel().apply(new Dimension(x + 500, transition.tranWF.getPanel().getPreferredSize().height));
					
				}
				
				/* Se il pannello della transizione va in una posizione che supera l'altezza del pannello del WorkFlow, 
				 * aumento la dimensione di quest'ultimo */
				if (getLocation().getY() > transition.tranWF.getPanel().getPreferredSize().height - 300) {
					setLocation(x, y + 200);
					transition.tranWF.getPanel().apply(new Dimension(transition.tranWF.getPanel().getPreferredSize().width, y + 500));
				}
				
				// Aggiorno le dimensione dei pannelli rappresentanti le freccie
				for(Transition t: transition.tranWF.transitions) {
					t.getArrow1().setSize(t.tranWF.panel.getSize());
					t.getArrow2().setSize(t.tranWF.panel.getSize());
					
				}
				// Scorro il pannello in modo da avere come visibile il rettangolo con l'elemento che sto spostando
				transition.tranWF.getPanel().scrollRectToVisible(new Rectangle(x, y, 200,100));
			}
		});
		panel_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				pressed = arg0;
			}
		});
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(6)
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
								.addComponent(panel_2, GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)))
						.addComponent(panel_3, GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE))
					.addGap(1)
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE)
					.addGap(6))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(6)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(panel, GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
							.addGap(1)
							.addComponent(panel_3, GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
							.addGap(1)
							.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)))
					.addGap(6))
		);
		panel_2.setLayout(new GridLayout(2, 0, 0, 0));
		
		JButton btnNewButton = new JButton("Delete\r\n");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				transition.tranWF.deleteTransition(transition.getTranCode());
			}
		});
		
		JButton btnNewButton_1 = new JButton("New description");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Description d = new Description(tabbedPane, "","", transition);
				transition.getDescriptions().add(d);
				tabbedPane.addTab(d.getLanguage(), null, d.getPanel(), null);
				tabbedPane.setSelectedIndex(tabbedPane.indexOfTab("--"));
				tabbedPane.setSelectedComponent(d.getPanel());
			}
		});
		panel_2.add(btnNewButton_1);
		panel_2.add(btnNewButton);
		panel_1.setLayout(new GridLayout(0, 1, 0, 0));
		// TextField del codice
		textField = new JTextField();
		textField.setFont(new Font("Calibri", Font.PLAIN, 21));
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(textField);
		textField.setText(transition.getTranCode());
		
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				transition.setTranCode(textField.getText());
				transition.tranWF.getToolTextField().setText("Press enter to confirm node code");
				/* nel momento in cui viene premuto il tasto ENTER, il nodo della transizione viene confermato e quest'ultima
				 * diventa definitva; le transizioni non definitive non vengono salvate */
				if(arg0.getKeyChar() =='\n' ) {
					for (Transition t: transition.tranWF.transitions) {	
						if((t.getTranCode().equals(transition.getTranCode()) && !(t.equals(transition)))){
							textField.setEditable(true);
							t.tranWF.getToolTextField().setText(new String("Transition code already existing"));
							break;
						}
						else {
							textField.setEditable(false);
							transition.setDef(true);
							transition.tranWF.setEditable(true);
							t.tranWF.getToolTextField().setText("Press nodes or transitions grey section to move them");
					    }
					}
				}
			}
		});
		// Se la transizione è definitiva, il codice non è più modificabile
				if(transition.isDef()) {
					textField.setEditable(false);
				}
		textField.setColumns(10);
		panel.setLayout(new GridLayout(1, 0, 0, 0));
		
		panel.add(tabbedPane);
		setLayout(groupLayout);

	}

	/**
	 * GETTERS AND SETTERS
	 */
	public Transition getTransition() {
		return transition;
	}

	public void setTransition(Transition transition) {
		this.transition = transition;
	}

	public JTabbedPane getTabbedPane() {
		return tabbedPane;
	}

	public void setTabbedPane(JTabbedPane tabbedPane) {
		this.tabbedPane = tabbedPane;
	}

	
}
