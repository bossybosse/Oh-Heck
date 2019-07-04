package ItemsList;


import game.Item;


public class H_Bean extends Item {

	private static final int HEAL_VALUE = 50;	
	private final static int HOW_COMMON = 3;
	private final static int USES = 1;

	public H_Bean(String name, int uses, int hpBoost, int attack, int defense) {
		super("bean", USES, HEAL_VALUE, 0, 0);		
	}

	@Override
	public void useItem() {
		// TODO Auto-generated method stub
		
	}

	
	

}
