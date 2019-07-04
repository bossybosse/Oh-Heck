package model;

import java.util.*;
import java.util.Map.*;

/**
 * PokerHand Class: Creates a five card hand and compares five card hands to
 * other hands.
 * 
 * @author Richard Bosse Jenna Franco
 * 
 */
public class PokerHand implements Comparable<PokerHand> {

	// Class Elements
	private Set<Card> theHand;
	TreeMap<Rank, Integer> quantityOfRank;
	// private cardComparator cardComparator;
	private static final int FOUR_OF_A_KIND = 4;
	private static final int THREE_OF_A_KIND = 3;
	private static final int A_PAIR = 2;
	private static final int SINGLE_CARD = 1;
	private static final int ACE = 14;
	private static final int FIVE = 5;

	public PokerHand() {

		// Sets the class Elements
		theHand = new TreeSet<Card>();
		quantityOfRank = new TreeMap<Rank, Integer>();

	}

	/**
	 * Constructor to put the 5 given cards into a hand. The hand is then sorted
	 * by value from lowest to greatest.
	 * 
	 * @throws DuplicateCardException
	 *             If there is a duplicate card, then the DuplicateCardException
	 *             is thrown
	 */
	public PokerHand(Card c1, Card c2, Card c3, Card c4, Card c5) {

		// Sets the class Elements
		theHand = new TreeSet<Card>();
		quantityOfRank = new TreeMap<Rank, Integer>();

		// Adds the cards
		theHand.add(c1);
		if (!theHand.add(c2)) {
			throw new DuplicateCardException();
		}
		if (!theHand.add(c3)) {
			throw new DuplicateCardException();
		}
		if (!theHand.add(c4)) {
			throw new DuplicateCardException();
		}
		if (!theHand.add(c5)) {
			throw new DuplicateCardException();
		}

		// Sets the treeMap
		// Iterator for the hand
		Iterator<Card> cardItorator = this.theHand.iterator();
		while (cardItorator.hasNext()) {

			Card tempCard = cardItorator.next();
			// If the rank has already been set
			if (quantityOfRank.containsKey(tempCard.getRank())) {

				// Increase its quantity by one
				int temp = quantityOfRank.get(tempCard.getRank());
				quantityOfRank.put(tempCard.getRank(), ++temp);

			} else {
				// Otherwise create it
				quantityOfRank.put(tempCard.getRank(), 1);
			}

		}

	}

	/**
	 * /** Compares two Poker hands and determines which hand has more value
	 * based on poker rules. If there is a duplicate card in both hands, an
	 * exception is thrown
	 * 
	 */
	public int compareTo(PokerHand b) {

		// Checks if either hand has a straight flush
		if (this.hasStraightFlush() || b.hasStraightFlush()) {

			// If the current hand has straight flush and b does not. return 1
			if (!b.hasStraightFlush())
				return 1;

			// If b hand has SF and current hand does not. return -1
			else if (!this.hasStraightFlush())
				return -1;

			// Both have straight flush so compare the highCard
			else
				return checkHighCard(b);
		}

		// Checks if either hand has a 4 of a kind
		if (this.has4ofAKind() || b.has4ofAKind()) {

			// If hand b does not have 4 of a kind
			if (!b.has4ofAKind())
				return 1;

			// If current hand does not have a 4 of a kind
			else if (!this.has4ofAKind())
				return -1;

			// If they both have four of a kind compare them
			else if (this.fourOfAKindRank() > b.fourOfAKindRank())
				// If current hand's 4 of a kind is greater return 1
				return 1;

			// If they both have four of a kind compare them
			else if (this.fourOfAKindRank() < b.fourOfAKindRank())
				// If current hand's 4 of a kind is greater return 1
				return -1;

			// Hand b's 4 of a kind is greater so return -1
			return checkHighCard(b);

		}

		// Checks if either hand has a Full House
		if (this.hasFullHouse() || b.hasFullHouse()) {

			// If hand b does not have full house
			if (!b.hasFullHouse())
				return 1;

			// If current hand does not have a 4 of a kind
			else if (!this.hasFullHouse())
				return -1;

			// If current hands full house is greater by comparing the 3 of a
			// kind
			else if (this.threeofAKindRank() > b.threeofAKindRank())
				return 1;

			// If hand b's full house is greater
			else if (this.threeofAKindRank() < b.threeofAKindRank())
				return -1;

			// Compare the pairs
			return this.pairComparison(b);

		}

		// Check both hands for a flush and run if either have a flush
		if (this.hasFlush() || b.hasFlush()) {

			// If hand b does not have a flush
			if (!b.hasFlush())
				return 1;

			// If current hand does not have a flush
			else if (!this.hasFlush())
				return -1;

			// Both hands have a flush so compare the high cards
			else
				return checkHighCard(b);
		}

		// Checks both hands for a Straight and runs if either has it
		if (this.hasStraight() || b.hasStraight()) {

			// If hand b does not have a straight
			if (!b.hasStraight())
				return 1;

			// If current hand does not have a straight
			else if (!this.hasStraight())
				return -1;

			// Both hands have a straight so compare the high cards
			else
				return checkHighCard(b);
		}

		// Checks both hands for a 3 of a kind and runs if either has it
		if (this.has3ofAKind() || b.has3ofAKind()) {

			// If hand b does not have a 3 of a kind
			if (!b.has3ofAKind())
				return 1;

			// If current hand does not have a 3 of a kind
			else if (!this.has3ofAKind())
				return -1;

			// If current hand's 3 of a kind is greater than hand b's
			else if (this.threeofAKindRank() > b.threeofAKindRank())
				return 1;

			// If b hands 3 of a kind is greater
			else if (this.threeofAKindRank() < b.threeofAKindRank())
				return -1;

			// compare the high card
			else
				return checkHighCard(b);
		}

		// Checks both hands for two pair and runs if either has it
		if (this.has2Pair() || b.has2Pair()) {

			// If hand b does not have 2 pair
			if (!b.has2Pair())
				return 1;

			// If current hand does not have 2 pair
			else if (!this.has2Pair())
				return -1;

			// Both hands have 2 pair so compare them
			else
				return pairComparison(b);
		}

		// Checks both hands for a pair and run if either has it
		if (this.hasPair() || b.hasPair()) {

			// If hand b does not have a pair
			if (!b.hasPair())
				return 1;

			// If current hand does not have a pair
			else if (!this.hasPair())
				return -1;

			// Both hands have a pair so compare the pairs
			else
				return pairComparison(b);
		}

		// Both hands have nothing so check the high card
		return checkHighCard(b);
	}

	/**
	 * Check if the hand has a straight flush
	 * 
	 * @return
	 */
	private boolean hasStraightFlush() {

		// If hand has a straight and a flush, then it has a straight flush
		if (this.hasFlush() && this.hasStraight())
			return true;

		// Return false is does not have straight flush
		return false;
	}

	/**
	 * Checks if the hand has a 4 of a kind
	 * 
	 * @return
	 */
	private boolean has4ofAKind() {

		// If there are 4 of the same rank in the hand, then the hand has a
		// four of a kind
		if (quantityOfRank.containsValue(FOUR_OF_A_KIND))
			return true;
		else
			return false;
	}

	/**
	 * Returns the rank of the four of a kind
	 * 
	 * @return
	 */
	private int fourOfAKindRank() {

		// A place to store the rank
		int theRank = 0;

		// Loop though all the elements in quantityOfrank
		for (Entry<Rank, Integer> entry : quantityOfRank.entrySet()) {

			// If the element has a four of a kind, set theRank to that rank
			if (entry.getValue() == FOUR_OF_A_KIND)
				theRank = entry.getKey().getValue();
		}

		// Return the rank
		return theRank;

	}

	/**
	 * Determines if hand has a Full House
	 * 
	 * @return
	 */
	private boolean hasFullHouse() {

		// If the Hand has a pair and three of a kind, return true
		if (quantityOfRank.containsValue(A_PAIR)
				&& quantityOfRank.containsValue(THREE_OF_A_KIND)) {
			return true;

		}

		// Otherwise return false
		return false;
	}

	/**
	 * Check if the hand has a flush
	 * 
	 * @return true if hand has a flush
	 */
	private boolean hasFlush() {

		// Iterator for the hand
		Iterator<Card> cardItorator = this.theHand.iterator();

		// Get the first card's suit.
		Suit theFlushSuit = cardItorator.next().getSuit();

		// Loops through the entire hand
		while (cardItorator.hasNext()) {

			// Gets the current card
			Card currCard = cardItorator.next();

			// Compares the current cards's suit to theFlush suit
			if (currCard.getSuit() != theFlushSuit) {
				// Returns false if different
				return false;
			}
		}

		// All cards have the same suit so return true
		return true;
	}

	/**
	 * Checks if a hand has a Straight
	 * 
	 * @return
	 */
	private boolean hasStraight() {

		// Iterator for the hand
		Iterator<Card> cardItorator = this.theHand.iterator();

		Card firstCard = cardItorator.next();
		Card previousCard = firstCard;

		// Loops through the entire hand
		while (cardItorator.hasNext()) {

			// Gets the current card
			Card currCard = cardItorator.next();

			// if card is ace, check for ace low straight
			if (currCard.getRank().getValue() == 14) {
				if (firstCard.getRank().getValue() == 2) {
					return true;
				}
			}
			if (currCard.getRank().getValue() != previousCard.getRank()
					.getValue() + 1) {
				return false;
			}
			previousCard = currCard;
		}

		return true;

	}

	/**
	 * Checks if a hand has a 3 of a kind
	 * 
	 * @return
	 */
	private boolean has3ofAKind() {

		// If there is 3 in quantity of rank, then there is a 3 of a kind
		if (quantityOfRank.containsValue(THREE_OF_A_KIND))
			return true;
		else
			return false;
	}

	/**
	 * Returns the rank of the three of a kind
	 * 
	 * @return
	 */
	private int threeofAKindRank() {

		// To keep track of the rank
		int theRank = 0;

		// Loops through every element in quantity of rank
		for (Entry<Rank, Integer> rank : quantityOfRank.entrySet()) {
			// if that element has a 3 of a king set theRank to it
			if (rank.getValue() == THREE_OF_A_KIND)
				theRank = rank.getKey().getValue();
		}

		return theRank;
	}

	/**
	 * Checks if hand has 2 pair
	 * 
	 * @return
	 */
	private boolean has2Pair() {

		/*
		 * With all the pairs grouped in quantityOfRank, the size can tell us if
		 * there are two pair With a pair only taking up one location in
		 * quantity of rank, two pairs will only take up two That being said,
		 * two pair is 4 cards and with the only being 5 cards in poker, the
		 * last card will take up another slot in quantityOfRank making its size
		 * 3. No matter what the ranks are, quantityOfRank will always have a
		 * size 3.
		 */
		if (quantityOfRank.size() == 3)
			return true;
		else
			return false;

	}

	/**
	 * Checks if a hand has a pair
	 * 
	 * @return
	 */
	private boolean hasPair() {
		if (quantityOfRank.containsValue(A_PAIR))
			return true;
		else
			return false;
	}

	/**
	 * Compares pairs together to determine which one has more rank. Both hands
	 * must have the same amount of pairs
	 * 
	 * @param b
	 *            PokerHand to compare
	 * @return
	 */
	private int pairComparison(PokerHand b) {

		// Lists of all the pairs in a hand
		LinkedList<Rank> thisHandsPairs = this.getRanksOfPairs();
		LinkedList<Rank> bHandsPairs = b.getRanksOfPairs();

		// Check until there are no more pairs
		while (!thisHandsPairs.isEmpty()) {

			// If the highest ranked pair in current is higher than b's return 1
			if (thisHandsPairs.getLast().getValue() > bHandsPairs.getLast()
					.getValue())
				return 1;

			// If b's highest ranked pair is higher than current, return -1
			else if (thisHandsPairs.getLast().getValue() < bHandsPairs
					.getLast().getValue())
				return -1;

			// Since both pairs where the same, remove them and check the next
			thisHandsPairs.removeLast();
			bHandsPairs.removeLast();
		}

		return checkHighCard(b);
	}

	/**
	 * Gets a list of the rankings of the pairs from smallest to largest
	 * 
	 * @return
	 */
	private LinkedList<Rank> getRanksOfPairs() {

		// The list to be returned
		LinkedList<Rank> thisHandsPairs = new LinkedList<Rank>();

		// Loops through the hand and adds every pair to the list
		for (Entry<Rank, Integer> rank : quantityOfRank.entrySet()) {
			if (rank.getValue() == A_PAIR) {
				thisHandsPairs.add(rank.getKey());
			}
		}

		// Returns the list
		return thisHandsPairs;

	}

	/**
	 * Compares the high cards of a hand. Any pairs will be ignored for
	 * comparison and if the hand has a straight with an ace low then the ace is
	 * ignored
	 * 
	 * @param b
	 * @return
	 */
	private int checkHighCard(PokerHand b) {

		// Needed lists
		LinkedList<Rank> thisHandsListOfRanks = new LinkedList<Rank>();
		LinkedList<Rank> bHandsListOfRanks = new LinkedList<Rank>();

		// Get all the ranks that only have 1 for current hand
		for (Entry<Rank, Integer> rank : this.quantityOfRank.entrySet()) {
			if (rank.getValue() == SINGLE_CARD)
				thisHandsListOfRanks.addFirst(rank.getKey());
		}

		// Get all the ranks that only have 1 for hand b
		for (Entry<Rank, Integer> rank : b.quantityOfRank.entrySet()) {
			if (rank.getValue() == SINGLE_CARD)
				bHandsListOfRanks.addFirst(rank.getKey());
		}

		// Creates the iterators
		Iterator<Rank> thisHandsIterator = thisHandsListOfRanks.iterator();
		Iterator<Rank> bHandsIterator = bHandsListOfRanks.iterator();
		Iterator<Rank> temp = thisHandsListOfRanks.iterator();

		// Check if the current hand has a straight
		if (this.hasStraight()) {
			// If current hand has a straight and ace is low, then move the
			// iterator passed the ace
			if (temp.next().getValue() == ACE && temp.next().getValue() == FIVE)
				thisHandsIterator.next();
		}

		// Check if hand b has a straight
		temp = bHandsListOfRanks.iterator();

		// If hand b has a straight, and ace is low, then move the iterator
		// passed the ace
		if (b.hasStraight()) {
			if (temp.next().getValue() == ACE && temp.next().getValue() == FIVE)
				bHandsIterator.next();
		}

		// Compare the high cards
		while (thisHandsIterator.hasNext()) {

			// Gets the values of the high cards in each hand
			int thisHandsRank = thisHandsIterator.next().getValue();
			int bHandsRank = bHandsIterator.next().getValue();

			// If current's card is higher
			if (thisHandsRank > bHandsRank)
				return 1;

			// If b's card is higher
			if (thisHandsRank < bHandsRank)
				return -1;
		}

		// If all the high cards have the same rank
		return 0;
	}

	/**
	 * Makes a string representation of the hand for printing
	 * 
	 * @return The hand as string
	 */
	public String toString() {

		// Creates the hand
		String hand = "";

		// Loops through every card and adds it to the string
		for (Card c : theHand) {
			hand += c.toString() + " ";
		}

		// Returns the hand
		return hand.trim();

	}

	/**
	 * Determines if the hand is bettable. A bettable hand has a pair of 8's or
	 * greater
	 * 
	 * @return
	 */
	public boolean isBetable() {

		// If the hand contains a straight or a flush return true
		if (this.hasStraight() || this.hasFlush() || this.has2Pair()
				|| this.has3ofAKind() || this.has4ofAKind())
			return true;

		// If the hand has a pair. Check if it is 8s or greater
		else if (this.hasPair()) {

			LinkedList<Rank> thePairs = this.getRanksOfPairs();

			// Go through each pair and check if they are greater than eight
			for (Rank p : thePairs) {

				// If less than 8 then return false
				if (p.getValue() < Rank.Eight.getValue())
					return false;
			}

			// The hand is bettable so return true
			return true;

		}
		// The hand is not bettable
		else
			return false;
	}
}