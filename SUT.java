
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
		
		addition(1,2);
		subtraction(2,1);
		multiplication(1,2);
		division(2,1);
		
	}
	
}
