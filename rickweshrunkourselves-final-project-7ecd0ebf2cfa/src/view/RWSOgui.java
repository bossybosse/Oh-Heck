package view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import listeners.*;
import client.RWSOClient;

public class RWSOgui{

	private static final long serialVersionUID = -4520094504783344689L;
	private static final String gameTitle = "Rick, We Shrunk Ourselves!";
	private static final int buffer = 10;
	private ChatPanel chatPanel;
	private GamePanel gamePanel;
	private StatsPanel statsPanel;
	private LoginPanel loginPanel;
	private InventoryPanel inventoryPanel;
	private static JFrame gameWindow;
	private GridBagLayout theLayout;
	private GridBagConstraints c;
	private LoginListener loginListener;
	private RegisterListener registerListener;
	private WindowCloseListener windowListener;
	
	private ArrowKeyListener arrowKeyListener;
	
	/**
	 * Constructor for the GUI. Sets up the listeners then the view.
	 * 
	 * @param client
	 * @param model
	 */
	public RWSOgui(RWSOClient client) {
		
		setUpListeners(client);
		setUpView();
		
	}

	/**
	 * Sets up all the listeners the GUI needs
	 * @param client
	 */
	private void setUpListeners(RWSOClient client) {
		loginListener = new LoginListener(this, client);
		registerListener = new RegisterListener(this, client);
		windowListener = new WindowCloseListener(client);
	//gameListener = new GameEnterListener(this, client);
		arrowKeyListener = new ArrowKeyListener(this, client);
	}

	/**
	 * Creates the Frame with the view
	 */
	private void setUpView() {

		// Create the JFrame and add the window listener
		gameWindow = new JFrame();
		gameWindow.addWindowListener(windowListener);

		// Define the layout manager
		theLayout = new GridBagLayout();
		c = new GridBagConstraints();

		// Define the window of the GUI
		gameWindow.setTitle(gameTitle);
		gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameWindow.setLayout(theLayout);
		gameWindow.setPreferredSize(new Dimension(800, 600));
		gameWindow.setResizable(false);
		gameWindow.setLocation(20, 20);

		// Set up and add the login panel
		loginPanel = new LoginPanel(loginListener, registerListener);
		c.weightx = 1; // Takes up more space on x axis
		c.weighty = 1; // Takes up more space on y axis
		c.fill = GridBagConstraints.BOTH;
		gameWindow.add(loginPanel, c);

		// Reset the constants
		c.weightx = 0;
		c.weighty = 0;
		c.fill = GridBagConstraints.NONE;

		// Sets the visibility.. KEEP AT THE BOTTOM OF THE METHOD
		gameWindow.pack();
		gameWindow.setVisible(true);
		gameWindow.repaint();
		
		
		

	}

	/**
	 * Sets up the game view after user has been authenticated
	 */
	public void setUpGameView() {

		// Creates the panels
		//gamePanel = new GamePanel(gameListener);
		gamePanel = new GamePanel(arrowKeyListener);
		statsPanel = new StatsPanel();
		chatPanel = new ChatPanel();
		inventoryPanel = new InventoryPanel();
		
		

		// Removes the login panel
		gameWindow.remove(loginPanel);

		// Sets the layout of the panels
		c.weightx = 1; // Takes up more space on x axis
		c.weighty = 1; // Takes up more space on y axis
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		c.gridheight = 5;
		c.insets = new Insets(buffer, buffer, buffer, buffer);
		gameWindow.add(gamePanel, c);
		
		c.weightx = 0; // Takes up more space on x axis
		c.weighty = .08; // Takes up more space on y axis
		c.gridheight = 1;
		c.gridx = 1;
		c.gridy = 1;
		gameWindow.add(statsPanel, c);
		
		c.weighty = .3;
		c.gridheight = 1;
		c.gridx = 1;
		c.gridy = 2;
		gameWindow.add(inventoryPanel, c);
		
		
		c.weightx = 1; // Takes up more space on x axis
		c.weighty = 1; // Takes up more space on y axis
		c.gridheight = 2;
		c.gridx = 1;
		c.gridy = 3;
		gameWindow.add(chatPanel, c);
		
		
		gameWindow.pack();
		gameWindow.setVisible(true);
		gameWindow.repaint();
	}

	/**
	 * Gets the username from the loginPanel
	 * @return
	 */
	public String getUserName() {		
		return loginPanel.getUserName();

	}

	/**
	 * Gets the password from the loginPanel
	 * @return
	 */
	public String getPassword() {
		return loginPanel.getPassword();

	}

	/**
	 * Resets the loginPanel if the credential fail
	 */
	public void resetLogin() {
		loginPanel.clearNameAndPassword();

	}

	/**
	 * Gets the commands from the game console
	 * @return
	 */
	public String getCommand() {
		return gamePanel.getCommand();
	}

	public void updateChatMessage(String message) {
		chatPanel.updateChatLog(message);
	}

	public void updateGameConsole(String message) {
		gamePanel.updateTextArea(message);
		
	}

	public void close() {
		gameWindow.dispose();		
	}

	public void updateScoreConsole(String score) {
		statsPanel.updateStatsPanel(score);
		
	}

	public void clearConsole() {
		gamePanel.clearTextArea();
		gameWindow.repaint();
	}

	public void updateInventoryConsole(String inventory) {
		inventoryPanel.updateInventory(inventory);
		
	}

	public void setCommandFromMemory(String savedCommand) {
		gamePanel.setCommandFromMemory(savedCommand);
		
	}
	
}
