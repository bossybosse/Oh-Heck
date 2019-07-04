package commands;

import java.io.IOException;

import server.RWSOserver;

public class PickUpCommand extends Command<RWSOserver>{

	private String item;

	public PickUpCommand(String username, String item) {
		super(username);
		this.item = item;
		
	}

	@Override
	public void execute(RWSOserver executeOn) {
		try {
			executeOn.pickUpCommand(clientUserName, item);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
