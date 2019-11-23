
public class injectedMutant7Line13add {
	
	public static int addition(int a, int b) {
		return a+b;
	}
	
	public static int subtraction(int a, int b) {
		return a-b;
	}
	
	public static int multiplication(int a, int b) {
		return a+b;
	}
	
	public static double division(int a, int b) {
		return a/b;
	}
	
	public static void main(String[] args) {
		
		int arg1 = Integer.parseInt(args[0]);
		int arg2 = Integer.parseInt(args[1]);
		
		System.out.println(Integer.toString(addition(arg1, arg2)));
		
	}
	
}
