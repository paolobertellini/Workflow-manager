package ui;

import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;

// TODO: Auto-generated Javadoc
/**
 *  Il pannello che contiene la freccia.
 */
public class ArrowPanel extends JPanel {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** La lunghezza della punta. */
	private int barb;
	
	/** L'angolo. */
	private double phi;
    
    /** Il punto di partenza della freccia. */
    public Point start; 
    
    /** Il punto di arrivo della freccia. */
    public Point end;
    
    /**
     *  Instanzia un nuovo arrow panel.
     */
    public ArrowPanel() {	
    }
    
	/**
	 * Instanzia un nuovo arrow panel che contiene il componente grafico
	 * con la freccia.
	 *
	 * @param start il punto di partenza della freccia
	 * @param end  il punto di arrivo della freccia
	 */
	public ArrowPanel(Point start, Point end) {
        barb = 10;                  
        phi = Math.PI/6;
        setOpaque(false);
        this.start=start;
        this.end=end;
    }

	/**
     * Disegna il componente grafico
     */
	protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        double theta; 
        double x1 = start.x, y1 = start.y, x2 = end.x, y2 = end.y;
        
        g2.setStroke(new BasicStroke(2.3f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2.setPaint(Color.black);
        
        //disegno la freccia
        g2.draw(new Line2D.Double(x1, y1, x2 , y2 ));
        
        theta = Math.atan2(y2 - y1, x2 - x1);
        Point m = new Point();
        m.x=(int) ((x1+x2)/2);
        m.y=(int) ((y1+y2)/2);
        
        // disegno la punta
        drawArrow(g2, theta,  m.x , m.y);  
    }
	
    /**
     * Disegna la punta della freccia.
     *
     * @param g2 oggetto grafico 2D
     * @param theta angolo della freccia
     * @param x0 the x 0
     * @param y0 the y 0
     */
    private void drawArrow(Graphics2D g2, double theta, double x0, double y0) {

        double x = x0 - barb * Math.cos(theta + phi);
        double y = y0 - barb * Math.sin(theta + phi);
        g2.draw(new Line2D.Double(x0, y0, x, y));
        x = x0 - barb * Math.cos(theta - phi);
        y = y0 - barb * Math.sin(theta - phi);
        g2.draw(new Line2D.Double(x0, y0, x, y));
    }

	@Override
	public String toString() {
		return "ArrowPanel [barb=" + barb + ", phi=" + phi + ", p1=" + start + ", p2=" + end + "]";
	}

}