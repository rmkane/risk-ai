import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

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
	public static Country ALASKA = new Country("Alaska", new Point(50, 41), 0xFF808000);
	public static Country ALBERTA = new Country("Alberta",new Point(93, 69), 0xFFF8F800);
	public static Country C_AMERICA = new Country("Central America",new Point(84, 165), 0xFFFFFF80);
	public static Country E_US = new Country("Eastern United States",new Point(136, 119), 0xFF787800);
	public static Country GREENLAND = new Country("Greenland",new Point(281, 20), 0xFFFFFF00);
	public static Country NW_TERRITORY = new Country("Northwest Territory",new Point(105, 42), 0xFF505027);
	public static Country ONTARIO = new Country("Ontario",new Point(145, 72), 0xFF949449);
	public static Country QUEBEC = new Country("Quebec",new Point(197, 73), 0xFFF8F880);
	public static Country W_US = new Country("Western United States",new Point(78, 105), 0xFF4f4f26);

	public static Continent S_AMERICA = new Continent("South America", new ArrayList<Country>(), 2);
	public static Country ARGENTINA = new Country("Argentina",new Point(189, 328), 0xFFFF0000);
	public static Country BRAZIL = new Country("Brazil",new Point(215, 261), 0xFF804040);
	public static Country PERU = new Country("Peru",new Point(172, 271), 0xFF800000);
	public static Country VENEZUELA = new Country("Venezuela",new Point(162, 212), 0xFFFF8080);

	public static Continent EUROPE = new Continent("Europe", new ArrayList<Country>(), 5);
	public static Country GREAT_BRITAIN = new Country("Great Britain", new Point(323, 77), 0xFF004080);
	public static Country ICELAND = new Country("Iceland", new Point(308, 51), 0xFF0000FF);
	public static Country N_EUROPE = new Country("Northern Europe", new Point(390, 79), 0xFF0000FE);
	public static Country SCANDINAVIA = new Country("Scandinavia", new Point(390, 51), 0xFF0080FF);
	public static Country S_EUROPE = new Country("Southern Europe", new Point(412, 100), 0xFF003F7F);
	public static Country UKRAINE = new Country("Ukraine", new Point(449, 67), 0xFF000080);
	public static Country W_EUROPE = new Country("Western Europe", new Point(345, 110), 0xFF007FFE);

	public static Continent AFRICA = new Continent("Africa", new ArrayList<Country>(), 3);
	public static Country CONGO = new Country("Congo", new Point(411, 226), 0xFFAE5700);
	public static Country E_AFRICA = new Country("East Africa", new Point(448, 193), 0xFFFF8000);
	public static Country EGYPT = new Country("Egypt", new Point(410, 150), 0xFF804000);
	public static Country MADAGASCAR = new Country("Madagascar", new Point(496, 293), 0xFFAD5600);
	public static Country N_AFRICA = new Country("North Africa", new Point(358, 175), 0xFFFF915B);
	public static Country S_AFRICA = new Country("South Africa", new Point(414, 288), 0xFF7f3f00);

	public static Continent ASIA = new Continent("Asia", new ArrayList<Country>(), 7);
	public static Country AFGHANISTAN = new Country("Afghanistan", new Point(515, 94), 0xFF80FF80);
	public static Country CHINA = new Country("China", new Point(635, 135), 0xFF008040);
	public static Country INDIA = new Country("India", new Point(563, 159), 0xFF008080);
	public static Country IRKUTSK = new Country("Irkutsk", new Point(608, 67), 0xFF7FFE00);
	public static Country JAPAN = new Country("Japan", new Point(725, 126), 0xFF80FF00);
	public static Country KAMCHATKA = new Country("Kamchatka",new Point(696, 41), 0xFF007F3F);
	public static Country MIDDLE_EAST = new Country("Middle East",new Point(465, 148), 0xFF008000);
	public static Country MONGOLIA = new Country("Mongolia",new Point(619, 97), 0xFF004000);
	public static Country SIAM = new Country("Siam",new Point(627, 182), 0xFF7FFE7F);
	public static Country SIBERIA = new Country("Siberia",new Point(553, 38), 0xFF007F00);
	public static Country URAL = new Country("Ural",new Point(510, 52), 0xFF003F00); // Change this dark-green (Mongolia)
	public static Country YAKUTSK = new Country("Yakutsk",new Point(619, 40), 0xFF007F7F);

	public static Continent OCEANIA = new Continent("Oceania", new ArrayList<Country>(), 2);
	public static Country E_AUSTRALIA = new Country("Eastern Australia",new Point(739, 308), 0xFF400040);
	public static Country INDONESIA = new Country("Indonesia",new Point(666, 226), 0xFF8000FF);
	public static Country NEW_GUINEA = new Country("New Guinea",new Point(746, 226), 0xFFFF00FF);
	public static Country W_AUSTRALIA = new Country("Western Australia",new Point(677, 305), 0xFF800040);

	public Board() {
		/*
		try {
			URL url = null;
			File xml = null;
			if (url == null) url  = this.getClass().getClassLoader().getResource("resources/map.xml");
			if (xml == null) xml = new File(url.getPath());
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xml);
			doc.getDocumentElement().normalize();
			
			System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
			NodeList nList = doc.getElementsByTagName("staff");
			System.out.println("-----------------------");
	 
			for (int temp = 0; temp < nList.getLength(); temp++) {
	 
			   Node nNode = nList.item(temp);
			   if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	 
			      Element eElement = (Element) nNode;
	 
			      System.out.println("First Name : " + getTagValue("continent", eElement));
			      System.out.println("Last Name : " + getTagValue("lastname", eElement));
		              System.out.println("Nick Name : " + getTagValue("nickname", eElement));
			      System.out.println("Salary : " + getTagValue("salary", eElement));
	 
			   }
			}
		} catch (Exception e) {} */
		

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
			territories.addEdge(ONTARIO, QUEBEC);
			territories.addEdge(ONTARIO, W_US);
			
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
			territories.addEdge(S_EUROPE, UKRAINE);
			territories.addEdge(S_EUROPE, N_AFRICA);
			territories.addEdge(S_EUROPE, EGYPT);
			territories.addEdge(W_EUROPE, S_EUROPE);
			territories.addEdge(W_EUROPE, EGYPT);
			territories.addEdge(W_EUROPE, N_AFRICA);
			territories.addEdge(W_EUROPE, MIDDLE_EAST);
			territories.addEdge(UKRAINE, AFGHANISTAN);
			territories.addEdge(UKRAINE, MIDDLE_EAST);
			territories.addEdge(UKRAINE, URAL);
			
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
	
	public ArrayList<Continent> getWorld() {
		return WORLD;
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
	
	private String getTagValue(String sTag, Element eElement) {
		NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();
	 
	        Node nValue = (Node) nlList.item(0);
	 
		return nValue.getNodeValue();
	}
	
	public static void main(String[] args) {
		new Board();
	}
	
}

