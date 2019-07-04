package commands;

import server.RWSOserver;

public class AddTellMessageCommand extends Command<RWSOserver>  {
	
	private String message;
	private String targetName;
	
	public AddTellMessageCommand(String username, String targetName, String message) {
		super(username);
		this.targetName= targetName;
		this.message = message;		
	}

	@Override
	public void execute(RWSOserver executeOn) {
		executeOn.addPersonalMessage(message, targetName, clientUserName);
		
	}

}
