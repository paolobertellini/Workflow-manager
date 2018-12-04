
package elements;

import java.awt.Dimension;
import java.io.File;
import java.io.Serializable;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import ui.NodePanel;
import ui.TransitionPanel;
import ui.WorkFlowPanel;

@XmlRootElement(name = "node_list")
@XmlAccessorType(XmlAccessType.FIELD)

public class WorkFlow implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@XmlTransient
	public WorkFlowPanel panel;
	
	//nodes list
	@XmlElementWrapper(name="nodes")
	public ArrayList<Node> nodes = new ArrayList<Node>();
	
	int next_index;
		
	//transitions list
	@XmlElementWrapper(name="transitions")
	public ArrayList<Transition> transitions = new ArrayList<Transition>();
	
	@XmlTransient
	File file;
	
	//node position and size
	int start_x=20;
	int start_y=20;
	
	@XmlTransient
	Dimension node_size = new Dimension(150, 265);
	
	//new transition elements
	@XmlTransient
	public Node start;
	@XmlTransient
	public boolean start_taken = false;
	@XmlTransient
	public Node end;
	@XmlTransient
	public boolean end_taken = false;
	
	
	/*
	 *  CONSTRUCTORS
	 */
	
	public WorkFlow() {
		super();
		this.panel = new WorkFlowPanel();
		this.panel.setLayout(null);
		this.file = null;
		this.next_index = 0;
	}
	
	/*
	 *  GETTERS AND SETTERS @XmlTransient
	 */
	
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

	public int getNext_index() {
		return next_index;
	}
	public void setNext_index(int next_index) {
		this.next_index = next_index;
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

	public int getStart_x() {
		return start_x;
	}
	public void setStart_x(int start_x) {
		this.start_x = start_x;
	}

	public int getStart_y() {
		return start_y;
	}
	public void setStart_y(int start_y) {
		this.start_y = start_y;
	}

	public Dimension getNode_size() {
		return node_size;
	}
	public void setNode_size(Dimension node_size) {
		this.node_size = node_size;
	}

	public Node getStart() {
		return start;
	}
	public void setStart(Node start) {
		this.start = start;
	}

	public boolean isStart_taken() {
		return start_taken;
	}
	public void setStart_taken(boolean start_taken) {
		this.start_taken = start_taken;
	}

	public Node getEnd() {
		return end;
	}
	public void setEnd(Node end) {
		this.end = end;
	}

	public boolean isEnd_taken() {
		return end_taken;
	}
	public void setEnd_taken(boolean end_taken) {
		this.end_taken = end_taken;
	}
	
	
	/*
	 *  PRINT WORKFLOW COMPONENTS
	 */

	public void printWorkFlow() {
		
		//print nodes
		for(Node n: nodes) {
			NodePanel newNodePanel = new NodePanel(n);
			panel.add(newNodePanel);
			n.setPanel(newNodePanel);
			n.setNodeWF(this);
		}
		
		//print transitions
		for(Transition t: transitions) {
			TransitionPanel newTransitionPanel = new TransitionPanel(t);
			panel.add(newTransitionPanel);
			t.setPanel(newTransitionPanel);
			t.setTranWF(this);
			t.paintTransition();
		}
	}
	
	public void deleteNode(String node_code) {
		for(Node n: nodes) {
			System.out.println(n);
			if(node_code.equals(n.node_code) || n.node_code.equals("")) {
				nodes.get(nodes.indexOf(n)).panel.setVisible(false);
				nodes.remove(nodes.indexOf(n));
			}
		}
	}
	
	public int getNodeIndexByCode(String node_code) {
		int i=0;
		for(Node n: nodes) {
			if(node_code.equals(n.node_code) || n.node_code.equals("")) {
				return i;
			}
			i++;
		}
		return -1;
	}
	
	
	/*
	 *  JAVA-XML TRANSLATION
	 */
	public String java_xml_Translate() throws JAXBException {
		
		JAXBContext jaxbContext = JAXBContext.newInstance(this.getClass());
		Marshaller marshaller = jaxbContext.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		StringWriter writer = new StringWriter();
		marshaller.marshal(this, writer);
		return writer.toString();		
	}
	
	
	/*
	 *  XML-JAVA TRANSLATION -- valutarne l'utilità
	 */
	public WorkFlow xml_java_Translate(String xml) throws JAXBException {
		
		JAXBContext jaxbContext = JAXBContext.newInstance(this.getClass());
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		StringReader reader = new StringReader(xml);
		return (WorkFlow) unmarshaller.unmarshal(reader);
	}


	@Override
	public String toString() {
		return "WorkFlow [nodes=" + nodes + ", transitions=" + transitions + ", file=" + file + ", start_x=" + start_x
				+ ", start_y=" + start_y + "]";
	}

}
