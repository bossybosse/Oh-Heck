package game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import javax.swing.Timer;


public class NPC extends Character implements Serializable, ActionListener{
	

	private static final long serialVersionUID = 1L;		
	private Random rnd = new Random();	
	
	public NPC(String name, int hp, int attack, int xLocation, int yLocation) {
		// TODO Auto-generated constructor stub		
		super(name,hp,attack, 0, xLocation, yLocation);		
	}	
	
	public void move(int random) {
		if(name!="Mouse"){
		if ( random == 0) //move up
			incrementXLocation(-1);
		if ( random == 1) //move down
			incrementXLocation(1);
		if ( random == 2) //move right
			incrementYLocation(1);
		if ( random == 3) //move left
			incrementYLocation(-1);
		}
	}

		
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {	
		
		// TODO Auto-generated method stub
		int random;
		random = rnd.nextInt(4);
		if ( random == 0) //move up
			incrementXLocation(-1);
		if ( random == 1) //move down
			incrementXLocation(1);
		if ( random == 2) //move right
			incrementYLocation(1);
		if ( random == 3) //move left
			incrementYLocation(-1);
	}

	
}
	