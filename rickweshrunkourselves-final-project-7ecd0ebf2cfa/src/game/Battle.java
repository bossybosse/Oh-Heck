package game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.Random;


public class Battle implements Serializable{
	
	
	public Battle(Player player, NPC npc) throws IOException {
		
		System.out.println("There is a "+npc.getName()+npc.getDescription()+" in this room.");
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		while(!player.isDead() && !npc.isDead()){
			System.out.print("Type 'a' for attack or 'r' to run away!");
			String action = in.readLine();
			if(action.equals("a")){
				int playerAttack = multiplier(player.getAttack());
				npc.loseHp(playerAttack);
				int npcAttack = multiplier(npc.getAttack());
				player.loseHp(npcAttack);
			}
			
		}
	}
	
	private int multiplier(int attack){
		Random random = new Random();
		int multiplier = random.nextInt(20);
		if(multiplier>=0 && multiplier<=2)
			attack= 0*attack;
		if(multiplier>=3 && multiplier<=15)
			attack= 1*attack;
		if(multiplier>=16 && multiplier<=20)
			attack= 2*attack;
		return attack;
	}
	
	

}
