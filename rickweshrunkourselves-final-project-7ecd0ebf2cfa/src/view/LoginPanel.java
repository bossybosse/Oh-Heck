package view;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.*;

import listeners.LoginListener;
import listeners.RegisterListener;


/**
 * Panel to allow the user to login or register for an account. This panel will
 * be the only one displayed until a user is logged on or registered.
 * 
 * @author Richard Bosse
 * @param <LoginButtonListener>
 * 
 */
public class LoginPanel extends JPanel {


	private static final long serialVersionUID = -1888517983726514560L;
	private JTextField userName;
	private JPasswordField password;
	private JButton loginButton;
	private JButton registerButton;
	private JLabel userNameLabel;
	private JLabel passwordLabel;
	private static final String logoFileName = "./images/logo.png";

	// Magic Numbers!!
	private static final int labelXLocation = 250;
	private static final int labelYLocation = 420;
	private static final int labelXSize = 120;
	private static final int labelYSize = 20;
	private static final int textFieldXSize = 150;
	private static final int textFieldYSize = 20;
	private static final int xFieldOffset = 130;
	private static final int yFieldOffset = 30;
	private static final int buffer = 10;
	private static final int xButtonSize = 130;
	private static final int yButtonSize = 50;
	private static final int imageY2Cordinage = 420;
	private static final int imageX2Cordinate = 700;
	private static final int imageYCordinate = 20;
	private static final int imageXCordinate = 100;

	/**
	 * Constructor for the LoginPanel. First the GUI will be set up then the
	 * listeners
	 * @param loginButtonListener 
	 * @param registerListener 
	 */	
	public LoginPanel(LoginListener loginButtonListener, RegisterListener registerListener) {
		setUpGUI();
		setUpListeners(loginButtonListener, registerListener);
	}

	/**
	 * Sets up the listeners for the buttons
	 * @param registerListener 
	 * @param aListener 
	 */
	private void setUpListeners(LoginListener loginButtonListener, RegisterListener registerListener) {
		//TODO: Create listener for registerButton
		loginButton.addActionListener(loginButtonListener);
		registerButton.addActionListener(registerListener);
	}

	/**
	 * Sets up the panel for the login. Uses null layout manager
	 */
	private void setUpGUI() {

		// All the components to the panel
		userName = new JTextField();
		password = new JPasswordField();
		userNameLabel = new JLabel("UserName");
		passwordLabel = new JLabel("Password");
		loginButton = new JButton("Login");
		registerButton = new JButton("Register");

		// Null layout for custom layout
		this.setLayout(null);

		// Sets the size and location of all the labels, fields, and buttons
		userNameLabel.setLocation(labelXLocation, labelYLocation);
		userNameLabel.setSize(labelXSize, labelYSize);

		userName.setLocation(labelXLocation + xFieldOffset, labelYLocation);
		userName.setSize(textFieldXSize, textFieldYSize);

		passwordLabel.setLocation(labelXLocation, labelYLocation + labelYSize
				+ buffer);
		passwordLabel.setSize(labelXSize, labelYSize);

		password.setLocation(labelXLocation + xFieldOffset, labelYLocation
				+ yFieldOffset);
		password.setSize(textFieldXSize, textFieldYSize);

		loginButton.setLocation(labelXLocation, labelYLocation + labelYSize
				+ textFieldYSize + buffer * 3);
		loginButton.setSize(xButtonSize, yButtonSize);

		registerButton.setLocation(labelXLocation + xFieldOffset + buffer
				+ buffer, labelYLocation + labelYSize + textFieldYSize + buffer
				* 3);
		registerButton.setSize(xButtonSize, yButtonSize);

		
		// Adds everything to the panel
		this.add(userNameLabel);
		this.add(userName);
		this.add(passwordLabel);
		this.add(password);
		this.add(loginButton);
		this.add(registerButton);
		this.repaint();
	}

	/**
	 * Paint component to paint the logo on the panel
	 */
	@Override
	public void paintComponent(Graphics g) {

		// Gets the image
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File(logoFileName));
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (image == null)
			return;

		// Draws the image with auto-scaling
		g.drawImage(image, imageXCordinate, imageYCordinate, imageX2Cordinate,
				imageY2Cordinage, 0, 0, image.getWidth(), image.getHeight(),
				null);
	}

	public String getUserName() {
		return userName.getText();
		
	}

	/**
	 * Gets the password from the password field
	 * @return
	 */
	public String getPassword() {
		// Get the password
		char []code = password.getPassword();
		
		// Make the password string
		String password = "";
		
		// convert the char[] to string
		for (char c: code){
			password += c;
		}
		
		// Return the password string
		return password;
	}

	public void clearNameAndPassword() {
		userName.setText("");
		password.setText(null);
		
	}
}