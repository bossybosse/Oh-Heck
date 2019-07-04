package ItemsList;

import game.Item;


public class O_Thumbtack extends Item{
	
	private final static int ATTACK_VALUE = 5;
	private final static int USES = 1000;
	private final static int HOW_COMMON = 2;

	public O_Thumbtack(String name, int uses, int hpBoost, int attack, int defense) {
		super("thumbtack", USES, 0, ATTACK_VALUE, 0);		
	}

	@Override
	public void useItem() {
		// TODO Auto-generated method stub
		
	}

}
