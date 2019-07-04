package ItemsList;


import game.Item;



//this deflects enemy attack but can only be used a couple times. Very common item
public class E_StickyTape extends Item{
	
	private final static int DEFENSE_VALUE = 0;
	private final static int USES = 1;
	private final static int HOW_COMMON = 5;
	
	public E_StickyTape(String name, int uses, int hpBoost, int attack, int defense) {
		super("sticky tape", USES, 0, 0, DEFENSE_VALUE);		
	}

	@Override
	public void useItem() {
		// TODO Auto-generated method stub
		
	}


}