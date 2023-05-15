package edu;

import java.util.Random;

public class Card {
	//All possible attributes of a card result in 3^4 different cards
	//Enums are implemented to make it easier to VALIDATE cards
	public enum SHAPE {
		DIAMOND("Diamond", 0), SQUIGGLE("Squiggle", 1), OVAL("Oval", 2);
		public final String label;
		public final int value;
		SHAPE(String label, int value) {this.label = label; this.value = value;}
	}
	public enum SHADING {
		SOLID("Solid", 0), STRIPED("Striped", 1), OPEN("Open", 2);
		public final String label;
		public final int value;
		SHADING(String label, int value) {this.label = label; this.value = value;}
	}
	public enum COLOR{
		RED("Red", 0), GREEN("Green", 1), PURPLE("Purple", 2);
		public final String label;
		public final int value;
		COLOR(String label, int value) {this.label = label; this.value = value;}
	}
	public enum QUANTITY {
		ONE(1, 0), TWO(2, 1), THREE(3, 2);
		public final int label;
		public final int value;
		QUANTITY(int label, int value) {this.label = label; this.value = value;}
	}
	
    private final SHAPE shape;
    private final SHADING shading;
    private final COLOR color;
    private final QUANTITY qty;
    /**
     * The order of attributes throughout this class will always be the same according to the conventional
     * order of adjectives in regular English: Quantity, Color, Shading, Shape <br>
     * Example: 3 red striped squiggle(s)
     * @param shape
     * @param shading
     * @param color
     * @param qty
     */
    public Card(QUANTITY qty, COLOR color, SHADING shading, SHAPE shape) {
        this.shape = shape;
        this.shading = shading;
        this.color = color;
        this.qty = qty;
    }
    //TODO: Build a constructur that allows a user to create a Card from numerical
    //representations of each enum attribute

    public SHAPE getShape() {
        return shape;
    }

    public COLOR getColor() {
        return color;
    }

    public QUANTITY getQuantity() {
        return qty;
    }

    public SHADING getShading() {
        return shading;
    }
    public static Card getRandomCard() {
    	//Generate random attributes
    	RandomEnumGenerator<QUANTITY> reg_qty = new RandomEnumGenerator<QUANTITY>(QUANTITY.class);
    	QUANTITY qty = (QUANTITY) reg_qty.randomEnum();
    	RandomEnumGenerator<COLOR> reg_color = new RandomEnumGenerator<COLOR>(COLOR.class);
    	COLOR color = (COLOR) reg_color.randomEnum();
    	RandomEnumGenerator<SHADING> reg_shading = new RandomEnumGenerator<SHADING>(SHADING.class);
    	SHADING shading = (SHADING) reg_shading.randomEnum();
    	RandomEnumGenerator<SHAPE> reg_shape = new RandomEnumGenerator<SHAPE>(SHAPE.class);
    	SHAPE shape = (SHAPE) reg_shape.randomEnum();
    	return new Card(qty, color, shading, shape);
    }
    /**
     * Compares two cards and returns the binary match status as an integer. <br>
     * Remember, the order is QTY, COLOR, SHADING, SHAPE. Always.
     * Example: <br>
     * Qty matches, Color does not match, Shading matches, shape does not match <br>
     * 1010 = 10
     * @param card
     * @return
     */
    public int matchStatus(Card card) {
    	int status = 0;
    	status += 8 * ((qty == card.getQuantity()) ? 1 : 0);
    	status += 4 * ((color == card.getColor()) ? 1 : 0);
    	status += 2 * ((shading == card.getShading()) ? 1 : 0);
    	status += 1 * ((shape == card.getShape()) ? 1 : 0);
    	return status;
    }
    @Override
    public String toString() {
    	return String.format("%d %s %s %s(s)", qty.label, color.label, shading.label, shape.label);
    }
}
//Utility class to generate a random card
class RandomEnumGenerator<T extends Enum<T>> {
    private static final Random PRNG = new Random();
    private final T[] values;

    public RandomEnumGenerator(Class<T> e) {
        values = e.getEnumConstants();
    }

    public T randomEnum() {
        return values[PRNG.nextInt(values.length)];
    }
}
