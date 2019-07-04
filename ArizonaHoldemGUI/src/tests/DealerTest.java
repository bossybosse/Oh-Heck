package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.List;
import model.*;

import org.junit.Test;

/**
 * Tests the Dealer Class
 * 
 * @author Richard Bosse, Jenna Franco
 * 
 */
public class DealerTest {

	// ****************Dealer Class Tests*****************
	/**
	 * Tests if the dealer is created
	 */
	@Test
	public void createDealer() {
		Dealer theDealer = new Dealer();
		assertEquals(1, 1);
	}

	/**
	 * Tests if cards are dealt
	 * 
	 */
	@Test
	public void testDealCards() {
		// Creates the dealer
		Dealer theDealer = new Dealer();

		// Creates the players and community cards
		List<Player> playerList = new ArrayList<Player>();
		playerList.add(new Player("Bob"));
		playerList.add(new Player("Jenna"));
		List<Card> communityCards = new ArrayList<Card>();
		assertEquals(0, communityCards.size());

		theDealer.dealCards(playerList, communityCards);

		assertEquals(5, communityCards.size());

	}

	/**
	 * Tests if the deck is reset for another game
	 * 
	 */
	@Test
	public void testClearDeck() {

		// Creates the game
		Dealer theDealer = new Dealer();
		List<Player> playerList = new ArrayList<Player>();
		playerList.add(new Player("Bob"));
		playerList.add(new Player("Jenna"));

		// Deals out cards
		playerList.get(0).setCard(CA);
		playerList.get(0).setCard(C10);

		playerList.get(1).setCard(SA);
		playerList.get(1).setCard(S10);

		assertTrue("CA C10".equals(playerList.get(0).getPlayer2Cards()));
		assertTrue("SA S10".equals(playerList.get(1).getPlayer2Cards()));

		// removes the cards
		theDealer.removeUsedDeck(playerList);

		assertTrue("".equals(playerList.get(0).getPlayer2Cards()));
		assertTrue("".equals(playerList.get(1).getPlayer2Cards()));

	}

	// ********************all cards*************************
	
	private final static Card C10 = new Card(Rank.Ten, Suit.Clubs);
	
	private final static Card CA = new Card(Rank.Ace, Suit.Clubs);

	

	
	
	
	private final static Card S10 = new Card(Rank.Ten, Suit.Spades);
	private final static Card SA = new Card(Rank.Ace, Suit.Spades);


}
