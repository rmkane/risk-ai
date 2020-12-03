package rmkane.github.com.risk;

import java.util.ArrayList;
import java.util.Collections;

public class Die {

	private static java.util.Random r = new java.util.Random(
			System.currentTimeMillis());

	private static int[] histogram = new int[6];

	public static int roll() {
		int result = r.nextInt(6);
		histogram[result]++;
		return result + 1;
	}

	public static byte rollOff(int a, int d) {
		byte aWins = 0, dWins = 0;
		ArrayList<Integer> aRolls = new ArrayList<Integer>(), dRolls = new ArrayList<Integer>();
		while (a > 0) {
			aRolls.add(roll());
			--a;
		}
		while (d > 0) {
			dRolls.add(roll());
			--d;
		}

		Collections.sort(aRolls, Collections.reverseOrder());
		Collections.sort(dRolls, Collections.reverseOrder());

		System.out.println("Attacker:" + aRolls + " Defender:" + dRolls);

		for (int i = 0; i < aRolls.size() && i < dRolls.size(); i++) {
			if (aRolls.get(i) > dRolls.get(i))
				aWins++;
			else
				dWins++;
		}
		return (byte) (aWins | (dWins << 4));
	}

	public static int rand(int min, int max) {
		return min + (int) (Math.random() * ((max - min) + 1));
	}

	public static void main(String[] args) {
		
		// Number starting of units
		int iua = 72, attackerUnits = iua;
		int iud = 1, defenderUnits = iud;
		float diffFact = 0.25f; // Afford to lose

		int attackWins = 0, defendWins = 0; // Store wins
		
		System.out.println(((float)iua*diffFact));

		while (attackerUnits > 1 && defenderUnits > 0 && attackerUnits > (iua*diffFact)) {
			int attackerDice = attackerUnits > 3 ? 3 : attackerUnits > 2 ? 2 : 1;
			int defenderDice = defenderUnits > 1 ? 2 : 1;

			byte result = Die.rollOff(attackerDice, defenderDice);
			int numberOfAttackWins = result & 0xF;
			int numberOfDefendWins = result >> 4;

			// Decrement units
			defenderUnits -= numberOfAttackWins;
			attackerUnits -= numberOfDefendWins;
			
			// Track wins
			attackWins += numberOfAttackWins;
			defendWins += numberOfDefendWins;
		}
		
		System.out.println("\n" + (attackerUnits > 1 && defenderUnits == 0 ? "Attacker Wins" : "Draw"));
		
		System.out.printf("\nKills\nAttacker:%d\nDefender:%d\n\n", attackWins, defendWins);
		System.out.println("Histogram:");
		for (int h : histogram) {
			for (int i = 0; i < h; i++) {
				System.out.print('*');
			}
			System.out.println(" " + h);
		}
	}
}