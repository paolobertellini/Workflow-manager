package ui;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JLayeredPane;
import javax.swing.Scrollable;

import elements.Node;
import elements.WorkFlow;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

// TODO: Auto-generated Javadoc
/**
 *  Il pannello di lavoro dove vengono visualizzati nodi, transizioni e frecce.
 */
public class WorkFlowPanel extends JLayeredPane implements Scrollable {

	/** Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** ArrowPanel che rappresenta la freccia che viene disegnata nel WorkFlowPanel nel momento in cui è stato scelto il primo 
	 * nodo della transizione ma deve essere ancora selezionato il secondo */
	protected ArrowPanel ap;
	
	/** Dimensione preferita del WorkFlowPanel */
	private Dimension preferredSize = new Dimension(1350, 670);
	
	/** Stato del listener del WorkFlowPanel */
	static boolean listenerState= false;
	
	/** Workflow associato al WorkFlowPanel */
	private WorkFlow workflow;

    /**
     * Costruttore del  WorkFlowPanel a cui viene passato il workflow
     */
    public WorkFlowPanel(WorkFlow workflow) {
        	  
    	// proprietà del pannello
    	Color background = new Color(255, 242, 204);
    	setOpaque(true);
        setBackground(background);
        setLayout(null);
        setFocusable(true);
        this.workflow = workflow;        
        
        addMouseMotionListener(new MouseMotionAdapter() {
    		@Override
    		public void mouseMoved(MouseEvent e) {
    			
    			//Se il listenerState è true, il workflow non è modificabile (non si possono aggiungere nodi o transizioni)
    			if(listenerState) {
    				workflow.setEditable(false);
    				ap.setVisible(true);
	    			ap.end.x = e.getX();
	    			ap.end.y = e.getY();
	    			ap.repaint();
	    			ap.revalidate();
	    			requestFocus();
	    			addKeyListener(new KeyAdapter() {
	    	    		@Override
	    	    		// Se viene premuto ESC, la creazione della transizione viene annullata
	    	    		public void keyPressed(KeyEvent f) {
	    	    			if(f.getKeyCode()== 27) {
	    	    			  ap.setVisible(false);
	    	    			  setListenerState(false);
	    	    			  workflow.setStartTaken(false);
	    	    			  workflow.setStart(null);
	    	    			  workflow.setEndTaken(false);
	    	    			  /* L'operazione è stata annullata: i nodi, se schiacciati, non verranno più selezionati come destinazione
	    	    			   * della transizione
	    	    			   */
	    	    			  for(Node n: workflow.nodes) {
	    	    				  n.getPanel();
								  NodePanel.listenerState=false;
	    	    			
	    	    			  }
	    	    			workflow.setEditable(true);
	    	    			workflow.getToolTextField().setText("Scegliere N per aggiungere un nodo, T per aggiungere una transizione");
	    	    		    }
	    	    	   }
	    	    	});
    			}
    		
    		
    		}
    	});
    }


    /**
     * apply  modifica la preferredSize e la dimensione del WorkFlowPanel
     */
    public void apply(Dimension size) {
        preferredSize = size;
        getWorkflow().setWidth(size.width);
        getWorkflow().setHeight(size.height);
        revalidate();
    }
   
    
    /*
	 *  GETTERS AND SETTERS
	 */

	@Override
    public Dimension getPreferredSize() {
        return preferredSize;
    }	
    @Override
    public Dimension getPreferredScrollableViewportSize() {
        return new Dimension(500, 500);
    }

    @Override
    public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
        return 128;
    }
    @Override
    public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
        return 128;
    }

    @Override
    public boolean getScrollableTracksViewportWidth() {
        return false;
    }
    @Override
    public boolean getScrollableTracksViewportHeight() {
        return false;
    }

	public void setPreferredSize(Dimension preferredSize) {
		this.preferredSize = preferredSize;
	}

	public static boolean isListenerState() {
		return listenerState;
	}
	public static void setListenerState(boolean listenerState) {
		WorkFlowPanel.listenerState = listenerState;
	}

	public WorkFlow getWorkflow() {
		return workflow;
	}
	public void setWorkflow(WorkFlow workflow) {
		this.workflow = workflow;
	}
	
}