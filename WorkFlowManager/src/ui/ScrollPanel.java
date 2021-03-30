package ui;

import javax.swing.JScrollPane;

// TODO: Auto-generated Javadoc
/**
 * Permette lo scroll sul pannello di lavoro.
 */
public class ScrollPanel extends JScrollPane{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Instanzia un nuovo scroll panel con il relativo pannello di lavoro.
	 *
	 * @param panel il pannello di lavoro a cui si aggiunge il scroll panel
	 */
	public ScrollPanel(WorkFlowPanel panel) {
		super(panel);
	}

}
