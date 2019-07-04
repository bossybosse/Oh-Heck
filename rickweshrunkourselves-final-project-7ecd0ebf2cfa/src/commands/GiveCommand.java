package commands;

import server.RWSOserver;

public class GiveCommand extends Command<RWSOserver>{

	private String itemName;
	private String targetName;	
	
	public GiveCommand(String username, String targetName, String itemName) {
		super(username);
		this.itemName= itemName;
		this.targetName = targetName;
	}


	@Override
	public void execute(RWSOserver executeOn) {
		executeOn.giveItem(itemName, targetName, clientUserName);		
	}

}
