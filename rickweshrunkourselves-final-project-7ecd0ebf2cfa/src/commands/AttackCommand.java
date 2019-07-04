package commands;

import server.RWSOserver;

public class AttackCommand extends Command<RWSOserver>{
	private String targetNPC;

	public AttackCommand(String clientUsername, String targetNPC) {
		super(clientUsername);
		this.targetNPC = targetNPC;
		
	}

	@Override
	public void execute(RWSOserver executeOn) {
		executeOn.attack(clientUserName, targetNPC);
		
	}

}
