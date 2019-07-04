package model;

import java.text.DecimalFormat;
import java.util.*;

/**
 * Player Class Controls the player, balance, 2 personal cards, and figures out
 * the players best hand
 * 
 * @author Jenna Franco, Richard Bosse
 * 
 */
public class Player {

	// Instance variables
	private static final int ALL_POSSIBILE_COMBINATIONS = 21;
	private static final int A_BET = 5;
	String playerName;
	double balance;
	List<Card> personal2Cards;
	// keeps track of best poker hand
	PokerHand bestHand;
	// keeps track of folding status
	boolean folded;
	// keeps track of winning status
	boolean isWinner;

	/**
	 * Player Constructor
	 * 
	 * @param name
	 *            : name of the player
	 */
	public Player(String name) {
		playerName = name;
		balance = 100.00;
		personal2Cards = new ArrayList<Card>();
		bestHand = new PokerHand();
		// initially player has neither folded, nor is a winner
		folded = false;
		isWinner = false;
	}

	/**
	 * Returns the balance of the player
	 * 
	 * @return the balance $XXX.XX
	 */
	public String getBalance() {
		// Creates the money format
		DecimalFormat moneyFormat = new DecimalFormat("'$'0.00");
		// Returns the balance in the money format
		return moneyFormat.format(balance);

	}

	/**
	 * Returns the players best poker hand
	 * 
	 * @return
	 */
	public PokerHand getHand() {
		return bestHand;
	}

	/**
	 * Returns the hand as a string
	 * 
	 * @return
	 */
	public String bestHandToString() {
		return bestHand.toString();
	}

	/**
	 * Returns the name of the player
	 * 
	 * @return
	 */
	public String getName() {
		return playerName;
	}

	/**
	 * Collects the Ante from the player
	 * 
	 * @param ante
	 *            : The amount to be taken
	 */
	public void collectAnte(int ante) {
		balance -= ante;
	}

	/**
	 * The player makes a bet which is removed from their balance
	 * 
	 */
	public void placeBet() {
		balance -= A_BET;
	}

	/**
	 * Gives the player a card
	 * 
	 * @param card
	 */
	public void setCard(Card card) {
		personal2Cards.add(card);
	}

	/**
	 * Returns the player's 2 cards as a string
	 * 
	 * @return
	 */
	public String getPlayer2Cards() {

		// String for the cards
		String twoCards = "";

		// Loops through the 2 cards and adds them to the string
		for (Card c : personal2Cards)
			twoCards += c.toString() + " ";

		// Returns the string
		return twoCards.trim();
	}

	/**
	 * Takes the community cards and figures the best 5 card hand from 7 by
	 * comparing all 21 possible unique combinations of player cards combined
	 * with community cards
	 * 
	 * @param communityCards
	 * @return
	 */
	public String setBestHand(List<Card> communityCards) {

		// Needed lists
		List<Card> allCards = new ArrayList<Card>();
		List<PokerHand> combinations = new LinkedList<PokerHand>();

		// Add the community cards to total cards
		for (Card c : communityCards) {
			allCards.add(c);
		}

		// Add the personal cards to the total cards
		for (Card c : personal2Cards) {
			allCards.add(c);
		}

		// All the different card combinations (21 in total)
		int[][] possibilites = { { 0, 1, 2, 3, 4 }, { 0, 1, 2, 3, 5 },
				{ 0, 1, 2, 3, 6 }, { 0, 1, 2, 4, 5 }, { 0, 1, 2, 4, 6 },
				{ 0, 1, 2, 5, 6 }, { 0, 1, 3, 4, 5 }, { 0, 1, 3, 4, 6 },
				{ 0, 1, 3, 5, 6 }, { 0, 1, 4, 5, 6 }, { 0, 2, 3, 4, 5 },
				{ 0, 2, 3, 4, 6 }, { 0, 2, 3, 5, 6 }, { 0, 2, 4, 5, 6 },
				{ 0, 3, 4, 5, 6 }, { 1, 2, 3, 4, 5 }, { 1, 2, 3, 4, 6 },
				{ 1, 2, 3, 5, 6 }, { 1, 2, 4, 5, 6 }, { 1, 3, 4, 5, 6 },
				{ 2, 3, 4, 5, 6 } };

		// Loops through each possibly
		for (int[] aPossibility : possibilites) {
			// Creates a poker hand with that possibility and adds it to
			// the combinations list
			combinations.add(new PokerHand(allCards.get(aPossibility[0]),
					allCards.get(aPossibility[1]), allCards
							.get(aPossibility[2]), allCards
							.get(aPossibility[3]), allCards
							.get(aPossibility[4])));

		}

		// Sorts the hands from least to greatest
		Collections.sort(combinations);

		// Stores the best hand (the last index of combinations)
		bestHand = combinations.get(ALL_POSSIBILE_COMBINATIONS - 1);

		// Returns the best hand
		return bestHand.toString();

	}

	/**
	 * Clears the cards from the hand
	 * 
	 */
	public void clearCards() {
		// Creates new lists to remove old cards
		bestHand = new PokerHand();
		personal2Cards = new ArrayList<Card>();
		isWinner = false;

	}

	/**
	 * The player gets the pot added to his balance
	 * 
	 * @param thePot
	 */
	public void givePot(double thePot) {
		balance += thePot;
	}

	/**
	 * Returns if the player has folded
	 * 
	 * @return
	 */
	public boolean hasFolded() {
		return folded;
	}

	public void foldHand() {
		setFolded(true);
	}

	/**
	 * The player decides to bet or not. The bot will bet if they have any kind
	 * of winning hand with good odds from a pair to a straight flush
	 * 
	 * @return
	 */
	public boolean makeBet() {

		if (this.bestHand.isBetable()) {
			this.placeBet();
			return true;
		} else {
			this.foldHand();
			return false;
		}
	}

	/**
	 * changes winner variable to true
	 * 
	 * @return
	 */
	public void setIsWinner() {
		isWinner = true;
	}

	/**
	 * returns winning status
	 * 
	 * @return
	 */
	public Boolean getIsWinner() {
		return isWinner;
	}

	/**
	 * sets folding status
	 * 
	 * @return
	 */
	public void setFolded(boolean b) {
		this.folded = b;

	}
}