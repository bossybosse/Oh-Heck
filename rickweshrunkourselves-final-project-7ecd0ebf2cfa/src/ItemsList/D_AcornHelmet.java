package ItemsList;
import game.Item;

public class D_AcornHelmet extends Item{

	private final static int DEFENSE_VALUE = 3;
	private final static int USES = 1000;
	private final static int HOW_COMMON = 2;
	
	public D_AcornHelmet(String name, int uses, int hpBoost, int attack, int defense) {
		super("acorn helmet", USES, 0, 0, DEFENSE_VALUE);		
	}

	@Override
	public void useItem() {
		// TODO Auto-generated method stub
		
	}
}
