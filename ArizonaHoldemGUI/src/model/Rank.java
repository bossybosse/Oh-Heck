package model;

/**
 * Enum Rank: Contains the rank of each card and that ranks value
 * 
 * @author Jenna Franco, Richard Bosse
 * 
 */

public enum Rank {
	Deuce(2), Three(3), Four(4), Five(5), Six(6), Seven(7), Eight(8), Nine(9), Ten(
			10), Jack(11), Queen(12), King(13), Ace(14);

	/**
	 * Gets the value of the rank
	 * 
	 * @return the value
	 */
	public int getValue() {
		return value;
	}

	// Enum Elements
	private int value;

	/**
	 * Constructor that sets the value to the rank
	 * 
	 * @param val
	 */
	Rank(int val) {
		value = val;
	}

}
