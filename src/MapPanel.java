import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class MapPanel extends JPanel implements MouseListener, Observer {

	private static final long serialVersionUID = 1L;
	private final int WIDTH = 800, HEIGHT = 409;
	private String filename = "resources/map.png";
	private BufferedImage map = readImage(filename);
	private Board board;
	private Font font = new Font("Verdana", Font.BOLD, 11);
	private int currPixel;

	public MapPanel(Board board) {
		this.board = board;
		this.addMouseListener(this);
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		currPixel = 0;

		//printAllARGBDetails(map);
	}

	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(map, 0, 0, this.getWidth(), this.getHeight(), this);

		for (Continent continent : board.getWorld()) {
			for (Country country : continent.getCountries()) {
				Player player = country.getPlayer();
				Color playerColor = player.getColor();
				Point coordinate = country.getPoint();
				int armySize = country.getArmySize();
				
				g.setColor(playerColor);
				g.fillRect(coordinate.x - 1, coordinate.y - 1, 18, 18);
				g.setColor(Color.WHITE);
				g.fillRect(coordinate.x, coordinate.y, 16, 16);
				g.setColor(playerColor);
				g.setFont(font);
				g.drawString(String.format("%02d", armySize), coordinate.x, coordinate.y + 12);
			}
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
	
	public int getPixelColor(BufferedImage image, int x, int y) {
		return image.getRGB(x, y);
	}

	public void getPixelInfo(BufferedImage image, int x, int y) {
		int pixel = image.getRGB(x, y);
		//System.out.printf("Pixel: (%d, %d) Color: %s\n", x, y, getARGBPixelData(pixel));
		System.out.printf("new Point(%d, %d), 0x%x\n", x, y, pixel);
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
				System.out.printf("Pixel Location(%d,%d) - [%s]", i, j, getARGBPixelData(pixel));
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
	
	public void update() {
		this.revalidate();
		this.repaint();
	}
	
	public int getCurrPixel() {
		return this.currPixel;
	}

	@Override
	public void mouseClicked(java.awt.event.MouseEvent e) {
		// TODO Auto-generated method stub
		getPixelInfo(map, e.getX(), e.getY());
		this.currPixel = getPixelColor(map, e.getX(), e.getY());
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

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}
}