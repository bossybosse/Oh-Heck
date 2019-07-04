package commands;

import server.RWSOserver;

public class GetCommand extends Command<RWSOserver>{

	
	private static final long serialVersionUID = -1100710124698407699L;
	private String itemName;
	private String targetName;	
	
	public GetCommand(String username, String targetName, String itemName) {
		super(username);
		this.itemName= itemName;
		this.targetName = targetName;
	}

	@Override
	public void execute(RWSOserver executeOn) {
		executeOn.getItem(itemName, targetName, clientUserName);	
		
	}

}
