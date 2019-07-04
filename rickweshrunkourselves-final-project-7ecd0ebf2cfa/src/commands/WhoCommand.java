package commands;

import game.Player;
import server.RWSOserver;

public class WhoCommand extends Command <RWSOserver> {

	

	private static final long serialVersionUID = -1721117397500977628L;
	
	public WhoCommand(String username) {
		super(username);
		
	}

	@Override
	public void execute(RWSOserver executeOn) {
	executeOn.whoCommand(clientUserName);
		
	}

}
