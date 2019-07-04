package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import view.RWSOgui;
import client.RWSOClient;

/**
 * Login Listener
 * @author Richard Bosse
 * 
 * Controller for the login button. When pressed, the button asks the client to verify.
 * If it cannot verify, then the user is notified and the panel is reset.
 *
 */
public class LoginListener implements ActionListener {
	
	// References to the view and client
	private RWSOgui view;
	private RWSOClient client;

	/**
	 * Constructor for the listener
	 * @param view
	 * @param client
	 */
	public LoginListener(RWSOgui view, RWSOClient client) {
		this.view = view;
		this.client = client;
	}

	/**
	 * Reads in the user name and password from the panel and sends it to the
	 * client to be verified. If not a valid login, the panel is reset after a prompt
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
	
		// If the user is not valid, then reset the login.
		if (!client.validateUser(view.getUserName(), view.getPassword())){
			JOptionPane.showMessageDialog(null, "Incorrect user name or password. Please try again");
			view.resetLogin();
			return;
		}
		
		// If the user is valid, then set up the new view.
		view.setUpGameView();
		client.sendLookandScoreCommand();
	}
}