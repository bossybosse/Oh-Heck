package model;

/**
 * Enum Suit: Contains all the different suits of cards
 * 
 * @author Jenna Franco, Richard Bosse
 * 
 */
public enum Suit {
	// All the suits with thier letters
	Diamonds('D'), Clubs('C'), Hearts('H'), Spades('S');

	/**
	 * Gets the letter associated with the suit
	 * 
	 * @return
	 */
	public char getLetter() {
		return letter;

	}

	// Stores the letter with the enum
	private char letter;

	/**
	 * Constructor that sets the letter
	 * 
	 * @param letter
	 */
	Suit(char letter) {
		this.letter = letter;
	}
}
