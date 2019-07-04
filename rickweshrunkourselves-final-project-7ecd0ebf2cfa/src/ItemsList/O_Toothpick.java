package ItemsList;

import game.Item;


public class O_Toothpick extends Item{
	
	private final static int ATTACK_VALUE = 3;
	private final static int USES = 50;
	private final static int HOW_COMMON = 3;

	public O_Toothpick(String name, int uses, int hpBoost, int attack, int defense) {
		super("toothpick", USES, 0, ATTACK_VALUE, 0);		
	}

	@Override
	public void useItem() {
		// TODO Auto-generated method stub
		
	}

}