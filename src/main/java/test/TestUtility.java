package test;


public class TestUtility {
	enum ANSI_COLORS {
		GREEN("\u001B[32m"), RED("\u001B[31m"), BLUE("\u001B[34m"),
		RESET("\u001B[0m");
	
		public final String value;
		ANSI_COLORS(String value) {this.value = value;}
	}
	//Prints OK in green
	public static void OK() {
		System.out.printf("%sOK%s\n", ANSI_COLORS.GREEN.value, ANSI_COLORS.RESET.value);
	}
	//Prints FAILED in red
	public static void FAILED() {
		System.out.printf("%sFAILED%s\n", ANSI_COLORS.RED.value, ANSI_COLORS.RESET.value);
	}
}
