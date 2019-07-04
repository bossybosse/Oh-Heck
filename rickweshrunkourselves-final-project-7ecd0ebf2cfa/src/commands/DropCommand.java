package commands;

import java.io.IOException;

import game.Player;
import server.RWSOserver;

public class DropCommand extends Command<RWSOserver>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3386744217195903551L;
	private String item;
	private Player theUser;

	public DropCommand(String username, String item) {
		super(username);
		this.item = item;
	
		
	}

	@Override
	public void execute(RWSOserver executeOn) {
		try {
			executeOn.dropCommand(clientUserName, item);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
