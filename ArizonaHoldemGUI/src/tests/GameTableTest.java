package tests;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import model.*;

import org.junit.Test;

/**
 * Tests the game table class
 * 
 * @author Richard Bosse, Jenna Franco
 * 
 */
public class GameTableTest {

	// ************************GameTable Tests*****************************

	/**
	 * Tests that the table can create a player, start a game, and take ante
	 * 
	 */
	@Test
	public void testGameTableConstructor() {
		GameTable table = new GameTable();
		Player bot3 = new Player("Bot 3");

		table.setPlayer("Player");
		table.setPlayer("Bot 1");
		table.setPlayer("Bot 2");
		table.setPlayer(bot3);

		assertTrue("Player".equals(table.getPlayerName(0)));
		assertTrue("$100.00".equals(table.getPlayersBalance(0)));
		assertTrue("".equals(table.getPlayersCards(0)));

		assertTrue("Bot 1".equals(table.getPlayerName(1)));
		assertTrue("$100.00".equals(table.getPlayersBalance(1)));
		assertTrue("".equals(table.getPlayersCards(1)));

		assertTrue("Bot 2".equals(table.getPlayerName(2)));
		assertTrue("$100.00".equals(table.getPlayersBalance(2)));
		assertTrue("".equals(table.getPlayersCards(2)));

		assertTrue("Bot 3".equals(table.getPlayerName(3)));
		assertTrue("$100.00".equals(table.getPlayersBalance(3)));
		assertTrue("".equals(table.getPlayersCards(3)));

		assertTrue("".equals(table.getCommunityCards()));
		table.startGame();

		for (int i = 0; i < 4; i++) {
			assertTrue("$98.00".equals(table.getPlayersBalance(i)));
			assertTrue(!"".equals(table.getPlayersCards(i)));
		}

	}

	/**
	 * Tests if a table can get a players best hand
	 */
	@Test
	public void testGameTableGetBestHand() {

		GameTable table = new GameTable();
		table.setPlayer("Jenna");
		assertTrue("".equals(table.getPlayersBestHand(0)));

		table.startGame();

		assertTrue(!"".equals(table.getPlayersBestHand(0)));

	}

	/**
	 * Test if a table can set a winner
	 */
	@Test
	public void testSetWinners() {
		GameTable table = new GameTable();
		Player jenna = new Player("Jenna");
		Player richard = new Player("richard");
		Player abby = new Player("abby");
		List<Card> communityCards = new ArrayList<Card>();

		communityCards.add(H4);
		communityCards.add(C5);
		communityCards.add(HA);
		communityCards.add(D8);
		communityCards.add(DK);

		jenna.setCard(CA);
		jenna.setCard(SA);
		jenna.setBestHand(communityCards);
		richard.setCard(S2);
		richard.setCard(C2);
		richard.setBestHand(communityCards);
		abby.setCard(C10);
		abby.setCard(C9);
		abby.setBestHand(communityCards);

		table.setPlayer(jenna);
		table.setPlayer(richard);
		table.setPlayer(abby);
		table.setWinner();

		String winner = table.getWinningPlayersStats();

		assertTrue("Winning hand\nJenna: D8 DK SA CA HA $100.00\n"
				.equals(winner));

	}

	/**
	 * Tests if a table can set multiple winners
	 */
	@Test
	public void testSetMultipleWinners() {

		GameTable table = new GameTable();
		Player jenna = new Player("Jenna");
		Player richard = new Player("richard");
		Player abby = new Player("abby");
		Player billy = new Player("Billy");
		Player bob = new Player("Bob");
		List<Card> communityCards = new ArrayList<Card>();
		table.setPlayer(jenna);
		table.setPlayer(richard);
		table.setPlayer(abby);
		table.setPlayer(billy);
		table.setPlayer(bob);

		// Straight flush, also tests flush
		communityCards.add(HA);
		communityCards.add(HK);
		communityCards.add(HQ);
		communityCards.add(HJ);
		communityCards.add(H10);
		jenna.setCard(D8);
		jenna.setCard(CA);
		jenna.setBestHand(communityCards);
		richard.setCard(H8);
		richard.setCard(C5);
		richard.setBestHand(communityCards);
		abby.setCard(C8);
		abby.setCard(D4);
		abby.setBestHand(communityCards);
		billy.setCard(D2);
		billy.setCard(H4);
		billy.setBestHand(communityCards);
		bob.setCard(S3);
		bob.setCard(C2);
		bob.setBestHand(communityCards);

		table.setWinner();
		String winner = table.getWinningPlayersStats();
		assertTrue("Winning hands (tie)\nJenna: H10 HJ HQ HK HA $100.00\nrichard: H10 HJ HQ HK HA $100.00\nabby: H10 HJ HQ HK HA $100.00\nBilly: H10 HJ HQ HK HA $100.00\nBob: H10 HJ HQ HK HA $100.00\n"
				.equals(winner));
		table.resetBoard();
		communityCards = new ArrayList<Card>();

		// Straight
		communityCards.add(D6);
		communityCards.add(H7);
		communityCards.add(C9);
		communityCards.add(H10);
		communityCards.add(CJ);
		jenna.setCard(D8);
		jenna.setCard(HA);
		jenna.setBestHand(communityCards);
		richard.setCard(H8);
		richard.setCard(C5);
		richard.setBestHand(communityCards);
		abby.setCard(C8);
		abby.setCard(D4);
		abby.setBestHand(communityCards);
		billy.setCard(D2);
		billy.setCard(H4);
		billy.setBestHand(communityCards);
		bob.setCard(S3);
		bob.setCard(C2);
		bob.setBestHand(communityCards);

		table.setWinner();
		winner = table.getWinningPlayersStats();
		assertTrue("Winning hands (tie)\nJenna: H7 D8 C9 H10 CJ $100.00\nrichard: H7 H8 C9 H10 CJ $100.00\nabby: H7 C8 C9 H10 CJ $100.00\n"
				.equals(winner));
		table.resetBoard();
		communityCards = new ArrayList<Card>();

		// 4 kind
		communityCards.add(H9);
		communityCards.add(C9);
		communityCards.add(S9);
		communityCards.add(D9);
		communityCards.add(D7);
		jenna.setCard(CK);
		jenna.setCard(S4);
		jenna.setBestHand(communityCards);
		richard.setCard(SK);
		richard.setCard(C4);
		richard.setBestHand(communityCards);
		abby.setCard(HK);
		abby.setCard(H4);
		abby.setBestHand(communityCards);
		billy.setCard(S2);
		billy.setCard(H10);
		billy.setBestHand(communityCards);
		bob.setCard(S4);
		bob.setCard(S6);
		bob.setBestHand(communityCards);

		table.setWinner();
		winner = table.getWinningPlayersStats();
		assertTrue("Winning hands (tie)\nJenna: D9 S9 C9 H9 CK $100.00\nrichard: D9 S9 C9 H9 SK $100.00\nabby: D9 S9 C9 H9 HK $100.00\n"
				.equals(winner));
		table.resetBoard();
		communityCards = new ArrayList<Card>();

		// Full House
		communityCards.add(H9);
		communityCards.add(C9);
		communityCards.add(S9);
		communityCards.add(D8);
		communityCards.add(DK);
		jenna.setCard(CK);
		jenna.setCard(S7);
		jenna.setBestHand(communityCards);
		richard.setCard(SK);
		richard.setCard(C4);
		richard.setBestHand(communityCards);
		abby.setCard(HK);
		abby.setCard(H3);
		abby.setBestHand(communityCards);
		billy.setCard(S2);
		billy.setCard(H10);
		billy.setBestHand(communityCards);
		bob.setCard(D7);
		bob.setCard(S6);
		bob.setBestHand(communityCards);

		table.setWinner();
		winner = table.getWinningPlayersStats();
		assertTrue("Winning hands (tie)\nJenna: S9 C9 H9 CK DK $100.00\nrichard: S9 C9 H9 SK DK $100.00\nabby: S9 C9 H9 HK DK $100.00\n"
				.equals(winner));
		table.resetBoard();
		communityCards = new ArrayList<Card>();

		// 3 kind
		communityCards.add(H9);
		communityCards.add(C9);
		communityCards.add(S9);
		communityCards.add(D8);
		communityCards.add(D7);
		jenna.setCard(CK);
		jenna.setCard(S4);
		jenna.setBestHand(communityCards);
		richard.setCard(SK);
		richard.setCard(C4);
		richard.setBestHand(communityCards);
		abby.setCard(HK);
		abby.setCard(H4);
		abby.setBestHand(communityCards);
		billy.setCard(S2);
		billy.setCard(H10);
		billy.setBestHand(communityCards);
		bob.setCard(S4);
		bob.setCard(S6);
		bob.setBestHand(communityCards);

		table.setWinner();
		winner = table.getWinningPlayersStats();
		assertTrue("Winning hands (tie)\nJenna: D8 S9 C9 H9 CK $100.00\nrichard: D8 S9 C9 H9 SK $100.00\nabby: D8 S9 C9 H9 HK $100.00\n"
				.equals(winner));
		table.resetBoard();
		communityCards = new ArrayList<Card>();

		// 2 pair, community has both
		communityCards.add(H9);
		communityCards.add(C9);
		communityCards.add(S10);
		communityCards.add(D10);
		communityCards.add(D7);
		jenna.setCard(CK);
		jenna.setCard(S4);
		jenna.setBestHand(communityCards);
		richard.setCard(SK);
		richard.setCard(C4);
		richard.setBestHand(communityCards);
		abby.setCard(HK);
		abby.setCard(H4);
		abby.setBestHand(communityCards);
		billy.setCard(SA);
		billy.setCard(H2);
		billy.setBestHand(communityCards);
		bob.setCard(DA);
		bob.setCard(S6);
		bob.setBestHand(communityCards);

		table.setWinner();
		winner = table.getWinningPlayersStats();
		assertTrue("Winning hands (tie)\nBilly: C9 H9 D10 S10 SA $100.00\nBob: C9 H9 D10 S10 DA $100.00\n"
				.equals(winner));
		table.resetBoard();
		communityCards = new ArrayList<Card>();

		// 2 pair, player has one, community has one
		communityCards.add(H9);
		communityCards.add(C9);
		communityCards.add(S2);
		communityCards.add(D7);
		communityCards.add(D6);
		jenna.setCard(CK);
		jenna.setCard(HK);
		jenna.setBestHand(communityCards);
		richard.setCard(SK);
		richard.setCard(DK);
		richard.setBestHand(communityCards);
		abby.setCard(H7);
		abby.setCard(H4);
		abby.setBestHand(communityCards);
		billy.setCard(SA);
		billy.setCard(H2);
		billy.setBestHand(communityCards);
		bob.setCard(DA);
		bob.setCard(S6);
		bob.setBestHand(communityCards);

		table.setWinner();
		winner = table.getWinningPlayersStats();
		assertTrue("Winning hands (tie)\nJenna: D7 C9 H9 HK CK $100.00\nrichard: D7 C9 H9 DK SK $100.00\n"
				.equals(winner));
		table.resetBoard();
		communityCards = new ArrayList<Card>();

		// pair
		communityCards.add(H5);
		communityCards.add(C3);
		communityCards.add(S2);
		communityCards.add(D7);
		communityCards.add(D6);
		jenna.setCard(CK);
		jenna.setCard(HK);
		jenna.setBestHand(communityCards);
		richard.setCard(SK);
		richard.setCard(DK);
		richard.setBestHand(communityCards);
		abby.setCard(H7);
		abby.setCard(H9);
		abby.setBestHand(communityCards);
		billy.setCard(SA);
		billy.setCard(H3);
		billy.setBestHand(communityCards);
		bob.setCard(DA);
		bob.setCard(S6);
		bob.setBestHand(communityCards);

		table.setWinner();
		winner = table.getWinningPlayersStats();
		assertTrue("Winning hands (tie)\nJenna: H5 D6 D7 HK CK $100.00\nrichard: H5 D6 D7 DK SK $100.00\n"
				.equals(winner));
		table.resetBoard();
		communityCards = new ArrayList<Card>();

		// high card
		communityCards.add(H5);
		communityCards.add(C3);
		communityCards.add(S2);
		communityCards.add(D7);
		communityCards.add(D6);
		jenna.setCard(CK);
		jenna.setCard(HA);
		jenna.setBestHand(communityCards);
		richard.setCard(SA);
		richard.setCard(DK);
		richard.setBestHand(communityCards);
		abby.setCard(HQ);
		abby.setCard(H9);
		abby.setBestHand(communityCards);
		billy.setCard(SA);
		billy.setCard(DQ);
		billy.setBestHand(communityCards);
		bob.setCard(DA);
		bob.setCard(SQ);
		bob.setBestHand(communityCards);

		table.setWinner();
		winner = table.getWinningPlayersStats();
		assertTrue("Winning hands (tie)\nJenna: H5 D6 D7 CK HA $100.00\nrichard: H5 D6 D7 DK SA $100.00\n"
				.equals(winner));
		table.resetBoard();
		communityCards = new ArrayList<Card>();

	}

	/**
	 * Tests the getComunityCards
	 */
	@Test
	public void testgetCommunityCards() {
		GameTable table = new GameTable();
		assertTrue("".equals(table.getCommunityCards()));

		table.startGame();

		assertTrue(!"".equals(table.getCommunityCards()));

	}

	/**
	 * Tests that the bots all place bets or not
	 */
	@Test
	public void testBetting() {

		// Create the game and players
		GameTable game = new GameTable();
		Player a = new Player("a");
		Player b = new Player("b");
		Player c = new Player("c");
		Player d = new Player("d");
		game.setPlayer(a);
		game.setPlayer(b);
		game.setPlayer(c);
		game.setPlayer(d);

		// Show the balance didnt change
		assertTrue("$100.00".equals(a.getBalance()));
		assertTrue("$100.00".equals(b.getBalance()));
		assertTrue("$100.00".equals(c.getBalance()));
		assertTrue("$100.00".equals(d.getBalance()));

		// Create the community Cards
		List<Card> communityCards = new ArrayList<Card>();
		communityCards.add(H4);
		communityCards.add(C5);
		communityCards.add(HA);
		communityCards.add(D8);
		communityCards.add(DK);

		// Set the player cards
		a.setCard(CA);
		a.setCard(CK);
		b.setCard(S2);
		b.setCard(D2);
		c.setCard(D9);
		c.setCard(H8);
		d.setCard(H3);
		d.setCard(DA);
		a.setBestHand(communityCards);
		b.setBestHand(communityCards);
		c.setBestHand(communityCards);
		d.setBestHand(communityCards);

		// Do the betting
		game.betting();

		// The tests
		assertTrue(!a.hasFolded());
		game.userFold(1);
		assertTrue(b.hasFolded());
		assertTrue(!c.hasFolded());
		assertTrue(!d.hasFolded());
		assertTrue("$95.00".equals(a.getBalance()));
		assertTrue("$100.00".equals(b.getBalance()));
		assertTrue("$95.00".equals(c.getBalance()));
		assertTrue("$95.00".equals(d.getBalance()));
		game.userBet(0);
		assertTrue("$90.00".equals(a.getBalance()));

		assertTrue(!"$0.00".equals(game.getPot()));

	}

	/**
	 * Tests that the bots all place bets or not
	 */
	@Test
	public void testWinning() {

		// Create the game and players
		GameTable game = new GameTable();
		Player a = new Player("a");
		Player b = new Player("b");
		Player c = new Player("c");
		Player d = new Player("d");
		game.setPlayer(a);
		game.setPlayer(b);
		game.setPlayer(c);
		game.setPlayer(d);
		game.startGame();

		game.userFold(0);
		game.userFold(1);
		game.userFold(2);
		game.userFold(3);
		assertTrue(game.getIsPlayerFolded(0) == true);
		Boolean[] wins = game.getWinningPlayersBooleanArray();
		assertTrue(wins[0] == false);
		assertTrue("$8.00".equals(game.getPot())); // no one can be a winner
													// since they all fold so
													// the money stays in the
													// pot
	}

	// ********************all cards*************************
	private final static Card C2 = new Card(Rank.Deuce, Suit.Clubs);
	private final static Card C3 = new Card(Rank.Three, Suit.Clubs);
	private final static Card C4 = new Card(Rank.Four, Suit.Clubs);
	private final static Card C5 = new Card(Rank.Five, Suit.Clubs);
	private final static Card C8 = new Card(Rank.Eight, Suit.Clubs);
	private final static Card C9 = new Card(Rank.Nine, Suit.Clubs);
	private final static Card C10 = new Card(Rank.Ten, Suit.Clubs);
	private final static Card CJ = new Card(Rank.Jack, Suit.Clubs);
	private final static Card CK = new Card(Rank.King, Suit.Clubs);
	private final static Card CA = new Card(Rank.Ace, Suit.Clubs);

	private final static Card D2 = new Card(Rank.Deuce, Suit.Diamonds);
	private final static Card D4 = new Card(Rank.Four, Suit.Diamonds);
	private final static Card D6 = new Card(Rank.Six, Suit.Diamonds);
	private final static Card D7 = new Card(Rank.Seven, Suit.Diamonds);
	private final static Card D8 = new Card(Rank.Eight, Suit.Diamonds);
	private final static Card D9 = new Card(Rank.Nine, Suit.Diamonds);
	private final static Card D10 = new Card(Rank.Ten, Suit.Diamonds);
	private final static Card DQ = new Card(Rank.Queen, Suit.Diamonds);
	private final static Card DK = new Card(Rank.King, Suit.Diamonds);
	private final static Card DA = new Card(Rank.Ace, Suit.Diamonds);

	private final static Card H2 = new Card(Rank.Deuce, Suit.Hearts);
	private final static Card H3 = new Card(Rank.Three, Suit.Hearts);
	private final static Card H4 = new Card(Rank.Four, Suit.Hearts);
	private final static Card H5 = new Card(Rank.Five, Suit.Hearts);
	private final static Card H7 = new Card(Rank.Seven, Suit.Hearts);
	private final static Card H8 = new Card(Rank.Eight, Suit.Hearts);
	private final static Card H9 = new Card(Rank.Nine, Suit.Hearts);
	private final static Card H10 = new Card(Rank.Ten, Suit.Hearts);
	private final static Card HJ = new Card(Rank.Jack, Suit.Hearts);
	private final static Card HQ = new Card(Rank.Queen, Suit.Hearts);
	private final static Card HK = new Card(Rank.King, Suit.Hearts);
	private final static Card HA = new Card(Rank.Ace, Suit.Hearts);

	private final static Card S2 = new Card(Rank.Deuce, Suit.Spades);
	private final static Card S3 = new Card(Rank.Three, Suit.Spades);
	private final static Card S4 = new Card(Rank.Four, Suit.Spades);
	private final static Card S6 = new Card(Rank.Six, Suit.Spades);
	private final static Card S7 = new Card(Rank.Seven, Suit.Spades);
	private final static Card S9 = new Card(Rank.Nine, Suit.Spades);
	private final static Card S10 = new Card(Rank.Ten, Suit.Spades);
	private final static Card SQ = new Card(Rank.Queen, Suit.Spades);
	private final static Card SK = new Card(Rank.King, Suit.Spades);
	private final static Card SA = new Card(Rank.Ace, Suit.Spades);


}
