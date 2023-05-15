package test;

import java.util.ArrayList;

import edu.Board;
import edu.Card;
import edu.Card.*;

public class Board_Test {
	//TODO: Test stringification
	public static void main(String[] args) {
		System.out.println("TESTS: Board Class");
		
		System.out.print("Fixing a duplicate card: ");
		//A list of cards
		ArrayList<Card> cards = new ArrayList<>();
		cards.add(new Card(QUANTITY.ONE, COLOR.GREEN, SHADING.SOLID, SHAPE.SQUIGGLE));
		cards.add(new Card(QUANTITY.TWO, COLOR.GREEN, SHADING.OPEN, SHAPE.SQUIGGLE));
		cards.add(new Card(QUANTITY.TWO, COLOR.RED, SHADING.STRIPED, SHAPE.OVAL));
		
		//A new card to add to the list (but it's already in the list)
		Card next_card = new Card(QUANTITY.ONE, COLOR.GREEN, SHADING.SOLID, SHAPE.SQUIGGLE);
		
		//Fix the duplicate value
		next_card = Board.fixDuplicates(cards, next_card);
		
		//Check for duplicates in the list
		boolean test_passing = true;
		for(int i = 0; i < cards.size(); i++) {
			if(cards.get(i).matchStatus(next_card) == 15) {
				TestUtility.FAILED();
				test_passing = false;
				break;
			}
		}
		if(test_passing) TestUtility.OK();
		
		//Make sure a board generates without duplicate values
		System.out.print("Generating a board: ");
		Board test_board = new Board();
		test_passing = true;
		for(int i = 0; i < Board.NUM_CARDS; i++) {
			for(int j = i + 1; j < Board.NUM_CARDS; j++) {
				if(test_board.getCard(i).matchStatus(test_board.getCard(j)) == 15) {
					TestUtility.FAILED();
					System.out.printf("Card %d matches %d\n", i, j);
					System.out.println(test_board.getCard(i));
					System.out.println(test_board.getCard(j));
					test_passing = false;
					System.out.println();
					break;
				}
			}
			if(!test_passing) break;
		}
		if(test_passing) TestUtility.OK();
		
		//Validate the board is the correct size
		System.out.print("Checking board size: ");
		if(test_board.getBoardSize() == Board.NUM_CARDS) {
			TestUtility.OK();
		} else {
			TestUtility.FAILED();
		}
		
		//TODO: Test set validation
	}
}
