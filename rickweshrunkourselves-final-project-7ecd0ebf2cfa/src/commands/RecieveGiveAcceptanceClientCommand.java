package commands;

import client.RWSOClient;

public class RecieveGiveAcceptanceClientCommand extends Command<RWSOClient>{

	
	private static final long serialVersionUID = 5820640837516829989L;
	private String recieverName;
	private String itemName;

	public RecieveGiveAcceptanceClientCommand(String targetName, String recieverName, String itemName) {
		super(targetName);
		this.recieverName = recieverName;
		this.itemName = itemName;
	}

	@Override
	public void execute(RWSOClient executeOn) {
		executeOn.getAcceptanceCommand(clientUserName, recieverName, itemName);
	}

}
