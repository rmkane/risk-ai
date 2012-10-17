import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.DecimalFormat;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Country implements Comparable<Country>{

	private String name;
	private Player player;
	private int armySize;
	private Point point;
	private int colorVal;
	private ImageIcon image;
	private float weight;
	
	public Country() {
		this.name = "";
		this.player = null;
		this.armySize = 0;
		this.point = new Point();
		this.colorVal = 0x0;
		this.image = null;
		this.weight = 0;
	}

	public Country(String name) {
		this.name = name;
		this.player = null;
		this.armySize = 0;
		this.point = new Point();
		this.colorVal = 0x0;
		setImage(name);
		this.weight = 0;
	}
	
	public Country(String name, Point point, int colorVal) {
		this.name = name;
		this.player = null;
		this.armySize = 0;
		this.point = point;
		this.colorVal = colorVal;
		setImage(name);
		this.weight = 0;
	}

	public String getName() {
		return name;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public int getArmySize() {
		return armySize;
	}

	public void setArmySize(int armySize) {
		this.armySize = armySize;
	}

	public Point getPoint() {
		return point;
	}

	public int getColorVal() {
		return colorVal;
	}
	
	public void setImage(String name) {
		String name_rpl = name.replace(" ", "_").toLowerCase();
		String fname = "resources/countries/" + name_rpl + ".png";
		//System.out.println(fname);
		this.image = new ImageIcon(readImage(fname));
		
	}		
	
	public BufferedImage readImage(String fileLocation) {
		System.out.println("LOADING: " + fileLocation);
		BufferedImage img = null;
		try {
			URL url = this.getClass().getClassLoader().getResource(fileLocation);
			//System.out.println(url.getPath());
			img = ImageIO.read(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}
	
	public ImageIcon getImage() {
		return image;
	}
	
	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}

	@Override
  public int compareTo(Country country) {
    return getName().compareTo(country.getName());
  }

	@Override
	public String toString() {
		DecimalFormat df = new DecimalFormat("#,###,##0.00000");
		
		return String.format("Name: %-24sWeight: %10s", name, (weight == 0) ? "#" : df.format(weight));
	}
}