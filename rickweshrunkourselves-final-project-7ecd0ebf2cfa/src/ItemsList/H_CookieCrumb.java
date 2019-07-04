package ItemsList;


import game.Item;


public class H_CookieCrumb extends Item{
	
	private static final int HEAL_VALUE = 100;	
	private final static int HOW_COMMON = 2;
	private final static int USES = 1;

	public H_CookieCrumb(String name, int uses, int hpBoost, int attack, int defense) {
		super("cookie crumb", USES, HEAL_VALUE, 0, 0);		
	}

	@Override
	public void useItem() {
		// TODO Auto-generated method stub
		
	}
}
