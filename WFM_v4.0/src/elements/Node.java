
package elements;

import java.awt.Dimension;
import java.beans.Transient;
import java.io.Serializable;

import javax.xml.bind.annotation.*;

import ui.NodePanel;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "node")

public class Node implements Serializable{

	private static final long serialVersionUID = 1L;

	// position and size
	public int pos_x;
	public int pos_y;
	@XmlTransient
	private Dimension node_size;
	
	//node identification
	public int node_id;
	
	@XmlTransient
	public WorkFlow nodeWF;

	//node values
	public String node_code;
	public String desc;
	public String language;
	
	@XmlTransient
	public NodePanel panel;
	
	
	public Node() {
		super();
		this.panel = new NodePanel(this);
		this.panel.revalidate();
		this.panel.setVisible(true);
	}
	
	public Node(WorkFlow node_WF) {
		super();
		this.nodeWF = node_WF;
		this.pos_x = node_WF.start_x;
		this.pos_y = node_WF.start_y;
		this.node_size = node_WF.node_size;
		this.node_id = node_WF.next_index;
		this.node_code = "";
		this.panel = new NodePanel(this);
		
	}

	/*
	 *  GETTER AND SETTERS
	 */

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

	public Dimension getNode_size() {
		return node_size;
	}
	public void setNode_size(Dimension node_size) {
		this.node_size = node_size;
	}

	public int getNode_id() {
		return node_id;
	}
	public void setNode_id(int node_id) {
		this.node_id = node_id;
	}

	public WorkFlow getNodeWF() {
		return nodeWF;
	}
	public void setNodeWF(WorkFlow nodeWF) {
		this.nodeWF = nodeWF;
	}

	public String getNode_code() {
		return node_code;
	}
	public void setNode_code(String node_code) {
		this.node_code = node_code;
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

	public NodePanel getPanel() {
		return panel;
	}
	public void setPanel(NodePanel panel) {
		this.panel = panel;
	}
	
	
	@Override
	public String toString() {
		return "Node [pos_x=" + pos_x + ", pos_y=" + pos_y + ", node_id=" + node_id + ", node_code=" + node_code
				+ ", desc=" + desc + ", language=" + language + "]";
	}
	
}
