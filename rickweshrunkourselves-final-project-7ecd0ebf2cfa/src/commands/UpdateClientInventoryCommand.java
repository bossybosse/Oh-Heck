package commands;

import client.RWSOClient;

public class UpdateClientInventoryCommand extends Command<RWSOClient>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1595616058124250701L;
	private String inventory;

	public UpdateClientInventoryCommand(String inventory) {
		super(null);
		this.inventory = inventory;
		
	}

	@Override
	public void execute(RWSOClient executeOn) {
		executeOn.updateInventory(inventory);
		
	}


}