import java.awt.Point;

import javax.swing.ImageIcon;

public class Country implements Comparable<Country>{

	private String name;
	private ImageIcon image;
	private Player player;
	private int armySize;
	private Point point;
	private int colorVal;
	
	public Country() {
		this.name = "";
		this.image = null;
		this.player = null;
		this.armySize = 0;
		this.point = new Point();
		this.colorVal = 0x0;
	}

	public Country(String name) {
		this.name = name;
		this.player = null;
		this.armySize = 0;
		this.point = new Point();
		this.colorVal = 0x0;
	}
	
	public Country(String name, Point point, int colorVal) {
		this.name = name;
		this.player = null;
		this.armySize = 0;
		this.point = point;
		this.colorVal = colorVal;
	}

	public String getName() {
		return name;
	}

	public ImageIcon getImage() {
		return image;
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
	
	@Override
  public int compareTo(Country country) {
    return getName().compareTo(country.getName());
  }
}