import java.util.ArrayList;

public class Continent {
	private String name;
	private ArrayList<Country> countries;
	private int bonusArmies;

	public Continent() {
		this.name = "";
		this.countries = new ArrayList<Country>();
		this.bonusArmies = 0;
	}

	public Continent(String name, ArrayList<Country> countries, int bonusArmies) {
		this.name = name;
		this.countries = countries;
		this.bonusArmies = bonusArmies;
	}

	public String getName() {
		return name;
	}

	public ArrayList<Country> getCountries() {
		return countries;
	}

	public int getBonusArmies() {
		return bonusArmies;
	}
}
