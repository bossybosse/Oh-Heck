package view;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * Panel for showing in game chat with other players
 * 
 * @author Richard Bosse
 * 
 */
public class ChatPanel extends JPanel {

	private static final int buffer = 10;
	private static final long serialVersionUID = -8779707120717706071L;
	private JTextArea chatTextArea; // chat log displayed here


	/**
	 * Constructor for the chat panel 
	 * 
	 * @param clientName
	 *            : The name of the user
	 * @param server
	 *            : The socket to the chat server
	 */
	public ChatPanel() {
		
		setUpGUI();

	}

	/**
	 * Sets up the Chat Panel Gui
	 */
	private void setUpGUI() {

		// Sets the layout
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		// Initiate the objects
		chatTextArea = new JTextArea();
		chatTextArea.setBackground(Color.WHITE);
		chatTextArea.setEditable(false);
		chatTextArea.setLineWrap(true);
		chatTextArea.setWrapStyleWord(true);
		
		// Sets the text area with constants
		c.gridx = 0; // First Column
		c.gridy = 0; // Top Row
		c.weightx = 1; // Takes up more space on x axis
		c.weighty = 1; // Takes up more space on y axis
		c.fill = GridBagConstraints.BOTH;
		this.add(new JScrollPane(chatTextArea), c);

	}

	public void updateChatLog(String message) {
		chatTextArea.append(message + "\n");
		chatTextArea.setCaretPosition(chatTextArea.getDocument().getLength());
	}
}
