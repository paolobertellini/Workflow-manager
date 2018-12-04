package ui;

import java.awt.Color;
import javax.swing.JPanel;

public class WorkFlowPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	public WorkFlowPanel() {
		Color background = new Color(255, 242, 204);
		this.setBackground(background);
		this.setVisible(true);
		this.setLayout(null);
		this.revalidate();
		this.repaint();
	}
}
