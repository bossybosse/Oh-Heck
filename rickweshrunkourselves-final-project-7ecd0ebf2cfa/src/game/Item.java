package game;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Abstract item class with methods all items should have
 * 
 * @author Jenna Franco
 * 
 */

public abstract class Item implements Serializable {

	private static final long serialVersionUID = -67279180165188742L;
	protected String name;
	protected int uses;
	protected int attack;
	protected int hpBoost;
	protected int defense;
	protected boolean isUsable;
	protected boolean isEquiped;

	protected int xLocation;
	protected int yLocation;

	// protected Location location;

	public Item(String name, int uses, int hpBoost, int attack, int defense) {
		this.name = name;
		this.uses = uses;
		this.hpBoost = hpBoost;
		this.attack = attack;
		this.defense = defense;
		// location = loc;
	}

	public boolean isUsable() {
		return uses > 0;
	}

	public boolean isEquiped() {
		return isEquiped;
	}

	public String getName() {
		return name;
	}

	public abstract void useItem();

	// public void setLocation(Location loc) {
	// location = loc;
	// }
	//
	//
	// public Location getLocation() {
	// return location;
	// }
	//
	//
	// public String getLocationText() {
	// return location.getValue();
	// }

}