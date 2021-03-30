
package elements;

import java.awt.Dimension;
import java.awt.Point;
import java.io.File;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.ArrayList;

import javax.swing.JTextField;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;


import ui.ArrowPanel;
import ui.DescriptionPanel;
import ui.NodePanel;
import ui.ScrollPanel;
import ui.TransitionPanel;
import ui.WorkFlowPanel;

// TODO: Auto-generated Javadoc
/**
 * Raggruppa tutte le informazioni relative al WF, i nodi e le transizioni presenti
 */
@XmlRootElement(name = "workflow")
@XmlAccessorType(XmlAccessType.FIELD)

public class WorkFlow implements Serializable {

	/**Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/**ScrollPanel su cui viene inserito il pannello del workflow (panel) */
	@XmlTransient
	public ScrollPanel scrollPanel;
	
	/** Pannello del workflow  */
	@XmlTransient
	public WorkFlowPanel panel;
	
	/**Larghezza del pannello del workflow */
	private int width = 1350;
	
	/** Altezza del pannello del workflow */
	private int height = 670;
	
	/** Lista dei nodi del workflow */
	@XmlElementWrapper(name="node-list")
	@XmlElement(name="node")
	public ArrayList<Node> nodes = new ArrayList<Node>();
		
	/** Lista di transizioni del workflow */
	@XmlElementWrapper(name="transition-list")
	@XmlElement(name="transition")
	public ArrayList<Transition> transitions = new ArrayList<Transition>();
	
	/** File associato al workflow */
	@XmlTransient
	private File file;
	
	/** Variabile booleana che indica se il file del workflow è stato salvato o no*/
	@XmlTransient
	private boolean saved = false;
	
	/** Ascissa iniziale*/
	private int startX=20;
	
	/** Ordinata iniziale */
	private int startY=20;
	
	/** Il workflow gestisce, volta per volta, la creazione di una nuova transizione; start è il nodo di partenza di tale transizione */
	@XmlTransient
	private Node start;
	
	/** startTaken indica se il nodo di partenza della transizione è stato preso; se true, si può procedere all'individuazione
	 *  del nodo di arrivo*/
	@XmlTransient
	private boolean startTaken = false;
	
	/** Il workflow gestisce, volta per volta, la creazione di una nuova transizione; end è il nodo di arrivo di tale transizione */
	@XmlTransient
	private Node end;
	
	/** endTaken indica se anche il nodo di arrivo della transizione è stato individuato: se true, abbiamo i due nodi necessari per
	 *  la costruzione della transizione */
	@XmlTransient
	private boolean endTaken = false;
	
	/** In ogni workflow è presente un textfield che guida l'utente nell'utilizzo dell'editor */
	@XmlTransient
	public JTextField toolTextField;
	
	/** Il workflow viene posto a editable (modificabile) solo se tutti i nodi e le transizioni aggiunte sono stati confermati 
	 * (ovvero se il loro codice è definitivo) */
	@XmlTransient
	private boolean editable = true;
	
	
	/*
	 *  COSTRUTTORI
	 */
	
	/**
	 * Costruttore del workflow 
	 */
	public WorkFlow() {
		super();
		this.panel = new WorkFlowPanel(this);
		this.scrollPanel = new ScrollPanel(panel);
		this.file = null;
		this.setStart(null);
	}
	
	/*
	 *  METODI
	 */
	
	/**
	 * Metodo che, ogni volta che un file contentente un workflow viene aperto, riposiziona tutti gli elementi sul pannello
	 */
	public void printWorkFlow() {
		
		panel.apply(new Dimension(width, height));
		//nodi 
		for(Node n: nodes) {
			NodePanel newNodePanel = new NodePanel(n);
			panel.add(newNodePanel);
			n.setPanel(newNodePanel);
			
			//descrizioni dei nodi
			int x=0;
			for(Description d: n.getDescriptions()) {
				d.type=n;
				DescriptionPanel newDP = new DescriptionPanel(d);
				d.setTab(n.getPanel().getTabbedPane());
				d.getTab().add(newDP);
				d.getTab().setTitleAt(x, (String) d.getLanguage());
				x++;
			}
			n.setNodeWF(this);
		}
		
		//transizioni
		for(Transition t: transitions) {
			t.setTranWF(this);
			for(Node n: nodes) {
				if(n.getNodeCode().equals(t.getN1Code())) {
					t.setN1(n);
				}
				if(n.getNodeCode().equals(t.getN2Code())) {
					t.setN2(n);
				}
			}
			
			TransitionPanel newTransitionPanel = new TransitionPanel(t);
			panel.add(newTransitionPanel);
			t.setPanel(newTransitionPanel);

			Point p1 = t.n1.getCenter(t.n1.getPosX(), t.n1.getPosY(), 200,  300);
			Point p2 = t.n2.getCenter(t.n2.getPosX(), t.n2.getPosY(), 200,  300);
			Point p12 = t.getCenter(t.getPosX(), t.getPosY(), 320, 150);
			
			t.setArrow1(new ArrowPanel(p1, p12));
			t.setArrow2(new ArrowPanel(p12, p2));
			
			t.getArrow1().setSize(new Dimension(width, height));
			t.getArrow2().setSize(new Dimension(width, height));
			
			panel.add(t.getArrow1());
			panel.add(t.getArrow2());
			
			//descrizioni dell transizioni
			int x=0;
			for(Description d: t.getDescriptions()) {
				d.type=t;
				DescriptionPanel newDP = new DescriptionPanel(d);
				d.setTab(t.getPanel().getTabbedPane());
				d.getTab().add(newDP);
				d.getTab().setTitleAt(x, (String) d.getLanguage());
				x++;
			}
		}
	}
	
	/**
	 * Cancellazione di un nodo
	 */
	public void deleteNode(String nodeCode) {
		int i = -1;
		for(Node n: nodes) {
			int nodeIndex = n.getNodeIndex();
			if(nodeCode.equals(n.getNodeCode())) {
				nodes.get(nodeIndex).getPanel().setVisible(false);
				i= nodeIndex;
			}
		}
		
		nodes.remove(i);
		setEditable(true);
		
		//Gli indici dei nodi successivi a quello cancellato scalano
		for(Node n: nodes) {
			if(n.getNodeIndex() > i) {
				n.setNodeIndex(n.getNodeIndex()-1);
			}
		}
	}
	
	/**
	 * Cancellazione di una transizione
     */
	public void deleteTransition(String tranCode) {
		int i = -1;
		for(Transition t: transitions) {
			int tranIndex = t.getTranIndex();
			if(tranCode.equals(t.getTranCode())) {
				transitions.get(tranIndex).getPanel().setVisible(false);
				transitions.get(tranIndex).getArrow1().setVisible(false);
				transitions.get(tranIndex).getArrow2().setVisible(false);
				i= tranIndex;
			}
		}
		
		transitions.remove(i);
		setEditable(true);
		
		//Gli indici delle transizioni successive a quella cancellata scalano
		for(Transition t: transitions) {
			if(t.getTranIndex() > i) {
				t.setTranIndex(t.getTranIndex()-1);
			}
		}
	}
	
	// JAVA-XML TRANSLATION
	   
	/**
	 * Traduttore da Java a XML invocato in fase di salvataggio del file
	 */
	public String javaXmlTranslate() throws JAXBException {
		
		JAXBContext jaxbContext = JAXBContext.newInstance(this.getClass());
		Marshaller marshaller = jaxbContext.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		StringWriter writer = new StringWriter();
		marshaller.marshal(this, writer);
		return writer.toString();		
	}
		

	/*
	 *  GETTERS AND SETTERS
	 */

	public boolean isStartTaken() {
		return startTaken;
	}

	public void setStartTaken(boolean startTaken) {
		this.startTaken = startTaken;
	}

	public WorkFlowPanel getPanel() {
		return panel;
	}

	public void setPanel(WorkFlowPanel panel) {
		this.panel = panel;
	}

	public ArrayList<Node> getNodes() {
		return nodes;
	}

	public void setNodes(ArrayList<Node> nodes) {
		this.nodes = nodes;
	}

	public ArrayList<Transition> getTransitions() {
		return transitions;
	}

	public void setTransitions(ArrayList<Transition> transitions) {
		this.transitions = transitions;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public int getStartX() {
		return startX;
	}

	public void setStartX(int startX) {
		this.startX = startX;
	}

	public int getStartY() {
		return startY;
	}

	public void setStartY(int startY) {
		this.startY = startY;
	}

	public Node getStart() {
		return start;
	}

	public void setStart(Node start) {
		this.start = start;
	}

	public Node getEnd() {
		return end;
	}

	public void setEnd(Node end) {
		this.end = end;
	}

	public boolean isEndTaken() {
		return endTaken;
	}

	public void setEndTaken(boolean endTaken) {
		this.endTaken = endTaken;
	}

	public boolean isSaved() {
		return saved;
	}

	public void setSaved(boolean saved) {
		this.saved = saved;
	}

	public JTextField getToolTextField() {
		return toolTextField;
	}

	public void setToolTextField(JTextField toolTextField) {
		this.toolTextField = toolTextField;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int sizeX) {
		this.width = sizeX;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int sizeY) {
		this.height = sizeY;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	@Override
	public String toString() {
		return "WorkFlow [scrollPanel=" + scrollPanel + ", panel=" + panel + ", width=" + width + ", height=" + height
				+ ", nodes=" + nodes + ", transitions=" + transitions + ", file=" + file + ", saved=" + saved
				+ ", startX=" + startX + ", startY=" + startY + ", start=" + start + ", startTaken=" + startTaken
				+ ", end=" + end + ", endTaken=" + endTaken + ", toolTextField=" + toolTextField + ", editable="
				+ editable + "]";
	}
}
