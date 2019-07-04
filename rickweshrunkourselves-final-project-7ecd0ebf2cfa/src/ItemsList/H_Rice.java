package ItemsList;


import game.Item;


public class H_Rice extends Item{

	private static final int HEAL_VALUE = 15;	
	private final static int HOW_COMMON = 4;
	private final static int USES = 1;

	public H_Rice(String name, int uses, int hpBoost, int attack, int defense) {
		super("rice", USES, HEAL_VALUE, 0, 0);		
	}

	@Override
	public void useItem() {
		// TODO Auto-generated method stub
		
	}
	
	

}