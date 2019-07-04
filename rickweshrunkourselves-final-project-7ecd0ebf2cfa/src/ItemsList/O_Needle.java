package ItemsList;


import game.Item;


public class O_Needle extends Item{
	
	private final static int ATTACK_VALUE = 10;
	private final static int USES = 100000;
	private final static int HOW_COMMON = 1;

	public O_Needle(String name, int uses, int hpBoost, int attack, int defense) {
		super("needle", USES, 0, ATTACK_VALUE, 0);		
	}

	@Override
	public void useItem() {
		// TODO Auto-generated method stub
		
	}

}
