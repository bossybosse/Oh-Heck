package ItemsList;


import game.Item;



//this deflects enemy attack but can only be used a couple times. Very common item
public class D_Rubberband extends Item{
	
	private final static int DEFENSE_VALUE = 6;
	private final static int USES = 2;
	private final static int HOW_COMMON = 5;
	
	public D_Rubberband(String name, int uses, int hpBoost, int attack, int defense) {
		super("rubber band", USES, 0, 0, DEFENSE_VALUE);		
	}

	@Override
	public void useItem() {
		// TODO Auto-generated method stub
		
	}


}
