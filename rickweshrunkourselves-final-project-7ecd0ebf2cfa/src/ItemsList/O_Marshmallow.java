package ItemsList;


import game.Item;


public class O_Marshmallow extends Item{
	
	private final static int ATTACK_VALUE = 2;
	private final static int USES = 1;
	private final static int HOW_COMMON = 5;

	public O_Marshmallow(String name, int uses, int hpBoost, int attack, int defense) {
		super("marshmallow ammo", USES, 0, ATTACK_VALUE, 0);		
	}

	@Override
	public void useItem() {
		// TODO Auto-generated method stub
		
	}

}
