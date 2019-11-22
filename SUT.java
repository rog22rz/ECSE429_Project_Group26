
public class SUT {
	
	public static int addition(int a, int b) {
		return a+b;
	}
	
	public static int subtraction(int a, int b) {
		return a-b;
	}
	
	public static int multiplication(int a, int b) {
		return a*b;
	}
	
	public static int division(int a, int b) {
		return a/b;
	}
	
	public static int mixed(int a, int b, int c) {
		return (a-b) * c;
	}
	
	public static void main(String[] args) {
		
		System.out.println(addition(args[0],args[1]));
		System.out.println(subtraction(args[0],args[1]));
		System.out.println(multiplication(args[0],args[1]));
		System.out.println(division(args[0],args[1]));
		
	}
	
}
