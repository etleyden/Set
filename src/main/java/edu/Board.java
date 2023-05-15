package edu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Board {
    private List<Card> cards;
    String[] shapes = {"oval", "squiggle", "diamond"};
    String[] colors = {"red", "purple", "green"};
    int[] numbers = {1, 2, 3};
    String[] shadings = {"solid", "striped", "outlined"};
    
    public Board() {
        cards = new ArrayList<>();
        for (String shape : shapes)
        	for (String color : colors)
        		for (int number : numbers)
        			for (String shading : shadings)
        				cards.add(new Card(shape, color, number, shading));
        Collections.shuffle(cards);
        cards = cards.subList(0, 12);
    }

    public boolean isSet(int[] cardIndices) {
        if (cardIndices.length != 3) {
            return false;
        }
        Card[] selectedCards = new Card[3];
        for (int i = 0; i < 3; i++) {
            if (cardIndices[i] < 0 || cardIndices[i] >= cards.size()) {
                return false;
            }
            selectedCards[i] = cards.get(cardIndices[i]);
        }
        return isValidSet(selectedCards);
    }

    private boolean isValidSet(Card[] cards) {
        boolean isValueSet = (cards[0].getNumber() == cards[1].getNumber() && cards[1].getNumber() == cards[2].getNumber())
                || (cards[0].getNumber() != cards[1].getNumber() && cards[1].getNumber() != cards[2].getNumber()
                        && cards[0].getNumber() != cards[2].getNumber());
        boolean isColorSet = (cards[0].getColor() == cards[1].getColor() && cards[1].getColor() == cards[2].getColor())
                || (cards[0].getColor() != cards[1].getColor() && cards[1].getColor() != cards[2].getColor()
                        && cards[0].getColor() != cards[2].getColor());
        boolean isShadingSet = (cards[0].getShading() == cards[1].getShading()
                && cards[1].getShading() == cards[2].getShading())
                || (cards[0].getShading() != cards[1].getShading() && cards[1].getShading() != cards[2].getShading()
                        && cards[0].getShading() != cards[2].getShading());
        boolean isShapeSet = (cards[0].getShape() == cards[1].getShape() && cards[1].getShape() == cards[2].getShape())
                || (cards[0].getShape() != cards[1].getShape() && cards[1].getShape() != cards[2].getShape()
                        && cards[0].getShape() != cards[2].getShape());
        return isValueSet && isColorSet && isShadingSet && isShapeSet;
    }


    public void removeCards(int[] cardIndices) {
        Arrays.sort(cardIndices);
        for (int i = 2; i >= 0; i--) {
            cards.remove(cardIndices[i]);
        }
        if (cards.size() < 12) {
            for (String shape : shapes)
            	for (String color : colors)
            		for (int number : numbers)
            			for (String shading : shadings) {
            				Card newCard = new Card(shape, color, number, shading);
            				cards.add(newCard);
                            if (!cards.contains(newCard)) {
                                cards.add(newCard);
                            }
                        }
            Collections.shuffle(cards);
            cards = cards.subList(0, 12);
        }
    }

    public List<Card> getCards() {
        return cards;
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
