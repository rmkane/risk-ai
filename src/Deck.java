import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Deck {
	private ArrayList<Card> deck;
	private ArrayList<Card> discard;

	public Deck() {
		deck = new ArrayList<Card>();
		discard = new ArrayList<Card>();

		deck.add(new Card("Afghanistan", UnitType.INFANTRY));
		deck.add(new Card("Alaska", UnitType.INFANTRY));
		deck.add(new Card("Alberta", UnitType.INFANTRY));
		deck.add(new Card("Argentina", UnitType.INFANTRY));
		deck.add(new Card("Brazil", UnitType.ARTILLERY));
		deck.add(new Card("Central America", UnitType.CAVALRY));
		deck.add(new Card("China", UnitType.CAVALRY));
		deck.add(new Card("Congo", UnitType.CAVALRY));
		deck.add(new Card("East Africa", UnitType.ARTILLERY));
		deck.add(new Card("Eastern Australia", UnitType.INFANTRY));
		deck.add(new Card("Eastern United States", UnitType.ARTILLERY));
		deck.add(new Card("Egypt", UnitType.INFANTRY));
		deck.add(new Card("Great Britain", UnitType.CAVALRY));
		deck.add(new Card("Greenland", UnitType.CAVALRY));
		deck.add(new Card("Iceland", UnitType.INFANTRY));
		deck.add(new Card("India", UnitType.INFANTRY));
		deck.add(new Card("Indonesia", UnitType.CAVALRY));
		deck.add(new Card("Irkutsk", UnitType.INFANTRY));
		deck.add(new Card("Japan", UnitType.INFANTRY));
		deck.add(new Card("Kamchatka", UnitType.CAVALRY));
		deck.add(new Card("Madagascar", UnitType.INFANTRY));
		deck.add(new Card("Middle East", UnitType.ARTILLERY));
		deck.add(new Card("Mongolia", UnitType.ARTILLERY));
		deck.add(new Card("New Guinea", UnitType.CAVALRY));
		deck.add(new Card("North Africa", UnitType.INFANTRY));
		deck.add(new Card("Northern Europe", UnitType.CAVALRY));
		deck.add(new Card("Northwest Territory", UnitType.ARTILLERY));
		deck.add(new Card("Ontario", UnitType.CAVALRY));
		deck.add(new Card("Peru", UnitType.CAVALRY));
		deck.add(new Card("Quebec", UnitType.ARTILLERY));
		deck.add(new Card("Scandinavia", UnitType.ARTILLERY));
		deck.add(new Card("Siam", UnitType.ARTILLERY));
		deck.add(new Card("Siberia", UnitType.ARTILLERY));
		deck.add(new Card("South Africa", UnitType.ARTILLERY));
		deck.add(new Card("Southern Europe", UnitType.CAVALRY));
		deck.add(new Card("Ukraine", UnitType.ARTILLERY));
		deck.add(new Card("Ural", UnitType.CAVALRY));
		deck.add(new Card("Venezuela", UnitType.ARTILLERY));
		deck.add(new Card("Western Australia", UnitType.ARTILLERY));
		deck.add(new Card("Western Europe", UnitType.INFANTRY));
		deck.add(new Card("Western United States", UnitType.INFANTRY));
		deck.add(new Card("Yakutsk", UnitType.CAVALRY));
		
		discard.add(new Card("Wild", UnitType.WILD));
		discard.add(new Card("Wild", UnitType.WILD));
		
		shuffle();
	}

	public void shuffle() {
		if (deckSize() > 0) {
			long seed = System.nanoTime();
			Collections.shuffle(deck, new Random(seed));
		}
	}
	
	public void addToDeck(Card card) {
		deck.add(card);
	}
	
	public void addToDiscard(Card card) {
		discard.add(card);
	}

	/** Move the discard to the deck if cards run out */
	public void emptyDeck() {
		if (deckSize() < 1 && discardSize() > 0) {
			for(int i = 0; i < discardSize(); i++) {
				deck.add(discard.get(i));
			}
		}
		shuffle();
	}
	
	public Card draw() {
		if (deckSize() > 0) {
			return deck.remove(0);
		}
		return null;
	}

	public int deckSize() {
		return deck.size();
	}
	
	public int discardSize() {
		return discard.size();
	}

	@Override
	public String toString() {
		return "Deck [deck=" + deck + ", discard=" + discard + "]";
	}
}