package edu;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Board implements Serializable {
	  //A list of the cards *dealt*.
    private List<Card> cards;
    public static final int NUM_CARDS = 12;
    public static final int SET_SIZE = 3;
    
    /**
     * Creates a board with cards *already dealt*
     */
    public Board() {
        cards = new ArrayList<>();
        for(int i = 0; i < NUM_CARDS; i++) {
        	//generates a random card, and ensures it is not a duplicate of dealt cards.
        	Card nextCard = fixDuplicates(cards, Card.getRandomCard());
        	cards.add(nextCard);
        }
    }
    /**
     * Takes a list of cards, and a proposed card to add to the list, and ensures it
     * has not already been added. Public for testing purposes
     * @param cards: a List of cards
     * @param card: the proposed card
     */
    public static Card fixDuplicates(List<Card> cards, Card card) {
    	boolean hasDuplicates;
    	do {
    		hasDuplicates = false;
    		for(int i = 0; i < cards.size(); i++) {
    			if(Objects.isNull(cards.get(i))) continue;
    			if(card.matchStatus(cards.get(i)) == 15) {
    				card = Card.getRandomCard();
    				hasDuplicates = true;
    			}
    		}
    	} while(hasDuplicates);
    	return card;
    }
    
    /**
     * Takes a set of cards, and validates it's set status. 
     * @param cards
     * @return
     */
    public static boolean isSet(Card[] cards) {
    	//This method only works when SET_SIZE == 3
    	if(cards.length < SET_SIZE) return false;
    	
    	//Since all the cards should have the same match_id, we can just
    	//set the id to whatever the first pair is
    	int match_id = -1; 
    	//Order of comparison: 0:1, 0:2, 1:2. Inflexible Set Size
    	return (cards[0].matchStatus(cards[1]) == cards[0].matchStatus(cards[2])
    				&& cards[0].matchStatus(cards[2]) == cards[1].matchStatus(cards[2]));
    }

    public Card getCard(int i) {
    	return cards.get(i);
    }
    public List<Card> getCards() {
        return cards;
    }
    public int getBoardSize() {
    	return cards.size();
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < cards.size(); i++) {
            sb.append(i).append(": ").append(cards.get(i)).append("\n");
        }
        return sb.toString();
    }
}
