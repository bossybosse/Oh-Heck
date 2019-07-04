package commands;

import server.RWSOserver;

public class InventoryCommand extends Command<RWSOserver>{

	public InventoryCommand(String username) {
		super(username);
		
	}

	@Override
	public void execute(RWSOserver executeOn) {
		executeOn.inventoryCommand(clientUserName);
		
	}

}
