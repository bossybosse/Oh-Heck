package model;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * GameTable class Controls
 * 
 * @author Jenna Franco, Richard Bosse
 * 
 */
public class GameTable {

	// Instance Variables
	private List<Player> playerList;
	private List<Player> theWinners;
	private List<Card> communityCards;
	private static final int ANTE = 2;
	private static final int A_BET = 5;
	private double thePot;
	private Dealer theDealer;

	/**
	 * Constructor for GameTable
	 * 
	 */
	public GameTable() {

		// Initializes all the instance variables
		playerList = new ArrayList<Player>();
		theDealer = new Dealer();
		thePot = 0;
		communityCards = new ArrayList<Card>();
		theWinners = new ArrayList<Player>();

	}

	/**
	 * Add a player to the game
	 * 
	 * @param name
	 *            : the name of your player
	 */
	public void setPlayer(String player) {

		// Creates the new player with specified name
		Player newPlayer = new Player(player);
		// Adds the player to the player list
		playerList.add(newPlayer);

	}

	/**
	 * Add a player object to the game
	 * 
	 * @param newplayer
	 *            : player object
	 */
	public void setPlayer(Player newplayer) {
		playerList.add(newplayer);
	}

	/**
	 * Starts the game by collecting the ante and dealing the cards
	 * 
	 */
	public void startGame() {
		// Collects the ante
		collectAnte();

		// Deal all the cards
		theDealer.dealCards(playerList, communityCards);

		// Set the players hands best hands
		setPlayersBestHands();
	}

	/**
	 * Get the player name at the given index
	 * 
	 * @param indexNum
	 * @return : the players name
	 */
	public String getPlayerName(int index) {
		return playerList.get(index).getName();
	}

	/**
	 * Collects $2 from each player's balance for the ante
	 * 
	 */
	private void collectAnte() {

		// Loops through each player
		for (Player player : playerList) {
			// Collects the ante
			player.collectAnte(ANTE);
			// Stores the ante in the pot
			thePot += ANTE;
		}
	}

	/**
	 * Creates to string of all the community cards
	 * 
	 * @return
	 */
	public String getCommunityCards() {
		// String for the cards
		String communityCards = "";
		// Loops through all the cards and adds them to the string
		for (Card c : this.communityCards) {
			communityCards += c.toString() + " ";
		}
		// Returns the string
		return communityCards.trim();
	}

	/**
	 * Returns the string balance display of the player at the giving index
	 * 
	 * @param indexNum
	 * @return $XXX
	 */
	public String getPlayersBalance(int index) {
		return playerList.get(index).getBalance();
	}

	/**
	 * Returns the to string of two cards dealt by the dealer
	 * 
	 * @param indexNum
	 * @return
	 */
	public String getPlayersCards(int index) {
		return playerList.get(index).getPlayer2Cards();
	}

	/**
	 * Sets the best hand attribute for the player
	 * 
	 * @param indexNum
	 * @return
	 */
	private void setPlayersBestHands() {

		// Loop through all the players and set the best hand
		for (Player player : playerList) {
			player.setBestHand(communityCards);
		}
	}

	/**
	 * Returns the to string of player hand at the given index
	 * 
	 * @param index
	 * @return
	 */
	public String getPlayersBestHand(int index) {
		return playerList.get(index).bestHandToString();
	}

	/**
	 * Computes who the winner is and gives them the winnings
	 * 
	 */
	private void giveWinnertheWinnings() {
		// Splits the winnings based on the number of winners
		double theWinnings = thePot / theWinners.size();

		// Loops through all the winners and gives them their share
		for (Player player : theWinners) {
			player.givePot(theWinnings);
		}

		// Reset the pot
		this.thePot = 0;

	}

	/**
	 * Returns a string displaying the winner's best hand and balance used for
	 * testing purposes
	 * 
	 * @param index
	 * 
	 * @return
	 */
	public String getWinningPlayersStats() {

		String result = "";
		// if only one winner, print stats
		if (theWinners.size() == 1) {
			result += "Winning hand" + "\n" + theWinners.get(0).getName()
					+ ": " + theWinners.get(0).getHand().toString() + " "
					+ theWinners.get(0).getBalance() + "\n";
		}

		else {

			result += "Winning hands (tie)" + "\n";
			// if multiple winners loop through each winner's stats
			for (Player player : theWinners) {
				result += player.getName() + ": " + player.getHand().toString()
						+ " " + player.getBalance() + "\n";
			}
		}
		return result;

	}

	/**
	 * Stores boolean values of players' winning status in an array
	 * 
	 */
	public Boolean[] getWinningPlayersBooleanArray() {

		Boolean[] temp = new Boolean[4];
		int i = 0;
		for (Player p : playerList) {
			temp[i] = p.getIsWinner();
			i++;
		}
		return temp;

	}

	/**
	 * Determines who is the winner of the hand
	 * 
	 */
	public void setWinner() {

		// An array of all the hands
		ArrayList<PokerHand> allHands = new ArrayList<PokerHand>();
		theWinners = new ArrayList<Player>();

		// Gets every hand from every player
		for (Player player : playerList) {
			if (!player.hasFolded())
				allHands.add(player.getHand());
		}

		// If everyone folds, there are no winners so money stays in pot
		if (allHands.size() > 0) {

			// Sorts the hands based on rank from least to greatest
			Collections.sort(allHands);

			// Gets the last index
			int lastIndex = allHands.size() - 1;

			// If the players hands is a winning hand, add them to theWinners
			// list
			for (Player player : playerList) {
				// Compares the players hand to the best community hand
				if (player.getHand().compareTo(allHands.get(lastIndex)) == 0) {
					theWinners.add(player);
					player.setIsWinner();
				}

			}

			// Give out the winnings
			giveWinnertheWinnings();

		}
	}

	/**
	 * Resets the board for another hand
	 * 
	 */
	public void resetBoard() {

		// Remove all the used cards
		theDealer.removeUsedDeck(playerList);
		communityCards = new ArrayList<Card>();
		for (Player p : playerList) {
			p.setFolded(false);
		}
		// Remove the winners from the winners list
		theWinners = new ArrayList<Player>();
	}

	/**
	 * Gets the pot amount
	 */
	public String getPot() {
		DecimalFormat moneyFormat = new DecimalFormat("'$'0.00");
		return moneyFormat.format(thePot);
	}

	/**
	 * Loops through each bot; each either folds or bets
	 */
	public void betting() {

		for (Player p : playerList) {
			// if bot bets, put their bet into the pot
			if (!p.getName().equals("Player") && p.makeBet()) {
				thePot += A_BET;
			}
		}

	}

	/**
	 * returns boolean of player's folding status
	 */
	public boolean getIsPlayerFolded(int index) {
		return playerList.get(index).hasFolded();
	}

	/**
	 * lets user at a specified index in playerList fold
	 */

	public void userFold(int index) {
		playerList.get(index).foldHand();

	}

	/**
	 * Lets the player bet
	 */
	public void userBet(int index) {
		// places bet into pot
		playerList.get(index).placeBet();
		thePot += A_BET;

	}
}