package commands;

import server.RWSOserver;

public class moveDownCommand extends Command<RWSOserver>{

	public moveDownCommand(String username) {
		super(username);
		
	}

	@Override
	public void execute(RWSOserver executeOn) {
		executeOn.moveDownCommand(clientUserName);
		
	}

}

