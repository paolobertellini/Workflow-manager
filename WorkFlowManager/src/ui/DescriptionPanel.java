package ui;

import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.JTextArea;
import java.awt.Insets;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.border.TitledBorder;

import elements.Description;
import elements.Node;
import elements.Transition;

import javax.swing.JComboBox;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Cursor;


// TODO: Auto-generated Javadoc
/**
 *  Il pannello relativo ad una descrizione.
 */
public class DescriptionPanel extends JPanel {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** La descrizione associata al pannello. */
	private Description description;
	
	/** La lista di lingue presenti nel pannello. */
	private String[] languages = {"--", "EN", "IT"};
	
	/**
	 * Instanzia un nuovo description panel.
	 *
	 * @param description la descrizione associata al pannello
	 */
	public DescriptionPanel(Description description) {
		
		this.description=description;
		
		// pannello per la lingua
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Language", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		// pannello per la descrizione
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Description", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		// impostazioni layout
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
				.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 149, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
					.addGap(1)
					.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE))
		);
		panel_1.setLayout(new GridLayout(1, 0, 0, 0));
		
		/*
		 *  TEXT AREA
		 */
		JTextArea textArea = new JTextArea();
		textArea.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
		textArea.setMaximumSize(new Dimension(4, 22));
		textArea.setText(description.getText());
		textArea.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(textArea.getText().length() < 100)
					description.setText(textArea.getText());
				else
					textArea.setText(description.getText());
			}
		});
		panel_1.add(textArea);
		textArea.setLineWrap(true);
		
		/*
		 *  LANGUAGE COMBOBOX
		 */
		JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.setEditable(true);
		comboBox.setModel(new DefaultComboBoxModel<String>(languages));
		comboBox.setSelectedItem(description.getLanguage());
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				description.setLanguage((String) comboBox.getSelectedItem());
				System.out.println((String) comboBox.getSelectedItem());
				description.getTab().setTitleAt(description.getTab().getSelectedIndex(), (String) comboBox.getSelectedItem());
			}
		});
		
		/*
		 *  button REMOVE
		 */
		JButton btnRemoveThisDescription = new JButton("Remove");
		btnRemoveThisDescription.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int i=getDescription().getTab().getSelectedIndex();
				if(getDescription().type.getClass().equals(new Node().getClass())) {
					((Node) getDescription().type).getDescriptions().remove(i);
				}
				if(getDescription().type.getClass().equals(new Transition().getClass())) {
					((Transition) getDescription().type).getDescriptions().remove(i);
				}
				getDescription().getTab().remove(i);
			}
		});
		btnRemoveThisDescription.setMargin(new Insets(2, 6, 2, 6));
		btnRemoveThisDescription.setIconTextGap(2);
		btnRemoveThisDescription.setFont(new Font("Tahoma", Font.PLAIN, 9));
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, 76, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnRemoveThisDescription)
					.addGap(31))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnRemoveThisDescription, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);
		setLayout(groupLayout);

	}

	/**
	 * GETTERS e SETTERS
	 */
	
	public Description getDescription() {
		return description;
	}
	public void setDescription(Description description) {
		this.description = description;
	}

	public String[] getLanguages() {
		return languages;
	}
	public void setLanguages(String[] languages) {
		this.languages = languages;
	}
		
}
