package elements;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import ui.ArrowPanel;
import ui.TransitionPanel;

// TODO: Auto-generated Javadoc
/**
 * Contiene le informazioni relative a una TRANSIZIONE
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "transition")

public class Transition implements Serializable{

	private static final long serialVersionUID = 1L;

	/** Nodo n1: nodo di partenza della transizione */
	@XmlTransient
	public Node n1;
	
	/** Nodo n2: nodo di arrivo della transizione. */
	@XmlTransient
	public Node n2;
	
	/** Codice del nodo n1 */
	private String n1Code;
	
	/** Codice del nodo n2*/
	private String n2Code;
	
	/** Riferimento al WorkFlow a cui appartiente il nodo */
	@XmlTransient
	public WorkFlow tranWF;
	
	/** Punto p1: punto da cui parte la prima freccia che descrive la transizione */
	@XmlTransient
	private Point p1;
	
	/** Punto p2: punto a cui arriva la seconda freccia che descrive la transizione */
	@XmlTransient
	private Point p2;
	
	/** The p12: centro del pannello della transizione; è il punto a cui arriva la prima freccia e da cui parte la seconda */
	@XmlTransient
	private Point p12;
	
	/** Pannello che rappresenta la transizione*/
	@XmlTransient
	public TransitionPanel panel;
	
	/** Ascissa della posizione della transizione nel WorkFlowPanel*/
	//position
	private int posX;
	
	/** Ordinata della posizione della transizione nel WorkFlowPanel*/
	private int posY;

	/** Codice della transizione; il codice delle transizioni deve essere univoco: tale condizione viene verificata nel metodo che gestisce il textField
	 *  relativo al tranCode, nella classe TransitionPanel */
	//transition values
	private String tranCode="";
	
	/** Lista di descrizioni della transizione */
	@XmlElementWrapper(name="description-list")
	@XmlElement(name="description")
	private ArrayList<Description> descriptions = new ArrayList<Description>();
	
	/** Indice della transizione all'interno della lista delle transizioni di un determinato Workflow */
	private int tranIndex;
	
	/** Variabile che indica se la transizione è definitiva o meno; le transizioni vengono rese definitive nel momento in cui il codice
	 *  viene confermato tramite il tasto ENTER; le transizioni non confermate non verranno salvate. */
	private boolean def = false;
	
	/** Pannello che rappresenta la prima freccia della transizione */
	//arrow utilities
	@XmlTransient
	private ArrowPanel arrow1;
	
	/** Pannello che rappresenta la seconda freccia della transizione*/
	@XmlTransient
	private ArrowPanel arrow2;
	
	/** Variabile utile per capire dove posizionare il pannello della transizione; frecca, infatti, contiene l'indice dell'ultima 
	 *  transizione costruita fra i nodi n1 ed n2, -1 se non è stata costruita alcuna transizione precedentemente fra n1 ed n2 */
	@XmlTransient
	private int freccia;
	
	/*
	 *  COSTRUTTORI
	 */
	
	/**
	 * Costruttore della transizione senza parametri (utilizzato in fase di apertura del file .xml)
	 */
	public Transition() {
		
		this.panel = new TransitionPanel(this);
		this.arrow1 = new ArrowPanel();
		this.arrow2 = new ArrowPanel();
	}

	/**
	 * Costruttore della transizione a cui vengono passati il nodo di partenza, il nodo di arrivo e la variabile freccia.
	 */
	public Transition(Node n1, Node n2, int freccia) {
		super();
		this.n1 = n1;
		this.n2 = n2;
		this.tranWF = n1.nodeWF;
		this.freccia = freccia;
		this.n1Code=n1.getNodeCode();
		this.n2Code=n2.getNodeCode();
		this.tranIndex=n1.nodeWF.transitions.size();
		
		/**
		 * Se freccia vale -1, non è stata costruita alcuna transizione fra i due nodi;
		 * posso posizionare la transizione nel punto medio fra i due nodi
		 */
		if(this.freccia == -1) {
			this.p1 =getCenter(n1.getPosX(), n1.getPosY(), n1.getPanel().getSize().width,  n1.getPanel().getSize().height);
			this.p2 = getCenter(n2.getPosX(), n2.getPosY(), n2.getPanel().getSize().width,  n2.getPanel().getSize().height);
			this.posX = ((this.p1.x + this.p2.x)/2) - 270/2;;
			this.posY = (this.p1.y + this.p2.y)/2 - 115/2;
			this.panel = new TransitionPanel(this);
			this.p12 = getCenter(this.posX, this.posY, this.panel.getWidth(), this.panel.getHeight());
			this.arrow1 = new ArrowPanel(this.p1, this.p12);
			this.arrow2 = new ArrowPanel(this.p12, this.p2);
			tranWF.getPanel().add(arrow1);
			arrow1.setSize(this.tranWF.getPanel().getSize());
			paintFirstArrow();
			tranWF.getPanel().add(arrow2);
			arrow2.setSize(this.tranWF.getPanel().getSize());
			paintSecondArrow();
		}
		/**
		 * freccia ha un valore diverso da -1: fra i nodi n1 ed n2 è stata già creata una transizione; la transizione attuale, dunque,
		 * verrà posta al di sotto di quella precedente
		 */
		else {
			this.posX = this.tranWF.transitions.get(this.freccia).posX;
			this.posY = this.tranWF.transitions.get(this.freccia).posY  + 160;
			this.panel = new TransitionPanel(this);
			this.p1 =getCenter(n1.getPosX(), n1.getPosY(), n1.getPanel().getSize().width,  n1.getPanel().getSize().height);
			this.p2 = getCenter(n2.getPosX(), n2.getPosY(), n2.getPanel().getSize().width,  n2.getPanel().getSize().height);
			this.p12 = getCenter(this.posX, this.posY, this.panel.getWidth(), this.panel.getHeight());
			this.arrow1 = new ArrowPanel(this.p1, this.p12);
			this.arrow2 = new ArrowPanel(this.p12, this.p2);
			tranWF.getPanel().add(arrow1);
			arrow1.setSize(this.tranWF.getPanel().getSize());
			paintFirstArrow();
			tranWF.getPanel().add(arrow2);
			arrow2.setSize(this.tranWF.getPanel().getSize());
			paintSecondArrow();
		}
	}
	
	/*
	 *  METODI
	 */
	
	/**
	 * Ridisegna la prima freccia
	 */
	public void paintFirstArrow() {
		arrow1.start=getCenter(this.n1.getPosX(), this.n1.getPosY(), 200,  300);
		arrow1.end =getCenter(this.getPosX(), this.getPosY(), 320, 150);	
		arrow1.setVisible(true);
		arrow1.repaint();
	}
	
	/**
	 * Ridisegna la seconda freccia
	 */
	public void paintSecondArrow() {
		arrow2.start= getCenter(getPosX(), getPosY(), 320, 150);	
		arrow2.end = getCenter(n2.getPosX(), n2.getPosY(), 200,  300);
		arrow2.setVisible(true);
		arrow2.repaint();
	}	
	
	/**
	 * Funzione che restituisce il centro del pannello della transizione
	 */
	public Point getCenter (int x1, int y1, int width, int height) {
		Point centro = new Point();		
		centro.x= x1 + (width/2);
		centro.y= y1 + (height/2);
		return centro;		
		
	}

	
	/*
	 *  GETTERS AND SETTERS
	 */

	public Node getN1() {
		return n1;
	}
	public void setN1(Node n1) {
		this.n1 = n1;
	}

	public Node getN2() {
		return n2;
	}	
	public void setN2(Node n2) {
		this.n2 = n2;
	}

	public Point getP1() {
		return p1;
	}	
	public void setP1(Point p1) {
		this.p1 = p1;
	}

	public Point getP2() {
		return p2;
	}
	public void setP2(Point p2) {
		this.p2 = p2;
	}	
	
	public Point getP12() {
		return p12;
	}
	public void setP12(Point p12) {
		this.p12 = p12;
	}
	
	public TransitionPanel getPanel() {
		return panel;
	}
	public void setPanel(TransitionPanel panel) {
		this.panel = panel;
	}

	public String getTranCode() {
		return tranCode;
	}
	public void setTranCode(String tranCode) {
		this.tranCode = tranCode;
	}

	public ArrayList<Description> getDescriptions() {
		return descriptions;
	}
	public void setDescriptions(ArrayList<Description> descriptions) {
		this.descriptions = descriptions;
	}

	public WorkFlow getTranWF() {
		return tranWF;
	}
	public void setTranWF(WorkFlow tranWF) {
		this.tranWF = tranWF;
	}

	public ArrowPanel getArrow1() {
		return arrow1;
	}
	public void setArrow1(ArrowPanel arrow1) {
		this.arrow1 = arrow1;
	}

	public ArrowPanel getArrow2() {
		return arrow2;
	}
	public void setArrow2(ArrowPanel arrow2) {
		this.arrow2 = arrow2;
	}

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

	public String getN1Code() {
		return n1Code;
	}
	public void setN1Code(String n1Code) {
		this.n1Code = n1Code;
	}

	public String getN2Code() {
		return n2Code;
	}
	public void setN2Code(String n2Code) {
		this.n2Code = n2Code;
	}
	
	public int getTranIndex() {
		return tranIndex;
	}
	public void setTranIndex(int tranIndex) {
		this.tranIndex = tranIndex;
	}

	public boolean isDef() {
		return def;
	}
	public void setDef(boolean def) {
		this.def = def;
	}

	@Override
	public String toString() {
		return "Transition [n1=" + n1 + ", n2=" + n2 + ", n1Code=" + n1Code + ", n2Code=" + n2Code + ", tranWF="
				+ tranWF + ", p1=" + p1 + ", p2=" + p2 + ", p12=" + p12 + ", panel=" + panel + ", posX=" + posX
				+ ", posY=" + posY + ", tranCode=" + tranCode + ", descriptions=" + descriptions + ", arrow1=" + arrow1
				+ ", arrow2=" + arrow2 + ", freccia=" + freccia + "]";
	}
}