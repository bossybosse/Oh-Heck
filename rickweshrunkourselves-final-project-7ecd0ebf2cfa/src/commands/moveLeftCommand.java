package commands;

import server.RWSOserver;

public class moveLeftCommand extends Command<RWSOserver>{

	public moveLeftCommand(String username) {
		super(username);
		
	}

	@Override
	public void execute(RWSOserver executeOn) {
		executeOn.moveLeftCommand(clientUserName);
		
	}

}
