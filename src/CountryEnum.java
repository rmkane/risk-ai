public enum CountryEnum {
	AFGANISTAN(			"Afghanistan", 						UnitType.INFANTRY),
	ALASKA(					"Alaska", 								UnitType.INFANTRY),
	ALBERTA(				"Alberta", 								UnitType.INFANTRY),
	ARGENTINA(			"Argentina", 							UnitType.INFANTRY),
	BRAZIL(					"Brazil", 								UnitType.ARTILLERY),
	C_AMERICA(			"Central America", 				UnitType.CAVALRY),
	CHINA(					"China", 									UnitType.CAVALRY),
	CONGO(					"Congo", 									UnitType.CAVALRY),
	E_AFRICA(				"East Africa", 						UnitType.ARTILLERY),
	E_AUSTRALIA(		"Eastern Australia", 			UnitType.INFANTRY),
	E_US(						"Eastern United States", 	UnitType.ARTILLERY),
	EGYPT(					"Egypt", 									UnitType.INFANTRY),
	GREAT_BRITTIAN(	"Great Britain", 					UnitType.CAVALRY),
	GRRENLAND(			"Greenland", 							UnitType.CAVALRY),
	ICELAND(				"Iceland", 								UnitType.INFANTRY),
	INDIA(					"India", 									UnitType.INFANTRY),
	INDONESIA(			"Indonesia", 							UnitType.CAVALRY),
	IRKUTSK(				"Irkutsk", 								UnitType.INFANTRY),
	JAPAN(					"Japan", 									UnitType.INFANTRY),
	KAMCHATKA(			"Kamchatka", 							UnitType.CAVALRY),
	MADAGASCAR(			"Madagascar", 						UnitType.INFANTRY),
	MIDDLE_EAST(		"Middle East", 						UnitType.ARTILLERY),
	MONGOLIA(				"Mongolia", 							UnitType.ARTILLERY),
	NEW_GUINEA(			"New Guinea", 						UnitType.CAVALRY),
	N_AFRICA(				"North Africa", 					UnitType.INFANTRY),
	N_EUROPE(				"Northern Europe", 				UnitType.CAVALRY),
	NW_TERRITORIES(	"Northwest Territories", 	UnitType.ARTILLERY),
	ONTARIO(				"Ontario", 								UnitType.CAVALRY),
	PERU(						"Peru", 									UnitType.CAVALRY),
	QUEBEC(					"Quebec", 								UnitType.ARTILLERY),
	SCANDINAVIA(		"Scandinavia", 						UnitType.ARTILLERY),
	SIAM(						"Siam", 									UnitType.ARTILLERY),
	SIBERIA(				"Siberia", 								UnitType.ARTILLERY),
	S_AFRICA(				"South Africa", 					UnitType.ARTILLERY),
	S_EUROPE(				"Southern Europe", 				UnitType.CAVALRY),
	UKRAINE(				"Ukraine", 								UnitType.ARTILLERY),
	URAL(						"Ural", 									UnitType.CAVALRY),
	VENEZUELA(			"Venezuela", 							UnitType.ARTILLERY),
	W_AUSTRALIA(		"Western Australia", 			UnitType.ARTILLERY),
	W_EUROPE(				"Western Europe", 				UnitType.INFANTRY),
	W_US(						"Western United States", 	UnitType.INFANTRY),
	YAKUTSK(				"Yakutsk", 								UnitType.CAVALRY);

	private String countryName;
	private UnitType unitType;

	CountryEnum(String countryName, UnitType unitType) {
		this.countryName = countryName;
		this.unitType = unitType;
	}

	public String countryName() {
		return countryName;
	}

	public UnitType unitType() {
		return unitType;
	}
}
