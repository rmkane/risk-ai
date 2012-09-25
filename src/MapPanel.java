import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class MapPanel extends JPanel implements MouseListener {

	private static final long serialVersionUID = 1L;
	private final int WIDTH = 800, HEIGHT = 409;
	// URL imageURL =
	// this.getClass().getClassLoader().getResource("resources/map.png");
	// Image map = new ImageIcon(imageURL).getImage();
	String filename = "resources/map.png";
	BufferedImage map = readImage(filename);

	public MapPanel() {
		this.addMouseListener(this);
		setPreferredSize(new Dimension(WIDTH, HEIGHT));

		//printAllARGBDetails(map);
	}
	
	private void getTerritoryInfo() {
		// Update Info
	}

	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(map, 0, 0, this.getWidth(), this.getHeight(), this);

		int[][] coordinates = { {50, 41},{ 80, 105 }, {94, 70}, { 215, 262 }, { 363, 176 },
				{ 566, 162 }, { 449, 69 }, { 285, 21 } };
		for (int c = 0; c < coordinates.length; c++) {
			g.setColor(Color.BLACK);
			g.fillRect(coordinates[c][0] - 1, coordinates[c][1] - 1, 18, 18);
			g.setColor(Color.WHITE);
			g.fillRect(coordinates[c][0], coordinates[c][1], 16, 16);
			g.setColor(Color.BLACK);
			g.drawString("99", coordinates[c][0] + 1, coordinates[c][1] + 12);
		}
	}

	public BufferedImage readImage(String fileLocation) {
		BufferedImage img = null;
		try {
			URL url = this.getClass().getClassLoader().getResource(fileLocation);
			img = ImageIO.read(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}

	public void getPixelInfo(BufferedImage image, int x, int y) {
		int pixel = image.getRGB(x, y);
		System.out.printf("Pixel: (%d, %d) Color: %s\n", x, y, getARGBPixelData(pixel));
	}

	public void printAllARGBDetails(BufferedImage image) {
		int width = image.getWidth();
		int height = image.getHeight();
		System.out
				.println("Image Dimension: Height-" + height + ", Width-" + width);
		System.out.println("Total Pixels: " + (height * width));
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {

				int pixel = image.getRGB(i, j);
				System.out.println("Pixel Location(" + i + "," + j + ")- ["
						+ getARGBPixelData(pixel) + "]");
			}
		}
	}

	public String getARGBPixelData(int pixel) {
		int alpha = (pixel >> 24) & 0xFF;
		int red = (pixel >> 16) & 0xFF;
		int green = (pixel >> 8) & 0xFF;
		int blue = (pixel) & 0xFF;
		return String.format("ARGB(%d,%d,%d,%d)", alpha, red, green, blue);
	}

	@Override
	public void mouseClicked(java.awt.event.MouseEvent e) {
		// TODO Auto-generated method stub
		getPixelInfo(map, e.getX(), e.getY());
	}

	@Override
	public void mousePressed(java.awt.event.MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(java.awt.event.MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(java.awt.event.MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(java.awt.event.MouseEvent e) {
		// TODO Auto-generated method stub

	}
}