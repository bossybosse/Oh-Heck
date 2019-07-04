package commands;
import client.RWSOClient;

public class UpdateClientChat extends Command<RWSOClient>{

	
	private static final long serialVersionUID = 6490476329037924947L;
	private String message;
	public UpdateClientChat(String message){
		super(null);
		this.message = message;
	}
	@Override
	public void execute(RWSOClient executeOn) {
		executeOn.updateChatMessage(message);
		
	}

}
