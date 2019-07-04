package client;

import game.*;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JProgressBar;

import commands.*;
import view.RWSOgui;

public class RWSOClient {

	private static final int REGISTERUSERCOMMAND = 2;
	private static final int VALIDATEUSERCOMMAND = 3;
	private static final int CHECKUSERNAMECOMMANDNUMBER = 1;
	private static final String host = "localhost"; // "192.168.1.2";//
	private static final int port = 9001;
	private String username;
	private Socket server;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private RWSOgui view;
	private LinkedList<String> enteredCommands;

	public static void main(String args[]) {
		new RWSOClient();
	}

	public RWSOClient() {
		
		enteredCommands = new LinkedList<String>();
		view = new RWSOgui(this);
		
		// Set up the JProgressBar
		JProgressBar pb = new JProgressBar();
		pb.setIndeterminate(true);
		JLabel label = new JLabel("Connecting to server. Please wait.");
		pb.setPreferredSize(new Dimension(175, 20));

		JPanel center_panel = new JPanel();
		center_panel.add(label);
		center_panel.add(pb);
		JDialog dialog = new JDialog();
		dialog.setLocation(250, 300);
		dialog.getContentPane().add(center_panel, BorderLayout.CENTER);
		dialog.pack();
		dialog.setVisible(true);

		setUpServerConnection();
		dialog.dispose();
	}

	private void setUpServerConnection() {
		try {
			server = new Socket(host, port);
			out = new ObjectOutputStream(server.getOutputStream());
			in = new ObjectInputStream(server.getInputStream());

		} catch (IOException e) {
			JOptionPane
					.showMessageDialog(null,
							"Cannot establish connection with the game server. Please try again later.");
			view.close();
		}

	}

	private class ServerHandler implements Runnable {

		public void run() {
			try {
				while (true) {
					// read a command from server and execute it

					@SuppressWarnings("unchecked")
					Command<RWSOClient> c = (Command<RWSOClient>) in
							.readObject();
					c.execute(RWSOClient.this);
				}
			} catch (SocketException e) {
				return; // "gracefully" terminate after disconnect
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public boolean validateUser(String userName, String password) {

		this.username = userName;
		try {

			out.writeObject(VALIDATEUSERCOMMAND);
			out.writeObject(userName);
			out.writeObject(password);
			if ((boolean) in.readObject()) {
				new Thread(new ServerHandler()).start();
				return true;
			}

		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;

	}

	public void sendDisconnectCommand() {

		try {
			out.writeObject(new DisconnectServerCommand(username));
			out.close();
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void sendCommand(String command) throws IOException {

		
		enteredCommands.add(command);
		if (!command.contains(" ")) {
			command += " ";
		}

		
		String theCommand = command.substring(0, command.indexOf(" "));
		String theApendage = command.substring(command.indexOf(" ") + 1);

		
		switch (theCommand) {

		case "attack":
			out.writeObject(new AttackCommand(username, theApendage));			
			break;			
		case "look":
			out.writeObject(new LookCommand(username));
			break;
		case "commands":
			view.updateGameConsole(printOutCommands());
			break;
		case "ooc":
			out.writeObject(new AddOOCMessageCommand("ooc--" + username + ": "
					+ theApendage));
			break;
		case "who":
			out.writeObject(new WhoCommand(username));
			break;
		case "say":
			out.writeObject(new AddSayMessageCommand(username, username
					+ " says to everyone in the room: '" + theApendage + "'"));
			break;
		case "tell":

			if (!theApendage.contains(" "))
				view.updateGameConsole("This is an invalid tell command. The command is: tell <player> message");
			else {
				String targetPlayer = theApendage.substring(0,
						theApendage.indexOf(' '));
				String message = theApendage
						.substring(theApendage.indexOf(' ') + 1);

				out.writeObject(new AddTellMessageCommand(username,
						targetPlayer, username + " tells you:  " + message));
			}

			break;
		case "score":
			out.writeObject(new ScoreCommand(username));
			break;
		case "give":
			if (!theApendage.contains(" "))
				view.updateGameConsole("This is an invalid give command. The command is: give <player> <item>");
			else {
				String targetPlayer = theApendage.substring(0,
						theApendage.indexOf(' '));
				String item = theApendage
						.substring(theApendage.indexOf(' ') + 1);

				out.writeObject(new GiveCommand(username, targetPlayer, item));
				out.writeObject(new InventoryCommand(username));
			}
			break;
		case "get":
			if (!theApendage.contains(" "))
				view.updateGameConsole("This is an invalid get command. The command is: get <item> <player>"
						+ "\n use 'pick-up' + <item> to retrieve an item from the room");
			else {
				String item = theApendage
						.substring(0, theApendage.lastIndexOf(' '));
				String targetPlayer = theApendage.substring(theApendage
						.lastIndexOf(' ') + 1);

				out.writeObject(new GetCommand(username, targetPlayer, item));
				out.writeObject(new InventoryCommand(username));
			}
			break;
		case "pick-up":
			out.writeObject(new PickUpCommand(username, theApendage));
			out.writeObject(new InventoryCommand(username));
			break;
		case "inventory":
			out.writeObject(new InventoryCommand(username));
			break;
		case "drop":
			out.writeObject(new DropCommand(username, theApendage));
			out.writeObject(new InventoryCommand(username));
			break;
		case "use":
			out.writeObject(new UseCommand(username, theApendage));
			out.writeObject(new InventoryCommand(username));			
			break;
		case "quit":
			view.close();
			this.sendDisconnectCommand();
			break;
		case "shutdown":
			String input = JOptionPane
					.showInputDialog("You have asked to shut down the RWSO server. Please validate admin credentials");
			out.writeObject(new ShutdownCommand(input));
			break;
		case "move-right":
			out.writeObject(new moveRightCommand(username));
			break;
		case "move-left":
			out.writeObject(new moveLeftCommand(username));
			break;
		case "move-up":
			out.writeObject(new moveUpCommand(username));
			break;
		case "move-down":
			out.writeObject(new moveDownCommand(username));
			break;
		default:
			view.updateGameConsole("Invalid command");
		}

	}

	private String printOutCommands() {
		String result = "commands: lists all the commands useable by the player"
				+ "\n\ndrop <item>: drops an item from the player�s inventory to the room"
				+ "\npick-up+ <item>: pick up an item in a room and add it to your inventory"
				+ "\nget+ <item> + <target>: request an item from target player/MOB/item; added to your inventory with their permission"
				+ "\ngive+ <target> + <item>: offer to transfer an item from your inventory to another player/MOB's inventory"
				+ "\nuse+ <item>: executes the item�s behavior"
				+ "\ninventory: lists the items you are carrying"
				+ "\nlook: gives a 360 degree report of the environment"
				+ "\nscore: displays your statistics (skill, hp, etc.)"
				+ "\nwho: lists all players that are logged in"

				+ "\n\nmove-up: move through north exit (if it exists)"
				+ "\nmove-down: move through south exit (if it exists)"
				+ "\nmove-left: move through east exit (if it exists)"
				+ "\nmove-right: move through west exit (if it exists)"

				+ "\n\nooc+ <message>: Out of Character channel��message goes to everyone currently connected"
				+ "\nsay + <message>: sends a message to all players in the same room as you"
				+ "\ntell+ <player> + <message>: sends a message only to the target player"

				+ "\n\nquit: allows you to exit the system while saving your information and game data"
				+ "\nshutdown: saves the MUD�s data and shuts the server down (only able to be used by admins)";
		return result;
	}

	public boolean checkNameAvaliability(String userName) {

		try {
			out.writeObject(CHECKUSERNAMECOMMANDNUMBER);
			out.writeObject(userName);
			Object temp = in.readObject();
			return (Boolean) temp;
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO: finish
		return false;

	}

	public void registerUser(String userName, String password) {
		this.username = userName;
		try {
			out.writeObject(REGISTERUSERCOMMAND);
			out.writeObject(userName);
			out.writeObject(password);
			if ((Boolean) in.readObject()) {
				new Thread(new ServerHandler()).start();
			} else {
				// TODO: cannot register
			}

		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void startServerHandler() {

		new Thread(new ServerHandler()).start();
	}

	public void updateChatMessage(String message) {
		view.updateChatMessage(message);
	}

	public void updateCommand(String text) {
		view.updateGameConsole(text);

	}

	public void disconnect() {
		JOptionPane
				.showMessageDialog(
						null,
						"The game admin is shutting down the server. \nWe apologize for the inconviencence");

		view.close();

	}

	public void updateScore(String score) {
		view.updateScoreConsole(score);
	}

	public void sendLookandScoreCommand() {
		try {
			out.writeObject(new ScoreCommand(username));
			out.writeObject(new InventoryCommand(username));
			Thread.sleep(200);
			view.clearConsole();
			out.writeObject(new RoomDescriptionCommand(username));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void updateInventory(String inventory) {
		view.updateInventoryConsole(inventory);
	}

	public void giveAcceptanceCommand(String itemName, String giversName) {
		int accepted = JOptionPane.showConfirmDialog(null, giversName
				+ " would like to give you a" + itemName + ". Do you accept?",
				"Item Acceptance", JOptionPane.YES_NO_OPTION);
		try {
			if (accepted == JOptionPane.YES_OPTION)

				out.writeObject(new AcceptGiveCommand(username, giversName,
						itemName, true));

			else
				out.writeObject(new AcceptGiveCommand(username, giversName,
						null, false));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void getAcceptanceCommand(String clientUserName,
			String recieverName, String itemName) {
		int accepted = JOptionPane.showConfirmDialog(null, recieverName
				+ " would like to take your " + itemName + ". Do you accept?",
				null, JOptionPane.YES_NO_OPTION);
		try {
			if (accepted == JOptionPane.YES_OPTION)

				out.writeObject(new AcceptGetCommand(username, recieverName,
						itemName, true));

			else
				out.writeObject(new AcceptGetCommand(username, recieverName,
						null, false));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	public LinkedList<String> getCommandList() {
		
		return enteredCommands;
	}
}
