package commands;

import server.RWSOserver;

public class AcceptGiveCommand extends Command<RWSOserver>{

	
	private static final long serialVersionUID = -1904728701762253224L;
	private String targetName;
	private boolean accepted;
	private String itemName;

	public AcceptGiveCommand(String acceptersName, String targetName, String itemName, boolean accepted) {
		super(acceptersName);
		this.targetName = targetName;
		this.accepted = accepted;
		this.itemName = itemName;
		
	}

	@Override
	public void execute(RWSOserver executeOn) {
		executeOn.GiveCommandResponse(targetName, clientUserName, itemName, accepted);
		
	}

}
