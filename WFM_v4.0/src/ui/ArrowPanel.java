
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

	public ArrowPanel(Point p1, Point p2)
    {
        barb = 10;                   // barb length
        phi = Math.PI/6;             // 30 degrees barb angle
        setOpaque(false);
        this.p1=p1;
        this.p2=p2;
    }
	
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        double theta;
  
        double x1 =p1.getX(), y1 = p1.getY(), x2 = p2.getX(), y2 = p2.getY();
  
        g2.setPaint(Color.black);
        g2.draw(new Line2D.Double(x1, y1, x2, y2));
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
    
    
    @Override
	public String toString() {
		return "ArrowPanel [barb=" + barb + ", phi=" + phi + ", p1=" + p1 + ", p2=" + p2 + "]";
	}

}