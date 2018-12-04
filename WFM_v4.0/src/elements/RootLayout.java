package elements;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.Color;
import java.awt.Dimension;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.swing.JFileChooser;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class RootLayout extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	public WorkFlow WF = null;
	
	public Node n1;
	public Node n2;
	
	/*
	 *  START THE APPLICATION
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RootLayout frame = new RootLayout();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public RootLayout() {
		
		/*
		 *  LOOK AND FEEL 
		 */
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		setTitle("WFManager");
		setPreferredSize(new Dimension(1024, 760));
		//setMinimumSize(new Dimension (1024, 760));
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		getContentPane().setBackground(Color.GRAY);
		getContentPane().setLayout(null);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		/*
		 *  --- MENU FILE --- 
		 */
		
		/*
		 *  NUOVO
		 */
		JMenuItem mntmNuovo = new JMenuItem("Nuovo");
		mnFile.add(mntmNuovo);
		mntmNuovo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				WorkFlow newWorkFlow = new WorkFlow();			
				
				int n;
				if (WF == null) //the file is empty
					n=1;
				else { //the file has data
					Object[] options = {"Save the WF", "Create a new WF without saving"};
					//n contains the choice
					n = JOptionPane.showOptionDialog(getContentPane(), "The file has not been saved", "Warning!", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);;
				}
				
				if (n == 0) {	//save the WF option
				
					if(WF.file != null) {
						try {
							saveWF(WF.file);
						} catch (IOException | JAXBException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					else {
						JFileChooser chooser = new JFileChooser();
					    int returnVal = chooser.showSaveDialog(getParent());
					    if(returnVal == JFileChooser.APPROVE_OPTION) {
					       try {
					    	   saveWF(chooser.getSelectedFile());
					       } catch (IOException | JAXBException e1) {
					    	   // TODO Auto-generated catch block
					    	   e1.printStackTrace();
					       }
					    }
					}
				}
				
				//new WF
				setContentPane(newWorkFlow.getPanel());
				newWorkFlow.getPanel().revalidate();
				setWF(newWorkFlow);
			}
		});
		
		/*
		 *  SALVA
		 */
		JMenuItem mntmSalva = new JMenuItem("Salva");
		mntmSalva.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(WF.file != null) {
					try {
						saveWF(WF.file);
					} catch (IOException | JAXBException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				else {
					JFileChooser chooser = new JFileChooser();
				    int returnVal = chooser.showSaveDialog(getParent());
				    if(returnVal == JFileChooser.APPROVE_OPTION) {
				       try {
				    	   WF.setFile(chooser.getSelectedFile());
				    	   saveWF(chooser.getSelectedFile());
				       } catch (IOException | JAXBException e1) {
				    	   // TODO Auto-generated catch block
				    	   e1.printStackTrace();
				       }
				    }
				}
			}
		});
		
		/*
		 *  APRI
		 */
		JMenuItem mntmApri = new JMenuItem("Apri");
		mntmApri.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				JFileChooser chooser = new JFileChooser();
				
				//filtri per aprire solo file con estensione xml
				//da rivedere
			    //FileNameExtensionFilter filter = new FileNameExtensionFilter("xml");
			    //chooser.setFileFilter(filter);
				
			    int returnVal = chooser.showOpenDialog(getParent());
			    
			    if(returnVal == JFileChooser.APPROVE_OPTION) {
			    	System.out.println("You chose to open this file: " + chooser.getSelectedFile());
				    try {
				    	
						WorkFlow nuovo = openWF(chooser.getSelectedFile());
					
						setWF(nuovo);
						setContentPane(nuovo.panel);
						nuovo.panel.setLayout(null);
						nuovo.panel.revalidate();
					
						System.out.println(nuovo); //per debug
						nuovo.setFile(chooser.getSelectedFile());
						nuovo.printWorkFlow();
						
					} catch (IOException | JAXBException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}	    
			}
		});
		
		mnFile.add(mntmApri);
		mnFile.add(mntmSalva);
		
		/*
		 *  SALVA CON NOME
		 */
		JMenuItem mntmSalvaConNome = new JMenuItem("Salva con nome");
		mntmSalvaConNome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				JFileChooser chooser = new JFileChooser();
			    int returnVal = chooser.showSaveDialog(getParent());
			    if(returnVal == JFileChooser.APPROVE_OPTION) {
			       try {
			    	   WF.setFile(chooser.getSelectedFile());
			    	   saveWF(chooser.getSelectedFile());
			       } catch (IOException | JAXBException e1) {
			    	   // TODO Auto-generated catch block
			    	   e1.printStackTrace();
			       }
			    }
			}
		});
		mnFile.add(mntmSalvaConNome);
		
		
		
		
		
		/*
		 *  --- MENU AGGIUNGI --- 
		 */
		
		JMenu mnAggiungi = new JMenu("Aggiungi");
		menuBar.add(mnAggiungi);
		
		/*
		 *  NEW NODE
		 */
		JMenuItem mntmNodo = new JMenuItem("Nodo");
		mnAggiungi.add(mntmNodo);
		mntmNodo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				//position assignment
				
				if (!WF.getNodes().isEmpty()) {
					WF.start_x=WF.nodes.get(WF.nodes.size()-1).pos_x + 200;
					WF.start_y=WF.nodes.get(WF.nodes.size()-1).pos_y;
					if (WF.nodes.get(WF.nodes.size()-1).getPos_x() > 1000) {
						WF.start_y=WF.nodes.get(WF.nodes.size()-1).pos_y + 300;
						WF.start_x=20;
					}
				}
				
				WF.nodes.add(new Node(WF));
				
				getContentPane().add(WF.nodes.get(WF.nodes.size()-1).panel);
				WF.nodes.get(WF.nodes.size()-1).panel.revalidate();	
				WF.next_index++;
			}
		});
	
		/*
		 *  NEW TRANSITION 
		 */
		JMenuItem mntmTransazione = new JMenuItem("Transizione");
		mntmTransazione.addActionListener(new ActionListener() {		
			public void actionPerformed(ActionEvent arg0) {
				WF.start_taken=false;
				WF.end_taken=false;
				WF.start=null;
				WF.end=null;
				for(Node n: WF.nodes) {
					n.panel.setListener_state(true);
				}
			}
		});
		mnAggiungi.add(mntmTransazione);
		
		
		/*
		 *  ZOOM IN E ZOOM OUT -- da rivedere 
		 */
		
		/*
		JMenu zoom_out = new JMenu("+");
		zoom_out.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				node_size.height*=1.1;
				node_size.width*=1.1;
				for(Node n: nodes) {
					n.setNode_size(node_size);
					n.revalidate();
				}
			}
		});
		menuBar.add(zoom_out);
		
		JMenu zoom_in = new JMenu("-");
		zoom_in.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(node_size.width >= 165 && node_size.height >= 275) {
					node_size.height/=1.1;
					node_size.width/=1.1;
					for(Node n: nodes) {
						n.setNode_size(node_size);
						n.revalidate();
						n.repaint();
					}
				}
				else
					JOptionPane.showMessageDialog(menuBar, "Minimum zoom size reached", "Attention!", JOptionPane.ERROR_MESSAGE);
			}
		});
		menuBar.add(zoom_in);
		*/
		JMenu menu = new JMenu("?");
		menuBar.add(menu);
		
		JMenuItem mntmGuida = new JMenuItem("Guida");
		menu.add(mntmGuida);
		
	}
	
	
	/*
	 *  OPEN WORKFLOW
	 */
	public WorkFlow openWF(File file) throws IOException, JAXBException {
		
		JAXBContext r = JAXBContext.newInstance(WorkFlow.class);
		Unmarshaller unmarshaller = r.createUnmarshaller();
        WorkFlow t = (WorkFlow) unmarshaller.unmarshal(file);
        return t;
	}


	/*
	 *  SAVE WORKFLOW
	 */
	void saveWF(File file) throws IOException, JAXBException {
		
		FileWriter fw = new FileWriter(file);
		fw.write(WF.java_xml_Translate());
		fw.flush();
		fw.close();
	}
	
	/*
	 *  GETTER AND SETTERS
	 */
	public WorkFlow getWF() {
		return WF;
	}
	public void setWF(WorkFlow wF) {
		WF = wF;
	}

}