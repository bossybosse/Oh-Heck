package commands;

import client.RWSOClient;

public class UpdateClientScoreCommand extends Command<RWSOClient>{

	private String score;

	public UpdateClientScoreCommand(String score) {
		super(null);
		this.score = score;
	}

	@Override
	public void execute(RWSOClient executeOn) {
		executeOn.updateScore(score);
		
	}

}
