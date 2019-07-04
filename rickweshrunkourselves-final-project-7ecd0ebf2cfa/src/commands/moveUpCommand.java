package commands;

import server.RWSOserver;

public class moveUpCommand extends Command<RWSOserver>{

	public moveUpCommand(String username) {
		super(username);
		
	}

	@Override
	public void execute(RWSOserver executeOn) {
		executeOn.moveUpCommand(clientUserName);
		
	}

}

