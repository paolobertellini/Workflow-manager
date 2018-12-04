package elements;

import java.awt.Point;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import ui.ArrowPanel;
import ui.TransitionPanel;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "node")

public class Transition {

	@XmlTransient
	public Node n1;
	@XmlTransient
	public Node n2;
	
	//@XmlTransient
	public Point p1;
	//@XmlTransient
	public Point p2;
	
	@XmlTransient
	public TransitionPanel panel;
	
	//transition identification
	@XmlTransient
	public int tran_id;
	
	int pos_x;
	int pos_y;

	//transition values
	public String tran_code;
	public String desc;
	public String language;
	
	@XmlTransient
	public WorkFlow tranWF;
	@XmlTransient
	public ArrowPanel arrow;
	
	
	/*
	 *  CONSTRUCTORS
	 */
	
	public Transition() {
		this.panel = new TransitionPanel();
	}

	public Transition(Node n1, Node n2) {
		super();
		this.n1 = n1;
		this.n2 = n2;
		this.tranWF = n1.nodeWF;
		this.arrow = new ArrowPanel(getCenter(n1.pos_x, n1.pos_y, n1.getNode_size().width,  n1.getNode_size().height), 
				getCenter(n2.pos_x, n2.pos_y, n2.getNode_size().width,  n2.getNode_size().height));
		
		this.pos_x = ((arrow.p1.x + arrow.p2.x)/2) - 112;
		this.pos_y = (arrow.p1.y + arrow.p2.y)/2 - 50;
		this.panel = new TransitionPanel(this);
		
		paintTransition();
		
	}
	
	public void paintTransition() {
		
		tranWF.panel.add(panel);
		panel.setSize(225, 100);
		panel.setLocation(pos_x, pos_y);
		panel.setVisible(true);
		panel.repaint();
		panel.revalidate();
		
		arrow.p1=getCenter(n1.getPos_x(), n1.getPos_y(), n1.getNode_size().width,  n1.getNode_size().height);
		arrow.p2=getCenter(n2.getPos_x(), n2.getPos_y(), n2.getNode_size().width,  n2.getNode_size().height);
		
		setPos_x((arrow.p1.x + arrow.p2.x)/2 - 112);
		setPos_y((arrow.p1.y + arrow.p2.y)/2 - 50);
		
		tranWF.panel.add(arrow);
		
		arrow.setSize(1024, 760);
		arrow.setVisible(true);
		arrow.repaint();
		arrow.revalidate();
		
		

	}
	
	public Point getCenter (int x1, int y1, int width, int height) {
		Point centro = new Point();		
		centro.x= x1 + (width/2);
		centro.y= y1 + (height/2);
		return centro;		
	}
	
	public Point getMiddle_left (int x1, int height) {
		Point centro = new Point();
		centro.x= x1;
		centro.y = height/2;
		return centro;
	}
	
	public Point getMiddle_left (int x1,int width,  int height) {
		Point centro = new Point();
		centro.x= x1 + width;
		centro.y = height/2;
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

	public TransitionPanel getPanel() {
		return panel;
	}
	public void setPanel(TransitionPanel panel) {
		this.panel = panel;
	}

	public int getTran_id() {
		return tran_id;
	}
	public void setTran_id(int tran_id) {
		this.tran_id = tran_id;
	}

	public String getTran_code() {
		return tran_code;
	}
	public void setTran_code(String tran_code) {
		this.tran_code = tran_code;
	}

	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}

	public WorkFlow getTranWF() {
		return tranWF;
	}
	public void setTranWF(WorkFlow tranWF) {
		this.tranWF = tranWF;
	}

	public ArrowPanel getArrow() {
		return arrow;
	}
	public void setArrow(ArrowPanel arrow) {
		this.arrow = arrow;
	}

	public int getPos_x() {
		return pos_x;
	}
	public void setPos_x(int pos_x) {
		this.pos_x = pos_x;
	}

	public int getPos_y() {
		return pos_y;
	}
	public void setPos_y(int pos_y) {
		this.pos_y = pos_y;
	}
	
	
}
