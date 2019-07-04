package gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * Creates winner image to mark winners in the game
 * 
 * @author Richard Bosse, Jenna Franco
 * 
 */
@SuppressWarnings("serial")
public class WinnerPanel extends JPanel {

	private Image winnerImage;

	public WinnerPanel() {

		Dimension dims = new Dimension(124, 100);
		setSize(dims);
		setPreferredSize(dims);

	}

	@Override
	public void paintComponent(Graphics g2) {

		g2.drawImage(winnerImage, 35, 5, null);

	}

	public void showWinner() {

		try { // Assume a folder named cardImages has two file 1.png and 3.png
			winnerImage = ImageIO.read(new File("cardImages/winner.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.repaint();
	}

	public void reset() {
		winnerImage = null;
		this.repaint();
	}

}
