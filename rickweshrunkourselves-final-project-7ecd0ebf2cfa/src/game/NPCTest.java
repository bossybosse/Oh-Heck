package game;

import static org.junit.Assert.*;

import org.junit.Test;

import NPCList.Ant;

public class NPCTest {

	@Test
	public void test() {
		Game g = new Game();
		NPC npc = new Ant("Test Ant", 50, 1, 0, 0);
		g.addNPCToRoom(npc);
		assertEquals(0,npc.getXLocation());
		assertEquals(0,npc.getYLocation());
		npc.move(0);
		assertEquals(0,npc.getXLocation());
		assertEquals(0,npc.getYLocation());
	}

}
