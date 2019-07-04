package model;

import java.util.*;

/**
 * PokerDeck Class Creates a deck of cards for the game
 * 
 * @author Jenna Franco, Richard Bosse
 * 
 */
public class PokerDeck {

	// The poker deck
	Stack<Card> deck;

	/**
	 * PokerDeck constructor creates a new deck
	 * 
	 */
	public PokerDeck() {

		// Creates the deck
		deck = new Stack<Card>();

		// Add a card containing each rank and suit possible
		for (Suit suit : Suit.values()) {
			for (Rank rank : Rank.values()) {
				deck.add(new Card(rank, suit));
			}
		}

	}

	/**
	 * Gets the top card from the deck
	 * 
	 * @return
	 */
	public Card getTopCard() {
		return deck.pop();
	}

	/**
	 * Shuffles the deck 5 times to represent realistic shuffling of a deck
	 * 
	 */
	public void shuffleCards() {

		// Shuffles the deck 5 times
		for (int i = 0; i < 5; i++) {
			Collections.shuffle(deck);
		}
	}
}
