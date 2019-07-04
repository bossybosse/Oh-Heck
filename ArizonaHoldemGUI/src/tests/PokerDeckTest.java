package tests;

import model.*;
import org.junit.Test;

/**
 * Tests the Poker Deck Class
 * 
 * @author Richard Bosse, Jenna Franco
 * 
 */
public class PokerDeckTest {
	// ***********PokerDeck Class Tests**********
	/**
	 * Tests if the card is created
	 * 
	 */
	@Test
	public void testCreateCard() {
		Card[] theCards = new Card[52];
		// Loop through all the suits and ranks and add them to the deck
		int i = 0;
		for (Suit suit : Suit.values()) {
			for (Rank rank : Rank.values()) {
				theCards[i] = new Card(rank, suit);
				i++;
			}
		}
	}

}
