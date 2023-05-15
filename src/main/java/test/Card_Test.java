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
		Card test_card1 = new Card(QUANTITY.ONE, COLOR.GREEN, SHADING.OPEN, SHAPE.DIAMOND);
		String stringify_test_card = test_card1.toString();
		String expected_result = "1 Green Open Diamond(s)";
		System.out.print("Generating a specific card: ");
		if(stringify_test_card.compareTo(expected_result) == 0) {
			TestUtility.OK();
		} else {
			TestUtility.FAILED();
			System.out.printf("Got: %s, Expected: %s\n", stringify_test_card, expected_result);
		}
		
		//This test generates a random card, and as long as there is no error, we should be fine (probably)
		System.out.print("Generating a random card: ");
		try {
			@SuppressWarnings("unused") //We are just testing to see if this will throw an exception
			Card random_card = Card.getRandomCard();
			TestUtility.OK();
		} catch (Exception e) {
			TestUtility.FAILED();
			System.out.println("(Exception Occurred)");
		}
		
		//Test Match Status
		System.out.print("Computing match status ");
		Card test_card2 = new Card(QUANTITY.ONE, COLOR.RED, SHADING.OPEN, SHAPE.SQUIGGLE);
		int expected_match_status1 = 10; //010
		Card test_card3 = new Card(QUANTITY.TWO, COLOR.RED, SHADING.STRIPED, SHAPE.SQUIGGLE);
		int expected_match_status2 = 5; //0101
		
		if(test_card2.matchStatus(test_card1) == expected_match_status1
				&& test_card2.matchStatus(test_card3) == expected_match_status2
				&& test_card1.matchStatus(test_card1) == 15) {
			TestUtility.OK();
		} else {
			TestUtility.FAILED();
		}
		
		
	}
	
	
}
