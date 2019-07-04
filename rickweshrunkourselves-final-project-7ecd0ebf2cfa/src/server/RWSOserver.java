package server;

import game.Game;
import game.NPC;
import game.Player;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import commands.*;

/**
 * The Server class
 * 
 * @author Barney Stinson
 * 
 */
public class RWSOserver {

	private static final int PORT = 9001;
	private static final int ADMIN_PASSWORD = 857904985;
	private static final int MAX_ITEM_COUNT = 8;
	private ServerSocket socket; // the server socket
	private HashMap<String, ObjectOutputStream> outputs;
	private Game theGame;

	/**
	 * The constructor for the server
	 */
	public RWSOserver(Game game) {

		this.outputs = new HashMap<String, ObjectOutputStream>();
		this.theGame = game;

		try {
			// start a new server on port 9001
			socket = new ServerSocket(PORT);
			System.out.println("RWSO Server started on port " + PORT
					+ " and address " + socket.getInetAddress());

			new Thread(new ClientAccepter()).start();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ClientAccepter
	 * 
	 * This thread listens for and sets up connections to new clients
	 */
	private class ClientAccepter implements Runnable {

		public void run() {
			try {

				while (true) {

					// accept a new client, get output & input streams
					Socket s = socket.accept();
					ObjectOutputStream output = new ObjectOutputStream(
							s.getOutputStream());
					ObjectInputStream input = new ObjectInputStream(
							s.getInputStream());

					new Thread(new ClientLoginandRegister(s, input, output))
							.start();

				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * Logs a user in or registers them
	 * 
	 * @author Barney Stinson
	 * 
	 */
	private class ClientLoginandRegister implements Runnable {

		private Socket s;
		private ObjectInputStream input;
		private ObjectOutputStream output;

		public ClientLoginandRegister(Socket s, ObjectInputStream input,
				ObjectOutputStream output) {
			this.s = s;
			this.input = input;
			this.output = output;
		}

		@Override
		public void run() {

			try {

				boolean notloggedOn = true;
				do {

					Object command = input.readObject();
					
					while (command instanceof Command){
					
						return;
					}
					
					int commandNum = (int) command;
					
					switch (commandNum) {
					case 1:

						boolean avalibility = theGame
								.checkUserAvailability((String) input
										.readObject());
						output.writeObject(avalibility);
						break;

					case 2:

						String clientName = (String) input.readObject();
						String password = (String) input.readObject();
						theGame.registerUser(clientName, password);
						outputs.put(clientName, output);
						notloggedOn = false;
						output.writeObject(true);

						// spawn a thread to handle chat communication with this
						// client
						new Thread(new ClientHandler(input)).start();

						// Prints a message about a user connecting
						System.out.println(clientName + " has connected");

						break;

					case 3:

						clientName = (String) input.readObject();
						password = (String) input.readObject();

						if (theGame.validateUser(clientName, password)) {

							outputs.put(clientName, output);
							notloggedOn = false;
							output.writeObject(true);

							// spawn a thread to handle chat communication with
							// this
							// client
							new Thread(new ClientHandler(input)).start();

							// Prints a message about a user connecting
							System.out.println(clientName + " has connected");

						} else {
							output.writeObject(false);
						}
					default:
					}

				} while (notloggedOn);

			} catch (IOException | ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	/**
	 * ClientHandler
	 * 
	 * This thread reads and executes commands sent by a client
	 */
	private class ClientHandler implements Runnable {

		private ObjectInputStream input; // the input stream from the client

		public ClientHandler(ObjectInputStream input) {
			this.input = input;
		}

		public void run() {
			try {
				while (true) {
					// read a command from the client, execute on the server
					Command<RWSOserver> command = (Command<RWSOserver>) input
							.readObject();
					command.execute(RWSOserver.this);

					// terminate if client is disconnecting
					if (command instanceof DisconnectServerCommand) {
						input.close();
						return;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void addGlobalMessage(String message) {

		// make an UpdateClientCommmand, write to all connected users
		UpdateClientChat update = new UpdateClientChat(message);
		try {
			for (ObjectOutputStream out : outputs.values())
				out.writeObject(update);
		} catch (Exception e) {
			e.printStackTrace();

		}
	}
	
	public void playerWins(String userName){
		UpdateClientCommand update = new UpdateClientCommand(userName + " has successfully defeated the mouse and captured the growth potion! They have won the game!");
		
		try {
			for (ObjectOutputStream out : outputs.values())
				out.writeObject(update);
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	public void addLocalMessage(String message, String clientUsername) {
		Player current = theGame.getPlayer(clientUsername);
		UpdateClientChat update = new UpdateClientChat(message);
		ArrayList<Player> playersInRoom = theGame
				.getPlayersInSameRoomAs(current);

		try {

			if (playersInRoom.size() <= 1) {
				ObjectOutputStream out = outputs.get(clientUsername);
				out.writeObject(new UpdateClientChat(
						"There is no one in the room. Do you usually talk to yourself?"));
			}

			for (Player p : playersInRoom) {// for each player in the same room,
											// including client calling the
											// command
				String targetUsername = p.getName();// get user name
				ObjectOutputStream out = outputs.get(targetUsername);// access
																		// output
																		// stream
																		// for
																		// that
																		// client
				out.writeObject(update);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		;
	}

	public void addPersonalMessage(String message, String targetUser,
			String username) {
		try {
			if (!theGame.checkUserAvailability(targetUser)) {

				outputs.get(username).writeObject(
						new UpdateClientCommand(
								"There is no player by the name of '"
										+ targetUser + "'"));
			}

			else if (!outputs.containsKey(targetUser)) {
				outputs.get(username).writeObject(
						new UpdateClientCommand((targetUser)
								+ "is not currently logged on."));
			}

			else {
				outputs.get(targetUser).writeObject(
						new UpdateClientChat(message));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void giveItem(String itemName, String targetName,
			String clientUserName) {
		
		try {
		//Check if you have the item
		if (!theGame.userHasItem(clientUserName, itemName))
			outputs.get(clientUserName).writeObject(new UpdateClientCommand("You do not have a " + itemName + " to give."));
			
		//Check if the other user exists and logged on
		else if (!theGame.checkUserAvailability(targetName))
			outputs.get(clientUserName).writeObject(new UpdateClientCommand(targetName + " does not exist."));
		
		else if (!outputs.containsKey(targetName))
			outputs.get(clientUserName).writeObject(new UpdateClientCommand(targetName + " is not logged on."));	
		
		else {
			outputs.get(clientUserName).writeObject(new UpdateClientCommand("Asking " + targetName + " to accept. You can keep playing until he responds."));
			outputs.get(targetName).writeObject(new RecieveAcceptanceClientCommand(clientUserName, itemName));
		}
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void getItem(String itemName, String targetName,
			String clientUserName) {
	
		try {
		
			if (!theGame.checkUserAvailability(targetName))
				outputs.get(clientUserName).writeObject(new UpdateClientCommand(targetName + " does not exsits."));
		
			else if (!theGame.userHasItem(targetName, itemName))
			outputs.get(clientUserName).writeObject(new UpdateClientCommand(targetName + " does not have a " + itemName + " to give."));
		
			else {
				outputs.get(clientUserName).writeObject(new UpdateClientCommand("Asking " + targetName + " for thier " + itemName + ". You can keep playing untill he responds."));
				outputs.get(targetName).writeObject(new RecieveGiveAcceptanceClientCommand(targetName, clientUserName, itemName));
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void disconnect(String clientName) {
		try {
			theGame.disconnectUser(clientName);
			outputs.get(clientName).close(); // close output stream
			outputs.remove(clientName); // remove from map

			// add notification message
			System.out.println(clientName + " disconnected");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Shuts down the server and the game
	 * 
	 * @param password
	 */
	public void shutDown(String Userpassword) {

		if (theGame.shutDownServer(ADMIN_PASSWORD, Userpassword)) {

			for (ObjectOutputStream out : outputs.values())
				try {
					out.writeObject(new DisconnectClientCommand(null));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		System.exit(0);
	}

		
	/**
	 * Gets the information about the room currently in
	 * 
	 * @param theUser
	 */
	public void lookCommand(String theUser) {
		String discription = theGame.getSurroundings(theUser);
		try {
			outputs.get(theUser).writeObject(
					new UpdateClientCommand(discription));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void roomDescriptionCommand(String theUser){
		String discription = theGame.getRoomDescription(theUser);
		try {
			outputs.get(theUser).writeObject(
					new UpdateClientCommand(discription));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void whoCommand(String username) {
		String loggedinUsers = theGame.getLoggedInUsers(username);
		try {
			outputs.get(username).writeObject(
					new UpdateClientCommand(loggedinUsers));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		;
	}

	public void scoreCommand(String userName) {

		String score = theGame.getPlayer(userName).getStatus();
		try {
			outputs.get(userName).writeObject(
					new UpdateClientScoreCommand(score));
			outputs.get(userName).writeObject(
					new UpdateClientCommand("\nYour stats have changed!"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void dropCommand(String userName, String item) throws IOException {

		boolean drop = theGame.dropItem(userName, item);
		if (drop) {
			outputs.get(userName).writeObject(
					new UpdateClientCommand(item + " has been dropped."));
		} else
			outputs.get(userName).writeObject(
					new UpdateClientCommand("Cannot drop your " + item));

	}

	public void inventoryCommand(String username) {
		String inventory = theGame.getUserInventory(username);
		try {
			outputs.get(username).writeObject(
					new UpdateClientInventoryCommand(inventory));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void pickUpCommand(String userName, String item) throws IOException {
		boolean getItem = theGame.pickUpItem(userName, item);
		if (theGame.getPlayer(userName).getItemCount() == MAX_ITEM_COUNT) {
			outputs.get(userName)
					.writeObject(
							new UpdateClientCommand(
									"You are carrying too many items. You will have to drop one before you can pick anything else up"));
		}

		if (getItem) {
			outputs.get(userName)
					.writeObject(
							new UpdateClientCommand("You have picked up "
									+ item + "."));
			outputs.get(userName).writeObject(
					new UpdateClientCommand(
							"\nThe contents of your inventory have changed"));
		} else
			outputs.get(userName).writeObject(
					new UpdateClientCommand("You could not pick up " + item
							+ "."));

	}

	public void moveRightCommand(String userName) {
		// TODO Auto-generated method stub
		Player current = theGame.getPlayer(userName);
		boolean mayMove = false;
		int oldY = current.getYLocation();
		current.incrementYLocation(1);

		String discription = "You walk right into the next area:" + "\n" + theGame.getRoomDescription(userName);
		if (oldY == current.getYLocation())
			discription = "You can't go that way!";
		// need to remove player from old room list and add them to the new one
		else{
			if (isHiddenRoom(current.getXLocation(), current.getYLocation())){	
				mayMove = mayMoveIntoHiddenRoom(userName,current.getXLocation(), current.getYLocation());
				if (!mayMove){
					discription = "You can't access this area without special item(s).";
					current.incrementYLocation(-1);//resets Y value	
				}
							
				else{					
					theGame.removePlayerFromRoom(current, current.getXLocation(), oldY);
					theGame.addPlayerToRoom(current);
				}
			}					
			else {						
			theGame.removePlayerFromRoom(current, current.getXLocation(), oldY);
			theGame.addPlayerToRoom(current);
			}
		}		
		try {	
			outputs.get(userName).writeObject(
					new UpdateClientCommand(discription));
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void moveLeftCommand(String userName) {
		Player current = theGame.getPlayer(userName);
		boolean mayMove = false;
		int oldY = current.getYLocation();
		current.incrementYLocation(-1);

		String discription = "You walk left into the next area:" + "\n" + theGame.getRoomDescription(userName);
		if (oldY == current.getYLocation())
			discription = "You can't go that way!";
		// need to remove player from old room list and add them to the new one
		else{
			if (isHiddenRoom(current.getXLocation(), current.getYLocation())){	
				mayMove = mayMoveIntoHiddenRoom(userName,current.getXLocation(), current.getYLocation());
				if (!mayMove){
					discription = "You can't access this area without special item(s).";
					current.incrementYLocation(1);//resets Y value	
				}
							
				else{					
					theGame.removePlayerFromRoom(current, current.getXLocation(), oldY);
					theGame.addPlayerToRoom(current);
				}
			}					
			else {						
			theGame.removePlayerFromRoom(current, current.getXLocation(), oldY);
			theGame.addPlayerToRoom(current);
			}
		}		
		try {	
			outputs.get(userName).writeObject(
					new UpdateClientCommand(discription));
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void moveUpCommand(String userName) {
		Player current = theGame.getPlayer(userName);
		boolean mayMove = false;
		int oldX = current.getXLocation();
		current.incrementXLocation(-1);

		String discription = "You walk up into the next area:" + "\n" + theGame.getRoomDescription(userName);
		if (oldX == current.getXLocation())
			discription = "You can't go that way!";
		// need to remove player from old room list and add them to the new one
		else{
			if (isHiddenRoom(current.getXLocation(), current.getYLocation())){	
				mayMove = mayMoveIntoHiddenRoom(userName,current.getXLocation(), current.getYLocation());
				if (!mayMove){
					discription = "You can't access this area without special item(s).";
					current.incrementXLocation(1);//resets X value	
				}
							
				else{					
					theGame.removePlayerFromRoom(current, oldX, current.getYLocation());
					theGame.addPlayerToRoom(current);
				}
			}					
			else {						
			theGame.removePlayerFromRoom(current, oldX, current.getYLocation());
			theGame.addPlayerToRoom(current);
			}
		}
		try {	
			outputs.get(userName).writeObject(
					new UpdateClientCommand(discription));
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void moveDownCommand(String userName) {
		Player current = theGame.getPlayer(userName);
		boolean mayMove = false;
		int oldX = current.getXLocation();
		current.incrementXLocation(1);

		String discription = "You walk down into the next area:" + "\n" + theGame.getRoomDescription(userName);
		if (oldX == current.getXLocation())
			discription = "You can't go that way!";
		// need to remove player from old room list and add them to the new one
		else{
			if (isHiddenRoom(current.getXLocation(), current.getYLocation())){	
				mayMove = mayMoveIntoHiddenRoom(userName,current.getXLocation(), current.getYLocation());
				if (!mayMove){
					discription = "You can't access this area without special item(s).";
					current.incrementXLocation(-1);//resets X value	
				}
							
				else{					
					theGame.removePlayerFromRoom(current, oldX, current.getYLocation());
					theGame.addPlayerToRoom(current);
				}
			}					
			else {						
			theGame.removePlayerFromRoom(current, oldX, current.getYLocation());
			theGame.addPlayerToRoom(current);
			}
		}
		try {	
				outputs.get(userName).writeObject(
						new UpdateClientCommand(discription));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private boolean mayMoveIntoHiddenRoom(String username, int x, int y){
		if (x==4 && y==5 && theGame.userHasItem(username, "Cheese"))
			return true;		
		if (theGame.userHasItem(username, "dental floss") && theGame.getPlayer(username).hasItem("sticky tape"))
			return true;	
		return false;
	}
	private boolean isHiddenRoom(int x, int y){
		//kitchen counter top
		if (x == 1 && y == 0){
			return true;
		}
		//on the couch
		if (x == 3 && y == 0){
			return true;
		}
		//on coffee table
		if (x == 3 && y == 1){
			return true;
		}
//		
//		//mouse lair
		if (x == 4 && y == 5){
			return true;
		}
		return false;
	}

	public void characterEnteredRoom(String name,
			ArrayList<Player> playersInSameRoomAs) {
		for (Player p : playersInSameRoomAs) {
			ObjectOutputStream out = outputs.get(p.getName());
			try {
				out.writeObject(new UpdateClientCommand(name
						+ " has entered the room"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public void characterExitRoom(String name,
			ArrayList<Player> playersInSameRoomAs) {
		for (Player p : playersInSameRoomAs) {
			ObjectOutputStream out = outputs.get(p.getName());
			try {
				out.writeObject(new UpdateClientCommand(name
						+ " has exited the room"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	
	public void characterDropsItem(String name,
			ArrayList<Player> playersInSameRoomAs, String itemName) {
		for (Player p : playersInSameRoomAs) {
			ObjectOutputStream out = outputs.get(p.getName());
			try {
				out.writeObject(new UpdateClientCommand(name
						+ " has dropped " + itemName));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	
	public void characterPicksUpItem(String name, ArrayList<Player> playersInSameRoomAs, String itemName) {
		for (Player p : playersInSameRoomAs) {
			ObjectOutputStream out = outputs.get(p.getName());
			try {
				out.writeObject(new UpdateClientCommand(name
						+ " has picked up " + itemName));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	
	
	
	public void useCommand(String clientUserName, String item) {
		theGame.playerUsesItem(clientUserName, item);

	}
	public void sendNoItemCommand(String clientUserName, String item) {
		try {
			outputs.get(clientUserName).writeObject(
					new UpdateClientCommand("You do not have " + item));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public void sendNotUsableItemCommand(String clientUserName, String item) {
		try {
			outputs.get(clientUserName).writeObject(
					new UpdateClientCommand("You cannot use the " + item + " to increase your stats."));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void attack(String clientUserName, String targetNPC) {
	if (targetNPC.equals(" ") || targetNPC.equals("") || targetNPC == null){
			try {
				outputs.get(clientUserName).writeObject(
						new UpdateClientCommand(
								"Invalid attack command. Please specify target NPC using 'attack' + <target>"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}

		
		
		if (!theGame.NPCisInRoomWithPlayer(clientUserName, targetNPC)) {
			NPC npc = theGame.getNPC(targetNPC);
			try {
				outputs.get(clientUserName).writeObject(
						new UpdateClientCommand(
								"There is no NPC by the name of '"
										+ targetNPC + "' to attack"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}

		else {
			NPC npc = theGame.getNPC(targetNPC);
			boolean wonBattle = theGame.getPlayer(clientUserName).battle(npc);
			if (wonBattle) {
				try {
					outputs.get(clientUserName).writeObject(
							new UpdateClientCommand(
									"The "+targetNPC+" hits you for "+npc.getAttack()+" hit points and you block "+theGame.getPlayer(clientUserName).getDefense()+"!\nYou hit the "+targetNPC+" for "+theGame.getPlayer(clientUserName).getAttack()+" points of damage!"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else
				try {
					outputs.get(clientUserName).writeObject(
							new UpdateClientCommand(
									"You killed the "+targetNPC+" and have gained 1 skill point!"));
					theGame.removeDeadNPCFromRoom(npc, theGame.getPlayer(clientUserName).getXLocation(), theGame.getPlayer(clientUserName).getYLocation());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			scoreCommand(clientUserName);
		}

	}

	
	public void GiveCommandResponse(String targetName, String clientUserName, String itemName, boolean accepted) {
		try {
		if (!accepted)
				outputs.get(targetName).writeObject(new UpdateClientCommand(clientUserName + " has denied your give request. They must be loopy to not want free items."));
		
		else {
			
			theGame.transferItem(targetName, clientUserName, itemName);
			outputs.get(targetName).writeObject(new UpdateClientCommand(clientUserName + " has accepted your " + itemName + "."));
			this.inventoryCommand(targetName);
			this.inventoryCommand(clientUserName);
		}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
	public void acceptGetCommand(String clientUserName, String gettersName,
			String itemName, boolean accepted) {
		try {
			if (!accepted)
					outputs.get(gettersName).writeObject(new UpdateClientCommand(clientUserName + " has denied your get request for the " + itemName + "."));
			
			else {
				
				theGame.transferItem(clientUserName,gettersName, itemName);
				outputs.get(gettersName).writeObject(new UpdateClientCommand(clientUserName + " let you take their " + itemName + "."));
				outputs.get(clientUserName).writeObject(new UpdateClientCommand("You let " + gettersName + " take your " + itemName + "."));
				this.inventoryCommand(gettersName);
				this.inventoryCommand(clientUserName);
			}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
}
