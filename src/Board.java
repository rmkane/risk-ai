import java.util.ArrayList;
import java.util.Set;

/**
 * 
 * @author Ryan
 * 
 * Reference Image:
 * http://upload.wikimedia.org/wikipedia/commons/thumb/a/aa/Risk_game_map_fixed.png/800px-Risk_game_map_fixed.png
 *
 */

public class Board {

	private UndirectedGraph<Country> territories = new UndirectedGraph<Country>();
	public static ArrayList<Continent> WORLD = new ArrayList<Continent>();
	
	public static Continent N_AMERICA = new Continent("North America", new ArrayList<Country>(), 5);
	public static Country ALASKA = new Country("Alaska");
	public static Country ALBERTA = new Country("Alberta");
	public static Country C_AMERICA = new Country("Central America");
	public static Country E_US = new Country("Eastern United States");
	public static Country GREENLAND = new Country("Greenland");
	public static Country NW_TERRITORY = new Country("Northwest Territory");
	public static Country ONTARIO = new Country("Ontario");
	public static Country QUEBEC = new Country("Quebec");
	public static Country W_US = new Country("Western United States");
	
	public static Continent S_AMERICA = new Continent("South America", new ArrayList<Country>(), 2);
	public static Country ARGENTINA = new Country("Argentina");
	public static Country BRAZIL = new Country("Brazil");
	public static Country PERU = new Country("Peru");
	public static Country VENEZUELA = new Country("Venezuela");
	
	public static Continent EUROPE = new Continent("Europe", new ArrayList<Country>(), 5);
	public static Country GREAT_BRITAIN = new Country("Great Britain");
	public static Country ICELAND = new Country("Iceland");
	public static Country N_EUROPE = new Country("Northern Europe");
	public static Country SCANDINAVIA = new Country("Scandinavia");
	public static Country S_EUROPE = new Country("Southern Europe");
	public static Country UKRAINE = new Country("Ukraine");
	public static Country W_EUROPE = new Country("Western Europe");
	
	public static Continent AFRICA = new Continent("Africa", new ArrayList<Country>(), 3);
	public static Country CONGO = new Country("Congo");
	public static Country E_AFRICA = new Country("East Africa");
	public static Country EGYPT = new Country("Egypt");
	public static Country MADAGASCAR = new Country("Madagascar");
	public static Country N_AFRICA = new Country("North Africa");
	public static Country S_AFRICA = new Country("South Africa");
	
	public static Continent ASIA = new Continent("Asia", new ArrayList<Country>(), 7);
	public static Country AFGHANISTAN = new Country("Afghanistan");
	public static Country CHINA = new Country("China");
	public static Country INDIA = new Country("India");
	public static Country IRKUTSK = new Country("Irkutsk");
	public static Country JAPAN = new Country("Japan");
	public static Country KAMCHATKA = new Country("Kamchatka");
	public static Country MIDDLE_EAST = new Country("Middle East");
	public static Country MONGOLIA = new Country("Mongolia");
	public static Country SIAM = new Country("Siam");
	public static Country SIBERIA = new Country("Siberia");
	public static Country URAL = new Country("Ural");
	public static Country YAKUTSK = new Country("Yakutsk");
	
	public static Continent OCEANIA = new Continent("Oceania", new ArrayList<Country>(), 2);
	public static Country E_AUSTRALIA = new Country("Eastern Australia");
	public static Country INDONESIA = new Country("Indonesia");
	public static Country NEW_GUINEA = new Country("New Guinea");
	public static Country W_AUSTRALIA = new Country("Western Australia");

	public Board() {
		// Add Continents to the World
		WORLD.add(N_AMERICA);
		WORLD.add(S_AMERICA);
		WORLD.add(EUROPE);
		WORLD.add(AFRICA);
		WORLD.add(ASIA);
		WORLD.add(OCEANIA);
		
		// Add Countries to Continents
		N_AMERICA.getCountries().add(ALASKA);
		N_AMERICA.getCountries().add(ALBERTA);
		N_AMERICA.getCountries().add(C_AMERICA);
		N_AMERICA.getCountries().add(E_US);
		N_AMERICA.getCountries().add(GREENLAND);
		N_AMERICA.getCountries().add(NW_TERRITORY);
		N_AMERICA.getCountries().add(ONTARIO);
		N_AMERICA.getCountries().add(QUEBEC);
		N_AMERICA.getCountries().add(W_US);
		S_AMERICA.getCountries().add(ARGENTINA);
		S_AMERICA.getCountries().add(BRAZIL);
		S_AMERICA.getCountries().add(PERU);
		S_AMERICA.getCountries().add(VENEZUELA);
		EUROPE.getCountries().add(GREAT_BRITAIN);
		EUROPE.getCountries().add(ICELAND);
		EUROPE.getCountries().add(N_EUROPE);
		EUROPE.getCountries().add(SCANDINAVIA);
		EUROPE.getCountries().add(S_EUROPE);
		EUROPE.getCountries().add(UKRAINE);
		EUROPE.getCountries().add(W_EUROPE);
		AFRICA.getCountries().add(CONGO);
		AFRICA.getCountries().add(E_AFRICA);
		AFRICA.getCountries().add(EGYPT);
		AFRICA.getCountries().add(MADAGASCAR);
		AFRICA.getCountries().add(N_AFRICA);
		AFRICA.getCountries().add(S_AFRICA);
		ASIA.getCountries().add(AFGHANISTAN);
		ASIA.getCountries().add(CHINA);
		ASIA.getCountries().add(INDIA);
		ASIA.getCountries().add(IRKUTSK);
		ASIA.getCountries().add(JAPAN);
		ASIA.getCountries().add(KAMCHATKA);
		ASIA.getCountries().add(MIDDLE_EAST);
		ASIA.getCountries().add(MONGOLIA);
		ASIA.getCountries().add(SIAM);
		ASIA.getCountries().add(SIBERIA);
		ASIA.getCountries().add(URAL);
		ASIA.getCountries().add(YAKUTSK);
		OCEANIA.getCountries().add(E_AUSTRALIA);
		OCEANIA.getCountries().add(INDONESIA);
		OCEANIA.getCountries().add(NEW_GUINEA);
		OCEANIA.getCountries().add(W_AUSTRALIA);

		// Add all the Countries to the Unconnected Graph
		territories.addNode(ALASKA);
		territories.addNode(ALBERTA);
		territories.addNode(C_AMERICA);
		territories.addNode(E_US);
		territories.addNode(GREENLAND);
		territories.addNode(NW_TERRITORY);
		territories.addNode(ONTARIO);
		territories.addNode(QUEBEC);
		territories.addNode(W_US);
		territories.addNode(ARGENTINA);
		territories.addNode(BRAZIL);
		territories.addNode(PERU);
		territories.addNode(VENEZUELA);
		territories.addNode(GREAT_BRITAIN);
		territories.addNode(ICELAND);
		territories.addNode(N_EUROPE);
		territories.addNode(SCANDINAVIA);
		territories.addNode(S_EUROPE);
		territories.addNode(UKRAINE);
		territories.addNode(W_EUROPE);
		territories.addNode(CONGO);
		territories.addNode(E_AFRICA);
		territories.addNode(EGYPT);
		territories.addNode(MADAGASCAR);
		territories.addNode(N_AFRICA);
		territories.addNode(S_AFRICA);
		territories.addNode(AFGHANISTAN);
		territories.addNode(CHINA);
		territories.addNode(INDIA);
		territories.addNode(IRKUTSK);
		territories.addNode(JAPAN);
		territories.addNode(KAMCHATKA);
		territories.addNode(MIDDLE_EAST);
		territories.addNode(MONGOLIA);
		territories.addNode(SIAM);
		territories.addNode(SIBERIA);
		territories.addNode(URAL);
		territories.addNode(YAKUTSK);
		territories.addNode(E_AUSTRALIA);
		territories.addNode(INDONESIA);
		territories.addNode(NEW_GUINEA);
		territories.addNode(W_AUSTRALIA);

		// Add all the edges between Territories
		try {
			territories.addEdge(ALASKA, ALBERTA);
			territories.addEdge(ALASKA, NW_TERRITORY);
			territories.addEdge(ALASKA, KAMCHATKA);
			territories.addEdge(ALBERTA, NW_TERRITORY);
			territories.addEdge(ALBERTA, ONTARIO);
			territories.addEdge(ALBERTA, W_US);
			territories.addEdge(C_AMERICA, E_US);
			territories.addEdge(C_AMERICA, W_US);
			territories.addEdge(C_AMERICA, VENEZUELA);
			territories.addEdge(E_US, ONTARIO);
			territories.addEdge(E_US, QUEBEC);
			territories.addEdge(E_US, W_US);
			territories.addEdge(GREENLAND, NW_TERRITORY);
			territories.addEdge(GREENLAND, ONTARIO);
			territories.addEdge(GREENLAND, QUEBEC);
			territories.addEdge(GREENLAND, ICELAND);
			territories.addEdge(NW_TERRITORY, ONTARIO);
			
			territories.addEdge(ARGENTINA, BRAZIL);
			territories.addEdge(ARGENTINA, PERU);
			territories.addEdge(BRAZIL, PERU);
			territories.addEdge(BRAZIL, VENEZUELA);
			territories.addEdge(BRAZIL, N_AFRICA);
			territories.addEdge(PERU, VENEZUELA);
			
			territories.addEdge(GREAT_BRITAIN, ICELAND);
			territories.addEdge(GREAT_BRITAIN, N_EUROPE);
			territories.addEdge(GREAT_BRITAIN, SCANDINAVIA);
			territories.addEdge(GREAT_BRITAIN, W_EUROPE);
			territories.addEdge(ICELAND, SCANDINAVIA);
			territories.addEdge(N_EUROPE, SCANDINAVIA);
			territories.addEdge(N_EUROPE, S_EUROPE);
			territories.addEdge(N_EUROPE, UKRAINE);
			territories.addEdge(N_EUROPE, W_EUROPE);
			territories.addEdge(SCANDINAVIA, UKRAINE);
			territories.addEdge(W_EUROPE, UKRAINE);
			territories.addEdge(W_EUROPE, W_EUROPE);
			territories.addEdge(W_EUROPE, EGYPT);
			territories.addEdge(W_EUROPE, N_AFRICA);
			territories.addEdge(W_EUROPE, MIDDLE_EAST);
			territories.addEdge(UKRAINE, AFGHANISTAN);
			territories.addEdge(UKRAINE, MIDDLE_EAST);
			territories.addEdge(UKRAINE, URAL);
			territories.addEdge(W_EUROPE, N_AFRICA);
			
			territories.addEdge(CONGO, E_AFRICA);
			territories.addEdge(CONGO, N_AFRICA);
			territories.addEdge(CONGO, S_AFRICA);
			territories.addEdge(E_AFRICA, EGYPT);
			territories.addEdge(E_AFRICA, MADAGASCAR);
			territories.addEdge(E_AFRICA, N_AFRICA);
			territories.addEdge(E_AFRICA, S_AFRICA);
			territories.addEdge(E_AFRICA, MIDDLE_EAST);
			territories.addEdge(EGYPT, N_AFRICA);
			territories.addEdge(EGYPT, MIDDLE_EAST);
			territories.addEdge(MADAGASCAR, S_AFRICA);
			
			territories.addEdge(AFGHANISTAN, CHINA);
			territories.addEdge(AFGHANISTAN, INDIA);
			territories.addEdge(AFGHANISTAN, MIDDLE_EAST);
			territories.addEdge(AFGHANISTAN, URAL);
			territories.addEdge(CHINA, INDIA);
			territories.addEdge(CHINA, MONGOLIA);
			territories.addEdge(CHINA, SIAM);
			territories.addEdge(CHINA, SIBERIA);
			territories.addEdge(CHINA, URAL);
			territories.addEdge(INDIA, SIAM);
			territories.addEdge(IRKUTSK, KAMCHATKA);
			territories.addEdge(IRKUTSK, MONGOLIA);
			territories.addEdge(IRKUTSK, SIBERIA);
			territories.addEdge(IRKUTSK, YAKUTSK);
			territories.addEdge(JAPAN, KAMCHATKA);
			territories.addEdge(JAPAN, MONGOLIA);
			territories.addEdge(KAMCHATKA, MONGOLIA);
			territories.addEdge(KAMCHATKA, YAKUTSK);
			territories.addEdge(SIAM, INDONESIA);
			territories.addEdge(SIBERIA, URAL);
			territories.addEdge(SIBERIA, YAKUTSK);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Iterable<Country> getTerritories() {
		return territories.getNodes();
	}
	
	public Set<Country> getNeighbors(Country country) {
		Set<Country> neighbors;
		try {
			neighbors = territories.getNeighbors(country);
		} catch (Exception e) {
			neighbors = null;
		}
		return neighbors;
	}
}