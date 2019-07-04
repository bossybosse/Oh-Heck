package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import model.*;

/**
 * GUI version of Arizona holdem
 * 
 * @author Richard Bosse, Jenna Franco
 * 
 */
@SuppressWarnings("serial")
public class ArizonaHoldem extends JFrame {

	/**
	 * Creates the gui and sets it to visible
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame aWindow = new ArizonaHoldem();
	}

	private JButton betButton;
	private JButton foldButton;
	private JButton newHandButton;
	private JButton quitButton;
	private PokerPlayerPanel bot1;
	private PokerPlayerPanel bot2;
	private PokerPlayerPanel bot3;
	private PokerPlayerPanel player;
	private JLabel thePot;
	private final static int BOT1_INDEX = 0;
	private final static int BOT2_INDEX = 1;
	private final static int BOT3_INDEX = 2;
	private final static int PLAYER_INDEX = 3;
	public final static int CARD_WIDTH = 72;
	public final static int CARD_HEIGHT = 96;
	public final static int PADDING = 5;
	public final static int NUM_OF_CARDS = 5;
	private CommunityCardPanel communityCards;
	private static GameTable holdem;

	/**
	 * Creates the gui and runs all the components
	 * 
	 */
	public ArizonaHoldem() {

		// Sets up the window format
		setUpWindow();

		// Sets up the game Model
		setUpModel();

		// Sets up the button action listeners
		setUpListeners();

		// Runs the game
		startNewGame();
	}

	private void setUpModel() {
		holdem = new GameTable();
		holdem.setPlayer("Bot 1");
		holdem.setPlayer("Bot 2");
		holdem.setPlayer("Bot 3");
		holdem.setPlayer("Player");

	}

	private void setUpListeners() {

		foldButton.addActionListener(new foldButtonListener());
		betButton.addActionListener(new betButtonListener());
		quitButton.addActionListener(new quitGameButtonListener());
		newHandButton.addActionListener(new newHandButtonListener());

	}

	/**
	 * Sets up the GUI window
	 * 
	 */
	private void setUpWindow() {

		// Sets up the Players JPlanes
		bot1 = new PokerPlayerPanel("Bot 1");
		bot2 = new PokerPlayerPanel("Bot 2");
		bot3 = new PokerPlayerPanel("Bot 3");
		player = new PokerPlayerPanel("Player");
		thePot = new JLabel("The Pot: $0.00", JLabel.CENTER);
		foldButton = new JButton("Fold");
		betButton = new JButton("Bet $5.00");
		quitButton = new JButton("Exit Program");
		newHandButton = new JButton("Deal New Hand");
		JPanel communityPanel = new JPanel();
		communityCards = new CommunityCardPanel();

		JPanel FoldAndBetbuttons = new JPanel();
		FoldAndBetbuttons.setLayout(new GridLayout(2, 1));
		FoldAndBetbuttons.add(betButton);
		FoldAndBetbuttons.add(foldButton);

		JPanel NewHandAndQuitbuttons = new JPanel();
		NewHandAndQuitbuttons.setLayout(new GridLayout(2, 1));
		NewHandAndQuitbuttons.add(newHandButton);
		NewHandAndQuitbuttons.add(quitButton);
		communityPanel.setLayout(new GridLayout(2,1));
		communityPanel.add(new JLabel("Community Cards"));
		communityPanel.add(communityCards);

		this.setLayout(new GridLayout(3, 3));
		this.add(thePot);
		this.add(communityPanel);

		this.add(new JLabel());
		this.add(bot1);
		this.add(bot2);
		this.add(bot3);
		this.add(FoldAndBetbuttons);
		this.add(player);
		this.add(NewHandAndQuitbuttons);

		// Set up the JFrame
		this.setSize(1000, 800);
		this.setResizable(true);
		this.setLocation(10, 10);
		this.setTitle("Arizona Holdem");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.pack();
	}

	private class foldButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			holdem.userFold(PLAYER_INDEX);
			player.updateStatus(true);
			// after the fold button has been clicked, disable bet & fold
			// buttons so they can't be clicked again
			foldButton.setEnabled(false);
			betButton.setEnabled(false);
			// enable new hand button after user chooses an action
			newHandButton.setEnabled(true);
			gamePlayAfterUserBetting();
		}
	}

	private class betButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			holdem.userBet(PLAYER_INDEX);
			player.updateStatus(false);
			// after the bet button has been clicked, disable bet & fold buttons
			// so they can't be clicked again
			betButton.setEnabled(false);
			foldButton.setEnabled(false);
			// enable new hand button after user chooses an action
			newHandButton.setEnabled(true);
			gamePlayAfterUserBetting();
		}
	}

	private class newHandButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// at the beginning of a new hand both are enabled
			foldButton.setEnabled(true);
			betButton.setEnabled(true);
			holdem.resetBoard();
			communityCards.reset();
			bot1.reset();
			bot2.reset();
			bot3.reset();
			player.reset();
			gamePlay();
			// want to disable button so it can't be clicked again until
			// bet/fold is chosen
			newHandButton.setEnabled(false);
		}
	}

	private class quitGameButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// exits the game
			JOptionPane.showMessageDialog(null, "Okay, thanks for playing!",
					"Goodbye", JOptionPane.PLAIN_MESSAGE);
			System.exit(0);

		}

	}

	private void startNewGame() {

		this.setVisible(true);
		// checks to see if user wants to play
		int play;
		// if not, quit the game
		play = JOptionPane.showConfirmDialog(null,
				"Do you want to play Arizona Hold 'em?", "Arizona Hold 'em",
				JOptionPane.YES_NO_OPTION);

		if (play == JOptionPane.NO_OPTION) {
			JOptionPane.showMessageDialog(null, "Okay, bye!", "Goodbye",
					JOptionPane.PLAIN_MESSAGE);
			System.exit(0);
		}

		// else run core of the code
		gamePlay();

	}

	private void gamePlay() {

		// Starts the game
		holdem.startGame();
		// initially this is not enabled
		newHandButton.setEnabled(false);
		// show community cards
		communityCards.updateCards(holdem.getCommunityCards());
		// set the bot cards
		bot1.setPlayerCards(holdem.getPlayersCards(BOT1_INDEX));
		bot2.setPlayerCards(holdem.getPlayersCards(BOT2_INDEX));
		bot3.setPlayerCards(holdem.getPlayersCards(BOT3_INDEX));
		// identify all bots' best hands
		bot1.setBestHand(holdem.getPlayersBestHand(BOT1_INDEX));
		bot2.setBestHand(holdem.getPlayersBestHand(BOT2_INDEX));
		bot3.setBestHand(holdem.getPlayersBestHand(BOT3_INDEX));

		// Updates the pot and the player balances
		updateBalances();
		thePot.setText("The Pot: " + holdem.getPot());

		// Sets and show your cards, identify best hand
		player.setPlayerCards(holdem.getPlayersCards(PLAYER_INDEX));
		player.setBestHand(holdem.getPlayersBestHand(PLAYER_INDEX));
		player.showCards();

		holdem.betting();
		bot1.updateStatus(holdem.getIsPlayerFolded(0));
		bot2.updateStatus(holdem.getIsPlayerFolded(1));
		bot3.updateStatus(holdem.getIsPlayerFolded(2));

	}

	private void gamePlayAfterUserBetting() {

		updateBalances();
		thePot.setText("The Pot: " + holdem.getPot());
		holdem.setWinner();
		// Show Winners cards
		Boolean[] theWinners = holdem.getWinningPlayersBooleanArray();

		bot1.showCards();
		bot1.showBestHand();
		bot2.showCards();
		bot2.showBestHand();
		bot3.showCards();
		bot3.showBestHand();
		player.showBestHand();

		if (theWinners[0]) {
			bot1.isWinner();
		}

		if (theWinners[1]) {
			bot2.isWinner();
		}

		if (theWinners[2]) {
			bot3.isWinner();
		}

		if (theWinners[3]) {
			player.isWinner();
		}

		updateBalances();
		thePot.setText("The Pot: " + holdem.getPot());
	}

	private void updateBalances() {

		bot1.setBalanceLbl(holdem.getPlayersBalance(BOT1_INDEX));
		bot2.setBalanceLbl(holdem.getPlayersBalance(BOT2_INDEX));
		bot3.setBalanceLbl(holdem.getPlayersBalance(BOT3_INDEX));
		player.setBalanceLbl(holdem.getPlayersBalance(PLAYER_INDEX));

	}
}
