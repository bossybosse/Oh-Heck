package view;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class InventoryPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2022428322249191241L;
	private static final int buffer = 10;
	private JTextArea inventory;

	public InventoryPanel() {
		setUpGUI();
	}

	public void setUpGUI() {
		// Sets the layout
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		// Initiate the objects
		inventory = new JTextArea();
		inventory.setBackground(Color.WHITE);

		inventory.setEditable(false);

		// Sets the text area with constants
		c.gridx = 0; // First Column
		c.gridy = 0; // Top Row
		c.weightx = 1; // Takes up more space on x axis
		c.weighty = 1; // Takes up more space on y axis
		c.fill = GridBagConstraints.BOTH;
		this.add(new JScrollPane(inventory), c);

	}

	public void updateInventory(String updatedInventory) {
		inventory.setText(updatedInventory);
	}

}
