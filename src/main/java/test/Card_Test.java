package test;

import edu.Card;
import edu.Card.COLOR;
import edu.Card.QUANTITY;
import edu.Card.SHADING;
import edu.Card.SHAPE;

public class Card_Test {
	public static void main(String[] args) {
		Card test_card = new Card(QUANTITY.ONE, COLOR.GREEN, SHADING.OPEN, SHAPE.DIAMOND);
		String stringify_test_card = test_card.toString();
		String expected_result = "1 Green Open Diamond(s)";
		System.out.print("Card Test: ");
		if(stringify_test_card.compareTo(expected_result) == 0) {
			System.out.printf("%sOK%s\n", ANSI_COLORS.GREEN.value, ANSI_COLORS.RESET.value);
		} else {
			System.out.printf("%sFAILED%s\n", ANSI_COLORS.RED.value, ANSI_COLORS.RESET.value);
			System.out.printf("Got: %s, Expected: %s\n", stringify_test_card, expected_result);
		}
	}
}
