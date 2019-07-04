package commands;

import server.RWSOserver;

public class DisconnectServerCommand extends Command<RWSOserver>{


	private static final long serialVersionUID = 3960381176210782250L;

	
	public DisconnectServerCommand(String name){
		super(name);
	}
	
	@Override
	public void execute(RWSOserver executeOn) {
		// disconnect client
		executeOn.disconnect(clientUserName);
	}
	
}
