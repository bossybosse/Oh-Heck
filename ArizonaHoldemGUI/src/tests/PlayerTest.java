package tests;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import model.Card;
import model.Player;
import model.PokerHand;
import model.Rank;
import model.Suit;

import org.junit.Test;

/**
 * Tests the player class
 * 
 * @author Richard Bosse, Jenna Franco
 * 
 */
public class PlayerTest {

	// ************************Player Class Tests*****************************

	private static final int THE_ANTE = 2;
	private static final double SPLIT_POT1 = 8.0 / 3.0;
	private static final double SPLIT_POT2 = 12.0 / 5.0;

	/**
	 * Tests if players are created
	 * 
	 */
	@Test
	public void testCreatePlayer() {
		Player jenna = new Player("Jenna");
		assertTrue("Jenna".equals(jenna.getName()));
		assertTrue("$100.00".equals(jenna.getBalance()));
		assertTrue("".equals(jenna.getPlayer2Cards()));
	}

	/**
	 * Tests if the ante are taken from the players
	 */
	@Test
	public void testGetAnte() {
		// Create player
		Player jenna = new Player("Jenna");
		assertTrue("$100.00".equals(jenna.getBalance()));

		// Tests if the ante is taken
		jenna.collectAnte(THE_ANTE);
		assertTrue("$98.00".equals(jenna.getBalance()));

		jenna.collectAnte(THE_ANTE);
		assertTrue("$96.00".equals(jenna.getBalance()));

		jenna.collectAnte(THE_ANTE);
		assertTrue("$94.00".equals(jenna.getBalance()));

		jenna.collectAnte(THE_ANTE);
		assertTrue("$92.00".equals(jenna.getBalance()));
	}

	/**
	 * Tests if the player can make a bet by removing 5 from balance
	 */
	@Test
	public void testPlaceBet() {
		Player testPlayer = new Player("Player");
		assertTrue("$100.00".equals(testPlayer.getBalance()));
		testPlayer.collectAnte(THE_ANTE);
		assertTrue("$98.00".equals(testPlayer.getBalance()));
		testPlayer.placeBet();
		assertTrue("$93.00".equals(testPlayer.getBalance()));
	}

	/**
	 * Tests if a player can fold
	 * 
	 */
	@Test
	public void testFold() {
		Player testPlayer = new Player("Player");
		assertTrue(!testPlayer.hasFolded());
		testPlayer.foldHand();
		assertTrue(testPlayer.hasFolded());
	}

	/**
	 * Test reseting the player
	 */
	@Test
	public void testPlayerReset() {
		Player testPlayer = new Player("Player");
		testPlayer.setCard(CA);
		testPlayer.setCard(CK);
		String cards = testPlayer.getPlayer2Cards();
		assertTrue("CA CK".equals(cards));

	}

	/**
	 * Tests if the player can receive the pot
	 * 
	 */
	@Test
	public void testGivePot() {
		Player jenna = new Player("Jenna");
		assertTrue("$100.00".equals(jenna.getBalance()));
		jenna.collectAnte(THE_ANTE);
		assertTrue("$98.00".equals(jenna.getBalance()));
		jenna.givePot(THE_ANTE);
		assertTrue("$100.00".equals(jenna.getBalance()));
		jenna.collectAnte(THE_ANTE);
		assertTrue("$98.00".equals(jenna.getBalance()));
		jenna.givePot(SPLIT_POT1);
		assertTrue("$100.67".equals(jenna.getBalance()));
		jenna.collectAnte(THE_ANTE);
		assertTrue("$98.67".equals(jenna.getBalance()));
		jenna.givePot(SPLIT_POT2);
		assertTrue("$101.07".equals(jenna.getBalance()));

	}

	/**
	 * Tests if the player can take in cards
	 */
	@Test
	public void test2Cards() {
		Player jenna = new Player("Jenna");
		jenna.setCard(C2);
		assertTrue("C2".equals(jenna.getPlayer2Cards()));

		jenna.setCard(C3);
		assertTrue("C2 C3".equals(jenna.getPlayer2Cards()));
	}

	/**
	 * Tests if the player and print their hand
	 * 
	 */
	@Test
	public void testGetHand() {
		Player jenna = new Player("Jenna");
		jenna.setCard(CA);
		jenna.setCard(C2);

		ArrayList<Card> communityCards = new ArrayList<Card>();

		communityCards.add(C3);
		communityCards.add(C4);
		communityCards.add(C5);
		communityCards.add(C9);
		communityCards.add(C10);

		jenna.setBestHand(communityCards);
		PokerHand pokerHand = jenna.getHand();
		String temp = pokerHand.toString();
		assertTrue("C2 C3 C4 C5 CA".equals(temp.toString()));

		assertTrue("C2 C3 C4 C5 CA".equals(jenna.bestHandToString()));

	}

	/**
	 * Tests if player can find straight flush
	 * 
	 */
	@Test
	public void testGetBestHandStraightFlush() {

		Player jenna = new Player("Jenna");
		List<Card> communityCards = new ArrayList<Card>();
		String bestHand = "";

		// Straight Flush high card in player hand
		jenna.setCard(CA);
		jenna.setCard(C10);
		communityCards.add(CK);
		communityCards.add(CJ);
		communityCards.add(CQ);
		communityCards.add(H2);
		communityCards.add(S2);
		bestHand = jenna.setBestHand(communityCards);
		assertTrue("C10 CJ CQ CK CA".equals(bestHand));
		assertTrue(bestHand.equals(jenna.getHand().toString()));

		jenna.clearCards();
		communityCards = new ArrayList<Card>();
		bestHand = "";

		// Straight flush high card in community
		jenna.setCard(CK);
		jenna.setCard(C10);
		communityCards.add(CA);
		communityCards.add(CJ);
		communityCards.add(CQ);
		communityCards.add(H2);
		communityCards.add(S2);
		bestHand = jenna.setBestHand(communityCards);
		assertTrue("C10 CJ CQ CK CA".equals(bestHand));
		assertTrue(bestHand.equals(jenna.getHand().toString()));

		jenna.clearCards();
		communityCards = new ArrayList<Card>();
		bestHand = "";

		// All cards form straight flush
		jenna.setCard(CK);
		jenna.setCard(CA);
		communityCards.add(C10);
		communityCards.add(CJ);
		communityCards.add(CQ);
		communityCards.add(H9);
		communityCards.add(S8);
		bestHand = jenna.setBestHand(communityCards);
		assertTrue("C10 CJ CQ CK CA".equals(bestHand));
		assertTrue(bestHand.equals(jenna.getHand().toString()));

		jenna.clearCards();
		communityCards = new ArrayList<Card>();
		bestHand = "";

		// All cards form straight flush community cards have high card
		jenna.setCard(C9);
		jenna.setCard(C8);
		communityCards.add(C10);
		communityCards.add(CJ);
		communityCards.add(CQ);
		communityCards.add(CA);
		communityCards.add(CK);
		bestHand = jenna.setBestHand(communityCards);
		assertTrue("C10 CJ CQ CK CA".equals(bestHand));
		assertTrue(bestHand.equals(jenna.getHand().toString()));

		jenna.clearCards();
		communityCards = new ArrayList<Card>();
		bestHand = "";

		// Straight flush community
		jenna.setCard(S3);
		jenna.setCard(S2);
		communityCards.add(C10);
		communityCards.add(CJ);
		communityCards.add(CQ);
		communityCards.add(CA);
		communityCards.add(CK);
		bestHand = jenna.setBestHand(communityCards);
		assertTrue("C10 CJ CQ CK CA".equals(bestHand));
		assertTrue(bestHand.equals(jenna.getHand().toString()));

		jenna.clearCards();
		communityCards = new ArrayList<Card>();
		bestHand = "";
	}

	/**
	 * Tests if player can find flush
	 * 
	 */
	@Test
	public void testGetBestHandFlush() {

		Player jenna = new Player("Jenna");
		List<Card> communityCards = new ArrayList<Card>();
		String bestHand = "";

		// Flush: user has high card, only 5 of that suit
		jenna.setCard(SA);
		jenna.setCard(SK);
		communityCards.add(S3);
		communityCards.add(HK);
		communityCards.add(DK);
		communityCards.add(SQ);
		communityCards.add(S4);
		bestHand = jenna.setBestHand(communityCards);
		assertTrue("S3 S4 SQ SK SA".equals(bestHand));
		assertTrue(bestHand.equals(jenna.getHand().toString()));

		jenna.clearCards();
		communityCards = new ArrayList<Card>();
		bestHand = "";

		// Flush: community has high card, only 5 of that suit
		jenna.setCard(S3);
		jenna.setCard(SK);
		communityCards.add(SA);
		communityCards.add(HK);
		communityCards.add(DK);
		communityCards.add(SQ);
		communityCards.add(S4);
		bestHand = jenna.setBestHand(communityCards);
		assertTrue("S3 S4 SQ SK SA".equals(bestHand));
		assertTrue(bestHand.equals(jenna.getHand().toString()));

		jenna.clearCards();
		communityCards = new ArrayList<Card>();
		bestHand = "";

		// Flush: user has high card, 6 of that suit
		jenna.setCard(SA);
		jenna.setCard(SK);
		communityCards.add(S3);
		communityCards.add(S2);
		communityCards.add(DK);
		communityCards.add(SQ);
		communityCards.add(S4);
		bestHand = jenna.setBestHand(communityCards);
		assertTrue("S3 S4 SQ SK SA".equals(bestHand));
		assertTrue(bestHand.equals(jenna.getHand().toString()));

		jenna.clearCards();
		communityCards = new ArrayList<Card>();
		bestHand = "";

		// Flush: community has high card, 6 of that suit
		jenna.setCard(S2);
		jenna.setCard(SK);
		communityCards.add(S3);
		communityCards.add(SA);
		communityCards.add(DK);
		communityCards.add(SQ);
		communityCards.add(S4);
		bestHand = jenna.setBestHand(communityCards);
		assertTrue("S3 S4 SQ SK SA".equals(bestHand));
		assertTrue(bestHand.equals(jenna.getHand().toString()));

		jenna.clearCards();
		communityCards = new ArrayList<Card>();
		bestHand = "";

		// Flush: user has high card, all 7 of that suit
		jenna.setCard(SA);
		jenna.setCard(SK);
		communityCards.add(S3);
		communityCards.add(S2);
		communityCards.add(S9);
		communityCards.add(SQ);
		communityCards.add(S4);
		bestHand = jenna.setBestHand(communityCards);
		assertTrue("S4 S9 SQ SK SA".equals(bestHand));
		assertTrue(bestHand.equals(jenna.getHand().toString()));

		jenna.clearCards();
		communityCards = new ArrayList<Card>();
		bestHand = "";

		// Flush: community has high card, all 7 of that suit
		jenna.setCard(S9);
		jenna.setCard(SK);
		communityCards.add(S3);
		communityCards.add(S2);
		communityCards.add(SA);
		communityCards.add(SQ);
		communityCards.add(S4);
		bestHand = jenna.setBestHand(communityCards);
		assertTrue("S4 S9 SQ SK SA".equals(bestHand));
		assertTrue(bestHand.equals(jenna.getHand().toString()));

		jenna.clearCards();
		communityCards = new ArrayList<Card>();
		bestHand = "";
	}

	/**
	 * Tests if player can find straight
	 * 
	 */
	@Test
	public void testGetBestHandStraight() {

		Player jenna = new Player("Jenna");
		List<Card> communityCards = new ArrayList<Card>();
		String bestHand = "";

		// User has high card, only 5 cards make a straight
		jenna.setCard(S9);
		jenna.setCard(DK);
		communityCards.add(S8);
		communityCards.add(H6);
		communityCards.add(D7);
		communityCards.add(C5);
		communityCards.add(S2);
		bestHand = jenna.setBestHand(communityCards);
		assertTrue("C5 H6 D7 S8 S9".equals(bestHand));
		assertTrue(bestHand.equals(jenna.getHand().toString()));

		jenna.clearCards();
		communityCards = new ArrayList<Card>();
		bestHand = "";

		// Community has high card, only 5 cards make a straight
		jenna.setCard(S8);
		jenna.setCard(DK);
		communityCards.add(S9);
		communityCards.add(H6);
		communityCards.add(D7);
		communityCards.add(C5);
		communityCards.add(S2);
		bestHand = jenna.setBestHand(communityCards);
		assertTrue("C5 H6 D7 S8 S9".equals(bestHand));
		assertTrue(bestHand.equals(jenna.getHand().toString()));

		jenna.clearCards();
		communityCards = new ArrayList<Card>();
		bestHand = "";

		// User has high card, 6 out 7 cards make a straight
		jenna.setCard(S9);
		jenna.setCard(DK);
		communityCards.add(S8);
		communityCards.add(H6);
		communityCards.add(D7);
		communityCards.add(C5);
		communityCards.add(D4);
		bestHand = jenna.setBestHand(communityCards);
		assertTrue("C5 H6 D7 S8 S9".equals(bestHand));
		assertTrue(bestHand.equals(jenna.getHand().toString()));

		jenna.clearCards();
		communityCards = new ArrayList<Card>();
		bestHand = "";

		// Community has high card, 6 out 7 cards make a straight
		jenna.setCard(S8);
		jenna.setCard(DK);
		communityCards.add(S9);
		communityCards.add(H6);
		communityCards.add(D7);
		communityCards.add(C5);
		communityCards.add(D4);
		bestHand = jenna.setBestHand(communityCards);
		assertTrue("C5 H6 D7 S8 S9".equals(bestHand));
		assertTrue(bestHand.equals(jenna.getHand().toString()));

		jenna.clearCards();
		communityCards = new ArrayList<Card>();
		bestHand = "";

		// User has high card, all 7 cards make a straight
		jenna.setCard(S9);
		jenna.setCard(D3);
		communityCards.add(S8);
		communityCards.add(H6);
		communityCards.add(D7);
		communityCards.add(C5);
		communityCards.add(D4);
		bestHand = jenna.setBestHand(communityCards);
		assertTrue("C5 H6 D7 S8 S9".equals(bestHand));
		assertTrue(bestHand.equals(jenna.getHand().toString()));

		jenna.clearCards();
		communityCards = new ArrayList<Card>();
		bestHand = "";

		// Community has high card, all 7 cards make a straight
		jenna.setCard(S8);
		jenna.setCard(D3);
		communityCards.add(S9);
		communityCards.add(H6);
		communityCards.add(D7);
		communityCards.add(C5);
		communityCards.add(D4);
		bestHand = jenna.setBestHand(communityCards);
		assertTrue("C5 H6 D7 S8 S9".equals(bestHand));
		assertTrue(bestHand.equals(jenna.getHand().toString()));

		jenna.clearCards();
		communityCards = new ArrayList<Card>();
		bestHand = "";
	}

	/**
	 * Tests if player can find 4 of a kind
	 * 
	 */
	@Test
	public void testGetBestHandFourofAKind() {

		Player jenna = new Player("Jenna");
		List<Card> communityCards = new ArrayList<Card>();
		String bestHand = "";

		// Four of a kind, user has 2 cards
		jenna.setCard(CA);
		jenna.setCard(SA);
		communityCards.add(DA);
		communityCards.add(HA);
		communityCards.add(D2);
		communityCards.add(H2);
		communityCards.add(S2);
		bestHand = jenna.setBestHand(communityCards);
		assertTrue(bestHand.equals(jenna.getHand().toString()));
		assertTrue("S2 SA CA HA DA".equals(bestHand));

		jenna.clearCards();
		communityCards = new ArrayList<Card>();
		bestHand = "";

		// Four of a kind, user has 1 card
		jenna.setCard(CA);
		jenna.setCard(S2);
		communityCards.add(DA);
		communityCards.add(HA);
		communityCards.add(D2);
		communityCards.add(H2);
		communityCards.add(SA);
		bestHand = jenna.setBestHand(communityCards);
		assertTrue("S2 CA SA HA DA".equals(bestHand));
		assertTrue(bestHand.equals(jenna.getHand().toString()));

		jenna.clearCards();
		communityCards = new ArrayList<Card>();
		bestHand = "";

		// Four of a kind user has 0 of the cards
		jenna.setCard(C2);
		jenna.setCard(S2);
		communityCards.add(DA);
		communityCards.add(HA);
		communityCards.add(CA);
		communityCards.add(H2);
		communityCards.add(SA);
		bestHand = jenna.setBestHand(communityCards);
		assertTrue("S2 SA CA HA DA".equals(bestHand));
		assertTrue(bestHand.equals(jenna.getHand().toString()));

		jenna.clearCards();
		communityCards = new ArrayList<Card>();
		bestHand = "";
	}

	/**
	 * Tests if player can find full house
	 * 
	 */
	@Test
	public void testGetBestHandFullHouse() {

		Player jenna = new Player("Jenna");
		List<Card> communityCards = new ArrayList<Card>();
		String bestHand = "";

		// Full House 1 possible
		jenna.setCard(CA);
		jenna.setCard(SA);
		communityCards.add(DA);
		communityCards.add(HK);
		communityCards.add(DK);
		communityCards.add(H2);
		communityCards.add(S3);
		bestHand = jenna.setBestHand(communityCards);
		assertTrue("DK HK SA CA DA".equals(bestHand));
		assertTrue(bestHand.equals(jenna.getHand().toString()));

		jenna.clearCards();
		communityCards = new ArrayList<Card>();
		bestHand = "";

		// Full House 2 possible
		jenna.setCard(CA);
		jenna.setCard(SA);
		communityCards.add(DA);
		communityCards.add(HK);
		communityCards.add(DK);
		communityCards.add(SK);
		communityCards.add(S2);
		bestHand = jenna.setBestHand(communityCards);
		assertTrue("SK DK SA CA DA".equals(bestHand));
		assertTrue(bestHand.equals(jenna.getHand().toString()));

		jenna.clearCards();
		communityCards = new ArrayList<Card>();
		bestHand = "";

		// Full house user has the pair
		jenna.setCard(CA);
		jenna.setCard(SA);
		communityCards.add(D3);
		communityCards.add(HK);
		communityCards.add(DK);
		communityCards.add(SK);
		communityCards.add(S2);
		bestHand = jenna.setBestHand(communityCards);
		assertTrue("SK DK HK SA CA".equals(bestHand));
		assertTrue(bestHand.equals(jenna.getHand().toString()));

		jenna.clearCards();
		communityCards = new ArrayList<Card>();
		bestHand = "";
	}

	/**
	 * Tests if player can find 3 of a kind
	 * 
	 */
	@Test
	public void testGetBestHandThreeOfAKind() {

		Player jenna = new Player("Jenna");
		List<Card> communityCards = new ArrayList<Card>();
		String bestHand = "";

		// 3 of a kind: user has 1 of the 3, user has high card
		jenna.setCard(S8);
		jenna.setCard(DK);
		communityCards.add(S2);
		communityCards.add(H8);
		communityCards.add(D8);
		communityCards.add(C5);
		communityCards.add(D4);
		bestHand = jenna.setBestHand(communityCards);
		assertTrue("C5 S8 D8 H8 DK".equals(bestHand));// should be
		assertTrue(bestHand.equals(jenna.getHand().toString()));
		jenna.clearCards();
		communityCards = new ArrayList<Card>();
		bestHand = "";

		// 3 of a kind: user has 1 of the 3, community has high card
		jenna.setCard(S8);
		jenna.setCard(S2);
		communityCards.add(DK);
		communityCards.add(H8);
		communityCards.add(D8);
		communityCards.add(C5);
		communityCards.add(D4);
		bestHand = jenna.setBestHand(communityCards);
		assertTrue("C5 S8 D8 H8 DK".equals(bestHand));
		assertTrue(bestHand.equals(jenna.getHand().toString()));
		jenna.clearCards();
		communityCards = new ArrayList<Card>();
		bestHand = "";

		// 3 of a kind: user has 2 of the 3, user has high card
		jenna.setCard(S8);
		jenna.setCard(D8);
		communityCards.add(D2);
		communityCards.add(H8);
		communityCards.add(S7);
		communityCards.add(C5);
		communityCards.add(D6);
		bestHand = jenna.setBestHand(communityCards);
		assertTrue("D6 S7 D8 S8 H8".equals(bestHand));// should be
		// assertTrue("D2 S7 D8 S8 H8".equals(bestHand)); thinks this is the
		// best hand
		assertTrue(bestHand.equals(jenna.getHand().toString()));
		jenna.clearCards();
		communityCards = new ArrayList<Card>();
		bestHand = "";

		// 3 of a kind: user has 2 of the 3, community has high card
		jenna.setCard(S8);
		jenna.setCard(D8);
		communityCards.add(DK);
		communityCards.add(H8);
		communityCards.add(S2);
		communityCards.add(C5);
		communityCards.add(D4);
		bestHand = jenna.setBestHand(communityCards);
		assertTrue("C5 D8 S8 H8 DK".equals(bestHand));// should be
		// assertTrue("S2 D8 S8 H8 DK".equals(bestHand)); thinks this is the
		// best
		assertTrue(bestHand.equals(jenna.getHand().toString()));
		jenna.clearCards();
		communityCards = new ArrayList<Card>();
		bestHand = "";
	}

	/**
	 * Tests if player can find best 2 pair
	 * 
	 */
	@Test
	public void testGetBestHandTwoPair() {

		Player jenna = new Player("Jenna");
		List<Card> communityCards = new ArrayList<Card>();
		String bestHand = "";

		// 2 Pair user has low pair
		jenna.setCard(S8);
		jenna.setCard(D8);
		communityCards.add(DK);
		communityCards.add(HK);
		communityCards.add(S2);
		communityCards.add(C5);
		communityCards.add(D4);
		bestHand = jenna.setBestHand(communityCards);
		assertTrue("C5 D8 S8 HK DK".equals(bestHand));
		assertTrue(bestHand.equals(jenna.getHand().toString()));

		jenna.clearCards();
		communityCards = new ArrayList<Card>();
		bestHand = "";

		// 2 pair user has high pair
		jenna.setCard(SK);
		jenna.setCard(DK);
		communityCards.add(D8);
		communityCards.add(H8);
		communityCards.add(S2);
		communityCards.add(C5);
		communityCards.add(D2);
		bestHand = jenna.setBestHand(communityCards);
		assertTrue("C5 H8 D8 DK SK".equals(bestHand));
		assertTrue(bestHand.equals(jenna.getHand().toString()));

		jenna.clearCards();
		communityCards = new ArrayList<Card>();
		bestHand = "";

		// 2 pair user has no pair high card
		jenna.setCard(SA);
		jenna.setCard(D8);
		communityCards.add(DK);
		communityCards.add(HK);
		communityCards.add(S2);
		communityCards.add(C2);
		communityCards.add(D4);
		bestHand = jenna.setBestHand(communityCards);
		assertTrue("C2 S2 HK DK SA".equals(bestHand));
		assertTrue(bestHand.equals(jenna.getHand().toString()));

		jenna.clearCards();
		communityCards = new ArrayList<Card>();
		bestHand = "";

		// 2 pair user has no pair low card
		jenna.setCard(S3);
		jenna.setCard(D8);
		communityCards.add(DK);
		communityCards.add(HK);
		communityCards.add(SA);
		communityCards.add(C2);
		communityCards.add(D2);
		bestHand = jenna.setBestHand(communityCards);
		assertTrue("D2 C2 HK DK SA".equals(bestHand));
		assertTrue(bestHand.equals(jenna.getHand().toString()));

		jenna.clearCards();
		communityCards = new ArrayList<Card>();
		bestHand = "";
	}

	/**
	 * Tests if player can find a pair
	 * 
	 */
	@Test
	public void testGetBestHandOnePair() {

		Player jenna = new Player("Jenna");
		List<Card> communityCards = new ArrayList<Card>();
		String bestHand = "";

		// Pair user has both cards in pair
		jenna.setCard(S8);
		jenna.setCard(D8);
		communityCards.add(DK);
		communityCards.add(H5);
		communityCards.add(SA);
		communityCards.add(C3);
		communityCards.add(D2);
		bestHand = jenna.setBestHand(communityCards);
		assertTrue("H5 D8 S8 DK SA".equals(bestHand));
		assertTrue(bestHand.equals(jenna.getHand().toString()));

		jenna.clearCards();
		communityCards = new ArrayList<Card>();
		bestHand = "";

		// Pair user has one card in pair
		jenna.setCard(S3);
		jenna.setCard(D8);
		communityCards.add(DK);
		communityCards.add(H4);
		communityCards.add(SA);
		communityCards.add(C8);
		communityCards.add(D2);
		bestHand = jenna.setBestHand(communityCards);
		assertTrue("H4 D8 C8 DK SA".equals(bestHand));
		assertTrue(bestHand.equals(jenna.getHand().toString()));

		jenna.clearCards();
		communityCards = new ArrayList<Card>();
		bestHand = "";

		// Community has pair
		jenna.setCard(S3);
		jenna.setCard(D8);
		communityCards.add(DK);
		communityCards.add(HK);
		communityCards.add(SA);
		communityCards.add(C4);
		communityCards.add(D2);
		bestHand = jenna.setBestHand(communityCards);
		assertTrue("C4 D8 HK DK SA".equals(bestHand));
		assertTrue(bestHand.equals(jenna.getHand().toString()));

		jenna.clearCards();
		communityCards = new ArrayList<Card>();
		bestHand = "";
	}

	/**
	 * Tests if player can find the best high card
	 * 
	 */
	@Test
	public void testGetBestHandHighCard() {

		Player jenna = new Player("Jenna");
		List<Card> communityCards = new ArrayList<Card>();
		String bestHand = "";

		// High Card user has highest card in hand
		jenna.setCard(S3);
		jenna.setCard(DA);
		communityCards.add(DK);
		communityCards.add(H6);
		communityCards.add(S7);
		communityCards.add(C4);
		communityCards.add(D2);
		bestHand = jenna.setBestHand(communityCards);
		assertTrue("C4 H6 S7 DK DA".equals(bestHand));
		assertTrue(bestHand.equals(jenna.getHand().toString()));

		jenna.clearCards();
		communityCards = new ArrayList<Card>();
		bestHand = "";

		// High Card user 2 highest cards in hand
		jenna.setCard(DK);
		jenna.setCard(DA);
		communityCards.add(S3);
		communityCards.add(H6);
		communityCards.add(S7);
		communityCards.add(C4);
		communityCards.add(D2);
		bestHand = jenna.setBestHand(communityCards);
		assertTrue("C4 H6 S7 DK DA".equals(bestHand));
		assertTrue(bestHand.equals(jenna.getHand().toString()));

		jenna.clearCards();
		communityCards = new ArrayList<Card>();
		bestHand = "";

		// High card user has 5th and 3rd cards in hand
		jenna.setCard(S7);
		jenna.setCard(DA);
		communityCards.add(DK);
		communityCards.add(H6);
		communityCards.add(S3);
		communityCards.add(C4);
		communityCards.add(D2);
		bestHand = jenna.setBestHand(communityCards);
		assertTrue("C4 H6 S7 DK DA".equals(bestHand));
		assertTrue(bestHand.equals(jenna.getHand().toString()));

		jenna.clearCards();
		communityCards = new ArrayList<Card>();
		bestHand = "";

		// High card user has 5th and 2nd cards in hand
		jenna.setCard(H6);
		jenna.setCard(DA);
		communityCards.add(DK);
		communityCards.add(S3);
		communityCards.add(S7);
		communityCards.add(C4);
		communityCards.add(D2);
		bestHand = jenna.setBestHand(communityCards);
		assertTrue("C4 H6 S7 DK DA".equals(bestHand));
		assertTrue(bestHand.equals(jenna.getHand().toString()));

		jenna.clearCards();
		communityCards = new ArrayList<Card>();
		bestHand = "";

		// High card user has 5th and 1st cards in hand
		jenna.setCard(C4);
		jenna.setCard(DA);
		communityCards.add(DK);
		communityCards.add(H6);
		communityCards.add(S7);
		communityCards.add(S3);
		communityCards.add(D2);
		bestHand = jenna.setBestHand(communityCards);
		assertTrue("C4 H6 S7 DK DA".equals(bestHand));
		assertTrue(bestHand.equals(jenna.getHand().toString()));

		jenna.clearCards();
		communityCards = new ArrayList<Card>();
		bestHand = "";

		// High card user has 4th and 3rd cards in hand
		jenna.setCard(S7);
		jenna.setCard(DK);
		communityCards.add(DA);
		communityCards.add(H6);
		communityCards.add(S3);
		communityCards.add(C4);
		communityCards.add(D2);
		bestHand = jenna.setBestHand(communityCards);
		assertTrue("C4 H6 S7 DK DA".equals(bestHand));
		assertTrue(bestHand.equals(jenna.getHand().toString()));

		jenna.clearCards();
		communityCards = new ArrayList<Card>();
		bestHand = "";

		// High card user has 4th & 2nd cards in hand
		jenna.setCard(H6);
		jenna.setCard(DK);
		communityCards.add(DA);
		communityCards.add(S7);
		communityCards.add(S3);
		communityCards.add(C4);
		communityCards.add(D2);
		bestHand = jenna.setBestHand(communityCards);
		assertTrue("C4 H6 S7 DK DA".equals(bestHand));
		assertTrue(bestHand.equals(jenna.getHand().toString()));

		jenna.clearCards();
		communityCards = new ArrayList<Card>();
		bestHand = "";

		// High card user has 3rd and 2nd cards in hand
		jenna.setCard(S7);
		jenna.setCard(H6);
		communityCards.add(DA);
		communityCards.add(DK);
		communityCards.add(S3);
		communityCards.add(C4);
		communityCards.add(D2);
		bestHand = jenna.setBestHand(communityCards);
		assertTrue("C4 H6 S7 DK DA".equals(bestHand));
		assertTrue(bestHand.equals(jenna.getHand().toString()));

		jenna.clearCards();
		communityCards = new ArrayList<Card>();
		bestHand = "";

		// High card user 4th in hand & low card
		jenna.setCard(D2);
		jenna.setCard(DK);
		communityCards.add(DA);
		communityCards.add(H6);
		communityCards.add(S7);
		communityCards.add(C4);
		communityCards.add(S3);
		bestHand = jenna.setBestHand(communityCards);
		assertTrue("C4 H6 S7 DK DA".equals(bestHand));
		assertTrue(bestHand.equals(jenna.getHand().toString()));

		jenna.clearCards();
		communityCards = new ArrayList<Card>();
		bestHand = "";

		// High card user has lowest cards
		jenna.setCard(S3);
		jenna.setCard(D2);
		communityCards.add(DK);
		communityCards.add(H6);
		communityCards.add(S7);
		communityCards.add(C4);
		communityCards.add(DA);
		bestHand = jenna.setBestHand(communityCards);
		assertTrue("C4 H6 S7 DK DA".equals(bestHand));
		assertTrue(bestHand.equals(jenna.getHand().toString()));

		jenna.clearCards();
		communityCards = new ArrayList<Card>();
		bestHand = "";
	}

	/**
	 * Tests if a bot will bet on a hand
	 */
	@Test
	public void testMakeBet() {
		Player jenna = new Player("Jenna");
		List<Card> communityCards = new ArrayList<Card>();

		// Betable hand
		jenna.setCard(CA);
		jenna.setCard(C10);
		communityCards.add(CK);
		communityCards.add(CJ);
		communityCards.add(CQ);
		communityCards.add(H2);
		communityCards.add(S2);
		jenna.setBestHand(communityCards);
		jenna.makeBet();
		assertTrue(!jenna.hasFolded());

		jenna.clearCards();
		communityCards = new ArrayList<Card>();

		// None betting hand
		jenna.setCard(HA);
		jenna.setCard(SK);
		communityCards.add(D3);
		communityCards.add(H2);
		communityCards.add(C5);
		communityCards.add(SQ);
		communityCards.add(S8);
		jenna.setBestHand(communityCards);
		jenna.makeBet();
		assertTrue(jenna.hasFolded());
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
	private final static Card CQ = new Card(Rank.Queen, Suit.Clubs);
	private final static Card CK = new Card(Rank.King, Suit.Clubs);
	private final static Card CA = new Card(Rank.Ace, Suit.Clubs);

	private final static Card D2 = new Card(Rank.Deuce, Suit.Diamonds);
	private final static Card D3 = new Card(Rank.Three, Suit.Diamonds);
	private final static Card D4 = new Card(Rank.Four, Suit.Diamonds);
	private final static Card D6 = new Card(Rank.Six, Suit.Diamonds);
	private final static Card D7 = new Card(Rank.Seven, Suit.Diamonds);
	private final static Card D8 = new Card(Rank.Eight, Suit.Diamonds);
	private final static Card DK = new Card(Rank.King, Suit.Diamonds);
	private final static Card DA = new Card(Rank.Ace, Suit.Diamonds);

	private final static Card H2 = new Card(Rank.Deuce, Suit.Hearts);
	private final static Card H4 = new Card(Rank.Four, Suit.Hearts);
	private final static Card H5 = new Card(Rank.Five, Suit.Hearts);
	private final static Card H6 = new Card(Rank.Six, Suit.Hearts);
	private final static Card H8 = new Card(Rank.Eight, Suit.Hearts);
	private final static Card H9 = new Card(Rank.Nine, Suit.Hearts);
	private final static Card HK = new Card(Rank.King, Suit.Hearts);
	private final static Card HA = new Card(Rank.Ace, Suit.Hearts);

	private final static Card S2 = new Card(Rank.Deuce, Suit.Spades);
	private final static Card S3 = new Card(Rank.Three, Suit.Spades);
	private final static Card S4 = new Card(Rank.Four, Suit.Spades);
	private final static Card S7 = new Card(Rank.Seven, Suit.Spades);
	private final static Card S8 = new Card(Rank.Eight, Suit.Spades);
	private final static Card S9 = new Card(Rank.Nine, Suit.Spades);
	private final static Card SQ = new Card(Rank.Queen, Suit.Spades);
	private final static Card SK = new Card(Rank.King, Suit.Spades);
	private final static Card SA = new Card(Rank.Ace, Suit.Spades);


}
