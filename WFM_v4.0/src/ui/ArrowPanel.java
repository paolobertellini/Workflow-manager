package ui;

import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
  
public class ArrowPanel extends JPanel
{


	private static final long serialVersionUID = 1L;
	int barb;
    double phi;
    public Point p1;
    public Point p2;
    private Dimension dim= new Dimension();
    
	public ArrowPanel(Point p1, Point p2)
    {
        barb = 10;                   // barb length
        phi = Math.PI/6;             // 30 degrees barb angle
       // setOpaque(false);
        setBackground(Color.blue);
        this.p1=p1;
        this.p2=p2;
        
        if (p1.x < p2.x) {
        	this.dim.width = p2.x - p1.x;
        }
        else {
        	this.dim.width = p1.x - p2.x;
        }
        if (p1.y < p2.y) {
        	this.dim.height = p2.y - p1.y;
        }
        else {
        	this.dim.height = p1.y - p2.y;
        }
    }
	
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        double theta;
  
        double x1 =0, y1 = 0, x2 = dim.getWidth(), y2 = dim.getHeight();
  
        g2.setPaint(Color.black);
        g2.draw(new Line2D.Double(x1, y1, x2 , y2 ));
        theta = Math.atan2(y2 - y1, x2 - x1);
        Point m = new Point();
        m.x=(int) ((x1+x2)/2);
        m.y=(int) ((y1+y2)/2);
        drawArrow(g2, theta, (m.x+x1)/2, (m.y+y1)/2);  
        drawArrow(g2, theta, (m.x+x2)/2, (m.y+y2)/2); 
  
    }
    
    //FUNZIONE CHE DISEGNA LA PUNTA DELLA FRECCIA 
    //(COME COPPIA DI LINEE CHE SI INTERSECANO NELLO STESSO PUNTO x0, y0/
    
    private void drawArrow(Graphics2D g2, double theta, double x0, double y0) {
        double x = x0 - barb * Math.cos(theta + phi);
        double y = y0 - barb * Math.sin(theta + phi);
        g2.draw(new Line2D.Double(x0, y0, x, y));
        x = x0 - barb * Math.cos(theta - phi);
        y = y0 - barb * Math.sin(theta - phi);
        g2.draw(new Line2D.Double(x0, y0, x, y));
    }
    
    
    
    public Dimension getDim() {
		return dim;
	}

	public void setDim(Dimension dim) {
		this.dim = dim;
	}

	@Override
	public String toString() {
		return "ArrowPanel [barb=" + barb + ", phi=" + phi + ", p1=" + p1 + ", p2=" + p2 + "]";
	}

}