package commands;


import client.RWSOClient;

public class UpdateClientCommand extends Command<RWSOClient> {

	private static final long serialVersionUID = 893308418146018094L;
	private String text; // the message log from the server

	public UpdateClientCommand(String messages) {
		super(null);
		this.text = messages;
	}
	
	
	@Override
	public void execute(RWSOClient executeOn) {
		executeOn.updateCommand(text);
	}

}
