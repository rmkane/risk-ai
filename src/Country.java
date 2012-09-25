import javax.swing.ImageIcon;

public class Country implements Comparable<Country>{

	private String name;
	private ImageIcon image;
	private Player player;
	private int armySize;
	

	public Country() {
		this.name = "";
		this.image = null;
		this.player = null;
		this.armySize = 0;
	}

	public Country(String name) {
		this.name = name;
		this.player = null;
		this.armySize = 0;

		//String filename = ("countries/").concat(name.replace(' ', '_').toLowerCase().concat(".png"));
		//URL imageURL = this.getClass().getClassLoader().getResource(filename);
		//image = new ImageIcon(imageURL);		
		//System.out.println(("Loaded: ").concat(filename));
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

	public static void main(String[] args) {
		//new Country("Brazil");
	}
	
	@Override
  public int compareTo(Country country) {
    return getName().compareTo(country.getName());
  }
}