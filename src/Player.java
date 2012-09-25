import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;

public class Player {

	private String name;
	private Color color;
	private int points;
	private int armies;
	private ArrayList<Country> territories;
	private ArrayList<String> territoriesByName;
	private ArrayList<Card> hand;

	public Player() {
		this.name = "";
		this.color = new Color(0x000000);
		this.points = 0;
		this.armies = 80;
		this.territories = new ArrayList<Country>();
		this.territoriesByName = new ArrayList<String>();
		this.hand = new ArrayList<Card>();
	}

	public Player(String name, PlayerColors color, int armies) {
		this.name = name;
		this.color = color.color();
		this.points = 0;
		this.armies = armies;
		this.territories = new ArrayList<Country>();
		this.territoriesByName = new ArrayList<String>();
		this.hand = new ArrayList<Card>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public int getArmies() {
		return armies;
	}

	public void setArmies(int armies) {
		this.armies = armies;
	}

	public ArrayList<Country> getTerritories() {
		return territories;
	}

	public void setTerritories(ArrayList<Country> territories) {
		this.territories = territories;
	}

	public ArrayList<Country> getCountries() {
		return territories;
	}

	public String[] getTerritoriesByName() {
		String[] countries = new String[getCountries().size()];
		Collections.sort(territories);
		for (int i = 0; i < countries.length; i++) {
			countries[i] = getCountries().get(i).getName();
		}
		return countries;
	}

	public int getArmySize() {
		int armySize = 0;
		for (Country country : getCountries()) {
			armySize += country.getArmySize();
		}
		return armySize;
	}

	public ArrayList<Card> getHand() {
		return hand;
	}

	public void setHand(ArrayList<Card> hand) {
		this.hand = hand;
	}
	
	public void addUnits(int units, Country country) {
		if (getArmies() > 0 && units <= getArmies() && country.getPlayer() == this) {
			setArmies(getArmies() - units);
			country.setArmySize(country.getArmySize() + units);
		}
	}
	
	public void removeUnits(int units, Country country) {
		if (country.getArmySize() > 1 && units < country.getArmySize() && country.getPlayer() == this) {
			setArmies(getArmies() + units);
			country.setArmySize(country.getArmySize() - units);
		}
	}

	@Override
	public String toString() {
		return "Player [name=" + name + ", color=" + color + ", points=" + points
				+ ", armies=" + armies + ", territories=" + territories
				+ ", territoriesByName=" + territoriesByName + ", hand=" + hand + "]";
	}

}