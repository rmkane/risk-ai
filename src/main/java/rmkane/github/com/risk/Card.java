package rmkane.github.com.risk;

public class Card {

	private String countryName;
	private UnitType unitType;
	
	public Card() {
		this.countryName = null;
		this.unitType = null;
	}
	
	public Card(String countryName, UnitType unitType) {
		this.countryName = countryName;
		this.unitType = unitType;
	}
	
	public String getCountryName() {
		return countryName;
	}
	
		public UnitType getUnitType() {
		return unitType;
	}

		@Override
		public String toString() {
			return "Card [countryName=" + countryName + ", unitType=" + unitType.getName()
					+ "]\n";
		}
}