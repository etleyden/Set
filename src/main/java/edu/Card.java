package edu;

public class Card {
	//All possible attributes of a card result in 3^4 different cards
	//Enums are implemented to make it easier to VALIDATE cards
	public enum SHAPE {
		DIAMOND("Diamond"), SQUIGGLE("Squiggle"), OVAL("Oval");
		public final String label;
		SHAPE(String label) {this.label = label;}
	}
	public enum SHADING {
		SOLID("Solid"), STRIPED("Striped"), OPEN("Open");
		public final String label;
		SHADING(String label) {this.label = label;}
	}
	public enum COLOR{
		RED("Red"), GREEN("Green"), PURPLE("Purple");
		public final String label;
		COLOR(String label) {this.label = label;}
	}
	public enum QUANTITY {
		ONE(1), TWO(2), THREE(3);
		public final int label;
		QUANTITY(int label) {this.label = label;}
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

    public SHAPE getShape() {
        return shape;
    }

    public COLOR getColor() {
        return color;
    }

    public QUANTITY getNumber() {
        return qty;
    }

    public SHADING getShading() {
        return shading;
    }

    @Override
    public String toString() {
    	return String.format("%d %s %s %s(s)", qty.label, color.label, shading.label, shape.label);
    }
}
