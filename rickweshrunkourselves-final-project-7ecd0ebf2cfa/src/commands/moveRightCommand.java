package commands;

import server.RWSOserver;

public class moveRightCommand extends Command<RWSOserver>{

	public moveRightCommand(String username) {
		super(username);
		
	}

	@Override
	public void execute(RWSOserver executeOn) {
		executeOn.moveRightCommand(clientUserName);
		
	}

}
