package commands;

import client.RWSOClient;



public class RecieveAcceptanceClientCommand extends Command<RWSOClient>{


	private static final long serialVersionUID = 5765565812699535242L;
	private String itemName;

	public RecieveAcceptanceClientCommand(String clientUsername, String itemName) {
		super(clientUsername);
		this.itemName = itemName;
	}

	@Override
	public void execute(RWSOClient executeOn) {
		executeOn.giveAcceptanceCommand(itemName, clientUserName);
		
	}



}
