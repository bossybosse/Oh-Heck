package game;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class Character implements Serializable{

	
	
	private static final long serialVersionUID = 4722870043138660358L;
	protected String name;
	protected int hp;
	protected int attack;
	protected int defense;
	protected int xLoc;
	protected int yLoc;
	protected HashMap<String, ArrayList<Item>> items;
	
	public Character(String name, int hp, int attack, int defense, int xLoc, int yLoc) {
		this.name = name;
		this.hp = hp;
		this.attack = attack;
		this.defense = defense;
		this.xLoc = xLoc;
		this.yLoc = yLoc;		
		items = new HashMap<String, ArrayList<Item>>();
	}
	
	public String getName(){
		return name;
	}
	
	public int getHp(){
		return hp;
	}
	
	public boolean isDead(){
		return hp<=0;
	}
	
	public void loseHp (int HpLost) {
		hp -= HpLost;
	}
	public void gainHp (int HpGain) {
		hp += HpGain;
	}	
	
	public int getAttack(){
		return attack;
	}
	
	public int getDefense(){
		return defense;
	}
	
	public int getXLocation(){		
		return xLoc;	
	}
	public int getYLocation(){		
		return yLoc;
	}
	public void incrementXLocation(int x) {
		
		xLoc += x;
		if ( xLoc < 0)
			xLoc = 0 ;
		else if (xLoc > 4)
			xLoc = 4;
			
	}
	public void incrementYLocation(int y) {
		yLoc += y;
		if (yLoc < 0)
			yLoc = 0;
		else if (yLoc > 5)
			yLoc = 5;
	}	
	
}
