package gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * Panel for displaying individual cards
 * 
 * @author Richard Bosse, Jenna Franco
 * 
 */
@SuppressWarnings("serial")
public class CardPanel extends JPanel {

	public final static int CARD_WIDTH = 72;
	public final static int CARD_HEIGHT = 96;
	public final static int PADDING = 5;

	private String[] cardNames;
	private List<Image> images;
	private Image cardBack;

	public CardPanel() {

		try { // Assume a folder named cardImages has two file 1.png and 3.png
			cardBack = ImageIO.read(new File("cardImages/backCard.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		images = new ArrayList<Image>();
		this.setLayout(new GridLayout(2, 1));
		images.add(cardBack);
		images.add(cardBack);

		// Don't forget to calculate the size of this panel based on how many
		// cards you have
		Dimension dims = new Dimension(images.size() * (CARD_WIDTH + PADDING)
				+ PADDING, CARD_HEIGHT + PADDING * 2);
		setSize(dims);
		setPreferredSize(dims);

	}

	public void setCards(String theCards) {

		cardNames = theCards.split(" ");

	}

	public void showCard() {

		images = new ArrayList<Image>();

		try { // Assume a folder named cardImages has two file 1.png and 3.png

			for (String c : cardNames) {
				c.trim();
				images.add(ImageIO.read(new File("cardImages/" + c + ".png")));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.repaint();
	}

	public void hideCards() {

		images = new ArrayList<Image>();
		images.add(cardBack);
		images.add(cardBack);
	}

	@Override
	public void paintComponent(Graphics g2) {

		int i = 0;
		for (Image img : images) {
			g2.drawImage(img, PADDING + i * (PADDING + CARD_WIDTH), PADDING,
					null);
			i++;
		}
	}

	public void reset() {
		this.hideCards();
		this.repaint();
		cardNames = null;
	}

}
