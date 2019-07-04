package model;

/**
 * Card Class Creates cards and holds their information
 * 
 * @author Jenna Franco, Richard Bosse
 * 
 */
public class Card implements Comparable<Card> {

	// Enums for the card
	private Rank rank;
	private Suit suit;

	/**
	 * Constructor for the card
	 * 
	 * @param r
	 * @param s
	 */
	public Card(Rank r, Suit s) {
		rank = r;
		suit = s;
	}

	/**
	 * Returns the rank of the card
	 * 
	 * @return
	 */
	public Rank getRank() {
		return rank;
	}

	/**
	 * Gets the suit of the card
	 * 
	 * @return
	 */
	public Suit getSuit() {
		return suit;
	}

	/**
	 * Returns a string representation of the card
	 * 
	 * @return
	 */
	public String toString() {

		// If the card value is less than 10, print the card
		if (this.getRank().getValue() <= 10)
			return this.getSuit().getLetter()
					+ Integer.toString(this.getRank().getValue());
		else
			// If the card is greater than 10, then get the letter for the rank
			return this.getSuit().getLetter()
					+ this.rank.name().substring(0, 1);
	}

	/**
	 * ComparTo compares the rank of 2 cards
	 * 
	 */
	@Override
	public int compareTo(Card other) {
		// If the ranks are equal, return 0
		if (this.getRank() == other.getRank()
				&& this.getSuit() == other.getSuit())
			return 0;
		// If the current card's value is greater, return 1
		else if (this.getRank().getValue() > other.getRank().getValue())
			return 1;
		else
			// Otherwise the other card's value is greater so return -1
			return -1;
	}

}
