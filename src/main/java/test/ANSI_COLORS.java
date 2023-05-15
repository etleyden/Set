package test;

public enum ANSI_COLORS {
	GREEN("\u001B[32m"), RED("\u001B[31m"), BLUE("\u001B[34m"),
	RESET("\u001B[0m");
	
	public final String value;
	ANSI_COLORS(String value) {this.value = value;}
}
