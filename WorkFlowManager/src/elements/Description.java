package elements;

import java.io.Serializable;

import javax.swing.JTabbedPane;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import ui.DescriptionPanel;

// TODO: Auto-generated Javadoc
/**
 * Contiene le informazioni relative a una DESCRIZIONE 
 * di un nodo o di una transizione.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "description")
public class Description implements Serializable{

	private static final long serialVersionUID = 1L;

	/** Lingua della descrizione */
	private String language;
	
	/** Testo della descrizione */
	private String text;
	
	/** Pannello della descrizione */
	@XmlTransient
	private DescriptionPanel panel;
	
	/** L'insieme delle descrizioni di un certo nodo / di una certa transizione viene rappresentato tramite un JTabbedPane */
	@XmlTransient
	private JTabbedPane tab;
	
	/** Variabile di tipo Object che rappresenta il tipo di elemento (nodo o descrizione) a cui fa riferimento la descrizione */
	@XmlTransient
	public Object type;

	
	/**
	 * Costruttore della descrizione senza parametri (utilizzato in fase di apertura del file .xml)
	 */
	public Description() {
		super();
		this.panel = new DescriptionPanel(this);
	}

	/**
	 * Costruttore della descrizione a cui vengono passati il JTabbedPane, la lingua, il testo e il tipo dell'elemento che viene descritto
	 */
	public Description(JTabbedPane tab, String language, String text, Object type) {
		super();
		this.setTab(tab);
		this.language = language;
		this.text = text;
		this.panel = new DescriptionPanel(this);
		this.type = type;
	}
	

	/**
	 * GETTERS e SETTERS
	 */
	
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}

	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}

	public DescriptionPanel getPanel() {
		return panel;
	}
	public void setPanel(DescriptionPanel panel) {
		this.panel = panel;
	}
	
	public JTabbedPane getTab() {
		return tab;
	}
	public void setTab(JTabbedPane tab) {
		this.tab = tab;
	}

	@Override
	public String toString() {
		return "Description [language=" + language + ", text=" + text + "]";
	}
}
