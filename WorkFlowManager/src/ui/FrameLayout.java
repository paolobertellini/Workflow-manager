package ui;

import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import elements.Node;
import elements.WorkFlow;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JFileChooser;

// TODO: Auto-generated Javadoc
/**
 * Implementa la finestra, il menu e la toolbar della applicazione.
 */
public class FrameLayout extends JFrame {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** Il workflow attivo che l'applicazione sta modificando. */
	private WorkFlow WF = null;
	
	/** Il nodo di partenza di una eventuale transizione. */
	public Node n1;
	
	/** Il nodo di arrivo di una eventuale transizione. */
	public Node n2;
	
	/** Il pannello che contiene l'area di disegno. */
	private JPanel contentPane;	
	
	/** Il display della toolbar che visualizza messaggi che guidano gli utenti. */
	private JTextField textField = new JTextField();

	/**
	 * Esecuzione della applicazione.
	 *
	 * @param args gli argomenti
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrameLayout frame = new FrameLayout();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Creazione della finestra.
	 */
	public FrameLayout() {
		
		/*  impostazione del LOOK AND FEEL 
		 *  (si applica quello di sistema) */
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		// impostazioni relative alla FINESTRA
		
		setTitle("WFManager");
		setPreferredSize(new Dimension(1024, 760));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		/* 					 
		 * 	---- MENU BAR ---- 
		 */ 
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		/* -- FILE MENU -- */
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		/* - NEW WORKFLOW - */
		JMenuItem mntmNew = new JMenuItem("New");
		mntmNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int choise=0;		
				/*  0 non salva il WF attuale
				 *  1 salva il WF attuale */
				
				/* se il file non è vuoto e non è salvato 
				 * chiedo all'utente di scegliere cosa fare */
				if(WF != null && !WF.isSaved()) { 
					Object[] options = {"Create a new WF without saving", "Save the WF"};
					choise = JOptionPane.showOptionDialog(getContentPane(), "The file has not been saved", "Warning!", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);;
				}
				
				// choise=1 -> salvo il WF
				if(choise == 1)	saveWF();
				
				// se è attivo un WF precedente lo cancello
				if(WF != null)
					WF.scrollPanel.setVisible(false);
				
				// creo un nuovo WF vuoto
				newWF(new WorkFlow());
				
			}
		});
		mnFile.add(mntmNew);
		
		/* - OPEN MENU - */
		JMenuItem mntmOpen = new JMenuItem("Open");
		mntmOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// finestra per scegliere file
				JFileChooser chooser = new JFileChooser();
				
				// filtro per aprire solo file .xml
			    FileNameExtensionFilter filter = new FileNameExtensionFilter("XML file(.xml)", "xml");
			    chooser.setFileFilter(filter);
				
			    int returnVal = chooser.showOpenDialog(getParent());
			    
			    // apertura file
			    if(returnVal == JFileChooser.APPROVE_OPTION) {
			    	
			    	// se è attivo un WF precedente lo cancello
			    	if(WF != null)
			    		WF.scrollPanel.setVisible(false);
			    	try {
			    		// creo il nuovo WF corrispondente a quello descritto nel file
						newWF(openWF(chooser.getSelectedFile()));
					} catch (IOException | JAXBException e1) {
						e1.printStackTrace();
					}
			    	
			    	// memorizzo il file
			        WF.setFile(chooser.getSelectedFile());
				}	
			}
		});
		mnFile.add(mntmOpen);

		/*
		 *  - SAVE -
		 */
		JMenuItem mntmSave = new JMenuItem("Save");
		mntmSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				saveWF();				
			}
		});
		mnFile.add(mntmSave);
		
		/*
		 *  - SAVE WITH NAME -
		 */
		JMenuItem mntmSaveWithName = new JMenuItem("Save with name");
		mntmSaveWithName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveWithNameWF(2);
				/* 2 faccio scegliere all'utente come gestire
				eventuali oggetti non definitivi prima del salvataggio */
			}
		});
		mnFile.add(mntmSaveWithName);
		
		
		/*
		 *  -- INSERT MENU --
		 */
		
		JMenu mnInsert = new JMenu("Insert");
		menuBar.add(mnInsert);
		
		/*
		 *  - NEW NODE -
		 */
		JMenuItem mntmNode = new JMenuItem("Node");
		mntmNode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(WF.isEditable())	{
					newNode();
				}
			}
		});
		mnInsert.add(mntmNode);
		
		/*
		 *  - NEW TRANSITION -
		 */
		JMenuItem mntmTransition = new JMenuItem("Transition");
		mntmTransition.addActionListener(new ActionListener() {		
			public void actionPerformed(ActionEvent arg0) {
				if(WF.isEditable()) {
					newTran();
				}
			}
		});
		mnInsert.add(mntmTransition);
		
		
		contentPane = new JPanel();
		contentPane.setBackground(Color.GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		/*
		 *  ---- HORIZONTAL STRUT ----
		 */
		Component horizontalStrut = Box.createHorizontalStrut(636);
		menuBar.add(horizontalStrut);
		
		
		/*
		 *  --- TOOLBAR ----
		 */
		JToolBar toolBar = new JToolBar();
		menuBar.add(toolBar);
		
		/*
		 *  - NEW NODE - 
		 */
		JButton nodeButton = new JButton("N");
		nodeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(WF.isEditable()) {
					newNode();
				}
			}
		});
		nodeButton.setMinimumSize(new Dimension(51, 23));
		nodeButton.setMaximumSize(new Dimension(51, 23));
		nodeButton.setPreferredSize(new Dimension(90, 23));
		nodeButton.setFont(new Font("3ds", Font.BOLD, 13));
		toolBar.add(nodeButton);
		
		/*
		 *  - NEW TRANSITION -
		 */
		JButton tranButton = new JButton("T");
		tranButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(WF.isEditable()) {
					newTran();
				}
			}
		});
		tranButton.setMaximumSize(new Dimension(51, 23));
		tranButton.setMinimumSize(new Dimension(51, 23));
		tranButton.setPreferredSize(new Dimension(90, 23));
		tranButton.setFont(new Font("3ds", Font.BOLD, 13));
		toolBar.add(tranButton);
		
		
		/*
		 *  - DISPLAY - 
		 */
		menuBar.add(textField);
		textField.setColumns(10);
		textField.setEditable(false);
		contentPane = new JPanel();
		contentPane.setBackground(Color.GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
        
	}

	/**
	 *  Salva il WF sul file già assegnato al WF.
	 */
	
	public void saveWF(){
		
		int choice = 0;
		/*  0 salva senza gli oggetti non definitivi
		 *  1 modifica il WF per rendere gli oggetti definitivi */
		
		/* se il file ha degli oggetti non definitivi 
		 * chiedo all'utente di scegliere cosa fare */
		if(!WF.isEditable()) {
			Object[] options = {"Save the WF without those object", "Modify the WF and make those object definitive"};
			choice = JOptionPane.showOptionDialog(getContentPane(), "The WF has not-definitive objects that will not be saved!", "Warning!", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);;
		}
		
		// choise=0 -> salvo il WF senza gli oggetti non definitivi
		if(choice == 0) {
			
			// rimuovo gli oggetti non definitivi
			if(!WF.nodes.isEmpty() && !WF.nodes.get(WF.nodes.size()-1).isDef()) 
					WF.deleteNode(WF.nodes.get(WF.nodes.size()-1).getNodeCode());
			if(!WF.transitions.isEmpty() && !WF.transitions.get(WF.transitions.size()-1).isDef())
					WF.deleteTransition(WF.transitions.get(WF.transitions.size()-1).getTranCode());
			
			// il WF ha già un file assegnato
			if(WF.getFile() != null) {
				try {
					FileWriter fw = new FileWriter(WF.getFile());
					fw.write(WF.javaXmlTranslate());
					fw.flush();
					fw.close();
					WF.setSaved(true);
				} catch (IOException | JAXBException e1) {
					e1.printStackTrace();
				}
			}
		
			// il WF non ha ancora un file assegnato
			else {
				saveWithNameWF(0); //0 comunico la scelta fatta dall'utente 
									//(salvare senza oggetti non definitivi)
			}
		}
	}
	
	/**
	 * Salva il WF chiedendo all'utente di specificare un nuovo file.
	 *
	 * @param choice la scelta di salvare o meno gli oggetti non definitivi (0 no 1 si)
	 */
	
	public void saveWithNameWF(int choice) { 
		
		/*  choice
		 *  0 salva senza gli oggetti non definitivi
		 *  1 modifica il WF per rendere gli oggetti definitivi */
		
		/* se il file ha degli oggetti non definitivi
		 * e non è stato scelto di salvare senza gli oggetti definitivi
		 * chiedo all'utente di scegliere cosa fare */
		
		if(!WF.isEditable() && choice != 0) {
			Object[] options = {"Save the WF without those object", "Modify the WF and make those object definitive"};
			choice = JOptionPane.showOptionDialog(getContentPane(), "The WF has not-definitive objects that will not be saved!", "Warning!", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);;
		}
		else
			choice=0;
		
		// choise=0 -> salvo il WF senza gli oggetti non definitivi
		if(choice == 0) {
			
			// rimuovo gli oggetti non definitivi
			if(!WF.nodes.isEmpty() && !WF.nodes.get(WF.nodes.size()-1).isDef()) 
					WF.deleteNode(WF.nodes.get(WF.nodes.size()-1).getNodeCode());
			if(!WF.transitions.isEmpty() && !WF.transitions.get(WF.transitions.size()-1).isDef())
					WF.deleteTransition(WF.transitions.get(WF.transitions.size()-1).getTranCode());
			
			// faccio scegliere il nuovo file
			JFileChooser chooser = new JFileChooser();
		    int returnVal = chooser.showSaveDialog(getParent());
		    if(returnVal == JFileChooser.APPROVE_OPTION) {
		       try {
		    	   FileWriter fw = new FileWriter(chooser.getSelectedFile());
		    	   fw.write(WF.javaXmlTranslate());
		    	   fw.flush();
		    	   fw.close();
		    	   WF.setFile(chooser.getSelectedFile());
		    	   WF.setSaved(true);
		       } catch (IOException | JAXBException e1) {
		    	   e1.printStackTrace();
		       }
		    }
		}
	}
	
	/**
	 * Imposta come attivo un nuovo WF.
	 *
	 * @param newWF il nuovo WF da impostare come attivo
	 */
	/*
	 *  NEW WORKFLOW
	 */
	public void newWF(WorkFlow newWF) {
		
		// imposta newWF come WF attivo
		setWF(newWF);
		
		// aggiorna display toolbar
		WF.setToolTextField(textField);
		getWF().getToolTextField().setText("Select N to add a node, T to add a transition");
		
		// aggiorna scrollbar
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
        gl_contentPane.setHorizontalGroup(
        	gl_contentPane.createParallelGroup(Alignment.LEADING)
        		.addComponent(newWF.scrollPanel, GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE)
        );
        gl_contentPane.setVerticalGroup(
        	gl_contentPane.createParallelGroup(Alignment.LEADING)
        		.addComponent(newWF.scrollPanel, GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)
        );
        contentPane.setLayout(gl_contentPane);
        revalidate();
        
		WF.printWorkFlow();
		WF.getToolTextField().setText("Select N to add a node, T to add a transition");
		WF.setSaved(true);
  
	}
	
	/**
	 * Apre un WF esistente dal suo file xml.
	 *
	 * @param file il file da aprire
	 * @return il workflow corrispondente
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws JAXBException the JAXB exception
	 */
	public WorkFlow openWF(File file) throws IOException, JAXBException {
		
		// JAXB Java Architecture for XML Binding
		JAXBContext r = JAXBContext.newInstance(WorkFlow.class);
		
		// BINDING processo di generazione di una serie di classi Java 
		// che rappresentano lo schema che si vuole accedere 
		
		// permette di effettuare l’unmarshalling del documento XML passato sotto forma di File
		Unmarshaller unmarshaller = r.createUnmarshaller();
		
		// l'oggetto ritornato viene convertito nella classe WF tramite casting
        WorkFlow t = (WorkFlow) unmarshaller.unmarshal(file);
        return t;
	}
	
	/**
	 * Crea una nuova transizione.
	 */
	public void newTran() {
		
		// aggiorno display toolbar
		WF.getToolTextField().setText("Select the logo of the first node to link");
		
		// inizializzazione attributi per creazione transizione
		WF.setStartTaken(false);
		WF.setEndTaken(false);
		WF.setStart(null);
		WF.setEnd(null);
		
		// attivo i listener per la selezione dei nodi di partenza e arrivo
		NodePanel.listenerState=true;
		
		// la creazione continua nella classe NodePanel
	}
	
	/**
	 * Crea un nuovo nodo.
	 */
	public void newNode() {
		
		/* se sono già presenti nodi calcolo la posizione dove inserire il nuovo nodo
		 * per evitare sovrapposizioni */
		if (!WF.getNodes().isEmpty()) {
			WF.setStartX(WF.nodes.get(WF.nodes.size()-1).getPosX() + 220);
			WF.setStartY(WF.nodes.get(WF.nodes.size()-1).getPosY());
			if (WF.nodes.get(WF.nodes.size()-1).getPosX() > 1000) {
				WF.setStartY(WF.nodes.get(WF.nodes.size()-1).getPosY() + 320);
				WF.setStartX(20);
			}
		}
		
		// aggiungo il nuovo nodo alla lista di nodi del WF
		WF.nodes.add(new Node(WF));
		
		// aggiungo il pannello del nodo al pannello di disegno
		WF.getPanel().add(WF.nodes.get(WF.nodes.size()-1).getPanel());
		WF.nodes.get(WF.nodes.size()-1).getPanel().setVisible(true);
		WF.nodes.get(WF.nodes.size()-1).getPanel().revalidate();	
		
		// aggiorno il display della toolbar
		WF.getToolTextField().setText("Insert node code to add new nodes or transitions");
		
		// avviso che ci sono modifiche non salvate sul file
		WF.setSaved(false);
		
		// avviso che ci sono oggetti non definitivi
		// e disabilito la possibilità di fare ulteriori modifiche
		WF.setEditable(false);
	}

	
	/**
	 * Ritorna il WF attualmente attivo.
	 *
	 * @return il WF attualmente attivo
	 */
	public WorkFlow getWF() {
		return WF;
	}
	
	/**
	 * Imposta il WF attualmente attivo
	 *
	 * @param wF il WF da rendere attivo
	 */
	public void setWF(WorkFlow wF) {
		WF = wF;
	}
	
}
