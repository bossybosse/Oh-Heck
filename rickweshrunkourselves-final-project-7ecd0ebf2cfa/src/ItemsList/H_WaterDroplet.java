package ItemsList;

import game.Item;


public class H_WaterDroplet extends Item{

	private static final int HEAL_VALUE = 5;	
	private final static int HOW_COMMON = 5;
	private final static int USES = 1;
	
	public H_WaterDroplet(String name, int uses, int hpBoost, int attack, int defense) {
		super("water droplet", USES, HEAL_VALUE, 0, 0);		
	}

	@Override
	public void useItem() {
		// TODO Auto-generated method stub
		
	}
	
	

}
