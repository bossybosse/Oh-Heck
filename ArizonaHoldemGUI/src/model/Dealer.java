package model;

import java.util.*;

/**
 * Dealer class:
 * 
 * Controls the deck and decides the winning hand
 * 
 * @author Jenna Franco and Richard Bosse
 * 
 */
public class Dealer {

	// The Deck
	private PokerDeck deck;

	/**
	 * Dealer constructor
	 * 
	 */
	public Dealer() {
		deck = new PokerDeck();
	}

	/**
	 * Deals out all the cards to the players
	 * 
	 * @param playerList
	 *            The list of players
	 * @param communityCards
	 *            The 5 community cards
	 * 
	 */
	public void dealCards(List<Player> playerList, List<Card> communityCards) {

		// Shuffle
		deck.shuffleCards();

		// Deal a card to each player until all players have 2 cards
		for (int round = 1; round <= 2; round++) {
			for (Player player : playerList) {
				player.setCard(deck.getTopCard());
			}
		}

		// Deal the 5 community cards
		for (int i = 0; i < 5; i++) {
			communityCards.add(deck.getTopCard());
		}
		Collections.sort(communityCards);
	}

	/**
	 * Resets the board by removing all the used cards from the players
	 * 
	 * @param playerList
	 */
	public void removeUsedDeck(List<Player> playerList) {

		// For each player, clear the cards
		for (Player player : playerList) {
			player.clearCards();
		}

		// Create a new deck of cards
		deck = new PokerDeck();
	}

}
