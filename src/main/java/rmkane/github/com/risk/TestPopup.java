package rmkane.github.com.risk;

import java.util.ArrayList;

public class TestPopup {
	
	public TestPopup() {

		Deck deck = new Deck();
		ArrayList<Card> hand = new ArrayList<Card>();
		
		int[] check = new int[4];
		
		for (int i = 0; i < 10; i++) {
			hand.add(deck.draw());
		}
		System.out.println("Before:");
		for (Card card : hand) System.out.printf("%s - %s\n", card.getCountryName(), card.getUnitType().getName());
		
		for (Card c : hand)
			check[c.getUnitType().id()]++;
		
		for (int i = 0; i < check.length; i++) {
			System.out.printf("%d: %d\n", i, check[i]);
		}
		
		int initialHandSize = hand.size() - 1;
		for (int i = initialHandSize; i >= 0; i--) {
			Card c = hand.get(i);
			deck.addToDiscard(c);
			hand.remove(c);
		}
	
		System.out.println("After:");
		for (Card card : hand) System.out.printf("%s - %s\n", card.getCountryName(), card.getUnitType().getName());
	}
	
	public static void main(String[] args) {
		new TestPopup();
	}
}
