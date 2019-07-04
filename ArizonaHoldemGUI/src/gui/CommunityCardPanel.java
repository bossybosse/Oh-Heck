package gui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * Panel for displaying community cards
 * 
 * @author Richard Bosse, Jenna Franco
 * 
 */
@SuppressWarnings("serial")
public class CommunityCardPanel extends JPanel {

	private String[] cardNames;
	private ArrayList<Image> cardImages;
	public final static int CARD_WIDTH = 72;
	public final static int CARD_HEIGHT = 96;
	public final static int PADDING = 5;
	public final static int NUM_OF_CARDS = 5;
	private Image backOfCard;

	public CommunityCardPanel() {

		cardNames = new String[NUM_OF_CARDS];
		cardImages = new ArrayList<Image>();
		try { // Assume a folder named cardImages has two file 1.png and 3.png
			backOfCard = ImageIO.read(new File("cardImages/backCard.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (int i = 0; i < NUM_OF_CARDS; i++) {
			cardImages.add(backOfCard);
		}

	}

	@Override
	public void paintComponent(Graphics g) {

		Graphics2D g2 = (Graphics2D) g;

		int i = 0;
		for (Image img : cardImages) {
			g2.drawImage(img, PADDING + i * (PADDING + CARD_WIDTH), PADDING,
					null);
			i++;
		}
	}

	public void updateCards(String communityCards) {

		cardNames = communityCards.split(" ");
		cardImages = new ArrayList<Image>();
		for (String c : cardNames) {

			try {
				c.trim();
				Image temp = ImageIO.read(new File("cardImages/" + c + ".png"));
				cardImages.add(temp);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		this.repaint();
	}

	public void reset() {

		cardNames = new String[5];
		cardImages = new ArrayList<Image>();

		try { // Assume a folder named cardImages has two file 1.png and 3.png
			backOfCard = ImageIO.read(new File("cardImages/backCard.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (int i = 0; i < 5; i++) {
			cardImages.add(backOfCard);
		}

		this.repaint();
	}

}
