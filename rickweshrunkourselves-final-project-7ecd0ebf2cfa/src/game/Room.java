package game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Room implements Serializable{


	
	private static final long serialVersionUID = 8672032913238947229L;
	private String name;
	private ArrayList<Player> playersInRoom;
	private ArrayList<NPC> NPCsInRoom;
	private ArrayList<Item> itemsInRoom;
	private int xCoord;
	private int yCoord;
	private String description;

	public Room(String name, int xCoord, int yCoord) {
		this.name = name;
		this.xCoord = xCoord;
		this.yCoord = yCoord;
		playersInRoom = new ArrayList<Player>();
		NPCsInRoom = new ArrayList<NPC>();
		itemsInRoom = new ArrayList<Item>();
		description = "";
	}

	public String getName() {
		return name;
	}

	public void addItem(Item i) {
		itemsInRoom.add(i);
	}

	public void addPlayer(Player p) {
		playersInRoom.add(p);
	}
	
	public void removePlayer(Player player) {
		playersInRoom.remove(player);
	}
	
	public ArrayList<Player> getPlayersInRoom(){
		return playersInRoom;
	}
	
	public void addNPC(NPC c) {
		NPCsInRoom.add(c);
	}
	
	public void removeNPC(NPC npc) {
		NPCsInRoom.remove(npc);
	}

	public String otherPlayersInRoom(Player current) {
		String list = "";
		for (Player p : playersInRoom){
				
			if (p.getName().equals(current.getName()))
				continue;
			
			list += p.getName() + ", ";
		
		}
		if (list.length() < 2){
			return "";
		}
		return list.substring(0, list.length()-2);		
	}

	public String npcInRoom() {
		String list = "";
		for (int i = 0; i < NPCsInRoom.size(); i++) {
			if (i == NPCsInRoom.size() - 1) {
				list += NPCsInRoom.get(i).getName();
			} else {
				list += NPCsInRoom.get(i).getName() + " and ";
			}
		}
		return list;
	}
	
	public List<NPC> npcsInRoomArray() {
	
		return NPCsInRoom;
	}

	public String itemsInRoom() {
		String list = "";
		for (int i = 0; i < itemsInRoom.size(); i++) {
			if (i == itemsInRoom.size() - 1) {
				list += "*" + itemsInRoom.get(i).getName()+ "*";
			} else {
				list += "*" + itemsInRoom.get(i).getName() + "* and ";
			}
		}
		return list;
	}
	public String getFullDescription(Player current){//gives entire description when you enter the room
		String roomDescription = description;
		String surroundings = getSurroundings(current);
		roomDescription += surroundings;
		return roomDescription;
	}

	public String getSurroundings(Player current) {//lists items, npcs, and other players			
		String surroundings = "";
		
		surroundings += " Looking around your immediate area, you notice "; 
		if (itemsInRoom.size() == 0)
			surroundings += "there are no items to be found";
		else if (itemsInRoom.size() == 1)
			surroundings += "a single " + itemsInRoom() + ".";
		else 
			surroundings += itemsInRoom() + " scattered about the area.";
				
		surroundings += "\n\nYou also check for other life in the room--hostile or not--and find ";
		
		if (playersInRoom.size() == 0 || otherPlayersInRoom(current) == "")

			surroundings += "there are no other players in this room.";	
		else 
			surroundings += "the other players in the room are: " + otherPlayersInRoom(current) + ".";	
		
		if (NPCsInRoom.size() == 0)
			surroundings += "";	
		else if (NPCsInRoom.size() == 1)
			surroundings += "\n\nThere is a " + npcInRoom() + " in here that wants to kill you!";
		else
			surroundings += "\n\nThe " + npcInRoom() + " in here want to kill you!";
		return surroundings + "\n";		
	}
	
	public void setRoomDescription(String d){
		description = d;		
	}
	
	public Item hasItem(String item) {
		
		for (Item roomitem : itemsInRoom){
			if (roomitem.getName().equals(item))
				return roomitem;
		}
		
		return null;
		
	}

	public Item getItem(Item roomItem) {

		int index = itemsInRoom.indexOf(roomItem);
		return itemsInRoom.remove(index);
	}
	
	

	public int getNumofPlayersInRoom() {
		return playersInRoom.size();
	}

	
	public boolean hasNPC(String targetNPC) {
		
		for ( NPC n : NPCsInRoom){
			if (n.getName().equals(targetNPC))
				return true;
		}
		
		return false;
	}	
}
