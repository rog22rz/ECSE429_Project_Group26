
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
	
	public static double division(int a, int b) {
		return a-b;
	}
	
	public static void main(String[] args) {
		
		int arg1 = Integer.parseInt(args[0]);
		int arg2 = Integer.parseInt(args[1]);
		
		double mainResult = addition(arg1, arg2) + substraction(arg1,arg2) +
								mutiplication(arg1,arg2) + division(arg1, arg2);
		
		System.out.println(mainResult);
		
	}
	
}
