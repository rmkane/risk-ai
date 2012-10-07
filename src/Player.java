import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;

public class Player implements PlayerActions {

	private String name;
	private Color color;
	private int points;
	private int draftedArmies;
	private int armySize;
	private ArrayList<Country> territories;
	private ArrayList<String> territoriesByName;
	private ArrayList<Card> hand;

	public Player() {
		this.name = "";
		this.color = new Color(0x000000);
		this.points = 0;
		this.draftedArmies = 80;
		this.territories = new ArrayList<Country>();
		this.territoriesByName = new ArrayList<String>();
		this.hand = new ArrayList<Card>();
	}

	public Player(String name, PlayerColors color, int armies) {
		this.name = name;
		this.color = color.color();
		this.points = 0;
		this.draftedArmies = armies;
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
	
	public int getDraftedArmies() {
		return draftedArmies;
	}

	public void setDraftedArmies(int draftedArmies) {
		this.draftedArmies = draftedArmies;
	}

	public ArrayList<Country> getTerritories() {
		return territories;
	}

	public void setTerritories(ArrayList<Country> territories) {
		this.territories = territories;
	}

	public String[] getTerritoriesByName() {
		String[] countries = new String[getTerritories().size()];
		Collections.sort(territories);
		for (int i = 0; i < countries.length; i++) {
			countries[i] = getTerritories().get(i).getName();
		}
		return countries;
	}

	public int getArmySize() {
		int armySize = 0;
		for (Country country : getTerritories()) {
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
	
	public void draftUnits(int units, Country country) {
		if (country.getPlayer() == null || country.getPlayer() == this && units <= this.getDraftedArmies()) {
			setDraftedArmies(getDraftedArmies() - units);
			country.setArmySize(country.getArmySize() + units);
		}
	}
	
	public void addUnits(int units, Country country) {
		if (country.getPlayer() == this || (country.getArmySize() == 0 && country.getPlayer() != this)) {
			country.setArmySize(country.getArmySize() + units);
		}
	}
	
	public void removeUnits(int units, Country country) {
		if (country.getPlayer() == this && country.getArmySize() >= units) {
			country.setArmySize(country.getArmySize() - units);
		}
	}
	
	public void loseBattle(Country country, Player enemy, int enemyUnits) {
		int yourUnits = country.getArmySize();
		this.removeUnits(yourUnits, country); // Remove your remaining units *could be 1 or 2
		this.getTerritories().remove(country); // Remove country from your territories list
		enemy.getTerritories().add(country); // Add the country to the Enemy's list of territories
		enemy.addUnits(enemyUnits, country); // Remember: Enemy player adds number of units greater than or equal to their final dice roll wins
		country.setPlayer(enemy); // Give the Enemy control of the territory 
	}

	@Override
	public String toString() {
		return "Player [name=" + name + ", color=" + color + ", points=" + points
				+ ", draftedArmies=" + draftedArmies + ", armySize=" + armySize
				+ ", territories=" + territories + ", territoriesByName="
				+ territoriesByName + ", hand=" + hand + "]";
	}

	@Override
	public void draft() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void attack() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void fortify() {
		// TODO Auto-generated method stub
		
	}
}