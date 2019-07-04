package game;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;


public class Player extends Character implements Serializable{

	
	private static final long serialVersionUID = -6631177862059410824L;
	private static final int ADMIN_PASSWORD = 857904985;
	private String password;	
	private HashMap<String, ArrayList<Item>> allItems;
	private ArrayList<String> itemDirectory;
	private int itemCount;
	private int skill;
	private final static int INITIAL_HP = 100;
	private final static int INITIAL_ATTACK_SKILL = 1;
	private final static int INITIAL_DEFENSE = 0;
	

	// attack skill is the number you multiply weapon attack value by to get
	// total damage. Ex: player with attack skill 3 using a toothpick with
	// damage level 2 would give 6 points total damage

	public Player(String playerName, String password) {
		super(playerName,INITIAL_HP, INITIAL_ATTACK_SKILL, INITIAL_DEFENSE, 0, 0);		
		this.password = password;	
		skill = 0;
		itemCount = 0;
		itemDirectory = new ArrayList<String>();
		allItems = new HashMap<String, ArrayList<Item>>();		
	}

	/**
	 * Retrieves the password for validation
	 * @param adminCredentials
	 * @return -1 for not valid or the actual integer value of the password
	 */
	public String retrievePasscode(int adminCredentials) {
		if (adminCredentials == ADMIN_PASSWORD)
			return password;
		else return "";		
	}		
	
	public HashMap<String, ArrayList<Item>> getItems() {
		return allItems;
	}
	public int getItemCount(){
		return itemCount;
	}

	public void pickUpItem(Item item) {
		
		if (!itemDirectory.contains(item.getName()))
			itemDirectory.add(item.getName());	
		
		if( allItems.containsKey(item.getName())){
			allItems.get(item.getName()).add(item);
		}		
		else {
			ArrayList<Item> list = new ArrayList<Item>();
			list.add(item);
			allItems.put(item.getName(), list);
		}	
		itemCount++;
	}
	
//	public void walk(int xCoordinate, int yCoordinate){
//		super.incrementYLocation(yCoordinate);
//		super.incrementXLocation(xCoordinate);
//	}
	public String getStatus() {
		return "" + super.getName() + "'s Stats: " + "\n Attack Power: " + super.getAttack()
				+ "    Defense Value: " + getDefense() + "    HP: " + super.getHp() + "" + "	Level: " + skill;	
	}
	
	public String getItemsList() {
		//this should list each item name and then how many of that item there are. For example:
		//Items:
		//Cookie Crumb: 2
		//Toothpick: 1
		//Acorn Hat: 1
		if (itemDirectory.isEmpty())
			return "You have no items";
					
		String text = "In your pouch you have: \n";
		for (String s: itemDirectory){
			text += s + ": " + allItems.get(s).size() + "\n";
		}		
		return text;
	}

	
	public boolean hasItem(String item) {
		
		if (allItems.containsKey(item))
			return true;
		
		return false;
	}

	public Item getItem(String item) {
		
		ArrayList<Item> entry = allItems.remove(item);		
		
		if (entry.size()== 1){
			itemDirectory.remove(item);
			itemCount--;
			Item target = entry.get(0);
			entry.remove(item);
			return target;			
		}		
		else {
			Item theItem = entry.remove(entry.size()-1);
			itemCount--;
			allItems.put(item, entry);				
			return theItem;			
		}	//more than one of that item, you don't mess with the directory
	}

	public void useItem(String item) {
		Item temp = this.getItem(item);
		this.changeAttack(temp.attack);
		this.gainHp(temp.hpBoost);
		this.changeDefense(temp.defense);		
	}

	private void changeDefense(int amt) {
		if (amt != 0)
			defense = amt;//only can equip one defense item at a time		
	}
	public void changeAttack(int amt) {
		if (amt != 0)
			attack = amt; //can only equip one attack item at a time
	}

	public boolean battle(NPC npc) {
		int npcAtt = npc.getAttack();
		
		if(npcAtt>defense)
			this.loseHp(npcAtt-defense);
		npc.gainHp(-attack);
		if(npc.isDead()){
			skill+=1;
			attack+=1;
			defense+=1;
			hp+=10;
			return false;
		}
		if(hp<=0){
			if(skill>=2)
				skill-=1;
			if(attack>=2)
				attack-=1;
			if(defense>=1)
				defense-=1;
			hp+=50;
			return false;
		}
	//	server.characterAttacks(userName, map[x][y].getPlayersInRoom(), targetNPC);
		return true;
	}		

}
