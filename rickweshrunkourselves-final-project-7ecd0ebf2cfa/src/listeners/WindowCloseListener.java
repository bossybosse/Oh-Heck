package listeners;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JOptionPane;

import view.RWSOgui;
import client.RWSOClient;

public class WindowCloseListener implements WindowListener {

	// References to the client
	private RWSOClient client;

	/**
	 * Constructor for the listener
	 * 
	 * @param view
	 * @param client
	 */
	public WindowCloseListener(RWSOClient client) {
		
		this.client = client;
	}

	@Override
		public void windowClosing(WindowEvent arg0) {
			client.sendDisconnectCommand();
			JOptionPane.showMessageDialog(null,"You have disconnected from the game. Thank you for playing!");
		}

	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		

	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

}
