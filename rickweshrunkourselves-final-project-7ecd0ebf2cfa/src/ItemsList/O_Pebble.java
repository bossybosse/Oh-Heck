package ItemsList;

import game.Item;


public class O_Pebble extends Item{
	
	private final static int ATTACK_VALUE = 4;
	private final static int USES = 1;
	private final static int HOW_COMMON = 4;

	public O_Pebble(String name, int uses, int hpBoost, int attack, int defense) {
		super("pebble ammo", USES, 0, ATTACK_VALUE, 0);		
	}

	@Override
	public void useItem() {
		// TODO Auto-generated method stub
		
	}

}
