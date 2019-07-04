package commands;

import server.RWSOserver;

public class RoomDescriptionCommand extends Command<RWSOserver> {
	private static final long serialVersionUID = -6278912220487923177L;
	private int yLocation;
	private int xLocation;

	public RoomDescriptionCommand(String theUser) {
		super(theUser);
		

	}

	@Override
	public void execute(RWSOserver executeOn) {
		executeOn.roomDescriptionCommand(clientUserName);

	}
}
