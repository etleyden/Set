package test;

import edu.Card;
import edu.Card.COLOR;
import edu.Card.QUANTITY;
import edu.Card.SHADING;
import edu.Card.SHAPE;

public class Card_Test { 
	public static void main(String[] args) {
		System.out.println("TESTS: Card class");
		
		//This test generates a specific card, and matches to the correct stringified version
		Card test_card = new Card(QUANTITY.ONE, COLOR.GREEN, SHADING.OPEN, SHAPE.DIAMOND);
		String stringify_test_card = test_card.toString();
		String expected_result = "1 Green Open Diamond(s)";
		System.out.print("Generating a specific card: ");
		if(stringify_test_card.compareTo(expected_result) == 0) {
			System.out.printf("%sOK%s\n", ANSI_COLORS.GREEN.value, ANSI_COLORS.RESET.value);
		} else {
			System.out.printf("%sFAILED%s\n", ANSI_COLORS.RED.value, ANSI_COLORS.RESET.value);
			System.out.printf("Got: %s, Expected: %s\n", stringify_test_card, expected_result);
		}
		
		//This test generates a random card, and as long as there is no error, we should be fine (probably)
		System.out.print("Generating a random card: ");
		try {
			test_card = Card.getRandomCard();
			System.out.printf("%sOK%s\n", ANSI_COLORS.GREEN.value, ANSI_COLORS.RESET.value);
		} catch (Exception e) {
			System.out.printf("%sFAILED (Exception occurred)%s\n", ANSI_COLORS.RED.value, ANSI_COLORS.RESET.value);
		}
	}
}
