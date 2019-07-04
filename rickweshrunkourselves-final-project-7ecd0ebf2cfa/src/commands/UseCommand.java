package commands;

import java.io.IOException;

import server.RWSOserver;

public class UseCommand extends Command<RWSOserver>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5387113125109000587L;
	private String item;

	public UseCommand(String username, String item) {
		super(username);
		this.item = item;
		
	}

	@Override
	public void execute(RWSOserver executeOn) {		
			executeOn.useCommand(clientUserName, item);
	
	}

}
