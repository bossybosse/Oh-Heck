package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Panel for showing player cards, folding/betting status, name, balance
 * 
 * @author Richard Bosse, Jenna Franco
 * 
 */
@SuppressWarnings("serial")
public class PokerPlayerPanel extends JPanel {

	private CardPanel playerCards;
	private JLabel playerName;
	private JLabel balanceAmount;
	private JLabel actionDiscription;
	private FiveCardPanel bestHand;
	private WinnerPanel winnerImage;

	public PokerPlayerPanel(String thePlayerName) {

		playerCards = new CardPanel();

		this.playerName = new JLabel(thePlayerName, JLabel.CENTER);
		balanceAmount = new JLabel("Balance: $100", JLabel.LEFT);
		actionDiscription = new JLabel("Waiting...", JLabel.LEFT);
		bestHand = new FiveCardPanel();
		winnerImage = new WinnerPanel();
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		c.gridx = 0;
		c.gridy = 0;
		c.ipadx = 5;
		c.gridwidth = 2;
		this.add(playerName, c);

		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		this.add(balanceAmount, c);

		c.gridx = 1;
		c.gridy = 1;
		this.add(actionDiscription, c);

		c.gridx = 0;
		c.gridy = 3;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.weighty = 1;
		this.add(new JLabel("Best Hand:"), c);
		
		c.gridx = 0;
		c.gridy = 2;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.weighty = 1;
		this.add(playerCards, c);

		c.gridx = 0;
		c.gridy = 4;
		c.gridwidth = 2;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.weighty = 1;
		this.add(bestHand, c);

		c.gridx = 1;
		c.gridy = 2;
		c.gridwidth = 0;
		c.weightx = 5;
		c.weighty = 5;
		c.fill = GridBagConstraints.BOTH;
		// c.anchor = GridBagConstraints.FIRST_LINE_START;
		this.add(winnerImage, c);

	}

	public void showCards() {
		playerCards.showCard();
		this.repaint();
	}

	public void showBestHand() {
		bestHand.showCards();
		this.repaint();
	}

	public void setPlayerCards(String theCards) {
		playerCards.setCards(theCards);
	}

	public void setBestHand(String bestHand) {
		this.bestHand.updateCards(bestHand);
	}

	public void setBalanceLbl(String playersBalance) {
		balanceAmount.setText("Balance: " + playersBalance);
		this.repaint();
	}

	public void updateStatus(boolean isFolded) {
		if (isFolded)
			actionDiscription.setText("Folds");

		else
			actionDiscription.setText("Bets $5.00");
		this.repaint();
	}

	public void reset() {
		actionDiscription.setText("Waiting...");
		playerCards.reset();
		bestHand.reset();
		winnerImage.reset();
		this.repaint();
	}

	public void isWinner() {

		winnerImage.showWinner();

	}
}
