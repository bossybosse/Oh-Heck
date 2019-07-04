package commands;

import game.Player;
import server.RWSOserver;

public class LookCommand extends Command<RWSOserver> {

	private static final long serialVersionUID = -6278912220487923177L;
	private int yLocation;
	private int xLocation;

	public LookCommand(String theUser) {
		super(theUser);
		

	}

	@Override
	public void execute(RWSOserver executeOn) {
		executeOn.lookCommand(clientUserName);

	}

}
