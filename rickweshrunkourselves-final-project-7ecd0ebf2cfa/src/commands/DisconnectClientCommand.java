package commands;

import client.RWSOClient;

public class DisconnectClientCommand extends Command<RWSOClient>{

	
	public DisconnectClientCommand(String username) {
		super(null);
		// TODO Auto-generated constructor stub
	}
	
	

	private static final long serialVersionUID = 7490925032506284790L;

	@Override
	public void execute(RWSOClient executeOn) {
		executeOn.disconnect();
		
	}

}
