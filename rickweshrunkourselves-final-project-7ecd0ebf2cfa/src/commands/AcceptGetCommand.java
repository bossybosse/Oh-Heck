package commands;

import server.RWSOserver;

public class AcceptGetCommand extends Command<RWSOserver>{

	private String gettersName;
	private boolean accepted;
	private String itemName;

	public AcceptGetCommand(String clientUsername, String gettersUserName, String itemName, boolean accepted) {
		super(clientUsername);
		this.gettersName = gettersUserName;
		this.accepted = accepted;
		this.itemName = itemName;
		
	}

	@Override
	public void execute(RWSOserver executeOn) {
		executeOn.acceptGetCommand(clientUserName, gettersName, itemName, accepted);
		
	}

}
