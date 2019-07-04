package commands;
import server.RWSOserver;

public class ShutdownCommand extends Command<RWSOserver> {

	
	private static final long serialVersionUID = 4888626927792332965L;

	private String password;
	
	public ShutdownCommand(String input) {
		super(null);
		this.password = input;
	}

	@Override
	public void execute(RWSOserver executeOn) {
		executeOn.shutDown(password);
		
	}

}
