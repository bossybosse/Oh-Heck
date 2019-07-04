package commands;

import server.RWSOserver;

public class ScoreCommand extends Command<RWSOserver>{

	

	public ScoreCommand(String username) {
		super(username);
	}

	@Override
	public void execute(RWSOserver executeOn) {
		executeOn.scoreCommand(clientUserName);
		
	}

}
