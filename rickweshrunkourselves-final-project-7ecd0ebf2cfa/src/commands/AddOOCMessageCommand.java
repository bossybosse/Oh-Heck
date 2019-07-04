package commands;

import server.RWSOserver;

public class AddOOCMessageCommand extends Command<RWSOserver> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 517416493638004734L;
	private String message; // message from client

	public AddOOCMessageCommand(String message) {
		super(null);
		this.message = message;
	}

	@Override
	public void execute(RWSOserver executeOn) {
		// add message to server's chat log
		executeOn.addGlobalMessage(message);

	}

}
