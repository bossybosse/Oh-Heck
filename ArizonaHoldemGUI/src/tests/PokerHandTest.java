package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import model.*;

/**
 * Test the Poker Hand Class
 * 
 * @author Richard Bosse, Jenna Franco
 * 
 * 
 */
public class PokerHandTest {

	/**
	 * Test the Suit Enum by printing them out
	 */
	@Test
	public void testSuitEnum() {
		String result = "";
		for (Suit suit : Suit.values())
			result += suit + " ";
		assertEquals("Diamonds Clubs Hearts Spades", result.trim());
	}

	/**
	 * Test the Rank Enum by printing them out
	 */
	@Test
	public void testRankEnum() {
		String result = "";
		for (Rank rank : Rank.values())
			result += rank + " ";
		assertEquals(
				"Deuce Three Four Five Six Seven Eight Nine Ten Jack Queen King Ace",
				result.trim());
	}

	/**
	 * Tests the printHand method
	 */
	@Test
	public void testPrintHand() {
		PokerHand hand = new PokerHand();
		hand = new PokerHand(C2, C3, C4, C5, C6);
		String theHand = "C2 C3 C4 C5 C6";
		assertTrue(theHand.equals(hand.toString()));

		hand = new PokerHand(C10, CJ, CQ, CK, CA);
		theHand = "C10 CJ CQ CK CA";
		assertTrue(theHand.equals(hand.toString()));

	}

	/**
	 * Test the Straight Flush comparison
	 */
	@Test
	public void testStraightFlush() {

		// One hand has straight flush
		PokerHand handOne = new PokerHand(C2, C3, C4, C5, C6);
		PokerHand handTwo = new PokerHand(S7, S8, C10, S10, S6);
		assertEquals(1, handOne.compareTo(handTwo), 0.01);
		assertEquals(-1, handTwo.compareTo(handOne), 0.01);

		// Both hands have different straight flush ace low and high
		handOne = new PokerHand(C2, C3, C4, C5, CA);
		handTwo = new PokerHand(SA, SK, SQ, S10, SJ);
		assertEquals(-1, handOne.compareTo(handTwo), 0.01);
		assertEquals(1, handTwo.compareTo(handOne), 0.01);

		// Both hands have same straight flush ace low
		handTwo = new PokerHand(S2, S3, S4, S5, SA);
		assertEquals(0, handOne.compareTo(handTwo), 0.01);
		assertEquals(0, handTwo.compareTo(handOne), 0.01);

		// Both hands have different straight flush ace low
		handOne = new PokerHand(C2, C3, C4, C5, CA);
		handTwo = new PokerHand(C6, C10, C7, C8, C9);
		assertEquals(-1, handOne.compareTo(handTwo), 0.01);
		assertEquals(1, handTwo.compareTo(handOne), 0.01);

		// Both hands have different straight flush ace high
		handOne = new PokerHand(C10, CJ, CQ, CK, CA);
		handTwo = new PokerHand(S9, S10, SJ, SQ, SK);
		assertEquals(1, handOne.compareTo(handTwo), 0.01);
		assertEquals(-1, handTwo.compareTo(handOne), 0.01);

		// Both hands have same Straight flush ace high
		handOne = new PokerHand(C10, CJ, CQ, CK, CA);
		handTwo = new PokerHand(SA, S10, SJ, SQ, SK);
		assertEquals(0, handOne.compareTo(handTwo), 0.01);
		assertEquals(0, handTwo.compareTo(handOne), 0.01);

		// One hand has straight flush ace low
		handOne = new PokerHand(C2, C3, C4, C5, CA);
		handTwo = nothingK8;
		assertEquals(1, handOne.compareTo(handTwo), 0.01);
		assertEquals(-1, handTwo.compareTo(handOne), 0.01);

		// One hand has straight flush ace high
		handOne = new PokerHand(C10, CJ, CQ, CK, CA);
		handTwo = nothingK8;
		assertEquals(1, handOne.compareTo(handTwo), 0.01);
		assertEquals(-1, handTwo.compareTo(handOne), 0.01);

		// Both hands have different straight flush
		handOne = new PokerHand(C2, C3, C4, C5, C6);
		handTwo = new PokerHand(S7, S8, S9, S10, SJ);
		assertEquals(-1, handOne.compareTo(handTwo), 0.01);
		assertEquals(1, handTwo.compareTo(handOne), 0.01);

		// Both hands have different straight flush
		handOne = new PokerHand(C2, C3, C6, C5, C4);
		handTwo = new PokerHand(SJ, S8, S9, S10, S7);
		assertEquals(-1, handOne.compareTo(handTwo), 0.01);
		assertEquals(1, handTwo.compareTo(handOne), 0.01);

	}

	/**
	 * Test the four of a kind comparison
	 */
	@Test
	public void testFourofAKind() {

		// One hand has four of a kind
		PokerHand handOne = new PokerHand(S9, C9, D9, H9, S8);
		PokerHand handTwo = new PokerHand(D2, S3, D10, HA, DA);
		assertEquals(1, handOne.compareTo(handTwo), 0.001);
		assertEquals(-1, handTwo.compareTo(handOne), 0.001);

		// One hand has four of a kind aces
		handOne = new PokerHand(SA, CA, DA, HA, S8);
		handTwo = new PokerHand(D2, S3, D10, DK, D9);
		assertEquals(1, handOne.compareTo(handTwo), 0.001);
		assertEquals(-1, handTwo.compareTo(handOne), 0.001);

		// Both hands have four of a kind
		handOne = new PokerHand(SA, CA, DA, HA, S8);
		handTwo = new PokerHand(D2, S2, H2, C2, D9);
		assertEquals(1, handOne.compareTo(handTwo), 0.001);
		assertEquals(-1, handTwo.compareTo(handOne), 0.001);

		// Both hands have four of a kind
		handOne = new PokerHand(S3, C3, D3, H3, S8);
		handTwo = new PokerHand(D2, S2, H2, C2, D9);
		assertEquals(1, handOne.compareTo(handTwo), 0.001);
		assertEquals(-1, handTwo.compareTo(handOne), 0.001);
	}

	/**
	 * Test the Full House comparison
	 */
	@Test
	public void testfullHouse() {

		// One hand has full house
		PokerHand handOne = new PokerHand(S3, D3, C3, SA, DA);
		PokerHand handTwo = new PokerHand(S4, D8, C9, CA, HA);
		assertEquals(1, handOne.compareTo(handTwo));
		assertEquals(-1, handTwo.compareTo(handOne));

		// Both hands have full house
		handOne = new PokerHand(S3, D3, C3, SA, DA);
		handTwo = new PokerHand(S2, D2, C2, CA, HA);
		assertEquals(1, handOne.compareTo(handTwo));
		assertEquals(-1, handTwo.compareTo(handOne));

		// Both have Full house different 3 of a kind
		handOne = new PokerHand(S3, D3, C3, SA, DA);
		handTwo = new PokerHand(S2, D2, C2, CA, HA);
		assertEquals(1, handOne.compareTo(handTwo));
		assertEquals(-1, handTwo.compareTo(handOne));

		handOne = new PokerHand(SK, DK, CK, SA, DA);
		handTwo = new PokerHand(S2, D2, CK, HK, SK);
		assertEquals(1, handOne.compareTo(handTwo));
		assertEquals(-1, handTwo.compareTo(handOne));

	}

	/**
	 * Test the flush comparison
	 */
	@Test
	public void testFlush() {

		// One hand has flush
		PokerHand handOne = new PokerHand(C4, CK, C10, C2, C5);
		PokerHand handTwo = new PokerHand(DA, CA, H2, C3, S10);
		assertEquals(1, handOne.compareTo(handTwo));
		assertEquals(-1, handTwo.compareTo(handOne));

		// Both hands have flush
		handOne = new PokerHand(C4, CK, C10, C2, C5);
		handTwo = new PokerHand(DJ, D2, D4, D3, D10);
		assertEquals(1, handOne.compareTo(handTwo));
		assertEquals(-1, handTwo.compareTo(handOne));

		// Both hands have flush with same high card
		handOne = new PokerHand(C4, CK, C10, C2, C5);
		handTwo = new PokerHand(D9, D2, D4, D3, DK);
		assertEquals(1, handOne.compareTo(handTwo));
		assertEquals(-1, handTwo.compareTo(handOne));

		// Both hands have flush with 2 same high card
		handOne = new PokerHand(C9, C8, C7, C3, CK);
		handTwo = new PokerHand(D9, D7, D6, D2, DK);
		assertEquals(1, handOne.compareTo(handTwo));
		assertEquals(-1, handTwo.compareTo(handOne));

		// Both hands have flush with 3 same high card
		handOne = new PokerHand(C9, C8, C7, C3, CK);
		handTwo = new PokerHand(D9, D8, D6, D2, DK);
		assertEquals(1, handOne.compareTo(handTwo));
		assertEquals(-1, handTwo.compareTo(handOne));

		// Both hands have flush with 4 same high card
		handOne = new PokerHand(C9, C8, C7, C3, CK);
		handTwo = new PokerHand(D9, D8, D7, D2, DK);
		assertEquals(1, handOne.compareTo(handTwo));
		assertEquals(-1, handTwo.compareTo(handOne));

		// Both hands have flush with no high card
		handOne = new PokerHand(C9, C8, C7, C3, CK);
		handTwo = new PokerHand(D9, D8, D7, D3, DK);
		assertEquals(0, handOne.compareTo(handTwo));
		assertEquals(0, handTwo.compareTo(handOne));

	}

	/**
	 * Test the straight comparison
	 */
	@Test
	public void testStraight() {

		// One hand has straight
		PokerHand handOne = new PokerHand(S6, C2, D3, C4, D5);
		PokerHand handTwo = new PokerHand(D9, S2, C3, SA, H7);
		assertEquals(1, handOne.compareTo(handTwo));
		assertEquals(-1, handTwo.compareTo(handOne));

		// One hand has straight ace high
		handOne = new PokerHand(SA, CK, DQ, CJ, D10);
		handTwo = new PokerHand(D9, S2, C3, S6, H7);
		assertEquals(1, handOne.compareTo(handTwo));
		assertEquals(-1, handTwo.compareTo(handOne));

		// One hand has straight ace low
		handOne = new PokerHand(SA, C2, D3, C4, D5);
		handTwo = new PokerHand(D9, S2, C3, S6, H7);
		assertEquals(1, handOne.compareTo(handTwo));
		assertEquals(-1, handTwo.compareTo(handOne));

		// Both hands have straight
		handOne = new PokerHand(S3, C4, D5, C6, D7);
		handTwo = new PokerHand(D2, D3, H4, S5, H6);
		assertEquals(1, handOne.compareTo(handTwo));
		assertEquals(-1, handTwo.compareTo(handOne));

		// Both hands have straight ace low
		handOne = new PokerHand(S3, C4, D5, C6, D7);
		handTwo = new PokerHand(D2, D3, H4, S5, HA);
		assertEquals(1, handOne.compareTo(handTwo));
		assertEquals(-1, handTwo.compareTo(handOne));

		// Both hands have straight ace high
		handOne = new PokerHand(SA, CK, DQ, CJ, D10);
		handTwo = new PokerHand(D2, D3, H4, S5, H6);
		assertEquals(1, handOne.compareTo(handTwo));
		assertEquals(-1, handTwo.compareTo(handOne));

		// Both hands have straight ace high and low
		handOne = new PokerHand(SA, CK, DQ, CJ, D10);
		handTwo = new PokerHand(D2, D3, H4, S5, HA);
		assertEquals(1, handOne.compareTo(handTwo));
		assertEquals(-1, handTwo.compareTo(handOne));

		// Both have same straight ace high
		handOne = new PokerHand(SA, CK, DQ, CJ, D10);
		handTwo = new PokerHand(DA, SK, HQ, SJ, H10);
		assertEquals(0, handOne.compareTo(handTwo));
		assertEquals(0, handTwo.compareTo(handOne));

		// Both have same straight ace low
		handOne = new PokerHand(SA, C2, D3, C4, D5);
		handTwo = new PokerHand(DA, S2, H3, S4, H5);
		assertEquals(0, handOne.compareTo(handTwo));
		assertEquals(0, handTwo.compareTo(handOne));

		// Both have same straight no ace
		handOne = new PokerHand(S6, C2, D3, C4, D5);
		handTwo = new PokerHand(D6, S2, H3, S4, H5);
		assertEquals(0, handOne.compareTo(handTwo));
		assertEquals(0, handTwo.compareTo(handOne));

	}

	/**
	 * Test Ace low Straight
	 */
	@Test
	public void testAceLowStraight() {
		PokerHand a = new PokerHand(CA, C2, C3, C4, H5);
		PokerHand b = new PokerHand(D2, D3, D4, D5, H6);
		boolean answer = a.compareTo(b) < 0;
		assertTrue(answer);
	}

	/**
	 * Test the 3 of a kind comparison
	 */
	@Test
	public void test3ofAKind() {

		// One hand has 3 of a kind
		PokerHand handOne = new PokerHand(D2, C2, H2, S10, S5);
		PokerHand handTwo = new PokerHand(S6, S2, H10, CA, D4);
		assertEquals(1, handOne.compareTo(handTwo));
		assertEquals(-1, handTwo.compareTo(handOne));

		// Both hands have 3 of a kind
		handOne = new PokerHand(DA, CA, HA, S10, S5);
		handTwo = new PokerHand(S3, C3, H3, SA, D2);
		assertEquals(1, handOne.compareTo(handTwo));
		assertEquals(-1, handTwo.compareTo(handOne));

	}

	/**
	 * Test the 2 pair comparison
	 */
	@Test
	public void test2Pair() {

		// One hand has 2 pair
		PokerHand handOne = new PokerHand(S3, D3, C4, S4, H10);
		PokerHand handTwo = new PokerHand(S10, C2, HA, C9, D4);
		assertEquals(1, handOne.compareTo(handTwo));
		assertEquals(-1, handTwo.compareTo(handOne));

		// One hand has 2 pair with aces
		handOne = new PokerHand(S3, D3, CA, SA, H10);
		handTwo = new PokerHand(S10, C2, HA, C9, D4);
		assertEquals(1, handOne.compareTo(handTwo));
		assertEquals(-1, handTwo.compareTo(handOne));

		// Both hands have 2 pair
		handOne = new PokerHand(S3, D3, C10, S10, H2);
		handTwo = new PokerHand(S5, C5, H6, C6, D7);
		assertEquals(1, handOne.compareTo(handTwo));
		assertEquals(-1, handTwo.compareTo(handOne));

		// Both hands have 2 pair one with aces
		handOne = new PokerHand(S3, D3, CA, SA, H10);
		handTwo = new PokerHand(S10, C10, HK, CK, D4);
		assertEquals(1, handOne.compareTo(handTwo));
		assertEquals(-1, handTwo.compareTo(handOne));

		// Both hands have 2 pair both with aces
		handOne = new PokerHand(S3, D3, CA, SA, H10);
		handTwo = new PokerHand(DA, C2, HA, CK, D2);
		assertEquals(1, handOne.compareTo(handTwo));
		assertEquals(-1, handTwo.compareTo(handOne));

		// Both hands have 2 pair with a matching pair
		handOne = new PokerHand(S6, D6, C7, S7, H10);
		handTwo = new PokerHand(H6, C6, H4, C4, D2);
		assertEquals(1, handOne.compareTo(handTwo));
		assertEquals(-1, handTwo.compareTo(handOne));

		handOne = new PokerHand(S6, D6, C7, S7, H10);
		handTwo = new PokerHand(H7, D7, H4, C4, D2);
		assertEquals(1, handOne.compareTo(handTwo));
		assertEquals(-1, handTwo.compareTo(handOne));

		// Both hands have 2 pair with both pairs matching
		handOne = new PokerHand(S6, D6, C7, S7, H10);
		handTwo = new PokerHand(H6, C6, H7, D7, D2);
		assertEquals(1, handOne.compareTo(handTwo));
		assertEquals(-1, handTwo.compareTo(handOne));

		handOne = new PokerHand(S6, D6, CK, SK, H10);
		handTwo = new PokerHand(H6, C6, HK, DK, D2);
		assertEquals(1, handOne.compareTo(handTwo));
		assertEquals(-1, handTwo.compareTo(handOne));

		// Both hands have 2 matching pair and matching high card
		handOne = new PokerHand(S3, D3, CK, SK, H6);
		handTwo = new PokerHand(H3, C3, HK, DK, D6);
		assertEquals(0, handOne.compareTo(handTwo));
		assertEquals(0, handTwo.compareTo(handOne));

		handOne = new PokerHand(SA, DA, C10, S10, HJ);
		handTwo = new PokerHand(HA, CA, H10, D10, D6);
		assertEquals(1, handOne.compareTo(handTwo));
		assertEquals(-1, handTwo.compareTo(handOne));

	}

	/**
	 * Test pair comparison
	 */
	@Test
	public void testPair() {

		// One hand has a pair
		PokerHand a = new PokerHand(C4, HK, D4, H3, DJ);
		PokerHand b = new PokerHand(H4, C5, C8, DA, S9);
		assertEquals(1, a.compareTo(b));
		assertEquals(-1, b.compareTo(a));

		// One hand has a pair with aces
		a = new PokerHand(CA, HK, DA, H3, DJ);
		b = new PokerHand(H4, C5, C8, DK, S9);
		assertEquals(1, a.compareTo(b));
		assertEquals(-1, b.compareTo(a));

		// Both hands have pair
		a = new PokerHand(CA, HK, DA, H3, DJ);
		b = new PokerHand(H2, C2, C8, DK, S9);
		assertEquals(1, a.compareTo(b));
		assertEquals(-1, b.compareTo(a));

		a = new PokerHand(CJ, HK, D4, H3, DJ);
		b = new PokerHand(H2, C2, C8, DK, SA);
		assertEquals(1, a.compareTo(b));
		assertEquals(-1, b.compareTo(a));

		// Both hands have same pair
		a = new PokerHand(C2, HA, D2, H3, DJ);
		b = new PokerHand(H2, S2, C8, DK, S9);
		assertEquals(1, a.compareTo(b));
		assertEquals(-1, b.compareTo(a));

		a = new PokerHand(CK, HK, D2, H3, D5);
		b = new PokerHand(H2, C3, C4, DK, SK);
		assertEquals(1, a.compareTo(b));
		assertEquals(-1, b.compareTo(a));

		// Both hands have pair of aces
		a = new PokerHand(CA, HA, D2, H3, DJ);
		b = new PokerHand(H2, C3, C8, DA, SA);
		assertEquals(1, a.compareTo(b));
		assertEquals(-1, b.compareTo(a));

		// Same pair and high card
		a = new PokerHand(CA, HA, D4, H5, DJ);
		b = new PokerHand(H2, C3, CJ, DA, SA);
		assertEquals(1, a.compareTo(b));
		assertEquals(-1, b.compareTo(a));

		// Same pair with 2 same high card
		a = new PokerHand(CA, HA, D3, H4, DJ);
		b = new PokerHand(DA, SA, C2, D4, SJ);
		assertEquals(1, a.compareTo(b));
		assertEquals(-1, b.compareTo(a));

		// Same pair with 3 same high card
		a = new PokerHand(CA, HA, D2, H3, DJ);
		b = new PokerHand(DA, SA, C2, D3, SJ);
		assertEquals(0, a.compareTo(b));
		assertEquals(0, b.compareTo(a));

	}

	/**
	 * Test high card comparison
	 */
	@Test
	public void testHighCard() {

		// Different High card
		PokerHand handOne = new PokerHand(C2, D3, H8, SA, C4);
		PokerHand handTwo = new PokerHand(D2, C3, C8, CK, C5);
		assertEquals(1, handOne.compareTo(handTwo));
		assertEquals(-1, handTwo.compareTo(handOne));

		// Has one matching high card
		handOne = new PokerHand(C2, D3, H8, SA, C4);
		handTwo = new PokerHand(D2, C3, C7, CA, C5);
		assertEquals(1, handOne.compareTo(handTwo));
		assertEquals(-1, handTwo.compareTo(handOne));

		// Has 2 matching high cards
		handOne = new PokerHand(CA, D10, H9, S8, C7);
		handTwo = new PokerHand(DA, C10, C2, C8, D7);
		assertEquals(1, handOne.compareTo(handTwo));
		assertEquals(-1, handTwo.compareTo(handOne));

		// Has 3 matching high cards
		handOne = new PokerHand(CA, D10, H9, S8, C7);
		handTwo = new PokerHand(DA, C10, C9, C2, D7);
		assertEquals(1, handOne.compareTo(handTwo));
		assertEquals(-1, handTwo.compareTo(handOne));

		// Has 4 matching high cards
		handOne = new PokerHand(CA, D10, H9, S8, C7);
		handTwo = new PokerHand(DA, C10, C9, C8, D2);
		assertEquals(1, handOne.compareTo(handTwo));
		assertEquals(-1, handTwo.compareTo(handOne));

		// Has 5 matching high cards
		handOne = new PokerHand(CA, D10, H9, S8, C7);
		handTwo = new PokerHand(DA, C10, C9, C8, D7);
		assertEquals(0, handOne.compareTo(handTwo));
		assertEquals(0, handTwo.compareTo(handOne));

	}

	/**
	 * Tests if the hand is bettable. Should return true for everything other
	 * than high card or a pair higher than a pair of 8's
	 */
	@Test
	public void testIsBettable() {

		// Straight Flush
		PokerHand handOne = new PokerHand(C2, C3, C4, C5, C6);
		assertTrue(handOne.isBetable());

		// Four of a kind
		handOne = new PokerHand(SA, CA, DA, HA, S8);
		assertTrue(handOne.isBetable());

		// Full House
		handOne = new PokerHand(SA, CA, DK, HK, SK);
		assertTrue(handOne.isBetable());

		// Flush
		handOne = new PokerHand(SA, S7, S2, SJ, S8);
		assertTrue(handOne.isBetable());

		// Straight
		handOne = new PokerHand(SA, CA, DA, HA, S8);
		assertTrue(handOne.isBetable());

		// 3 of a kind
		handOne = new PokerHand(S2, CA, DA, HA, S8);
		assertTrue(handOne.isBetable());

		// 2 pair
		handOne = new PokerHand(SA, CA, DK, HK, S8);
		assertTrue(handOne.isBetable());

		// Pair Above 8
		handOne = new PokerHand(SA, CA, D2, H3, S8);
		assertTrue(handOne.isBetable());

		// Pair of eights
		handOne = new PokerHand(S8, C8, D2, H3, S4);
		assertTrue(handOne.isBetable());

		// Pair below 8
		handOne = new PokerHand(S7, C7, D2, H3, S8);
		assertTrue(!handOne.isBetable());

		handOne = new PokerHand(S6, C6, D2, H3, S8);
		assertTrue(!handOne.isBetable());

		handOne = new PokerHand(S5, C5, D2, H3, S8);
		assertTrue(!handOne.isBetable());

		handOne = new PokerHand(S4, C4, D2, H3, S8);
		assertTrue(!handOne.isBetable());

		handOne = new PokerHand(S3, C4, D2, H3, S8);
		assertTrue(!handOne.isBetable());

		handOne = new PokerHand(S2, C2, D4, H3, S8);
		assertTrue(!handOne.isBetable());

		// High Card
		handOne = new PokerHand(S6, C5, D2, H3, S8);
		assertTrue(!handOne.isBetable());

	}

	/**
	 * Tests that the DuplicateCardException is thrown when duplicate cards are
	 * added to the same hand
	 */
	@Test(expected = DuplicateCardException.class)
	public void testDuplicateCardInOneHandA() {

		PokerHand a = new PokerHand(C2, C2, C2, C2, C2);

	}

	/**
	 * Tests that the DuplicateCardException is thrown when duplicate cards are
	 * added to the same hand
	 */
	@Test(expected = DuplicateCardException.class)
	public void testDuplicateCardInOneHandB() {

		PokerHand b = new PokerHand(C2, C3, C2, C3, C3);

	}

	/**
	 * Tests that the DuplicateCardException is thrown when duplicate cards are
	 * added to the same hand
	 */
	@Test(expected = DuplicateCardException.class)
	public void testDuplicateCardInOneHandC() {

		PokerHand c = new PokerHand(C2, C3, C4, C2, C2);

	}

	/**
	 * Tests that the DuplicateCardException is thrown when duplicate cards are
	 * added to the same hand
	 */
	@Test(expected = DuplicateCardException.class)
	public void testDuplicateCardInOneHandD() {

		PokerHand d = new PokerHand(C2, C3, C4, C5, C2);

	}

	/**
	 * Tests that the DuplicateCardException is thrown when duplicate cards are
	 * added to the same hand
	 */
	@Test(expected = DuplicateCardException.class)
	public void testDuplicateCardInOneHandE() {

		PokerHand e = new PokerHand(C4, C2, C3, C7, C2);

	}

	/**
	 * Tests that the DuplicateCardException is thrown when duplicate cards are
	 * added to the same hand
	 */
	@Test(expected = DuplicateCardException.class)
	public void testDuplicateCardInOneHandF() {

		PokerHand f = new PokerHand(C9, CJ, CQ, CK, CK);

	}

	// ********************all cards*************************
	private final static Card C2 = new Card(Rank.Deuce, Suit.Clubs);
	private final static Card C3 = new Card(Rank.Three, Suit.Clubs);
	private final static Card C4 = new Card(Rank.Four, Suit.Clubs);
	private final static Card C5 = new Card(Rank.Five, Suit.Clubs);
	private final static Card C6 = new Card(Rank.Six, Suit.Clubs);
	private final static Card C7 = new Card(Rank.Seven, Suit.Clubs);
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
	private final static Card D5 = new Card(Rank.Five, Suit.Diamonds);
	private final static Card D6 = new Card(Rank.Six, Suit.Diamonds);
	private final static Card D7 = new Card(Rank.Seven, Suit.Diamonds);
	private final static Card D8 = new Card(Rank.Eight, Suit.Diamonds);
	private final static Card D9 = new Card(Rank.Nine, Suit.Diamonds);
	private final static Card D10 = new Card(Rank.Ten, Suit.Diamonds);
	private final static Card DJ = new Card(Rank.Jack, Suit.Diamonds);
	private final static Card DQ = new Card(Rank.Queen, Suit.Diamonds);
	private final static Card DK = new Card(Rank.King, Suit.Diamonds);
	private final static Card DA = new Card(Rank.Ace, Suit.Diamonds);

	private final static Card H2 = new Card(Rank.Deuce, Suit.Hearts);
	private final static Card H3 = new Card(Rank.Three, Suit.Hearts);
	private final static Card H4 = new Card(Rank.Four, Suit.Hearts);
	private final static Card H5 = new Card(Rank.Five, Suit.Hearts);
	private final static Card H6 = new Card(Rank.Six, Suit.Hearts);
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
	private final static Card S5 = new Card(Rank.Five, Suit.Spades);
	private final static Card S6 = new Card(Rank.Six, Suit.Spades);
	private final static Card S7 = new Card(Rank.Seven, Suit.Spades);
	private final static Card S8 = new Card(Rank.Eight, Suit.Spades);
	private final static Card S9 = new Card(Rank.Nine, Suit.Spades);
	private final static Card S10 = new Card(Rank.Ten, Suit.Spades);
	private final static Card SJ = new Card(Rank.Jack, Suit.Spades);
	private final static Card SQ = new Card(Rank.Queen, Suit.Spades);
	private final static Card SK = new Card(Rank.King, Suit.Spades);
	private final static Card SA = new Card(Rank.Ace, Suit.Spades);

	private static PokerHand nothingK8 = new PokerHand(HK, HQ, HJ, H10, S8);

}
