
package elements;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;

import javax.xml.bind.annotation.*;

import ui.NodePanel;

// TODO: Auto-generated Javadoc
/**
 * Contiene le informazioni relative a un NODO.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement

public class Node implements Serializable{

	/**  Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** Ascissa della posizione del nodo nel WorkFlowPanel */
	//position 
	private int posX;
	
	/** Ordinata della posizione del nodo nel WorkFlowPanel  */
	private int posY;
	
	/** Riferimento al WorkFlow a cui appartiente il nodo */
	@XmlTransient
	public WorkFlow nodeWF;

	/** Codice del nodo; il codice dei nodi deve essere univoco: tale condizione viene verificata nel metodo che gestisce il textField
	 *  relativo al nodeCode, nella classe NodePanel */
	//node values
	private String nodeCode;
	
	/** Lista di descrizioni del nodo */
	@XmlElementWrapper(name="description-list")
	@XmlElement(name="description")
	private ArrayList<Description> descriptions = new ArrayList<Description>();

	/** Pannello del nodo */
	@XmlTransient
	private NodePanel panel;

	/** Indice del nodo all'interno della lista dei nodi di un determinato WorkFlow */
	private int nodeIndex;
	
	/** Variabile che indica se il nodo è definitivo o meno; i nodi vengono resi definitivi nel momento in cui il codice viene confermato
	 *  tramite il tasto ENTER; i nodi non confermati non verranno salvati. */
	private boolean def=false;

	
	/**
	 * Costruttore del nodo senza parametri (utilizzato in fase di apertura del file .xml)
	 */
	public Node() {
		super();
		this.nodeCode = "";
		this.panel = new NodePanel(this);
		this.panel.revalidate();
		this.panel.setVisible(true);
	}
	
	/**
	 * Costruttore del nodo a cui viene passato il Workflow a cui appartiene il nodo stesso
	 */
	public Node(WorkFlow nodeWF) {
		super();
		this.nodeWF = nodeWF;
		this.posX = nodeWF.getStartX();
		this.posY = nodeWF.getStartY();
		this.nodeCode = "";
		this.nodeIndex = nodeWF.nodes.size();
		this.panel = new NodePanel(this);
	}
	
	/**
	 * Funzione che prende il centro del nodo a partire dalla sua posizione, dalla sua larghezza e dalla sua altezza;
	 * il centro del nodo è utile per il disegno della freccia che lo collegherà alla transizione
	 */
	public Point getCenter (int x1, int y1, int width, int height) {
		Point centro = new Point();		
		centro.x= x1 + (width/2);
		centro.y= y1 + (height/2);
		return centro;		
		
	}
	
	
	/**
	 * GETTERS e SETTERS
	 */

	public int getPosX() {
		return posX;
	}
	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() {
		return posY;
	}
	public void setPosY(int posY) {
		this.posY = posY;
	}

	public WorkFlow getNodeWF() {
		return nodeWF;
	}
	public void setNodeWF(WorkFlow nodeWF) {
		this.nodeWF = nodeWF;
	}

	public String getNodeCode() {
		return nodeCode;
	}
	public void setNodeCode(String nodeCode) {
		this.nodeCode = nodeCode;
	}

	public NodePanel getPanel() {
		return panel;
	}
	public void setPanel(NodePanel panel) {
		this.panel = panel;
	}

	public ArrayList<Description> getDescriptions() {
		return descriptions;
	}
	public void setDescriptions(ArrayList<Description> descriptions) {
		this.descriptions = descriptions;
	}

	public int getNodeIndex() {
		return nodeIndex;
	}
	public void setNodeIndex(int nodeIndex) {
		this.nodeIndex = nodeIndex;
	}
	
	public boolean isDef() {
		return def;
	}
	public void setDef(boolean def) {
		this.def = def;
	}
	
	@Override
	public String toString() {
		return "Node [posX=" + posX + ", posY=" + posY + ", nodeWF=" + nodeWF + ", nodeCode=" + nodeCode
				+ ", descriptions=" + descriptions + ", panel=" + panel + "]";
	}
}
