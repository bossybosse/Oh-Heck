package game;

import java.io.*;
import java.util.*;
import java.util.Map.Entry;

import ItemsList.*;
import NPCList.*;
import server.RWSOserver;

import java.util.Timer;
import java.util.TimerTask;

public class Game implements Serializable {
	private static final int ADMIN_PASSWORD = 857904985;
	private static final String SHUTDOWN_PASSWORD = "please";
	private static final String saveFile = "RWSOgame.sav";
	public static final int DELAY = 30;
	private Timer timer;
	private Room[][] map;
	private RWSOserver server;
	private HashMap<String, Player> registeredUser;
	private HashMap<String, Player> playersLoggedOn;
	private ArrayList<NPC> npcList;

	public static void main(String args[]) {
		Game theGame = new Game();
	}

	public Game() {

		server = new RWSOserver(this);
		playersLoggedOn = new HashMap<String, Player>();
		npcList = new ArrayList<NPC>();

		try {
			loadGameData();
		} catch (ClassNotFoundException | IOException e) {
			registeredUser = new HashMap<String, Player>();
			map = new Room[5][6];
			generateMap();
			generateNPC();
			generateItems();
		}

		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				actionListener();
			}
		}, 1000, 60000);

	}

	public void actionListener() {
		Random rnd = new Random();
		int random;

		for (NPC npc : npcList) {
			random = rnd.nextInt(4);
			int oldX = npc.getXLocation();
			int oldY = npc.getYLocation();

			npc.move(random);

			int newX = npc.getXLocation();
			int newY = npc.getYLocation();

			if (oldX != newX || oldY != newY) {
				removeNPCFromRoom(npc, oldX, oldY);
				addNPCToRoom(npc);
			}
		}
	}

	public ArrayList<NPC> getNPCList() {
		return npcList;
	}

	private ArrayList<Player> getPlayersInSameRoomAsNPC(NPC n) {

		int x = n.getXLocation();
		int y = n.getYLocation();
		return map[x][y].getPlayersInRoom();

	}

	public void loadGameData() throws IOException, ClassNotFoundException {
		FileInputStream filein = new FileInputStream(saveFile);
		ObjectInputStream objectin = new ObjectInputStream(filein);
		map = (Room[][]) objectin.readObject();
		registeredUser = (HashMap<String, Player>) objectin.readObject();
		npcList = (ArrayList<NPC>) objectin.readObject();
		objectin.close();

	}

	public boolean checkUserAvailability(String userName) {

		if (registeredUser.containsKey(userName))
			return true;

		return false;
	}

	public void registerUser(String userName, String password) {
		Player aPlayer = new Player(userName, password);
		registeredUser.put(userName, aPlayer);
		playersLoggedOn.put(userName, aPlayer);
		addPlayerToRoom(aPlayer);

	}

	public boolean validateUser(String userName, String password) {

		// then check to see if username/passcode combo exists

		if (registeredUser.containsKey(userName)
				&& !playersLoggedOn.containsKey(userName)) {
			Player playerAccount = registeredUser.get(userName);

			if (playerAccount.retrievePasscode(ADMIN_PASSWORD).equals(password)) {
				playersLoggedOn.put(userName, playerAccount);
				int x = playerAccount.getXLocation();
				int y = playerAccount.getYLocation();
				addPlayerToRoom(playerAccount);

				return true;
			}

			return false;
		}

		return false;
	}

	public Room getRoom(int xLocation, int yLocation) {
		return map[xLocation][yLocation];
	}

	private void generateNPC() {
		Random rand1 = new Random();
		Random rand2 = new Random();
		int randomX = 0;
		int randomY = 0;
		// generate 5 ant in map
		for (int i = 0; i < 5; i++) {
			randomX = rand2.nextInt(5);
			randomY = rand1.nextInt(4);
			NPC npc = new Ant("Ant " + i, 5, 1, randomX, randomY);
			npcList.add(npc);
			map[randomX][randomY].addNPC(npc);
		}
		// generate 3 cockroach in map
		for (int i = 0; i < 3; i++) {
			randomX = rand2.nextInt(5);
			randomY = rand1.nextInt(4);
			NPC npc = new Cockroach("Cockroach " + i, 15, 3, randomX, randomY);
			npcList.add(npc);
			map[randomX][randomY].addNPC(npc);
		}
		// generate 3 bedbug in map
		for (int i = 0; i < 3; i++) {
			randomX = rand2.nextInt(5);
			randomY = rand1.nextInt(4);
			NPC npc = new Bedbug("Bedbug " + i, 6, 2, randomX, randomY);
			npcList.add(npc);
			map[randomX][randomY].addNPC(npc);
		}
		// generate 3 fly in map
		for (int i = 0; i < 3; i++) {
			randomX = rand2.nextInt(5);
			randomY = rand1.nextInt(4);
			NPC npc = new Fly("Fly " + i, 9, 2, randomX, randomY);
			npcList.add(npc);
			map[randomX][randomY].addNPC(npc);
		}
		// generate 3 spider in map
		for (int i = 0; i < 3; i++) {
			randomX = rand2.nextInt(5);
			randomY = rand1.nextInt(4);
			NPC npc = new Spider("Spider " + i, 20, 4, randomX, randomY);
			npcList.add(npc);
			map[randomX][randomY].addNPC(npc);
		}
		// generate 1 ladybug in map
		for (int i = 0; i < 1; i++) {
			randomX = rand2.nextInt(5);
			randomY = rand1.nextInt(4);
			NPC npc = new Ladybug("Ladybug " + i, 20, 1, randomX, randomY);
			npcList.add(npc);
			map[randomX][randomY].addNPC(npc);
		}
		// generate 1 mouse in map
		for (int i = 0; i < 1; i++) {
			NPC npc = new Mouse("Mouse", 50, 10, 4, 5);
			npcList.add(npc);
			map[4][5].addNPC(npc);
		}
		// generate 1 dragonfly
		for (int i = 0; i < 1; i++) {
			randomX = rand2.nextInt(5);
			randomY = rand1.nextInt(4);
			NPC npc = new DragonFly("DragonFly " + i, 12, 3, randomX, randomY);
			npcList.add(npc);
			map[randomX][randomY].addNPC(npc);
		}
		// generate 2 scorpion
		for (int i = 0; i < 2; i++) {
			randomX = rand2.nextInt(5);
			randomY = rand1.nextInt(4);
			NPC npc = new Scorpion("Scorpion " + i, 30, 6, randomX, randomY);
			npcList.add(npc);
			map[randomX][randomY].addNPC(npc);
		}
		// generate 3 wasp
		for (int i = 0; i < 3; i++) {
			randomX = rand2.nextInt(5);
			randomY = rand1.nextInt(4);
			NPC npc = new Wasp("Wasp " + i, 20, 5, randomX, randomY);
			npcList.add(npc);
			map[randomX][randomY].addNPC(npc);
		}

	}

	private void generateItems() {
		Random rand1 = new Random();
		Random rand2 = new Random();
		int randomX = 0;
		int randomY = 0;
		// generate 2 acorn helmets in map
		for (int i = 0; i < 2; i++) {
			randomX = rand2.nextInt(5);
			randomY = rand1.nextInt(4);
			map[randomX][randomY].addItem(new D_AcornHelmet("Acorn Helmet", 50,
					1, randomX, randomY));
		}
		// generate 10 dental floss
		for (int i = 0; i < 10; i++) {
			randomX = rand2.nextInt(5);
			randomY = rand1.nextInt(4);
			map[randomX][randomY].addItem(new E_DentalFloss("Dental Floss", 0,
					1, randomX, randomY));
		}
		// generate 10 Sticky Tape
		for (int i = 0; i < 10; i++) {
			randomX = rand2.nextInt(5);
			randomY = rand1.nextInt(4);
			map[randomX][randomY].addItem(new E_StickyTape("Sticky Tape", 0, 1,
					randomX, randomY));
		}
		// generate 1 Cheese
		for (int i = 0; i < 1; i++) {
			randomX = rand2.nextInt(4);
			randomY = rand1.nextInt(4);
			map[randomX][randomY].addItem(new E_Cheese("Cheese", 0, 1, randomX,
					randomY));
		}
		// generate 1 Growth Potion
		for (int i = 0; i < 1; i++) {
			map[4][5].addItem(new E_GrowthPotion("Growth Potion", 0, 1, 4, 5));
		}
		// generate 5 rubberband shields on map
		for (int i = 0; i < 5; i++) {
			randomX = rand2.nextInt(5);
			randomY = rand1.nextInt(4);
			map[randomX][randomY].addItem(new D_Rubberband("Rubberband Shield",
					50, 1, randomX, randomY));
		}
		// generate 5 marshmallows on map
		for (int i = 0; i < 5; i++) {
			randomX = rand2.nextInt(5);
			randomY = rand1.nextInt(4);
			map[randomX][randomY].addItem(new O_Marshmallow("Marshmallow", 50,
					1, randomX, randomY));
		}
		// generate 4 pebbles on map
		for (int i = 0; i < 4; i++) {
			randomX = rand2.nextInt(5);
			randomY = rand1.nextInt(4);
			map[randomX][randomY].addItem(new O_Pebble("Pebble", 50, 1,
					randomX, randomY));

		}
		// generate 3 toothpicks
		for (int i = 0; i < 3; i++) {
			randomX = rand2.nextInt(5);
			randomY = rand1.nextInt(4);
			map[randomX][randomY].addItem(new O_Toothpick("Toothpick", 50, 1,
					randomX, randomY));
		}
		// generate 2 thumbtacks
		for (int i = 0; i < 2; i++) {
			randomX = rand2.nextInt(5);
			randomY = rand1.nextInt(4);
			map[randomX][randomY].addItem(new O_Thumbtack("Thumbtack", 50, 1,
					randomX, randomY));
		}
		// generate 1 needle
		for (int i = 0; i < 1; i++) {
			randomX = rand2.nextInt(5);
			randomY = rand1.nextInt(4);
			map[randomX][randomY].addItem(new O_Needle("Needle", 50, 1,
					randomX, randomY));
		}
		// generate 5 water droplets
		for (int i = 0; i < 5; i++) {
			randomX = rand2.nextInt(5);
			randomY = rand1.nextInt(4);
			map[randomX][randomY].addItem(new H_WaterDroplet("Water Droplet",
					50, 1, randomX, randomY));
		}
		// generate 4 Rice
		for (int i = 0; i < 4; i++) {
			randomX = rand2.nextInt(5);
			randomY = rand1.nextInt(4);
			map[randomX][randomY].addItem(new H_Rice("Rice", 50, 1, randomX,
					randomY));
		}
		// generate 3 Beans
		for (int i = 0; i < 3; i++) {
			randomX = rand2.nextInt(5);
			randomY = rand1.nextInt(4);
			map[randomX][randomY].addItem(new H_Bean("Bean", 50, 1, randomX,
					randomY));
		}
		// generate 2 Cookie Crumb
		for (int i = 0; i < 2; i++) {
			randomX = rand2.nextInt(5);
			randomY = rand1.nextInt(4);
			map[randomX][randomY].addItem(new H_CookieCrumb("Cookie Crumb", 50,
					1, randomX, randomY));
		}
	}

	private void generateMap() {
		map[0][0] = new Room("KITCHEN FLOOR", 0, 0);
		map[0][1] = new Room("PANTRY", 0, 1);
		map[0][2] = new Room("SMALL HALLWAY", 0, 2);
		map[0][3] = new Room("BATHROOM PUDDLE", 0, 3);
		map[0][4] = new Room("DOG DISHES", 0, 4);
		map[0][5] = new Room("LAUNDRY ROOM FLOOR", 0, 5);

		map[1][0] = new Room("UNDER KITCHEN SINK", 1, 0);
		map[1][1] = new Room("BEHIND THE FRIDGE", 1, 1);
		map[1][2] = new Room("BEDROOM RUG", 1, 2);
		map[1][3] = new Room("SHOWER", 1, 3);
		map[1][4] = new Room("BATHROOM CABINET", 1, 4);
		map[1][5] = new Room("BEHIND THE DRYER", 1, 5);

		map[2][0] = new Room("KITCHEN COUNTER", 2, 0);
		map[2][1] = new Room("MEDIUM HALLWAY", 2, 1);
		map[2][2] = new Room("UNDER THE BED", 2, 2);
		map[2][3] = new Room("ON TOP OF THE TOILET", 2, 3);
		map[2][4] = new Room("NEAR THE WINDOW", 2, 4);
		map[2][5] = new Room("LONG HALLWAY", 2, 5);

		map[3][0] = new Room("TOP OF THE COUCH", 3, 0);
		map[3][1] = new Room("TOP OF COFFEE TABLE", 3, 1);
		map[3][2] = new Room("BEDROOM CLOSET", 3, 2);
		map[3][3] = new Room("NEAR THE TOILET", 3, 3);
		map[3][4] = new Room("TOP OF THE GUEST BED", 3, 4);
		map[3][5] = new Room("GUEST CLOSET", 3, 5);

		map[4][0] = new Room("UNDER THE COUCH", 4, 0);
		map[4][1] = new Room("UNDER THE COFFEE TABLE", 4, 1);
		map[4][2] = new Room("HALL CLOSET", 4, 2);
		map[4][3] = new Room("FRONT DOOR", 4, 3);
		map[4][4] = new Room("GUEST BEDROOM FLOOR", 4, 4);
		map[4][5] = new Room("MOUSE'S LAIR", 4, 5);
		setRoomDescriptions();
	}

	private void setRoomDescriptions() {
		map[0][0]
				.setRoomDescription("\nYou are in the middle of the "
						+ map[0][0].getName()
						+ ". Everything looms over you and a mix of different food smells hang in the air along with the stink from the trash can. Out in the open you feel a bit vulnerable. Going DOWN to the bottom end of the room there is a sink with cabinets; one of them is ajar. At the far RIGHT side of the room is a pantry.");
		map[0][1]
				.setRoomDescription("\nYou squeeze under the door and enter the "
						+ map[0][1].getName()
						+ ". Now that you are tiny, all of the food is way to high to reach. It won't do you much good. Walking DOWN would lead to the fridge. To your LEFT is the open expanse of kitchen floor where you started, and to your RIGHT is a small hallway.");
		map[0][2]
				.setRoomDescription("\nYou enter a "
						+ map[0][2].getName()
						+ ". There is not much to see here. Going LEFT leads to the kitchen pantry, and going DOWN through the doorway leads to a bedroom. To your RIGHT you see the entrance to a bathroom.");
		map[0][3]
				.setRoomDescription("\nYou are by the "
						+ map[0][3].getName()
						+ ". Someone has spilled water and the floor is slippery, be careful! Going DOWN leads to the shower (maybe the water came from here). To your LEFT is the doorway leading to the small hallway; to your RIGHT are what look to be dog dishes sitting on the floor.");
		map[0][4]
				.setRoomDescription("\nYou are now standing by some "
						+ map[0][4].getName()
						+ ". You look around nervously, hoping the dog doesn't suddenly show up. Going DOWN or LEFT leads to the bathroom. Going RIGHT leads to a laundry room.");
		map[0][5]
				.setRoomDescription("\nYou are standing in the "
						+ map[0][5].getName()
						+ ". The washing machine is running, and the whirring sound it makes as it washes the clothes is an almost unbearably loud roar. You probably don't want to stay in here long. You might go deaf. To your LEFT are some dog dishes; thankfully you don't see a dog anywhere. You can also walk DOWN to go behind the dryer.");

		map[1][0]
				.setRoomDescription("\nYou now are on top of the "
						+ map[2][0].getName()
						+ ". Man, that was tiring! But now you can survey the whole kitchen from here. Whoever lives here isn't very neat. There are bits of food all over the floor. Walking UP leads to a metal pole you can slide on to get to the kitchen floor. Going DOWN leads to under the sink. Walking RIGHT leads to the behind the fridge.");
		map[1][1]
				.setRoomDescription("\nYou walk around and end up "
						+ map[1][1].getName()
						+ ". Nothing of interest is back here--just some dust bunnies and old, dried up food. Going LEFT leads to the kitchen sink, UP leads to the pantry, DOWN leads to a medium hallway, and going RIGHT through a doorway leads to a bedroom. Yeah, that's a weird place for a bedroom, I know.");
		map[1][2]
				.setRoomDescription("\nYou walk through the door to stand on the "
						+ map[1][2].getName()
						+ ". It is all soft and shaggy, a lovely shade of eggplant which coordinates nicely with the maple wood floor. You realize you've become distracted by aethestics and snap back to situation. Moving UP through the doorway leads to a small hallway, LEFT goes back to the kitchen, RIGHT goes to a bathroom, and DOWN leads to under the bed.");
		map[1][3]
				.setRoomDescription("\nYou climb into the "
						+ map[1][3].getName()
						+ ". The water is not running right now but it is slippery in here. Luckily you haven't shruck enough to go down the drain. That would be way too much of an adventure! Going LEFT leads to the bedroom; UP leads to the puddle in the floor; RIGHT goes to the bathroom cabinet. If you go DOWN out of the shower you can climb on top of the toilet.");
		map[1][4]
				.setRoomDescription("\nYou enter the "
						+ map[1][4].getName()
						+ ". Leaky bottles of soap and shampoo balance haphazzardly and there is spilled toothpaste in the corner. Don't step in it or you'll have really sticky feet...");
		map[1][5]
				.setRoomDescription("\nYou walk around and slip "
						+ map[1][5].getName()
						+ ". Standing back here blocks some of the noise from the washing machine. Nothing much back here but some giant lint balls. Walking UP leads back out into the laundry room, LEFT goes to the bathroom, and DOWN leads to a long hallway.");

		map[2][0]
				.setRoomDescription("\nYou squeeze through the partially opened cabinet door to get to "
						+ map[1][0].getName()
						+ ". It is damp and smells like mildew under here. The pipe drips in a slow *plink* every other second. Go UP to climb on the counter top. Going RIGHT leads to a medium hallway. Going DOWN leads to the family room near the couch.");
		map[2][1]
				.setRoomDescription("\nYou enter a "
						+ map[2][1].getName()
						+ ". Nothing much to see. Left and up go back to the kitchen, right goes to the bedroom, and down to the family room.");
		map[2][2]
				.setRoomDescription("\nYou walk "
						+ map[2][2].getName()
						+ ". You freeze in terror as you realize a cat is under here sleeping! Better get out of here before he wakes up and decides you would make a tasty snack. Move UP to go to the bedroom rug, DOWN to go to the closet, LEFT to go to the hallway, and RIGHT to go to the bathroom.");
		map[2][3]
				.setRoomDescription("\nYou climb up the toilet paper and end up "
						+ map[2][3].getName()
						+ ". Now that you are up here you seriously doubt your judgment...what was the point of climbing up here? Pretty unhygienic and nothing up here but the danger of falling into the toilet bowl. UP leads to the shower, DOWN goes back to the floor, LEFT goes to the bedroom and RIGHT goes to a window.");
		map[2][4]
				.setRoomDescription("\nYou now "
						+ map[2][4].getName()
						+ ". It is raining outside and water streaks down the glass. Move UP or LEFT to go to the bathroom, RIGHT to go to a long hallway, or DOWN to go to the guest bedrooom.");
		map[2][5]
				.setRoomDescription("\nYou are in a "
						+ map[2][5].getName()
						+ ". There are many rooms all way down  until the far end but it is way too long to walk. Probably just more bedrooms and bathrooms anyway. The doors closest to you lead UP to the laundry room or DOWN to the guest bedroom. LEFT leads to a window.");

		map[3][0]
				.setRoomDescription("\nYou are now "
						+ map[3][0].getName()
						+ ". The couch feels bouncy when you walk. There is a remote on the cushion. Now is not the time for watching TV though. Move UP to go to the kitchen and climb on the counter, move DOWN to get back off the couch. Move right to jump to the coffee table.");
		map[3][1]
				.setRoomDescription("\nYou are "
						+ map[3][1].getName()
						+ ". It's a glass table so it is weird to walk on while looking at the floor so far away and directly below you. Move LEFT to jump onto the couch, DOWN to climb off of the table, UP to go to the hallway, or RIGHT to go to the bedroom.");
		map[3][2]
				.setRoomDescription("\nYou are in the "
						+ map[3][2].getName()
						+ ". Lots of clothes hang above you and there are smelly shoes lined up on the floor. Move UP to go under the bed, LEFT to go to the family room, DOWN to go to the hall closet, and RIGHT to go to the bathroom.");
		map[3][3]
				.setRoomDescription("\nYou walk over and stand "
						+ map[3][3].getName()
						+ ". Not much to say. It's a toilet. You can use the toilet paper trailing on the floor to climb UP on top if you want. Go LEFT to bedroom, RIGHT to guest bedroom or DOWN to the front door.");
		map[3][4]
				.setRoomDescription("\nYou climb up the quilt to get on the "
						+ map[3][4].getName()
						+ ". The bed is really squishy and soft; too bad you can't take a nap right now. Go UP to the window, DOWN to the floor, LEFT to the bathroom, or RIGHT to the closet.");
		map[3][5]
				.setRoomDescription("\nYou walk into the "
						+ map[3][5].getName()
						+ ". It's mostly being used for storage of boxes and empty suitcases. Move UP to the long hallway, LEFT to the bed, or DOWN to investigate a mouse hole in the wall.");

		map[4][0]
				.setRoomDescription("\nYou are "
						+ map[4][0].getName()
						+ ". Lots of dust and little things have collected under here. You sneeze. Move UP to climb on top of the couch or RIGHT to move under the coffee table.");
		map[4][1]
				.setRoomDescription("\nYou are now "
						+ map[4][1].getName()
						+ ". It's a glass coffee table and looking up through the glass at the things sitting on it makes you feel like Alice when she was shrunk in Wonderland. Move UP to climb on top of the coffee table, LEFT to go under the couch, or RIGHT to go to the hall closet.");
		map[4][2]
				.setRoomDescription("\nYou are in the "
						+ map[4][2].getName()
						+ ". There are coats hanging and a shelf with some good boardgames on it. Move LEFT to the family room, UP to the bedroom, or RIGHT to the front door.");
		map[4][3]
				.setRoomDescription("\nYou are standing by the "
						+ map[4][3].getName()
						+ ". There is stained glass panel in the door and it throws a rainbow onto the floor as light streams in. There is no way for you to get the door open, and even if you did, you wouldn't want to go outside when you are this tiny anyway. Go LEFT to the hall closet, UP to the bathroom, or RIGHT to the guest bedroom.");
		map[4][4]
				.setRoomDescription("\nYou are in the middle of the "
						+ map[4][4].getName()
						+ ". It is pretty tidy in here and the bed has a long quilt which nearly touches the floor. You can go UP to climb on the bed via the quilt, LEFT to go to the front door, or RIGHT to go investigate a mouse hole in the wall.");
		map[4][5]
				.setRoomDescription("\nYou are finally in the "
						+ map[4][5].getName()
						+ ". You will need to battle the mouse to get the growth potion you need to return to normal. If you need to build up your stats or collect more item, move UP or LEFT to leave the lair.");

	}

	public void disconnectUser(String clientName) {

		Player player = playersLoggedOn.get(clientName);
		removePlayerFromRoom(player);
		playersLoggedOn.remove(clientName);

	}

	public void removePlayerFromRoom(Player p) {
		int x = p.getXLocation();
		int y = p.getYLocation();
		map[x][y].removePlayer(p);

		if (map[x][y].getNumofPlayersInRoom() >= 1)
			server.characterExitRoom(p.getName(), map[x][y].getPlayersInRoom());
	}

	public void removePlayerFromRoom(Player p, int x, int y) {
		map[x][y].removePlayer(p);
		if (map[x][y].getNumofPlayersInRoom() >= 1)
			server.characterExitRoom(p.getName(), map[x][y].getPlayersInRoom());
	}

	public void removeNPCFromRoom(NPC n, int x, int y) {

		map[x][y].removeNPC(n);

		if (map[x][y].getNumofPlayersInRoom() >= 1)
			server.characterExitRoom(n.getName(), map[x][y].getPlayersInRoom());
	}

	public void removeDeadNPCFromRoom(NPC n, int x, int y) {
		map[x][y].removeNPC(n);
	}

	public void addPlayerToRoom(Player p) {
		int x = p.getXLocation();
		int y = p.getYLocation();

		if (map[x][y].getNumofPlayersInRoom() >= 1)
			server.characterEnteredRoom(p.getName(),
					map[x][y].getPlayersInRoom());

		map[x][y].addPlayer(p);
	}

	public void addNPCToRoom(NPC n) {
		int x = n.getXLocation();
		int y = n.getYLocation();

		if (map[x][y].getNumofPlayersInRoom() >= 1)
			server.characterEnteredRoom(n.getName(),
					map[x][y].getPlayersInRoom());

		map[x][y].addNPC(n);
	}

	public Player getPlayer(String userName) {

		return playersLoggedOn.get(userName);
	}

	public boolean shutDownServer(int adminPassword, String userpassword) {

		if (adminPassword == ADMIN_PASSWORD) {
			if (userpassword.equals(SHUTDOWN_PASSWORD)) {

				for (Player p : playersLoggedOn.values()) {
					removePlayerFromRoom(p);
				}

				try {
					ObjectOutputStream fileSave = new ObjectOutputStream(
							new FileOutputStream(new File(saveFile)));
					fileSave.writeObject(map);
					fileSave.writeObject(registeredUser);
					fileSave.writeObject(npcList);
					fileSave.close();

					return true;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return false;
	}

	public String getLoggedInUsers(String username) {
		String temp = "";
		if (playersLoggedOn.size() == 1) {
			temp += "You are the only user logged on";
		} else {
			temp += "Players currently logged on:";

			Iterator<Entry<String, Player>> iterator = playersLoggedOn
					.entrySet().iterator();

			while (iterator.hasNext()) {
				Entry<String, Player> playerEntry = iterator.next();
				String name = playerEntry.getKey();
				if (!name.equals(username))
					temp += " " + name;
			}

		}
		return temp;
	}

	public boolean dropItem(String userName, String item) {

		Player player = playersLoggedOn.get(userName);
		if (player.hasItem(item)) {
			Item theItem = player.getItem(item);
			int x = player.getXLocation();
			int y = player.getYLocation();
			map[x][y].addItem(theItem);
			server.characterDropsItem(userName, map[x][y].getPlayersInRoom(),
					item);
			return true;
		}
		return false;
	}

	public String getSurroundings(String theUser) {
		Player player = playersLoggedOn.get(theUser);
		int x = player.getXLocation();
		int y = player.getYLocation();
		return map[x][y].getSurroundings(player);
	}

	public String getRoomDescription(String theUser) {
		Player player = playersLoggedOn.get(theUser);
		int x = player.getXLocation();
		int y = player.getYLocation();
		return map[x][y].getFullDescription(player);
	}

	public String getUserInventory(String username) {
		return playersLoggedOn.get(username).getItemsList();

	}

	public boolean pickUpItem(String userName, String item) {
		Player player = playersLoggedOn.get(userName);
		int x = player.getXLocation();
		int y = player.getYLocation();

		Item roomItem = map[x][y].hasItem(item);
		if (roomItem != null) {
			if (item.equals("Growth Potion") && !getNPC("Mouse").isDead())
				return false;						
			player.pickUpItem(map[x][y].getItem(roomItem));
			server.characterPicksUpItem(userName, map[x][y].getPlayersInRoom(),
					item);
			return true;
		}

		return false;
	}

	public ArrayList<Player> getPlayersInSameRoomAs(Player current) {
		int x = current.getXLocation();
		int y = current.getYLocation();
		return map[x][y].getPlayersInRoom();
	}

	public void playerUsesItem(String clientUserName, String item) {
		Player player = getPlayer(clientUserName);
		if (!player.hasItem(item)) {
			server.sendNoItemCommand(clientUserName, item);
		}
		if (item.equals("Cheese") || item.equals("dental floss")|| item.equals("sticky tape"))
			server.sendNotUsableItemCommand(clientUserName, item);
		if (item.equals("Growth Potion"))
			server.playerWins(clientUserName);
		else {
			player.useItem(item);
			server.scoreCommand(clientUserName);
		}

	}

	public boolean battle(String clientUserName, String targetNPC) {

		Player p = getPlayer(clientUserName);
		int x = p.getXLocation();
		int y = p.getYLocation();
		NPC npc = null;

		for (NPC n : npcList) {
			if (n.getName() == targetNPC)
				npc = n;
		}

		return p.battle(npc);
	}

	public void transferItem(String giversName, String acceptersName,
			String itemName) {
		Item item = playersLoggedOn.get(giversName).getItem(itemName);
		playersLoggedOn.get(acceptersName).pickUpItem(item);

	}

	public boolean userHasItem(String playerName, String itemName) {
		if (playersLoggedOn.get(playerName).hasItem(itemName))
			return true;

		return false;
	}

	public boolean NPCisInRoomWithPlayer(String clientUserName, String targetNPC) {
		
		Player p = playersLoggedOn.get(clientUserName);
		int x = p.getXLocation();
		int y = p.getYLocation();
		
		if (map[x][y].hasNPC(targetNPC))
			return true;

		return false;
	}

	public NPC getNPC(String targetNPC) {
		
		for (NPC n : npcList){
			if (n.getName().equals(targetNPC))
				return n;
		}
		return null;
	}

}
