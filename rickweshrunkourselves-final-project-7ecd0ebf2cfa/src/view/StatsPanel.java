package view;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class StatsPanel extends JPanel {

	private static final int buffer = 10;
	private JTextArea statsDisplayArea; // chat log displayed here

	public StatsPanel() {

		setUpGUI();

	}

	private void setUpGUI() {

		// Sets the layout
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		// Initiate the objects
		statsDisplayArea = new JTextArea();
		statsDisplayArea.setBackground(Color.WHITE);
		statsDisplayArea.setEditable(false);
		statsDisplayArea.setLineWrap(true);
		statsDisplayArea.setWrapStyleWord(true);

		// Sets the text area with constants
		c.gridx = 0; // First Column
		c.gridy = 0; // Top Row
		c.weightx = 1; // Takes up more space on x axis
		c.weighty = 1; // Takes up more space on y axis
		c.fill = GridBagConstraints.BOTH;
		this.add(new JScrollPane(statsDisplayArea), c);

	}
	
	public void updateStatsPanel(String updatedStats){
		statsDisplayArea.setText(updatedStats);
	}

}
